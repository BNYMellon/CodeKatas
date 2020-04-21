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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MutableListTest {

    @Test
    public void filter() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        MutableList<Integer> actual = list.filter(each -> each % 2 == 0);
        var expected = MutableList.of(2, 4);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void filterNot() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        MutableList<Integer> actual = list.filterNot(each -> each % 2 == 0);
        var expected = MutableList.of(1, 3, 5);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void map() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        MutableList<String> actual = list.map(String::valueOf);
        var expected = List.of("1", "2", "3", "4", "5");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void flatMap() {
        MutableList<List<Integer>> list = MutableList.of(List.of(1), List.of(2));
        MutableList<Integer> actual = list.flatMap(each -> each);
        var expected = List.of(1, 2);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void reduce() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        Optional<Integer> reduce = list.reduce(Integer::sum);
        Assert.assertEquals(Integer.valueOf(15), reduce.orElse(0));
        MutableList<Integer> empty = MutableList.empty();
        Assert.assertTrue(empty.reduce(Integer::sum).isEmpty());
    }

    @Test
    public void anyMatch() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        Assert.assertTrue(list.anyMatch(each -> each % 2 == 0));
        Assert.assertFalse(list.anyMatch(each -> each < 0));
    }

    @Test
    public void allMatch() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        Assert.assertFalse(list.allMatch(each -> each % 2 == 0));
        Assert.assertTrue(list.allMatch(each -> each > 0));
    }

    @Test
    public void noneMatch() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        Assert.assertFalse(list.noneMatch(each -> each % 2 == 0));
        Assert.assertTrue(list.noneMatch(each -> each < 0));
    }

    @Test
    public void count() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        Assert.assertEquals(2, list.count(each -> each % 2 == 0));
        Assert.assertEquals(3, list.count(each -> each % 2 == 1));
    }

    @Test
    public void findFirst() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        Assert.assertEquals(Integer.valueOf(2), list.findFirst(each -> each % 2 == 0).orElse(0));
        Assert.assertEquals(Integer.valueOf(0), list.findFirst(each -> each < 0).orElse(0));
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
        Assert.assertEquals(3, counts.getOccurrences(1));
        Assert.assertEquals(2, counts.getOccurrences(0));
    }

    @Test
    public void groupBy() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        MutableListMultimap<Integer, Integer> grouped = list.groupBy(each -> each % 2);
        MutableListMultimap<Integer, Integer> expected = ArrayListMultimap.newMultimap();
        expected.put(1, MutableList.of(1, 3, 5));
        expected.put(0, MutableList.of(2, 4));

        Assert.assertEquals(expected, grouped);
    }

    @Test
    public void collect() {
        MutableList<Integer> list = MutableList.of(1, 2, 3, 4, 5);
        var streamGroupingBy = list.stream().collect(
                Collectors.groupingBy(
                        each -> each % 2,
                        Collectors.toList()));
        var collectGroupingBy = list.collect(
                Collectors.groupingBy(
                        each -> each % 2,
                        MutableMap::empty,
                        Collectors.toCollection(MutableList::empty)));
        Assert.assertEquals(streamGroupingBy, collectGroupingBy);
    }

    @Test
    public void toList() {
        MutableList<Integer> actual = MutableSet.of(1).toList();
        var expected = List.of(1);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void toSet() {
        MutableSet<Integer> actual = MutableList.of(1).toSet();
        var expected = Set.of(1);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void fromIterable() {
        var expected = List.of(1, 2, 3);
        MutableList<Integer> actual = MutableList.fromIterable(expected);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void fromStream() {
        MutableList<Integer> actual = MutableList.fromStream(Stream.of(1, 2, 3));
        var expected = List.of(1, 2, 3);
        Assert.assertEquals(expected, actual);
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
