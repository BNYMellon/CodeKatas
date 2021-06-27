/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.jmhkata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This is a set of unit tests to validate the code is working as expected.
 */
public class PersonTest
{
    @Test
    public void ageStatistics()
    {
        Assertions.assertEquals(Person.NUMBER_OF_PEOPLE,
                            Person.getJDKPeople().stream().mapToInt(Person::getAge).summaryStatistics().getCount());
        Assertions.assertEquals(Person.NUMBER_OF_PEOPLE,
                            Person.getECPeople().asLazy().collectInt(Person::getAge).summaryStatistics().getCount());
    }

    @Test
    public void weightStatisticsJDK()
    {
        Assertions.assertEquals(Person.NUMBER_OF_PEOPLE,
                            Person.getJDKPeople().stream().mapToDouble(Person::getWeightInPounds).summaryStatistics().getCount());
        Assertions.assertEquals(Person.NUMBER_OF_PEOPLE,
                            Person.getECPeople().asLazy().collectDouble(Person::getWeightInPounds).summaryStatistics().getCount());
    }

    @Test
    public void heightStatisticsJDK()
    {
        Assertions.assertEquals(Person.NUMBER_OF_PEOPLE,
                            Person.getJDKPeople().stream().mapToDouble(Person::getHeightInInches).summaryStatistics().getCount());
        Assertions.assertEquals(Person.NUMBER_OF_PEOPLE,
                            Person.getECPeople().asLazy().collectDouble(Person::getHeightInInches).summaryStatistics().getCount());
    }
}
