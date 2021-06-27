/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.deckofcards.custom.collections;

import java.util.AbstractList;
import java.util.Collection;
import java.util.RandomAccess;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

final class ImmutableArrayList<T>
        extends AbstractList<T>
        implements RandomAccess, ImmutableList<T>
{
    private final ArrayList2<T> arrayList;

    public ImmutableArrayList(Collection<T> collection)
    {
        this.arrayList = new ArrayList2<>(collection);
    }

    @Override
    public ImmutableList<T> filter(Predicate<? super T> predicate)
    {
        return this.arrayList
                .filter(predicate)
                .toImmutable();
    }

    @Override
    public ImmutableList<T> filterNot(Predicate<? super T> predicate)
    {
        return this.arrayList
                .filterNot(predicate)
                .toImmutable();
    }

    @Override
    public <V> ImmutableList<V> map(Function<? super T, ? extends V> function)
    {
        return this.arrayList
                .<V>map(function)
                .toImmutable();
    }

    @Override
    public <V> ImmutableList<V> flatMap(Function<? super T, ? extends Iterable<V>> function)
    {
        return this.arrayList
                .flatMap(function)
                .toImmutable();
    }

    @Override
    public int size()
    {
        return this.arrayList.size();
    }

    @Override
    public T get(int index)
    {
        return this.arrayList.get(index);
    }

    @Override
    public Stream<T> stream()
    {
        return this.arrayList.stream();
    }
}
