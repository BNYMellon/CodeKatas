/*
 * Copyright 2020 The Bank of New York Mellon.
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

package bnymellon.codekatas.calendarkata10;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.TimeZone;

import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MyCalendar10Test
{
    private MyCalendar calendar;

    /**
     * Do not change this method!  The tests will fail due to overlapping meetings.  Implement the logic in
     * MyCalendar to check for overlapping meetings.
     *
     * @see MyCalendar#hasOverlappingMeeting(LocalDate, LocalTime, Duration) )
     */
    @BeforeEach
    public void setUp() throws Exception
    {
        this.calendar = new MyCalendar(TimeZone.getTimeZone("UTC"));
        this.setupWeekendMeetings();
        this.setupOverlappingWeekendMeetings();
        this.setupWeekdayMeetings();
        this.setupOverlappingWeekdayMeetings();
    }

    /**
     * Do not change this method!
     */
    private void setupWeekendMeetings()
    {
        this.calendar.addMeeting("Soccer Match",
                LocalDate.of(2017, 7, 2),
                LocalTime.of(13, 0),
                Duration.ofHours(2));
        this.calendar.addMeeting("Swimming Championship",
                LocalDate.of(2017, 7, 8),
                LocalTime.of(13, 0),
                Duration.ofHours(2));
    }

    /**
     * Do not change this method!
     */
    private void setupOverlappingWeekendMeetings()
    {
        this.calendar.addMeeting("Soccer Match",
                LocalDate.of(2017, 7, 2),
                LocalTime.of(12, 30),
                Duration.ofHours(1));
        this.calendar.addMeeting("Swimming Championship",
                LocalDate.of(2017, 7, 8),
                LocalTime.of(12, 30),
                Duration.ofHours(1));
    }

    /**
     * Do not change this method!
     */
    private void setupWeekdayMeetings()
    {
        this.calendar.addMeeting("Lunch",
                LocalDate.of(2017, 7, 3),
                LocalTime.NOON,
                Duration.ofHours(1));
        this.calendar.addMeeting("Lunch",
                LocalDate.of(2017, 7, 5),
                LocalTime.NOON,
                Duration.ofHours(1));
        this.calendar.addMeeting("Lunch",
                LocalDate.of(2017, 7, 6),
                LocalTime.NOON,
                Duration.ofHours(1));
        this.calendar.addMeeting("Lunch",
                LocalDate.of(2017, 7, 7),
                LocalTime.NOON,
                Duration.ofHours(1));
    }

    /**
     * Do not change this method!
     */
    private void setupOverlappingWeekdayMeetings()
    {
        this.calendar.addMeeting("Lunch",
                LocalDate.of(2017, 7, 3),
                LocalTime.NOON.plusMinutes(30),
                Duration.ofHours(1));
        this.calendar.addMeeting("Lunch",
                LocalDate.of(2017, 7, 5),
                LocalTime.NOON.plusMinutes(30),
                Duration.ofHours(1));
        this.calendar.addMeeting("Lunch",
                LocalDate.of(2017, 7, 6),
                LocalTime.NOON.plusMinutes(30),
                Duration.ofHours(1));
        this.calendar.addMeeting("Lunch",
                LocalDate.of(2017, 7, 7),
                LocalTime.NOON.plusMinutes(30),
                Duration.ofHours(1));
    }

    @Test
    public void printCalendar()
    {
        System.out.println(this.calendar);
    }

    /**
     * Implement the {@link MyCalendar#hasOverlappingMeeting(LocalDate, LocalTime, Duration)} method.
     */
    @Test
    public void hasOverlappingMeeting()
    {
        Assertions.assertTrue(this.calendar.hasOverlappingMeeting(
                LocalDate.of(2017, 7, 7),
                LocalTime.NOON,
                Duration.ofHours(1)));
    }

    @Test
    public void getMeetingsForDate()
    {
        var meetingsForJuly6 = this.calendar.getMeetingsForDate(LocalDate.of(2017, 7, 6));
        Verify.assertSize(1, meetingsForJuly6);
        System.out.println(meetingsForJuly6);
    }

    @Test
    public void getMeetingsForWorkWeekOf()
    {
        var workWeek = this.calendar.getMeetingsForWorkWeekOf(LocalDate.of(2017, 7, 6));
        Assertions.assertEquals(4, workWeek.getNumberOfMeetings());
        System.out.println(workWeek);
    }

    @Test
    public void getMeetingsForFullWeekOf()
    {
        var fullWeek = this.calendar.getMeetingsForFullWeekOf(LocalDate.of(2017, 7, 6));
        Assertions.assertEquals(6, fullWeek.getNumberOfMeetings());
        System.out.println(fullWeek);
    }

    @Test
    public void getMeetingsForMonthOf()
    {
        var fullMonth = this.calendar.getMeetingsForYearMonth(2017, Month.JULY);
        Assertions.assertEquals(6, fullMonth.getNumberOfMeetings());
        System.out.println(fullMonth);
    }

    @Test
    public void getAvailableTimeslots()
    {
        var availableTimeslots1 = this.calendar.getAvailableTimeslots(LocalDate.of(2017, 7, 6));
        Assertions.assertEquals(2, availableTimeslots1.size());
        System.out.println(availableTimeslots1);
        var availableTimeslots2 = this.calendar.getAvailableTimeslots(LocalDate.of(2017, 7, 1));
        Assertions.assertEquals(1, availableTimeslots2.size());
        System.out.println(availableTimeslots2);
    }
}
