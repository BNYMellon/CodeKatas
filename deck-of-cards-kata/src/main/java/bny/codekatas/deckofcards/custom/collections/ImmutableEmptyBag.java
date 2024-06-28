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

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

final class ImmutableEmptyBag<T> implements ImmutableBag<T>
{
    static final ImmutableBag<?> INSTANCE = new ImmutableEmptyBag<>();

    @Override
    public int size()
    {
        return 0;
    }

    @Override
    public boolean isEmpty()
    {
        return true;
    }

    @Override
    public int sizeDistinct()
    {
        return 0;
    }

    @Override
    public ImmutableBag<T> filter(Predicate<? super T> predicate)
    {
        return this;
    }

    @Override
    public ImmutableBag<T> filterNot(Predicate<? super T> predicate)
    {
        return this;
    }

    @Override
    public <V> ImmutableBag<V> map(Function<? super T, ? extends V> function)
    {
        return ImmutableBag.empty();
    }

    @Override
    public <V> ImmutableBag<V> flatMap(Function<? super T, ? extends Iterable<V>> function)
    {
        return ImmutableBag.empty();
    }

    @Override
    public int getOccurrences(T element)
    {
        return 0;
    }

    @Override
    public void forEachWithOccurrences(BiConsumer<? super T, Integer> procedure)
    {

    }

    @Override
    public void forEach(Consumer<? super T> action)
    {
    }

    @Override
    public Spliterator<T> spliterator()
    {
        return Spliterators.emptySpliterator();
    }

    @Override
    public Iterator<T> iterator()
    {
        return (Iterator<T>) EmptyIterator.INSTANCE;
    }

    @Override
    public int hashCode()
    {
        return 0;
    }

    @Override
    public boolean equals(Object other)
    {
        if (this == other)
        {
            return true;
        }
        if (other instanceof Bag bag)
        {
            return bag.isEmpty();
        }
        return super.equals(other);
    }
}
