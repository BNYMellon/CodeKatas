/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
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
