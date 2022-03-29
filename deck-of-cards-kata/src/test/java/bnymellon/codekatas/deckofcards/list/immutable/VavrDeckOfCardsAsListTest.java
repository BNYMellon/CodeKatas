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

package bnymellon.codekatas.deckofcards.list.immutable;

import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;

public class VavrDeckOfCardsAsListTest
{
    private JDKImperativeDeckOfCardsAsList jdkDeck = new JDKImperativeDeckOfCardsAsList();
    private VavrDeckOfCardsAsList vavrDeck = new VavrDeckOfCardsAsList();

    @Test
    public void allCards()
    {
        Assertions.assertEquals(this.jdkDeck.getCards(), this.vavrDeck.getCards().toJavaList());
    }

    @Test
    public void diamonds()
    {
        Assertions.assertEquals(this.jdkDeck.diamonds(), this.vavrDeck.diamonds().toJavaList());
    }

    @Test
    public void hearts()
    {
        Assertions.assertEquals(this.jdkDeck.hearts(), this.vavrDeck.hearts().toJavaList());
    }

    @Test
    public void spades()
    {
        Assertions.assertEquals(this.jdkDeck.spades(), this.vavrDeck.spades().toJavaList());
    }

    @Test
    public void clubs()
    {
        Assertions.assertEquals(this.jdkDeck.clubs(), this.vavrDeck.clubs().toJavaList());
    }

    @Test
    public void deal()
    {
        var jdkShuffle = this.jdkDeck.shuffle(new Random(1));
        var vavrShuffle = this.vavrDeck.shuffle(new Random(1));

        var jdkHand = this.jdkDeck.deal(jdkShuffle, 5);
        var vavrHand = this.vavrDeck.deal(vavrShuffle, 5)._1().toJavaSet();
        Assertions.assertEquals(jdkHand, vavrHand);
    }

    @Test
    public void shuffleAndDealHands()
    {
        var jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        var vavrHands = this.vavrDeck.shuffleAndDeal(new Random(1), 5, 5);
        Assertions.assertEquals(jdkHands.get(0), vavrHands.get(0).toJavaSet());
        Assertions.assertEquals(jdkHands.get(1), vavrHands.get(1).toJavaSet());
        Assertions.assertEquals(jdkHands.get(2), vavrHands.get(2).toJavaSet());
        Assertions.assertEquals(jdkHands.get(3), vavrHands.get(3).toJavaSet());
        Assertions.assertEquals(jdkHands.get(4), vavrHands.get(4).toJavaSet());
    }

    @Test
    public void dealHands()
    {
        var jdkShuffled = this.jdkDeck.shuffle(new Random(1));
        var vavrShuffled = this.vavrDeck.shuffle(new Random(1));
        var jdkHands = this.jdkDeck.dealHands(jdkShuffled, 5, 5);
        var vavrHands = this.vavrDeck.dealHands(vavrShuffled, 5, 5);
        Assertions.assertEquals(jdkHands.get(0), vavrHands.get(0).toJavaSet());
        Assertions.assertEquals(jdkHands.get(1), vavrHands.get(1).toJavaSet());
        Assertions.assertEquals(jdkHands.get(2), vavrHands.get(2).toJavaSet());
        Assertions.assertEquals(jdkHands.get(3), vavrHands.get(3).toJavaSet());
        Assertions.assertEquals(jdkHands.get(4), vavrHands.get(4).toJavaSet());
    }

    @Test
    public void cardsBySuit()
    {
        var jdkCardsBySuit = this.jdkDeck.getCardsBySuit();
        var vavrCardsBySuit = this.vavrDeck.getCardsBySuit();
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
