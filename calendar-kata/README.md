# **What is the Calendar Kata?**

The Calendar Kata is an advanced kata which can help developers
become familiar with the [Java 8 Date/Time](https://docs.oracle.com/javase/8/docs/api/java/time/package-summary.html) 
and [ThreeTen-Extra](http://www.threeten.org/threeten-extra/) libraries.  

The domain for the Calendar kata is an object representation of an Outlook Calendar.  
There are several domain classes that are shared by all of the exercises.  These are 
[`MyCalendar`](src/main/java/bny/codekatas/calendarkata/MyCalendar.java), 
[`Meeting`](src/main/java/bny/codekatas/calendarkata/Meeting.java),
[`WorkWeek`](src/main/java/bny/codekatas/calendarkata/WorkWeek.java),
[`FullWeek`](src/main/java/bny/codekatas/calendarkata/FullWeek.java),
[`FullMonth`](src/main/java/bny/codekatas/calendarkata/FullMonth.java) and

![Diagram](mycalendar.png)
</p> 

### How to get started

* There are failing tests in [`MyCalendarTest`](./src/test/java/bny/codekatas/calendarkata/MyCalendarTest.java)
	* Make the tests pass by following and completing the TODOs in MyCalendar, WorkWeek, FullMonth and FullWeek.