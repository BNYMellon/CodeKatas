  /**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
*/

package bnymellon.codekatas.deckofcards.custom.collections;

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
