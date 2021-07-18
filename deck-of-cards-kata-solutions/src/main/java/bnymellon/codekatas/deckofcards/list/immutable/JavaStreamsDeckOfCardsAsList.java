  /**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;

public class JavaStreamsDeckOfCardsAsList
{
    private final List<Card> cards;
    private final Map<Suit, List<Card>> cardsBySuit;

    public JavaStreamsDeckOfCardsAsList()
    {
        this.cards = Card.streamCards().sorted().toList();
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
                .toList();
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
