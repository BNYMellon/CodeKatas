/*
 * Copyright 2024 The Bank of New York Mellon.
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

package bny.codekatas.donutkatakotlin

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
