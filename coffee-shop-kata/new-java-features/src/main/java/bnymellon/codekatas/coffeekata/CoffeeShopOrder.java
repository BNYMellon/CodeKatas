/*
 * Copyright 2023 The Bank of New York Mellon.
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

package bnymellon.codekatas.coffeekata;

import bnymellon.codekatas.coffeekata.beverage.Americano;
import bnymellon.codekatas.coffeekata.beverage.Latte;
import bnymellon.codekatas.coffeekata.beverage.Macchiato;
import bnymellon.codekatas.coffeekata.beverage.Tea;

import java.util.ArrayList;
import java.util.List;

public class CoffeeShopOrder
{

    private final String customerName;
    private final List<Item> orderItems;

    public CoffeeShopOrder(String customerName, List<Item> orderItems)
    {
        this.customerName = customerName;
        this.orderItems = orderItems;
    }

    /**
     * Print out a list of custom strings for the customer's food items!
     * Start with, "Order #[orderNumber] for [customerName] includes: "
     * If the item is a Bagel: Print [toasted] [bagelType] with [spreadType]
     * If the item is a Cookie: Print [warmed] [cookieType]
     * If the item is a Donut: Print [donutType]
     * <p>
     * NOTE: This method show-cases a switch-case pattern matching.
     */
    public List<String> getFoodItemsForOrder()
    {
        // TODO implement method
        return null;
    }

    /**
     * Generate a receipt for a customer's order.
     * If the item is a Donut: Print Donut: [donutType] $Price
     * If the item is a Cookie: Print Cookie: [cookieType] $Price
     * If the item is a Bagel: Print Bagel: [bagelType] $Price
     * Total: $Total Price
     * <p>
     * NOTE: The method highlights the usage of a record deconstruction pattern
     */
    public String generateReceipt()
    {
        // TODO: Implement the receipt generation logic here.
        // Use the instanceof operator and the record pattern to differentiate between different food items.
        return null;
    }

    /**
     * Create and print drink order
     * First drink : Hot Americano
     * Second drink : Hot Caramel Latte with Almond Milk
     * Third drink : Hot Vanilla Macchiato with Whole Milk
     * Fourth drink : MATCHA Tea
     * <p>
     * NOTE: Use interface to create four drinks
     */
    public List<String> getDrinkForOrder()
    {
        // TODO implement method
        return null;
    }
}
