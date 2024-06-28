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

package bny.codekatas.coffeeshopkata;

import bny.codekatas.coffeeshopkata.beverage.Americano;
import bny.codekatas.coffeeshopkata.beverage.CoffeeDrink;
import bny.codekatas.coffeeshopkata.beverage.DrinkTemperature;
import bny.codekatas.coffeeshopkata.beverage.FlavorSyrup;
import bny.codekatas.coffeeshopkata.beverage.Latte;
import bny.codekatas.coffeeshopkata.beverage.Macchiato;
import bny.codekatas.coffeeshopkata.beverage.MilkType;
import bny.codekatas.coffeeshopkata.beverage.Tea;
import bny.codekatas.coffeeshopkata.beverage.TeaType;
import bny.codekatas.coffeeshopkata.food.Bagel;
import bny.codekatas.coffeeshopkata.food.BagelType;
import bny.codekatas.coffeeshopkata.food.Cookie;
import bny.codekatas.coffeeshopkata.food.CookieType;
import bny.codekatas.coffeeshopkata.food.Donut;
import bny.codekatas.coffeeshopkata.food.DonutType;
import bny.codekatas.coffeeshopkata.food.SpreadType;

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
     * Generate a receipt for a customer's food items.
     * If the item is a Donut: Print Donut: [donutType] $Price
     * If the item is a Cookie: Print Cookie: [cookieType] $Price
     * If the item is a Bagel: Print Bagel: [bagelType] $Price
     * Total: $Total Price
     * <p>
     * NOTE: The method highlights the usage of a record deconstruction pattern
     */
    public String generateReceiptForFoodItems()
    {
        double total = 0.0;
        List<String> receiptItems = new ArrayList<>();

        for (Item item : this.orderItems)
        {
            if (item instanceof Donut(DonutType donutType))
            {
                receiptItems.add("Donut: " + donutType + " $" + item.getPrice());
                total += item.getPrice();
            }
            else if (item instanceof Bagel(BagelType bagelType, SpreadType spreadType, boolean toasted))
            {
                receiptItems.add("Bagel: " + bagelType + " $" + item.getPrice());
                total += item.getPrice();
            }
            else if (item instanceof Cookie(CookieType cookieType, boolean warmed))
            {
                receiptItems.add("Cookie: " + cookieType + " $" + item.getPrice());
                total += item.getPrice();
            }
        }
        receiptItems.add("Total: $" + total);

        return String.join("\n", receiptItems);
    }

    /**
     * Return a list of custom strings for the customer's food items!
     * The string format for each food item is as follows:
     * If the item is a Bagel: "[bagelType] with [spreadType]"
     * If the item is a Cookie: "[cookieType] cookie"
     * If the item is a Donut: "[donutType] donut"
     * Otherwise: it is a beverage and should not be added to the list!
     * <p>
     * NOTE: This method show-cases a switch-case pattern matching.
     */
    public List<String> getFoodItemsForOrder()
    {
        List<String> foodItems = new ArrayList<>();
        for (Item item : this.orderItems)
        {
            switch (item)
            {
                case Bagel bagel -> foodItems.add(bagel.bagelType() + " bagel with " + bagel.spreadType());
                case Cookie cookie -> foodItems.add(cookie.cookieType() + " cookie");
                case Donut donut -> foodItems.add(donut.donutType() + " donut");
                default ->
                {
                }
            }
        }
        return foodItems;
    }

    /**
     * Return a list of custom strings for the customer's drinks!
     * First drink: Hot Americano
     * Second drink: Hot Caramel Latte with Almond Milk
     * Third drink: Hot Vanilla Macchiato with Whole Milk
     * Fourth drink: Matcha Tea
     * <p>
     * NOTE: This method utilize sealed classes and permit to define coffee drink types
     * (e.g., Americano, Latte, Macchiato) are allowed within a hierarchy.
     * However, Tea is not part of this hierarchy.
     */
    public List<String> getDrinksForOrder()
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
