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

import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.impl.factory.Bags;
import org.eclipse.collections.impl.factory.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SuitMatchGameTest
{
    @Test
    public void matchHandOneMatch()
    {
        SuitMatchGame game = new SuitMatchGame(1, 5, 20, false);

        MutableList<Card> hand = Lists.mutable.with(
                new Card(Rank.ACE, Suit.CLUBS),
                new Card(Rank.TWO, Suit.CLUBS),
                new Card(Rank.THREE, Suit.HEARTS),
                new Card(Rank.THREE, Suit.SPADES),
                new Card(Rank.THREE, Suit.DIAMONDS));
        MutableBag<Suit> result = Bags.mutable.empty();
        ListIterable<Card> cards = game.matchHand(hand, result);
        String expected = "|3♥|, |3♠|, |3♦|";
        Assertions.assertEquals(expected, cards.makeString(", "));
    }

    @Test
    public void matchHandTwoMatches()
    {
        SuitMatchGame game = new SuitMatchGame(1, 5, 20, false);

        MutableList<Card> hand = Lists.mutable.with(
                new Card(Rank.ACE, Suit.CLUBS),
                new Card(Rank.TWO, Suit.CLUBS),
                new Card(Rank.THREE, Suit.HEARTS),
                new Card(Rank.FOUR, Suit.HEARTS),
                new Card(Rank.FIVE, Suit.HEARTS));
        MutableBag<Suit> result = Bags.mutable.empty();
        ListIterable<Card> cards = game.matchHand(hand, result);
        String expected = "|A♣|, |2♣|";
        Assertions.assertEquals(expected, cards.makeString(", "));
    }

    @Test
    public void matchHandNoMatches()
    {
        SuitMatchGame game = new SuitMatchGame(1, 4, 20, false);

        MutableList<Card> hand = Lists.mutable.with(
                new Card(Rank.ACE, Suit.CLUBS),
                new Card(Rank.TWO, Suit.DIAMONDS),
                new Card(Rank.THREE, Suit.HEARTS),
                new Card(Rank.FOUR, Suit.SPADES));
        MutableBag<Suit> result = Bags.mutable.empty();
        ListIterable<Card> cards = game.matchHand(hand, result);
        String expected = "|A♣|, |2♦|, |3♥|, |4♠|";
        Assertions.assertEquals(expected, cards.makeString(", "));
    }

    @Test
    public void createDeck()
    {
        MutableStack<Card> deck = new SuitMatchGame(1, 4, 20, false).createDeck();
        Assertions.assertEquals(52, deck.size());
        MutableStack<Card> deck2 = new SuitMatchGame(2, 4, 20, false).createDeck();
        Assertions.assertEquals(104, deck2.size());
        MutableStack<Card> deck3 = new SuitMatchGame(3, 4, 20, false).createDeck();
        Assertions.assertEquals(156, deck3.size());
    }
}
