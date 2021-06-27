/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.calendarkata10;

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
