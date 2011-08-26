package org.kuali.hr.time.holidaycalendar.dao;

import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.kuali.hr.time.holidaycalendar.HolidayCalendarDateEntry;

import java.util.Date;
import java.util.List;

public interface HolidayCalendarDao {
	public HolidayCalendar getHolidayCalendarByGroup(String holidayCalendarGroup);
	public List<HolidayCalendarDateEntry> getHolidayCalendarDateEntriesForPayPeriod(Long hrHolidayCalendarId,
				Date startDate, Date endDate);
	public HolidayCalendarDateEntry getHolidayCalendarDateEntryByDate(Long hrHolidayCalendarId, Date startDate);
}
