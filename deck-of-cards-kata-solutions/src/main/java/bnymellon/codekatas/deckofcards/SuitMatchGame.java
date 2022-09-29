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

package bnymellon.codekatas.deckofcards;

import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.bag.primitive.MutableIntBag;
import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.partition.list.PartitionList;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.factory.Bags;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.primitive.IntBags;
import org.eclipse.collections.impl.list.primitive.IntInterval;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;

/**
 * The SuitMatchGame will continually try and match suits, removing matched
 * suits from a deck of cards.
 *
 * Rules:
 * <li>Only one suit is matched per round.</li>
 * <li>The suit with the greatest number of matches is chosen automatically.</li>
 * <li>Zero matches in a hand and the game ends.</li>
 * <li>Exceed the number of rounds and the game ends.</li>
 * <li>If you have one card or less remaining in the deck, you win the game.</li>
 * @param numberOfDecks
 * @param numberOfCards
 * @param rounds
 * @param verbose
 */
public record SuitMatchGame(int numberOfDecks, int numberOfCards, int rounds, boolean verbose)
{
    public static void main(String[] args)
    {
        // 1 Deck, 5 cards per hand, 20 rounds, verbose = false
        SuitMatchGame suitMatch = new SuitMatchGame(1, 5, 20, false);
        // Autoplay 10 games
        suitMatch.playGames(10);
    }

    public void playGames(int numberOfGames)
    {
        MutableIntBag results = IntBags.mutable.empty();
        MutableBag<String> winners = Bags.mutable.empty();
        IntInterval.oneTo(numberOfGames).forEach(each ->
        {
            Bag<String> suits = this.play().getOne();
            results.add(suits.size());
            if (suits.size() > (this.numberOfDecks * 50))
            {
                winners.add(suits.toStringOfItemToCount());
            }
        });
        this.outputResultsAndWinners(results, winners);
    }

    private void outputResultsAndWinners(MutableIntBag results, MutableBag<String> winners)
    {
        System.out.println("# Cards matched / # of times: " + results.toStringOfItemToCount());
        System.out.println("Winners: " + winners.toStringOfItemToCount());
    }

    public ObjectIntPair<Bag<String>> play()
    {
        if (this.verbose)
        {
            System.out.println("** Game Start");
        }
        ObjectIntPair<Bag<String>> result = this.playRounds();
        if (this.verbose)
        {
            System.out.println("** Game End - Rounds: " + result.getTwo());
        }
        return result;
    }

    ObjectIntPair<Bag<String>> playRounds()
    {
        MutableStack<Card> deck = this.createDeck();
        MutableBag<Suit> result = Bags.mutable.empty();
        int round = 0;
        boolean anyMatches = true;
        while (deck.notEmpty() && anyMatches && round < this.rounds)
        {
            ListIterable<Card> matchedHand = this.dealAndMatchHand(deck, result);
            anyMatches = matchedHand.size() < this.numberOfCards;
            matchedHand.forEach(deck::push);
            if (this.verbose)
            {
                System.out.println("Remaining: " + deck.toSortedList());
            }
            deck = deck.toList().shuffleThis().toStack();
            round++;
        }
        return PrimitiveTuples.pair(result.collect(Suit::toString), round);
    }

    ListIterable<Card> dealAndMatchHand(MutableStack<Card> deck, MutableBag<Suit> result)
    {
        ListIterable<Card> hand = deck.pop(Math.min(this.numberOfCards, deck.size()));
        ListIterable<Card> matchedHand = this.matchHand(hand, result);
        return matchedHand;
    }

    MutableStack<Card> createDeck()
    {
        MutableList<Card> cards = IntInterval.oneTo(numberOfDecks).flatCollect(i -> Card.lazyCards(), Lists.mutable.empty());
        return cards.shuffleThis().toStack();
    }

    ListIterable<Card> matchHand(ListIterable<Card> hand, MutableBag<Suit> result)
    {
        MutableBag<Suit> duplicates = hand.collect(Card::suit).toBag().selectDuplicates();
        if (duplicates.notEmpty())
        {
            ObjectIntPair<Suit> topMatch = duplicates.topOccurrences(1).getFirst();
            Suit topMatchingSuit = topMatch.getOne();
            result.addOccurrences(topMatchingSuit, topMatch.getTwo());
            PartitionList<Card> matches = hand.partition(card -> card.suit().equals(topMatchingSuit));
            if (this.verbose)
            {
                System.out.println("Retained: " + matches.getRejected() + " -> Matched: " + matches.getSelected());
            }
            return matches.getRejected();
        }
        return hand;
    }
}
