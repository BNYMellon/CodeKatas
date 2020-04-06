/*
 * Copyright 2020 BNY Mellon.
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

import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Multiset;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;

public class GoogleGuavaDeckOfCardsAsSortedSet
{
    private ImmutableSortedSet<Card> cards;
    private ImmutableSetMultimap<Suit, Card> cardsBySuit;

    /**
     * TODO Replace the null values below with something more useful.
     * Use Google Guava APIs with {@link Card#streamCards()} to create an ImmutableSortedSet and store that in cards.
     * Group all of the cards by {@link Card#suit()} into an ImmutableSetMultimap and stored that in cardsBySuit.
     *
     * Hint: Look at {@link ImmutableSetMultimap.Builder#orderValuesBy(Comparator)}
     */
    public GoogleGuavaDeckOfCardsAsSortedSet()
    {
        this.cards = null;
        this.cardsBySuit = null;
    }

    public Deque<Card> shuffle(Random random)
    {
        // TODO Shuffle the deck 3 times with the Random parameter and push the shuffled cards onto an ArrayDeque
        return null;
    }

    public Set<Card> deal(Deque<Card> deque, int count)
    {
        Set<Card> hand = new HashSet<>();
        IntStream.range(0, count).forEach(i -> hand.add(deque.pop()));
        return hand;
    }

    public ImmutableList<Set<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand)
    {
        Deque<Card> shuffled = this.shuffle(random);
        return this.dealHands(shuffled, hands, cardsPerHand);
    }

    public ImmutableList<Set<Card>> dealHands(
            Deque<Card> shuffled,
            int hands,
            int cardsPerHand)
    {
        // TODO Deal the number of hands with the cardsPerHand into an ImmutableList<Set<Card>>
        return null;
    }

    public Set<Card> diamonds()
    {
        // TODO Return all of the diamonds as an Immutable "Sorted" Set
        return null;
    }

    public Set<Card> hearts()
    {
        // TODO Return all of the hearts as an Immutable "Sorted" Set
        return null;
    }

    public Set<Card> spades()
    {
        // TODO Return all of the spades as an Immutable "Sorted" Set
        return null;
    }

    public Set<Card> clubs()
    {
        // TODO Return all of the clubs as an Immutable "Sorted" Set
        return null;
    }

    public Multiset<Suit> countsBySuit()
    {
        // TODO return the count of cards by Suit
        // Hint: Look at HashMultiset
        return null;
    }

    public Multiset<Rank> countsByRank()
    {
        // TODO return the count of cards by Rank
        // Hint: Look at HashMultiset
        return null;
    }

    public ImmutableSortedSet<Card> getCards()
    {
        return this.cards;
    }

    public ImmutableSetMultimap<Suit, Card> getCardsBySuit()
    {
        return this.cardsBySuit;
    }
}
