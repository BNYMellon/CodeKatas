/**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
*/
package bnymellon.codekatas.donutkatakotlin

import org.eclipse.collections.impl.utility.ArrayIterate

enum class DonutType(private val abbreviation: String)
{
    BOSTON_CREAM("BC"),
    GLAZED("G"),
    OLD_FASHIONED("OF"),
    CHOCOLATE_GLAZED("CG"),
    VANILLA_FROSTED("VF"),
    PUMPKIN("P"),
    BLUEBERRY("B"),
    JELLY("J"),
    BAVARIAN_CREAM("BA");

    private fun abbreviationEquals(abbreviation: String): Boolean
    {
        return abbreviation == this.abbreviation
    }

    companion object
    {
        fun forAbbreviation(abbreviation: String): DonutType
        {
            return ArrayIterate.detectWith(
                DonutType.values(),
                { obj, abbr -> obj.abbreviationEquals(abbr) },
                abbreviation)
        }
    }
}
