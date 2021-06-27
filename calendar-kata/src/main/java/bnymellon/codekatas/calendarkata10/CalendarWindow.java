/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.calendarkata10;

import java.time.LocalDate;

import org.eclipse.collections.api.multimap.Multimap;
import org.threeten.extra.LocalDateRange;

public class CalendarWindow
{
    LocalDateRange range;
    Multimap<LocalDate, Meeting> meetings;

    public LocalDate getStart()
    {
        return this.range.getStart();
    }

    public LocalDate getEnd()
    {
        return this.range.getEnd().minusDays(1);
    }

    public int getNumberOfMeetings()
    {
        return this.meetings.size();
    }

    protected String iterateMeetings()
    {
        var builder = new StringBuilder();
        this.range.stream().forEach(date -> {
            builder.append("Date=" + date);
            builder.append(" {Meetings= ");
            builder.append(this.meetings.get(date));
            builder.append("} ");
        });
        return builder.toString();
    }
}
