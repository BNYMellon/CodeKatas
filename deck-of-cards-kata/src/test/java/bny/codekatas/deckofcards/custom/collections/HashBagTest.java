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

import java.util.Iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HashBagTest
{
    private HashBag<String> testObj;

    @BeforeEach
    public void setup()
    {
        this.testObj = new HashBag<>();
    }

    @Test
    public void withAllVarArgs()
    {
        MutableBag<String> bag = this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assertions.assertEquals(1, bag.getOccurrences("1"));
        Assertions.assertEquals(2, bag.getOccurrences("2"));
        Assertions.assertEquals(3, bag.getOccurrences("3"));
    }

    @Test
    public void withAll()
    {
        MutableBag<String> bag = this.testObj.withAll(MutableList.of("1", "2", "3", "2", "3", "3"));
        Assertions.assertEquals(1, bag.getOccurrences("1"));
        Assertions.assertEquals(2, bag.getOccurrences("2"));
        Assertions.assertEquals(3, bag.getOccurrences("3"));
    }

    @Test
    public void size_sizeDistinct()
    {
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assertions.assertEquals(6, this.testObj.size());
        Assertions.assertEquals(3, this.testObj.sizeDistinct());
    }

    @Test
    public void isEmpty()
    {
        Assertions.assertTrue(this.testObj.isEmpty());
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assertions.assertFalse(this.testObj.isEmpty());
    }

    @Test
    public void contains()
    {
        Assertions.assertFalse(this.testObj.contains("1"));
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assertions.assertTrue(this.testObj.contains("1"));
        Assertions.assertFalse(this.testObj.contains("4"));
    }

    @Test
    public void toArray()
    {
        Assertions.assertArrayEquals(new String[]{}, this.testObj.toArray());
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assertions.assertArrayEquals(new String[]{"1", "2", "2", "3", "3", "3"}, this.testObj.toArray());
    }

    @Test
    public void toArray_target()
    {
        Assertions.assertArrayEquals(new String[]{}, this.testObj.toArray(new String[]{}));
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assertions.assertArrayEquals(new String[]{"1", "2", "2", "3", "3", "3"}, this.testObj.toArray(new String[]{"0"}));
    }

    @Test
    public void add()
    {
        this.testObj.add("1");
        this.testObj.add("2");
        this.testObj.add("2");

        Assertions.assertEquals(MutableBag.of("1", "2", "2"), this.testObj);
    }

    @Test
    public void remove()
    {
        this.testObj.add("1");
        this.testObj.add("2");
        this.testObj.add("2");

        Assertions.assertEquals(MutableBag.of("1", "2", "2"), this.testObj);
        this.testObj.remove("2");
        Assertions.assertEquals(MutableBag.of("1"), this.testObj);
        this.testObj.remove("1");
        Assertions.assertEquals(MutableBag.empty(), this.testObj);
    }

    @Test
    public void containsAll()
    {
        Assertions.assertTrue(this.testObj.containsAll(MutableList.empty()));
        Assertions.assertFalse(this.testObj.containsAll(MutableList.of("1")));

        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assertions.assertTrue(this.testObj.containsAll(MutableList.of("1")));
        Assertions.assertTrue(this.testObj.containsAll(MutableList.of("1", "2")));
        Assertions.assertFalse(this.testObj.containsAll(MutableList.of("1", "2", "4")));
    }

    @Test
    public void addAll()
    {
        Assertions.assertFalse(this.testObj.addAll(MutableList.empty()));
        Assertions.assertEquals(MutableBag.empty(), this.testObj);
        Assertions.assertTrue(this.testObj.addAll(MutableList.of("1", "2", "2")));
        Assertions.assertEquals(MutableBag.of("1", "2", "2"), this.testObj);
    }

    @Test
    public void removeAll()
    {
        Assertions.assertFalse(this.testObj.removeAll(MutableList.empty()));
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assertions.assertTrue(this.testObj.removeAll(MutableList.of("1", "2")));
        Assertions.assertEquals(MutableBag.of("3", "3", "3"), this.testObj);
        Assertions.assertFalse(this.testObj.removeAll(MutableList.of("4")));
        Assertions.assertTrue(this.testObj.removeAll(MutableList.of("3", "3", "3")));
        Assertions.assertEquals(MutableBag.empty(), this.testObj);
    }

    @Test
    public void retailAll()
    {
        Assertions.assertFalse(this.testObj.retainAll(MutableList.empty()));
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assertions.assertTrue(this.testObj.retainAll(MutableList.of("1", "2")));
        Assertions.assertEquals(MutableBag.of("1", "2", "2"), this.testObj);
        Assertions.assertFalse(this.testObj.retainAll(MutableList.of("4")));
        Assertions.assertEquals(MutableBag.of("1", "2", "2"), this.testObj);
        Assertions.assertTrue(this.testObj.retainAll(MutableList.of("2", "4")));
        Assertions.assertEquals(MutableBag.of("2", "2"), this.testObj);
    }

    @Test
    public void clear()
    {
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assertions.assertFalse(this.testObj.isEmpty());
        this.testObj.clear();
        Assertions.assertTrue(this.testObj.isEmpty());
        Assertions.assertEquals(0, this.testObj.size());
        Assertions.assertEquals(0, this.testObj.sizeDistinct());
    }

    @Test
    public void getOccurrences()
    {
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assertions.assertEquals(1, this.testObj.getOccurrences("1"));
        Assertions.assertEquals(2, this.testObj.getOccurrences("2"));
        Assertions.assertEquals(3, this.testObj.getOccurrences("3"));
        Assertions.assertEquals(0, this.testObj.getOccurrences("4"));
    }

    @Test
    public void iterator()
    {
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Iterator<String> iterator = this.testObj.iterator();

        Assertions.assertEquals("1", iterator.next());
        Assertions.assertTrue(iterator.hasNext());
        iterator.remove();
        Assertions.assertEquals("2", iterator.next());
        iterator.remove();
        Assertions.assertEquals("2", iterator.next());
        Assertions.assertEquals("3", iterator.next());
        iterator.remove();
        Assertions.assertEquals("3", iterator.next());
        iterator.remove();
        Assertions.assertEquals("3", iterator.next());
        iterator.remove();
        Assertions.assertFalse(iterator.hasNext());
        Assertions.assertEquals(MutableBag.of("2"), this.testObj);
    }

    @Test
    public void addOccurrence()
    {
        this.testObj.addOccurrence("1");
        Assertions.assertEquals(MutableBag.of("1"), this.testObj);
        Assertions.assertEquals(1, this.testObj.size());
        Assertions.assertEquals(1, this.testObj.sizeDistinct());
    }

    @Test
    public void addOccurrences()
    {
        this.testObj.addOccurrences("1", 1);
        Assertions.assertEquals(MutableBag.of("1"), this.testObj);
        Assertions.assertEquals(1, this.testObj.size());
        Assertions.assertEquals(1, this.testObj.sizeDistinct());
        this.testObj.addOccurrences("2", 2);
        Assertions.assertEquals(MutableBag.of("1", "2", "2"), this.testObj);
        Assertions.assertEquals(3, this.testObj.size());
        Assertions.assertEquals(2, this.testObj.sizeDistinct());
    }

    @Test
    public void removeOccurrence()
    {
        Assertions.assertFalse(this.testObj.removeOccurrence("1"));
        Assertions.assertEquals(MutableBag.empty(), this.testObj);
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assertions.assertTrue(this.testObj.removeOccurrence("1"));
        Assertions.assertEquals(MutableBag.of("2", "2", "3", "3", "3"), this.testObj);
        Assertions.assertTrue(this.testObj.removeOccurrence("2"));
        Assertions.assertEquals(MutableBag.of("2", "3", "3", "3"), this.testObj);
        Assertions.assertTrue(this.testObj.removeOccurrence("2"));
        Assertions.assertEquals(MutableBag.of("3", "3", "3"), this.testObj);
        Assertions.assertFalse(this.testObj.removeOccurrence("2"));
    }

    @Test
    public void removeOccurrences()
    {
        Assertions.assertFalse(this.testObj.removeOccurrences("1", 1));
        Assertions.assertEquals(MutableBag.empty(), this.testObj);
        this.testObj.withAll("1", "2", "3", "2", "3", "3");
        Assertions.assertTrue(this.testObj.removeOccurrences("1", 2));
        Assertions.assertEquals(MutableBag.of("2", "2", "3", "3", "3"), this.testObj);
        Assertions.assertEquals(5, this.testObj.size());
        Assertions.assertTrue(this.testObj.removeOccurrences("2", 2));
        Assertions.assertEquals(MutableBag.of("3", "3", "3"), this.testObj);
        Assertions.assertFalse(this.testObj.removeOccurrences("2", 2));
    }

    @Test
    public void forEachWithOccurrences()
    {
        MutableList<String> list = MutableList.empty();
        this.testObj.withAll("1", "2", "3", "2", "3", "3")
                .forEachWithOccurrences((each, count) -> list.add(each + count));
        Assertions.assertEquals(MutableList.of("11", "22", "33"), list);
    }

    @Test
    public void forEach()
    {
        MutableList<String> list = MutableList.empty();
        this.testObj.withAll("1", "2", "3", "2", "3", "3")
                .forEach((each) -> list.add(each));
        Assertions.assertEquals(MutableList.of("1", "2", "2", "3", "3", "3"), list);
    }

    @Test
    public void fromIterable()
    {
        Assertions.assertEquals(
                MutableBag.of("1", "2", "2", "3", "3", "3"),
                MutableBag.fromIterable(MutableList.of("1", "2", "2", "3", "3", "3")));
    }

    @Test
    public void fromStream()
    {
        Assertions.assertEquals(
                MutableBag.of("1", "2", "2", "3", "3", "3"),
                MutableBag.fromStream(MutableList.of("1", "2", "2", "3", "3", "3").stream()));
    }

    @Test
    public void filter()
    {
        Assertions.assertEquals(
                MutableBag.of("2", "2"),
                MutableBag.of("1", "2", "2", "3", "3", "3").filter(each -> Integer.valueOf(each) % 2 == 0));
    }

    @Test
    public void filterNot()
    {
        Assertions.assertEquals(
                MutableBag.of("1", "3", "3", "3"),
                MutableBag.of("1", "2", "2", "3", "3", "3").filterNot(each -> Integer.parseInt(each) % 2 == 0));
    }

    @Test
    public void map()
    {
        Assertions.assertEquals(
                MutableBag.of(1, 2, 2, 3, 3, 3),
                MutableBag.of("1", "2", "2", "3", "3", "3").map(Integer::valueOf));
    }

    @Test
    public void flatMap()
    {
        Assertions.assertEquals(
                MutableBag.of("1", "2", "2", "3", "3", "3"),
                MutableBag
                        .of(MutableList.of("1"), MutableList.of("2", "2"), MutableList.of("3", "3", "3"))
                        .flatMap(each -> each));
    }
}
