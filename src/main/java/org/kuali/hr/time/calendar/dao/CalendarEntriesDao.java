package org.kuali.hr.time.calendar.dao;

import org.kuali.hr.time.calendar.CalendarEntries;

import java.util.Date;
import java.util.List;

public interface CalendarEntriesDao {

	public void saveOrUpdate(CalendarEntries calendarEntries);
	public CalendarEntries getCalendarEntries(Long hrPyCalendarEntriesId);

    public CalendarEntries getCalendarEntriesByIdAndPeriodEndDate(Long hrPyCalendarId, Date endPeriodDate);
	public CalendarEntries getCurrentCalendarEntriesByCalendarId(Long hrPyCalendarId, Date currentDate);
    public CalendarEntries getNextCalendarEntriesByCalendarId(Long hrPyCalendarId, CalendarEntries calendarEntries);
    public CalendarEntries getPreviousCalendarEntriesByCalendarId(Long hrPyCalendarId, CalendarEntries calendarEntries);


	public List<CalendarEntries> getCurrentCalendarEntryNeedsScheduled(int thresholdDays, Date asOfDate);
}
