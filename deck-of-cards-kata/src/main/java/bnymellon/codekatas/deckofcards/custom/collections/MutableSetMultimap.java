/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.deckofcards.custom.collections;

public interface MutableSetMultimap<K, V> extends MutableMultimap<K, V>
{
    public static <K1, V1> MutableSetMultimap<K1, V1> empty()
    {
        return HashSetMultimap.newMultimap();
    }

    MutableSet<V> get(Object key);

    MutableSet<V> remove(K key);

    void clear();
}
