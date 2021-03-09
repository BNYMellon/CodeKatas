/*
 * Copyright 2021 The Bank of New York Mellon.
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

package bnymellon.codekatas.deckofcards.sortedset.immutable;

import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;
import org.eclipse.collections.impl.utility.Iterate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JDKImperativeDeckOfCardsAsSortedSetTest
{
    private JDKImperativeDeckOfCardsAsSortedSet jdkDeck = new JDKImperativeDeckOfCardsAsSortedSet();

    @Test
    public void allCards()
    {
        Assertions.assertEquals(52, this.jdkDeck.getCards().size());
        Assertions.assertEquals(new Card(Rank.ACE, Suit.SPADES), this.jdkDeck.getCards().first());
        Assertions.assertEquals(new Card(Rank.KING, Suit.CLUBS), this.jdkDeck.getCards().last());
    }

    @Test
    public void cardsAreImmutable()
    {
        var jdkCards = this.jdkDeck.getCards();
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> jdkCards.remove(null));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                jdkCards::clear);
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> jdkCards.add(null));
    }

    @Test
    public void diamonds()
    {
        Assertions.assertEquals(13, this.jdkDeck.diamonds().size());
        Assertions.assertTrue(Iterate.allSatisfy(this.jdkDeck.diamonds(), Card::isDiamonds));
    }

    @Test
    public void hearts()
    {
        Assertions.assertEquals(13, this.jdkDeck.hearts().size());
        Assertions.assertTrue(Iterate.allSatisfy(this.jdkDeck.hearts(), Card::isHearts));
    }

    @Test
    public void spades()
    {
        Assertions.assertEquals(13, this.jdkDeck.spades().size());
        Assertions.assertTrue(Iterate.allSatisfy(this.jdkDeck.spades(), Card::isSpades));
    }

    @Test
    public void clubs()
    {
        Assertions.assertEquals(13, this.jdkDeck.clubs().size());
        Assertions.assertTrue(Iterate.allSatisfy(this.jdkDeck.clubs(), Card::isClubs));
    }

    @Test
    public void deal()
    {
        Deque<Card> jdkShuffle = this.jdkDeck.shuffle(new Random(1));
        Set<Card> jdkHand = this.jdkDeck.deal(jdkShuffle, 5);
        Assertions.assertEquals(5, jdkHand.size());
        Assertions.assertEquals(47, jdkShuffle.size());
    }

    @Test
    public void shuffleAndDealHands()
    {
        List<Set<Card>> jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        Assertions.assertEquals(5, jdkHands.size());
        Assertions.assertTrue(Iterate.allSatisfy(jdkHands, each -> each.size() == 5));
    }

    @Test
    public void dealHands()
    {
        Deque<Card> jdkShuffled = this.jdkDeck.shuffle(new Random(1));
        List<Set<Card>> jdkHands = this.jdkDeck.dealHands(jdkShuffled, 5, 5);
        Assertions.assertEquals(5, jdkHands.size());
        Assertions.assertTrue(Iterate.allSatisfy(jdkHands, each -> each.size() == 5));
        Assertions.assertEquals(27, jdkShuffled.size());
    }

    @Test
    public void cardsBySuit()
    {
        Map<Suit, SortedSet<Card>> jdkCardsBySuit = this.jdkDeck.getCardsBySuit();
        Assertions.assertEquals(4, jdkCardsBySuit.size());
        Assertions.assertEquals(13, jdkCardsBySuit.get(Suit.CLUBS).size());
        Assertions.assertEquals(13, jdkCardsBySuit.get(Suit.DIAMONDS).size());
        Assertions.assertEquals(13, jdkCardsBySuit.get(Suit.SPADES).size());
        Assertions.assertEquals(13, jdkCardsBySuit.get(Suit.HEARTS).size());
    }

    @Test
    public void cardsBySuitIsImmutable()
    {
        var jdkCardsBySuit = this.jdkDeck.getCardsBySuit();
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> jdkCardsBySuit.remove(Suit.CLUBS));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                jdkCardsBySuit::clear);
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> jdkCardsBySuit.get(Suit.CLUBS).remove(null));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> jdkCardsBySuit.get(Suit.CLUBS).add(null));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                jdkCardsBySuit.get(Suit.CLUBS)::clear);
    }

    @Test
    public void countsBySuit()
    {
        Assertions.assertEquals(Long.valueOf(13), this.jdkDeck.countsBySuit().get(Suit.CLUBS));
    }

    @Test
    public void countsByRank()
    {
        Assertions.assertEquals(Long.valueOf(4), this.jdkDeck.countsByRank().get(Rank.TEN));
    }
}
