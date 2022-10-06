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
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        Assertions.assertEquals("|A♦|,|2♦|,|3♦|,|4♦|,|5♦|,|6♦|,|7♦|,|8♦|,|9♦|,|10♦|,|J♦|,|Q♦|,|K♦|",
                this.vavrDeck.diamonds().mkString(","));
    }

    @Test
    public void hearts()
    {
        Assertions.assertEquals("|A♥|,|2♥|,|3♥|,|4♥|,|5♥|,|6♥|,|7♥|,|8♥|,|9♥|,|10♥|,|J♥|,|Q♥|,|K♥|",
                this.vavrDeck.hearts().mkString(","));
    }

    @Test
    public void spades()
    {
        Assertions.assertEquals("|A♠|,|2♠|,|3♠|,|4♠|,|5♠|,|6♠|,|7♠|,|8♠|,|9♠|,|10♠|,|J♠|,|Q♠|,|K♠|",
                this.vavrDeck.spades().mkString(","));
    }

    @Test
    public void clubs()
    {
        Assertions.assertEquals("|A♣|,|2♣|,|3♣|,|4♣|,|5♣|,|6♣|,|7♣|,|8♣|,|9♣|,|10♣|,|J♣|,|Q♣|,|K♣|",
                this.vavrDeck.clubs().mkString(","));
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
        var vavrCardsBySuit = this.vavrDeck.getCardsBySuit();
        Assertions.assertEquals(4, vavrCardsBySuit.size());
        Assertions.assertEquals("|A♣|,|2♣|,|3♣|,|4♣|,|5♣|,|6♣|,|7♣|,|8♣|,|9♣|,|10♣|,|J♣|,|Q♣|,|K♣|",
                vavrCardsBySuit.get(Suit.CLUBS).get().mkString(","));
        Assertions.assertEquals("|A♠|,|2♠|,|3♠|,|4♠|,|5♠|,|6♠|,|7♠|,|8♠|,|9♠|,|10♠|,|J♠|,|Q♠|,|K♠|",
                vavrCardsBySuit.get(Suit.SPADES).get().mkString(","));
        Assertions.assertEquals("|A♥|,|2♥|,|3♥|,|4♥|,|5♥|,|6♥|,|7♥|,|8♥|,|9♥|,|10♥|,|J♥|,|Q♥|,|K♥|",
                vavrCardsBySuit.get(Suit.HEARTS).get().mkString(","));
        Assertions.assertEquals("|A♦|,|2♦|,|3♦|,|4♦|,|5♦|,|6♦|,|7♦|,|8♦|,|9♦|,|10♦|,|J♦|,|Q♦|,|K♦|",
                vavrCardsBySuit.get(Suit.DIAMONDS).get().mkString(","));

    }

    @Test
    public void countsBySuit()
    {
        Map<Suit, Long> map = HashMap.of(
            Suit.CLUBS, 13L, Suit.SPADES, 13L, Suit.DIAMONDS,13L, Suit.HEARTS,13L);
        Assertions.assertEquals(map, this.vavrDeck.countsBySuit());
    }

    @Test
    public void countsByRank()
    {
        Assertions.assertEquals(
                this.jdkDeck.countsByRank().get(Rank.TEN),
                this.vavrDeck.countsByRank().get(Rank.TEN).get());
    }
}
