  /**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
*/

package bnymellon.codekatas.deckofcards.custom.collections;

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
        else
        {
            boolean result = false;
            for (T each : iterable)
            {
                result |= this.add(each);
            }
            return result;
        }
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
}
