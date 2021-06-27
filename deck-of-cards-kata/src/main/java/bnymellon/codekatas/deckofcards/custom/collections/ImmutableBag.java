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

public sealed interface ImmutableBag<T> extends ImmutableCollection<T>, Bag<T>
        permits ImmutableEmptyBag, ImmutableHashBag
{
    static <E> ImmutableBag<E> empty()
    {
        return (ImmutableBag<E>) ImmutableEmptyBag.INSTANCE;
    }

    static <E> ImmutableBag<E> of(E... args)
    {
        if (args.length == 0)
        {
            return ImmutableBag.empty();
        }
        return MutableBag.of(args).toImmutable();
    }

    static <E> ImmutableBag<E> fromIterable(Iterable<E> iterable)
    {
        if (iterable instanceof ImmutableBag<E> bag)
        {
            return bag;
        }
        return MutableBag.fromIterable(iterable).toImmutable();
    }

    static <E> ImmutableBag<E> fromStream(Stream<E> stream)
    {
        return MutableBag.fromStream(stream).toImmutable();
    }

    static <E> Collector<E, MutableBag<E>, ImmutableBag<E>> collector()
    {
        return Collector.of(
                MutableBag::empty,
                MutableBag::add,
                MutableBag::withAll,
                MutableBag::toImmutable);
    }

    @Override
    ImmutableBag<T> filter(Predicate<? super T> predicate);

    @Override
    ImmutableBag<T> filterNot(Predicate<? super T> predicate);

    @Override
    <V> ImmutableBag<V> map(Function<? super T, ? extends V> function);

    @Override
    <V> ImmutableBag<V> flatMap(Function<? super T, ? extends Iterable<V>> function);

    @Override
    default ImmutableBag<T> peek(Consumer<? super T> consumer)
    {
        this.forEach(consumer);
        return this;
    }
}
