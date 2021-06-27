/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
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
