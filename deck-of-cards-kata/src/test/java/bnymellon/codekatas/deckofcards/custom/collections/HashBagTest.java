/*
 * Copyright 2020 The Bank of New York Mellon.
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
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

public class HashBagTest {
    private HashBag<String> testObj;

    @Before
    public void setup() {
        this.testObj = new HashBag<>();
    }

    @Test
    public void withAll() {
        HashBag<String> bag = this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assert.assertEquals(1, bag.getOccurrences("1"));
        Assert.assertEquals(2, bag.getOccurrences("2"));
        Assert.assertEquals(3, bag.getOccurrences("3"));
    }

    @Test
    public void size_sizeDistinct() {
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assert.assertEquals(6, this.testObj.size());
        Assert.assertEquals(3, this.testObj.sizeDistinct());
    }

    @Test
    public void isEmpty() {
        Assert.assertTrue(this.testObj.isEmpty());
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assert.assertFalse(this.testObj.isEmpty());
    }

    @Test
    public void contains() {
        Assert.assertFalse(this.testObj.contains("1"));
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assert.assertTrue(this.testObj.contains("1"));
        Assert.assertFalse(this.testObj.contains("4"));
    }

    @Test
    public void toArray() {
        Assert.assertArrayEquals(new String[]{}, this.testObj.toArray());
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assert.assertArrayEquals(new String[]{"1", "2", "2", "3", "3", "3"}, this.testObj.toArray());
    }

    @Test
    public void toArray_target() {
        Assert.assertArrayEquals(new String[]{}, this.testObj.toArray(new String[]{}));
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assert.assertArrayEquals(new String[]{"1", "2", "2", "3", "3", "3"}, this.testObj.toArray(new String[]{"0"}));
    }

    @Test
    public void forEachWithIndex() {
        MutableList<String> list = MutableList.empty();
        this.testObj.withAll("1", "2", "3", "2", "3", "3")
                .forEachWithIndex((each, index) -> list.add(each + index));
        Assert.assertEquals(MutableList.of("10", "21", "22", "33", "34", "35"), list);
    }

    @Test
    public void add() {
        this.testObj.add("1");
        this.testObj.add("2");
        this.testObj.add("2");

        Assert.assertEquals(MutableBag.of("1", "2", "2"), this.testObj);
    }

    @Test
    public void remove() {
        this.testObj.add("1");
        this.testObj.add("2");
        this.testObj.add("2");

        Assert.assertEquals(MutableBag.of("1", "2", "2"), this.testObj);
        this.testObj.remove("2");
        Assert.assertEquals(MutableBag.of("1"), this.testObj);
        this.testObj.remove("1");
        Assert.assertEquals(MutableBag.empty(), this.testObj);
    }

    @Test
    public void containsAll() {
        Assert.assertTrue(this.testObj.containsAll(MutableList.empty()));
        Assert.assertFalse(this.testObj.containsAll(MutableList.of("1")));

        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assert.assertTrue(this.testObj.containsAll(MutableList.of("1")));
        Assert.assertTrue(this.testObj.containsAll(MutableList.of("1", "2")));
        Assert.assertFalse(this.testObj.containsAll(MutableList.of("1", "2", "4")));
    }

    @Test
    public void addAll() {
        Assert.assertFalse(this.testObj.addAll(MutableList.empty()));
        Assert.assertEquals(MutableBag.empty(), this.testObj);
        Assert.assertTrue(this.testObj.addAll(MutableList.of("1", "2", "2")));
        Assert.assertEquals(MutableBag.of("1", "2", "2"), this.testObj);
    }

    @Test
    public void removeAll() {
        Assert.assertFalse(this.testObj.removeAll(MutableList.empty()));
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assert.assertTrue(this.testObj.removeAll(MutableList.of("1", "2")));
        Assert.assertEquals(MutableBag.of("3", "3", "3"), this.testObj);
        Assert.assertFalse(this.testObj.removeAll(MutableList.of("4")));
        Assert.assertTrue(this.testObj.removeAll(MutableList.of("3", "3", "3")));
        Assert.assertEquals(MutableBag.empty(), this.testObj);
    }

    @Test
    public void retailAll() {
        Assert.assertFalse(this.testObj.retainAll(MutableList.empty()));
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assert.assertTrue(this.testObj.retainAll(MutableList.of("1", "2")));
        Assert.assertEquals(MutableBag.of("1", "2", "2"), this.testObj);
        Assert.assertFalse(this.testObj.retainAll(MutableList.of("4")));
        Assert.assertEquals(MutableBag.of("1", "2", "2"), this.testObj);
        Assert.assertTrue(this.testObj.retainAll(MutableList.of("2", "4")));
        Assert.assertEquals(MutableBag.of("2", "2"), this.testObj);
    }

    @Test
    public void clear() {
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assert.assertFalse(this.testObj.isEmpty());
        this.testObj.clear();
        Assert.assertTrue(this.testObj.isEmpty());
        Assert.assertEquals(0, this.testObj.size());
        Assert.assertEquals(0, this.testObj.sizeDistinct());
    }

    @Test
    public void getOccurrences() {
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assert.assertEquals(1, this.testObj.getOccurrences("1"));
        Assert.assertEquals(2, this.testObj.getOccurrences("2"));
        Assert.assertEquals(3, this.testObj.getOccurrences("3"));
        Assert.assertEquals(0, this.testObj.getOccurrences("4"));
    }

    @Test
    public void iterator() {
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Iterator<String> iterator = this.testObj.iterator();

        Assert.assertEquals("1", iterator.next());
        Assert.assertTrue(iterator.hasNext());
        iterator.remove();
        Assert.assertEquals("2", iterator.next());
        iterator.remove();
        Assert.assertEquals("2", iterator.next());
        Assert.assertEquals("3", iterator.next());
        iterator.remove();
        Assert.assertEquals("3", iterator.next());
        iterator.remove();
        Assert.assertEquals("3", iterator.next());
        iterator.remove();
        Assert.assertFalse(iterator.hasNext());
        Assert.assertEquals(MutableBag.of("2"), this.testObj);
    }

    @Test
    public void addOccurrence() {
        this.testObj.addOccurrence("1");
        Assert.assertEquals(MutableBag.of("1"), this.testObj);
        Assert.assertEquals(1, this.testObj.size());
        Assert.assertEquals(1, this.testObj.sizeDistinct());
    }

    @Test
    public void addOccurrences() {
        this.testObj.addOccurrences("1", 1);
        Assert.assertEquals(MutableBag.of("1"), this.testObj);
        Assert.assertEquals(1, this.testObj.size());
        Assert.assertEquals(1, this.testObj.sizeDistinct());
        this.testObj.addOccurrences("2", 2);
        Assert.assertEquals(MutableBag.of("1", "2", "2"), this.testObj);
        Assert.assertEquals(3, this.testObj.size());
        Assert.assertEquals(2, this.testObj.sizeDistinct());
    }

    @Test
    public void removeOccurrence() {
        Assert.assertFalse(this.testObj.removeOccurrence("1"));
        Assert.assertEquals(MutableBag.empty(), this.testObj);
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assert.assertTrue(this.testObj.removeOccurrence("1"));
        Assert.assertEquals(MutableBag.of("2", "2", "3", "3", "3"), this.testObj);
        Assert.assertTrue(this.testObj.removeOccurrence("2"));
        Assert.assertEquals(MutableBag.of("2", "3", "3", "3"), this.testObj);
        Assert.assertTrue(this.testObj.removeOccurrence("2"));
        Assert.assertEquals(MutableBag.of("3", "3", "3"), this.testObj);
        Assert.assertFalse(this.testObj.removeOccurrence("2"));
    }

    @Test
    public void removeOccurrences() {
        Assert.assertFalse(this.testObj.removeOccurrences("1", 1));
        Assert.assertEquals(MutableBag.empty(), this.testObj);
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assert.assertTrue(this.testObj.removeOccurrences("1", 2));
        Assert.assertEquals(MutableBag.of("2", "2", "3", "3", "3"), this.testObj);
        Assert.assertEquals(5, this.testObj.size());
        Assert.assertTrue(this.testObj.removeOccurrences("2", 2));
        Assert.assertEquals(MutableBag.of("3", "3", "3"), this.testObj);
        Assert.assertFalse(this.testObj.removeOccurrences("2", 2));
    }

    @Test
    public void forEachWithOccurrences() {
        MutableList<String> list = MutableList.empty();
        this.testObj.withAll("1", "2", "3", "2", "3", "3")
                .forEachWithOccurrences((each, count) -> list.add(each + count));
        Assert.assertEquals(MutableList.of("11", "22", "33"), list);
    }

    @Test
    public void forEach() {
        MutableList<String> list = MutableList.empty();
        this.testObj.withAll("1", "2", "3", "2", "3", "3")
                .forEach((each) -> list.add(each));
        Assert.assertEquals(MutableList.of("1", "2", "2", "3", "3", "3"), list);
    }

    @Test
    public void fromIterable() {
        Assert.assertEquals(MutableBag.of("1", "2", "2", "3", "3", "3"),
                MutableBag.fromIterable(MutableList.of("1", "2", "2", "3", "3", "3")));
    }

    @Test
    public void fromStream() {
        Assert.assertEquals(MutableBag.of("1", "2", "2", "3", "3", "3"),
                MutableBag.fromStream(MutableList.of("1", "2", "2", "3", "3", "3").stream()));
    }

    @Test
    public void filter() {
        Assert.assertEquals(MutableBag.of("2", "2"),
                MutableBag.of("1", "2", "2", "3", "3", "3").filter(each -> Integer.valueOf(each) % 2 == 0));
    }

    @Test
    public void filterNot() {
        Assert.assertEquals(MutableBag.of("1", "3", "3", "3"),
                MutableBag.of("1", "2", "2", "3", "3", "3").filterNot(each -> Integer.parseInt(each) % 2 == 0));
    }

    @Test
    public void map() {
        Assert.assertEquals(MutableBag.of(1, 2, 2, 3, 3, 3),
                MutableBag.of("1", "2", "2", "3", "3", "3").map(Integer::valueOf));
    }

    @Test
    public void flatMap() {
        Assert.assertEquals(MutableBag.of("1", "2", "2", "3", "3", "3"),
                MutableBag.of(MutableList.of("1"), MutableList.of("2", "2"), MutableList.of("3", "3", "3")).flatMap(each -> each));
    }
}
