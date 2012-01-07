package org.kuali.hr.time.calendar.dao;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.calendar.CalendarEntries;

public interface CalendarEntriesDao {

	public void saveOrUpdate(CalendarEntries calendarEntries);
	public CalendarEntries getCalendarEntries(String hrPyCalendarEntriesId);

    public CalendarEntries getCalendarEntriesByIdAndPeriodEndDate(String hrPyCalendarId, Date endPeriodDate);
	public CalendarEntries getCurrentCalendarEntriesByCalendarId(String hrPyCalendarId, Date currentDate);
    public CalendarEntries getNextCalendarEntriesByCalendarId(String hrPyCalendarId, CalendarEntries calendarEntries);
    public CalendarEntries getPreviousCalendarEntriesByCalendarId(String hrPyCalendarId, CalendarEntries calendarEntries);


	public List<CalendarEntries> getCurrentCalendarEntryNeedsScheduled(int thresholdDays, Date asOfDate);
}
