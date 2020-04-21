/*
 * Copyright 2020 BNY Mellon.
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

package bnymellon.codekatas.deckofcards.custom.collections;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MutableListTest {

    @Test
    public void filter() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        MutableList<Integer> actual = list.filter(each -> each % 2 == 0);
        List<Integer> actualStream = list.stream().filter(each -> each % 2 == 0).collect(Collectors.toList());
        var expected = MutableList.of(2, 4);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, actualStream);
    }

    @Test
    public void filterNot() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        MutableList<Integer> actual = list.filterNot(each -> each % 2 == 0);
        List<Integer> actualStream = list.stream().filter(each -> each % 2 != 0).collect(Collectors.toList());
        var expected = MutableList.of(1, 3, 5);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, actualStream);
    }

    @Test
    public void map() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        MutableList<String> actual = list.map(String::valueOf);
        List<String> actualStream = list.stream().map(String::valueOf).collect(Collectors.toList());
        var expected = List.of("1", "2", "3", "4", "5");
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, actualStream);
    }

    @Test
    public void flatMap() {
        MutableList<List<Integer>> list = MutableList.of(List.of(1), List.of(2));
        MutableList<Integer> actual = list.flatMap(each -> each);
        List<Integer> actualStream = list.stream().flatMap(List::stream).collect(Collectors.toList());
        var expected = List.of(1, 2);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, actualStream);
    }

    @Test
    public void reduce() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        Optional<Integer> reduce = list.reduce(Integer::sum);
        Optional<Integer> reduceStream = list.stream().reduce(Integer::sum);
        Assert.assertEquals(Integer.valueOf(15), reduce.orElse(0));
        Assert.assertEquals(Integer.valueOf(15), reduceStream.orElse(0));
        MutableList<Integer> empty = MutableList.empty();
        Assert.assertTrue(empty.reduce(Integer::sum).isEmpty());
    }

    @Test
    public void anyMatch() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        Assert.assertTrue(list.anyMatch(each -> each % 2 == 0));
        Assert.assertTrue(list.stream().anyMatch(each -> each % 2 == 0));
        Assert.assertFalse(list.anyMatch(each -> each < 0));
        Assert.assertFalse(list.stream().anyMatch(each -> each < 0));
    }

    @Test
    public void allMatch() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        Assert.assertFalse(list.allMatch(each -> each % 2 == 0));
        Assert.assertFalse(list.stream().allMatch(each -> each % 2 == 0));
        Assert.assertTrue(list.allMatch(each -> each > 0));
        Assert.assertTrue(list.stream().allMatch(each -> each > 0));
    }

    @Test
    public void noneMatch() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        Assert.assertFalse(list.noneMatch(each -> each % 2 == 0));
        Assert.assertFalse(list.stream().noneMatch(each -> each % 2 == 0));
        Assert.assertTrue(list.noneMatch(each -> each < 0));
        Assert.assertTrue(list.stream().noneMatch(each -> each < 0));
    }

    @Test
    public void count() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        Assert.assertEquals(2, list.count(each -> each % 2 == 0));
        Assert.assertEquals(2L, list.stream().filter(each -> each % 2 == 0).count());
        Assert.assertEquals(3, list.count(each -> each % 2 == 1));
        Assert.assertEquals(3L, list.stream().filter(each -> each % 2 == 1).count());
    }

    @Test
    public void findFirst() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        Assert.assertEquals(Integer.valueOf(2), list.findFirst(each -> each % 2 == 0).orElse(0));
        Assert.assertEquals(Integer.valueOf(2), list.stream().filter(each -> each % 2 == 0).findFirst().orElse(0));
        Assert.assertEquals(Integer.valueOf(0), list.findFirst(each -> each < 0).orElse(0));
        Assert.assertEquals(Integer.valueOf(0), list.stream().filter(each -> each < 0).findFirst().orElse(0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void asUnmodifiable() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        List<Integer> unmodifiable = list.asUnmodifiable();
        unmodifiable.add(6);
    }

    @Test
    public void countBy() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        MutableBag<Integer> counts = list.countBy(each -> each % 2);
        Map<Integer, Long> countsStream = list.stream().collect(Collectors.groupingBy(each -> each % 2, Collectors.counting()));
        Assert.assertEquals(3, counts.getOccurrences(1));
        Assert.assertEquals(2, counts.getOccurrences(0));
        Assert.assertEquals(Long.valueOf(3L), countsStream.get(1));
        Assert.assertEquals(Long.valueOf(2L), countsStream.get(0));
    }

    @Test
    public void groupBy() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        MutableListMultimap<Integer, Integer> grouped = list.groupBy(each -> each % 2);
        Map<Integer, List<Integer>> groupedStream = list.stream().collect(Collectors.groupingBy(each -> each % 2));
        var expected = ArrayListMultimap.<Integer, Integer>newMultimap();
        var oneList = MutableList.of(1, 3, 5);
        var zeroList = MutableList.of(2, 4);
        expected.put(1, oneList);
        expected.put(0, zeroList);
        Assert.assertEquals(expected, grouped);
        Assert.assertEquals(Map.of(1, oneList, 0, zeroList), groupedStream);
    }

    @Test
    public void collect() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        Collector<Integer, ?, Map<Integer, List<Integer>>> collector =
                Collectors.groupingBy(
                        each -> each % 2,
                        Collectors.toList());
        var streamGroupingBy = list.stream().collect(collector);
        var collectGroupingBy = list.collect(collector);
        Assert.assertEquals(streamGroupingBy, collectGroupingBy);
    }

    @Test
    public void toList() {
        MutableSet<Integer> set = MutableSet.of(1);
        MutableList<Integer> actual = set.toList();
        List<Integer> actualStream = set.stream().collect(Collectors.toList());
        var expected = List.of(1);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, actualStream);
    }

    @Test
    public void toSet() {
        MutableList<Integer> list = MutableList.of(1);
        MutableSet<Integer> actual = list.toSet();
        Set<Integer> actualStream = list.stream().collect(Collectors.toSet());
        var expected = Set.of(1);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, actualStream);
    }

    @Test
    public void fromIterable() {
        Iterable<Integer> expected = List.of(1, 2, 3);
        MutableList<Integer> actual = MutableList.fromIterable(expected);
        List<Integer> actualStream = StreamSupport.stream(expected.spliterator(), false).collect(Collectors.toList());
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, actualStream);
    }

    @Test
    public void fromStream() {
        MutableList<Integer> actual = MutableList.fromStream(Stream.of(1, 2, 3));
        List<Integer> actualStream = Stream.of(1, 2, 3).collect(Collectors.toList());
        var expected = List.of(1, 2, 3);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, actualStream);
    }

    @Test
    public void addAllIterable() {
        MutableList<Integer> list1 = MutableList.empty();
        var expected = List.of(1, 2, 3);
        list1.addAllIterable(expected::iterator);
        Assert.assertEquals(expected, list1);
        MutableList<Integer> list2 = MutableList.empty();
        list2.addAllIterable(expected::iterator);
        Assert.assertEquals(expected, list2);
    }
}
