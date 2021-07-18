  /**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
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
