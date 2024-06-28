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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import bny.codekatas.deckofcards.Card;
import bny.codekatas.deckofcards.Rank;
import bny.codekatas.deckofcards.Suit;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.MultiMapUtils;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.collections4.SetValuedMap;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.multiset.HashMultiSet;

public class ApacheCommonsDeckOfCardsAsSortedSet
{
    private SortedSet<Card> cards;
    private MultiValuedMap<Suit, Card> cardsBySuit;

    public ApacheCommonsDeckOfCardsAsSortedSet()
    {
        this.cards = SetUtils.unmodifiableSortedSet(
                Card.streamCards().collect(Collectors.toCollection(TreeSet::new)));
        SetValuedMap<Suit, Card> cbs = MultiMapUtils.newSetValuedHashMap();
        this.cards.forEach(card -> cbs.put(card.suit(), card));
        this.cardsBySuit = MultiMapUtils.unmodifiableMultiValuedMap(cbs);
    }

    public Deque<Card> shuffle(Random random)
    {
        List<Card> shuffled = new ArrayList<>(this.cards);
        Collections.shuffle(shuffled, random);
        Collections.shuffle(shuffled, random);
        Collections.shuffle(shuffled, random);
        ArrayDeque<Card> cards = new ArrayDeque<>();
        shuffled.forEach(cards::push);
        return cards;
    }

    public Set<Card> deal(Deque<Card> deque, int count)
    {
        Set<Card> hand = new HashSet<>();
        IntStream.range(0, count).forEach(i -> hand.add(deque.pop()));
        return hand;
    }

    public List<Set<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand)
    {
        Deque<Card> shuffled = this.shuffle(random);
        return this.dealHands(shuffled, hands, cardsPerHand);
    }

    public List<Set<Card>> dealHands(
            Deque<Card> shuffled,
            int hands,
            int cardsPerHand)
    {
        return ListUtils.unmodifiableList(
                IntStream.range(0, hands)
                        .mapToObj(i -> this.deal(shuffled, cardsPerHand))
                        .collect(Collectors.toList()));
    }

    public SortedSet<Card> diamonds()
    {
        return new TreeSet<>(this.cardsBySuit.get(Suit.DIAMONDS));
    }

    public SortedSet<Card> hearts()
    {
        return new TreeSet<>(this.cardsBySuit.get(Suit.HEARTS));
    }

    public SortedSet<Card> spades()
    {
        return new TreeSet<>(this.cardsBySuit.get(Suit.SPADES));
    }

    public SortedSet<Card> clubs()
    {
        return new TreeSet<>(this.cardsBySuit.get(Suit.CLUBS));
    }

    public Bag<Suit> countsBySuit()
    {
        return this.cards.stream()
                .map(Card::suit)
                .collect(Collectors.toCollection(HashBag::new));
    }

    public MultiSet<Rank> countsByRank()
    {
        return this.cards.stream()
                .map(Card::rank)
                .collect(Collectors.toCollection(HashMultiSet::new));
    }

    public SortedSet<Card> getCards()
    {
        return this.cards;
    }

    public MultiValuedMap<Suit, Card> getCardsBySuit()
    {
        return this.cardsBySuit;
    }
}
