/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
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
