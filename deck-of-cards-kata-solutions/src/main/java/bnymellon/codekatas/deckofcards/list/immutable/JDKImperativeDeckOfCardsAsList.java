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

import java.util.*;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;

public class JDKImperativeDeckOfCardsAsList
{
    private final List<Card> cards;
    private final Map<Suit, List<Card>> cardsBySuit;

    public JDKImperativeDeckOfCardsAsList()
    {
        this.cards = List.copyOf(this.buildCardsAsSortedList());
        this.cardsBySuit = Map.copyOf(this.buildCardsAsMapBySuit());
    }

    private List<Card> buildCardsAsSortedList()
    {
        var list = new ArrayList<Card>();
        var iterator = Card.streamCards().iterator();
        while (iterator.hasNext())
        {
            list.add(iterator.next());
        }
        Collections.sort(list);
        return list;
    }

    private Map<Suit,List<Card>> buildCardsAsMapBySuit()
    {
        var map = new HashMap<Suit, List<Card>>();
        for (var card : this.cards)
        {
            Suit suit = card.suit();
            List<Card> list = map.computeIfAbsent(suit, k -> new ArrayList<>());
            list.add(card);
        }
        for (var suitListEntry : map.entrySet())
        {
            var value = suitListEntry.getValue();
            Collections.sort(value);
            suitListEntry.setValue(List.copyOf(value));
        }
        return map;
    }

    public Deque<Card> shuffle(Random random)
    {
        var shuffled = new ArrayList<Card>(this.cards);
        for (int i = 0; i < 3; i++)
        {
            Collections.shuffle(shuffled, random);
        }
        var deck = new ArrayDeque<Card>();
        for (var card : shuffled)
        {
            deck.push(card);
        }
        return deck;
    }

    public Set<Card> deal(Deque<Card> deque, int count)
    {
        var hand = new HashSet<Card>();
        for (int i = 0; i < count; i++)
        {
            hand.add(deque.pop());
        }
        return hand;
    }

    public List<Set<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand)
    {
        var shuffled = this.shuffle(random);
        return this.dealHands(shuffled, hands, cardsPerHand);
    }

    public List<Set<Card>> dealHands(Deque<Card> shuffled, int hands, int cardsPerHand)
    {
        var result = new ArrayList<Set<Card>>();
        for (int i = 0; i < hands; i++)
        {
            result.add(this.deal(shuffled, cardsPerHand));
        }
        return List.copyOf(result);
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
        var result = new HashMap<Suit, Long>();
        for (var card : this.cards)
        {
            var suit = card.suit();
            var value = result.computeIfAbsent(suit, s -> Long.valueOf(0));
            result.put(suit, value + 1);
        }
        return result;
    }

    public Map<Rank, Long> countsByRank()
    {
        var result = new HashMap<Rank, Long>();
        for (var card : this.cards)
        {
            var rank = card.rank();
            var value = result.computeIfAbsent(rank, r -> Long.valueOf(0));
            result.put(rank, value + 1);
        }
        return result;
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
