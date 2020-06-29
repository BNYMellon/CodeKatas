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

package bnymellon.codekatas.deckofcards.custom.collections;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import java.util.stream.IntStream;

public class CustomCollectionsDeckOfCardsAsList {
    private MutableList<Card> cards;
    private MutableListMultimap<Suit, Card> cardsBySuit;

    /**
     * Use Custom Collections with {@link Card#streamCards()} to create an "immutable" List and store that in cards.
     * Group all of the cards by {@link Card#suit()} into an "immutable" Map and stored that in cardsBySuit.
     */
    public CustomCollectionsDeckOfCardsAsList() {
        this.cards = MutableList.fromStream(Card.streamCards().sorted()).asUnmodifiable();
        this.cardsBySuit = this.cards.groupByUnmodifiable(Card::suit);
    }

    public Deque<Card> shuffle(Random random) {
        // Shuffle the deck 3 times with the Random parameter and push the shuffled cards onto an ArrayDeque
        return this.cards.toList()
                .shuffle(random)
                .shuffle(random)
                .shuffle(random)
                .toCollection(ArrayDeque::new, ArrayDeque::push);
    }

    public MutableSet<Card> deal(Deque<Card> deque, int count) {
        var hand = MutableSet.<Card>empty();
        IntStream.range(0, count).forEach(i -> hand.add(deque.pop()));
        return hand;
    }

    public MutableList<MutableSet<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand) {
        var shuffled = this.shuffle(random);
        return this.dealHands(shuffled, hands, cardsPerHand);
    }

    public MutableList<MutableSet<Card>> dealHands(Deque<Card> shuffled, int hands, int cardsPerHand) {
        return MutableList.fromStream(IntStream.range(0, hands)
                .mapToObj(each -> this.deal(shuffled, cardsPerHand)))
                .asUnmodifiable();
    }

    public MutableList<Card> diamonds() {
        return this.cardsBySuit.get(Suit.DIAMONDS);
    }

    public MutableList<Card> hearts() {
        return this.cardsBySuit.get(Suit.HEARTS);
    }

    public MutableList<Card> spades() {
        return this.cardsBySuit.get(Suit.SPADES);
    }

    public MutableList<Card> clubs() {
        return this.cardsBySuit.get(Suit.CLUBS);
    }

    public MutableBag<Suit> countsBySuit() {
        return this.cards.countBy(Card::suit);
    }

    public MutableBag<Rank> countsByRank() {
        return this.cards.countBy(Card::rank);
    }

    public MutableList<Card> getCards() {
        return this.cards;
    }

    public MutableListMultimap<Suit, Card> getCardsBySuit() {
        return this.cardsBySuit;
    }
}
