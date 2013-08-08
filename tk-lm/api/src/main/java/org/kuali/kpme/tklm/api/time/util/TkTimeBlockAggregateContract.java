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
package org.kuali.kpme.tklm.api.time.util;

import java.util.List;

import org.joda.time.DateTimeZone;
import org.kuali.kpme.core.api.calendar.CalendarContract;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlockContract;
import org.kuali.kpme.tklm.api.time.flsa.FlsaWeekContract;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlockContract;

/**
 * <p>TkTimeBlockAggregateContract interface</p>
 *
 */
public interface TkTimeBlockAggregateContract {
	
	/**
	 * The list of TimeBlock objects associated with the TkTimeBlockAggregate
	 * 
	 * <p>
	 * the list of flattened time blocks of a TkTimeBlockAggregate
	 * </p>
	 * 
	 * @return the list of flattened time blocks for TkTimeBlockAggregate
	 */
	public List<? extends TimeBlockContract> getFlattenedTimeBlockList();
	
	/**
	 * The list of LeaveBlock objects associated with the TkTimeBlockAggregate
	 * 
	 * <p>
	 * the list of flattened leave blocks of a TkTimeBlockAggregate
	 * </p>
	 * 
	 * @return the list of flattened leave blocks for TkTimeBlockAggregate
	 */
	public List<? extends LeaveBlockContract> getFlattenedLeaveBlockList();

	/**
	 * Provides a way to access all of the time blocks for a given week
	 * 
	 * <p>
	 * Outer list is 0 indexed list representing days in a week.
	 * Inner List are all of the time blocks for that day.
	 *
	 * Ex.
	 *
	 * List<List<TimeBlock>> week0 = getWeekTimeBlocks(0);
	 * List<TimeBlock> day0 = week0.get(0);
	 * </p>
	 * 
	 * @param week to retrieve time blocks for
	 * @return the list of time blocks for a given week
	 */
	public List<? extends List<? extends TimeBlockContract>> getWeekTimeBlocks(int week);
	
	/**
	 * Provides a way to access all of the leave blocks for a given week
	 * 
	 * <p>
	 * Outer list is 0 indexed list representing days in a week.
	 * Inner List are all of the leave blocks for that day.
	 *
	 * Ex.
	 *
	 *  List<List<LeaveBlock>> week0 = getWeekLeaveBlocks(0);
	 * List<LeaveBlock> day0 = week0.get(0);
	 * </p>
	 * 
	 * @param week to retrieve leave blocks for
	 * @return the list of leave blocks for a given week
	 */
	public List<? extends List<? extends LeaveBlockContract>> getWeekLeaveBlocks(int week);

	/**
	 * TODO: Put a better comment
	 * The list of flsa weeks associated with the TkTimeBlockAggregate
	 * 
	 * <p>
	 * the list of flsa weeks of a TkTimeBlockAggregate
	 * <p>
	 * 
	 * @param The TimeZone to use when constructing this relative sorting
	 * @return the list of flsa weeks for TkTimeBlockAggregate
	 */
	public List<? extends FlsaWeekContract> getFlsaWeeks(DateTimeZone zone);
	
	/**
	 * TODO: Put a better comment
	 * The list of flsa weeks associated with the TkTimeBlockAggregate
	 * 
	 * <p>
	 * the list of flsa weeks of a TkTimeBlockAggregate
	 * <p>
	 * 
	 * @param zone 
	 * @param principalId
	 * @return the list of flsa weeks for TkTimeBlockAggregate
	 */
	public List<? extends List<? extends FlsaWeekContract>> getFlsaWeeks(DateTimeZone zone, String principalId);
	
	/**
	 * The number of aggregated weeks associated with the TkTimeBlockAggregate
	 * 
	 * <p>
	 * </p>
	 * 
	 * @return the number of weeks that this object represents.
	 */
	public int numberOfAggregatedWeeks();

	/**
	 * The list of day TimeBlock objects associated with the TkTimeBlockAggregate
	 * 
	 * <p>
	 * dayTimeBlockList of a TkTimeBlockAggregate
	 * </p>
	 * 
	 * @return dayTimeBlockList for TkTimeBlockAggregate
	 */
	public List<? extends List<? extends TimeBlockContract>> getDayTimeBlockList();
	
	/**
	 * The list of day LeaveBlock objects associated with the TkTimeBlockAggregate
	 * 
	 * <p>
	 * dayLeaveBlockList of a TkTimeBlockAggregate
	 * </p>
	 * 
	 * @return dayLeaveBlockList for TkTimeBlockAggregate
	 */
	public List<? extends List<? extends LeaveBlockContract>> getDayLeaveBlockList();

	/**
	 * The CalendarEntry object associated with the TkTimeBlockAggregate
	 * 
	 * <p>
	 * payCalendarEntry of a TkTimeBlockAggregate
	 * </p>
	 * 
	 * @return payCalendarEntry for TkTimeBlockAggregate
	 */
	public CalendarEntryContract getPayCalendarEntry();
	
	/**
	 * The Calendar object associated with the TkTimeBlockAggregate
	 * 
	 * <p>
	 * payCalendar of a TkTimeBlockAggregate
	 * </p>
	 * 
	 * @return payCalendar for TkTimeBlockAggregate
	 */
	public CalendarContract getPayCalendar();

}
