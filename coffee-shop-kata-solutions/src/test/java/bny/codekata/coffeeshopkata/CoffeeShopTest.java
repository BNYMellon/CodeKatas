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

package bny.codekata.coffeeshopkata;

import bny.codekatas.coffeeshopkata.CoffeeShopOrder;
import bny.codekatas.coffeeshopkata.Item;
import bny.codekatas.coffeeshopkata.beverage.Americano;
import bny.codekatas.coffeeshopkata.beverage.CoffeeDrink;
import bny.codekatas.coffeeshopkata.beverage.DrinkTemperature;
import bny.codekatas.coffeeshopkata.beverage.Latte;
import bny.codekatas.coffeeshopkata.beverage.Macchiato;
import bny.codekatas.coffeeshopkata.food.Bagel;
import bny.codekatas.coffeeshopkata.food.Cookie;
import bny.codekatas.coffeeshopkata.food.Donut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static bny.codekatas.coffeeshopkata.food.BagelType.EVERYTHING;
import static bny.codekatas.coffeeshopkata.food.CookieType.CHOCOLATE_CHIP;
import static bny.codekatas.coffeeshopkata.food.DonutType.GLAZED;
import static bny.codekatas.coffeeshopkata.food.SpreadType.HERB_GARLIC_CREAM_CHEESE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CoffeeShopTest
{
    private List<Item> itemList;
    private CoffeeShopOrder coffeeShopOrder;
    private Bagel bagel1;
    private Cookie cookie1;
    private Donut donut1;
    private Americano americano;

    @BeforeEach
    public void setUp()
    {
        bagel1 = new Bagel(EVERYTHING, HERB_GARLIC_CREAM_CHEESE, true);
        cookie1 = new Cookie(CHOCOLATE_CHIP, true);
        donut1 = new Donut(GLAZED);
        americano = new Americano(DrinkTemperature.HOT);
        itemList = List.of(bagel1, cookie1, donut1, americano);
        coffeeShopOrder = new CoffeeShopOrder("Emilie", itemList);
    }

    @Test
    public void testBagelRecord()
    {
        Bagel bagel2 = new Bagel(EVERYTHING, HERB_GARLIC_CREAM_CHEESE, true);
        assertTrue(Bagel.class.isRecord());
        assertEquals(bagel1, bagel2);
        assertEquals("Bagel[bagelType=EVERYTHING, spreadType=HERB_GARLIC_CREAM_CHEESE, toasted=true]", bagel1.toString());
        assertTrue(bagel1.toasted());
        assertEquals(bagel1.bagelType(), EVERYTHING);
        assertEquals(bagel1.spreadType(), HERB_GARLIC_CREAM_CHEESE);
    }

    @Test
    public void generateReceiptForFoodItemsTest()
    {
        String expectedReceipt = """
                Bagel: EVERYTHING $2.5
                Cookie: CHOCOLATE_CHIP $1.25
                Donut: GLAZED $1.75
                Total: $5.5""";
        assertEquals(expectedReceipt, coffeeShopOrder.generateReceiptForFoodItems());
    }

    @Test
    public void getFoodItemsForOrderTest()
    {
        List<String> expected = List.of("CHOCOLATE_CHIP cookie", "EVERYTHING bagel with HERB_GARLIC_CREAM_CHEESE", "GLAZED donut");
        List<String> actual = coffeeShopOrder.getFoodItemsForOrder();
        Collections.sort(actual);
        assertEquals(expected, actual);
    }


    @Test
    public void testSealedClasses()
    {
        assertTrue(CoffeeDrink.class.isSealed());
        assertFalse(Americano.class.isSealed());
        assertFalse(Macchiato.class.isSealed());
        assertFalse(Latte.class.isSealed());
    }

    @Test
    public void getDrinkItems()
    {
        List<String> expected = List.of("HOT Americano", "HOT CARAMEL Latte with ALMOND_MILK",
                "HOT VANILLA Macchiato with WHOLE_MILK", "MATCHA Tea");
        List<String> actual = coffeeShopOrder.getDrinksForOrder();
        Collections.sort(actual);
        assertEquals(expected, actual);
    }
}
