/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.deckofcards.custom.collections;

import java.util.AbstractList;
import java.util.Collection;
import java.util.stream.Stream;

public class UnmodifiableMutableList<T> extends AbstractList<T> implements MutableList<T>
{
    private final MutableList<T> adapted;

    public UnmodifiableMutableList(MutableList<T> adapted)
    {
        this.adapted = adapted;
    }

    @Override
    public MutableList<T> asUnmodifiable()
    {
        return this;
    }

    @Override
    public T get(int index)
    {
        return this.adapted.get(index);
    }

    @Override
    public int size()
    {
        return this.adapted.size();
    }

    @Override
    public boolean add(T t)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public T set(int index, T element)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<T> stream()
    {
        return this.adapted.stream();
    }
}
