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

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public sealed interface ImmutableCollection<T> extends RichIterable<T>
        permits ImmutableList, ImmutableBag, ImmutableSet
{
    @Override
    ImmutableCollection<T> filter(Predicate<? super T> predicate);

    @Override
    ImmutableCollection<T> filterNot(Predicate<? super T> predicate);

    @Override
    <V> ImmutableCollection<V> map(Function<? super T, ? extends V> function);

    @Override
    <V> ImmutableCollection<V> flatMap(Function<? super T, ? extends Iterable<V>> function);

    @Override
    default ImmutableCollection<T> peek(Consumer<? super T> consumer)
    {
        this.forEach(consumer);
        return this;
    }

    default <K> ImmutableBag<K> countBy(Function<? super T, ? extends K> function)
    {
        var counts = MutableBag.<K>empty();
        return this.map(function, counts).toImmutable();
    }

    default <V> ImmutableBag<V> countByEach(Function<? super T, ? extends Iterable<V>> function)
    {
        return this.flatMap(function, MutableBag.empty()).toImmutable();
    }
}
