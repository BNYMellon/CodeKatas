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

import org.eclipse.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UnmodifiableArrayListMultimapTest
{
    private UnmodifiableArrayListMultimap<String, Integer> testObj;
    private ArrayListMultimap<String, Integer> inputMultimap;

    @Before
    public void setup()
    {
        this.inputMultimap = new ArrayListMultimap<>();
        this.inputMultimap.putAll(MutableMap.of("A", MutableList.of(1, 2, 3, 1)));
        this.inputMultimap.putAll(MutableMap.of("B", MutableList.of(10, 10)));
        this.testObj = new UnmodifiableArrayListMultimap<>(this.inputMultimap);
    }

    @Test
    public void isConstructed()
    {
        Assert.assertNotNull(new ArrayListMultimap<>());
    }

    @Test
    public void isEmpty()
    {
        this.testObj = new UnmodifiableArrayListMultimap(new ArrayListMultimap());
        Assert.assertTrue(this.testObj.isEmpty());
        this.testObj = new UnmodifiableArrayListMultimap(this.inputMultimap);
        Assert.assertFalse(this.testObj.isEmpty());
    }

    @Test
    public void get()
    {
        MutableList<Integer> value = this.testObj.get("A");
        Assert.assertEquals(MutableList.of(1, 2, 3, 1), value);
        Assert.assertEquals(MutableList.empty(), this.testObj.get("C"));
        Verify.assertThrows(UnsupportedOperationException.class, () -> value.add(6));
    }

    @Test
    public void put()
    {
        Verify.assertThrows(UnsupportedOperationException.class, () -> this.testObj.put("A", 1));
    }

    @Test
    public void get_putIterable()
    {
        Verify.assertThrows(UnsupportedOperationException.class, () -> this.testObj.put("A", MutableList.of(1)));
    }

    @Test
    public void putAll()
    {
        Verify.assertThrows(UnsupportedOperationException.class, () -> this.testObj.putAll(MutableMap.of("A", MutableList.of(1, 2, 3))));
    }

    @Test
    public void remove()
    {
        Verify.assertThrows(UnsupportedOperationException.class, () -> this.testObj.remove("A"));
    }

    @Test
    public void remove_keyValue()
    {
        Verify.assertThrows(UnsupportedOperationException.class, () -> this.testObj.remove("A"));
    }

    @Test
    public void containsKey()
    {
        Assert.assertTrue(this.testObj.containsKey("A"));
        Assert.assertTrue(this.testObj.containsKey("B"));
        Assert.assertFalse(this.testObj.containsKey("C"));
    }

    @Test
    public void containsValue()
    {
        Assert.assertTrue(this.testObj.containsValue(1));
        Assert.assertTrue(this.testObj.containsValue(2));
        Assert.assertTrue(this.testObj.containsValue(3));
        Assert.assertTrue(this.testObj.containsValue(10));
        Assert.assertFalse(this.testObj.containsValue(4));
    }

    @Test
    public void clear()
    {
        Verify.assertThrows(UnsupportedOperationException.class, () -> this.testObj.clear());
    }

    @Test
    public void keySet()
    {
        Assert.assertEquals(MutableSet.of("A", "B"), this.testObj.keySet());
    }

    @Test
    public void forEach()
    {
        MutableList<String> combined = MutableList.empty();
        this.testObj.forEach((key, value) -> combined.add(key + value.toString()));
        Assert.assertEquals(
                MutableList.of("A1", "A2", "A3", "A1", "B10", "B10"),
                combined);
    }
}
