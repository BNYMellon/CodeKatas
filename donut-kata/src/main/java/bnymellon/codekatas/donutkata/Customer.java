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

import java.util.Objects;

import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.SetIterable;
import org.eclipse.collections.impl.factory.Lists;

public class Customer
{
    private String name;
    private MutableList<Delivery> deliveries = Lists.mutable.empty();

    public Customer(String name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || this.getClass() != o.getClass())
        {
            return false;
        }
        Customer customer = (Customer) o;
        return Objects.equals(this.name, customer.name);
    }

    @Override
    public int hashCode()
    {
        return this.name != null ? this.name.hashCode() : 0;
    }

    public boolean named(String name)
    {
        return name.equals(this.name);
    }

    public String getName()
    {
        return this.name;
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
                .flatCollect(Delivery::getDonuts)
                .collect(Donut::getType)
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
