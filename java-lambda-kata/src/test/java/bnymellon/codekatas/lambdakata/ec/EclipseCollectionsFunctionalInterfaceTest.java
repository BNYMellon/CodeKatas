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

package bnymellon.codekatas.lambdakata.ec;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.block.procedure.Procedure2;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Maps;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
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
public class EclipseCollectionsFunctionalInterfaceTest
{
    @Test
    public void procedure()
    {
        MutableList<String> strings = Lists.mutable.with("one", "two", "three");

        // TODO - Can you remove the final keyword from the variable below?
        final MutableList<String> result = Lists.mutable.empty();

        // TODO - Convert the anonymous inner class to a lambda
        Procedure<String> procedure = new Procedure<String>()
        {
            @Override
            public void value(String each)
            {
                result.add(each.toUpperCase());
            }
        };
        procedure.accept("zero");
        Assert.assertEquals(Lists.mutable.with("ZERO"), result);
        strings.each(procedure);
        Assert.assertEquals(Lists.mutable.with("ZERO", "ONE", "TWO", "THREE"), result);
    }

    @Test
    public void predicateIsEven()
    {
        MutableList<Integer> numbers = Interval.oneTo(10).toList();

        // TODO - Convert the anonymous inner class to a lambda
        Predicate<Integer> evenPredicate = new Predicate<Integer>()
        {
            @Override
            public boolean accept(Integer integer)
            {
                return integer % 2 == 0;
            }
        };
        Assert.assertTrue(evenPredicate.test(2));
        Assert.assertFalse(evenPredicate.test(1));
        MutableList<Integer> evens = numbers.select(evenPredicate);
        Assert.assertTrue(evens.allSatisfy(evenPredicate));
        Assert.assertTrue(evens.stream().allMatch(evenPredicate));
        Assert.assertFalse(evens.noneSatisfy(evenPredicate));
        Assert.assertFalse(evens.stream().noneMatch(evenPredicate));
        Assert.assertTrue(evens.anySatisfy(evenPredicate));
        Assert.assertTrue(evens.stream().anyMatch(evenPredicate));
        Assert.assertEquals(Interval.evensFromTo(1, 10), evens);
    }

    @Test
    public void predicateIsOdd()
    {
        MutableList<Integer> numbers = Interval.oneTo(10).toList();

        // TODO - Convert the anonymous inner class to a lambda
        Predicate<Integer> oddPredicate = new Predicate<Integer>()
        {
            @Override
            public boolean accept(Integer integer)
            {
                return integer % 2 == 1;
            }
        };
        Assert.assertFalse(oddPredicate.test(2));
        Assert.assertTrue(oddPredicate.test(1));
        MutableList<Integer> odds = numbers.select(oddPredicate);
        Assert.assertTrue(odds.allSatisfy(oddPredicate));
        Assert.assertTrue(odds.stream().allMatch(oddPredicate));
        Assert.assertFalse(odds.noneSatisfy(oddPredicate));
        Assert.assertFalse(odds.stream().noneMatch(oddPredicate));
        Assert.assertTrue(odds.stream().anyMatch(oddPredicate));
        Assert.assertTrue(odds.anySatisfy(oddPredicate));
        Assert.assertEquals(Interval.oddsFromTo(1, 10), odds);
    }

    @Test
    public void function()
    {
        // TODO - Convert the anonymous inner class to a lambda and then a method reference
        Function<String, String> toUppercase = new Function<String, String>()
        {
            @Override
            public String valueOf(String string)
            {
                return string.toUpperCase();
            }
        };
        Assert.assertEquals("UPPERCASE", toUppercase.apply("uppercase"));
        MutableList<String> lowercase = Lists.mutable.with("a", "b", "c", "d");
        MutableList<String> uppercase = lowercase.collect(toUppercase);
        Assert.assertEquals(Arrays.asList("A", "B", "C", "D"), uppercase);
    }

    @Test
    public void function0()
    {
        // TODO - Convert this anonymous inner class to a lambda and then to a constructor reference
        Function0<List<String>> supplier = new Function0<List<String>>()
        {
            @Override
            public List<String> value()
            {
                return FastList.newList();
            }
        };
        Assert.assertEquals(Lists.mutable.empty(), supplier.get());
        Assert.assertNotSame(supplier.get(), supplier.get());
        List<String> list = Stream.of("1", "2", "3").collect(Collectors.toCollection(supplier));
        Assert.assertEquals(Lists.mutable.with("1", "2", "3"), list);
    }

    @Test
    public void procedure2()
    {
        MutableMap<String, String> result = Maps.mutable.empty();
        // TODO - Convert the anonymous inner class to a lambda
        Procedure2<String, String> procedure2 = new Procedure2<String, String>()
        {
            @Override
            public void value(String key, String value)
            {
                result.put(key.toUpperCase(), value.toUpperCase());
            }
        };
        procedure2.accept("a", "one");
        Assert.assertEquals(Maps.mutable.with("A", "ONE"), result);
        Maps.mutable.with("b", "two", "c", "three").forEachKeyValue(procedure2);
        Assert.assertEquals(Maps.mutable.with("A", "ONE", "B", "TWO", "C", "THREE"), result);
    }
}
