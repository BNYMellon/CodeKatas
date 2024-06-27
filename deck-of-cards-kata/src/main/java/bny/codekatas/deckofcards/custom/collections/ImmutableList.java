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
