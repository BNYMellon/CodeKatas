/*
 * Copyright 2018 BNY Mellon.
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

package bnymellon.codekatas.donutkata10;

import java.time.LocalDate;
import java.util.DoubleSummaryStatistics;

import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.primitive.ObjectDoublePair;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.factory.Bags;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.list.primitive.IntInterval;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;

/**
 * A DonutShop has a Bag of DonutTypes, a Set of Customers, a List of Orders, and a List of Deliveries.
 * Prices for donuts are determined by how many donuts are ordered.  Deliveries are always made the same
 * day as an Order.  If there are not enough donuts to fill an order, more donuts are made.
 */
public class DonutShop
{
    private static final int SINGLE = 1;
    private static final int DOUBLE = 2;
    private static final int HALF_DOZEN = 6;
    private static final int DOZEN = 12;
    private static final int BAKERS_DOZEN = 13;
    private static final ImmutableList<ObjectDoublePair<IntInterval>> PRICES =
            Lists.immutable.with(
                    PrimitiveTuples.pair(IntInterval.zeroTo(SINGLE), 1.50d),
                    PrimitiveTuples.pair(IntInterval.fromTo(DOUBLE, HALF_DOZEN - 1), 1.35d),
                    PrimitiveTuples.pair(IntInterval.fromTo(HALF_DOZEN, DOZEN - 1), 1.25d),
                    PrimitiveTuples.pair(IntInterval.fromTo(DOZEN, DOZEN), 1.00d),
                    PrimitiveTuples.pair(IntInterval.fromTo(BAKERS_DOZEN, DOZEN * 100), 0.95d));

    private MutableBag<DonutType> donuts = Bags.mutable.empty();
    private MutableList<Order> orders = Lists.mutable.empty();
    private MutableSet<Customer> customers = Sets.mutable.empty();
    private MutableList<Delivery> deliveries = Lists.mutable.empty();

    public void makeDonuts(DonutType type, int count)
    {
        this.donuts.addOccurrences(type, count);
    }

    private void makeMissingDonuts(DonutType type, int count)
    {
        int inventory = this.donuts.occurrencesOf(type);
        if (inventory < count)
        {
            int missing = count - inventory;
            this.makeDonuts(type, missing);
        }
    }

    public Delivery deliverOrder(String customerName, LocalDate date, String donutTypeCounts)
    {
        var customer = this.getOrCreateCustomer(customerName);
        var order = this.createOrder(customer, date, donutTypeCounts);
        return this.fillOrder(order);
    }

    private Delivery fillOrder(Order order)
    {
        order.getCounts().forEachWithOccurrences(this::makeMissingDonuts);
        double price = this.calculatePricePerDonut(order.getCounts().size());
        var delivery = this.createDelivery(order, price);
        order.getCounts().forEachWithOccurrences(this.donuts::removeOccurrences);
        return delivery;
    }

    private Delivery createDelivery(Order order, double price)
    {
        var donuts = order.getCounts()
                .asLazy()
                .collect(type -> new Donut(type, price))
                .toList()
                .toImmutable();
        var delivery = new Delivery(order, donuts);
        this.deliveries.add(delivery);
        return delivery;
    }

    private double calculatePricePerDonut(int orderSize)
    {
        return PRICES.detectIfNone(
                pair -> pair.getOne().contains(orderSize),
                () ->
                {
                    throw new IllegalArgumentException("This order cannot be satisfied");
                })
                .getTwo();
    }

    private Order createOrder(Customer customer, LocalDate date, String donutTypeCounts)
    {
        var order = new Order(customer, date, donutTypeCounts);
        this.orders.add(order);
        return order;
    }

    private Customer getOrCreateCustomer(String customerName)
    {
        var customer = this.customers.detectWithIfNone(
                Customer::named, customerName,
                () -> new Customer(customerName));
        if (!this.customers.contains(customer))
        {
            this.customers.add(customer);
        }
        return customer;
    }

    public Bag<DonutType> getDonuts()
    {
        return this.donuts.asUnmodifiable();
    }

    public MutableList<ObjectIntPair<DonutType>> getTopDonuts(int n)
    {
        // TODO - Write the code necessary to calculate the top n donuts
        // Hint: Look at the domain and use deliveries which have collections of ordered donuts
        // Hint: You will need to flatten the donuts and collect their donut types
        // Hint: Bag has a method named topOccurrences(n)
        return null;
    }

    public double getTotalDeliveryValueFor(LocalDate date)
    {
        // TODO - Write the code necessary to sum up the total delivery value for the specified date
        // Hint: Look at sumOfDouble()
        return 0.0d;
    }

    public Customer getTopCustomer()
    {
        // TODO - Write the code necessary to find the max Customer by total donuts ordered
        // Hint: There is a method maxBy on all RichIterables
        return null;
    }

    public Multimap<DonutType, Customer> getCustomersByDonutTypesOrdered()
    {
        // TODO - Group all of the Customers by the Donut Types they order
        // Hint: There is a method groupByEach which takes a function which returns Iterable
        return null;
    }

    public DoubleSummaryStatistics getDonutPriceStatistics(LocalDate fromDate, LocalDate toDate)
    {
        // TODO - Calculate the DoubleSummaryStatistics for the deliveries inclusive of the specified date range.
        // Hint: Look at select(), flatCollect() and summarizeDouble()
        return null;
    }

    @Override
    public String toString()
    {
        return "DonutShop(" +
                "donuts=" + this.donuts.toStringOfItemToCount() +
                ", deliveries=" + this.deliveries +
                ')';
    }
}
