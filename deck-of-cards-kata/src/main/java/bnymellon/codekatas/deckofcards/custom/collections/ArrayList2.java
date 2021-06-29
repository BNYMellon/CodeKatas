  /**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
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
