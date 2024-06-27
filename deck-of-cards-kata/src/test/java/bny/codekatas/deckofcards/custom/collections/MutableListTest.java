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
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.api.tuple.Twin;
import org.eclipse.collections.impl.test.ClassComparer;
import org.eclipse.collections.impl.tuple.Tuples;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MutableListTest
{

    @Test
    public void symmetricDiffAndIntersectionTest()
    {
        ClassComparer comparer = new ClassComparer(true, false, false);

        Twin<MutableSortedSet<String>> results =
                Tuples.twin(
                        comparer.symmetricDifference(MutableList.class, Stream.class),
                        comparer.intersect(MutableList.class, Stream.class));

        Assertions.assertTrue(results.getOne().size() > results.getTwo().size());
    }

    @Test
    public void filter()
    {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);

        // filter method on MutableList
        MutableList<Integer> eagerFilter =
                list.filter(each -> each % 2 == 0);

        // filter method on java.util.stream.Stream
        List<Integer> lazyFilter = list.stream()
                .filter(each -> each % 2 == 0)
                .collect(Collectors.toList());

        var expected = List.of(2, 4);
        Assertions.assertEquals(expected, eagerFilter);
        Assertions.assertEquals(expected, lazyFilter);
    }

    @Test
    public void filterMapReduce()
    {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);

        // calling filter, map, reduce on MutableList
        Optional<String> eager = list
                .peek(i -> System.out.println("filter: " + i))
                .filter(each -> each % 2 == 0)
                .peek(i -> System.out.println("map: " + i))
                .map(String::valueOf)
                .peek(i -> System.out.println("reduce: " + i))
                .reduce(String::concat);

        // calling filter, map, reduce on java.util.stream.Stream
        Optional<String> lazy = list.stream()
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
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);

        // calling filter, map, anyMatch on MutableList
        boolean eager = list
                .peek(i -> System.out.println("filter: " + i))
                .filter(each -> each % 2 == 0)
                .peek(i -> System.out.println("map: " + i))
                .map(String::valueOf)
                .peek(i -> System.out.println("anyMatch: " + i))
                .anyMatch("2"::equals);

        // calling filter, map, anyMatch on java.util.stream.Stream
        boolean lazy = list.stream()
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
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);

        // filterNot method on MutableList
        MutableList<Integer> actual = list.filterNot(each -> each % 2 == 0);

        // filter method on java.util.stream.Stream using negative predicate
        List<Integer> actualStream = list.stream()
                .filter(each -> each % 2 != 0)
                .collect(Collectors.toList());

        var expected = MutableList.of(1, 3, 5);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, actualStream);
    }

    @Test
    public void map()
    {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);

        // map method on MutableList
        MutableList<String> actual = list.map(String::valueOf);

        // map method on java.util.stream.Stream
        List<String> actualStream = list.stream()
                .map(String::valueOf)
                .collect(Collectors.toList());

        var expected = List.of("1", "2", "3", "4", "5");
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, actualStream);
    }

    @Test
    public void flatMap()
    {
        MutableList<List<Integer>> list = MutableList.of(List.of(1), List.of(2));

        // flatMap method on MutableList
        MutableList<Integer> actual = list.flatMap(each -> each);

        // flatMap method on java.util.stream.Stream
        List<Integer> actualStream = list.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        var expected = List.of(1, 2);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, actualStream);
    }

    @Test
    public void reduce()
    {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);

        // reduce method on MutableList
        Optional<Integer> reduce = list.reduce(Integer::sum);

        // reduce method on java.util.stream.Stream
        Optional<Integer> reduceStream = list.stream().reduce(Integer::sum);

        Assertions.assertEquals(Integer.valueOf(15), reduce.orElse(0));
        Assertions.assertEquals(Integer.valueOf(15), reduceStream.orElse(0));
        MutableList<Integer> empty = MutableList.empty();
        Assertions.assertTrue(empty.reduce(Integer::sum).isEmpty());
    }

    @Test
    public void anyMatch()
    {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);

        // anyMatch method on MutableList
        Assertions.assertTrue(list.anyMatch(each -> each % 2 == 0));
        Assertions.assertFalse(list.anyMatch(each -> each < 0));

        // anyMatch method on java.util.stream.Stream
        Assertions.assertTrue(list.stream().anyMatch(each -> each % 2 == 0));
        Assertions.assertFalse(list.stream().anyMatch(each -> each < 0));
    }

    @Test
    public void allMatch()
    {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);

        // allMatch method on MutableList
        Assertions.assertFalse(list.allMatch(each -> each % 2 == 0));
        Assertions.assertTrue(list.allMatch(each -> each > 0));

        // allMatch method on java.util.stream.Stream
        Assertions.assertFalse(list.stream().allMatch(each -> each % 2 == 0));
        Assertions.assertTrue(list.stream().allMatch(each -> each > 0));
    }

    @Test
    public void noneMatch()
    {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);

        // noneMatch method on MutableList
        Assertions.assertFalse(list.noneMatch(each -> each % 2 == 0));
        Assertions.assertTrue(list.noneMatch(each -> each < 0));

        // noneMatch method on java.util.stream.Stream
        Assertions.assertFalse(list.stream().noneMatch(each -> each % 2 == 0));
        Assertions.assertTrue(list.stream().noneMatch(each -> each < 0));
    }

    @Test
    public void count()
    {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);

        // count method on MutableList
        Assertions.assertEquals(2, list.count(each -> each % 2 == 0));
        Assertions.assertEquals(3, list.count(each -> each % 2 == 1));

        // filter + count method on java.util.stream.Stream
        Assertions.assertEquals(2L, list.stream()
                .filter(each -> each % 2 == 0)
                .count());
        Assertions.assertEquals(3L, list.stream()
                .filter(each -> each % 2 == 1)
                .count());
    }

    @Test
    public void findFirst()
    {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);

        // findFirst method on MutableList
        Assertions.assertEquals(Integer.valueOf(2), list.findFirst(each -> each % 2 == 0).orElse(0));
        Assertions.assertEquals(Integer.valueOf(0), list.findFirst(each -> each < 0).orElse(0));

        // filter + findFirst method on java.util.stream.Stream
        Assertions.assertEquals(Integer.valueOf(2), list.stream().filter(each -> each % 2 == 0).findFirst().orElse(0));
        Assertions.assertEquals(Integer.valueOf(0), list.stream().filter(each -> each < 0).findFirst().orElse(0));
    }

    @Test
    public void asUnmodifiable()
    {
        Assertions.assertThrows(UnsupportedOperationException.class, () ->
        {
            MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
            List<Integer> unmodifiable = list.asUnmodifiable();
            unmodifiable.add(6);
        });
    }

    @Test
    public void countBy()
    {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);

        // countBy method on MutableList
        MutableBag<Integer> counts = list.countBy(each -> each % 2);

        // collect method on java.util.stream.Stream
        // + groupingBy & counting on java.util.stream.Collectors
        Map<Integer, Long> countsStream = list.stream()
                .collect(Collectors.groupingBy(
                        each -> each % 2,
                        Collectors.counting()));

        Assertions.assertEquals(3, counts.getOccurrences(1));
        Assertions.assertEquals(2, counts.getOccurrences(0));
        Assertions.assertEquals(Long.valueOf(3L), countsStream.get(1));
        Assertions.assertEquals(Long.valueOf(2L), countsStream.get(0));
    }

    @Test
    public void groupBy()
    {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);

        // groupBy method on MutableList returning Multimap
        MutableListMultimap<Integer, Integer> grouped = list.groupBy(each -> each % 2);

        // collect method on java.util.stream.Stream + groupingBy on java.util.stream.Collectors
        Map<Integer, List<Integer>> groupedStream = list.stream()
                .collect(Collectors.groupingBy(each -> each % 2));

        var expected = ArrayListMultimap.<Integer, Integer>newMultimap();
        var oneList = MutableList.of(1, 3, 5);
        var zeroList = MutableList.of(2, 4);
        expected.put(1, oneList);
        expected.put(0, zeroList);
        Assertions.assertEquals(expected, grouped);
        Assertions.assertEquals(Map.of(1, oneList, 0, zeroList), groupedStream);
    }

    @Test
    public void collect()
    {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        Collector<Integer, ?, Map<Integer, List<Integer>>> collector =
                Collectors.groupingBy(
                        each -> each % 2,
                        Collectors.toList());

        // collect method on MutableList
        var collectGroupingBy = list.collect(collector);

        // collect method on java.util.stream.Stream
        var streamGroupingBy = list.stream().collect(collector);

        Assertions.assertEquals(streamGroupingBy, collectGroupingBy);
    }

    @Test
    public void toList()
    {
        MutableSet<Integer> set = MutableSet.of(1);

        // toList method on MutableList
        MutableList<Integer> actual = set.toList();

        // collect method on java.util.stream.Stream + toList on java.util.stream.Collectors
        List<Integer> actualStream = set.stream().collect(Collectors.toList());

        var expected = List.of(1);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, actualStream);
    }

    @Test
    public void toSet()
    {
        MutableList<Integer> list = MutableList.of(1);

        // toSet method on MutableList
        MutableSet<Integer> actual = list.toSet();

        // collect method on java.util.stream.Stream + toSet on java.util.stream.Collectors
        Set<Integer> actualStream = list.stream().collect(Collectors.toSet());

        var expected = Set.of(1);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, actualStream);
    }

    @Test
    public void fromIterable()
    {
        Iterable<Integer> expected = List.of(1, 2, 3);

        // fromIterable method on MutableList
        MutableList<Integer> actual = MutableList.fromIterable(expected);

        // stream from java.util.stream.StreamSupport
        // + collect method on java.util.stream.Stream + toList on java.util.stream.Collectors
        List<Integer> actualStream = StreamSupport.stream(expected.spliterator(), false)
                .collect(Collectors.toList());

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, actualStream);
    }

    @Test
    public void fromStream()
    {
        // fromStream method on MutableList
        MutableList<Integer> actual = MutableList.fromStream(Stream.of(1, 2, 3));

        // collect method on java.util.stream.Stream + toList on java.util.stream.Collectors
        List<Integer> actualStream = Stream.of(1, 2, 3).collect(Collectors.toList());

        var expected = List.of(1, 2, 3);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, actualStream);
    }

    @Test
    public void addAllIterable()
    {
        MutableList<Integer> list1 = MutableList.empty();
        var expected = List.of(1, 2, 3);
        list1.addAllIterable(expected::iterator);
        Assertions.assertEquals(expected, list1);
        MutableList<Integer> list2 = MutableList.empty();
        list2.addAllIterable(expected::iterator);
        Assertions.assertEquals(expected, list2);
    }

    @Test
    public void countByEach()
    {
        MutableList<MutableList<Integer>> list = MutableList.of(MutableList.of(1, 2), MutableList.of(1, 2, 3));
        MutableBag<Integer> counts = list.countByEach(each -> each);
        Assertions.assertEquals(2, counts.getOccurrences(1));
        Assertions.assertEquals(2, counts.getOccurrences(2));
        Assertions.assertEquals(1, counts.getOccurrences(3));
    }

    @Test
    public void groupByEach()
    {
        MutableList<Integer> list = MutableList.of(1, 2, 3);
        MutableListMultimap<Integer, Integer> multimap = list.groupByEach(each -> IntStream.range(0, each).boxed().toList());
        Assertions.assertEquals(List.of(1, 2, 3), multimap.get(0));
        Assertions.assertEquals(List.of(2, 3), multimap.get(1));
        Assertions.assertEquals(List.of(3), multimap.get(2));
    }

    @Test
    public void toBag()
    {
        MutableList<Integer> list = MutableList.of(1, 2, 2, 3, 3, 3);
        MutableBag<Integer> bag = list.toBag();

        Assertions.assertEquals(MutableBag.of(1, 2, 2, 3, 3, 3), bag);
    }

    @Test
    public void containsBy()
    {
        MutableList<Integer> hasEvens = MutableList.of(1, 2, 3);
        MutableList<Integer> onlyOdds = MutableList.of(1, 3);

        Assertions.assertTrue(hasEvens.containsBy(each -> each % 2, 0));
        Assertions.assertFalse(onlyOdds.containsBy(each -> each % 2, 0));
    }
}
