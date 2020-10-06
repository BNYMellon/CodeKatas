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

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Bag<T> extends RichIterable<T>
{
    int sizeDistinct();

    @Override
    Bag<T> filter(Predicate<? super T> predicate);

    @Override
    Bag<T> filterNot(Predicate<? super T> predicate);

    @Override
    <V> Bag<V> map(Function<? super T, ? extends V> function);

    @Override
    <V> Bag<V> flatMap(Function<? super T, ? extends Iterable<V>> function);

    @Override
    default Bag<T> peek(Consumer<? super T> consumer)
    {
        this.forEach(consumer);
        return this;
    }

    int getOccurrences(T element);

    void forEachWithOccurrences(BiConsumer<? super T, Integer> procedure);
}
