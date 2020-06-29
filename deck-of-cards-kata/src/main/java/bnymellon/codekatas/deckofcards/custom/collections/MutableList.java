/*
 * Copyright 2020 The Bank of New York Mellon.
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

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface MutableList<T> extends MutableCollection<T>, List<T> {

    static <E> MutableList<E> empty() {
        return new ArrayList2<>();
    }

    static <E> MutableList<E> of(E one) {
        return new ArrayList2<E>().with(one);
    }

    static <E> MutableList<E> of(E... args) {
        return new ArrayList2<E>().withAll(args);
    }

    static <E> MutableList<E> fromIterable(Iterable<E> iterable) {
        var mutableList = MutableList.<E>empty();
        iterable.forEach(mutableList::add);
        return mutableList;
    }

    static <E> MutableList<E> fromStream(Stream<E> stream) {
        var mutableList = MutableList.<E>empty();
        stream.forEach(mutableList::add);
        return mutableList;
    }

    default MutableList<T> asUnmodifiable() {
        return new UnmodifiableMutableList<>(this);
    }

    @Override
    default MutableList<T> filter(Predicate<? super T> predicate) {
        var mutableList = MutableList.<T>empty();
        for (T each : this) {
            if (predicate.test(each)) {
                mutableList.add(each);
            }
        }
        return mutableList;
    }

    @Override
    default MutableList<T> filterNot(Predicate<? super T> predicate) {
        var mutableList = MutableList.<T>empty();
        for (T each : this) {
            if (!predicate.test(each)) {
                mutableList.add(each);
            }
        }
        return mutableList;
    }

    @Override
    default <V> MutableList<V> map(Function<? super T, ? extends V> function) {
        var mutableList = MutableList.<V>empty();
        this.forEach(each -> mutableList.add(function.apply(each)));
        return mutableList;
    }

    @Override
    default <V> MutableList<V> flatMap(Function<? super T, ? extends Iterable<V>> function) {
        var mutableList = MutableList.<V>empty();
        this.forEach(each -> mutableList.addAllIterable(function.apply(each)));
        return mutableList;
    }

    @Override
    default MutableList<T> peek(Consumer<? super T> consumer) {
        this.forEach(consumer);
        return this;
    }

    default <K> MutableListMultimap<K, T> groupBy(Function<? super T, ? extends K> function) {
        var multimap = MutableListMultimap.<K, T>empty();
        this.forEach(each -> multimap.put(function.apply(each), each));
        return multimap;
    }

    default <K> MutableListMultimap<K, T> groupByUnmodifiable(Function<? super T, ? extends K> function) {
        var multimap = this.<K>groupBy(function);
        return multimap.asUnmodifiable();
    }

    default MutableList<T> shuffle(Random random) {
        Collections.shuffle(this, random);
        return this;
    }
}
