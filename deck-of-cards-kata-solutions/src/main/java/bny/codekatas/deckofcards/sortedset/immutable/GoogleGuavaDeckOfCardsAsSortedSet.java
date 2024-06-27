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
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import bny.codekatas.deckofcards.Card;
import bny.codekatas.deckofcards.Rank;
import bny.codekatas.deckofcards.Suit;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Multiset;

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
        this.cards = Card.streamCards()
                .collect(ImmutableSortedSet.toImmutableSortedSet(Comparator.naturalOrder()));
        ImmutableSetMultimap.Builder<Suit, Card> builder =
                new ImmutableSetMultimap.Builder<Suit, Card>().orderValuesBy(Comparator.naturalOrder());
        this.cards.forEach(card -> builder.put(card.suit(), card));
        this.cardsBySuit = builder.build();
    }

    public Deque<Card> shuffle(Random random)
    {
        var shuffled = new ArrayList<>(this.cards);
        Collections.shuffle(shuffled, random);
        Collections.shuffle(shuffled, random);
        Collections.shuffle(shuffled, random);
        var cards = new ArrayDeque<Card>();
        shuffled.forEach(cards::push);
        return cards;
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
        return IntStream.range(0, hands)
                .mapToObj(i -> this.deal(shuffled, cardsPerHand))
                .collect(ImmutableList.toImmutableList());
    }

    public Set<Card> diamonds()
    {
        return this.cardsBySuit.get(Suit.DIAMONDS);
    }

    public Set<Card> hearts()
    {
        return this.cardsBySuit.get(Suit.HEARTS);
    }

    public Set<Card> spades()
    {
        return this.cardsBySuit.get(Suit.SPADES);
    }

    public Set<Card> clubs()
    {
        return this.cardsBySuit.get(Suit.CLUBS);
    }

    public Multiset<Suit> countsBySuit()
    {
        return this.cards.stream()
                .map(Card::suit)
                .collect(Collectors.toCollection(HashMultiset::create));
    }

    public Multiset<Rank> countsByRank()
    {
        return this.cards.stream()
                .map(Card::rank)
                .collect(Collectors.toCollection(HashMultiset::create));
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
