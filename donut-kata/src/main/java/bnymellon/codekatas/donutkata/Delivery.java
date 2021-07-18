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

import org.eclipse.collections.api.list.ImmutableList;

public record Delivery(Order order, ImmutableList<Donut>donuts)
{
    public Delivery
    {
        order.customer().addDelivery(this);
    }

    public Customer getCustomer()
    {
        return this.order.customer();
    }

    public LocalDate getDate()
    {
        return this.order.date();
    }

    public boolean deliveredOn(LocalDate date)
    {
        return this.getDate().equals(date);
    }

    public int getTotalDonuts()
    {
        return this.donuts.size();
    }

    public double getTotalPrice()
    {
        return this.donuts.sumOfDouble(Donut::price);
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
