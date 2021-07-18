  /**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
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
