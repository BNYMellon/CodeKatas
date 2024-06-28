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

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public interface MutableMultimap<K, V>
{
    int size();

    boolean isEmpty();

    RichIterable<V> get(Object key);

    boolean put(K key, V value);

    boolean put(K key, RichIterable<V> value);

    void putAll(Map<? extends K, ? extends RichIterable<V>> map);

    RichIterable<V> remove(K key);

    boolean remove(K key, V value);

    boolean containsKey(Object key);

    boolean containsValue(Object value);

    Set<K> keySet();

    void clear();

    void forEach(BiConsumer<K, V> biConsumer);
}
