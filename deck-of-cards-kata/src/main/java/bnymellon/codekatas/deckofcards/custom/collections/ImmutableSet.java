/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.deckofcards.custom.collections;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

public sealed interface ImmutableSet<T> extends ImmutableCollection<T>, Set<T>
        permits ImmutableEmptySet, ImmutableHashSet
{
    static <E> ImmutableSet<E> empty()
    {
        return (ImmutableSet<E>) ImmutableEmptySet.INSTANCE;
    }

    static <E> ImmutableSet<E> of(E... args)
    {
        if (args.length == 0)
        {
            return ImmutableSet.empty();
        }
        return MutableSet.of(args).toImmutable();
    }

    static <E> ImmutableSet<E> fromIterable(Iterable<E> iterable)
    {
        if (iterable instanceof ImmutableSet<E> set)
        {
            return set;
        }
        return MutableSet.fromIterable(iterable).toImmutable();
    }

    static <E> ImmutableSet<E> fromStream(Stream<E> stream)
    {
        return MutableSet.fromStream(stream).toImmutable();
    }

    static <E> Collector<E, MutableSet<E>, ImmutableSet<E>> collector()
    {
        return Collector.of(
                MutableSet::empty,
                MutableSet::add,
                MutableSet::withAll,
                MutableSet::toImmutable);
    }

    @Override
    ImmutableSet<T> filter(Predicate<? super T> predicate);

    @Override
    ImmutableSet<T> filterNot(Predicate<? super T> predicate);

    @Override
    <V> ImmutableSet<V> map(Function<? super T, ? extends V> function);

    @Override
    <V> ImmutableSet<V> flatMap(Function<? super T, ? extends Iterable<V>> function);

    @Override
    default ImmutableSet<T> peek(Consumer<? super T> consumer)
    {
        this.forEach(consumer);
        return this;
    }

    default <K, V> MutableSetMultimap<K, T> groupBy(Function<? super T, ? extends K> function)
    {
        var multimap = MutableSetMultimap.<K, T>empty();
        this.forEach(each -> multimap.put(function.apply(each), each));
        return multimap;
    }
}
