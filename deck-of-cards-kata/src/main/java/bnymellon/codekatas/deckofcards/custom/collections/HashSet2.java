/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.deckofcards.custom.collections;

import java.util.Collection;
import java.util.HashSet;

public class HashSet2<T> extends HashSet<T> implements MutableSet<T>
{
    public HashSet2(Collection<? extends T> collection)
    {
        super(collection);
    }

    public HashSet2(int initialCapacity, float loadFactor)
    {
        super(initialCapacity, loadFactor);
    }

    public HashSet2(int initialCapacity)
    {
        super(initialCapacity);
    }

    public HashSet2()
    {
        super();
    }
}
