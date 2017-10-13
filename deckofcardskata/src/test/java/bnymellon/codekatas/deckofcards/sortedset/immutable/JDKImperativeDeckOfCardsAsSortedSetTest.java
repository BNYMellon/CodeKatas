/*
 * Copyright 2017 BNY Mellon.
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

import org.eclipse.collections.impl.utility.Iterate;
import org.junit.Assert;
import org.junit.Test;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;

public class JDKImperativeDeckOfCardsAsSortedSetTest
{
    private JDKImperativeDeckOfCardsAsSortedSet jdkDeck = new JDKImperativeDeckOfCardsAsSortedSet();

    @Test
    public void allCards()
    {
        Assert.assertEquals(52, this.jdkDeck.getCards().size());
        Assert.assertEquals(new Card(Rank.ACE, Suit.SPADES), this.jdkDeck.getCards().first());
        Assert.assertEquals(new Card(Rank.KING, Suit.CLUBS), this.jdkDeck.getCards().last());
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
        Deque<Card> jdkShuffle = this.jdkDeck.shuffle(new Random(1));
        Set<Card> jdkHand = this.jdkDeck.deal(jdkShuffle, 5);
        Assert.assertEquals(5, jdkHand.size());
        Assert.assertEquals(47, jdkShuffle.size());
    }

    @Test
    public void shuffleAndDealHands()
    {
        List<Set<Card>> jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        Assert.assertEquals(5, jdkHands.size());
        Assert.assertTrue(Iterate.allSatisfy(jdkHands, each -> each.size() == 5));
    }

    @Test
    public void dealHands()
    {
        Deque<Card> jdkShuffled = this.jdkDeck.shuffle(new Random(1));
        List<Set<Card>> jdkHands = this.jdkDeck.dealHands(jdkShuffled, 5, 5);
        Assert.assertEquals(5, jdkHands.size());
        Assert.assertTrue(Iterate.allSatisfy(jdkHands, each -> each.size() == 5));
        Assert.assertEquals(27, jdkShuffled.size());
    }

    @Test
    public void cardsBySuit()
    {
        Map<Suit, SortedSet<Card>> jdkCardsBySuit = this.jdkDeck.getCardsBySuit();
        Assert.assertEquals(4, jdkCardsBySuit.size());
        Assert.assertEquals(13, jdkCardsBySuit.get(Suit.CLUBS).size());
        Assert.assertEquals(13, jdkCardsBySuit.get(Suit.DIAMONDS).size());
        Assert.assertEquals(13, jdkCardsBySuit.get(Suit.SPADES).size());
        Assert.assertEquals(13, jdkCardsBySuit.get(Suit.HEARTS).size());
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
