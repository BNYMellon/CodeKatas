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

package bny.codekatas.deckofcards.sortedset.immutable;

import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

import bny.codekatas.deckofcards.Card;
import bny.codekatas.deckofcards.Rank;
import bny.codekatas.deckofcards.Suit;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import io.vavr.collection.SortedSet;
import io.vavr.collection.TreeSet;

public class VavrDeckOfCardsAsSortedSet
{
    private SortedSet<Card> cards;
    private Map<Suit, ? extends SortedSet<Card>> cardsBySuit;

    public VavrDeckOfCardsAsSortedSet()
    {
        this.cards = Card.streamCards()
                .collect(TreeSet.collector());
        this.cardsBySuit = this.cards.groupBy(Card::suit);
    }

    public List<Card> shuffle(Random random)
    {
        java.util.List<Card> shuffled = this.cards.toJavaList();
        Collections.shuffle(shuffled, random);
        Collections.shuffle(shuffled, random);
        Collections.shuffle(shuffled, random);
        return List.<Card>empty().pushAll(shuffled);
    }

    public Tuple2<Set<Card>, ? extends List<Card>> deal(List<Card> stack, int count)
    {
        Set<Card> hand = HashSet.empty();
        for (int i = 0; i < count; i++)
        {
            Tuple2<Card, ? extends List<Card>> cardTuple2 = stack.pop2();
            stack = cardTuple2._2();
            hand = hand.add(cardTuple2._1());
        }
        return Tuple.of(hand, stack);
    }

    public List<Set<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand)
    {
        List<Card> shuffled = this.shuffle(random);
        return this.dealHands(shuffled, hands, cardsPerHand);
    }

    public List<Set<Card>> dealHands(
            List<Card> shuffled,
            int hands,
            int cardsPerHand)
    {
        List<Set<Card>> list = List.empty();
        for (int i = 0; i < hands; i++)
        {
            Tuple2<Set<Card>, ? extends List<Card>> tuple2 =
                    this.deal(shuffled, cardsPerHand);
            shuffled = tuple2._2();
            list = list.append(tuple2._1());
        }
        return list;
    }

    public SortedSet<Card> diamonds()
    {
        return this.cardsBySuit.get(Suit.DIAMONDS).get();
    }

    public SortedSet<Card> hearts()
    {
        return this.cardsBySuit.get(Suit.HEARTS).get();
    }

    public SortedSet<Card> spades()
    {
        return this.cardsBySuit.get(Suit.SPADES).get();
    }

    public SortedSet<Card> clubs()
    {
        return this.cardsBySuit.get(Suit.CLUBS).get();
    }

    public Map<Suit, Long> countsBySuit()
    {
        return HashMap.ofAll(this.cards.collect(
                Collectors.groupingBy(
                        Card::suit,
                        Collectors.counting())));
    }

    public Map<Rank, Long> countsByRank()
    {
        return HashMap.ofAll(this.cards.collect(
                Collectors.groupingBy(
                        Card::rank,
                        Collectors.counting())));
    }

    public SortedSet<Card> getCards()
    {
        return this.cards;
    }

    public Map<Suit, ? extends SortedSet<Card>> getCardsBySuit()
    {
        return this.cardsBySuit;
    }
}
