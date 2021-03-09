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

public class JDK8DeckOfCardsAsListTest
{
    private JDKImperativeDeckOfCardsAsList jdk1Deck = new JDKImperativeDeckOfCardsAsList();
    private JDK8DeckOfCardsAsList jdk2Deck = new JDK8DeckOfCardsAsList();

    @Test
    public void allCards()
    {
        Assertions.assertEquals(this.jdk1Deck.getCards(), this.jdk2Deck.getCards());
    }

    @Test
    public void cardsAreImmutable()
    {
        var jdk2Cards = this.jdk2Deck.getCards();
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> jdk2Cards.remove(0));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                jdk2Cards::clear);
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> jdk2Cards.add(null));
    }

    @Test
    public void diamonds()
    {
        Assertions.assertEquals(this.jdk1Deck.diamonds(), this.jdk2Deck.diamonds());
    }

    @Test
    public void hearts()
    {
        Assertions.assertEquals(this.jdk1Deck.hearts(), this.jdk2Deck.hearts());
    }

    @Test
    public void spades()
    {
        Assertions.assertEquals(this.jdk1Deck.spades(), this.jdk2Deck.spades());
    }

    @Test
    public void clubs()
    {
        Assertions.assertEquals(this.jdk1Deck.clubs(), this.jdk2Deck.clubs());
    }

    @Test
    public void deal()
    {
        var jdk1Shuffle = this.jdk1Deck.shuffle(new Random(1));
        var jdk2Shuffle = this.jdk2Deck.shuffle(new Random(1));

        var jdk1Hand = this.jdk1Deck.deal(jdk1Shuffle, 5);
        var jdk2Hand = this.jdk2Deck.deal(jdk2Shuffle, 5);
        Assertions.assertEquals(jdk1Hand, jdk2Hand);
    }

    @Test
    public void shuffleAndDealHands()
    {
        var jdk1Hands = this.jdk1Deck.shuffleAndDeal(new Random(1), 5, 5);
        var jdk2Hands = this.jdk2Deck.shuffleAndDeal(new Random(1), 5, 5);
        Assertions.assertEquals(jdk1Hands, jdk2Hands);
    }

    @Test
    public void dealHands()
    {
        var jdk1Shuffled = this.jdk1Deck.shuffle(new Random(1));
        var jdk2Shuffled = this.jdk2Deck.shuffle(new Random(1));
        var jdk1Hands = this.jdk1Deck.dealHands(jdk1Shuffled, 5, 5);
        var jdk2Hands = this.jdk2Deck.dealHands(jdk2Shuffled, 5, 5);
        Assertions.assertEquals(jdk1Hands, jdk2Hands);
    }

    @Test
    public void cardsBySuit()
    {
        var jdk1CardsBySuit = this.jdk1Deck.getCardsBySuit();
        var jdk2CardsBySuit = this.jdk2Deck.getCardsBySuit();
        Assertions.assertEquals(jdk1CardsBySuit.get(Suit.CLUBS), jdk2CardsBySuit.get(Suit.CLUBS));
    }

    @Test
    public void cardsBySuitIsImmutable()
    {
        var jdk2CardsBySuit = this.jdk2Deck.getCardsBySuit();
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> jdk2CardsBySuit.remove(Suit.CLUBS));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                jdk2CardsBySuit::clear);
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> jdk2CardsBySuit.get(Suit.CLUBS).remove(0));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> jdk2CardsBySuit.get(Suit.CLUBS).add(null));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                jdk2CardsBySuit.get(Suit.CLUBS)::clear);
    }

    @Test
    public void countsBySuit()
    {
        Assertions.assertEquals(
                this.jdk1Deck.countsBySuit().get(Suit.CLUBS),
                this.jdk2Deck.countsBySuit().get(Suit.CLUBS));
    }

    @Test
    public void countsByRank()
    {
        Assertions.assertEquals(
                this.jdk1Deck.countsByRank().get(Rank.TEN),
                this.jdk2Deck.countsByRank().get(Rank.TEN));
    }
}
