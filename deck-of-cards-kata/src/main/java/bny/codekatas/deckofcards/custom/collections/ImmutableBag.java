/*
 * Copyright 2024 The Bank of New York Mellon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package bny.codekatas.deckofcards.custom.collections;

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
