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
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import bny.codekatas.deckofcards.Card;
import bny.codekatas.deckofcards.Rank;
import bny.codekatas.deckofcards.Suit;
import org.eclipse.collections.impl.utility.Iterate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JDKImperativeDeckOfCardsAsListTest
{
    private JDKImperativeDeckOfCardsAsList jdkDeck = new JDKImperativeDeckOfCardsAsList();

    @Test
    public void allCards()
    {
        Assertions.assertEquals(52, this.jdkDeck.getCards().size());
        Assertions.assertEquals(new Card(Rank.ACE, Suit.SPADES), this.jdkDeck.getCards().get(0));
        Assertions.assertEquals(new Card(Rank.KING, Suit.CLUBS), this.jdkDeck.getCards().get(51));
    }

    @Test
    public void cardsAreImmutable()
    {
        var jdkCards = this.jdkDeck.getCards();
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> jdkCards.remove(0));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                jdkCards::clear);
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> jdkCards.add(null));
    }

    @Test
    public void diamonds()
    {
        Assertions.assertEquals(
                "|A♦|,|2♦|,|3♦|,|4♦|,|5♦|,|6♦|,|7♦|,|8♦|,|9♦|,|10♦|,|J♦|,|Q♦|,|K♦|",
                this.jdkDeck.getCardsBySuit().get(Suit.DIAMONDS).stream().map(Objects::toString).collect(Collectors.joining(",")));
        Assertions.assertTrue(Iterate.allSatisfy(this.jdkDeck.diamonds(), Card::isDiamonds));
    }

    @Test
    public void hearts()
    {
        Assertions.assertEquals(
                "|A♥|,|2♥|,|3♥|,|4♥|,|5♥|,|6♥|,|7♥|,|8♥|,|9♥|,|10♥|,|J♥|,|Q♥|,|K♥|",
                this.jdkDeck.getCardsBySuit().get(Suit.HEARTS).stream().map(Objects::toString).collect(Collectors.joining(",")));
        Assertions.assertTrue(Iterate.allSatisfy(this.jdkDeck.hearts(), Card::isHearts));
    }

    @Test
    public void spades()
    {
        Assertions.assertEquals(
                "|A♠|,|2♠|,|3♠|,|4♠|,|5♠|,|6♠|,|7♠|,|8♠|,|9♠|,|10♠|,|J♠|,|Q♠|,|K♠|",
                this.jdkDeck.getCardsBySuit().get(Suit.SPADES).stream().map(Objects::toString).collect(Collectors.joining(",")));
        Assertions.assertTrue(Iterate.allSatisfy(this.jdkDeck.spades(), Card::isSpades));
    }

    @Test
    public void clubs()
    {
        Assertions.assertEquals(
                "|A♣|,|2♣|,|3♣|,|4♣|,|5♣|,|6♣|,|7♣|,|8♣|,|9♣|,|10♣|,|J♣|,|Q♣|,|K♣|",
                this.jdkDeck.getCardsBySuit().get(Suit.CLUBS).stream().map(Objects::toString).collect(Collectors.joining(",")));
        Assertions.assertTrue(Iterate.allSatisfy(this.jdkDeck.clubs(), Card::isClubs));
    }

    @Test
    public void deal()
    {
        var jdkShuffle = this.jdkDeck.shuffle(new Random(1));
        var jdkHand = this.jdkDeck.deal(jdkShuffle, 5);
        Assertions.assertEquals(5, jdkHand.size());
        Assertions.assertEquals(47, jdkShuffle.size());
        Assertions.assertEquals(
                "|3♦|, |5♥|, |6♥|, |3♣|, |Q♣|",
                jdkHand.stream().sorted().map(Object::toString).collect(Collectors.joining(", ")));
    }

    @Test
    public void shuffleAndDealHands()
    {
        var jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        var hands = jdkHands.stream().map(each -> each.stream().sorted().map(Object::toString).collect(Collectors.joining(", "))).toList();
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
        var jdkShuffled = this.jdkDeck.shuffle(new Random(1));
        var jdkHands = this.jdkDeck.dealHands(jdkShuffled, 5, 5);
        Assertions.assertEquals(27, jdkShuffled.size());
        var hands = jdkHands.stream().map(each -> each.stream().sorted().map(Object::toString).collect(Collectors.joining(", "))).toList();
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
        var jdkCardsBySuit = this.jdkDeck.getCardsBySuit();
        Assertions.assertEquals(
                "|A♣|,|2♣|,|3♣|,|4♣|,|5♣|,|6♣|,|7♣|,|8♣|,|9♣|,|10♣|,|J♣|,|Q♣|,|K♣|",
                jdkCardsBySuit.get(Suit.CLUBS).stream().map(Objects::toString).collect(Collectors.joining(",")));
        Assertions.assertEquals(
                "|A♦|,|2♦|,|3♦|,|4♦|,|5♦|,|6♦|,|7♦|,|8♦|,|9♦|,|10♦|,|J♦|,|Q♦|,|K♦|",
                jdkCardsBySuit.get(Suit.DIAMONDS).stream().map(Objects::toString).collect(Collectors.joining(",")));
        Assertions.assertEquals(
                "|A♠|,|2♠|,|3♠|,|4♠|,|5♠|,|6♠|,|7♠|,|8♠|,|9♠|,|10♠|,|J♠|,|Q♠|,|K♠|",
                jdkCardsBySuit.get(Suit.SPADES).stream().map(Objects::toString).collect(Collectors.joining(",")));
        Assertions.assertEquals(
                "|A♥|,|2♥|,|3♥|,|4♥|,|5♥|,|6♥|,|7♥|,|8♥|,|9♥|,|10♥|,|J♥|,|Q♥|,|K♥|",
                jdkCardsBySuit.get(Suit.HEARTS).stream().map(Objects::toString).collect(Collectors.joining(",")));
    }

    @Test
    public void cardsBySuitIsImmutable()
    {
        var jdkCardsBySuit = this.jdkDeck.getCardsBySuit();
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> jdkCardsBySuit.remove(Suit.CLUBS));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                jdkCardsBySuit::clear);
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> jdkCardsBySuit.get(Suit.CLUBS).remove(0));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> jdkCardsBySuit.get(Suit.CLUBS).add(null));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                jdkCardsBySuit.get(Suit.CLUBS)::clear);
    }

    @Test
    public void countsBySuit()
    {
        Map<Suit, Long> map = new HashMap<>();
        for (Suit suit : Suit.values())
        {
            map.put(suit, 13L);
        }
        Assertions.assertEquals(map, this.jdkDeck.countsBySuit());
    }

    @Test
    public void countsByRank()
    {
        Map<Rank, Long> map = new HashMap<>();
        for (Rank rank : Rank.values())
        {
            map.put(rank, 4L);
        }
        Assertions.assertEquals(map, this.jdkDeck.countsByRank());
    }
}
