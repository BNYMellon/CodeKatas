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

package bny.codekatas.coffeeshopkata.beverage;

public non-sealed class Macchiato implements CoffeeDrink
{
    private final MilkType milkType;
    private final FlavorSyrup flavorSyrup;
    private final DrinkTemperature drinkTemperature;

    public Macchiato(MilkType milkType, FlavorSyrup flavorSyrup, DrinkTemperature drinkTemperature)
    {
        this.milkType = milkType;
        this.flavorSyrup = flavorSyrup;
        this.drinkTemperature = drinkTemperature;
    }

    public MilkType getMilkType()
    {
        return milkType;
    }

    public FlavorSyrup getFlavorSyrup()
    {
        return flavorSyrup;
    }

    public DrinkTemperature getDrinkTemperature()
    {
        return drinkTemperature;
    }

    @Override
    public int espressoShot()
    {
        return 2;
    }

    @Override
    public String toString()
    {
        return drinkTemperature + " " + flavorSyrup + " Macchiato with " + milkType;
    }

    @Override
    public double getPrice()
    {
        return 3.75;
    }
}
