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

package bnymellon.codekatas.donutkata;

import java.time.LocalDate;

import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.factory.Bags;
import org.eclipse.collections.impl.list.fixed.ArrayAdapter;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;

public class Order
{
    private Customer customer;
    private LocalDate date;
    private MutableBag<DonutType> counts = Bags.mutable.empty();

    public Order(Customer customer, LocalDate date, String donutTypeCounts)
    {
        this.customer = customer;
        ArrayAdapter.adapt(donutTypeCounts.split(","))
                .asLazy()
                .collect(pair -> pair.split(":"))
                .collect(pair -> PrimitiveTuples.pair(DonutType.forAbbreviation(pair[0]), Integer.parseInt(pair[1])))
                .each(this::add);
        this.date = date;
    }

    private void add(ObjectIntPair<DonutType> pair)
    {
        this.counts.addOccurrences(pair.getOne(), pair.getTwo());
    }

    public Customer getCustomer()
    {
        return this.customer;
    }

    public Bag<DonutType> getCounts()
    {
        return this.counts.asUnmodifiable();
    }

    public LocalDate getDate()
    {
        return this.date;
    }

    @Override
    public String toString()
    {
        return "Order(" +
                "customer=" + customer +
                ", date=" + date +
                ", counts=" + counts.toStringOfItemToCount() +
                ')';
    }
}
