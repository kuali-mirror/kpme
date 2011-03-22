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
	/**
	 * Fetch holiday calendar group
	 * @param holidayCalendarGroup
	 * @return
	 */
	public HolidayCalendar getHolidayCalendarByGroup(String holidayCalendarGroup);
	/**
	 * Fetch List of HolidayCalendarDateEntry for a given pay periods start and end date
	 * @param holidayCalendarId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<HolidayCalendarDateEntry> getHolidayCalendarDateEntriesForPayPeriod(Long holidayCalendarId, Date startDate, Date endDate);
	/**
	 * Get Assignment to apply to holidays
	 * @param timesheetDocument
	 * @param payEndDate
	 * @return
	 */
	public Assignment getAssignmentToApplyHolidays(TimesheetDocument timesheetDocument, java.sql.Date payEndDate);
	/**
	 * Calculate the total of holiday hours for a given Job and holiday hours
	 * @param job
	 * @param holidayHours
	 * @return
	 */
	public BigDecimal calculateHolidayHours(Job job, BigDecimal holidayHours);
}
