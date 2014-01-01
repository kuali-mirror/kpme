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
package org.kuali.kpme.tklm.api.time.calendar;

import java.util.List;

import org.joda.time.DateTime;
import org.kuali.kpme.core.api.calendar.CalendarParentContract;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;


/**
 * <p>TkCalendarContract interface</p>
 *
 */
public interface TkCalendarContract extends CalendarParentContract {

    /**
	 * The payCalEntry object associated with the TkCalendar
	 * 
	 * <p>
	 * payCalEntry of a TkCalendar
	 * <p>
	 * 
	 * @return payCalEntry for TkCalendar
	 */
    public CalendarEntryContract getPayCalEntry();

    /**
	 * The beginDateTime associated with the TkCalendar
	 * 
	 * <p>
	 * beginDateTime of a TkCalendar
	 * <p>
	 * 
	 * @return beginDateTime for TkCalendar
	 */
    public DateTime getBeginDateTime();

    /**
	 * The endDateTime associated with the TkCalendar
	 * 
	 * <p>
	 * endDateTime of a TkCalendar
	 * <p>
	 * 
	 * @return endDateTime for TkCalendar
	 */
    public DateTime getEndDateTime();
    
    /**
	 * The string for calendar title use associated with the TkCalendar
	 * 
	 * <p>
	 * Provides the calendar title/heading. If the Pay Calendar entry spans
     * multiple months, you get Abbreviated Month name + year of both the
     * beginning month and the ending month.
	 * <p>
	 * 
	 * @return a string for calendar title use for TkCalendar
	 */
    public String getCalendarTitle();
    
    /**
	 * The list of string titles for each row block (day) associated with the TkCalendar
	 * 
	 * <p>
	 * Assumption of 7 "days" per week, or 7 "blocks" per row.
	 * <p>
	 * 
	 * @return a list of string titles for each row block (day) for TkCalendar
	 */
    public List<String> getCalendarDayHeadings();

    /**
	 * The year portion of beginDateTime associated with the TkCalendar
	 * 
	 * <p>
	 * year portion of beginDateTime of a TkCalendar
	 * <p>
	 * 
	 * @return getBeginDateTime().toString("yyyy") for TkCalendar
	 */
    public String getCalenadrYear();

    /**
	 * The month portion of beginDateTime associated with the TkCalendar
	 * 
	 * <p>
	 * month portion of beginDateTime of a TkCalendar
	 * <p>
	 * 
	 * @return getBeginDateTime().toString("M") for TkCalendar
	 */
    public String getCalendarMonth();

}
