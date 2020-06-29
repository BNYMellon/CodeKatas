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

import org.eclipse.collections.api.list.ImmutableList;

public class Delivery
{
    private final Order order;
    private final ImmutableList<Donut> donuts;

    public Delivery(Order order, ImmutableList<Donut> donuts)
    {
        this.donuts = donuts;
        this.order = order;
        this.order.getCustomer().addDelivery(this);
    }

    public Customer getCustomer()
    {
        return this.order.getCustomer();
    }

    public Order getOrder()
    {
        return this.order;
    }

    public ImmutableList<Donut> getDonuts()
    {
        return this.donuts;
    }

    public LocalDate getDate()
    {
        return this.order.getDate();
    }

    public boolean deliveredOn(LocalDate date)
    {
        return this.getDate().equals(date);
    }

    public int getTotalDonuts()
    {
        return this.getDonuts().size();
    }

    public double getTotalPrice()
    {
        return this.getDonuts().sumOfDouble(Donut::getPrice);
    }

    @Override
    public String toString()
    {
        return "Delivery(" +
                "order=" + this.order +
                ", donuts=" + this.donuts.toBag().toStringOfItemToCount() +
                ')';
    }
}
