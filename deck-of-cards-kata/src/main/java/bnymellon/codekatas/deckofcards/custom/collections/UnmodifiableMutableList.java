  /**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
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
