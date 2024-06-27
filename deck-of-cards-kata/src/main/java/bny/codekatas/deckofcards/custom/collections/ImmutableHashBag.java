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

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

final class ImmutableHashBag<T>
        extends AbstractCollection<T>
        implements ImmutableBag<T>
{
    private final MutableBag<T> hashBag;

    public ImmutableHashBag(Collection<T> collection)
    {
        HashBag<T> bag = new HashBag<>();
        bag.addAll(collection);
        this.hashBag = bag;
    }

    @Override
    public ImmutableBag<T> filter(Predicate<? super T> predicate)
    {
        return this.hashBag
                .filter(predicate)
                .toImmutable();
    }

    @Override
    public ImmutableBag<T> filterNot(Predicate<? super T> predicate)
    {
        return this.hashBag
                .filterNot(predicate)
                .toImmutable();
    }

    @Override
    public <V> ImmutableBag<V> map(Function<? super T, ? extends V> function)
    {
        return this.hashBag
                .<V>map(function)
                .toImmutable();
    }

    @Override
    public <V> ImmutableBag<V> flatMap(Function<? super T, ? extends Iterable<V>> function)
    {
        return this.hashBag
                .flatMap(function)
                .toImmutable();
    }

    @Override
    public int getOccurrences(T element)
    {
        return this.hashBag.getOccurrences(element);
    }

    @Override
    public void forEachWithOccurrences(BiConsumer<? super T, Integer> procedure)
    {
        this.hashBag.forEachWithOccurrences(procedure);
    }

    @Override
    public Iterator<T> iterator()
    {
        return this.stream().iterator();
    }

    @Override
    public Stream<T> stream()
    {
        return this.hashBag.stream();
    }

    @Override
    public int size()
    {
        return this.hashBag.size();
    }

    @Override
    public int sizeDistinct()
    {
        return this.hashBag.sizeDistinct();
    }

    @Override
    public int hashCode()
    {
        return this.hashBag.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return this.hashBag.equals(obj);
    }
}
