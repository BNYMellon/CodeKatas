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

import java.util.Comparator;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.impl.factory.Sets;

public record Card(Rank rank, Suit suit) implements Comparable<Card>
{
    /**
     * {@link LazyIterable} and {@link Sets#cartesianProduct(Set, Set, Function2)} are from Eclipse Collections
     */
    public static LazyIterable<Card> lazyCards()
    {
        return Sets.cartesianProduct(
                EnumSet.allOf(Rank.class),
                EnumSet.allOf(Suit.class),
                Rank::of);
    }

    public static Stream<Card> streamCards()
    {
        return EnumSet.allOf(Rank.class)
                .stream()
                .flatMap(rank -> EnumSet.allOf(Suit.class)
                        .stream()
                        .map(suit -> rank.of(suit)));
    }

    @Override
    public int compareTo(Card other)
    {
        return Comparator.comparing(Card::suit).thenComparing(Card::rank).compare(this, other);
    }

    @Override
    public String toString()
    {
        return "|" + rank.toString() + suit.toString() + "|";
    }

    public boolean isDiamonds()
    {
        return this.suit == Suit.DIAMONDS;
    }

    public boolean isHearts()
    {
        return this.suit == Suit.HEARTS;
    }

    public boolean isSpades()
    {
        return this.suit == Suit.SPADES;
    }

    public boolean isClubs()
    {
        return this.suit == Suit.CLUBS;
    }
}
