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

public class MutableSetTest {
    @Test
    public void filter() {
        MutableSet<Integer> set = MutableSet.of(1, 2, 3, 4, 5);
        MutableSet<Integer> actual = set.filter(each -> each % 2 == 0);
        Set<Integer> actualStream = set.stream().filter(each -> each % 2 == 0).collect(Collectors.toSet());
        var expected = MutableSet.of(2, 4);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, actualStream);
    }

    @Test
    public void filterNot() {
        MutableSet<Integer> set = MutableSet.of(1, 2, 3, 4, 5);
        MutableSet<Integer> actual = set.filterNot(each -> each % 2 == 0);
        Set<Integer> actualStream = set.stream().filter(each -> each % 2 != 0).collect(Collectors.toSet());
        var expected = MutableSet.of(1, 3, 5);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, actualStream);
    }

    @Test
    public void map() {
        MutableSet<Integer> set = MutableSet.of(1, 2, 3, 4, 5);
        MutableSet<String> actual = set.map(String::valueOf);
        Set<String> actualStream = set.stream().map(String::valueOf).collect(Collectors.toSet());
        var expected = MutableSet.of("1", "2", "3", "4", "5");
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, actualStream);
    }

    @Test
    public void flatMap() {
        MutableSet<List<Integer>> set = MutableSet.of(List.of(1), List.of(2));
        MutableSet<Integer> actual = set.flatMap(each -> each);
        Set<Integer> actualStream = set.stream().flatMap(List::stream).collect(Collectors.toSet());
        var expected = Set.of(1, 2);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, actualStream);
    }

    @Test
    public void reduce() {
        MutableSet<Integer> set = MutableSet.of(1, 2, 3, 4, 5);
        Optional<Integer> reduce = set.reduce(Integer::sum);
        Optional<Integer> reduceStream = set.stream().reduce(Integer::sum);
        Assert.assertEquals(Integer.valueOf(15), reduce.orElse(0));
        Assert.assertEquals(Integer.valueOf(15), reduceStream.orElse(0));
        MutableSet<Integer> empty = MutableSet.empty();
        Assert.assertTrue(empty.reduce(Integer::sum).isEmpty());
    }

    @Test
    public void anyMatch() {
        MutableSet<Integer> set = MutableSet.of(1, 2, 3, 4, 5);
        Assert.assertTrue(set.anyMatch(each -> each % 2 == 0));
        Assert.assertTrue(set.stream().anyMatch(each -> each % 2 == 0));
        Assert.assertFalse(set.anyMatch(each -> each < 0));
        Assert.assertFalse(set.stream().anyMatch(each -> each < 0));
    }

    @Test
    public void allMatch() {
        MutableSet<Integer> set = MutableSet.of(1, 2, 3, 4, 5);
        Assert.assertFalse(set.allMatch(each -> each % 2 == 0));
        Assert.assertFalse(set.stream().allMatch(each -> each % 2 == 0));
        Assert.assertTrue(set.allMatch(each -> each > 0));
        Assert.assertTrue(set.stream().allMatch(each -> each > 0));
    }

    @Test
    public void noneMatch() {
        MutableSet<Integer> set = MutableSet.of(1, 2, 3, 4, 5);
        Assert.assertFalse(set.noneMatch(each -> each % 2 == 0));
        Assert.assertFalse(set.stream().noneMatch(each -> each % 2 == 0));
        Assert.assertTrue(set.stream().noneMatch(each -> each < 0));
    }

    @Test
    public void count() {
        MutableSet<Integer> set = MutableSet.of(1, 2, 3, 4, 5);
        Assert.assertEquals(2, set.count(each -> each % 2 == 0));
        Assert.assertEquals(2L, set.stream().filter(each -> each % 2 == 0).count());
        Assert.assertEquals(3, set.count(each -> each % 2 == 1));
        Assert.assertEquals(3L, set.stream().filter(each -> each % 2 == 1).count());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void asUnmodifiable() {
        MutableSet<Integer> set = MutableSet.of(1, 2, 3, 4, 5);
        Set<Integer> unmodifiable = set.asUnmodifiable();
        unmodifiable.add(6);
    }

    @Test
    public void countBy() {
        MutableSet<Integer> set = MutableSet.of(1, 2, 3, 4, 5);
        MutableBag<Integer> counts = set.countBy(each -> each % 2);
        Map<Integer, Long> countsStream = set.stream().collect(Collectors.groupingBy(each -> each % 2, Collectors.counting()));
        var expected = MutableBag.of(1, 1, 1, 0, 0);
        Assert.assertEquals(expected, counts);
        Map<Integer, Long> expectedStream = Map.of(1, 3L, 0, 2L);
        Assert.assertEquals(expectedStream, countsStream);
    }

    @Test
    public void groupBy() {
        MutableSet<Integer> set = MutableSet.of(1, 2, 3, 4, 5);
        MutableSetMultimap<Integer, Integer> grouped = set.groupBy(each -> each % 2);
        Map<Integer, Set<Integer>> groupedStream = set.stream().collect(Collectors.groupingBy(each -> each % 2, Collectors.toSet()));
        var expected = MutableSetMultimap.<Integer, Integer>empty();
        var oddSet = MutableSet.of(1, 3, 5);
        var evenSet = MutableSet.of(2, 4);
        expected.put(1, oddSet);
        expected.put(0, evenSet);
        Assert.assertEquals(expected, grouped);
        Assert.assertEquals(Map.of(1, oddSet, 0, evenSet), groupedStream);
    }

    @Test
    public void collect() {
        MutableSet<Integer> set = MutableSet.of(1, 2, 3, 4, 5);
        Collector<Integer, ?, Map<Integer, Set<Integer>>> collector = Collectors.groupingBy(
                each -> each % 2,
                Collectors.toSet());
        var streamGroupingBy = set.stream().collect(collector);
        var collectGroupingBy = set.collect(collector);
        Assert.assertEquals(streamGroupingBy, collectGroupingBy);
    }

    @Test
    public void fromIterable() {
        var expected = Set.of(1, 2, 3);
        MutableSet<Integer> actual = MutableSet.fromIterable(expected);
        Set<Integer> actualStream = StreamSupport.stream(expected.spliterator(), false).collect(Collectors.toSet());
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, actualStream);
    }

    @Test
    public void fromStream() {
        var expected = Set.of(1, 2, 3);
        MutableSet<Integer> actual = MutableSet.fromStream(Stream.of(1, 2, 3));
        Set<Integer> actualStream = Stream.of(1, 2, 3).collect(Collectors.toSet());
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, actualStream);
    }
}
