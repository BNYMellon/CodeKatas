/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
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
