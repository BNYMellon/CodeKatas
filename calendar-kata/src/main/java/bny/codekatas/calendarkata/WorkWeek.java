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
