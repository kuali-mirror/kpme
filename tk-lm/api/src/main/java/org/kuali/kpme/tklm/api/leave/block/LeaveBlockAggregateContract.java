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
package org.kuali.kpme.tklm.api.leave.block;

import java.util.List;

import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;
import org.kuali.kpme.tklm.api.leave.calendar.LeaveCalendarContract;


/**
 * <p>LeaveBlockAggregateContract interface</p>
 *
 */
public interface LeaveBlockAggregateContract {

	/**
	 * The dayLeaveBlockListist that has been sorted by timestamp for the LeaveBlockAggregateContract
	 * 
	 * <p>
	 * a sorted dayLeaveBlockList of a LeaveBlockAggregate
	 * <p>
	 * 
	 * @return a sorted dayLeaveBlockList for LeaveBlockAggregate
	 */	
	public List<? extends LeaveBlockContract> getFlattenedLeaveBlockList();

	/**
	 * Provides a way to access all of the leave blocks for a given week.
	 * 
	 * <p>
	 * Outer list is 0 indexed list representing days in a week.
	 * Inner List are all of the time blocks for that day.
	 *
	 * Ex.
	 *
	 * List<List<LeaveBlock>> week0 = getWeekLeaveBlocks(0);
	 * List<LeaveBlock> day0 = week0.get(0);
	 * <p>
	 * 
	 * @param week (integer) to retrieve the leave blocks for
	 * @return the total number of weeks for LeaveBlockAggregate
	 */	
	public List<? extends List<? extends LeaveBlockContract>> getWeekLeaveBlocks(int week);

	/**
	 * The total number of weeks that the LeaveBlockAggregate represents
	 * 
	 * <p>
	 * the total number of weeks of a LeaveBlockAggregate
	 * <p>
	 * 
	 * @return the total number of weeks
	 */	
	public int numberOfAggregatedWeeks();

	/**
	 * The dayLeaveBlockList associated with the LeaveBlockAggregate
	 * 
	 * <p>
	 * dayLeaveBlockList of a LeaveBlockAggregate
	 * <p>
	 * 
	 * @return dayLeaveBlockList for LeaveBlockAggregate
	 */	
	public List<? extends List <? extends LeaveBlockContract>> getDayLeaveBlockList();

	/**
	 * The CalendarEntry object associated with the LeaveBlockAggregate
	 * 
	 * <p>
	 * leaveCalendarEntry of a LeaveBlockAggregate
	 * <p>
	 * 
	 * @return leaveCalendarEntry for LeaveBlockAggregate
	 */	
	public CalendarEntryContract getleaveCalendarEntry(); 

	/**
	 * The LeaveCalendar object associated with the LeaveBlockAggregate
	 * 
	 * <p>
	 * leaveCalendar of a LeaveBlockAggregate
	 * <p>
	 * 
	 * @return leaveCalendar for LeaveBlockAggregate
	 */	
	public LeaveCalendarContract getLeaveCalendar();
	
}
