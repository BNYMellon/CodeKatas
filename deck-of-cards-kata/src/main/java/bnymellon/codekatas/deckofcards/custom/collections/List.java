/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.deckofcards.custom.collections;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface List<T> extends RichIterable<T>
{
    @Override
    List<T> filter(Predicate<? super T> predicate);

    @Override
    List<T> filterNot(Predicate<? super T> predicate);

    @Override
    <V> List<V> map(Function<? super T, ? extends V> function);

    @Override
    <V> List<V> flatMap(Function<? super T, ? extends Iterable<V>> function);

    @Override
    default List<T> peek(Consumer<? super T> consumer)
    {
        this.forEach(consumer);
        return this;
    }

    T get(int index);
}
