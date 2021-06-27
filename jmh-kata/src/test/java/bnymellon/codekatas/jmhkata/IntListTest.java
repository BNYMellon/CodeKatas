/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.jmhkata;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.IntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * This is a set of unit tests to validate the code is working as expected.
 */
public class IntListTest
{
    private List<Integer> jdkList;
    private IntList ecIntList;
    private MutableList<Integer> ecList;
    private ExecutorService executor;
    private long expectedSum;

    @BeforeEach
    public void setUp()
    {
        this.executor = Executors.newWorkStealingPool();
        var iterator = new Random(1L).ints(-1000, 1000).iterator();
        this.ecList = FastList.newWithNValues(1_000_000, iterator::nextInt);
        this.jdkList = new ArrayList<>(1_000_000);
        this.jdkList.addAll(this.ecList);
        this.ecIntList = this.ecList.collectInt(i -> i, new IntArrayList(1_000_000));
        this.initializeExpectedSum();
    }

    public void initializeExpectedSum()
    {
        this.expectedSum = 0;
        for (var integer : this.jdkList)
        {
            this.expectedSum += integer.longValue();
        }
    }

    @Test
    public void sum()
    {
        Assertions.assertEquals(this.expectedSum, this.jdkList.stream().mapToLong(i -> i).sum());
        Assertions.assertEquals(this.expectedSum, this.jdkList.parallelStream().mapToLong(i -> i).sum());
        Assertions.assertEquals(this.expectedSum, this.ecIntList.sum());
        Assertions.assertEquals(this.expectedSum, this.ecList.sumOfInt(i -> i));
        Assertions.assertEquals(this.expectedSum, this.ecList.asParallel(this.executor, 100_000).sumOfInt(i -> i));
    }

    @Test
    public void filter()
    {
        Assertions.assertEquals(this.jdkList.stream().filter(i -> i % 2 == 0).collect(Collectors.toList()),
                            this.ecList.select(i -> i % 2 == 0));
        Assertions.assertEquals(this.ecIntList.select(i -> i % 2 == 0),
                            this.ecList.select(i -> i % 2 == 0).collectInt(Integer::intValue));
        Assertions.assertEquals(this.jdkList.parallelStream().filter(i -> i % 2 == 0).collect(Collectors.toList()),
                            this.ecList.asParallel(this.executor, 100_000).select(i -> i % 2 == 0).toList());
    }

    @Test
    public void transform()
    {
        Assertions.assertEquals(this.jdkList.stream().mapToInt(i -> i * 2).boxed().collect(Collectors.toList()),
                            this.ecList.collect(i -> i * 2).toList());
        Assertions.assertEquals(this.ecIntList.collectInt(i -> i * 2, IntLists.mutable.empty()),
                            this.ecList.collect(i -> i * 2).collectInt(Integer::intValue).toList());
        Assertions.assertEquals(this.jdkList.parallelStream().mapToInt(i -> i * 2).boxed().collect(Collectors.toList()),
                            this.ecList.asParallel(this.executor, 100_000).collect(i -> i * 2).toList());
    }
}
