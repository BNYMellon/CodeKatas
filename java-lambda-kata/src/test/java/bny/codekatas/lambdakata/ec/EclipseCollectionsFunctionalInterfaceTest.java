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

package bny.codekatas.lambdakata.ec;

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
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.impl.list.Interval;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        var strings = Lists.mutable.with("one", "two", "three");

        // TODO - Can you remove the final keyword from the variable below?
        final var result = Lists.mutable.empty();

        // TODO - Convert the anonymous inner class to a lambda
        var procedure = new Procedure<String>()
        {
            @Override
            public void value(String each)
            {
                result.add(each.toUpperCase());
            }
        };
        procedure.accept("zero");
        Assertions.assertEquals(Lists.mutable.with("ZERO"), result);
        strings.each(procedure);
        Assertions.assertEquals(Lists.mutable.with("ZERO", "ONE", "TWO", "THREE"), result);
    }

    @Test
    public void predicateIsEven()
    {
        var numbers = Interval.oneTo(10).toList();

        // TODO - Convert the anonymous inner class to a lambda
        var evenPredicate = new Predicate<Integer>()
        {
            @Override
            public boolean accept(Integer integer)
            {
                return integer % 2 == 0;
            }
        };
        Assertions.assertTrue(evenPredicate.test(2));
        Assertions.assertFalse(evenPredicate.test(1));
        MutableList<Integer> evens = numbers.select(evenPredicate);
        Assertions.assertTrue(evens.allSatisfy(evenPredicate));
        Assertions.assertTrue(evens.stream().allMatch(evenPredicate));
        Assertions.assertFalse(evens.noneSatisfy(evenPredicate));
        Assertions.assertFalse(evens.stream().noneMatch(evenPredicate));
        Assertions.assertTrue(evens.anySatisfy(evenPredicate));
        Assertions.assertTrue(evens.stream().anyMatch(evenPredicate));
        Assertions.assertEquals(Interval.evensFromTo(1, 10), evens);
    }

    @Test
    public void predicateIsOdd()
    {
        var numbers = Interval.oneTo(10).toList();

        // TODO - Convert the anonymous inner class to a lambda
        var oddPredicate = new Predicate<Integer>()
        {
            @Override
            public boolean accept(Integer integer)
            {
                return integer % 2 == 1;
            }
        };
        Assertions.assertFalse(oddPredicate.test(2));
        Assertions.assertTrue(oddPredicate.test(1));
        MutableList<Integer> odds = numbers.select(oddPredicate);
        Assertions.assertTrue(odds.allSatisfy(oddPredicate));
        Assertions.assertTrue(odds.stream().allMatch(oddPredicate));
        Assertions.assertFalse(odds.noneSatisfy(oddPredicate));
        Assertions.assertFalse(odds.stream().noneMatch(oddPredicate));
        Assertions.assertTrue(odds.stream().anyMatch(oddPredicate));
        Assertions.assertTrue(odds.anySatisfy(oddPredicate));
        Assertions.assertEquals(Interval.oddsFromTo(1, 10), odds);
    }

    @Test
    public void function()
    {
        // TODO - Convert the anonymous inner class to a lambda and then a method reference
        var toUppercase = new Function<String, String>()
        {
            @Override
            public String valueOf(String string)
            {
                return string.toUpperCase();
            }
        };
        Assertions.assertEquals("UPPERCASE", toUppercase.apply("uppercase"));
        MutableList<String> lowercase = Lists.mutable.with("a", "b", "c", "d");
        MutableList<String> uppercase = lowercase.collect(toUppercase);
        Assertions.assertEquals(Arrays.asList("A", "B", "C", "D"), uppercase);
    }

    @Test
    public void function0()
    {
        // TODO - Convert this anonymous inner class to a lambda and then to a constructor reference
        var supplier = new Function0<List<String>>()
        {
            @Override
            public List<String> value()
            {
                return Lists.mutable.empty();
            }
        };
        Assertions.assertEquals(Lists.mutable.empty(), supplier.get());
        Assertions.assertNotSame(supplier.get(), supplier.get());
        List<String> list = Stream.of("1", "2", "3").collect(Collectors.toCollection(supplier));
        Assertions.assertEquals(Lists.mutable.with("1", "2", "3"), list);
    }

    @Test
    public void procedure2()
    {
        var result = Maps.mutable.empty();
        // TODO - Convert the anonymous inner class to a lambda
        var procedure2 = new Procedure2<String, String>()
        {
            @Override
            public void value(String key, String value)
            {
                result.put(key.toUpperCase(), value.toUpperCase());
            }
        };
        procedure2.accept("a", "one");
        Assertions.assertEquals(Maps.mutable.with("A", "ONE"), result);
        Maps.mutable.with("b", "two", "c", "three").forEachKeyValue(procedure2);
        Assertions.assertEquals(Maps.mutable.with("A", "ONE", "B", "TWO", "C", "THREE"), result);
    }
}
