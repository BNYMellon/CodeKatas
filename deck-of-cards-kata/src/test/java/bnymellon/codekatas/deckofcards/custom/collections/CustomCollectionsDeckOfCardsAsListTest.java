/*
 * Copyright 2022 The Bank of New York Mellon.
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

package bnymellon.codekatas.deckofcards.custom.collections;

import java.util.Random;

import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;
import bnymellon.codekatas.deckofcards.list.immutable.JDKImperativeDeckOfCardsAsList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomCollectionsDeckOfCardsAsListTest
{
    private final JDKImperativeDeckOfCardsAsList jdkDeck = new JDKImperativeDeckOfCardsAsList();
    private final CustomCollectionsDeckOfCardsAsList customDeck = new CustomCollectionsDeckOfCardsAsList();

    @Test
    public void allCards()
    {
        Assertions.assertEquals(this.jdkDeck.getCards(), this.customDeck.getCards());
    }

    @Test
    public void diamonds()
    {
        Assertions.assertEquals(this.jdkDeck.diamonds(), this.customDeck.diamonds());
    }

    @Test
    public void hearts()
    {
        Assertions.assertEquals(this.jdkDeck.hearts(), this.customDeck.hearts());
    }

    @Test
    public void spades()
    {
        Assertions.assertEquals(this.jdkDeck.spades(), this.customDeck.spades());
    }

    @Test
    public void clubs()
    {
        Assertions.assertEquals(this.jdkDeck.clubs(), this.customDeck.clubs());
    }

    @Test
    public void deal()
    {
        var jdk1Shuffle = this.jdkDeck.shuffle(new Random(1));
        var jdk2Shuffle = this.customDeck.shuffle(new Random(1));

        var jdk1Hand = this.jdkDeck.deal(jdk1Shuffle, 5);
        var jdk2Hand = this.customDeck.deal(jdk2Shuffle, 5);
        Assertions.assertEquals(jdk1Hand, jdk2Hand);
    }

    @Test
    public void shuffleAndDealHands()
    {
        var jdk1Hands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        var jdk2Hands = this.customDeck.shuffleAndDeal(new Random(1), 5, 5);
        Assertions.assertEquals(jdk1Hands, jdk2Hands);
    }

    @Test
    public void dealHands()
    {
        var jdk1Shuffled = this.jdkDeck.shuffle(new Random(1));
        var jdk2Shuffled = this.customDeck.shuffle(new Random(1));
        var jdk1Hands = this.jdkDeck.dealHands(jdk1Shuffled, 5, 5);
        var jdk2Hands = this.customDeck.dealHands(jdk2Shuffled, 5, 5);
        Assertions.assertEquals(jdk1Hands, jdk2Hands);
    }

    @Test
    public void cardsBySuit()
    {
        var jdk1CardsBySuit = this.jdkDeck.getCardsBySuit();
        var jdk2CardsBySuit = this.customDeck.getCardsBySuit();
        Assertions.assertEquals(jdk1CardsBySuit.get(Suit.CLUBS), jdk2CardsBySuit.get(Suit.CLUBS));
    }

    @Test
    public void cardsBySuitIsImmutable()
    {
        var jdk2CardsBySuit = this.customDeck.getCardsBySuit();
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
        for (Suit suit : Suit.values())
        {
            Assertions.assertEquals(
                    this.jdkDeck.countsBySuit().get(suit).intValue(),
                    this.customDeck.countsBySuit().getOccurrences(suit));
        }
    }

    @Test
    public void countsByRank()
    {
        for (Rank rank : Rank.values())
        {
            Assertions.assertEquals(
                    this.jdkDeck.countsByRank().get(rank).intValue(),
                    this.customDeck.countsByRank().getOccurrences(rank));
        }
    }
}
