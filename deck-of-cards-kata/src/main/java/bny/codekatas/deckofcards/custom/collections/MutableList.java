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
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

public interface MutableList<T> extends MutableCollection<T>, java.util.List<T>, List<T>
{
    static <E> MutableList<E> empty()
    {
        return new ArrayList2<>();
    }

    default ImmutableList<T> toImmutable()
    {
        if (this.isEmpty())
        {
            return ImmutableList.empty();
        }
        return new ImmutableArrayList<>(this);
    }

    static <E> MutableList<E> of(E one)
    {
        return new ArrayList2<E>().with(one);
    }

    static <E> MutableList<E> of(E... args)
    {
        return new ArrayList2<E>().withAll(args);
    }

    static <E> MutableList<E> fromIterable(Iterable<E> iterable)
    {
        var mutableList = MutableList.<E>empty();
        iterable.forEach(mutableList::add);
        return mutableList;
    }

    static <E> MutableList<E> fromStream(Stream<E> stream)
    {
        var mutableList = MutableList.<E>empty();
        stream.forEach(mutableList::add);
        return mutableList;
    }

    static <E> Collector<E, ?, MutableList<E>> collector()
    {
        return Collector.of(
                MutableList::empty,
                MutableList::add,
                MutableList::withAll);
    }

    default MutableList<T> asUnmodifiable()
    {
        return new UnmodifiableMutableList<>(this);
    }

    @Override
    default MutableList<T> filter(Predicate<? super T> predicate)
    {
        var mutableList = MutableList.<T>empty();
        return this.filter(predicate, mutableList);
    }

    @Override
    default MutableList<T> filterNot(Predicate<? super T> predicate)
    {
        return this.filter(predicate.negate());
    }

    @Override
    default <V> MutableList<V> map(Function<? super T, ? extends V> function)
    {
        var mutableList = MutableList.<V>empty();
        return this.map(function, mutableList);
    }

    @Override
    default <V> MutableList<V> flatMap(Function<? super T, ? extends Iterable<V>> function)
    {
        var mutableList = MutableList.<V>empty();
        return this.flatMap(function, mutableList);
    }

    @Override
    default MutableList<T> peek(Consumer<? super T> consumer)
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

    default <K> MutableListMultimap<K, T> groupByEach(Function<? super T, ? extends Iterable<K>> function)
    {
        var multimap = MutableListMultimap.<K, T>empty();
        this.forEach(each -> function.apply(each).forEach(key -> multimap.put(key, each)));
        return multimap;
    }

    default <K> MutableListMultimap<K, T> groupByUnmodifiable(Function<? super T, ? extends K> function)
    {
        var multimap = this.<K>groupBy(function);
        return multimap.asUnmodifiable();
    }

    default MutableList<T> shuffle(Random random)
    {
        Collections.shuffle(this, random);
        return this;
    }

    default MutableList<T> with(T value)
    {
        this.add(value);
        return this;
    }

    default MutableList<T> withAll(Collection<? extends T> collection)
    {
        this.addAll(collection);
        return this;
    }

    default MutableList<T> withAll(T... args)
    {
        Collections.addAll(this, args);
        return this;
    }
}
