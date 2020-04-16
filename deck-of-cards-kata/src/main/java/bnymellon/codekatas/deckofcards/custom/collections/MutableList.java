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
import java.util.stream.Collectors;
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
        MutableList<E> result = MutableList.empty();
        iterable.forEach(result::add);
        return result;
    }

    public static <E> MutableList<E> fromStream(Stream<E> stream) {
        MutableList<E> result = MutableList.empty();
        stream.forEach(result::add);
        return result;
    }

    default MutableList<T> asUnmodifiable() {
        return new UnmodifiableMutableList<>(this);
    }

    @Override
    default MutableList<T> filter(Predicate<? super T> predicate) {
        MutableList<T> result = MutableList.empty();
        this.forEach(each -> {
            if (predicate.test(each)) {
                result.add(each);
            }
        });
        return result;
    }

    @Override
    default MutableList<T> filterNot(Predicate<? super T> predicate) {
        MutableList<T> result = MutableList.empty();
        this.forEach(each -> {
            if (!predicate.test(each)) {
                result.add(each);
            }
        });
        return result;
    }

    @Override
    default <V> MutableList<V> map(Function<? super T, ? extends V> function) {
        MutableList<V> result = MutableList.empty();
        this.forEach(each -> result.add(function.apply(each)));
        return result;
    }

    @Override
    default <V> MutableList<V> flatMap(Function<? super T, ? extends Iterable<V>> function) {
        MutableList<V> result = MutableList.empty();
        this.forEach(each -> result.addAllIterable(function.apply(each)));
        return result;
    }

    default <K, V> MutableMap<K, MutableList<T>> groupBy(Function<? super T, ? extends K> function) {
        return this.collect(Collectors.groupingBy(function, MutableMap::empty, Collectors.toCollection(MutableList::empty)));
    }

    default <K, V> MutableMap<K, MutableList<T>> groupByUnmodifiable(Function<? super T, ? extends K> function) {
        MutableMap<K, MutableList<T>> mutable = this.groupBy(function);
        mutable.replaceAll((k, ts) -> new UnmodifiableMutableList<>(ts));
        return mutable.asUnmodifiable();
    }

    default MutableList<T> shuffle(Random random) {
        Collections.shuffle(this, random);
        return this;
    }
}
