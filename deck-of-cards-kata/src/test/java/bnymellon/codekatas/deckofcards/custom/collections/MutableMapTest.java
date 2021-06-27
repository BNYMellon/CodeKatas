/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.deckofcards.custom.collections;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MutableMapTest
{
    @Test
    public void of()
    {
        Assertions.assertEquals(Map.of(1, "1"), MutableMap.of(1, "1"));
        MutableMap<Integer, String> map = MutableMap.of(1, "1", 2, "2");
        Map<Integer, String> expected = Map.of(1, "1", 2, "2");
        Assertions.assertEquals(expected, map);
    }
}
