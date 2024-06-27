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
