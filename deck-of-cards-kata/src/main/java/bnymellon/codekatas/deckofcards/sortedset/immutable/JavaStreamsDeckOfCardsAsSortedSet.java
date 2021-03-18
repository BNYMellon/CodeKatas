/*
 * Copyright 2020 The Bank of New York Mellon.
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

import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;

public class JavaStreamsDeckOfCardsAsSortedSet
{
    private SortedSet<Card> cards;
    private Map<Suit, SortedSet<Card>> cardsBySuit;

    /**
     * TODO Replace the null values below with something more useful.
     * Use Java 8 Streams APIs with {@link Card#streamCards()} to create an "immutable" SortedSet and store that in cards.
     * Group all of the cards by {@link Card#suit()} into an "immutable" Map and stored that in cardsBySuit.
     *
     * Hint: Look at {@link Collectors#groupingBy(Function)}, {@link Collections#unmodifiableSortedSet(SortedSet)}
     * {@link Collectors#collectingAndThen(Collector, Function)}, {@link Collectors#mapping(Function, Collector)}
     */
    public JavaStreamsDeckOfCardsAsSortedSet()
    {
        this.cards = null;
        this.cardsBySuit = null;
    }

    public Deque<Card> shuffle(Random random)
    {
        // TODO Shuffle the deck 3 times with the Random parameter and push the shuffled cards onto an ArrayDeque
        // Hint: Look at IntStream.range() or IntStream.rangeClosed() and Collections.shuffle()
        return null;
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
        // TODO Deal the number of hands with the cardsPerHand into an "immutable" List<Set<Card>>
        return null;
    }

    public SortedSet<Card> diamonds()
    {
        // TODO return all diamonds as an "Immutable" SortedSet
        return null;
    }

    public SortedSet<Card> hearts()
    {
        // TODO return all hearts as an "Immutable" SortedSet
        return null;
    }

    public SortedSet<Card> spades()
    {
        // TODO return all spades as an "Immutable" SortedSet
        return null;
    }

    public SortedSet<Card> clubs()
    {
        // TODO return all clubs as an "Immutable" SortedSet
        return null;
    }

    public Map<Suit, Long> countsBySuit()
    {
        // TODO return the count of cards by Suit
        // Hint: Look at Collectors.groupingBy() and Collectors.counting()
        return null;
    }

    public Map<Rank, Long> countsByRank()
    {
        // TODO return the count of cards by Rank
        // Hint: Look at Collectors.groupingBy() and Collectors.counting()
        return null;
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
