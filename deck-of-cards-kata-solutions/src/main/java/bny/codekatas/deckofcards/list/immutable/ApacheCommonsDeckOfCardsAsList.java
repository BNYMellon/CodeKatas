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

package bny.codekatas.deckofcards.list.immutable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import bny.codekatas.deckofcards.Card;
import bny.codekatas.deckofcards.Rank;
import bny.codekatas.deckofcards.Suit;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.collections4.MultiMapUtils;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.multiset.HashMultiSet;

public class ApacheCommonsDeckOfCardsAsList
{
    private List<Card> cards;
    private MultiValuedMap<Suit, Card> cardsBySuit;

    public ApacheCommonsDeckOfCardsAsList()
    {
        this.cards = ListUtils.unmodifiableList(Card.streamCards().sorted().collect(Collectors.toList()));
        ListValuedMap<Suit, Card> cbs = MultiMapUtils.newListValuedHashMap();
        this.cards.forEach(card -> cbs.put(card.suit(), card));
        this.cardsBySuit = MultiMapUtils.unmodifiableMultiValuedMap(cbs);
    }

    public Deque<Card> shuffle(Random random)
    {
        List<Card> shuffled = new ArrayList<>(this.cards);
        Collections.shuffle(shuffled, random);
        Collections.shuffle(shuffled, random);
        Collections.shuffle(shuffled, random);
        ArrayDeque<Card> cards = new ArrayDeque<>();
        shuffled.forEach(cards::push);
        return cards;
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
        return ListUtils.unmodifiableList(
                IntStream.range(0, hands)
                        .mapToObj(i -> this.deal(shuffled, cardsPerHand))
                        .collect(Collectors.toList()));
    }

    public List<Card> diamonds()
    {
        return MultiMapUtils.getValuesAsList(this.cardsBySuit, Suit.DIAMONDS);
    }

    public List<Card> hearts()
    {
        return MultiMapUtils.getValuesAsList(this.cardsBySuit, Suit.HEARTS);
    }

    public List<Card> spades()
    {
        return MultiMapUtils.getValuesAsList(this.cardsBySuit, Suit.SPADES);
    }

    public List<Card> clubs()
    {
        return MultiMapUtils.getValuesAsList(this.cardsBySuit, Suit.CLUBS);
    }

    public Bag<Suit> countsBySuit()
    {
        return this.cards.stream()
                .map(Card::suit)
                .collect(Collectors.toCollection(HashBag::new));
    }

    public MultiSet<Rank> countsByRank()
    {
        return this.cards.stream()
                .map(Card::rank)
                .collect(Collectors.toCollection(HashMultiSet::new));
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
