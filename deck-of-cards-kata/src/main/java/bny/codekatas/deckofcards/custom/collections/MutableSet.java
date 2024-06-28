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

import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

public interface MutableSet<T> extends MutableCollection<T>, java.util.Set<T>, Set<T>
{
    static <E> MutableSet<E> empty()
    {
        return new HashSet2<>();
    }

    static <E> MutableSet<E> of(E one)
    {
        return new HashSet2<E>().with(one);
    }

    static <E> MutableSet<E> of(E... args)
    {
        return new HashSet2<E>().withAll(args);
    }

    static <E> MutableSet<E> fromIterable(Iterable<E> iterable)
    {
        var mutableSet = MutableSet.<E>empty();
        iterable.forEach(mutableSet::add);
        return mutableSet;
    }

    static <E> MutableSet<E> fromStream(Stream<E> stream)
    {
        var mutableSet = MutableSet.<E>empty();
        stream.forEach(mutableSet::add);
        return mutableSet;
    }

    static <E> Collector<E, ?, MutableSet<E>> collector()
    {
        return Collector.of(
                MutableSet::empty,
                MutableSet::add,
                MutableSet::withAll);
    }

    @Override
    default MutableSet<T> peek(Consumer<? super T> consumer)
    {
        this.forEach(consumer);
        return this;
    }

    default java.util.Set<T> asUnmodifiable()
    {
        return Collections.unmodifiableSet(this);
    }

    @Override
    default MutableSet<T> filter(Predicate<? super T> predicate)
    {
        var mutableSet = MutableSet.<T>empty();
        return this.filter(predicate, mutableSet);
    }

    @Override
    default MutableSet<T> filterNot(Predicate<? super T> predicate)
    {
        return this.filter(predicate.negate());
    }

    @Override
    default <V> MutableSet<V> map(Function<? super T, ? extends V> function)
    {
        var mutableSet = MutableSet.<V>empty();
        return this.map(function, mutableSet);
    }

    @Override
    default <V> MutableSet<V> flatMap(Function<? super T, ? extends Iterable<V>> function)
    {
        var mutableSet = MutableSet.<V>empty();
        return this.flatMap(function, mutableSet);
    }

    default <K, V> MutableSetMultimap<K, T> groupBy(Function<? super T, ? extends K> function)
    {
        var multimap = MutableSetMultimap.<K, T>empty();
        this.forEach(each -> multimap.put(function.apply(each), each));
        return multimap;
    }

    default ImmutableSet<T> toImmutable()
    {
        if (this.isEmpty())
        {
            return ImmutableSet.empty();
        }
        return new ImmutableHashSet<>(this);
    }

    default MutableSet<T> with(T value)
    {
        this.add(value);
        return this;
    }

    default MutableSet<T> withAll(Collection<? extends T> collection)
    {
        this.addAll(collection);
        return this;
    }

    default MutableSet<T> withAll(T... args)
    {
        Collections.addAll(this, args);
        return this;
    }
}
