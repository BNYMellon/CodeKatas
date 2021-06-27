/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.deckofcards.custom.collections;

public interface MutableListMultimap<K, V> extends MutableMultimap<K, V>
{
    public static <K1, V1> MutableListMultimap<K1, V1> empty()
    {
        return ArrayListMultimap.newMultimap();
    }

    @Override
    MutableList<V> get(Object key);

    @Override
    MutableList<V> remove(K key);

    MutableListMultimap<K, V> asUnmodifiable();
}
