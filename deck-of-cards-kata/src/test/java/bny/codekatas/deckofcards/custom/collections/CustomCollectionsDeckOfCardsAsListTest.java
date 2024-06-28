/*
 * Copyright 2024 The Bank of New York Mellon.
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

package bny.codekatas.deckofcards.custom.collections;

import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import bny.codekatas.deckofcards.Rank;
import bny.codekatas.deckofcards.Suit;
import bny.codekatas.deckofcards.list.immutable.JDKImperativeDeckOfCardsAsList;
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
        Assertions.assertEquals(
                "|A♦|,|2♦|,|3♦|,|4♦|,|5♦|,|6♦|,|7♦|,|8♦|,|9♦|,|10♦|,|J♦|,|Q♦|,|K♦|",
                this.customDeck.diamonds().stream().map(Object::toString).collect(Collectors.joining(",")));
    }

    @Test
    public void hearts()
    {
        Assertions.assertEquals(
                "|A♥|,|2♥|,|3♥|,|4♥|,|5♥|,|6♥|,|7♥|,|8♥|,|9♥|,|10♥|,|J♥|,|Q♥|,|K♥|",
                this.customDeck.hearts().stream().map(Object::toString).collect(Collectors.joining(",")));
    }

    @Test
    public void spades()
    {
        Assertions.assertEquals(
                "|A♠|,|2♠|,|3♠|,|4♠|,|5♠|,|6♠|,|7♠|,|8♠|,|9♠|,|10♠|,|J♠|,|Q♠|,|K♠|",
                this.customDeck.spades().stream().map(Object::toString).collect(Collectors.joining(",")));
    }

    @Test
    public void clubs()
    {
        Assertions.assertEquals(
                "|A♣|,|2♣|,|3♣|,|4♣|,|5♣|,|6♣|,|7♣|,|8♣|,|9♣|,|10♣|,|J♣|,|Q♣|,|K♣|",
                this.customDeck.clubs().stream().map(Object::toString).collect(Collectors.joining(",")));
    }

    @Test
    public void deal()
    {
        var jdk1Shuffle = this.jdkDeck.shuffle(new Random(1));
        var customShuffle = this.customDeck.shuffle(new Random(1));

        var jdk1Hand = this.jdkDeck.deal(jdk1Shuffle, 5);
        var customHand = this.customDeck.deal(customShuffle, 5);
        Assertions.assertEquals(jdk1Hand, customHand);
        Assertions.assertEquals(
                "|3♦|, |5♥|, |6♥|, |3♣|, |Q♣|",
                customHand.stream().sorted().map(Object::toString).collect(Collectors.joining(", ")));
    }

    @Test
    public void shuffleAndDealHands()
    {
        var jdk1Hands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        var customHands = this.customDeck.shuffleAndDeal(new Random(1), 5, 5);
        Assertions.assertEquals(jdk1Hands, customHands);
        var hands = customHands.stream().map(each -> each.stream().sorted().map(Objects::toString).collect(Collectors.joining(", "))).toList();
        ImmutableList<String> expectedHands = ImmutableList.of(
                "|3♦|, |5♥|, |6♥|, |3♣|, |Q♣|",
                "|10♠|, |J♠|, |10♥|, |5♣|, |9♣|",
                "|2♠|, |9♠|, |4♦|, |A♣|, |10♣|",
                "|Q♠|, |8♦|, |4♥|, |7♣|, |J♣|",
                "|A♦|, |A♥|, |2♥|, |J♥|, |6♣|");
        Assertions.assertEquals(expectedHands, hands);
    }

    @Test
    public void dealHands()
    {
        var jdk1Shuffled = this.jdkDeck.shuffle(new Random(1));
        var customShuffled = this.customDeck.shuffle(new Random(1));
        var jdk1Hands = this.jdkDeck.dealHands(jdk1Shuffled, 5, 5);
        var customHands = this.customDeck.dealHands(customShuffled, 5, 5);
        Assertions.assertEquals(jdk1Hands, customHands);
        var hands = customHands.stream().map(each -> each.stream().sorted().map(Objects::toString).collect(Collectors.joining(", "))).toList();
        ImmutableList<String> expectedHands = ImmutableList.of(
                "|3♦|, |5♥|, |6♥|, |3♣|, |Q♣|",
                "|10♠|, |J♠|, |10♥|, |5♣|, |9♣|",
                "|2♠|, |9♠|, |4♦|, |A♣|, |10♣|",
                "|Q♠|, |8♦|, |4♥|, |7♣|, |J♣|",
                "|A♦|, |A♥|, |2♥|, |J♥|, |6♣|");
        Assertions.assertEquals(expectedHands, hands);
    }

    @Test
    public void cardsBySuit()
    {
        var jdk1CardsBySuit = this.jdkDeck.getCardsBySuit();
        var customCardsBySuit = this.customDeck.getCardsBySuit();
        Assertions.assertEquals(jdk1CardsBySuit.get(Suit.CLUBS), customCardsBySuit.get(Suit.CLUBS));
        Assertions.assertEquals(
                "|A♣|,|2♣|,|3♣|,|4♣|,|5♣|,|6♣|,|7♣|,|8♣|,|9♣|,|10♣|,|J♣|,|Q♣|,|K♣|",
                customCardsBySuit.get(Suit.CLUBS).stream().map(Object::toString).collect(Collectors.joining(",")));
        Assertions.assertEquals(
                "|A♦|,|2♦|,|3♦|,|4♦|,|5♦|,|6♦|,|7♦|,|8♦|,|9♦|,|10♦|,|J♦|,|Q♦|,|K♦|",
                customCardsBySuit.get(Suit.DIAMONDS).stream().map(Object::toString).collect(Collectors.joining(",")));
        Assertions.assertEquals(
                "|A♠|,|2♠|,|3♠|,|4♠|,|5♠|,|6♠|,|7♠|,|8♠|,|9♠|,|10♠|,|J♠|,|Q♠|,|K♠|",
                customCardsBySuit.get(Suit.SPADES).stream().map(Object::toString).collect(Collectors.joining(",")));
        Assertions.assertEquals(
                "|A♥|,|2♥|,|3♥|,|4♥|,|5♥|,|6♥|,|7♥|,|8♥|,|9♥|,|10♥|,|J♥|,|Q♥|,|K♥|",
                customCardsBySuit.get(Suit.HEARTS).stream().map(Object::toString).collect(Collectors.joining(",")));
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
        MutableBag<Suit> expected = new HashBag<>();
        for (Suit suit : Suit.values())
        {
            expected.addOccurrences(suit, 13);
        }
        Assertions.assertEquals(expected, this.customDeck.countsBySuit());
    }

    @Test
    public void countsByRank()
    {
        MutableBag<Rank> expected = new HashBag<>();
        for (Rank rank : Rank.values())
        {
            expected.addOccurrences(rank, 4);
        }
        Assertions.assertEquals(expected, this.customDeck.countsByRank());
    }
}
