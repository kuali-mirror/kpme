package org.kuali.hr.time.paycalendar;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.PeriodFormatterBuilder;

public class MockClockLog {

    private DateTime clockIn;
    private DateTime clockOut;
    
    private Integer clockInTimezone;
    private Integer clockOutTimezone;
    
    private DateTimeFormatter dtfmt = new DateTimeFormatterBuilder()
	.appendMonthOfYearText().appendLiteral(' ')
	.appendDayOfMonth(2).appendLiteral(", ")
	.appendYear(4, 4).appendLiteral(" ")
	.appendHourOfDay(2).appendLiteral(':')
	.appendMinuteOfHour(2).appendLiteral(':')
	.appendSecondOfMinute(2)
	.appendLiteral(" GMT")
	.appendTimeZoneOffset(null, true, 2, 4)
	.toFormatter();

    
    public MockClockLog() {
	clockIn = new DateTime(2010, 6, 10, 12, 13, 55, 0, DateTimeZone.UTC);
	clockOut = new DateTime(2010, 6, 10, 20, 37, 22, 0, DateTimeZone.UTC);
	clockInTimezone = -4;
	clockOutTimezone= -5;
    }
    
    public DateTime getClockIn() { return clockIn; }
    public DateTime getClockOut() { return clockOut; }
    
    public Period getTimeWorked() {
	return new Period(clockIn, clockOut);
    }
    
    public DateTime getClockInWithOffset(int gmtOffset) {
	return clockIn.toDateTime(DateTimeZone.forOffsetHours(gmtOffset));
    }
    
    public DateTime getClockOutWithOffset(int gmtOffset) {
	return clockOut.toDateTime(DateTimeZone.forOffsetHours(gmtOffset));
    }
    
    public String getFormattedClockInWithOffset(int gmtOffset) {
	return this.getClockInWithOffset(gmtOffset).toString(dtfmt);
    }
    
    public String getFormattedClockOutWithOffset(int gmtOffset) {
	return this.getClockOutWithOffset(gmtOffset).toString(dtfmt);
    }
    
    public String getFormattedClockInOriginalOffset() {
	return this.getClockInWithOffset(this.clockInTimezone).toString(dtfmt);
    }
    
    public String getFormattedClockOutOriginalOffset() {
	return this.getClockOutWithOffset(this.clockOutTimezone).toString(dtfmt);
    }
    
    public static void main(String[] args) {
	MockClockLog mcl = new MockClockLog();
	PeriodFormatterBuilder pfb = new PeriodFormatterBuilder()
		.printZeroAlways()
		.appendHours().appendSuffix(" hour ", " hours ")
		.appendMinutes().appendSuffix(" minute ", " minutes ")
		.appendSeconds().appendSuffix(" second ", " seconds ");
	Period period = mcl.getTimeWorked();
	System.out.println("This block represents: " + period.toString(pfb.toFormatter()));
	System.out.println("");
	System.out.println("EDT Relative:");
	System.out.println("Clock in : " + mcl.getFormattedClockInWithOffset(-4));
	System.out.println("Clock out: " + mcl.getFormattedClockOutWithOffset(-4));
	System.out.println("Employee Relative:");
	System.out.println("Clock in : " + mcl.getFormattedClockInOriginalOffset());
	System.out.println("Clock out: " + mcl.getFormattedClockOutOriginalOffset());
	System.out.println("GMT Relative:");
	System.out.println("Clock in : " + mcl.getFormattedClockInWithOffset(0));
	System.out.println("Clock out: " + mcl.getFormattedClockOutWithOffset(0));
    }
}
