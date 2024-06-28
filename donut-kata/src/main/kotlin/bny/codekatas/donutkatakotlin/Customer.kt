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

import org.eclipse.collections.api.list.ListIterable
import org.eclipse.collections.api.set.SetIterable
import org.eclipse.collections.impl.factory.Lists

class Customer(val name: String?)
{
    private val deliveries = Lists.mutable.empty<Delivery>()

    override fun equals(other: Any?): Boolean
    {
        if (this === other)
        {
            return true
        }
        if (other == null || javaClass != other.javaClass)
        {
            return false
        }
        val customer = other as Customer?
        return this.name == customer!!.name
    }

    override fun hashCode(): Int
    {
        return this.name?.hashCode() ?: 0
    }

    fun named(name: String): Boolean
    {
        return name == this.name
    }

    fun addDelivery(delivery: Delivery)
    {
        this.deliveries.add(delivery)
    }

    fun getDeliveries(): ListIterable<Delivery>
    {
        return this.deliveries.asUnmodifiable()
    }

    val totalDonutsOrdered: Long
        get() = this.deliveries.sumOfInt { it.totalDonuts }

    val donutTypesOrdered: SetIterable<DonutType>
        get() = this.deliveries
            .flatCollect { it.donuts }
            .collect { it.type }
            .toSet()

    override fun toString(): String
    {
        return "Customer(" +
            "name='" + this.name + '\'' +
            ')'
    }
}
