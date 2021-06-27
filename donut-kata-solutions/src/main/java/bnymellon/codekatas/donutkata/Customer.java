/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.donutkata;

import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.SetIterable;
import org.eclipse.collections.impl.factory.Lists;

public record Customer(String name, MutableList<Delivery> deliveries)
{
    public Customer(String name)
    {
        this(name, Lists.mutable.empty());
    }

    public boolean named(String name)
    {
        return name.equals(this.name);
    }

    public void addDelivery(Delivery delivery)
    {
        this.deliveries.add(delivery);
    }

    public ListIterable<Delivery> getDeliveries()
    {
        return this.deliveries.asUnmodifiable();
    }

    public long getTotalDonutsOrdered()
    {
        return this.deliveries.sumOfInt(Delivery::getTotalDonuts);
    }

    public SetIterable<DonutType> getDonutTypesOrdered()
    {
        return this.deliveries
                .flatCollect(Delivery::donuts)
                .collect(Donut::type)
                .toSet();
    }

    @Override
    public String toString()
    {
        return "Customer(" +
                "name='" + this.name + '\'' +
                ')';
    }
}
