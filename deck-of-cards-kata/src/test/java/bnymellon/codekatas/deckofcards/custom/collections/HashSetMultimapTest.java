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

import org.eclipse.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

public class HashSetMultimapTest {
    private HashSetMultimap<String, Integer> testObj;

    @Before
    public void setup() {
        this.testObj = new HashSetMultimap<>();
    }

    @Test
    public void isConstructed() {
        Assert.assertNotNull(new HashSetMultimap<>());
    }

    @Test
    public void isEmpty() {
        Assert.assertTrue(this.testObj.isEmpty());
        this.testObj.put("A", 1);
        Assert.assertFalse(this.testObj.isEmpty());
    }

    @Test
    public void get_put() {
        this.testObj.put("A", 1);
        Assert.assertEquals(MutableSet.of(1), this.testObj.get("A"));
        Assert.assertEquals(1, this.testObj.size());
        this.testObj.put("A", 2);
        Assert.assertEquals(MutableSet.of(1, 2), this.testObj.get("A"));
        Assert.assertEquals(2, this.testObj.size());
        this.testObj.put("A", 1);
        Assert.assertEquals(MutableSet.of(1, 2), this.testObj.get("A"));
        Assert.assertEquals(2, this.testObj.size());
        this.testObj.put("B", 10);
        Assert.assertEquals(MutableSet.of(10), this.testObj.get("B"));
        Assert.assertEquals(3, this.testObj.size());
        this.testObj.put("B", 11);
        Assert.assertEquals(MutableSet.of(10, 11), this.testObj.get("B"));
        Assert.assertEquals(4, this.testObj.size());
        this.testObj.put("A", 2);
        Assert.assertEquals(MutableSet.of(1, 2), this.testObj.get("A"));
        Assert.assertEquals(4, this.testObj.size());
    }

    @Test
    public void get_putIterable() {
        this.testObj.put("A", MutableSet.of(1));
        Assert.assertEquals(MutableSet.of(1), this.testObj.get("A"));
        Assert.assertEquals(1, this.testObj.size());
        this.testObj.put("A", MutableSet.of(2, 1));
        Assert.assertEquals(MutableSet.of(1, 2), this.testObj.get("A"));
        Assert.assertEquals(2, this.testObj.size());
        this.testObj.put("B", MutableSet.of(10, 11));
        Assert.assertEquals(MutableSet.of(10, 11), this.testObj.get("B"));
        Assert.assertEquals(4, this.testObj.size());
        this.testObj.put("A", MutableSet.of(2, 3, 4));
        Assert.assertEquals(MutableSet.of(1, 2, 3, 4), this.testObj.get("A"));
        Assert.assertEquals(6, this.testObj.size());
    }

    @Test
    public void putAll() {
        this.testObj.putAll(MutableMap.of("A", MutableList.of(1, 2, 3)));
        Assert.assertEquals(MutableSet.of(1, 2, 3), this.testObj.get("A"));
        Assert.assertEquals(3, this.testObj.size());
        this.testObj.putAll(MutableMap.of("B", MutableList.of(10, 10)));
        Assert.assertEquals(MutableSet.of(10, 10), this.testObj.get("B"));
        Assert.assertEquals(4, this.testObj.size());
    }

    @Test
    public void remove() {
        Assert.assertEquals(MutableSet.empty(), this.testObj.remove("A"));
        this.testObj.putAll(MutableMap.of("A", MutableList.of(1, 2, 3)));
        this.testObj.putAll(MutableMap.of("B", MutableList.of(10, 10)));
        Assert.assertEquals(4, this.testObj.size());
        this.testObj.remove("A");
        Assert.assertEquals(1, this.testObj.size());
        this.testObj.remove("B");
        Assert.assertEquals(0, this.testObj.size());
    }

    @Test
    public void remove_keyValue() {
        Assert.assertEquals(MutableSet.empty(), this.testObj.remove("A"));
        this.testObj.putAll(MutableMap.of("A", MutableList.of(1, 2, 3, 1)));
        this.testObj.putAll(MutableMap.of("B", MutableList.of(10, 10)));
        Assert.assertEquals(4, this.testObj.size());
        this.testObj.remove("A", 1);
        Assert.assertEquals(MutableSet.of(2, 3), this.testObj.get("A"));
        Assert.assertEquals(3, this.testObj.size());
        this.testObj.remove("B", 10);
        MutableSet<Integer> getForB = this.testObj.get("B");
        Assert.assertEquals(MutableSet.empty(), getForB);
        Assert.assertEquals(2, this.testObj.size());
        Assert.assertNotSame(getForB, this.testObj.get("B"));
    }

    @Test
    public void containsKey() {
        Assert.assertFalse(this.testObj.containsKey("A"));
        this.testObj.putAll(MutableMap.of("A", MutableSet.of(1, 2, 3, 1)));
        Assert.assertTrue(this.testObj.containsKey("A"));
        this.testObj.putAll(MutableMap.of("B", MutableSet.of(10, 10)));
        Assert.assertTrue(this.testObj.containsKey("A"));
        Assert.assertTrue(this.testObj.containsKey("B"));
    }

    @Test
    public void containsValue() {
        Assert.assertFalse(this.testObj.containsValue(1));
        this.testObj.putAll(MutableMap.of("A", MutableSet.of(1, 2, 3, 1)));
        Assert.assertTrue(this.testObj.containsValue(1));
        Assert.assertTrue(this.testObj.containsValue(2));
        Assert.assertTrue(this.testObj.containsValue(3));
        Assert.assertFalse(this.testObj.containsValue(4));
        this.testObj.putAll(MutableMap.of("B", MutableSet.of(10, 10)));
        Assert.assertTrue(this.testObj.containsValue(10));
        Assert.assertFalse(this.testObj.containsValue(4));
    }

    @Test
    public void clear() {
        Assert.assertEquals(0, this.testObj.size());
        this.testObj.clear();
        this.testObj.putAll(MutableMap.of("A", MutableList.of(1, 2, 3, 1)));
        this.testObj.putAll(MutableMap.of("B", MutableList.of(10, 10)));
        Assert.assertEquals(4, this.testObj.size());
        this.testObj.clear();
        Assert.assertEquals(0, this.testObj.size());
    }

    @Test
    public void keySet() {
        Assert.assertEquals(new HashSet<>(), this.testObj.keySet());
        this.testObj.putAll(MutableMap.of("A", MutableSet.of(1, 2, 3, 1)));
        this.testObj.putAll(MutableMap.of("B", MutableSet.of(10, 10)));
        Assert.assertEquals(MutableSet.of("A", "B"), this.testObj.keySet());
    }

    @Test
    public void forEach() {
        this.testObj.putAll(MutableMap.of("A", MutableList.of(1, 2, 3, 1)));
        this.testObj.putAll(MutableMap.of("B", MutableList.of(10, 10)));
        MutableList<String> combined = MutableList.empty();
        this.testObj.forEach((key, value) -> combined.add(key + value.toString()));
        Assert.assertEquals(
                MutableList.of("A1", "A2", "A3", "B10"),
                combined);
    }

    @Test
    public void hashCodeEquals() {
        this.testObj.putAll(MutableMap.of("A", MutableList.of(1, 2, 3, 1)));
        this.testObj.putAll(MutableMap.of("B", MutableList.of(10, 10)));

        HashSetMultimap<String, Integer> other = HashSetMultimap.newMultimap();
        other.putAll(MutableMap.of("A", MutableList.of(1, 2, 3, 1)));
        Assert.assertTrue(this.testObj.equals(this.testObj));
        Assert.assertFalse(this.testObj.equals(other));
        Assert.assertFalse(other.equals(this.testObj));

        other.putAll(MutableMap.of("B", MutableList.of(10, 10)));
        Verify.assertEqualsAndHashCode(this.testObj, this.testObj);
        Verify.assertEqualsAndHashCode(this.testObj, other);
        Verify.assertEqualsAndHashCode(other, this.testObj);
    }
}
