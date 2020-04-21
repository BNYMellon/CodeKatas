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

import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface MutableSet<T> extends MutableCollection<T>, Set<T> {

    static <E> MutableSet<E> empty() {
        return new HashSet2<>();
    }

    static <E> MutableSet<E> of(E one) {
        return new HashSet2<E>().with(one);
    }

    static <E> MutableSet<E> of(E... args) {
        return new HashSet2<E>().withAll(args);
    }

    static <E> MutableSet<E> fromIterable(Iterable<E> iterable) {
        var mutableSet = MutableSet.<E>empty();
        iterable.forEach(mutableSet::add);
        return mutableSet;
    }

    static <E> MutableSet<E> fromStream(Stream<E> stream) {
        var mutableSet = MutableSet.<E>empty();
        stream.forEach(mutableSet::add);
        return mutableSet;
    }

    default Set<T> asUnmodifiable() {
        return Collections.unmodifiableSet(this);
    }

    @Override
    default MutableSet<T> filter(Predicate<? super T> predicate) {
        var mutableSet = MutableSet.<T>empty();
        for (T each : this) {
            if (predicate.test(each)) {
                mutableSet.add(each);
            }
        }
        return mutableSet;
    }

    @Override
    default MutableSet<T> filterNot(Predicate<? super T> predicate) {
        var mutableSet = MutableSet.<T>empty();
        for (T each : this) {
            if (!predicate.test(each)) {
                mutableSet.add(each);
            }
        }
        return mutableSet;
    }

    @Override
    default <V> MutableSet<V> map(Function<? super T, ? extends V> function) {
        var mutableSet = MutableSet.<V>empty();
        for (T each : this) {
            mutableSet.add(function.apply(each));
        }
        return mutableSet;
    }

    @Override
    default <V> MutableSet<V> flatMap(Function<? super T, ? extends Iterable<V>> function) {
        var mutableSet = MutableSet.<V>empty();
        for (T each : this) {
            mutableSet.addAllIterable(function.apply(each));
        }
        return mutableSet;
    }

    default <K, V> MutableSetMultimap<K, T> groupBy(Function<? super T, ? extends K> function) {
        var multimap = MutableSetMultimap.<K, T>empty();
        for (T each : this) {
            K key = function.apply(each);
            multimap.put(key, each);
        }
        return multimap;
    }
}
