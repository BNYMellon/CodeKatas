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
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;

import bny.codekatas.deckofcards.Card;
import bny.codekatas.deckofcards.Rank;
import bny.codekatas.deckofcards.Suit;

import com.google.common.base.Joiner;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import com.google.common.collect.Iterables;
import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GoogleGuavaDeckOfCardsAsSortedSetTest
{
    private JDKImperativeDeckOfCardsAsSortedSet jdkDeck = new JDKImperativeDeckOfCardsAsSortedSet();
    private GoogleGuavaDeckOfCardsAsSortedSet ggDeck = new GoogleGuavaDeckOfCardsAsSortedSet();

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
                () -> ggCards.remove(null));
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
        Assertions.assertEquals(
                "|A♦|,|2♦|,|3♦|,|4♦|,|5♦|,|6♦|,|7♦|,|8♦|,|9♦|,|10♦|,|J♦|,|Q♦|,|K♦|",
                Joiner.on(',').join(this.ggDeck.diamonds()));
    }

    @Test
    public void hearts()
    {
        Assertions.assertEquals(
                "|A♥|,|2♥|,|3♥|,|4♥|,|5♥|,|6♥|,|7♥|,|8♥|,|9♥|,|10♥|,|J♥|,|Q♥|,|K♥|",
                Joiner.on(',').join(this.ggDeck.hearts()));
    }

    @Test
    public void spades()
    {
        Assertions.assertEquals(
                "|A♠|,|2♠|,|3♠|,|4♠|,|5♠|,|6♠|,|7♠|,|8♠|,|9♠|,|10♠|,|J♠|,|Q♠|,|K♠|",
                Joiner.on(',').join(this.ggDeck.spades()));
    }

    @Test
    public void clubs()
    {
        Assertions.assertEquals(
                "|A♣|,|2♣|,|3♣|,|4♣|,|5♣|,|6♣|,|7♣|,|8♣|,|9♣|,|10♣|,|J♣|,|Q♣|,|K♣|",
                Joiner.on(',').join(this.ggDeck.clubs()));
    }

    @Test
    public void deal()
    {
        Deque<Card> jdkShuffle = this.jdkDeck.shuffle(new Random(1));
        Deque<Card> ggShuffle = this.ggDeck.shuffle(new Random(1));

        Set<Card> jdkHand = this.jdkDeck.deal(jdkShuffle, 5);
        Set<Card> ggHand = this.ggDeck.deal(ggShuffle, 5);
        Assertions.assertEquals(jdkHand, ggHand);
        Assertions.assertEquals(
                "|3♦|, |5♥|, |6♥|, |3♣|, |Q♣|",
                Joiner.on(", ").join(Ordering.natural().sortedCopy(Set.copyOf(ggHand))));
    }

    @Test
    public void shuffleAndDealHands()
    {
        List<Set<Card>> jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> ggHands = this.ggDeck.shuffleAndDeal(new Random(1), 5, 5);
        Assertions.assertEquals(jdkHands, ggHands);
        var sorted = Collections2.transform(
                ggHands, o -> Joiner.on(", ").join(Ordering.natural().sortedCopy(Set.copyOf(o))));
        var hands = Sets.newHashSet(Iterables.concat(sorted));
        Set<String> expectedHands = ImmutableSet.of(
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
        Deque<Card> jdkShuffled = this.jdkDeck.shuffle(new Random(1));
        Deque<Card> ggShuffled = this.ggDeck.shuffle(new Random(1));
        List<Set<Card>> jdkHands = this.jdkDeck.dealHands(jdkShuffled, 5, 5);
        List<Set<Card>> ggHands = this.ggDeck.dealHands(ggShuffled, 5, 5);
        Assertions.assertEquals(jdkHands, ggHands);
        var sorted = Collections2.transform(
                ggHands, o -> Joiner.on(", ").join(Ordering.natural().sortedCopy(Set.copyOf(o))));
        var hands = Sets.newHashSet(Iterables.concat(sorted));
        Set<String> expectedHands = ImmutableSet.of(
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
        Map<Suit, SortedSet<Card>> jdkCardsBySuit = this.jdkDeck.getCardsBySuit();
        ImmutableSetMultimap<Suit, Card> ggCardsBySuit = this.ggDeck.getCardsBySuit();
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
                () -> ggCardsBySuit.get(Suit.CLUBS).remove(null));
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
        for (Suit suit: Suit.values())
        {
            set.add(suit,13);
        }
        Assertions.assertEquals(set, this.ggDeck.countsBySuit());
    }

    @Test
    public void countsByRank()
    {
        Multiset<Rank> set = HashMultiset.create();
        for (Rank rank: Rank.values())
        {
            set.add(rank, 4);
        }
        Assertions.assertEquals(set, this.ggDeck.countsByRank());
    }
}
