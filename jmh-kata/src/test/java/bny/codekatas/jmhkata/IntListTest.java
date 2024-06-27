/*
 * Copyright 2024 The Bank of New York Mellon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bny.codekatas.jmhkata;

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
