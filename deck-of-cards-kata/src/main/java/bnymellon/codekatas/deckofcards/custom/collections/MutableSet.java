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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface MutableSet<T> extends MutableCollection<T>, Set<T> {

    public static <E> MutableSet<E> empty() {
        return new HashSet2<>();
    }

    public static <E> MutableSet<E> of(E one) {
        return new HashSet2<E>().with(one);
    }

    public static <E> MutableSet<E> of(E... args) {
        return new HashSet2<E>().withAll(args);
    }

    public static <E> MutableSet<E> fromIterable(Iterable<E> iterable) {
        MutableSet<E> result = MutableSet.empty();
        iterable.forEach(result::add);
        return result;
    }

    public static <E> MutableSet<E> fromStream(Stream<E> stream) {
        MutableSet<E> result = MutableSet.empty();
        stream.forEach(result::add);
        return result;
    }

    default Set<T> asUnmodifiable() {
        return Collections.unmodifiableSet(this);
    }

    @Override
    default MutableSet<T> filter(Predicate<? super T> predicate) {
        MutableSet<T> result = MutableSet.empty();
        this.forEach(each -> {
            if (predicate.test(each)) {
                result.add(each);
            }
        });
        return result;
    }

    @Override
    default MutableSet<T> filterNot(Predicate<? super T> predicate) {
        MutableSet<T> result = MutableSet.empty();
        this.forEach(each -> {
            if (!predicate.test(each)) {
                result.add(each);
            }
        });
        return result;
    }

    @Override
    default <V> MutableSet<V> map(Function<? super T, ? extends V> function) {
        MutableSet<V> result = MutableSet.empty();
        this.forEach(each -> result.add(function.apply(each)));
        return result;
    }

    @Override
    default <V> MutableSet<V> flatMap(Function<? super T, ? extends Iterable<V>> function) {
        MutableSet<V> result = MutableSet.empty();
        this.forEach(each -> result.addAllIterable(function.apply(each)));
        return result;
    }

    default <K, V> MutableMap<K, MutableSet<T>> groupBy(Function<? super T, ? extends K> function) {
        return this.collect(Collectors.groupingBy(function, MutableMap::empty, Collectors.toCollection(MutableSet::empty)));
    }
}
