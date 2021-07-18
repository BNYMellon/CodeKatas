/**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
*/

package bnymellon.codekatas.donutkatakotlin

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
