/*
 * Copyright 2017 BNY Mellon.
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

import java.util.Random;
import java.util.function.Function;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import io.vavr.collection.SortedSet;
import io.vavr.collection.TreeSet;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;

public class VavrDeckOfCardsAsSortedSet
{
    private SortedSet<Card> cards;
    private Map<Suit, ? extends SortedSet<Card>> cardsBySuit;

    /**
     * TODO Replace the null values below with something more useful.
     * Use Vavr APIs with {@link Card#streamCards()} to create an "immutable" SortedSet and store that in cards.
     * Group all of the cards by {@link Card#suit} into an "immutable" Map and stored that in cardsBySuit.
     *
     * Hint: Look at {@link TreeSet#collector()} and {@link SortedSet#groupBy(Function)}
     */
    public VavrDeckOfCardsAsSortedSet()
    {
        this.cards = null;
        this.cardsBySuit = null;
    }

    public List<Card> shuffle(Random random)
    {
        // TODO Shuffle the deck 3 times with the Random parameter and push the shuffled cards onto a List
        // Remember: List above is immutable
        return null;
    }

    public Tuple2<Set<Card>, ? extends List<Card>> deal(List<Card> stack, int count)
    {
        Set<Card> hand = HashSet.empty();
        for (int i = 0; i < count; i++)
        {
            Tuple2<Card, ? extends List<Card>> cardTuple2 = stack.pop2();
            stack = cardTuple2._2();
            hand = hand.add(cardTuple2._1());
        }
        return Tuple.of(hand, stack);
    }

    public List<Set<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand)
    {
        List<Card> shuffled = this.shuffle(random);
        return this.dealHands(shuffled, hands, cardsPerHand);
    }

    public List<Set<Card>> dealHands(
            List<Card> shuffled,
            int hands,
            int cardsPerHand)
    {
        // TODO Deal the number of hands with the cardsPerHand into an "immutable" List<Set<Card>>
        // Remember: List above is immutable
        return null;
    }

    public SortedSet<Card> diamonds()
    {
        // TODO return all the diamonds as an ImmutableSortedSet
        return null;
    }

    public SortedSet<Card> hearts()
    {
        // TODO return all the hearts as an ImmutableSortedSet
        return null;
    }

    public SortedSet<Card> spades()
    {
        // TODO return all the spades as an ImmutableSortedSet
        return null;
    }

    public SortedSet<Card> clubs()
    {
        // TODO return all the clubs as an ImmutableSortedSet
        return null;
    }

    public Map<Suit, Long> countsBySuit()
    {
        // TODO return the count of cards by Suit
        // Hint: Look at using Stream to calculate this because Vavr does not have a Bag or Multiset
        return null;
    }

    public Map<Rank, Long> countsByRank()
    {
        // TODO return the count of cards by Rank
        // Hint: Look at using Stream to calculate this because Vavr does not have a Bag
        return null;
    }

    public SortedSet<Card> getCards()
    {
        return this.cards;
    }

    public Map<Suit, ? extends SortedSet<Card>> getCardsBySuit()
    {
        return this.cardsBySuit;
    }
}
