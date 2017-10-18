/*
 * Copyright 2017 BNY Mellon.
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

package bnymellon.codekatas.jmhkata;

import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is just a set of unit tests to validate the code is working as expected.
 */
public class PersonTest
{
    @Test
    public void ageStatisticsJDK()
    {
        IntSummaryStatistics stats =
                Person.getJDKPeople().stream().mapToInt(Person::getAge).summaryStatistics();
        Assert.assertEquals(Person.NUMBER_OF_PEOPLE, stats.getCount());
    }

    @Test
    public void ageStatisticsEC()
    {
        IntSummaryStatistics stats =
                Person.getECPeople().asLazy().collectInt(Person::getAge).summaryStatistics();
        Assert.assertEquals(Person.NUMBER_OF_PEOPLE, stats.getCount());
    }

    @Test
    public void weightStatisticsJDK()
    {
        DoubleSummaryStatistics stats =
                Person.getJDKPeople().stream().mapToDouble(Person::getWeightInPounds).summaryStatistics();
        Assert.assertEquals(Person.NUMBER_OF_PEOPLE, stats.getCount());
    }

    @Test
    public void weightStatisticsEC()
    {
        DoubleSummaryStatistics stats =
                Person.getECPeople().asLazy().collectDouble(Person::getWeightInPounds).summaryStatistics();
        Assert.assertEquals(Person.NUMBER_OF_PEOPLE, stats.getCount());
    }

    @Test
    public void heightStatisticsJDK()
    {
        DoubleSummaryStatistics stats =
                Person.getJDKPeople().stream().mapToDouble(Person::getHeightInInches).summaryStatistics();
        Assert.assertEquals(Person.NUMBER_OF_PEOPLE, stats.getCount());
    }

    @Test
    public void heightStatisticsEC()
    {
        DoubleSummaryStatistics stats =
                Person.getECPeople().asLazy().collectDouble(Person::getHeightInInches).summaryStatistics();
        Assert.assertEquals(Person.NUMBER_OF_PEOPLE, stats.getCount());
    }
}
