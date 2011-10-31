package org.kuali.hr.time.calendar.dao;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;

public interface CalendarDao {

	public void saveOrUpdate(Calendar payCalendarDates);

	public void saveOrUpdate(List<Calendar> payCalendarDatesList);

	public Calendar getPayCalendar(Long hrPyCalendarId);

	public Calendar getPayCalendarByGroup(String pyCalendarGroup);
	
	public CalendarEntries getPreviousPayCalendarEntry(Long tkPayCalendarId, Date beginDateCurrentPayCalendar);

}
