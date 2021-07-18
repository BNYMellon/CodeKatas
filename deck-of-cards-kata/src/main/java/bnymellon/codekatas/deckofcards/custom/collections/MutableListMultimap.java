  /**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
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
