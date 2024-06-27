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

import java.util.HashSet;

import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HashSetMultimapTest
{
    private HashSetMultimap<String, Integer> testObj;

    @BeforeEach
    public void setup()
    {
        this.testObj = new HashSetMultimap<>();
    }

    @Test
    public void isConstructed()
    {
        Assertions.assertNotNull(new HashSetMultimap<>());
    }

    @Test
    public void isEmpty()
    {
        Assertions.assertTrue(this.testObj.isEmpty());
        this.testObj.put("A", 1);
        Assertions.assertFalse(this.testObj.isEmpty());
    }

    @Test
    public void get_put()
    {
        this.testObj.put("A", 1);
        Assertions.assertEquals(MutableSet.of(1), this.testObj.get("A"));
        Assertions.assertEquals(1, this.testObj.size());
        this.testObj.put("A", 2);
        Assertions.assertEquals(MutableSet.of(1, 2), this.testObj.get("A"));
        Assertions.assertEquals(2, this.testObj.size());
        this.testObj.put("A", 1);
        Assertions.assertEquals(MutableSet.of(1, 2), this.testObj.get("A"));
        Assertions.assertEquals(2, this.testObj.size());
        this.testObj.put("B", 10);
        Assertions.assertEquals(MutableSet.of(10), this.testObj.get("B"));
        Assertions.assertEquals(3, this.testObj.size());
        this.testObj.put("B", 11);
        Assertions.assertEquals(MutableSet.of(10, 11), this.testObj.get("B"));
        Assertions.assertEquals(4, this.testObj.size());
        this.testObj.put("A", 2);
        Assertions.assertEquals(MutableSet.of(1, 2), this.testObj.get("A"));
        Assertions.assertEquals(4, this.testObj.size());
    }

    @Test
    public void get_putIterable()
    {
        this.testObj.put("A", MutableSet.of(1));
        Assertions.assertEquals(MutableSet.of(1), this.testObj.get("A"));
        Assertions.assertEquals(1, this.testObj.size());
        this.testObj.put("A", MutableSet.of(2, 1));
        Assertions.assertEquals(MutableSet.of(1, 2), this.testObj.get("A"));
        Assertions.assertEquals(2, this.testObj.size());
        this.testObj.put("B", MutableSet.of(10, 11));
        Assertions.assertEquals(MutableSet.of(10, 11), this.testObj.get("B"));
        Assertions.assertEquals(4, this.testObj.size());
        this.testObj.put("A", MutableSet.of(2, 3, 4));
        Assertions.assertEquals(MutableSet.of(1, 2, 3, 4), this.testObj.get("A"));
        Assertions.assertEquals(6, this.testObj.size());
    }

    @Test
    public void putAll()
    {
        this.testObj.putAll(MutableMap.of("A", MutableList.of(1, 2, 3)));
        Assertions.assertEquals(MutableSet.of(1, 2, 3), this.testObj.get("A"));
        Assertions.assertEquals(3, this.testObj.size());
        this.testObj.putAll(MutableMap.of("B", MutableList.of(10, 10)));
        Assertions.assertEquals(MutableSet.of(10, 10), this.testObj.get("B"));
        Assertions.assertEquals(4, this.testObj.size());
    }

    @Test
    public void remove()
    {
        Assertions.assertEquals(MutableSet.empty(), this.testObj.remove("A"));
        this.testObj.putAll(MutableMap.of("A", MutableList.of(1, 2, 3)));
        this.testObj.putAll(MutableMap.of("B", MutableList.of(10, 10)));
        Assertions.assertEquals(4, this.testObj.size());
        this.testObj.remove("A");
        Assertions.assertEquals(1, this.testObj.size());
        this.testObj.remove("B");
        Assertions.assertEquals(0, this.testObj.size());
    }

    @Test
    public void remove_keyValue()
    {
        Assertions.assertEquals(MutableSet.empty(), this.testObj.remove("A"));
        this.testObj.putAll(MutableMap.of("A", MutableList.of(1, 2, 3, 1)));
        this.testObj.putAll(MutableMap.of("B", MutableList.of(10, 10)));
        Assertions.assertEquals(4, this.testObj.size());
        this.testObj.remove("A", 1);
        Assertions.assertEquals(MutableSet.of(2, 3), this.testObj.get("A"));
        Assertions.assertEquals(3, this.testObj.size());
        this.testObj.remove("B", 10);
        MutableSet<Integer> getForB = this.testObj.get("B");
        Assertions.assertEquals(MutableSet.empty(), getForB);
        Assertions.assertEquals(2, this.testObj.size());
        Assertions.assertNotSame(getForB, this.testObj.get("B"));
    }

    @Test
    public void containsKey()
    {
        Assertions.assertFalse(this.testObj.containsKey("A"));
        this.testObj.putAll(MutableMap.of("A", MutableSet.of(1, 2, 3, 1)));
        Assertions.assertTrue(this.testObj.containsKey("A"));
        this.testObj.putAll(MutableMap.of("B", MutableSet.of(10, 10)));
        Assertions.assertTrue(this.testObj.containsKey("A"));
        Assertions.assertTrue(this.testObj.containsKey("B"));
    }

    @Test
    public void containsValue()
    {
        Assertions.assertFalse(this.testObj.containsValue(1));
        this.testObj.putAll(MutableMap.of("A", MutableSet.of(1, 2, 3, 1)));
        Assertions.assertTrue(this.testObj.containsValue(1));
        Assertions.assertTrue(this.testObj.containsValue(2));
        Assertions.assertTrue(this.testObj.containsValue(3));
        Assertions.assertFalse(this.testObj.containsValue(4));
        this.testObj.putAll(MutableMap.of("B", MutableSet.of(10, 10)));
        Assertions.assertTrue(this.testObj.containsValue(10));
        Assertions.assertFalse(this.testObj.containsValue(4));
    }

    @Test
    public void clear()
    {
        Assertions.assertEquals(0, this.testObj.size());
        this.testObj.clear();
        this.testObj.putAll(MutableMap.of("A", MutableList.of(1, 2, 3, 1)));
        this.testObj.putAll(MutableMap.of("B", MutableList.of(10, 10)));
        Assertions.assertEquals(4, this.testObj.size());
        this.testObj.clear();
        Assertions.assertEquals(0, this.testObj.size());
    }

    @Test
    public void keySet()
    {
        Assertions.assertEquals(new HashSet<>(), this.testObj.keySet());
        this.testObj.putAll(MutableMap.of("A", MutableSet.of(1, 2, 3, 1)));
        this.testObj.putAll(MutableMap.of("B", MutableSet.of(10, 10)));
        Assertions.assertEquals(MutableSet.of("A", "B"), this.testObj.keySet());
    }

    @Test
    public void forEach()
    {
        this.testObj.putAll(MutableMap.of("A", MutableList.of(1, 2, 3, 1)));
        this.testObj.putAll(MutableMap.of("B", MutableList.of(10, 10)));
        MutableList<String> combined = MutableList.empty();
        this.testObj.forEach((key, value) -> combined.add(key + value.toString()));
        Assertions.assertEquals(
                MutableList.of("A1", "A2", "A3", "B10"),
                combined);
    }

    @Test
    public void hashCodeEquals()
    {
        this.testObj.putAll(MutableMap.of("A", MutableList.of(1, 2, 3, 1)));
        this.testObj.putAll(MutableMap.of("B", MutableList.of(10, 10)));

        HashSetMultimap<String, Integer> other = HashSetMultimap.newMultimap();
        other.putAll(MutableMap.of("A", MutableList.of(1, 2, 3, 1)));
        Assertions.assertTrue(this.testObj.equals(this.testObj));
        Assertions.assertFalse(this.testObj.equals(other));
        Assertions.assertFalse(other.equals(this.testObj));

        other.putAll(MutableMap.of("B", MutableList.of(10, 10)));
        Verify.assertEqualsAndHashCode(this.testObj, this.testObj);
        Verify.assertEqualsAndHashCode(this.testObj, other);
        Verify.assertEqualsAndHashCode(other, this.testObj);
    }
}
