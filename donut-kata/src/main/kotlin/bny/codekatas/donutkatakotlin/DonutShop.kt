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
import org.eclipse.collections.api.list.MutableList
import org.eclipse.collections.api.multimap.Multimap
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair
import org.eclipse.collections.impl.factory.Bags
import org.eclipse.collections.impl.factory.Lists
import org.eclipse.collections.impl.factory.Sets
import org.eclipse.collections.impl.list.primitive.IntInterval.fromTo
import org.eclipse.collections.impl.list.primitive.IntInterval.zeroTo
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples.pair
import java.time.LocalDate
import java.util.*

/**
 * A DonutShop has a Bag of DonutTypes, a Set of Customers, a List of Orders, and a List of Deliveries.
 * Prices for donuts are determined by how many donuts are ordered.  Deliveries are always made the same
 * day as an Order.  If there are not enough donuts to fill an order, more donuts are made.
 */
class DonutShop
{
    private val donuts = Bags.mutable.empty<DonutType>()
    private val orders = Lists.mutable.empty<Order>()
    private val customers = Sets.mutable.empty<Customer>()
    private val deliveries = Lists.mutable.empty<Delivery>()

    fun makeDonuts(type: DonutType, count: Int)
    {
        this.donuts.addOccurrences(type, count)
    }

    private fun makeMissingDonuts(type: DonutType, count: Int)
    {
        val inventory = this.donuts.occurrencesOf(type)
        if (inventory < count)
        {
            val missing = count - inventory
            this.makeDonuts(type, missing)
        }
    }

    fun deliverOrder(customerName: String, date: LocalDate, donutTypeCounts: String): Delivery
    {
        val customer = this.getOrCreateCustomer(customerName)
        val order = this.createOrder(customer, date, donutTypeCounts)
        return this.fillOrder(order)
    }

    private fun fillOrder(order: Order): Delivery
    {
        order.getCounts().forEachWithOccurrences(this::makeMissingDonuts)
        val price = this.calculatePricePerDonut(order.getCounts().size())
        val delivery = this.createDelivery(order, price)
        order.getCounts()
            .forEachWithOccurrences { item, occurrences -> this.donuts.removeOccurrences(item, occurrences) }
        return delivery
    }

    private fun createDelivery(order: Order, price: Double): Delivery
    {
        val donuts = order.getCounts()
            .asLazy()
            .collect { Donut(type = it, price = price) }
            .toImmutableList()
        val delivery = Delivery(order, donuts)
        this.deliveries.add(delivery)
        return delivery
    }

    private fun calculatePricePerDonut(orderSize: Int): Double
    {
        return PRICES.detectIfNone(
            { it.one.contains(orderSize) }
            , { throw IllegalArgumentException("This order cannot be satisfied") }).two
    }

    private fun createOrder(customer: Customer, date: LocalDate, donutTypeCounts: String): Order
    {
        val order = Order(customer, date, donutTypeCounts)
        this.orders.add(order)
        return order
    }

    private fun getOrCreateCustomer(customerName: String): Customer
    {
        val customer =
            this.customers.detectWithIfNone(
                Customer::named,
                customerName,
                { Customer(name = customerName) })
        if (!this.customers.contains(customer))
        {
            this.customers.add(customer)
        }
        return customer
    }

    fun getDonuts(): Bag<DonutType>
    {
        return this.donuts.asUnmodifiable()
    }

    fun getTopDonuts(n: Int): MutableList<ObjectIntPair<DonutType>>?
    {
        // TODO - Write the code necessary to calculate the top n donuts
        // Hint: Look at the domain and use deliveries which have collections of ordered donuts
        // Hint: You will need to flatten the donuts and collect their donut types
        // Hint: Bag has a method named topOccurrences(n)
        return null
    }

    fun getTotalDeliveryValueFor(date: LocalDate): Double
    {
        // TODO - Write the code necessary to sum up the total delivery value for the specified date
        // Hint: Look at sumOfDouble()
        return 0.0
    }

    val topCustomer: Customer?
        get() = null
        // TODO - Write the code necessary to find the max Customer by total donuts ordered
        // Hint: There is a method maxBy on all RichIterables

    val customersByDonutTypesOrdered: Multimap<DonutType, Customer>?
        get() = null
        // TODO - Group all of the Customers by the Donut Types they order
        // Hint: There is a method groupByEach which takes a function which returns Iterable


    fun getDonutPriceStatistics(fromDate: LocalDate, toDate: LocalDate): DoubleSummaryStatistics?
    {
        // TODO - Calculate the DoubleSummaryStatistics for the deliveries inclusive of the specified date range.
        // Hint: Look at select(), flatCollect() and summarizeDouble()
        return null
    }

    override fun toString(): String
    {
        return "DonutShop(" +
            "donuts=" + this.donuts.toStringOfItemToCount() +
            ", deliveries=" + this.deliveries +
            ')'
    }

    companion object
    {
        private val SINGLE = 1
        private val DOUBLE = 2
        private val HALF_DOZEN = 6
        private val DOZEN = 12
        private val BAKERS_DOZEN = 13
        private val PRICES = Lists.immutable.with(
            pair(zeroTo(SINGLE), 1.50),
            pair(fromTo(DOUBLE, HALF_DOZEN - 1), 1.35),
            pair(fromTo(HALF_DOZEN, DOZEN - 1), 1.25),
            pair(fromTo(DOZEN, DOZEN), 1.00),
            pair(fromTo(BAKERS_DOZEN, DOZEN * 100), 0.95))
    }
}
