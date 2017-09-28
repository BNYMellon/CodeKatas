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

package bnymellon.codekatas.deckofcards.list.immutable;

import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;

public class VavrDeckOfCardsAsListTest
{
    private JDKImperativeDeckOfCardsAsList jdkDeck = new JDKImperativeDeckOfCardsAsList();
    private VavrDeckOfCardsAsList vavrDeck = new VavrDeckOfCardsAsList();

    @Test
    public void allCards()
    {
        Assert.assertEquals(this.jdkDeck.getCards(), this.vavrDeck.getCards().toJavaList());
    }

    @Test
    public void diamonds()
    {
        Assert.assertEquals(this.jdkDeck.diamonds(), this.vavrDeck.diamonds().toJavaList());
    }

    @Test
    public void hearts()
    {
        Assert.assertEquals(this.jdkDeck.hearts(), this.vavrDeck.hearts().toJavaList());
    }

    @Test
    public void spades()
    {
        Assert.assertEquals(this.jdkDeck.spades(), this.vavrDeck.spades().toJavaList());
    }

    @Test
    public void clubs()
    {
        Assert.assertEquals(this.jdkDeck.clubs(), this.vavrDeck.clubs().toJavaList());
    }

    @Test
    public void deal()
    {
        Deque<Card> jdkShuffle = this.jdkDeck.shuffle(new Random(1));
        io.vavr.collection.List<Card> vavrShuffle = this.vavrDeck.shuffle(new Random(1));

        Set<Card> jdkHand = this.jdkDeck.deal(jdkShuffle, 5);
        Set<Card> vavrHand = this.vavrDeck.deal(vavrShuffle, 5)._1().toJavaSet();
        Assert.assertEquals(jdkHand, vavrHand);
    }

    @Test
    public void shuffleAndDealHands()
    {
        List<Set<Card>> jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        io.vavr.collection.List<io.vavr.collection.Set<Card>> vavrHands =
                this.vavrDeck.shuffleAndDeal(new Random(1), 5, 5);
        Assert.assertEquals(jdkHands.get(0), vavrHands.get(0).toJavaSet());
        Assert.assertEquals(jdkHands.get(1), vavrHands.get(1).toJavaSet());
        Assert.assertEquals(jdkHands.get(2), vavrHands.get(2).toJavaSet());
        Assert.assertEquals(jdkHands.get(3), vavrHands.get(3).toJavaSet());
        Assert.assertEquals(jdkHands.get(4), vavrHands.get(4).toJavaSet());
    }

    @Test
    public void dealHands()
    {
        Deque<Card> jdkShuffled = this.jdkDeck.shuffle(new Random(1));
        io.vavr.collection.List<Card> vavrShuffled = this.vavrDeck.shuffle(new Random(1));
        List<Set<Card>> jdkHands = this.jdkDeck.dealHands(jdkShuffled, 5, 5);
        io.vavr.collection.List<io.vavr.collection.Set<Card>> vavrHands =
                this.vavrDeck.dealHands(vavrShuffled, 5, 5);
        Assert.assertEquals(jdkHands.get(0), vavrHands.get(0).toJavaSet());
        Assert.assertEquals(jdkHands.get(1), vavrHands.get(1).toJavaSet());
        Assert.assertEquals(jdkHands.get(2), vavrHands.get(2).toJavaSet());
        Assert.assertEquals(jdkHands.get(3), vavrHands.get(3).toJavaSet());
        Assert.assertEquals(jdkHands.get(4), vavrHands.get(4).toJavaSet());
    }

    @Test
    public void cardsBySuit()
    {
        Map<Suit, List<Card>> jdkCardsBySuit = this.jdkDeck.getCardsBySuit();
        io.vavr.collection.Map<Suit, ? extends io.vavr.collection.List<Card>> vavrCardsBySuit =
                this.vavrDeck.getCardsBySuit();
        Assert.assertEquals(jdkCardsBySuit.get(Suit.CLUBS), vavrCardsBySuit.get(Suit.CLUBS).get().toJavaList());
    }

    @Test
    public void countsBySuit()
    {
        Assert.assertEquals(
                this.jdkDeck.countsBySuit().get(Suit.CLUBS),
                this.vavrDeck.countsBySuit().get(Suit.CLUBS).get());
    }

    @Test
    public void countsByRank()
    {
        Assert.assertEquals(
                this.jdkDeck.countsByRank().get(Rank.TEN),
                this.vavrDeck.countsByRank().get(Rank.TEN).get());
    }
}
