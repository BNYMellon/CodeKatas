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

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.collections.api.set.MutableSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CardTest
{
    @Test
    public void cartesianProductLazy()
    {
        MutableSet<Card> cards = Card.lazyCards().toSet();
        Set<Card> expected = this.imperativeCartesianProductOfRankAndSuit();
        Assertions.assertEquals(expected, cards);
    }

    @Test
    public void cartesianProductStream()
    {
        Set<Card> cards = Card.streamCards().collect(Collectors.toSet());
        Set<Card> expected = this.imperativeCartesianProductOfRankAndSuit();
        Assertions.assertEquals(expected, cards);
    }

    private Set<Card> imperativeCartesianProductOfRankAndSuit()
    {
        Set<Card> expected = new HashSet<>();
        for (Rank rank : Rank.values())
        {
            for (Suit suit : Suit.values())
            {
                expected.add(rank.of(suit));
            }
        }
        return expected;
    }

    @Test
    public void suits()
    {
        Assertions.assertTrue(Rank.ACE.of(Suit.CLUBS).isClubs());
        Assertions.assertTrue(Rank.ACE.of(Suit.DIAMONDS).isDiamonds());
        Assertions.assertTrue(Rank.ACE.of(Suit.HEARTS).isHearts());
        Assertions.assertTrue(Rank.ACE.of(Suit.SPADES).isSpades());
        Assertions.assertFalse(Rank.ACE.of(Suit.CLUBS).isDiamonds());
        Assertions.assertFalse(Rank.ACE.of(Suit.CLUBS).isHearts());
        Assertions.assertFalse(Rank.ACE.of(Suit.CLUBS).isSpades());
    }

    @Test
    public void compareTo()
    {
        Assertions.assertTrue(Rank.ACE.of(Suit.SPADES).compareTo(Rank.ACE.of(Suit.HEARTS)) < 0);
        Assertions.assertTrue(Rank.ACE.of(Suit.SPADES).compareTo(Rank.ACE.of(Suit.SPADES)) == 0);
        Assertions.assertTrue(Rank.KING.of(Suit.SPADES).compareTo(Rank.QUEEN.of(Suit.SPADES)) > 0);
    }

    @Test
    public void toStringWithEmoji()
    {
        Assertions.assertEquals("|A♠|", Rank.ACE.of(Suit.SPADES).toString());
        Assertions.assertEquals("|A♥|", Rank.ACE.of(Suit.HEARTS).toString());
        Assertions.assertEquals("|A♦|", Rank.ACE.of(Suit.DIAMONDS).toString());
        Assertions.assertEquals("|A♣|", Rank.ACE.of(Suit.CLUBS).toString());
    }
}
