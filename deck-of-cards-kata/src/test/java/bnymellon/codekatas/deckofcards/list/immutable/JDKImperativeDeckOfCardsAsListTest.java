/*
 * Copyright 2020 The Bank of New York Mellon.
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

package bnymellon.codekatas.deckofcards.list.immutable;

import java.util.Random;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.utility.Iterate;
import org.junit.Assert;
import org.junit.Test;

public class JDKImperativeDeckOfCardsAsListTest
{
    private JDKImperativeDeckOfCardsAsList jdkDeck = new JDKImperativeDeckOfCardsAsList();

    @Test
    public void allCards()
    {
        Assert.assertEquals(52, this.jdkDeck.getCards().size());
        Assert.assertEquals(new Card(Rank.ACE, Suit.SPADES), this.jdkDeck.getCards().get(0));
        Assert.assertEquals(new Card(Rank.KING, Suit.CLUBS), this.jdkDeck.getCards().get(51));
    }

    @Test
    public void cardsAreImmutable()
    {
        var jdkCards = this.jdkDeck.getCards();
        Verify.assertThrows(
                UnsupportedOperationException.class,
                () -> jdkCards.remove(0));
        Verify.assertThrows(
                UnsupportedOperationException.class,
                jdkCards::clear);
        Verify.assertThrows(
                UnsupportedOperationException.class,
                () -> jdkCards.add(null));
    }

    @Test
    public void diamonds()
    {
        Assert.assertEquals(13, this.jdkDeck.diamonds().size());
        Assert.assertTrue(Iterate.allSatisfy(this.jdkDeck.diamonds(), Card::isDiamonds));
    }

    @Test
    public void hearts()
    {
        Assert.assertEquals(13, this.jdkDeck.hearts().size());
        Assert.assertTrue(Iterate.allSatisfy(this.jdkDeck.hearts(), Card::isHearts));
    }

    @Test
    public void spades()
    {
        Assert.assertEquals(13, this.jdkDeck.spades().size());
        Assert.assertTrue(Iterate.allSatisfy(this.jdkDeck.spades(), Card::isSpades));
    }

    @Test
    public void clubs()
    {
        Assert.assertEquals(13, this.jdkDeck.clubs().size());
        Assert.assertTrue(Iterate.allSatisfy(this.jdkDeck.clubs(), Card::isClubs));
    }

    @Test
    public void deal()
    {
        var jdkShuffle = this.jdkDeck.shuffle(new Random(1));
        var jdkHand = this.jdkDeck.deal(jdkShuffle, 5);
        Assert.assertEquals(5, jdkHand.size());
        Assert.assertEquals(47, jdkShuffle.size());
    }

    @Test
    public void shuffleAndDealHands()
    {
        var jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        Assert.assertEquals(5, jdkHands.size());
        Assert.assertTrue(Iterate.allSatisfy(jdkHands, each -> each.size() == 5));
    }

    @Test
    public void dealHands()
    {
        var jdkShuffled = this.jdkDeck.shuffle(new Random(1));
        var jdkHands = this.jdkDeck.dealHands(jdkShuffled, 5, 5);
        Assert.assertEquals(5, jdkHands.size());
        Assert.assertTrue(Iterate.allSatisfy(jdkHands, each -> each.size() == 5));
        Assert.assertEquals(27, jdkShuffled.size());
    }

    @Test
    public void cardsBySuit()
    {
        var jdkCardsBySuit = this.jdkDeck.getCardsBySuit();
        Assert.assertEquals(4, jdkCardsBySuit.size());
        Assert.assertEquals(13, jdkCardsBySuit.get(Suit.CLUBS).size());
        Assert.assertEquals(13, jdkCardsBySuit.get(Suit.DIAMONDS).size());
        Assert.assertEquals(13, jdkCardsBySuit.get(Suit.SPADES).size());
        Assert.assertEquals(13, jdkCardsBySuit.get(Suit.HEARTS).size());
    }

    @Test
    public void cardsBySuitIsImmutable()
    {
        var jdkCardsBySuit = this.jdkDeck.getCardsBySuit();
        Verify.assertThrows(
                UnsupportedOperationException.class,
                () -> jdkCardsBySuit.remove(Suit.CLUBS));
        Verify.assertThrows(
                UnsupportedOperationException.class,
                jdkCardsBySuit::clear);
        Verify.assertThrows(
                UnsupportedOperationException.class,
                () -> jdkCardsBySuit.get(Suit.CLUBS).remove(0));
        Verify.assertThrows(
                UnsupportedOperationException.class,
                () -> jdkCardsBySuit.get(Suit.CLUBS).add(null));
        Verify.assertThrows(
                UnsupportedOperationException.class,
                jdkCardsBySuit.get(Suit.CLUBS)::clear);
    }

    @Test
    public void countsBySuit()
    {
        Assert.assertEquals(Long.valueOf(13), this.jdkDeck.countsBySuit().get(Suit.CLUBS));
    }

    @Test
    public void countsByRank()
    {
        Assert.assertEquals(Long.valueOf(4), this.jdkDeck.countsByRank().get(Rank.TEN));
    }
}
