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
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface MutableList<T> extends MutableCollection<T>, List<T> {

    public static <E> MutableList<E> empty() {
        return new ArrayList2<>();
    }

    public static <E> MutableList<E> of(E one) {
        return new ArrayList2<E>().with(one);
    }

    public static <E> MutableList<E> of(E... args) {
        return new ArrayList2<E>().withAll(args);
    }

    public static <E> MutableList<E> fromIterable(Iterable<E> iterable) {
        var mutableList = MutableList.<E>empty();
        iterable.forEach(mutableList::add);
        return mutableList;
    }

    public static <E> MutableList<E> fromStream(Stream<E> stream) {
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
        for (T each : this) {
            mutableList.add(function.apply(each));
        }
        return mutableList;
    }

    @Override
    default <V> MutableList<V> flatMap(Function<? super T, ? extends Iterable<V>> function) {
        var mutableList = MutableList.<V>empty();
        for (T each : this) {
            mutableList.addAllIterable(function.apply(each));
        }
        return mutableList;
    }

    default <K, V> MutableMap<K, MutableList<T>> groupBy(Function<? super T, ? extends K> function) {
        var mutableMap = MutableMap.<K, MutableList<T>>empty();
        for (T each : this) {
            K key = function.apply(each);
            mutableMap.getIfAbsentPut(key, MutableList::empty)
                    .add(each);
        }
        return mutableMap;
    }

    default <K, V> MutableMap<K, MutableList<T>> groupByUnmodifiable(Function<? super T, ? extends K> function) {
        var mutableMap = this.<K, MutableList<T>>groupBy(function);
        mutableMap.replaceAll((k, ts) -> new UnmodifiableMutableList<>(ts));
        return mutableMap.asUnmodifiable();
    }

    default MutableList<T> shuffle(Random random) {
        Collections.shuffle(this, random);
        return this;
    }
}
