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

package bnymellon.codekatas.deckofcards.list.immutable;

import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.collections4.MultiMapUtils;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;

public class ApacheCommonsDeckOfCardsAsList
{
    private List<Card> cards;
    private MultiValuedMap<Suit, Card> cardsBySuit;

    /**
     * TODO Replace the null values below with something more useful.
     * Use Apache Commons Collections API with  {@link Card#streamCards()} to create an "immutable" List and store that in cards.
     * Group all of the cards by {@link Card#suit()} into an "immutable" ListValuedMap and stored that in cardsBySuit.
     *
     * Hint: Look at {@link MultiValuedMap}, {@link ListValuedMap} and {@link MultiMapUtils#unmodifiableMultiValuedMap(MultiValuedMap)}
     */
    public ApacheCommonsDeckOfCardsAsList()
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

    public List<Set<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand)
    {
        var shuffled = this.shuffle(random);
        return this.dealHands(shuffled, hands, cardsPerHand);
    }

    public List<Set<Card>> dealHands(Deque<Card> shuffled, int hands, int cardsPerHand)
    {
        // TODO Deal the number of hands with the cardsPerHand into an "immutable" List<Set<Card>>
        return null;
    }

    public List<Card> diamonds()
    {
        // TODO Return all of the diamonds as a List
        return null;
    }

    public List<Card> hearts()
    {
        // TODO Return all of the hearts as a List
        return null;
    }

    public List<Card> spades()
    {
        // TODO Return all of the spades as a List
        return null;
    }

    public List<Card> clubs()
    {
        // TODO Return all of the clubs as a List
        return null;
    }

    public Bag<Suit> countsBySuit()
    {
        // TODO return the count of cards by Suit
        // Hint: Look at HashBag
        return null;
    }

    public MultiSet<Rank> countsByRank()
    {
        // TODO return the count of cards by Rank
        // Hint: Look at HashMultiset.  Compare HashMultiset to HashBag.  What is the difference?
        return null;
    }

    public List<Card> getCards()
    {
        return this.cards;
    }

    public MultiValuedMap<Suit, Card> getCardsBySuit()
    {
        return this.cardsBySuit;
    }
}
