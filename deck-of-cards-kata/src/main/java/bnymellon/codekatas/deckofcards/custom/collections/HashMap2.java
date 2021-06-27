/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.deckofcards.custom.collections;

import java.util.HashMap;

public class HashMap2<K, V> extends HashMap<K, V> implements MutableMap<K, V>
{

    final HashMap2<K, V> withKeyValue(K key, V value)
    {
        this.put(key, value);
        return this;
    }
}
