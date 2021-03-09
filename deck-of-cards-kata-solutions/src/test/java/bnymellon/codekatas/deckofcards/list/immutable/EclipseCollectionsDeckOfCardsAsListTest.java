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

package bnymellon.codekatas.deckofcards.list.immutable;

import java.util.Random;

import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EclipseCollectionsDeckOfCardsAsListTest
{
    private EclipseCollectionsDeckOfCardsAsList ecDeck = new EclipseCollectionsDeckOfCardsAsList();
    private JDKImperativeDeckOfCardsAsList jdkDeck = new JDKImperativeDeckOfCardsAsList();

    @Test
    public void allCards()
    {
        Assertions.assertEquals(this.jdkDeck.getCards(), this.ecDeck.getCards());
    }

    @Test
    public void cardsAreImmutable()
    {
        var ecCards = this.ecDeck.getCards().castToList();
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> ecCards.remove(0));
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
        Assertions.assertEquals(this.jdkDeck.diamonds(), this.ecDeck.diamonds());
    }

    @Test
    public void hearts()
    {
        Assertions.assertEquals(this.jdkDeck.hearts(), this.ecDeck.hearts());
    }

    @Test
    public void spades()
    {
        Assertions.assertEquals(this.jdkDeck.spades(), this.ecDeck.spades());
    }

    @Test
    public void clubs()
    {
        Assertions.assertEquals(this.jdkDeck.clubs(), this.ecDeck.clubs());
    }

    @Test
    public void deal()
    {
        var ecShuffle = this.ecDeck.shuffle(new Random(1));
        var jdkShuffle = this.jdkDeck.shuffle(new Random(1));

        var ecHand = this.ecDeck.deal(ecShuffle, 5);
        var jdkHand = this.jdkDeck.deal(jdkShuffle, 5);
        Assertions.assertEquals(jdkHand, ecHand);
    }

    @Test
    public void shuffleAndDealHands()
    {
        var ecHands = this.ecDeck.shuffleAndDeal(new Random(1), 5, 5);
        var jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        Assertions.assertEquals(jdkHands, ecHands);
    }

    @Test
    public void dealHands()
    {
        var ecShuffled = this.ecDeck.shuffle(new Random(1));
        var jdkShuffled = this.jdkDeck.shuffle(new Random(1));
        var ecHands = this.ecDeck.dealHands(ecShuffled, 5, 5);
        var jdkHands = this.jdkDeck.dealHands(jdkShuffled, 5, 5);
        Assertions.assertEquals(jdkHands, ecHands);
    }

    @Test
    public void cardsBySuit()
    {
        var ecCardsBySuit = this.ecDeck.getCardsBySuit();
        var jdkCardsBySuit = this.jdkDeck.getCardsBySuit();
        Assertions.assertEquals(jdkCardsBySuit.get(Suit.CLUBS), ecCardsBySuit.get(Suit.CLUBS));
    }

    @Test
    public void cardsBySuitIsImmutable()
    {
        var ecCardsBySuit = this.ecDeck.getCardsBySuit();
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> ecCardsBySuit.get(Suit.CLUBS).castToList().remove(0));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> ecCardsBySuit.get(Suit.CLUBS).castToList().add(null));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                ecCardsBySuit.get(Suit.CLUBS).castToList()::clear);
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
