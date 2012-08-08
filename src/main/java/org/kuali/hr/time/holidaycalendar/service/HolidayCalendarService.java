package org.kuali.hr.time.holidaycalendar.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.kuali.hr.time.holidaycalendar.HolidayCalendarDateEntry;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.springframework.cache.annotation.Cacheable;

public interface HolidayCalendarService {
	/**
	 * Fetch holiday calendar group
	 * @param holidayCalendarGroup
	 * @return
	 */
    @Cacheable(value= HolidayCalendar.CACHE_NAME, key="'holidayCalendarGroup=' + #p0")
	public HolidayCalendar getHolidayCalendarByGroup(String holidayCalendarGroup);
	/**
	 * Fetch List of HolidayCalendarDateEntry for a given pay periods start and end date
	 * @param hrHolidayCalendarId
	 * @param startDate
	 * @param endDate
	 * @return
	 */

    @Cacheable(value= HolidayCalendar.CACHE_NAME,
            key="'hrHolidayCalendarId=' + #p0" +
                    "+ '|' + 'startDate=' + #p1" +
                    "+ '|' + 'endDate=' + #p2")
	public List<HolidayCalendarDateEntry> getHolidayCalendarDateEntriesForPayPeriod(String hrHolidayCalendarId, Date startDate, Date endDate);
	/**
	 * Fetch a HolidayCalendarDateEntry for a given hrHolidayCalendarId and date
	 * @param hrHolidayCalendarId
	 * @param date
	 * @return
	 */
    @Cacheable(value= HolidayCalendar.CACHE_NAME, key="'hrHolidayCalendarId=' + #p0 + '|' + 'startDate=' + #p1")
	public HolidayCalendarDateEntry getHolidayCalendarDateEntryByDate(String hrHolidayCalendarId, Date startDate);
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
