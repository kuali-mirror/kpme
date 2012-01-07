package org.kuali.hr.time.calendar.dao;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;

public interface CalendarDao {

	public void saveOrUpdate(Calendar calendarDates);

	public void saveOrUpdate(List<Calendar> calendarDatesList);

	public Calendar getCalendar(String hrPyCalendarId);

	public Calendar getCalendarByGroup(String pyCalendarGroup);
	
	public CalendarEntries getPreviousCalendarEntry(String tkCalendarId, Date beginDateCurrentCalendar);

}
