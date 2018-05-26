# **What is the Calendar Kata?**

The Calendar Kata is an advanced kata which can help developers
become familiar with the [Java 8 Date/Time](https://docs.oracle.com/javase/8/docs/api/java/time/package-summary.html) 
and [ThreeTen-Extra](http://www.threeten.org/threeten-extra/) libraries.  

The domain for the Calendar kata is an object representation of an Outlook Calendar.  
There are several domain classes that are shared by all of the exercises.  These are 
[`MyCalendar`](./src/main/java/bnymellon/codekatas/calendarkata/MyCalendar.java), 
[`Meeting`](./src/main/java/bnymellon/codekatas/calendarkata/Meeting.java),
[`WorkWeek`](./src/main/java/bnymellon/codekatas/calendarkata/WorkWeek.java),
[`FullWeek`](./src/main/java/bnymellon/codekatas/calendarkata/FullWeek.java),
[`FullMonth`](./src/main/java/bnymellon/codekatas/calendarkata/FullMonth.java) and

![Diagram](mycalendar.png)
</p> 

### How to get started

Java 8 Version:
* There are failing tests in [`MyCalendarTest`](./src/test/java/bnymellon/codekatas/calendarkata/MyCalendarTest.java)
	* Make the tests pass by following and completing the TODOs in MyCalendar, WorkWeek, FullMonth and FullWeek. 

Java 10 Version (uses [Local-Variable Type Inference](http://openjdk.java.net/jeps/286) feature):
* There are failing tests in [`MyCalendarTest`](./src/test/java/bnymellon/codekatas/calendarkata10/MyCalendar10Test.java)
	* Make the tests pass by following and completing the TODOs in MyCalendar, WorkWeek, FullMonth and FullWeek. 
