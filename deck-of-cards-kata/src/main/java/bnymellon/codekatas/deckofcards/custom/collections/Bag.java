/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
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
