  /**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
*/

package bnymellon.codekatas.calendarkata;

import java.time.LocalDate;

import org.eclipse.collections.api.multimap.sortedset.SortedSetMultimap;
import org.threeten.extra.LocalDateRange;

public class FullMonth extends CalendarWindow
{
    /**
     * TODO Calculate the start, end and range of dates for the full month including the specified date.
     *
     * Hint: Look at {@link LocalDate#withDayOfMonth(int)}
     * Hint: Look at {@link LocalDate#lengthOfMonth()}
     * Hint: Look at {@link LocalDateRange#of(LocalDate, LocalDate)}
     * Hint: The end date is exclusive in LocalDateRange
     */
    public FullMonth(LocalDate forDate, SortedSetMultimap<LocalDate, Meeting> calendarMeetings)
    {
        LocalDate start = null;
        LocalDate end = null;
        this.range = null;
        this.meetings = calendarMeetings.selectKeysValues(
                (date, meeting) ->
                        date.getMonth().equals(start.getMonth()) &&
                                date.getYear() == start.getYear());
    }

    @Override
    public String toString()
    {
        return "FullMonth(" +
                "start=" + this.getStart() +
                ", end=" + this.getEnd() +
                ", meetings=" + this.iterateMeetings() +
                ')';
    }
}
