  /**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
*/

package bnymellon.codekatas.deckofcards.custom.collections;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

public sealed interface ImmutableList<T> extends ImmutableCollection<T>, List<T>
        permits ImmutableEmptyList, ImmutableArrayList
{
    static <E> ImmutableList<E> empty()
    {
        return (ImmutableList<E>) ImmutableEmptyList.INSTANCE;
    }

    static <E> ImmutableList<E> of(E... args)
    {
        if (args.length == 0)
        {
            return ImmutableList.empty();
        }
        return MutableList.of(args).toImmutable();
    }

    static <E> ImmutableList<E> fromIterable(Iterable<E> iterable)
    {
        if (iterable instanceof ImmutableList<E> list)
        {
            return list;
        }
        return MutableList.fromIterable(iterable).toImmutable();
    }

    static <E> ImmutableList<E> fromStream(Stream<E> stream)
    {
        return MutableList.fromStream(stream).toImmutable();
    }

    static <E> Collector<E, MutableList<E>, ImmutableList<E>> collector()
    {
        return Collector.of(
                MutableList::empty,
                MutableList::add,
                MutableList::withAll,
                MutableList::toImmutable);
    }

    @Override
    ImmutableList<T> filter(Predicate<? super T> predicate);

    @Override
    ImmutableList<T> filterNot(Predicate<? super T> predicate);

    @Override
    <V> ImmutableList<V> map(Function<? super T, ? extends V> function);

    @Override
    <V> ImmutableList<V> flatMap(Function<? super T, ? extends Iterable<V>> function);

    @Override
    default ImmutableList<T> peek(Consumer<? super T> consumer)
    {
        this.forEach(consumer);
        return this;
    }

    default <K> MutableListMultimap<K, T> groupBy(Function<? super T, ? extends K> function)
    {
        var multimap = MutableListMultimap.<K, T>empty();
        this.forEach(each -> multimap.put(function.apply(each), each));
        return multimap;
    }

    default <K> MutableListMultimap<K, T> groupByUnmodifiable(Function<? super T, ? extends K> function)
    {
        var multimap = this.<K>groupBy(function);
        return multimap.asUnmodifiable();
    }
}
