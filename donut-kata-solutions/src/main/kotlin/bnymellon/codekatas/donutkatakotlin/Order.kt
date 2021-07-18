/**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
*/

package bnymellon.codekatas.donutkatakotlin

import org.eclipse.collections.api.bag.Bag
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair
import org.eclipse.collections.impl.factory.Bags
import org.eclipse.collections.impl.list.fixed.ArrayAdapter
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples
import java.time.LocalDate

class Order(val customer: Customer, val date: LocalDate, donutTypeCounts: String)
{
    private val counts = Bags.mutable.empty<DonutType>()

    init
    {
        ArrayAdapter.adapt(*donutTypeCounts.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            .asLazy()
            .collect { pair -> pair.split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray() }
            .collect { pair -> PrimitiveTuples.pair(DonutType.forAbbreviation(pair[0]), Integer.parseInt(pair[1])) }
            .each { this.add(it) }
    }

    private fun add(pair: ObjectIntPair<DonutType>)
    {
        this.counts.addOccurrences(pair.one, pair.two)
    }

    fun getCounts(): Bag<DonutType>
    {
        return this.counts.asUnmodifiable()
    }

    override fun toString(): String
    {
        return "Order(" +
            "customer=" + this.customer +
            ", date=" + this.date +
            ", counts=" + this.counts.toStringOfItemToCount() +
            ')'
    }
}
