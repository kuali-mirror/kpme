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
package org.kuali.kpme.core.calendar.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.calendar.Calendar;
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

  

    public List<Calendar> getCalendars(String calendarName, String calendarTypes, String flsaBeginDay, String flsaBeginTime);

}
