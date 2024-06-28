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

package bny.codekatas.calendarkata;

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
