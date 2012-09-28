/**
 * Copyright 2004-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
