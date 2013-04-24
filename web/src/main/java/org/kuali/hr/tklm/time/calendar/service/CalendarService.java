/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.hr.tklm.time.calendar.service;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.hr.tklm.time.calendar.Calendar;
import org.kuali.hr.tklm.time.calendar.CalendarEntry;
import org.springframework.cache.annotation.Cacheable;

public interface CalendarService {
	/**
	 * Fetch a pay calendar with the given id
	 * @param hrCalendarId
	 * @return
	 */
    @Cacheable(value= Calendar.CACHE_NAME, key="'hrCalendarId=' + #p0")
	public Calendar getCalendar(String hrCalendarId);

	/**
	 * Fetch a pay calendar by group
	 * @param calendarName
	 * @return
	 */
    @Cacheable(value= Calendar.CACHE_NAME, key="'calendarName=' + #p0")
	public Calendar getCalendarByGroup(String calendarName);

	/**
     * Use this method to get CalendarEntry if you are passing in a "current date"
     * style of date, ie todays date. If you are in a logic situation where you would
     * pass EITHER todays date or a pay calendar date, pass the Pay period BEGIN date,
     * so that the retrieval logic will correctly place the date in the window.
	 *
	 * @param principalId
	 * @param currentDate
	 * @return
	 */
	public CalendarEntry getCurrentCalendarDates(String principalId, DateTime currentDate);

    /**
     * Use this method to get CalendarEntry if you are passing in a date range
     *  ie being/end of a year.
     *
     * @param principalId
     * @param beginDate
     * @param endDate
     * @return
     */
    public CalendarEntry getCurrentCalendarDates(String principalId, DateTime beginDate, DateTime endDate);

    /**
     * A method to use specifically when you have a Timesheet Documents Pay Period
     * end date. Do not use if you are passing in a date known not to be the END period date.
     *
     * @param principalId
     * @param payEndDate
     * @return
     */
    @Cacheable(value= Calendar.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'payEndDate=' + #p1 + '|' + 'calendarType=' + #p2")
    public CalendarEntry getCalendarDatesByPayEndDate(String principalId, DateTime payEndDate, String calendarType);

	/**
	 * Returns the Pay CalendarEntry for previous pay calendar
	 * @param tkCalendarId
	 * @param beginDateCurrentCalendar
	 * @return
	 */
    @Cacheable(value= Calendar.CACHE_NAME, key="'tkCalendarId=' + #p0 + '|' + 'beginDateCurrentCalendar=' + #p1")
	public CalendarEntry getPreviousCalendarEntry(String tkCalendarId, DateTime beginDateCurrentCalendar);
	
	/**
	 * Fetch a pay calendar with the given principalId and date, returns null if none found
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
	public Calendar getCalendarByPrincipalIdAndDate(String principalId, LocalDate asOfDate, boolean findLeaveCal);

    /**
     * Fetch a pay calendar with the given principalId, begin and end date, returns null if none found
     * @param principalId
     * @param beginDate
     * @param endDate
     * @return
     */
    public Calendar getCalendarByPrincipalIdAndDate(String principalId, LocalDate beginDate, LocalDate endDate, boolean findLeaveCal);

    /**
     *
     * @param principalId
     * @param beginDate
     * @param endDate
     * @return
     */
    @Cacheable(value= Calendar.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'beginDate=' + #p1 + '|' + 'endDate=' + #p2")
    public CalendarEntry getCurrentCalendarDatesForLeaveCalendar(String principalId, DateTime beginDate, DateTime endDate);

	/**
	 * 
	 * @param principalId
	 * @param currentDate
	 * @return
	 */
    @Cacheable(value= Calendar.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'currentDate=' + #p1")
	public CalendarEntry getCurrentCalendarDatesForLeaveCalendar(String principalId, DateTime currentDate);

    public List<Calendar> getCalendars(String calendarName, String calendarTypes, String flsaBeginDay, String flsaBeginTime);

}
