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

import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GoogleGuavaDeckOfCardsAsListTest
{
    private JDKImperativeDeckOfCardsAsList jdkDeck = new JDKImperativeDeckOfCardsAsList();
    private GoogleGuavaDeckOfCardsAsList ggDeck = new GoogleGuavaDeckOfCardsAsList();

    @Test
    public void allCards()
    {
        Assertions.assertEquals(this.jdkDeck.getCards(), this.ggDeck.getCards());
    }

    @Test
    public void cardsAreImmutable()
    {
        var ggCards = this.ggDeck.getCards();
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> ggCards.remove(0));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                ggCards::clear);
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> ggCards.add(null));
    }

    @Test
    public void diamonds()
    {
        Assertions.assertEquals(this.jdkDeck.diamonds(), this.ggDeck.diamonds());
    }

    @Test
    public void hearts()
    {
        Assertions.assertEquals(this.jdkDeck.hearts(), this.ggDeck.hearts());
    }

    @Test
    public void spades()
    {
        Assertions.assertEquals(this.jdkDeck.spades(), this.ggDeck.spades());
    }

    @Test
    public void clubs()
    {
        Assertions.assertEquals(this.jdkDeck.clubs(), this.ggDeck.clubs());
    }

    @Test
    public void deal()
    {
        var jdkShuffle = this.jdkDeck.shuffle(new Random(1));
        var ggShuffle = this.ggDeck.shuffle(new Random(1));

        var jdkHand = this.jdkDeck.deal(jdkShuffle, 5);
        var ggHand = this.ggDeck.deal(ggShuffle, 5);
        Assertions.assertEquals(jdkHand, ggHand);
    }

    @Test
    public void shuffleAndDealHands()
    {
        var jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        var ggHands = this.ggDeck.shuffleAndDeal(new Random(1), 5, 5);
        Assertions.assertEquals(jdkHands, ggHands);
    }

    @Test
    public void dealHands()
    {
        var jdkShuffled = this.jdkDeck.shuffle(new Random(1));
        var ggShuffled = this.ggDeck.shuffle(new Random(1));
        var jdkHands = this.jdkDeck.dealHands(jdkShuffled, 5, 5);
        var ggHands = this.ggDeck.dealHands(ggShuffled, 5, 5);
        Assertions.assertEquals(jdkHands, ggHands);
    }

    @Test
    public void cardsBySuit()
    {
        var jdkCardsBySuit = this.jdkDeck.getCardsBySuit();
        var ggCardsBySuit = this.ggDeck.getCardsBySuit();
        Assertions.assertEquals(jdkCardsBySuit.get(Suit.CLUBS), ggCardsBySuit.get(Suit.CLUBS));
    }

    @Test
    public void cardsBySuitIsImmutable()
    {
        var ggCardsBySuit = this.ggDeck.getCardsBySuit();
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> ggCardsBySuit.removeAll(Suit.CLUBS));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                ggCardsBySuit::clear);
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> ggCardsBySuit.get(Suit.CLUBS).remove(0));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> ggCardsBySuit.get(Suit.CLUBS).add(null));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                ggCardsBySuit.get(Suit.CLUBS)::clear);
    }

    @Test
    public void countsBySuit()
    {
        Multiset<Suit> set = HashMultiset.create();
        set.add(Suit.HEARTS,13);
        set.add(Suit.CLUBS,13);
        set.add(Suit.DIAMONDS,13);
        set.add(Suit.SPADES,13);
        Assertions.assertEquals(set, this.ggDeck.countsBySuit());
    }

    @Test
    public void countsByRank()
    {
        Multiset<Rank> set = HashMultiset.create();
        set.add(Rank.ACE,4);
        set.add(Rank.KING,4);
        set.add(Rank.QUEEN,4);
        set.add(Rank.JACK,4);
        set.add(Rank.TWO,4);
        set.add(Rank.THREE,4);
        set.add(Rank.FOUR,4);
        set.add(Rank.FIVE,4);
        set.add(Rank.SIX,4);
        set.add(Rank.SEVEN,4);
        set.add(Rank.EIGHT,4);
        set.add(Rank.NINE,4);
        set.add(Rank.TEN,4);
        Assertions.assertEquals(set, this.ggDeck.countsByRank());
    }
}
