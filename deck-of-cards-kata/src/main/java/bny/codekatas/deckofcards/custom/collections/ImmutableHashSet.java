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

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.RandomAccess;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

final class ImmutableHashSet<T>
        extends AbstractSet<T>
        implements RandomAccess, ImmutableSet<T>
{
    private final HashSet2<T> hashSet;

    public ImmutableHashSet(Collection<T> collection)
    {
        this.hashSet = new HashSet2<>(collection);
    }

    @Override
    public ImmutableSet<T> filter(Predicate<? super T> predicate)
    {
        return this.hashSet
                .filter(predicate)
                .toImmutable();
    }

    @Override
    public ImmutableSet<T> filterNot(Predicate<? super T> predicate)
    {
        return this.hashSet
                .filterNot(predicate)
                .toImmutable();
    }

    @Override
    public <V> ImmutableSet<V> map(Function<? super T, ? extends V> function)
    {
        return this.hashSet
                .<V>map(function)
                .toImmutable();
    }

    @Override
    public <V> ImmutableSet<V> flatMap(Function<? super T, ? extends Iterable<V>> function)
    {
        return this.hashSet
                .flatMap(function)
                .toImmutable();
    }

    @Override
    public int size()
    {
        return this.hashSet.size();
    }

    @Override
    public Stream<T> stream()
    {
        return this.hashSet.stream();
    }

    @Override
    public boolean equals(Object o)
    {
        return this.hashSet.equals(o);
    }

    @Override
    public int hashCode()
    {
        return this.hashSet.hashCode();
    }

    @Override
    public Iterator<T> iterator()
    {
        return this.stream().iterator();
    }
}
