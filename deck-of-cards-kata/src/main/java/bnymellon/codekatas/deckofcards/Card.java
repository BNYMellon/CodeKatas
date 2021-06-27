/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.deckofcards;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
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
                Card::new);
    }

    public static Stream<Card> streamCards()
    {
        return Card.cartesianProduct(
                EnumSet.allOf(Rank.class),
                EnumSet.allOf(Suit.class),
                Card::new);
    }

    private static <A, B, C> Stream<C> cartesianProduct(
            Set<A> set1,
            Set<B> set2,
            Function2<A, B, C> function)
    {
        // TODO Implement Cartesian Product using Java 8 Streams
        // Hint: Check out flatMap() and map() on Stream
        return Card.imperativeCartesianProductOfCards(set1, set2, function).stream();
    }

    @Deprecated
    private static <A, B, C> List<C> imperativeCartesianProductOfCards(
            Set<A> set1,
            Set<B> set2,
            Function2<A, B, C> function)
    {
        var result = new ArrayList<C>();
        for (A first : set1)
        {
            for (B second : set2)
            {
                result.add(function.value(first, second));
            }
        }
        return result;
    }

    @Override
    public int compareTo(Card other)
    {
        return Comparator.comparing(Card::suit).thenComparing(Card::rank).compare(this, other);
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
