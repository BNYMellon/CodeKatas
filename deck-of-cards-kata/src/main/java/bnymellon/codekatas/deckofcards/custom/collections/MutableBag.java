/*
 * Copyright 2020 BNY Mellon.
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
package bnymellon.codekatas.deckofcards.custom.collections;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface MutableBag<T> extends MutableCollection<T> {

    int sizeDistinct();

    static <E> MutableBag<E> empty() {
        return new HashBag<>();
    }

    static <E> MutableBag<E> of(E one) {
        return new HashBag<E>().withAll(one);
    }

    static <E> MutableBag<E> of(E... args) {
        return new HashBag<E>().withAll(args);
    }

    static <E> MutableBag<E> fromIterable(Iterable<E> iterable) {
        var mutableBag = MutableBag.<E>empty();
        iterable.forEach(mutableBag::add);
        return mutableBag;
    }

    static <E> MutableBag<E> fromStream(Stream<E> stream) {
        var mutableBag = MutableBag.<E>empty();
        stream.forEach(mutableBag::add);
        return mutableBag;
    }

    @Override
    default MutableBag<T> peek(Consumer<? super T> consumer) {
        this.forEach(consumer);
        return this;
    }

    @Override
    default MutableBag<T> filter(Predicate<? super T> predicate) {
        var mutableBag = MutableBag.<T>empty();
        for (T each : this) {
            if (predicate.test(each)) {
                mutableBag.add(each);
            }
        }
        return mutableBag;
    }

    @Override
    default MutableBag<T> filterNot(Predicate<? super T> predicate) {
        var mutableBag = MutableBag.<T>empty();
        for (T each : this) {
            if (!predicate.test(each)) {
                mutableBag.add(each);
            }
        }
        return mutableBag;
    }

    @Override
    default <V> MutableBag<V> map(Function<? super T, ? extends V> function) {
        var mutableBag = MutableBag.<V>empty();
        for (T each : this) {
            mutableBag.add(function.apply(each));
        }
        return mutableBag;
    }

    @Override
    default <V> MutableBag<V> flatMap(Function<? super T, ? extends Iterable<V>> function) {
        var mutableBag = MutableBag.<V>empty();
        for (T each : this) {
            mutableBag.addAllIterable(function.apply(each));
        }
        return mutableBag;
    }

    default <K> ArrayListMultimap<K, T> groupBy(Function<? super T, ? extends K> function) {
        throw new UnsupportedOperationException("groupBy is not supported yet");
    }

    default <K> UnmodifiableArrayListMultimap<K, T> groupByUnmodifiable(Function<? super T, ? extends K> function) {
        throw new UnsupportedOperationException("groupBy is not supported yet");
    }

    int getOccurrences(T element);

    boolean addOccurrence(T element);

    boolean addOccurrences(T element, int occurrences);

    boolean removeOccurrence(T element);

    boolean removeOccurrences(T element, int occurrences);

    void forEachWithOccurrences(BiConsumer<? super T, Integer> procedure);
}
