  /**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
*/

package bnymellon.codekatas.deckofcards.list.immutable;

import java.util.ArrayList;
import java.util.Random;

import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApacheCommonsDeckOfCardsAsListTest
{
    private JDKImperativeDeckOfCardsAsList jdkDeck = new JDKImperativeDeckOfCardsAsList();
    private ApacheCommonsDeckOfCardsAsList acDeck = new ApacheCommonsDeckOfCardsAsList();

    @Test
    public void allCards()
    {
        Assertions.assertEquals(this.jdkDeck.getCards(), this.acDeck.getCards());
    }

    @Test
    public void cardsAreImmutable()
    {
        var acCards = this.acDeck.getCards();
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> acCards.remove(0));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                acCards::clear);
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> acCards.add(null));
    }

    @Test
    public void diamonds()
    {
        Assertions.assertEquals(this.jdkDeck.diamonds(), this.acDeck.diamonds());
    }

    @Test
    public void hearts()
    {
        Assertions.assertEquals(this.jdkDeck.hearts(), this.acDeck.hearts());
    }

    @Test
    public void spades()
    {
        Assertions.assertEquals(this.jdkDeck.spades(), this.acDeck.spades());
    }

    @Test
    public void clubs()
    {
        Assertions.assertEquals(this.jdkDeck.clubs(), this.acDeck.clubs());
    }

    @Test
    public void deal()
    {
        var jdkShuffle = this.jdkDeck.shuffle(new Random(1));
        var acShuffle = this.acDeck.shuffle(new Random(1));

        var jdkHand = this.jdkDeck.deal(jdkShuffle, 5);
        var acHand = this.acDeck.deal(acShuffle, 5);
        Assertions.assertEquals(jdkHand, acHand);
    }

    @Test
    public void shuffleAndDealHands()
    {
        var jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        var acHands = this.acDeck.shuffleAndDeal(new Random(1), 5, 5);
        Assertions.assertEquals(jdkHands, acHands);
    }

    @Test
    public void dealHands()
    {
        var jdkShuffled = this.jdkDeck.shuffle(new Random(1));
        var acShuffled = this.acDeck.shuffle(new Random(1));
        var jdkHands = this.jdkDeck.dealHands(jdkShuffled, 5, 5);
        var acHands = this.acDeck.dealHands(acShuffled, 5, 5);
        Assertions.assertEquals(jdkHands, acHands);
    }

    @Test
    public void cardsBySuit()
    {
        var jdkCardsBySuit = this.jdkDeck.getCardsBySuit();
        var acCardsBySuit = this.acDeck.getCardsBySuit();
        Assertions.assertEquals(jdkCardsBySuit.get(Suit.CLUBS), new ArrayList<>(acCardsBySuit.get(Suit.CLUBS)));
    }

    @Test
    public void cardsBySuitIsImmutable()
    {
        var acCardsBySuit = this.acDeck.getCardsBySuit();
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> acCardsBySuit.remove(Suit.CLUBS));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                acCardsBySuit::clear);
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> acCardsBySuit.get(Suit.CLUBS).remove(0));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> acCardsBySuit.get(Suit.CLUBS).add(null));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                acCardsBySuit.get(Suit.CLUBS)::clear);
    }

    @Test
    public void countsBySuit()
    {
        Assertions.assertEquals(
                this.jdkDeck.countsBySuit().get(Suit.CLUBS).intValue(),
                this.acDeck.countsBySuit().getCount(Suit.CLUBS));
    }

    @Test
    public void countsByRank()
    {
        Assertions.assertEquals(
                this.jdkDeck.countsByRank().get(Rank.TEN).intValue(),
                this.acDeck.countsByRank().getCount(Rank.EIGHT));
    }
}
