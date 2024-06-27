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
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ImmutableSetTest
{
    @Test
    public void filter()
    {
        ImmutableSet<Integer> set = ImmutableSet.of(1, 2, 3, 4, 5);

        // filter method on MutableSet
        ImmutableSet<Integer> actual = set.filter(each -> each % 2 == 0);

        // filter method on java.util.stream.Stream
        Set<Integer> actualStream = set.stream()
                .filter(each -> each % 2 == 0)
                .collect(Collectors.toCollection(MutableSet::empty));

        var expected = MutableSet.of(2, 4);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, actualStream);
    }

    @Test
    public void filterNot()
    {
        ImmutableSet<Integer> set = ImmutableSet.of(1, 2, 3, 4, 5);

        // filterNot method on MutableSet
        ImmutableSet<Integer> actual = set.filterNot(each -> each % 2 == 0);

        // filter method on java.util.stream.Stream using negative predicate
        Set<Integer> actualStream = set.stream()
                .filter(each -> each % 2 != 0)
                .collect(Collectors.toCollection(MutableSet::empty));

        var expected = MutableSet.of(1, 3, 5);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, actualStream);
    }

    @Test
    public void map()
    {
        ImmutableSet<Integer> set = ImmutableSet.of(1, 2, 3, 4, 5);

        // map method on MutableSet
        ImmutableSet<String> actual = set.map(String::valueOf);

        // map method on java.util.stream.Stream
        Set<String> actualStream = set.stream()
                .map(String::valueOf)
                .collect(Collectors.toCollection(MutableSet::empty));

        var expected = MutableSet.of("1", "2", "3", "4", "5");
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, actualStream);
    }

    @Test
    public void flatMap()
    {
        ImmutableSet<List<Integer>> set = ImmutableSet.of(List.of(1), List.of(2));

        // flatMap method on MutableSet
        ImmutableSet<Integer> actual = set.flatMap(each -> each);

        // flatMap method on java.util.stream.Stream
        Set<Integer> actualStream = set.stream()
                .flatMap(List::stream)
                .collect(Collectors.toCollection(MutableSet::empty));

        var expected = MutableSet.of(1, 2);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, actualStream);
    }

    @Test
    public void reduce()
    {
        ImmutableSet<Integer> set = ImmutableSet.of(1, 2, 3, 4, 5);

        // reduce method on MutableSet
        Optional<Integer> reduce = set.reduce(Integer::sum);

        // reduce method on java.util.stream.Stream
        Optional<Integer> reduceStream = set.stream().reduce(Integer::sum);

        Assertions.assertEquals(Integer.valueOf(15), reduce.orElse(0));
        Assertions.assertEquals(Integer.valueOf(15), reduceStream.orElse(0));
        ImmutableSet<Integer> empty = ImmutableSet.empty();
        Assertions.assertTrue(empty.reduce(Integer::sum).isEmpty());
    }

    @Test
    public void anyMatch()
    {
        ImmutableSet<Integer> set = ImmutableSet.of(1, 2, 3, 4, 5);

        // anyMatch method on MutableSet
        Assertions.assertTrue(set.anyMatch(each -> each % 2 == 0));
        Assertions.assertFalse(set.anyMatch(each -> each < 0));

        // anyMatch method on java.util.stream.Stream
        Assertions.assertTrue(set.stream().anyMatch(each -> each % 2 == 0));
        Assertions.assertFalse(set.stream().anyMatch(each -> each < 0));
    }

    @Test
    public void allMatch()
    {
        ImmutableSet<Integer> set = ImmutableSet.of(1, 2, 3, 4, 5);

        // allMatch method on MutableSet
        Assertions.assertFalse(set.allMatch(each -> each % 2 == 0));
        Assertions.assertTrue(set.allMatch(each -> each > 0));

        // allMatch method on java.util.stream.Stream
        Assertions.assertFalse(set.stream().allMatch(each -> each % 2 == 0));
        Assertions.assertTrue(set.stream().allMatch(each -> each > 0));
    }

    @Test
    public void noneMatch()
    {
        ImmutableSet<Integer> set = ImmutableSet.of(1, 2, 3, 4, 5);

        // noneMatch method on MutableSet
        Assertions.assertFalse(set.noneMatch(each -> each % 2 == 0));
        Assertions.assertTrue(set.noneMatch(each -> each < 0));

        // noneMatch method on java.util.stream.Stream
        Assertions.assertFalse(set.stream().noneMatch(each -> each % 2 == 0));
        Assertions.assertTrue(set.stream().noneMatch(each -> each < 0));
    }

    @Test
    public void count()
    {
        ImmutableSet<Integer> set = ImmutableSet.of(1, 2, 3, 4, 5);

        // count method on MutableSet
        Assertions.assertEquals(2, set.count(each -> each % 2 == 0));
        Assertions.assertEquals(3, set.count(each -> each % 2 == 1));

        // filter + count method on java.util.stream.Stream
        Assertions.assertEquals(2L, set.stream()
                .filter(each -> each % 2 == 0)
                .count());
        Assertions.assertEquals(3L, set.stream()
                .filter(each -> each % 2 == 1)
                .count());
    }

    @Test
    public void countBy()
    {
        ImmutableSet<Integer> set = ImmutableSet.of(1, 2, 3, 4, 5);

        // countBy method on MutableSet
        ImmutableBag<Integer> counts = set.countBy(each -> each % 2);

        // collect method on java.util.stream.Stream
        // + groupingBy & counting on java.util.stream.Collectors
        Map<Integer, Long> countsStream = set.stream()
                .collect(Collectors.groupingBy(
                        each -> each % 2,
                        Collectors.counting()));

        var expected = MutableBag.of(1, 1, 1, 0, 0);
        Assertions.assertEquals(expected, counts);
        Map<Integer, Long> expectedStream = Map.of(1, 3L, 0, 2L);
        Assertions.assertEquals(expectedStream, countsStream);
    }

    @Test
    public void groupBy()
    {
        ImmutableSet<Integer> set = ImmutableSet.of(1, 2, 3, 4, 5);

        // groupBy method on MutableSet returning Multimap
        MutableSetMultimap<Integer, Integer> grouped = set.groupBy(each -> each % 2);

        // collect method on java.util.stream.Stream + groupingBy on java.util.stream.Collectors
        Map<Integer, MutableSet<Integer>> groupedStream = set.stream()
                .collect(Collectors.groupingBy(
                        each -> each % 2,
                        MutableSet.collector()));

        var expected = MutableSetMultimap.<Integer, Integer>empty();
        var oddSet = MutableSet.of(1, 3, 5);
        var evenSet = MutableSet.of(2, 4);
        expected.put(1, oddSet);
        expected.put(0, evenSet);
        Assertions.assertEquals(expected, grouped);
        Assertions.assertEquals(Map.of(1, oddSet, 0, evenSet), groupedStream);
    }

    @Test
    public void collect()
    {
        ImmutableSet<Integer> set = ImmutableSet.of(1, 2, 3, 4, 5);
        Collector<Integer, ?, Map<Integer, MutableSet<Integer>>> collector = Collectors.groupingBy(
                each -> each % 2,
                MutableSet.collector());

        // collect method on MutableSet
        var collectGroupingBy = set.collect(collector);

        // collect method on java.util.stream.Stream
        var streamGroupingBy = set.stream().collect(collector);

        Assertions.assertEquals(streamGroupingBy, collectGroupingBy);
    }

    @Test
    public void fromIterable()
    {
        Iterable<Integer> expected = MutableSet.of(1, 2, 3);

        // fromIterable method on MutableSet
        ImmutableSet<Integer> actual = ImmutableSet.fromIterable(expected);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void fromStream()
    {
        var expected = MutableSet.of(1, 2, 3);
        MutableSet<Integer> actual = MutableSet.fromStream(Stream.of(1, 2, 3));
        Set<Integer> actualStream = Stream.of(1, 2, 3)
                .collect(MutableSet.collector());
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, actualStream);
    }
}
