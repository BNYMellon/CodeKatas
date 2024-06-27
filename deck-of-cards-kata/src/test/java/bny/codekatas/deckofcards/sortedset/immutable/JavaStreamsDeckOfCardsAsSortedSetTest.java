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

package bny.codekatas.deckofcards.sortedset.immutable;

import java.util.Deque;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import bny.codekatas.deckofcards.Card;
import bny.codekatas.deckofcards.Rank;
import bny.codekatas.deckofcards.Suit;
import org.eclipse.collections.impl.utility.Iterate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JavaStreamsDeckOfCardsAsSortedSetTest
{
    private JDKImperativeDeckOfCardsAsSortedSet jdk1Deck = new JDKImperativeDeckOfCardsAsSortedSet();
    private JavaStreamsDeckOfCardsAsSortedSet jdk2Deck = new JavaStreamsDeckOfCardsAsSortedSet();

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
                () -> jdk2Cards.remove(null));
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
        Assertions.assertEquals("|A♦|, |2♦|, |3♦|, |4♦|, |5♦|, |6♦|, |7♦|, |8♦|, |9♦|, |10♦|, |J♦|, |Q♦|, |K♦|",
                Iterate.makeString(this.jdk2Deck.diamonds()));
    }

    @Test
    public void hearts()
    {
        Assertions.assertEquals("|A♥|, |2♥|, |3♥|, |4♥|, |5♥|, |6♥|, |7♥|, |8♥|, |9♥|, |10♥|, |J♥|, |Q♥|, |K♥|",
                Iterate.makeString(jdk2Deck.hearts()));
    }

    @Test
    public void spades()
    {
        Assertions.assertEquals("|A♠|, |2♠|, |3♠|, |4♠|, |5♠|, |6♠|, |7♠|, |8♠|, |9♠|, |10♠|, |J♠|, |Q♠|, |K♠|",
                Iterate.makeString(this.jdk2Deck.spades()));
    }

    @Test
    public void clubs()
    {
        Assertions.assertEquals("|A♣|, |2♣|, |3♣|, |4♣|, |5♣|, |6♣|, |7♣|, |8♣|, |9♣|, |10♣|, |J♣|, |Q♣|, |K♣|",
                Iterate.makeString(this.jdk2Deck.clubs()));
    }

    @Test
    public void deal()
    {
        Deque<Card> jdk1Shuffle = this.jdk1Deck.shuffle(new Random(1));
        Deque<Card> jdk2Shuffle = this.jdk2Deck.shuffle(new Random(1));

        Set<Card> jdk1Hand = this.jdk1Deck.deal(jdk1Shuffle, 5);
        Set<Card> jdk2Hand = this.jdk2Deck.deal(jdk2Shuffle, 5);
        Assertions.assertEquals(jdk1Hand, jdk2Hand);
        Assertions.assertEquals(
                "|3♦|,|5♥|,|6♥|,|3♣|,|Q♣|",
                jdk2Hand.stream().sorted().map(Object::toString).collect(Collectors.joining(",")));
    }

    @Test
    public void shuffleAndDealHands()
    {
        List<Set<Card>> jdk1Hands = this.jdk1Deck.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> jdk2Hands = this.jdk2Deck.shuffleAndDeal(new Random(1), 5, 5);
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
        Deque<Card> jdk1Shuffled = this.jdk1Deck.shuffle(new Random(1));
        Deque<Card> jdk2Shuffled = this.jdk2Deck.shuffle(new Random(1));
        List<Set<Card>> jdk1Hands = this.jdk1Deck.dealHands(jdk1Shuffled, 5, 5);
        List<Set<Card>> jdk2Hands = this.jdk2Deck.dealHands(jdk2Shuffled, 5, 5);
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
        Map<Suit, SortedSet<Card>> jdkCardsBySuit = this.jdk1Deck.getCardsBySuit();
        Assertions.assertEquals(13, jdkCardsBySuit.get(Suit.CLUBS).size());
        Assertions.assertEquals("|A♣|, |2♣|, |3♣|, |4♣|, |5♣|, |6♣|, |7♣|, |8♣|, |9♣|, |10♣|, |J♣|, |Q♣|, |K♣|",
                Iterate.makeString(jdkCardsBySuit.get(Suit.CLUBS)));
        Assertions.assertEquals("|A♦|, |2♦|, |3♦|, |4♦|, |5♦|, |6♦|, |7♦|, |8♦|, |9♦|, |10♦|, |J♦|, |Q♦|, |K♦|",
                Iterate.makeString(jdkCardsBySuit.get(Suit.DIAMONDS)));
        Assertions.assertEquals("|A♠|, |2♠|, |3♠|, |4♠|, |5♠|, |6♠|, |7♠|, |8♠|, |9♠|, |10♠|, |J♠|, |Q♠|, |K♠|",
                Iterate.makeString(jdkCardsBySuit.get(Suit.SPADES)));
        Assertions.assertEquals("|A♥|, |2♥|, |3♥|, |4♥|, |5♥|, |6♥|, |7♥|, |8♥|, |9♥|, |10♥|, |J♥|, |Q♥|, |K♥|",
                Iterate.makeString(jdkCardsBySuit.get(Suit.HEARTS)));
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
                () -> jdk2CardsBySuit.get(Suit.CLUBS).remove(null));
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
        for (Suit suit: Suit.values())
        {
            map.put(suit, 13L);
        }
        Assertions.assertEquals(map, this.jdk2Deck.countsBySuit());
    }

    @Test
    public void countsByRank()
    {
        Map<Rank, Long> map = new HashMap<>();
        for (Rank rank: Rank.values())
        {
            map.put(rank, 4L);
        }
        Assertions.assertEquals(map, this.jdk2Deck.countsByRank());
    }
}
