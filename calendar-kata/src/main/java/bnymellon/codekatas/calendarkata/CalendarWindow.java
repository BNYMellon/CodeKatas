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
        StringBuilder builder = new StringBuilder();
        this.range.stream().forEach(date -> {
            builder.append("Date=" + date);
            builder.append(" {Meetings= ");
            builder.append(this.meetings.get(date));
            builder.append("} ");
        });
        return builder.toString();
    }
}
