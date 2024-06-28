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

import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import bny.codekatas.deckofcards.Card;
import bny.codekatas.deckofcards.Rank;
import bny.codekatas.deckofcards.Suit;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.FluentIterable;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.functors.ClosureTransformer;
import org.apache.commons.collections4.multiset.HashMultiSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApacheCommonsDeckOfCardsAsSortedSetTest
{
    private JDKImperativeDeckOfCardsAsSortedSet jdkDeck = new JDKImperativeDeckOfCardsAsSortedSet();
    private ApacheCommonsDeckOfCardsAsSortedSet acDeck = new ApacheCommonsDeckOfCardsAsSortedSet();

    @Test
    public void allCards()
    {
        Assertions.assertEquals(this.jdkDeck.getCards(), this.acDeck.getCards());
    }

    @Test
    public void cardsAreImmutable()
    {
        var acCards = this.acDeck.getCards();
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> acCards.remove(null));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                acCards::clear);
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> acCards.add(null));
    }

    @Test
    public void diamonds()
    {
        Assertions.assertEquals(
                "|A♦|, |2♦|, |3♦|, |4♦|, |5♦|, |6♦|, |7♦|, |8♦|, |9♦|, |10♦|, |J♦|, |Q♦|, |K♦|",
                IterableUtils.toString(this.acDeck.diamonds(), Object::toString, ", ", "", ""));
    }

    @Test
    public void hearts()
    {
        Assertions.assertEquals(
                "|A♥|, |2♥|, |3♥|, |4♥|, |5♥|, |6♥|, |7♥|, |8♥|, |9♥|, |10♥|, |J♥|, |Q♥|, |K♥|",
                IterableUtils.toString(this.acDeck.hearts(), Object::toString, ", ", "", ""));
    }

    @Test
    public void spades()
    {
        Assertions.assertEquals(
                "|A♠|, |2♠|, |3♠|, |4♠|, |5♠|, |6♠|, |7♠|, |8♠|, |9♠|, |10♠|, |J♠|, |Q♠|, |K♠|",
                IterableUtils.toString(this.acDeck.spades(), Object::toString, ", ", "", ""));
    }

    @Test
    public void clubs()
    {
        Assertions.assertEquals(
                "|A♣|, |2♣|, |3♣|, |4♣|, |5♣|, |6♣|, |7♣|, |8♣|, |9♣|, |10♣|, |J♣|, |Q♣|, |K♣|",
                IterableUtils.toString(this.acDeck.clubs(), Object::toString, ", ", "", ""));
    }

    @Test
    public void deal()
    {
        Deque<Card> jdkShuffle = this.jdkDeck.shuffle(new Random(1));
        Deque<Card> acShuffle = this.acDeck.shuffle(new Random(1));

        Set<Card> jdkHand = this.jdkDeck.deal(jdkShuffle, 5);
        Set<Card> acHand = this.acDeck.deal(acShuffle, 5);
        Assertions.assertEquals(jdkHand, acHand);
        Assertions.assertEquals(
                "|3♦|, |5♥|, |6♥|, |3♣|, |Q♣|",
                IterableUtils.toString(acHand.stream().sorted().toList(), Object::toString, ", ", "", ""));
    }

    @Test
    public void shuffleAndDealHands()
    {
        List<Set<Card>> jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> acHands = this.acDeck.shuffleAndDeal(new Random(1), 5, 5);
        Assertions.assertEquals(jdkHands, acHands);
        var hands = new HashSet<>();
        FluentIterable.of(acHands)
                .transform(FluentIterable::of)
                .transform(FluentIterable::toList)
                .transform(ClosureTransformer.closureTransformer(each -> each.sort(Comparator.naturalOrder())))
                .transform(each -> IterableUtils.toString(each, Object::toString, ", ", "", ""))
                .copyInto(hands);
        Set<String> expectedHands = Set.of(
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
        Deque<Card> acShuffled = this.acDeck.shuffle(new Random(1));
        List<Set<Card>> jdkHands = this.jdkDeck.dealHands(jdkShuffled, 5, 5);
        List<Set<Card>> acHands = this.acDeck.dealHands(acShuffled, 5, 5);
        Assertions.assertEquals(jdkHands, acHands);
        var hands = new HashSet<>();
        FluentIterable.of(acHands)
                .transform(FluentIterable::of)
                .transform(FluentIterable::toList)
                .transform(ClosureTransformer.closureTransformer(each -> each.sort(Comparator.naturalOrder())))
                .transform(each -> IterableUtils.toString(each, Object::toString, ", ", "", ""))
                .copyInto(hands);
        Set<String> expectedHands = Set.of(
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
        MultiValuedMap<Suit, Card> acCardsBySuit = this.acDeck.getCardsBySuit();
        Assertions.assertEquals(jdkCardsBySuit.get(Suit.CLUBS), new TreeSet<>(acCardsBySuit.get(Suit.CLUBS)));
        Assertions.assertEquals(
                "|A♣|,|2♣|,|3♣|,|4♣|,|5♣|,|6♣|,|7♣|,|8♣|,|9♣|,|10♣|,|J♣|,|Q♣|,|K♣|",
                IterableUtils.toString(new TreeSet<>(this.acDeck.getCardsBySuit().get(Suit.CLUBS)), Object::toString, ",", "", ""));
        Assertions.assertEquals(
                "|A♦|,|2♦|,|3♦|,|4♦|,|5♦|,|6♦|,|7♦|,|8♦|,|9♦|,|10♦|,|J♦|,|Q♦|,|K♦|",
                IterableUtils.toString(new TreeSet<>(this.acDeck.getCardsBySuit().get(Suit.DIAMONDS)), Object::toString, ",", "", ""));
        Assertions.assertEquals(
                "|A♠|,|2♠|,|3♠|,|4♠|,|5♠|,|6♠|,|7♠|,|8♠|,|9♠|,|10♠|,|J♠|,|Q♠|,|K♠|",
                IterableUtils.toString(new TreeSet<>(this.acDeck.getCardsBySuit().get(Suit.SPADES)), Object::toString, ",", "", ""));
        Assertions.assertEquals(
                "|A♥|,|2♥|,|3♥|,|4♥|,|5♥|,|6♥|,|7♥|,|8♥|,|9♥|,|10♥|,|J♥|,|Q♥|,|K♥|",
                IterableUtils.toString(new TreeSet<>(this.acDeck.getCardsBySuit().get(Suit.HEARTS)), Object::toString, ",", "", ""));
    }

    @Test
    public void cardsBySuitIsImmutable()
    {
        var acCardsBySuit = this.acDeck.getCardsBySuit();
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> acCardsBySuit.remove(Suit.CLUBS));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                acCardsBySuit::clear);
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> acCardsBySuit.get(Suit.CLUBS).remove(null));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> acCardsBySuit.get(Suit.CLUBS).add(null));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                acCardsBySuit.get(Suit.CLUBS)::clear);
    }

    @Test
    public void countsBySuit()
    {
        Bag<Suit> bag = new HashBag<>();
        for (Suit suit : Suit.values())
        {
            bag.add(suit, 13);
        }
        Assertions.assertEquals(bag, this.acDeck.countsBySuit());
    }

    @Test
    public void countsByRank()
    {
        MultiSet<Rank> set = new HashMultiSet<>();
        for (Rank rank : Rank.values())
        {
            set.add(rank, 4);
        }
        Assertions.assertEquals(set, this.acDeck.countsByRank());
    }
}
