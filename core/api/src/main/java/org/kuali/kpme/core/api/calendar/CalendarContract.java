/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.api.calendar;

import java.sql.Time;
import java.util.List;

import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;
import org.kuali.kpme.core.api.util.HrApiConstants;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

/**
 * <p>CalendarContract interface</p>
 *
 */
public interface CalendarContract extends PersistableBusinessObject {
	
	public static final String CACHE_NAME = HrApiConstants.CacheNamespace.NAMESPACE_PREFIX + "Calendar";

	/**
	 * The primary Key of a Calendar entry saved in a database
	 * 
	 * <p>
	 * hrCalendarId of a Calendar
	 * <p>
	 * 
	 * @return hrCalendarId for a Calendar
	 */
	public String getHrCalendarId();

	/**
	 * The text field used to define the calendar entry for pay or leave reporting periods
	 * 
	 * <p>
	 * calendarName of a Calendar
	 * </p>
	 * 
	 * @return calendarName of Calendar
	 */
	public String getCalendarName();

	/**
	 * The Text field used to indicate calendar is to be used for pay or leave reporting periods
	 * 
	 * <p>
	 * calendarTypes of a Calendar
	 * </p>
	 * 
	 * @return calendarTypes of Calendar
	 */
	public String getCalendarTypes();

	/**
	 * The list of CalendarEntry object the Calendar is associated with
	 * 
	 * <p>
	 * calendarEntries of a Calendar
	 * </p>
	 * 
	 * @return calendarEntries of Calendar
	 */
	public List<? extends CalendarEntryContract> getCalendarEntries();

	/**
	 * The Text field used to describe the calendar
	 * 
	 * <p>
	 * calendarDescriptions of a Calendar
	 * </p>
	 * 
	 * @return calendarDescriptions of Calendar
	 */
	public String getCalendarDescriptions();

	/**
	 * The value that determines the FLSA period for overtime calculations
	 * 
	 * <p>
	 * flsaBeginDay of a Calendar
	 * </p>
	 * 
	 * @return flsaBeginDay for Calendar
	 */
	public String getFlsaBeginDay();
	
	/**
	 * The time of day when FLSA period begins
	 * 
	 * <p>
	 * flsaBeginTime of a Calendar
	 * </p>
	 * 
	 * @return flsaBeginTime for Calendar
	 */
	public Time getFlsaBeginTime();

	/**
	 * The FLSA start day the Calendar is associated with
	 * 
	 * <p>
	 * org.joda.time.DateTimeConstants.MONDAY
	 * ...
	 * org.joda.time.DateTimeConstants.SUNDAY
	 * </p>
	 * 
	 * @return an int representing the FLSA start day, sourced from org.joda.time.DateTimeConstants in the interval [1,7]
	 */
	public int getFlsaBeginDayConstant();
    
}