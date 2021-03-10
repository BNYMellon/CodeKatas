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

package bnymellon.codekatas.deckofcards.list.immutable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;

public class JDK8DeckOfCardsAsList
{
    private final List<Card> cards;
    private final Map<Suit, List<Card>> cardsBySuit;

    public JDK8DeckOfCardsAsList()
    {
        this.cards = Card.streamCards().sorted().collect(Collectors.toUnmodifiableList());
        this.cardsBySuit = Map.copyOf(this.cards.stream().collect(Collectors.groupingBy(Card::suit, Collectors.toUnmodifiableList())));
    }

    public Deque<Card> shuffle(Random random)
    {
        ArrayList<Card> shuffle = new ArrayList<>(this.cards);
        Collections.shuffle(shuffle, random);
        Collections.shuffle(shuffle, random);
        Collections.shuffle(shuffle, random);
        ArrayDeque<Card> deque = new ArrayDeque<>();
        shuffle.forEach(deque::push);
        return deque;
    }

    public Set<Card> deal(Deque<Card> deque, int count)
    {
        var hand = new HashSet<Card>();
        IntStream.range(0, count).forEach(i -> hand.add(deque.pop()));
        return hand;
    }

    public List<Set<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand)
    {
        var shuffled = this.shuffle(random);
        return this.dealHands(shuffled, hands, cardsPerHand);
    }

    public List<Set<Card>> dealHands(Deque<Card> shuffled, int hands, int cardsPerHand)
    {
        return IntStream.rangeClosed(1, hands)
                .mapToObj(i -> this.deal(shuffled, cardsPerHand))
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Card> diamonds()
    {
        return this.cardsBySuit.get(Suit.DIAMONDS);
    }

    public List<Card> hearts()
    {
        return this.cardsBySuit.get(Suit.HEARTS);
    }

    public List<Card> spades()
    {
        return this.cardsBySuit.get(Suit.SPADES);
    }

    public List<Card> clubs()
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

    public List<Card> getCards()
    {
        return this.cards;
    }

    public Map<Suit, List<Card>> getCardsBySuit()
    {
        return this.cardsBySuit;
    }
}
