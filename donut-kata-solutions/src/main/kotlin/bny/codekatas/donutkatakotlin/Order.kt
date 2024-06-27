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
