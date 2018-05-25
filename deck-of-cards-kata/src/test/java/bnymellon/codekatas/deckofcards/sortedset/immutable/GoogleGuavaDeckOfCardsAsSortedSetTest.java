/*
 * Copyright 2018 BNY Mellon.
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

import com.google.common.collect.ImmutableSetMultimap;
import org.eclipse.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;

public class GoogleGuavaDeckOfCardsAsSortedSetTest
{
    private JDKImperativeDeckOfCardsAsSortedSet jdkDeck = new JDKImperativeDeckOfCardsAsSortedSet();
    private GoogleGuavaDeckOfCardsAsSortedSet ggDeck = new GoogleGuavaDeckOfCardsAsSortedSet();

    @Test
    public void allCards()
    {
        Assert.assertEquals(this.jdkDeck.getCards(), this.ggDeck.getCards());
    }

    @Test
    public void cardsAreImmutable()
    {
        var ggCards = this.ggDeck.getCards();
        Verify.assertThrows(
                UnsupportedOperationException.class,
                () -> ggCards.remove(null));
        Verify.assertThrows(
                UnsupportedOperationException.class,
                ggCards::clear);
        Verify.assertThrows(
                UnsupportedOperationException.class,
                () -> ggCards.add(null));
    }

    @Test
    public void diamonds()
    {
        Assert.assertEquals(this.jdkDeck.diamonds(), this.ggDeck.diamonds());
    }

    @Test
    public void hearts()
    {
        Assert.assertEquals(this.jdkDeck.hearts(), this.ggDeck.hearts());
    }

    @Test
    public void spades()
    {
        Assert.assertEquals(this.jdkDeck.spades(), this.ggDeck.spades());
    }

    @Test
    public void clubs()
    {
        Assert.assertEquals(this.jdkDeck.clubs(), this.ggDeck.clubs());
    }

    @Test
    public void deal()
    {
        Deque<Card> jdkShuffle = this.jdkDeck.shuffle(new Random(1));
        Deque<Card> ggShuffle = this.ggDeck.shuffle(new Random(1));

        Set<Card> jdkHand = this.jdkDeck.deal(jdkShuffle, 5);
        Set<Card> ggHand = this.ggDeck.deal(ggShuffle, 5);
        Assert.assertEquals(jdkHand, ggHand);
    }

    @Test
    public void shuffleAndDealHands()
    {
        List<Set<Card>> jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> ggHands = this.ggDeck.shuffleAndDeal(new Random(1), 5, 5);
        Assert.assertEquals(jdkHands, ggHands);
    }

    @Test
    public void dealHands()
    {
        Deque<Card> jdkShuffled = this.jdkDeck.shuffle(new Random(1));
        Deque<Card> ggShuffled = this.ggDeck.shuffle(new Random(1));
        List<Set<Card>> jdkHands = this.jdkDeck.dealHands(jdkShuffled, 5, 5);
        List<Set<Card>> ggHands = this.ggDeck.dealHands(ggShuffled, 5, 5);
        Assert.assertEquals(jdkHands, ggHands);
    }

    @Test
    public void cardsBySuit()
    {
        Map<Suit, SortedSet<Card>> jdkCardsBySuit = this.jdkDeck.getCardsBySuit();
        ImmutableSetMultimap<Suit, Card> ggCardsBySuit = this.ggDeck.getCardsBySuit();
        Assert.assertEquals(jdkCardsBySuit.get(Suit.CLUBS), ggCardsBySuit.get(Suit.CLUBS));
    }

    @Test
    public void cardsBySuitIsImmutable()
    {
        var ggCardsBySuit = this.ggDeck.getCardsBySuit();
        Verify.assertThrows(
                UnsupportedOperationException.class,
                () -> ggCardsBySuit.removeAll(Suit.CLUBS));
        Verify.assertThrows(
                UnsupportedOperationException.class,
                ggCardsBySuit::clear);
        Verify.assertThrows(
                UnsupportedOperationException.class,
                () -> ggCardsBySuit.get(Suit.CLUBS).remove(null));
        Verify.assertThrows(
                UnsupportedOperationException.class,
                () -> ggCardsBySuit.get(Suit.CLUBS).add(null));
        Verify.assertThrows(
                UnsupportedOperationException.class,
                ggCardsBySuit.get(Suit.CLUBS)::clear);
    }

    @Test
    public void countsBySuit()
    {
        Assert.assertEquals(
                this.jdkDeck.countsBySuit().get(Suit.CLUBS).intValue(),
                this.ggDeck.countsBySuit().count(Suit.CLUBS));
    }

    @Test
    public void countsByRank()
    {
        Assert.assertEquals(
                this.jdkDeck.countsByRank().get(Rank.TEN).intValue(),
                this.ggDeck.countsByRank().count(Rank.NINE));
    }
}
