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

package bnymellon.codekatas.deckofcards.sortedset.immutable;

import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;

import org.junit.Assert;
import org.junit.Test;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;

public class JDK8DeckOfCardsAsSortedSetTest
{
    private JDKImperativeDeckOfCardsAsSortedSet jdk1Deck = new JDKImperativeDeckOfCardsAsSortedSet();
    private JDK8DeckOfCardsAsSortedSet jdk2Deck = new JDK8DeckOfCardsAsSortedSet();

    @Test
    public void allCards()
    {
        Assert.assertEquals(this.jdk1Deck.getCards(), this.jdk2Deck.getCards());
    }

    @Test
    public void diamonds()
    {
        Assert.assertEquals(this.jdk1Deck.diamonds(), this.jdk2Deck.diamonds());
    }

    @Test
    public void hearts()
    {
        Assert.assertEquals(this.jdk1Deck.hearts(), this.jdk2Deck.hearts());
    }

    @Test
    public void spades()
    {
        Assert.assertEquals(this.jdk1Deck.spades(), this.jdk2Deck.spades());
    }

    @Test
    public void clubs()
    {
        Assert.assertEquals(this.jdk1Deck.clubs(), this.jdk2Deck.clubs());
    }

    @Test
    public void deal()
    {
        Deque<Card> jdk1Shuffle = this.jdk1Deck.shuffle(new Random(1));
        Deque<Card> jdk2Shuffle = this.jdk2Deck.shuffle(new Random(1));

        Set<Card> jdk1Hand = this.jdk1Deck.deal(jdk1Shuffle, 5);
        Set<Card> jdk2Hand = this.jdk2Deck.deal(jdk2Shuffle, 5);
        Assert.assertEquals(jdk1Hand, jdk2Hand);
    }

    @Test
    public void shuffleAndDealHands()
    {
        List<Set<Card>> jdk1Hands = this.jdk1Deck.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> jdk2Hands = this.jdk2Deck.shuffleAndDeal(new Random(1), 5, 5);
        Assert.assertEquals(jdk1Hands, jdk2Hands);
    }

    @Test
    public void dealHands()
    {
        Deque<Card> jdk1Shuffled = this.jdk1Deck.shuffle(new Random(1));
        Deque<Card> jdk2Shuffled = this.jdk2Deck.shuffle(new Random(1));
        List<Set<Card>> jdk1Hands = this.jdk1Deck.dealHands(jdk1Shuffled, 5, 5);
        List<Set<Card>> jdk2Hands = this.jdk2Deck.dealHands(jdk2Shuffled, 5, 5);
        Assert.assertEquals(jdk1Hands, jdk2Hands);
    }

    @Test
    public void cardsBySuit()
    {
        Map<Suit, SortedSet<Card>> jdk1CardsBySuit = this.jdk1Deck.getCardsBySuit();
        Map<Suit, SortedSet<Card>> jdk2CardsBySuit = this.jdk2Deck.getCardsBySuit();
        Assert.assertEquals(jdk1CardsBySuit.get(Suit.CLUBS), jdk2CardsBySuit.get(Suit.CLUBS));
    }

    @Test
    public void countsBySuit()
    {
        Assert.assertEquals(
                this.jdk1Deck.countsBySuit().get(Suit.CLUBS),
                this.jdk2Deck.countsBySuit().get(Suit.CLUBS));
    }

    @Test
    public void countsByRank()
    {
        Assert.assertEquals(
                this.jdk1Deck.countsByRank().get(Rank.TEN),
                this.jdk2Deck.countsByRank().get(Rank.TEN));
    }
}
