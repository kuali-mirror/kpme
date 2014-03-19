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

import org.joda.time.DateTime;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.calendar.web.CalendarWeekContract;

import java.util.List;

/**
 * <p>CalendarParentContract interface</p>
 *
 */
public interface CalendarParentContract {

	/**
	 * The being date time the CalendarParent is associated with
	 * 
	 * <p>
	 * beginDateTime of a CalendarParent
	 * </p>
	 * 
	 * @return beginDateTime of CalendarParent
	 */
    public DateTime getBeginDateTime();
   
    /**
	 * The end date time the CalendarParent is associated with
	 * 
	 * <p>
	 * endDateTime of a CalendarParent
	 * </p>
	 * 
	 * @return endDateTime of CalendarParent
	 */
    public DateTime getEndDateTime();
    
    /**
	 * The CalendarEntry object the CalendarParent is associated with
	 * 
	 * <p>
	 * calendarEntry of a CalendarParent
	 * </p>
	 * 
	 * @return calendarEntry of CalendarParent
	 */
    public CalendarEntry getCalendarEntry();
  
    /**
	 * The list of CalendarWeek objects the CalendarParent is associated with
	 * 
	 * <p>
	 * weeks of a CalendarParent
	 * </p>
	 * 
	 * @return weeks of CalendarParent
	 */
    public List<? extends CalendarWeekContract> getWeeks();

    /**
	 * The calendar title/heading the CalendarParent is associated with
	 * 
	 * <p>
	 * If the Pay Calendar entry spans multiple months, you get Abbreviated Month name + year of both the
     * beginning month and the ending month.
	 * </p>
	 * 
	 * @return String for calendar title use
	 */
    public String getCalendarTitle() ;

    /**
	 * The calendar day headings the CalendarParent is associated with
	 * 
	 * <p>
	 * Assumption of 7 "days" per week, or 7 "blocks" per row.
	 * </p>
	 * 
	 * @return A list of string titles for each row block (day)
	 */
    public List<String> getCalendarDayHeadings();

}