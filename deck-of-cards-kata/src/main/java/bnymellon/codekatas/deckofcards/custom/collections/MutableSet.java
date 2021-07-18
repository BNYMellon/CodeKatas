  /**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
*/

package bnymellon.codekatas.deckofcards.custom.collections;

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
        for (T each : this)
        {
            if (predicate.test(each))
            {
                mutableSet.add(each);
            }
        }
        return mutableSet;
    }

    @Override
    default MutableSet<T> filterNot(Predicate<? super T> predicate)
    {
        var mutableSet = MutableSet.<T>empty();
        for (T each : this)
        {
            if (!predicate.test(each))
            {
                mutableSet.add(each);
            }
        }
        return mutableSet;
    }

    @Override
    default <V> MutableSet<V> map(Function<? super T, ? extends V> function)
    {
        var mutableSet = MutableSet.<V>empty();
        this.forEach(each -> mutableSet.add(function.apply(each)));
        return mutableSet;
    }

    @Override
    default <V> MutableSet<V> flatMap(Function<? super T, ? extends Iterable<V>> function)
    {
        var mutableSet = MutableSet.<V>empty();
        this.forEach(each -> mutableSet.addAllIterable(function.apply(each)));
        return mutableSet;
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
