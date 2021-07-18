  /**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
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
