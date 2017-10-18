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

package bnymellon.codekatas.deckofcards.list.immutable;

import java.util.*;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;

public class JDKImperativeDeckOfCardsAsList
{
    private List<Card> cards;
    private Map<Suit, List<Card>> cardsBySuit;

    public JDKImperativeDeckOfCardsAsList()
    {
        this.cards = Collections.unmodifiableList(this.buildCardsAsSortedList());
        this.cardsBySuit = Collections.unmodifiableMap(this.buildCardsAsMapBySuit());
    }

    private List<Card> buildCardsAsSortedList()
    {
        List<Card> list = new ArrayList<>();
        Iterator<Card> iterator = Card.streamCards().iterator();
        while (iterator.hasNext())
        {
            list.add(iterator.next());
        }
        Collections.sort(list);
        return list;
    }

    private Map<Suit,List<Card>> buildCardsAsMapBySuit()
    {
        Map<Suit, List<Card>> map = new HashMap<>();
        for (Card card : this.cards)
        {
            Suit suit = card.getSuit();
            List<Card> list = map.get(suit);
            if (list == null)
            {
                list = new ArrayList<>();
                map.put(suit, list);
            }
            list.add(card);
        }
        for (Map.Entry<Suit, List<Card>> suitListEntry : map.entrySet())
        {
            List<Card> value = suitListEntry.getValue();
            Collections.sort(value);
            suitListEntry.setValue(Collections.unmodifiableList(value));
        }
        return map;
    }

    public Deque<Card> shuffle(Random random)
    {
        List<Card> shuffled = new ArrayList<>(this.cards);
        for (int i = 0; i < 3; i++)
        {
            Collections.shuffle(shuffled, random);
        }
        ArrayDeque<Card> deck = new ArrayDeque<>();
        for (Card card : shuffled)
        {
            deck.push(card);
        }
        return deck;
    }

    public Set<Card> deal(Deque<Card> deque, int count)
    {
        Set<Card> hand = new HashSet<>();
        for (int i = 0; i < count; i++)
        {
            hand.add(deque.pop());
        }
        return hand;
    }

    public List<Set<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand)
    {
        Deque<Card> shuffled = this.shuffle(random);
        return this.dealHands(shuffled, hands, cardsPerHand);
    }

    public List<Set<Card>> dealHands(Deque<Card> shuffled, int hands, int cardsPerHand)
    {
        List<Set<Card>> result = new ArrayList<>();
        for (int i = 0; i < hands; i++)
        {
            result.add(this.deal(shuffled, cardsPerHand));
        }
        return Collections.unmodifiableList(result);
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
        Map<Suit, Long> result = new HashMap<>();
        for (Card card : this.cards)
        {
            Suit suit = card.getSuit();
            Long value = result.get(suit);
            if (value == null)
            {
                value = Long.valueOf(0);
            }
            result.put(suit, value + 1);
        }
        return result;
    }

    public Map<Rank, Long> countsByRank()
    {
        Map<Rank, Long> result = new HashMap<>();
        for (Card card : this.cards)
        {
            Rank rank = card.getRank();
            Long value = result.get(rank);
            if (value == null)
            {
                value = Long.valueOf(0);
            }
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
