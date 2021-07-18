  /**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
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
