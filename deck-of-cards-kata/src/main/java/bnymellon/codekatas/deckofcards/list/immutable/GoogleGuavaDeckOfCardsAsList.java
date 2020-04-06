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

package bnymellon.codekatas.deckofcards.list.immutable;

import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;

public class GoogleGuavaDeckOfCardsAsList
{
    private ImmutableList<Card> cards;
    private ImmutableListMultimap<Suit, Card> cardsBySuit;

    /**
     * TODO Replace the null values below with something more useful.
     * Use Google Guava APIs with {@link Card#streamCards()} to create an ImmutableSortedSet and store that in cards.
     * Group all of the cards by {@link Card#suit()} into an ImmutableSetMultimap and stored that in cardsBySuit.
     *
     * Hint: Look at {@link Multimaps#index(Iterable, Function)} and {@link ImmutableList#copyOf(Collection)}
     */
    public GoogleGuavaDeckOfCardsAsList()
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
        var hand = new HashSet<Card>();
        IntStream.range(0, count).forEach(i -> hand.add(deque.pop()));
        return hand;
    }

    public ImmutableList<Set<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand)
    {
        var shuffled = this.shuffle(random);
        return this.dealHands(shuffled, hands, cardsPerHand);
    }

    public ImmutableList<Set<Card>> dealHands(Deque<Card> shuffled, int hands, int cardsPerHand)
    {
        // TODO Deal the number of hands with the cardsPerHand into an ImmutableList<Set<Card>>
        return null;
    }

    public ImmutableList<Card> diamonds()
    {
        // TODO Return all of the diamonds as an ImmutableList
        return null;
    }

    public ImmutableList<Card> hearts()
    {
        // TODO Return all of the hearts as an ImmutableList
        return null;
    }

    public ImmutableList<Card> spades()
    {
        // TODO Return all of the spades as an ImmutableList
        return null;
    }

    public ImmutableList<Card> clubs()
    {
        // TODO Return all of the clubs as an ImmutableList
        return null;
    }

    public Multiset<Suit> countsBySuit()
    {
        // TODO return the count of cards by Suit
        return null;
    }

    public Multiset<Rank> countsByRank()
    {
        // TODO return the count of cards by Rank
        return null;
    }

    public ImmutableList<Card> getCards()
    {
        return this.cards;
    }

    public ImmutableListMultimap<Suit, Card> getCardsBySuit()
    {
        return this.cardsBySuit;
    }
}
