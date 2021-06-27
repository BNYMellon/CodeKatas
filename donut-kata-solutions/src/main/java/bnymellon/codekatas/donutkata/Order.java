/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.donutkata;

import java.time.LocalDate;

import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.factory.Bags;
import org.eclipse.collections.impl.list.fixed.ArrayAdapter;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;

public record Order(Customer customer, LocalDate date, MutableBag<DonutType> counts)
{
    public Order(Customer customer, LocalDate date, String donutTypeCounts)
    {
        this(customer, date, Bags.mutable.empty());
        ArrayAdapter.adapt(donutTypeCounts.split(","))
                .asLazy()
                .collect(pair -> pair.split(":"))
                .collect(pair -> PrimitiveTuples.pair(DonutType.forAbbreviation(pair[0]), Integer.parseInt(pair[1])))
                .each(this::add);
    }

    private void add(ObjectIntPair<DonutType> pair)
    {
        this.counts.addOccurrences(pair.getOne(), pair.getTwo());
    }

    @Override
    public int hashCode()
    {
        return System.identityHashCode(this);
    }

    public String toString()
    {
        return "Order(" +
                "customer=" + this.customer +
                ", date=" + this.date +
                ", counts=" + this.counts.toStringOfItemToCount() +
                ')';
    }
}
