/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.deckofcards.sortedset.immutable;

import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;

public class VavrDeckOfCardsAsSortedSetTest
{
    private JDKImperativeDeckOfCardsAsSortedSet jdkDeck = new JDKImperativeDeckOfCardsAsSortedSet();
    private VavrDeckOfCardsAsSortedSet vavrDeck = new VavrDeckOfCardsAsSortedSet();

    @Test
    public void allCards()
    {
        Assertions.assertEquals(this.jdkDeck.getCards(), this.vavrDeck.getCards().toJavaSet());
    }

    @Test
    public void diamonds()
    {
        Assertions.assertEquals(this.jdkDeck.diamonds(), this.vavrDeck.diamonds().toJavaSet());
    }

    @Test
    public void hearts()
    {
        Assertions.assertEquals(this.jdkDeck.hearts(), this.vavrDeck.hearts().toJavaSet());
    }

    @Test
    public void spades()
    {
        Assertions.assertEquals(this.jdkDeck.spades(), this.vavrDeck.spades().toJavaSet());
    }

    @Test
    public void clubs()
    {
        Assertions.assertEquals(this.jdkDeck.clubs(), this.vavrDeck.clubs().toJavaSet());
    }

    @Test
    public void deal()
    {
        Deque<Card> jdkShuffle = this.jdkDeck.shuffle(new Random(1));
        io.vavr.collection.List<Card> vavrShuffle = this.vavrDeck.shuffle(new Random(1));

        Set<Card> jdkHand = this.jdkDeck.deal(jdkShuffle, 5);
        Set<Card> vavrHand = this.vavrDeck.deal(vavrShuffle, 5)._1().toJavaSet();
        Assertions.assertEquals(jdkHand, vavrHand);
    }

    @Test
    public void shuffleAndDealHands()
    {
        List<Set<Card>> jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        io.vavr.collection.List<io.vavr.collection.Set<Card>> vavrHands =
                this.vavrDeck.shuffleAndDeal(new Random(1), 5, 5);
        Assertions.assertEquals(jdkHands.get(0), vavrHands.get(0).toJavaSet());
        Assertions.assertEquals(jdkHands.get(1), vavrHands.get(1).toJavaSet());
        Assertions.assertEquals(jdkHands.get(2), vavrHands.get(2).toJavaSet());
        Assertions.assertEquals(jdkHands.get(3), vavrHands.get(3).toJavaSet());
        Assertions.assertEquals(jdkHands.get(4), vavrHands.get(4).toJavaSet());
    }

    @Test
    public void dealHands()
    {
        Deque<Card> jdkShuffled = this.jdkDeck.shuffle(new Random(1));
        io.vavr.collection.List<Card> vavrShuffled = this.vavrDeck.shuffle(new Random(1));
        List<Set<Card>> jdkHands = this.jdkDeck.dealHands(jdkShuffled, 5, 5);
        io.vavr.collection.List<io.vavr.collection.Set<Card>> vavrHands =
                this.vavrDeck.dealHands(vavrShuffled, 5, 5);
        Assertions.assertEquals(jdkHands.get(0), vavrHands.get(0).toJavaSet());
        Assertions.assertEquals(jdkHands.get(1), vavrHands.get(1).toJavaSet());
        Assertions.assertEquals(jdkHands.get(2), vavrHands.get(2).toJavaSet());
        Assertions.assertEquals(jdkHands.get(3), vavrHands.get(3).toJavaSet());
        Assertions.assertEquals(jdkHands.get(4), vavrHands.get(4).toJavaSet());
    }

    @Test
    public void cardsBySuit()
    {
        Map<Suit, SortedSet<Card>> jdkCardsBySuit = this.jdkDeck.getCardsBySuit();
        io.vavr.collection.Map<Suit, ? extends io.vavr.collection.SortedSet<Card>> vavrCardsBySuit =
                this.vavrDeck.getCardsBySuit();
        Assertions.assertEquals(jdkCardsBySuit.get(Suit.CLUBS), vavrCardsBySuit.get(Suit.CLUBS).get().toJavaList());
    }

    @Test
    public void countsBySuit()
    {
        Assertions.assertEquals(
                this.jdkDeck.countsBySuit().get(Suit.CLUBS),
                this.vavrDeck.countsBySuit().get(Suit.CLUBS).get());
    }

    @Test
    public void countsByRank()
    {
        Assertions.assertEquals(
                this.jdkDeck.countsByRank().get(Rank.TEN),
                this.vavrDeck.countsByRank().get(Rank.TEN).get());
    }
}
