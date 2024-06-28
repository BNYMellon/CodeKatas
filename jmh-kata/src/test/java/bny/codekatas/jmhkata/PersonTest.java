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

package bny.codekatas.jmhkata;

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
