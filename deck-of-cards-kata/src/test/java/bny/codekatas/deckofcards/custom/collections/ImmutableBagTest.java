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

package bny.codekatas.deckofcards.custom.collections;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ImmutableBagTest
{
    @Test
    public void filter()
    {
        ImmutableBag<Integer> bag = ImmutableBag.of(1, 2, 3, 4, 5);

        // filter method on MutableList
        ImmutableBag<Integer> eagerFilter =
                bag.filter(each -> each % 2 == 0);

        // filter method on java.util.stream.Stream
        Bag<Integer> lazyFilter = bag.stream()
                .filter(each -> each % 2 == 0)
                .collect(MutableBag.collector());

        var expected = MutableBag.of(2, 4);
        Assertions.assertEquals(expected, eagerFilter);
        Assertions.assertEquals(expected, lazyFilter);
    }

    @Test
    public void filterMapReduce()
    {
        ImmutableBag<Integer> bag = ImmutableBag.of(1, 2, 3, 4, 5);

        // calling filter, map, reduce on MutableList
        Optional<String> eager = bag
                .peek(i -> System.out.println("filter: " + i))
                .filter(each -> each % 2 == 0)
                .peek(i -> System.out.println("map: " + i))
                .map(String::valueOf)
                .peek(i -> System.out.println("reduce: " + i))
                .reduce(String::concat);

        // calling filter, map, reduce on java.util.stream.Stream
        Optional<String> lazy = bag.stream()
                .peek(i -> System.out.println("stream filter: " + i))
                .filter(each -> each % 2 == 0)
                .peek(i -> System.out.println("stream map: " + i))
                .map(String::valueOf)
                .peek(i -> System.out.println("stream reduce: " + i))
                .reduce(String::concat);

        var expected = "24";
        Assertions.assertEquals(expected, eager.orElse(""));
        Assertions.assertEquals(expected, lazy.orElse(""));
    }

    @Test
    public void filterMapAnyMatch()
    {
        ImmutableBag<Integer> bag = ImmutableBag.of(1, 2, 3, 4, 5);

        // calling filter, map, anyMatch on MutableList
        boolean eager = bag
                .peek(i -> System.out.println("filter: " + i))
                .filter(each -> each % 2 == 0)
                .peek(i -> System.out.println("map: " + i))
                .map(String::valueOf)
                .peek(i -> System.out.println("anyMatch: " + i))
                .anyMatch("2"::equals);

        // calling filter, map, anyMatch on java.util.stream.Stream
        boolean lazy = bag.stream()
                .peek(i -> System.out.println("stream filter: " + i))
                .filter(each -> each % 2 == 0)
                .peek(i -> System.out.println("stream map: " + i))
                .map(String::valueOf)
                .peek(i -> System.out.println("stream anyMatch: " + i))
                .anyMatch("2"::equals);

        Assertions.assertTrue(eager);
        Assertions.assertTrue(lazy);
    }

    @Test
    public void filterNot()
    {
        ImmutableBag<Integer> bag = ImmutableBag.of(1, 2, 3, 4, 5);

        // filterNot method on MutableList
        ImmutableBag<Integer> actual = bag.filterNot(each -> each % 2 == 0);

        // filter method on java.util.stream.Stream using negative predicate
        Bag<Integer> actualStream = bag.stream()
                .filter(each -> each % 2 != 0)
                .collect(MutableBag.collector());

        var expected = MutableBag.of(1, 3, 5);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, actualStream);
    }

    @Test
    public void map()
    {
        ImmutableBag<Integer> bag = ImmutableBag.of(1, 2, 3, 4, 5);

        // map method on MutableList
        ImmutableBag<String> actual = bag.map(String::valueOf);

        // map method on java.util.stream.Stream
        Bag<String> actualStream = bag.stream()
                .map(String::valueOf)
                .collect(MutableBag.collector());

        var expected = MutableBag.of("1", "2", "3", "4", "5");
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, actualStream);
    }

    @Test
    public void flatMap()
    {
        ImmutableBag<List<Integer>> bag = ImmutableBag.of(List.of(1), List.of(2));

        // flatMap method on MutableList
        ImmutableBag<Integer> actual = bag.flatMap(each -> each);

        // flatMap method on java.util.stream.Stream
        Bag<Integer> actualStream = bag.stream()
                .flatMap(List::stream)
                .collect(MutableBag.collector());

        var expected = MutableBag.of(1, 2);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, actualStream);
    }

    @Test
    public void reduce()
    {
        ImmutableBag<Integer> bag = ImmutableBag.of(1, 2, 3, 4, 5);

        // reduce method on MutableList
        Optional<Integer> reduce = bag.reduce(Integer::sum);

        // reduce method on java.util.stream.Stream
        Optional<Integer> reduceStream = bag.stream().reduce(Integer::sum);

        Assertions.assertEquals(Integer.valueOf(15), reduce.orElse(0));
        Assertions.assertEquals(Integer.valueOf(15), reduceStream.orElse(0));
        MutableList<Integer> empty = MutableList.empty();
        Assertions.assertTrue(empty.reduce(Integer::sum).isEmpty());
    }

    @Test
    public void anyMatch()
    {
        ImmutableBag<Integer> bag = ImmutableBag.of(1, 2, 3, 4, 5);

        // anyMatch method on MutableList
        Assertions.assertTrue(bag.anyMatch(each -> each % 2 == 0));
        Assertions.assertFalse(bag.anyMatch(each -> each < 0));

        // anyMatch method on java.util.stream.Stream
        Assertions.assertTrue(bag.stream().anyMatch(each -> each % 2 == 0));
        Assertions.assertFalse(bag.stream().anyMatch(each -> each < 0));
    }

    @Test
    public void allMatch()
    {
        ImmutableBag<Integer> bag = ImmutableBag.of(1, 2, 3, 4, 5);

        // allMatch method on MutableList
        Assertions.assertFalse(bag.allMatch(each -> each % 2 == 0));
        Assertions.assertTrue(bag.allMatch(each -> each > 0));

        // allMatch method on java.util.stream.Stream
        Assertions.assertFalse(bag.stream().allMatch(each -> each % 2 == 0));
        Assertions.assertTrue(bag.stream().allMatch(each -> each > 0));
    }

    @Test
    public void noneMatch()
    {
        ImmutableBag<Integer> bag = ImmutableBag.of(1, 2, 3, 4, 5);

        // noneMatch method on MutableList
        Assertions.assertFalse(bag.noneMatch(each -> each % 2 == 0));
        Assertions.assertTrue(bag.noneMatch(each -> each < 0));

        // noneMatch method on java.util.stream.Stream
        Assertions.assertFalse(bag.stream().noneMatch(each -> each % 2 == 0));
        Assertions.assertTrue(bag.stream().noneMatch(each -> each < 0));
    }

    @Test
    public void count()
    {
        ImmutableBag<Integer> bag = ImmutableBag.of(1, 2, 3, 4, 5);

        // count method on MutableList
        Assertions.assertEquals(2, bag.count(each -> each % 2 == 0));
        Assertions.assertEquals(3, bag.count(each -> each % 2 == 1));

        // filter + count method on java.util.stream.Stream
        Assertions.assertEquals(2L, bag.stream()
                .filter(each -> each % 2 == 0)
                .count());
        Assertions.assertEquals(3L, bag.stream()
                .filter(each -> each % 2 == 1)
                .count());
    }

    @Test
    public void findFirst()
    {
        ImmutableBag<Integer> bag = ImmutableBag.of(1, 2, 3, 4, 5);

        // findFirst method on MutableList
        Assertions.assertEquals(Integer.valueOf(2), bag.findFirst(each -> each % 2 == 0).orElse(0));
        Assertions.assertEquals(Integer.valueOf(0), bag.findFirst(each -> each < 0).orElse(0));

        // filter + findFirst method on java.util.stream.Stream
        Assertions.assertEquals(Integer.valueOf(2), bag.stream().filter(each -> each % 2 == 0).findFirst().orElse(0));
        Assertions.assertEquals(Integer.valueOf(0), bag.stream().filter(each -> each < 0).findFirst().orElse(0));
    }

    @Test
    public void countBy()
    {
        ImmutableBag<Integer> bag = ImmutableBag.of(1, 2, 3, 4, 5);

        // countBy method on MutableList
        ImmutableBag<Integer> counts = bag.countBy(each -> each % 2);

        // collect method on java.util.stream.Stream
        // + groupingBy & counting on java.util.stream.Collectors
        Map<Integer, Long> countsStream = bag.stream()
                .collect(Collectors.groupingBy(
                        each -> each % 2,
                        Collectors.counting()));

        Assertions.assertEquals(3, counts.getOccurrences(1));
        Assertions.assertEquals(2, counts.getOccurrences(0));
        Assertions.assertEquals(Long.valueOf(3L), countsStream.get(1));
        Assertions.assertEquals(Long.valueOf(2L), countsStream.get(0));
    }

    @Test
    public void collect()
    {
        ImmutableBag<Integer> bag = ImmutableBag.of(1, 2, 3, 4, 5);
        Collector<Integer, ?, Map<Integer, List<Integer>>> collector =
                Collectors.groupingBy(
                        each -> each % 2,
                        Collectors.toList());

        // collect method on MutableList
        var collectGroupingBy = bag.collect(collector);

        // collect method on java.util.stream.Stream
        var streamGroupingBy = bag.stream().collect(collector);

        Assertions.assertEquals(streamGroupingBy, collectGroupingBy);
    }

    @Test
    public void toList()
    {
        ImmutableBag<Integer> bag = ImmutableBag.of(1);

        // toList method on MutableList
        MutableList<Integer> actual = bag.toList();

        // collect method on java.util.stream.Stream + toList on java.util.stream.Collectors
        List<Integer> actualStream = bag.stream().collect(Collectors.toList());

        var expected = List.of(1);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, actualStream);
    }

    @Test
    public void toSet()
    {
        ImmutableBag<Integer> bag = ImmutableBag.of(1);

        // toSet method on MutableList
        MutableSet<Integer> actual = bag.toSet();

        // collect method on java.util.stream.Stream + toSet on java.util.stream.Collectors
        Set<Integer> actualStream = bag.stream().collect(Collectors.toSet());

        var expected = Set.of(1);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, actualStream);
    }

    @Test
    public void fromIterable()
    {
        Iterable<Integer> expected = ImmutableBag.of(1, 2, 3);

        // fromIterable method on MutableList
        ImmutableBag<Integer> actual = ImmutableBag.fromIterable(expected);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void fromStream()
    {
        // fromStream method on MutableList
        ImmutableBag<Integer> actual = ImmutableBag.fromStream(Stream.of(1, 2, 3));

        var expected = MutableBag.of(1, 2, 3);
        Assertions.assertEquals(expected, actual);
    }
}
