/*
 * Copyright 2024 The Bank of New York Mellon.
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

package bny.codekatas.deckofcards.custom.collections;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import java.util.stream.IntStream;

import bny.codekatas.deckofcards.Card;
import bny.codekatas.deckofcards.Rank;
import bny.codekatas.deckofcards.Suit;

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
