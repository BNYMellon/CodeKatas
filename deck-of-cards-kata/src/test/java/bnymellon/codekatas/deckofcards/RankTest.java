/*
 * Copyright 2022 The Bank of New York Mellon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bnymellon.codekatas.deckofcards;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RankTest
{
    @Test
    public void of()
    {
        Card card = Rank.ACE.of(Suit.CLUBS);

        Card expectedCard = new Card(Rank.ACE, Suit.CLUBS);
        Assertions.assertEquals(expectedCard, card);
    }

    @Test
    public void toStringWithValue()
    {
        Assertions.assertEquals("A", Rank.ACE.toString());
        Assertions.assertEquals("K", Rank.KING.toString());
        Assertions.assertEquals("Q", Rank.QUEEN.toString());
        Assertions.assertEquals("J", Rank.JACK.toString());
        Assertions.assertEquals("10", Rank.TEN.toString());
        Assertions.assertEquals("9", Rank.NINE.toString());
        Assertions.assertEquals("8", Rank.EIGHT.toString());
        Assertions.assertEquals("7", Rank.SEVEN.toString());
        Assertions.assertEquals("6", Rank.SIX.toString());
        Assertions.assertEquals("5", Rank.FIVE.toString());
        Assertions.assertEquals("4", Rank.FOUR.toString());
        Assertions.assertEquals("3", Rank.THREE.toString());
        Assertions.assertEquals("2", Rank.TWO.toString());
    }
}
