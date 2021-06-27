/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.donutkata;

import org.eclipse.collections.impl.utility.ArrayIterate;

public enum DonutType
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

    private String abbreviation;

    DonutType(String abbreviation)
    {
        this.abbreviation = abbreviation;
    }

    private boolean abbreviationEquals(String abbreviation)
    {
        return abbreviation.equals(this.abbreviation);
    }

    public static DonutType forAbbreviation(String abbreviation)
    {
        return ArrayIterate.detectWith(DonutType.values(), DonutType::abbreviationEquals, abbreviation);
    }
}
