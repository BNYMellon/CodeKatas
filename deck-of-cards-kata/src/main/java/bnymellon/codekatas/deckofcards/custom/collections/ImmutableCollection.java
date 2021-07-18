  /**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
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
