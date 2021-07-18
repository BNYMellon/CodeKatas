  /**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
*/

package bnymellon.codekatas.deckofcards.custom.collections;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

final class ImmutableEmptySet<T> implements ImmutableSet<T>
{
    static final ImmutableSet<?> INSTANCE = new ImmutableEmptySet<>();

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
    public ImmutableSet<T> filter(Predicate<? super T> predicate)
    {
        return this;
    }

    @Override
    public ImmutableSet<T> filterNot(Predicate<? super T> predicate)
    {
        return this;
    }

    @Override
    public <V> ImmutableSet<V> map(Function<? super T, ? extends V> function)
    {
        return ImmutableSet.empty();
    }

    @Override
    public <V> ImmutableSet<V> flatMap(Function<? super T, ? extends Iterable<V>> function)
    {
        return ImmutableSet.empty();
    }

    @Override
    public Iterator<T> iterator()
    {
        return (Iterator<T>) EmptyIterator.INSTANCE;
    }
}
