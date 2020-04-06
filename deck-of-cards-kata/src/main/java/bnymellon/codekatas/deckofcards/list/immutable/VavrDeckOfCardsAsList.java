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

import java.util.Random;
import java.util.function.Function;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Set;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;

public class VavrDeckOfCardsAsList
{
    private List<Card> cards;
    private Map<Suit, ? extends List<Card>> cardsBySuit;

    /**
     * Use Vavr APIs with {@link Card#streamCards()} to create an ImmutableList and store that in cards.
     * Group all of the cards by {@link Card#suit()} into an Immutable Map of Lists and store that in cardsBySuit.
     *
     * Hint: Look at {@link List#collector()} and {@link List#groupBy(Function)}
     */
    public VavrDeckOfCardsAsList()
    {
        this.cards = null;
        this.cardsBySuit = null;
    }

    public List<Card> shuffle(Random random)
    {
        // Shuffle the deck 3 times with the Random parameter and push the shuffled cards onto a List
        return null;
    }

    public Tuple2<Set<Card>, ? extends List<Card>> deal(List<Card> stack, int count)
    {
        var hand = HashSet.<Card>empty();
        for (int i = 0; i < count; i++)
        {
            var cardTuple2 = stack.pop2();
            stack = cardTuple2._2();
            hand = hand.add(cardTuple2._1());
        }
        return Tuple.of(hand, stack);
    }

    public List<Set<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand)
    {
        var shuffled = this.shuffle(random);
        return this.dealHands(shuffled, hands, cardsPerHand);
    }

    public List<Set<Card>> dealHands(
            List<Card> shuffled,
            int hands,
            int cardsPerHand)
    {
        // Deal the number of hands with the cardsPerHand into an Immutable List<Set<Card>>
        return null;
    }

    public List<Card> diamonds()
    {
        // Return all of the diamonds as an Immutable List
        return null;
    }

    public List<Card> hearts()
    {
        // Return all of the hearts as an Immutable List
        return null;
    }

    public List<Card> spades()
    {
        // Return all of the spades as an Immutable List
        return null;
    }

    public List<Card> clubs()
    {
        // Return all of the clubs as an Immutable List
        return null;
    }

    public Map<Suit, Long> countsBySuit()
    {
        // return the count of cards by Suit
        return null;
    }

    public Map<Rank, Long> countsByRank()
    {
        // return the count of cards by Rank
        return null;
    }

    public List<Card> getCards()
    {
        return this.cards;
    }

    public Map<Suit, ? extends List<Card>> getCardsBySuit()
    {
        return this.cardsBySuit;
    }
}
