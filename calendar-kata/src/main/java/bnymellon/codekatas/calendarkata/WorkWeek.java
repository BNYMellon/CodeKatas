  /**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
*/

package bnymellon.codekatas.calendarkata;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import org.eclipse.collections.api.multimap.sortedset.SortedSetMultimap;
import org.threeten.extra.LocalDateRange;

public class WorkWeek extends CalendarWindow
{
    /**
     * TODO Calculate the start date and range of dates for the 5 day week from Monday to Friday.
     *
     * Hint: Look at {@link LocalDate#with(TemporalAdjuster)}
     * Hint: Look at {@link TemporalAdjusters#previousOrSame(DayOfWeek)}
     * Hint: Look at {@link LocalDate#plusDays(long)}
     * Hint: Look at {@link LocalDateRange#of(LocalDate, LocalDate)}
     * Hint: The end date is exclusive in LocalDateRange
     */
    public WorkWeek(LocalDate forDate, SortedSetMultimap<LocalDate, Meeting> calendarMeetings)
    {
        LocalDate start = null;
        this.range = null;
        this.meetings = calendarMeetings.selectKeysValues((date, meeting) -> this.range.contains(date));
    }

    @Override
    public String toString()
    {
        return "WorkWeek(" +
                "start=" + this.getStart() +
                ", end=" + this.getEnd() +
                ", meetings=" + this.iterateMeetings() +
                ')';
    }
}
