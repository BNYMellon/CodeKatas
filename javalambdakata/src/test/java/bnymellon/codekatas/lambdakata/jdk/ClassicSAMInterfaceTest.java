/*
 * Copyright 2017 BNY Mellon.
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

package bnymellon.codekatas.lambdakata.jdk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.list.Interval;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test will illustrate how you can use lambdas with older JDK Single Abstract Method types
 * like Comparator, Runnable and Callable.
 *
 * Please refer to this tutorial for an overview of Lambdas for Java 8.
 * @see <a href="http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/index.html">Lambda Quickstart</a>
 *
 * Then follow the TODOs in each test and convert the anonymous inner classes to lambdas and/or method references.
 */
public class ClassicSAMInterfaceTest
{
    @Test
    public void comparator()
    {
        // TODO - Convert the comparator to a lambda and then to a method reference
        Comparator<Integer> comparator = new Comparator<Integer>()
        {
            @Override
            public int compare(Integer one, Integer two)
            {
                return one.compareTo(two);
            }
        };
        Assert.assertEquals(0, comparator.compare(1, 1));
        Assert.assertEquals(-1, comparator.compare(1, 2));
        Assert.assertEquals(1, comparator.compare(3, 2));

        List<Integer> integers = IntStream.rangeClosed(1, 5).boxed().collect(Collectors.toList());
        Collections.shuffle(integers);
        integers.sort(comparator);

        Assert.assertEquals(Arrays.asList(1, 2, 3, 4, 5), integers);
    }

    @Test
    public void runnable()
    {
        // Note: The following set reference is "effectively" final, which is a new feature in Java 8
        List<Integer> set = new ArrayList<>();
        // TODO - convert the anonymous Inner class to a lambda
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                set.add(1);
            }
        };
        runnable.run();
        Assert.assertEquals(Collections.singletonList(1), set);
        // TODO - convert the anonymous Inner class to a lambda
        Interval.fromTo(2, 10).run(new Runnable()
        {
            @Override
            public void run()
            {
                set.add(1);
            }
        });
        List<Integer> expectedSet = Collections.nCopies(10, 1);
        Assert.assertEquals(expectedSet, set);
    }

    @Test
    public void callable() throws Exception
    {
        // Note: The following set references is "effectively" final, which is a new feature in Java 8
        Set<Integer> set = new HashSet<>();
        // TODO - convert the anonymous inner class to lambda
        Callable<Boolean> callable = new Callable<Boolean>()
        {
            @Override
            public Boolean call() throws Exception
            {
                return set.add(1);
            }
        };
        Assert.assertTrue(callable.call());
        Assert.assertEquals(Collections.singleton(1), set);

        ExecutorService executor = Executors.newWorkStealingPool();
        List<Future<Boolean>> futures = executor.invokeAll(Collections.nCopies(5, callable));

        // Note: Functions.throwing() is a utility method in Eclipse Collections without which we would
        // have to wrap the call to Future.get() in a try-catch block.
        Assert.assertTrue(futures.stream().map(Functions.throwing(Future::get)).noneMatch(b -> b));
        Assert.assertEquals(Collections.singleton(1), set);
    }
}
