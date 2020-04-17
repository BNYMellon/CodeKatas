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
import java.util.stream.Stream;

public class MutableSetTest {
    @Test
    public void filter() {
        MutableSet<Integer> set = MutableSet.of(1, 2, 3, 4, 5);
        MutableSet<Integer> actual = set.filter(each -> each % 2 == 0);
        MutableSet<Integer> expected = MutableSet.of(2, 4);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void filterNot() {
        MutableSet<Integer> set = MutableSet.of(1, 2, 3, 4, 5);
        MutableSet<Integer> actual = set.filterNot(each -> each % 2 == 0);
        MutableSet<Integer> expected = MutableSet.of(1, 3, 5);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void map() {
        MutableSet<Integer> set = MutableSet.of(1, 2, 3, 4, 5);
        MutableSet<String> actual = set.map(String::valueOf);
        MutableSet<String> expected = MutableSet.of("1", "2", "3", "4", "5");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void flatMap() {
        MutableSet<List<Integer>> list = MutableSet.of(List.of(1), List.of(2));
        MutableSet<Integer> actual = list.flatMap(each -> each);
        Set<Integer> expected = Set.of(1, 2);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void reduce() {
        MutableSet<Integer> list = MutableSet.of(1, 2, 3, 4, 5);
        Optional<Integer> reduce = list.reduce(Integer::sum);
        Assert.assertEquals(Integer.valueOf(15), reduce.orElse(0));
    }

    @Test
    public void anyMatch() {
        MutableSet<Integer> set = MutableSet.of(1, 2, 3, 4, 5);
        Assert.assertTrue(set.anyMatch(each -> each % 2 == 0));
        Assert.assertFalse(set.anyMatch(each -> each < 0));
    }

    @Test
    public void allMatch() {
        MutableSet<Integer> set = MutableSet.of(1, 2, 3, 4, 5);
        Assert.assertFalse(set.allMatch(each -> each % 2 == 0));
        Assert.assertTrue(set.allMatch(each -> each > 0));
    }

    @Test
    public void noneMatch() {
        MutableSet<Integer> set = MutableSet.of(1, 2, 3, 4, 5);
        Assert.assertFalse(set.noneMatch(each -> each % 2 == 0));
        Assert.assertTrue(set.noneMatch(each -> each < 0));
    }

    @Test
    public void count() {
        MutableSet<Integer> set = MutableSet.of(1, 2, 3, 4, 5);
        Assert.assertEquals(2, set.count(each -> each % 2 == 0));
        Assert.assertEquals(3, set.count(each -> each % 2 == 1));
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
        MutableMap<Integer, Long> counts = set.countBy(each -> each % 2);
        MutableMap<Integer, Long> expected = MutableMap.of(1, 3L, 0, 2L);
        Assert.assertEquals(expected, counts);
    }

    @Test
    public void groupBy() {
        MutableSet<Integer> set = MutableSet.of(1, 2, 3, 4, 5);
        MutableMap<Integer, MutableSet<Integer>> grouped = set.groupBy(each -> each % 2);
        MutableMap<Integer, MutableSet<Integer>> expected = MutableMap.of(1, MutableSet.of(1, 3, 5), 0, MutableSet.of(2, 4));
        Assert.assertEquals(expected, grouped);
    }

    @Test
    public void fromIterable() {
        Set<Integer> expected = Set.of(1, 2, 3);
        MutableSet<Integer> actual = MutableSet.fromIterable(expected);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void fromStream() {
        Set<Integer> expected = Set.of(1, 2, 3);
        MutableSet<Integer> actual = MutableSet.fromStream(Stream.of(1, 2, 3));
        Assert.assertEquals(expected, actual);
    }
}
