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

package bnymellon.codekatas.deckofcards.sortedset.immutable;

import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;
import org.apache.commons.collections4.MultiValuedMap;
import org.junit.Assert;
import org.junit.Test;

public class ApacheCommonsDeckOfCardsAsSortedSetTest
{
    private JDKImperativeDeckOfCardsAsSortedSet jdkDeck = new JDKImperativeDeckOfCardsAsSortedSet();
    private ApacheCommonsDeckOfCardsAsSortedSet acDeck = new ApacheCommonsDeckOfCardsAsSortedSet();

    @Test
    public void allCards()
    {
        Assert.assertEquals(this.jdkDeck.getCards(), this.acDeck.getCards());
    }

    @Test
    public void cardsAreImmutable()
    {
        var acCards = this.acDeck.getCards();
        Assert.assertThrows(
                UnsupportedOperationException.class,
                () -> acCards.remove(null));
        Assert.assertThrows(
                UnsupportedOperationException.class,
                acCards::clear);
        Assert.assertThrows(
                UnsupportedOperationException.class,
                () -> acCards.add(null));
    }

    @Test
    public void diamonds()
    {
        Assert.assertEquals(this.jdkDeck.diamonds(), this.acDeck.diamonds());
    }

    @Test
    public void hearts()
    {
        Assert.assertEquals(this.jdkDeck.hearts(), this.acDeck.hearts());
    }

    @Test
    public void spades()
    {
        Assert.assertEquals(this.jdkDeck.spades(), this.acDeck.spades());
    }

    @Test
    public void clubs()
    {
        Assert.assertEquals(this.jdkDeck.clubs(), this.acDeck.clubs());
    }

    @Test
    public void deal()
    {
        Deque<Card> jdkShuffle = this.jdkDeck.shuffle(new Random(1));
        Deque<Card> acShuffle = this.acDeck.shuffle(new Random(1));

        Set<Card> jdkHand = this.jdkDeck.deal(jdkShuffle, 5);
        Set<Card> acHand = this.acDeck.deal(acShuffle, 5);
        Assert.assertEquals(jdkHand, acHand);
    }

    @Test
    public void shuffleAndDealHands()
    {
        List<Set<Card>> jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> acHands = this.acDeck.shuffleAndDeal(new Random(1), 5, 5);
        Assert.assertEquals(jdkHands, acHands);
    }

    @Test
    public void dealHands()
    {
        Deque<Card> jdkShuffled = this.jdkDeck.shuffle(new Random(1));
        Deque<Card> acShuffled = this.acDeck.shuffle(new Random(1));
        List<Set<Card>> jdkHands = this.jdkDeck.dealHands(jdkShuffled, 5, 5);
        List<Set<Card>> acHands = this.acDeck.dealHands(acShuffled, 5, 5);
        Assert.assertEquals(jdkHands, acHands);
    }

    @Test
    public void cardsBySuit()
    {
        Map<Suit, SortedSet<Card>> jdkCardsBySuit = this.jdkDeck.getCardsBySuit();
        MultiValuedMap<Suit, Card> acCardsBySuit = this.acDeck.getCardsBySuit();
        Assert.assertEquals(jdkCardsBySuit.get(Suit.CLUBS), new TreeSet<>(acCardsBySuit.get(Suit.CLUBS)));
    }

    @Test
    public void cardsBySuitIsImmutable()
    {
        var acCardsBySuit = this.acDeck.getCardsBySuit();
        Assert.assertThrows(
                UnsupportedOperationException.class,
                () -> acCardsBySuit.remove(Suit.CLUBS));
        Assert.assertThrows(
                UnsupportedOperationException.class,
                acCardsBySuit::clear);
        Assert.assertThrows(
                UnsupportedOperationException.class,
                () -> acCardsBySuit.get(Suit.CLUBS).remove(null));
        Assert.assertThrows(
                UnsupportedOperationException.class,
                () -> acCardsBySuit.get(Suit.CLUBS).add(null));
        Assert.assertThrows(
                UnsupportedOperationException.class,
                acCardsBySuit.get(Suit.CLUBS)::clear);
    }

    @Test
    public void countsBySuit()
    {
        Assert.assertEquals(
                this.jdkDeck.countsBySuit().get(Suit.CLUBS).intValue(),
                this.acDeck.countsBySuit().getCount(Suit.CLUBS));
    }

    @Test
    public void countsByRank()
    {
        Assert.assertEquals(
                this.jdkDeck.countsByRank().get(Rank.TEN).intValue(),
                this.acDeck.countsByRank().getCount(Rank.EIGHT));
    }
}
