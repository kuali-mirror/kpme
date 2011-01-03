package org.kuali.hr.time.holidaycalendar.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.kuali.hr.time.holidaycalendar.HolidayCalendarDateEntry;
import org.kuali.hr.time.timesheet.TimesheetDocument;

public interface HolidayCalendarService {
	public HolidayCalendar getHolidayCalendarByGroup(String holidayCalendarGroup);
	public List<HolidayCalendarDateEntry> getHolidayCalendarDateEntriesForPayPeriod(Long holidayCalendarId, Date startDate, Date endDate);
	public Assignment getAssignmentToApplyHolidays(TimesheetDocument timesheetDocument, java.sql.Date payEndDate);
	public BigDecimal calculateHolidayHours(Job job, BigDecimal holidayHours);
}
