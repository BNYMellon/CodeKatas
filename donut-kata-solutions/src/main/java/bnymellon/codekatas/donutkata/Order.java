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
