// Copyright 2021 BNYMellon. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

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
