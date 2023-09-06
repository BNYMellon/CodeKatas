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
import bnymellon.codekatas.coffeekata.beverage.CoffeeDrink;
import bnymellon.codekatas.coffeekata.beverage.DrinkTemperature;
import bnymellon.codekatas.coffeekata.beverage.FlavorSyrup;
import bnymellon.codekatas.coffeekata.beverage.Latte;
import bnymellon.codekatas.coffeekata.beverage.Macchiato;
import bnymellon.codekatas.coffeekata.beverage.MilkType;
import bnymellon.codekatas.coffeekata.beverage.Tea;
import bnymellon.codekatas.coffeekata.beverage.TeaType;
import bnymellon.codekatas.coffeekata.food.Bagel;
import bnymellon.codekatas.coffeekata.food.BagelType;
import bnymellon.codekatas.coffeekata.food.Cookie;
import bnymellon.codekatas.coffeekata.food.CookieType;
import bnymellon.codekatas.coffeekata.food.Donut;
import bnymellon.codekatas.coffeekata.food.DonutType;
import bnymellon.codekatas.coffeekata.food.SpreadType;

import java.util.ArrayList;
import java.util.List;

public class CoffeeShopOrder
{

    private final String customerName;
    private final List<Item> orderItems;

    public CoffeeShopOrder(String customerName, java.util.List<Item> orderItems)
    {
        this.customerName = customerName;
        this.orderItems = orderItems;
    }

    /**
     * Print out a list of custom strings for the customer's food items!
     * If the item is a Bagel: Print [bagelType] with [spreadType]
     * If the item is a Cookie: Print [cookieType]
     * If the item is a Donut: Print [donutType]
     * <p>
     * NOTE: This method show-cases a switch-case pattern matching.
     */
    public List<String> getFoodItemsForOrder()
    {
        List<String> foodItems = new ArrayList<>();
        for (Item item : this.orderItems)
        {
            if (item instanceof Bagel)
            {
                Bagel bagel = (Bagel) item;
                foodItems.add(bagel.getBagelType() + " bagel with " + bagel.getSpreadType());
            }
            else if (item instanceof Cookie)
            {
                Cookie cookie = (Cookie) item;
                foodItems.add(cookie.getCookieType() + " cookie");
            }
            else if (item instanceof Donut)
            {
                Donut donut = (Donut) item;
                foodItems.add(donut.getDonutType() + " donut");
            }
            else
            {
                throw new IllegalStateException("Unexpected value: " + item);
            }
        }
        return foodItems;
    }

    /**
     * Generate a receipt for a customer's order.
     * If the item is a Donut: Print Donut: [donutType] $Price
     * If the item is a Cookie: Print Cookie: [cookieType] $Price
     * If the item is a Bagel: Print Bagel: [bagelType] $Price
     * Total: $Total Price
     * <p>
     * NOTE: The method highlights the usage of a data extraction
     * HINT: Use instanceOf
     */
    public String generateReceipt()
    {
        double total = 0.0;
        StringBuilder receiptBuilder = new StringBuilder();

        for (Item item : this.orderItems)
        {
            if (item instanceof Donut)
            {
                Donut donut = (Donut) item;
                DonutType donutType = donut.getDonutType();
                receiptBuilder.append("Donut: ").append(donutType).append(" $").append(item.getPrice()).append("\n");
            }
            else if (item instanceof Bagel)
            {
                Bagel bagel = (Bagel) item;
                BagelType bagelType = bagel.getBagelType();
                receiptBuilder.append("Bagel: ").append(bagelType).append(" $").append(item.getPrice()).append("\n");
            }
            else if (item instanceof Cookie)
            {
                Cookie cookie = (Cookie) item;
                CookieType cookieType = cookie.getCookieType();
                receiptBuilder.append("Cookie: ").append(cookieType).append(" $").append(item.getPrice()).append("\n");
            }
            total += item.getPrice();
        }

        receiptBuilder.append("Total: $").append(total);
        return receiptBuilder.toString();
    }

    /**
     * Create and print drink order
     * First drink : Hot Americano
     * Second drink : Hot Caramel Latte with Almond Milk
     * Third drink : Hot Vanilla Macchiato with Whole Milk
     * Fourth drink : MATCHA Tea
     * <p>
     * NOTE: Use interface to create four drinks
     * Use the toString() to obtain descriptions of the dinks
     */
    public List<String> getDrinkForOrder()
    {
        List<String> drinkItems = new ArrayList<>();
        CoffeeDrink coffeeDrink1 = new Americano(DrinkTemperature.HOT);
        CoffeeDrink coffeeDrink2 = new Latte(FlavorSyrup.CARAMEL, MilkType.ALMOND_MILK, false, DrinkTemperature.HOT);
        CoffeeDrink coffeeDrink3 = new Macchiato(MilkType.WHOLE_MILK, FlavorSyrup.VANILLA, DrinkTemperature.HOT);
        Tea tea = new Tea(TeaType.MATCHA);

        drinkItems.add(coffeeDrink1.toString());
        drinkItems.add(coffeeDrink2.toString());
        drinkItems.add(coffeeDrink3.toString());
        drinkItems.add(tea.toString());

        return drinkItems;
    }
}
