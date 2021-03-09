/*
 * Copyright 2021 The Bank of New York Mellon.
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

package bnymellon.codekatas.deckofcards.sortedset.immutable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;

public class JDK8DeckOfCardsAsSortedSet
{
    private final SortedSet<Card> cards;
    private final Map<Suit, SortedSet<Card>> cardsBySuit;

    public JDK8DeckOfCardsAsSortedSet()
    {
        this.cards = Collections.unmodifiableSortedSet(
                Card.streamCards().sorted().collect(Collectors.toCollection(TreeSet::new)));
        this.cardsBySuit = Map.copyOf(this.cards.stream()
                .collect(Collectors.groupingBy(
                        Card::suit,
                        Collectors.collectingAndThen(
                                Collectors.toCollection(TreeSet::new),
                                Collections::unmodifiableSortedSet))));
    }

    public Deque<Card> shuffle(Random random)
    {
        ArrayList<Card> shuffled = new ArrayList<>(this.cards);
        Collections.shuffle(shuffled, random);
        Collections.shuffle(shuffled, random);
        Collections.shuffle(shuffled, random);
        ArrayDeque<Card> deque = new ArrayDeque<>();
        shuffled.forEach(deque::push);
        return deque;
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
        return IntStream.rangeClosed(1, hands)
                .mapToObj(i -> this.deal(shuffled, cardsPerHand))
                .collect(Collectors.toUnmodifiableList());
    }

    public SortedSet<Card> diamonds()
    {
        return this.cardsBySuit.get(Suit.DIAMONDS);
    }

    public SortedSet<Card> hearts()
    {
        return this.cardsBySuit.get(Suit.HEARTS);
    }

    public SortedSet<Card> spades()
    {
        return this.cardsBySuit.get(Suit.SPADES);
    }

    public SortedSet<Card> clubs()
    {
        return this.cardsBySuit.get(Suit.CLUBS);
    }

    public Map<Suit, Long> countsBySuit()
    {
        return this.cards.stream()
                .collect(Collectors.groupingBy(Card::suit, Collectors.counting()));
    }

    public Map<Rank, Long> countsByRank()
    {
        return this.cards.stream()
                .collect(Collectors.groupingBy(Card::rank, Collectors.counting()));
    }

    public SortedSet<Card> getCards()
    {
        return this.cards;
    }

    public Map<Suit, SortedSet<Card>> getCardsBySuit()
    {
        return this.cardsBySuit;
    }
}
