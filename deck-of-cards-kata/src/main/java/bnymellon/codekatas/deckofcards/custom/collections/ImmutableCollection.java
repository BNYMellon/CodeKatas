/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.deckofcards.custom.collections;

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
        this.forEach(each -> counts.add(function.apply(each)));
        return counts.toImmutable();
    }
}
