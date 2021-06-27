/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.deckofcards.custom.collections;

import java.util.ArrayList;
import java.util.Collection;

public class ArrayList2<T> extends ArrayList<T> implements MutableList<T>
{
    public ArrayList2(Collection<? extends T> collection)
    {
        super(collection);
    }

    public ArrayList2(int initialCapacity)
    {
        super(initialCapacity);
    }

    public ArrayList2()
    {
        super();
    }
}
