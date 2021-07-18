/**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
*/

package bnymellon.codekatas.donutkatakotlin

import org.eclipse.collections.api.list.ImmutableList
import java.time.LocalDate

class Delivery(private val order: Order, val donuts: ImmutableList<Donut>)
{
    init
    {
        this.order.customer.addDelivery(this)
    }

    val customer: Customer
        get() = this.order.customer

    fun getOrder(): Order
    {
        return this.order
    }

    val date: LocalDate
        get() = this.order.date

    fun deliveredOn(date: LocalDate): Boolean
    {
        return this.date == date
    }

    val totalDonuts: Int
        get() = this.donuts.size()

    val totalPrice: Double
        get() = this.donuts.sumOfDouble { it.price }

    override fun toString(): String
    {
        return "Delivery(" +
            "order=" + order +
            ", donuts=" + donuts.toBag().toStringOfItemToCount() +
            ')'
    }
}
