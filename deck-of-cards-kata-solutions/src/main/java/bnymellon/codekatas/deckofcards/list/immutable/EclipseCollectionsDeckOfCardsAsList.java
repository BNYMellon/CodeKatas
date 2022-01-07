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

import java.util.Random;
import java.util.Set;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;
import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.multimap.list.ImmutableListMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.impl.list.primitive.IntInterval;

public record EclipseCollectionsDeckOfCardsAsList(
        ImmutableList<Card> cards,
        ImmutableListMultimap<Suit, Card> cardsBySuit)
{
    public EclipseCollectionsDeckOfCardsAsList()
    {
        this(Card.lazyCards().toImmutableSortedList(),
                Card.lazyCards().toImmutableSortedList().groupBy(Card::suit));
    }

    public MutableStack<Card> shuffle(Random random)
    {
        return IntInterval.oneTo(3)
                .injectInto(this.cards.toList(), (list, i) -> list.shuffleThis(random))
                .toStack();
    }

    public MutableSet<Card> deal(MutableStack<Card> stack, int count)
    {
        return stack.pop(count).toSet();
    }

    public ImmutableList<Set<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand)
    {
        return this.dealHands(this.shuffle(random), hands, cardsPerHand);
    }

    public ImmutableList<Set<Card>> dealHands(
            MutableStack<Card> shuffled,
            int hands,
            int cardsPerHand)
    {
        return IntInterval.oneTo(hands).collect(i -> this.deal(shuffled, cardsPerHand));
    }

    public ImmutableList<Card> diamonds()
    {
        return this.cardsBySuit.get(Suit.DIAMONDS);
    }

    public ImmutableList<Card> hearts()
    {
        return this.cardsBySuit.get(Suit.HEARTS);
    }

    public ImmutableList<Card> spades()
    {
        return this.cardsBySuit.get(Suit.SPADES);
    }

    public ImmutableList<Card> clubs()
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
}
