  /**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
*/

package bnymellon.codekatas.deckofcards.custom.collections;

public class ArrayListMultimap<K, V>
        extends AbstractMutableMultimap<K, V, MutableList<V>>
        implements MutableListMultimap<K, V>
{
    private final MutableMap<K, MutableList<V>> BACKING_MAP = MutableMap.empty();
    private int size;

    public static <K, V> ArrayListMultimap<K, V> newMultimap()
    {
        return new ArrayListMultimap<>();
    }

    @Override
    protected MutableMap<K, MutableList<V>> getBackingMap()
    {
        return BACKING_MAP;
    }

    @Override
    protected void incrementSizeBy(int increment)
    {
        this.size += increment;
    }

    @Override
    protected void decrementSizeBy(int decrement)
    {
        this.size -= decrement;
    }

    @Override
    protected MutableList<V> createEmptyValueCollection()
    {
        return MutableList.empty();
    }

    @Override
    public int size()
    {
        return this.size;
    }

    public MutableListMultimap<K, V> asUnmodifiable()
    {
        return new UnmodifiableArrayListMultimap<>(this);
    }
}
