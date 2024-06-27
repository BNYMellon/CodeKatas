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
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

public interface MutableBag<T> extends MutableCollection<T>, Bag<T>
{
    default ImmutableBag<T> toImmutable()
    {
        if (this.isEmpty())
        {
            return ImmutableBag.empty();
        }
        return new ImmutableHashBag<>(this);
    }

    int sizeDistinct();

    static <E> MutableBag<E> empty()
    {
        return new HashBag<>();
    }

    static <E> MutableBag<E> of(E... args)
    {
        return new HashBag<E>().withAll(args);
    }

    static <E> MutableBag<E> fromIterable(Iterable<E> iterable)
    {
        var mutableBag = MutableBag.<E>empty();
        iterable.forEach(mutableBag::add);
        return mutableBag;
    }

    static <E> MutableBag<E> fromStream(Stream<E> stream)
    {
        var mutableBag = MutableBag.<E>empty();
        stream.forEach(mutableBag::add);
        return mutableBag;
    }

    static <E> Collector<E, ?, MutableBag<E>> collector()
    {
        return Collector.of(
                MutableBag::empty,
                MutableBag::add,
                MutableBag::withAll);
    }

    @Override
    default MutableBag<T> peek(Consumer<? super T> consumer)
    {
        this.forEach(consumer);
        return this;
    }

    @Override
    default MutableBag<T> filter(Predicate<? super T> predicate)
    {
        var mutableBag = MutableBag.<T>empty();
        return this.filter(predicate, mutableBag);
    }

    @Override
    default MutableBag<T> filterNot(Predicate<? super T> predicate)
    {
        return this.filter(predicate.negate());
    }

    @Override
    default <V> MutableBag<V> map(Function<? super T, ? extends V> function)
    {
        var mutableBag = MutableBag.<V>empty();
        return this.map(function, mutableBag);
    }

    @Override
    default <V> MutableBag<V> flatMap(Function<? super T, ? extends Iterable<V>> function)
    {
        var mutableBag = MutableBag.<V>empty();
        return this.flatMap(function, mutableBag);
    }

    default <K> ArrayListMultimap<K, T> groupBy(Function<? super T, ? extends K> function)
    {
        throw new UnsupportedOperationException("groupBy is not supported yet");
    }

    default <K> UnmodifiableArrayListMultimap<K, T> groupByUnmodifiable(Function<? super T, ? extends K> function)
    {
        throw new UnsupportedOperationException("groupBy is not supported yet");
    }

    int getOccurrences(T element);

    boolean addOccurrence(T element);

    boolean addOccurrences(T element, int occurrences);

    boolean removeOccurrence(T element);

    boolean removeOccurrences(T element, int occurrences);

    void forEachWithOccurrences(BiConsumer<? super T, Integer> procedure);

    default MutableBag<T> withAll(Collection<? extends T> collection)
    {
        this.addAll(collection);
        return this;
    }

    default MutableBag<T> withAll(T... elements)
    {
        for (T element : elements)
        {
            this.addOccurrence(element);
        }
        return this;
    }
}
