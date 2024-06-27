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
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public interface RichIterable<T> extends Iterable<T>
{
    int size();

    boolean isEmpty();

    default boolean notEmpty()
    {
        return !this.isEmpty();
    }

    default Stream<T> stream()
    {
        throw new UnsupportedOperationException();
    }

    RichIterable<T> filter(Predicate<? super T> predicate);

    default <R extends Collection<T>> R filter(Predicate<? super T> predicate, R target)
    {
        this.forEach(each ->
        {
            if (predicate.test(each))
            {
                target.add(each);
            }
        });
        return target;
    }

    RichIterable<T> filterNot(Predicate<? super T> predicate);

    default <R extends Collection<T>> R filterNot(Predicate<? super T> predicate, R target)
    {
        return this.filter(predicate.negate(), target);
    }

    <V> RichIterable<V> map(Function<? super T, ? extends V> function);

    default <V, R extends Collection<V>> R map(Function<? super T, ? extends V> function, R target)
    {
        this.forEach(each -> target.add(function.apply(each)));
        return target;
    }

    <V> RichIterable<V> flatMap(Function<? super T, ? extends Iterable<V>> function);

    default <V, R extends MutableCollection<V>> R flatMap(Function<? super T, ? extends Iterable<V>> function, R target)
    {
        this.forEach(each -> target.addAllIterable(function.apply(each)));
        return target;
    }

    default RichIterable<T> peek(Consumer<? super T> consumer)
    {
        this.forEach(consumer);
        return this;
    }

    default Optional<T> reduce(BinaryOperator<T> accumulator)
    {
        T result = null;
        for (T each : this)
        {
            if (result == null)
            {
                result = each;
            }
            else
            {
                result = accumulator.apply(result, each);
            }
        }
        return result == null ? Optional.empty() : Optional.of(result);
    }

    default boolean anyMatch(Predicate<? super T> predicate)
    {
        for (T each : this)
        {
            if (predicate.test(each))
            {
                return true;
            }
        }
        return false;
    }

    default <V> boolean containsBy(Function<? super T, ? extends V> function, V value)
    {
        return this.anyMatch(each -> value.equals(function.apply(each)));
    }

    default boolean allMatch(Predicate<? super T> predicate)
    {
        for (T each : this)
        {
            if (!predicate.test(each))
            {
                return false;
            }
        }
        return true;
    }

    default boolean noneMatch(Predicate<? super T> predicate)
    {
        for (T each : this)
        {
            if (predicate.test(each))
            {
                return false;
            }
        }
        return true;
    }

    default int count(Predicate<? super T> predicate)
    {
        int count = 0;
        for (T each : this)
        {
            if (predicate.test(each))
            {
                count++;
            }
        }
        return count;
    }

    default Optional<T> findFirst(Predicate<? super T> predicate)
    {
        for (T each : this)
        {
            if (predicate.test(each))
            {
                return Optional.of(each);
            }
        }
        return Optional.empty();
    }

    default <R, A> R collect(Collector<? super T, A, R> collector)
    {
        A mutableResult = collector.supplier().get();
        var accumulator = collector.accumulator();
        this.forEach(each -> accumulator.accept(mutableResult, each));
        return collector.finisher().apply(mutableResult);
    }

    default MutableList<T> toList()
    {
        return this.toCollection(MutableList::empty, Collection::add);
    }

    default MutableSet<T> toSet()
    {
        return this.toCollection(MutableSet::empty, Collection::add);
    }

    default MutableBag<T> toBag()
    {
        return this.toCollection(MutableBag::empty, Collection::add);
    }

    default <R extends Collection<T>> R toCollection(Supplier<R> supplier, BiConsumer<R, T> addMethod)
    {
        R collection = supplier.get();
        this.forEach(each -> addMethod.accept(collection, each));
        return collection;
    }

    <K> Bag<K> countBy(Function<? super T, ? extends K> function);

    <V> Bag<V> countByEach(Function<? super T, ? extends Iterable<V>> function);
}
