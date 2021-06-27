/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.deckofcards.custom.collections;

import java.util.Map;
import java.util.function.Supplier;

public interface MutableMap<K, V> extends Map<K, V>
{
    static <K1, V1> MutableMap<K1, V1> empty()
    {
        return new HashMap2<>();
    }

    static <K1, V1> MutableMap<K1, V1> of(K1 key, V1 value)
    {
        return new HashMap2<K1, V1>().withKeyValue(key, value);
    }

    static <K1, V1> MutableMap<K1, V1> of(K1 key1, V1 value1, K1 key2, V1 value2)
    {
        return new HashMap2<K1, V1>()
                .withKeyValue(key1, value1)
                .withKeyValue(key2, value2);
    }

    default MutableMap<K, V> asUnmodifiable()
    {
        return new UnmodifiableMutableMap<>(this);
    }

    default V getIfAbsentPut(K key, Supplier<? extends V> supplier)
    {
        V result = this.get(key);
        if (result == null && !this.containsKey(key))
        {
            result = supplier.get();
            this.put(key, result);
        }
        return result;
    }
}
