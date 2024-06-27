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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UnmodifiableArrayListMultimapTest
{
    private UnmodifiableArrayListMultimap<String, Integer> testObj;
    private ArrayListMultimap<String, Integer> inputMultimap;

    @BeforeEach
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
        Assertions.assertNotNull(new ArrayListMultimap<>());
    }

    @Test
    public void isEmpty()
    {
        this.testObj = new UnmodifiableArrayListMultimap(new ArrayListMultimap());
        Assertions.assertTrue(this.testObj.isEmpty());
        this.testObj = new UnmodifiableArrayListMultimap(this.inputMultimap);
        Assertions.assertFalse(this.testObj.isEmpty());
    }

    @Test
    public void get()
    {
        MutableList<Integer> value = this.testObj.get("A");
        Assertions.assertEquals(MutableList.of(1, 2, 3, 1), value);
        Assertions.assertEquals(MutableList.empty(), this.testObj.get("C"));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> value.add(6));
    }

    @Test
    public void put()
    {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> this.testObj.put("A", 1));
    }

    @Test
    public void get_putIterable()
    {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> this.testObj.put("A", MutableList.of(1)));
    }

    @Test
    public void putAll()
    {
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> this.testObj.putAll(MutableMap.of("A", MutableList.of(1, 2, 3))));
    }

    @Test
    public void remove()
    {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> this.testObj.remove("A"));
    }

    @Test
    public void remove_keyValue()
    {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> this.testObj.remove("A"));
    }

    @Test
    public void containsKey()
    {
        Assertions.assertTrue(this.testObj.containsKey("A"));
        Assertions.assertTrue(this.testObj.containsKey("B"));
        Assertions.assertFalse(this.testObj.containsKey("C"));
    }

    @Test
    public void containsValue()
    {
        Assertions.assertTrue(this.testObj.containsValue(1));
        Assertions.assertTrue(this.testObj.containsValue(2));
        Assertions.assertTrue(this.testObj.containsValue(3));
        Assertions.assertTrue(this.testObj.containsValue(10));
        Assertions.assertFalse(this.testObj.containsValue(4));
    }

    @Test
    public void clear()
    {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> this.testObj.clear());
    }

    @Test
    public void keySet()
    {
        Assertions.assertEquals(MutableSet.of("A", "B"), this.testObj.keySet());
    }

    @Test
    public void forEach()
    {
        MutableList<String> combined = MutableList.empty();
        this.testObj.forEach((key, value) -> combined.add(key + value.toString()));
        Assertions.assertEquals(
                MutableList.of("A1", "A2", "A3", "A1", "B10", "B10"),
                combined);
    }
}
