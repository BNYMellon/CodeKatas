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
