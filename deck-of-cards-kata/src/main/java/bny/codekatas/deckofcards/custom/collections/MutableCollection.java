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
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface MutableCollection<T> extends RichIterable<T>, Collection<T>
{
    default boolean addAllIterable(Iterable<T> iterable)
    {
        if (iterable instanceof Collection<T> collection)
        {
            return this.addAll(collection);
        }
        boolean result = false;
        for (T each : iterable)
        {
            result |= this.add(each);
        }
        return result;
    }

    @Override
    default MutableCollection<T> peek(Consumer<? super T> consumer)
    {
        this.forEach(consumer);
        return this;
    }

    @Override
    default Stream<T> stream()
    {
        return Collection.super.stream();
    }

    @Override
    MutableCollection<T> filter(Predicate<? super T> predicate);

    @Override
    MutableCollection<T> filterNot(Predicate<? super T> predicate);

    @Override
    <V> MutableCollection<V> map(Function<? super T, ? extends V> function);

    @Override
    <V> MutableCollection<V> flatMap(Function<? super T, ? extends Iterable<V>> function);

    default <K> MutableBag<K> countBy(Function<? super T, ? extends K> function)
    {
        var counts = MutableBag.<K>empty();
        this.forEach(each -> counts.add(function.apply(each)));
        return counts;
    }

    default <V> MutableBag<V> countByEach(Function<? super T, ? extends Iterable<V>> function)
    {
        return this.flatMap(function, MutableBag.empty());
    }
}
