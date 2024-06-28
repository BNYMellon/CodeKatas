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

package bny.codekatas.deckofcards.list.immutable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import bny.codekatas.deckofcards.Rank;
import bny.codekatas.deckofcards.Suit;
import org.eclipse.collections.impl.utility.Iterate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JavaStreamsDeckOfCardsAsListTest
{
    private JDKImperativeDeckOfCardsAsList jdk1Deck = new JDKImperativeDeckOfCardsAsList();
    private JavaStreamsDeckOfCardsAsList jdk2Deck = new JavaStreamsDeckOfCardsAsList();

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
        Assertions.assertEquals(
                "|A♦|,|2♦|,|3♦|,|4♦|,|5♦|,|6♦|,|7♦|,|8♦|,|9♦|,|10♦|,|J♦|,|Q♦|,|K♦|",
                this.jdk2Deck.diamonds().stream().map(Object::toString).collect(Collectors.joining(",")));
    }

    @Test
    public void hearts()
    {
        Assertions.assertEquals(
                "|A♥|,|2♥|,|3♥|,|4♥|,|5♥|,|6♥|,|7♥|,|8♥|,|9♥|,|10♥|,|J♥|,|Q♥|,|K♥|",
                this.jdk2Deck.hearts().stream().map(Object::toString).collect(Collectors.joining(",")));
    }

    @Test
    public void spades()
    {
        Assertions.assertEquals(
                "|A♠|,|2♠|,|3♠|,|4♠|,|5♠|,|6♠|,|7♠|,|8♠|,|9♠|,|10♠|,|J♠|,|Q♠|,|K♠|",
                this.jdk2Deck.spades().stream().map(Object::toString).collect(Collectors.joining(",")));
    }

    @Test
    public void clubs()
    {
        Assertions.assertEquals(
                "|A♣|,|2♣|,|3♣|,|4♣|,|5♣|,|6♣|,|7♣|,|8♣|,|9♣|,|10♣|,|J♣|,|Q♣|,|K♣|",
                this.jdk2Deck.clubs().stream().map(Object::toString).collect(Collectors.joining(",")));
    }

    @Test
    public void deal()
    {
        var jdk1Shuffle = this.jdk1Deck.shuffle(new Random(1));
        var jdk2Shuffle = this.jdk2Deck.shuffle(new Random(1));

        var jdk1Hand = this.jdk1Deck.deal(jdk1Shuffle, 5);
        var jdk2Hand = this.jdk2Deck.deal(jdk2Shuffle, 5);
        Assertions.assertEquals(jdk1Hand, jdk2Hand);
        Assertions.assertEquals(
                "|3♦|,|5♥|,|6♥|,|3♣|,|Q♣|",
                jdk2Hand.stream().sorted().map(Object::toString).collect(Collectors.joining(",")));
    }

    @Test
    public void shuffleAndDealHands()
    {
        var jdk1Hands = this.jdk1Deck.shuffleAndDeal(new Random(1), 5, 5);
        var jdk2Hands = this.jdk2Deck.shuffleAndDeal(new Random(1), 5, 5);
        Assertions.assertEquals(jdk1Hands, jdk2Hands);
        var hands = jdk2Hands.stream().map(each -> each.stream().sorted().map(Object::toString).collect(Collectors.joining(", "))).toList();
        List<String> expectedHands = List.of(
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
        var jdk1Shuffled = this.jdk1Deck.shuffle(new Random(1));
        var jdk2Shuffled = this.jdk2Deck.shuffle(new Random(1));
        var jdk1Hands = this.jdk1Deck.dealHands(jdk1Shuffled, 5, 5);
        var jdk2Hands = this.jdk2Deck.dealHands(jdk2Shuffled, 5, 5);
        Assertions.assertEquals(jdk1Hands, jdk2Hands);
        var hands = jdk2Hands.stream().map(each -> each.stream().sorted().map(Object::toString).collect(Collectors.joining(", "))).toList();
        List<String> expectedHands = List.of(
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
        var jdk2CardsBySuit = this.jdk2Deck.getCardsBySuit();
        Assertions.assertEquals(13, jdk2CardsBySuit.get(Suit.CLUBS).size());
        Assertions.assertEquals(
                "|A♣|, |2♣|, |3♣|, |4♣|, |5♣|, |6♣|, |7♣|, |8♣|, |9♣|, |10♣|, |J♣|, |Q♣|, |K♣|",
                Iterate.makeString(jdk2CardsBySuit.get(Suit.CLUBS)));
        Assertions.assertEquals(
                "|A♦|, |2♦|, |3♦|, |4♦|, |5♦|, |6♦|, |7♦|, |8♦|, |9♦|, |10♦|, |J♦|, |Q♦|, |K♦|",
                Iterate.makeString(jdk2CardsBySuit.get(Suit.DIAMONDS)));
        Assertions.assertEquals(
                "|A♠|, |2♠|, |3♠|, |4♠|, |5♠|, |6♠|, |7♠|, |8♠|, |9♠|, |10♠|, |J♠|, |Q♠|, |K♠|",
                Iterate.makeString(jdk2CardsBySuit.get(Suit.SPADES)));
        Assertions.assertEquals(
                "|A♥|, |2♥|, |3♥|, |4♥|, |5♥|, |6♥|, |7♥|, |8♥|, |9♥|, |10♥|, |J♥|, |Q♥|, |K♥|",
                Iterate.makeString(jdk2CardsBySuit.get(Suit.HEARTS)));
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
        Map<Suit, Long> map = new HashMap<>();
        for (Suit suit : Suit.values())
        {
            map.put(suit, 13L);
        }
        Assertions.assertEquals(map, this.jdk2Deck.countsBySuit());
    }

    @Test
    public void countsByRank()
    {
        Map<Rank, Long> map = new HashMap<>();
        for (Rank rank : Rank.values())
        {
            map.put(rank, 4L);
        }
        Assertions.assertEquals(map, this.jdk2Deck.countsByRank());
    }
}
