/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.deckofcards.custom.collections;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import java.util.stream.IntStream;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;

public class CustomCollectionsDeckOfCardsAsList
{
    private ImmutableList<Card> cards;
    private MutableListMultimap<Suit, Card> cardsBySuit;

    /**
     * Use Custom Collections with {@link Card#streamCards()} to create an "immutable" List and store that in cards.
     * Group all of the cards by {@link Card#suit()} into an "immutable" Map and stored that in cardsBySuit.
     */
    public CustomCollectionsDeckOfCardsAsList()
    {
        // There are two ways to build an ImmutableList from a Stream
        // this.cards = Card.streamCards().sorted().collect(ImmutableList.collector());
        this.cards = ImmutableList.fromStream(Card.streamCards().sorted());
        this.cardsBySuit = this.cards.groupByUnmodifiable(Card::suit);
    }

    public Deque<Card> shuffle(Random random)
    {
        // Shuffle the deck 3 times with the Random parameter and push the shuffled cards onto an ArrayDeque
        return this.cards.toList()
                .shuffle(random)
                .shuffle(random)
                .shuffle(random)
                .toCollection(ArrayDeque::new, ArrayDeque::push);
    }

    public Set<Card> deal(Deque<Card> deque, int count)
    {
        var hand = MutableSet.<Card>empty();
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
        return IntStream.range(0, hands)
                .mapToObj(each -> this.deal(shuffled, cardsPerHand))
                .collect(ImmutableList.collector());
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

    public Bag<Suit> countsBySuit()
    {
        return this.cards.countBy(Card::suit);
    }

    public Bag<Rank> countsByRank()
    {
        return this.cards.countBy(Card::rank);
    }

    public List<Card> getCards()
    {
        return this.cards;
    }

    public MutableListMultimap<Suit, Card> getCardsBySuit()
    {
        return this.cardsBySuit;
    }
}
