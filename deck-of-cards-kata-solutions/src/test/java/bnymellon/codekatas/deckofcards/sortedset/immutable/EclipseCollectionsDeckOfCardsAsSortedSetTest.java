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
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.multimap.sortedset.ImmutableSortedSetMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.stack.MutableStack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EclipseCollectionsDeckOfCardsAsSortedSetTest
{
    private EclipseCollectionsDeckOfCardsAsSortedSet ecDeck = new EclipseCollectionsDeckOfCardsAsSortedSet();
    private JDKImperativeDeckOfCardsAsSortedSet jdkDeck = new JDKImperativeDeckOfCardsAsSortedSet();

    @Test
    public void allCards()
    {
        Assertions.assertEquals(this.ecDeck.getCards(), this.jdkDeck.getCards());
    }

    @Test
    public void cardsAreImmutable()
    {
        var ecCards = this.ecDeck.getCards().castToSortedSet();
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> ecCards.remove(null));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                ecCards::clear);
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> ecCards.add(null));
    }

    @Test
    public void diamonds()
    {
        Assertions.assertEquals(this.ecDeck.diamonds(), this.jdkDeck.diamonds());
    }

    @Test
    public void hearts()
    {
        Assertions.assertEquals(this.ecDeck.hearts(), this.jdkDeck.hearts());
    }

    @Test
    public void spades()
    {
        Assertions.assertEquals(this.ecDeck.spades(), this.jdkDeck.spades());
    }

    @Test
    public void clubs()
    {
        Assertions.assertEquals(this.ecDeck.clubs(), this.jdkDeck.clubs());
    }

    @Test
    public void deal()
    {
        MutableStack<Card> ecShuffle = this.ecDeck.shuffle(new Random(1));
        Deque<Card> jdkShuffle = this.jdkDeck.shuffle(new Random(1));

        MutableSet<Card> ecHand = this.ecDeck.deal(ecShuffle, 5);
        Set<Card> jdkHand = this.jdkDeck.deal(jdkShuffle, 5);
        Assertions.assertEquals(ecHand, jdkHand);
    }

    @Test
    public void shuffleAndDealHands()
    {
        ImmutableList<Set<Card>> ecHands = this.ecDeck.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        Assertions.assertEquals(ecHands, jdkHands);
    }

    @Test
    public void dealHands()
    {
        MutableStack<Card> ecShuffled = this.ecDeck.shuffle(new Random(1));
        Deque<Card> jdkShuffled = this.jdkDeck.shuffle(new Random(1));
        ImmutableList<Set<Card>> ecHands = this.ecDeck.dealHands(ecShuffled, 5, 5);
        List<Set<Card>> jdkHands = this.jdkDeck.dealHands(jdkShuffled, 5, 5);
        Assertions.assertEquals(ecHands, jdkHands);
    }

    @Test
    public void cardsBySuit()
    {
        ImmutableSortedSetMultimap<Suit, Card> ecCardsBySuit = this.ecDeck.getCardsBySuit();
        Map<Suit, SortedSet<Card>> jdkCardsBySuit = this.jdkDeck.getCardsBySuit();
        Assertions.assertEquals(ecCardsBySuit.get(Suit.CLUBS), jdkCardsBySuit.get(Suit.CLUBS));
    }

    @Test
    public void cardsBySuitIsImmutable()
    {
        var ecCardsBySuit = this.ecDeck.getCardsBySuit();
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> ecCardsBySuit.get(Suit.CLUBS).castToSortedSet().remove(null));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> ecCardsBySuit.get(Suit.CLUBS).castToSortedSet().add(null));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                ecCardsBySuit.get(Suit.CLUBS).castToSortedSet()::clear);
    }

    @Test
    public void countsBySuit()
    {
        Assertions.assertEquals(
                this.jdkDeck.countsBySuit().get(Suit.CLUBS).intValue(),
                this.ecDeck.countsBySuit().occurrencesOf(Suit.CLUBS));
    }

    @Test
    public void countsByRank()
    {
        Assertions.assertEquals(
                this.jdkDeck.countsByRank().get(Rank.TEN).intValue(),
                this.ecDeck.countsByRank().occurrencesOf(Rank.SEVEN));
    }
}
