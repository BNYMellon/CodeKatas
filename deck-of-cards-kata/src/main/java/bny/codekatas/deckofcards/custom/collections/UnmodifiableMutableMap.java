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

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class UnmodifiableMutableMap<K, V> extends AbstractMap<K, V> implements MutableMap<K, V>
{
    private final MutableMap<K, V> adapted;

    public UnmodifiableMutableMap(MutableMap<K, V> adapted)
    {
        this.adapted = adapted;
    }

    @Override
    public MutableMap<K, V> asUnmodifiable()
    {
        return this;
    }

    @Override
    public V put(K key, V value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(Object key)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public V putIfAbsent(K key, V value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size()
    {
        return adapted.size();
    }

    @Override
    public boolean isEmpty()
    {
        return adapted.isEmpty();
    }

    @Override
    public boolean containsKey(Object key)
    {
        return adapted.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value)
    {
        return adapted.containsValue(value);
    }

    @Override
    public V get(Object key)
    {
        return adapted.get(key);
    }

    @Override
    public Set<K> keySet()
    {
        return adapted.keySet();
    }

    @Override
    public Collection<V> values()
    {
        return adapted.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet()
    {
        return adapted.entrySet();
    }

    @Override
    public boolean equals(Object o)
    {
        return adapted.equals(o);
    }

    @Override
    public int hashCode()
    {
        return adapted.hashCode();
    }

    @Override
    public V getOrDefault(Object key, V defaultValue)
    {
        return adapted.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action)
    {
        adapted.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object key, Object value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public V replace(K key, V value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)
    {
        return adapted.computeIfPresent(key, remappingFunction);
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)
    {
        return adapted.compute(key, remappingFunction);
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction)
    {
        throw new UnsupportedOperationException();
    }
}
