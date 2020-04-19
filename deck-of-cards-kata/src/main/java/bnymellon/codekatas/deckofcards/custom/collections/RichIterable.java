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

import java.util.Collection;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

public interface RichIterable<T> extends Iterable<T> {

    RichIterable<T> filter(Predicate<? super T> predicate);

    RichIterable<T> filterNot(Predicate<? super T> predicate);

    <V> RichIterable<V> map(Function<? super T, ? extends V> function);

    <V> RichIterable<V> flatMap(Function<? super T, ? extends Iterable<V>> function);

    default Optional<T> reduce(BinaryOperator<T> accumulator) {
        T result = null;
        for (T each : this) {
            if (result == null) {
                result = each;
            } else {
                result = accumulator.apply(result, each);
            }
        }
        return result == null ? Optional.empty() : Optional.of(result);
    }

    default boolean anyMatch(Predicate<? super T> predicate) {
        for (T each : this) {
            if (predicate.test(each)) {
                return true;
            }
        }
        return false;
    }

    default boolean allMatch(Predicate<? super T> predicate) {
        for (T each : this) {
            if (!predicate.test(each)) {
                return false;
            }
        }
        return true;
    }

    default boolean noneMatch(Predicate<? super T> predicate) {
        for (T each : this) {
            if (predicate.test(each)) {
                return false;
            }
        }
        return true;
    }

    default int count(Predicate<? super T> predicate) {
        int count = 0;
        for (T each : this) {
            if (predicate.test(each)) {
                count++;
            }
        }
        return count;
    }

    default Optional<T> findFirst(Predicate<? super T> predicate) {
        for (T each : this) {
            if (predicate.test(each)) {
                return Optional.of(each);
            }
        }
        return Optional.empty();
    }

    default <R, A> R collect(Collector<? super T, A, R> collector) {
        A mutableResult = collector.supplier().get();
        BiConsumer<A, ? super T> accumulator = collector.accumulator();
        for (T each : this) {
            accumulator.accept(mutableResult, each);
        }
        return collector.finisher().apply(mutableResult);
    }

    default <K> MutableMap<K, Long> countBy(Function<? super T, ? extends K> function) {
        MutableMap<K, Long> counts = MutableMap.empty();
        for (T each : this) {
            K key = function.apply(each);
            Long value = counts.get(key);
            if (value == null) {
                value = 0L;
            }
            counts.put(key, value + 1L);
        }
        return counts;
    }

    default MutableList<T> toList() {
        return this.toCollection(MutableList::empty, Collection::add);
    }

    default MutableSet<T> toSet() {
        return this.toCollection(MutableSet::empty, Collection::add);
    }

    default <R extends Collection<T>> R toCollection(Supplier<R> supplier, BiConsumer<R, T> addMethod) {
        R collection = supplier.get();
        for (T each : this) {
            addMethod.accept(collection, each);
        }
        return collection;
    }
}
