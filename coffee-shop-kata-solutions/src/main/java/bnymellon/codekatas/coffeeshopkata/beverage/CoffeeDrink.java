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

package bnymellon.codekatas.coffeeshopkata.beverage;

/**
 * Prior to Java 15, a class was either declared as 'final' or left 'open,'
 * which meant that the class could be extended infinitely.
 * With the introduction of "sealed" classes, it becomes possible to
 * establish a controlled hierarchy for extensions.
 * Modify the following class to permit only the classes
 * Latte, Macchiato, and Americano, while excluding Tea.
 *
 * <p>
 * NOTE: This class hierarchy shows the usage of sealed classes
 */

//TODO  Convert to Sealed interface
public sealed interface CoffeeDrink extends Beverage permits Latte, Macchiato, Americano
{
    int espressoShot();
}
