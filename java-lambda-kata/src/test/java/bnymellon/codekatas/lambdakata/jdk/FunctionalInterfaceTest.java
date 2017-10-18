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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.eclipse.collections.impl.list.Interval;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test will illustrate how you can use lambdas with Functional Interface types introduced in Java 8
 * like Consumer, Function and Predicate.
 *
 * Please refer to this tutorial for an overview of Lambdas for Java 8.
 * @see <a href="http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/index.html">Lambda Quickstart</a>
 *
 * Then follow the TODOs in each test and convert the anonymous inner classes to lambdas and/or method references.
 */
public class FunctionalInterfaceTest
{
    @Test
    public void consumer()
    {
        List<String> strings = Arrays.asList("one", "two", "three");

        // TODO - Can you remove the final keyword from the variable below?
        List<String> result = new ArrayList<>();

        // TODO - Convert the anonymous inner class to a lambda
        Consumer<String> consumer = new Consumer<String>()
        {
            @Override
            public void accept(String each)
            {
                result.add(each.toUpperCase());
            }
        };
        consumer.accept("zero");
        Assert.assertEquals(Arrays.asList("ZERO"), result);
        strings.forEach(consumer);
        Assert.assertEquals(Arrays.asList("ZERO", "ONE", "TWO", "THREE"), result);
    }

    @Test
    public void predicateIsEven()
    {
        List<Integer> numbers = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());

        // TODO - Convert the anonymous inner class to a lambda
        Predicate<Integer> evenPredicate = new Predicate<Integer>()
        {
            @Override
            public boolean test(Integer integer)
            {
                return integer % 2 == 0;
            }
        };
        Assert.assertTrue(evenPredicate.test(2));
        Assert.assertFalse(evenPredicate.test(1));
        List<Integer> evens = numbers.stream().filter(evenPredicate).collect(Collectors.toList());
        Assert.assertTrue(evens.stream().allMatch(evenPredicate));
        Assert.assertFalse(evens.stream().noneMatch(evenPredicate));
        Assert.assertTrue(evens.stream().anyMatch(evenPredicate));
        Assert.assertEquals(Interval.evensFromTo(1, 10), evens);
    }

    @Test
    public void predicateIsOdd()
    {
        List<Integer> numbers = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());

        // TODO - Convert the anonymous inner class to a lambda
        Predicate<Integer> oddPredicate = new Predicate<Integer>()
        {
            @Override
            public boolean test(Integer integer)
            {
                return integer % 2 == 1;
            }
        };
        Assert.assertFalse(oddPredicate.test(2));
        Assert.assertTrue(oddPredicate.test(1));
        List<Integer> odds = numbers.stream().filter(oddPredicate).collect(Collectors.toList());
        Assert.assertTrue(odds.stream().allMatch(oddPredicate));
        Assert.assertFalse(odds.stream().noneMatch(oddPredicate));
        Assert.assertTrue(odds.stream().anyMatch(oddPredicate));
        Assert.assertEquals(Interval.oddsFromTo(1, 10), odds);
    }

    @Test
    public void function()
    {
        // TODO - Convert the anonymous inner class to a lambda and then a method reference
        Function<String, String> toUppercase = new Function<String, String>()
        {
            @Override
            public String apply(String s)
            {
                return s.toUpperCase();
            }
        };
        Assert.assertEquals("UPPERCASE", toUppercase.apply("uppercase"));
        List<String> lowercase = Arrays.asList("a", "b", "c", "d");
        List<String> uppercase = lowercase.stream().map(toUppercase).collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("A", "B", "C", "D"), uppercase);
    }

    @Test
    public void supplier()
    {
        // TODO - Convert this anonymous inner class to a lambda and then to a constructor reference
        Supplier<List<String>> supplier = new Supplier<List<String>>()
        {
            @Override
            public List<String> get()
            {
                return new CopyOnWriteArrayList<String>();
            }
        };
        Assert.assertEquals(new CopyOnWriteArrayList<>(), supplier.get());
        Assert.assertNotSame(supplier.get(), supplier.get());
        List<String> list = Stream.of("1", "2", "3").collect(Collectors.toCollection(supplier));
        Assert.assertEquals(Arrays.asList("1", "2", "3"), list);
    }

    @Test
    public void biConsumer()
    {
        Map<String, String> result = new HashMap<>();
        // TODO - Convert the anonymous inner class to a lambda
        BiConsumer<String, String> biConsumer = new BiConsumer<String, String>()
        {
            @Override
            public void accept(String key, String value)
            {
                result.put(key.toUpperCase(), value.toUpperCase());
            }
        };
        biConsumer.accept("a", "one");
        Assert.assertEquals(this.createSmallMap(new String[]{"A", "ONE"}), result);
        this.createSmallMap(new String[]{"a", "one"}, new String[]{"b", "two"}, new String[]{"c", "three"})
                .forEach(biConsumer);
        Assert.assertEquals(
                this.createSmallMap(new String[]{"A", "ONE"}, new String[]{"B", "TWO"}, new String[]{"C", "THREE"}),
                result);
    }

    private Map<String, String> createSmallMap(String[]... keyValues)
    {
        return Stream.of(keyValues).collect(Collectors.toMap(a -> a[0], a -> a[1]));
    }

    @Test
    public void unaryOperator()
    {
        // TODO - Convert the anonymous inner class to a lambda
        UnaryOperator<Integer> squared = integer -> integer * integer;
        Assert.assertEquals(Integer.valueOf(4), squared.apply(2));
        Assert.assertEquals(Integer.valueOf(9), squared.apply(3));
        Assert.assertEquals(Integer.valueOf(16), squared.apply(4));
        Assert.assertTrue(Stream.iterate(2, squared).anyMatch(new Predicate<Integer>()
        {
            @Override
            public boolean test(Integer i)
            {
                return i.equals(Integer.valueOf(256));
            }
        }));
    }
}
