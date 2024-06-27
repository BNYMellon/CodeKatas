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

import java.util.AbstractList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

final class ImmutableEmptyList<T>
        extends AbstractList<T>
        implements ImmutableList<T>
{
    static final ImmutableList<?> INSTANCE = new ImmutableEmptyList<>();

    @Override
    public ImmutableList<T> filter(Predicate<? super T> predicate)
    {
        return this;
    }

    @Override
    public ImmutableList<T> filterNot(Predicate<? super T> predicate)
    {
        return this;
    }

    @Override
    public <V> ImmutableList<V> map(Function<? super T, ? extends V> function)
    {
        return ImmutableList.empty();
    }

    @Override
    public <V> ImmutableList<V> flatMap(Function<? super T, ? extends Iterable<V>> function)
    {
        return ImmutableList.empty();
    }

    @Override
    public T get(int index)
    {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public Iterator<T> iterator()
    {
        return (Iterator<T>) EmptyIterator.INSTANCE;
    }

    @Override
    public int size()
    {
        return 0;
    }

    @Override
    public void forEach(Consumer<? super T> action)
    {
    }

    @Override
    public Stream<T> stream()
    {
        return StreamSupport.stream(this.spliterator(), false);
    }

    @Override
    public Spliterator<T> spliterator()
    {
        return Spliterators.emptySpliterator();
    }
}
