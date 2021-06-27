/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.deckofcards.custom.collections;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

final class ImmutableEmptyBag<T> implements ImmutableBag<T>
{
    static final ImmutableBag<?> INSTANCE = new ImmutableEmptyBag<>();

    @Override
    public int size()
    {
        return 0;
    }

    @Override
    public boolean isEmpty()
    {
        return true;
    }

    @Override
    public int sizeDistinct()
    {
        return 0;
    }

    @Override
    public ImmutableBag<T> filter(Predicate<? super T> predicate)
    {
        return this;
    }

    @Override
    public ImmutableBag<T> filterNot(Predicate<? super T> predicate)
    {
        return this;
    }

    @Override
    public <V> ImmutableBag<V> map(Function<? super T, ? extends V> function)
    {
        return ImmutableBag.empty();
    }

    @Override
    public <V> ImmutableBag<V> flatMap(Function<? super T, ? extends Iterable<V>> function)
    {
        return ImmutableBag.empty();
    }

    @Override
    public int getOccurrences(T element)
    {
        return 0;
    }

    @Override
    public void forEachWithOccurrences(BiConsumer<? super T, Integer> procedure)
    {

    }

    @Override
    public void forEach(Consumer<? super T> action)
    {
    }

    @Override
    public Spliterator<T> spliterator()
    {
        return Spliterators.emptySpliterator();
    }

    @Override
    public Iterator<T> iterator()
    {
        return (Iterator<T>) EmptyIterator.INSTANCE;
    }

    @Override
    public int hashCode()
    {
        return 0;
    }

    @Override
    public boolean equals(Object other)
    {
        if (this == other)
        {
            return true;
        }
        if (other instanceof Bag bag)
        {
            return bag.isEmpty();
        }
        return super.equals(other);
    }
}
