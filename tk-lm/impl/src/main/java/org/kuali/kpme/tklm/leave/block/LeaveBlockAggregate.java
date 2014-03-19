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
package org.kuali.kpme.tklm.leave.block;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlockAggregateContract;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlockContract;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaveBlockAggregate implements LeaveBlockAggregateContract {
	public List<List<LeaveBlock>> dayLeaveBlockList = new ArrayList<List<LeaveBlock>>();
	private CalendarEntry leaveCalendarEntry;
	private LeaveCalendar leaveCalendar;

    
    /**
     * Provides the option to refer to the time zone adjusted time for the current
     * user.
     * @param leaveBlocks
     * @param leaveCalendarEntry
     * @param leaveCalendar
     */
    public LeaveBlockAggregate(List<LeaveBlock> leaveBlocks, CalendarEntry leaveCalendarEntry, LeaveCalendar leaveCalendar) {
		this.leaveCalendarEntry = leaveCalendarEntry;
		this.leaveCalendar = leaveCalendar;
		List<Interval> dayIntervals = TKUtils.getDaySpanForCalendarEntry(leaveCalendarEntry);
		for(Interval dayInt : dayIntervals){
			List<LeaveBlock> dayLeaveBlocks = new ArrayList<LeaveBlock>();
			for(LeaveBlock leaveBlock : leaveBlocks){
				LocalDate localDate = leaveBlock.getLeaveLocalDate();
                LocalDate dayIntBegin = dayInt.getStart().toLocalDate();
				if(localDate.equals(dayIntBegin)){
					dayLeaveBlocks.add(leaveBlock);
				}
			}
			dayLeaveBlockList.add(dayLeaveBlocks);
		} 
	}
    
    public LeaveBlockAggregate(List<LeaveBlock> leaveBlocks, CalendarEntry leaveCalendarEntry) {
		this.leaveCalendarEntry = leaveCalendarEntry;
		List<Interval> dayIntervals = TKUtils.getDaySpanForCalendarEntry(leaveCalendarEntry);
		for(Interval dayInt : dayIntervals){
			List<LeaveBlock> dayLeaveBlocks = new ArrayList<LeaveBlock>();
			for(LeaveBlock leaveBlock : leaveBlocks){
                LocalDate localDate = leaveBlock.getLeaveLocalDate();
                LocalDate dayIntBegin = dayInt.getStart().toLocalDate();
                if(localDate.equals(dayIntBegin)){
                    dayLeaveBlocks.add(leaveBlock);
                }
			}
			dayLeaveBlockList.add(dayLeaveBlocks);
		} 
	}
    
    /**
     *  build leaveBlockAggregate with given leaveBlocks, calendarEntry and dayIntervals
     *  dayIntervals with full week span is for Time Calendar
     * @param leaveBlocks
     * @param leaveCalendarEntry
     * @param dayIntervals
     */ 
    public LeaveBlockAggregate(List<LeaveBlock> leaveBlocks, CalendarEntry leaveCalendarEntry, List<Interval> dayIntervals) {
    	this.leaveCalendarEntry = leaveCalendarEntry;
		for(Interval dayInt : dayIntervals){
			List<LeaveBlock> dayLeaveBlocks = new ArrayList<LeaveBlock>();
			DateTime localTime = dayInt.getStart().toLocalDateTime().toDateTime();
			String intervalStartDateString = localTime.toLocalDate().toString();
			
			for(LeaveBlock leaveBlock : leaveBlocks){
				// if the interval end time is 0, ie the beginning of a day, use the date string of the interval start time
				// to check if the leave block should go into this interval. Leave blocks only have leaveDate, there's no leave time
				if(dayInt.getEnd().getHourOfDay() == 0) {
					String lbDateString = leaveBlock.getLeaveLocalDate().toString();
					if(intervalStartDateString.equals(lbDateString)) {
						dayLeaveBlocks.add(leaveBlock);
					}
				} else {
                    LocalDate localDate = leaveBlock.getLeaveLocalDate();
                    LocalDate dayIntBegin = dayInt.getStart().toLocalDate();
                    if(localDate.equals(dayIntBegin)){
                        dayLeaveBlocks.add(leaveBlock);
                    }
				}
			}
			dayLeaveBlockList.add(dayLeaveBlocks);
		} 
    }   
    
	public List<LeaveBlock> getFlattenedLeaveBlockList(){
		List<LeaveBlock> lstLeaveBlocks = new ArrayList<LeaveBlock>();
		for(List<LeaveBlock> leaveBlocks : dayLeaveBlockList){
			lstLeaveBlocks.addAll(leaveBlocks);
		}

		Collections.sort(lstLeaveBlocks, new Comparator<LeaveBlockContract>() { // Sort the Leave Blocks
			public int compare(LeaveBlockContract tb1, LeaveBlockContract tb2) {
				if (tb1 != null && tb2 != null) {
                    if (tb1.getCreateTime() != null && tb2.getCreateTime() != null) {
					    return tb1.getCreateTime().compareTo(tb2.getCreateTime());
                    }
                }
				return 0;
			}
		});

		return lstLeaveBlocks;
	}

	/**
	 * Provides a way to access all of the leave blocks for a given week.
	 *
	 * Outer list is 0 indexed list representing days in a week.
	 * Inner List are all of the time blocks for that day.
	 *
	 * Ex.
	 *
	 * List<List<LeaveBlock>> week0 = getWeekLeaveBlocks(0);
	 * List<LeaveBlock> day0 = week0.get(0);
	 *
	 * @param week
	 * @return
	 */
	public List<List<LeaveBlock>> getWeekLeaveBlocks(int week){
		int startIndex = week*7;
		int endIndex = (week*7)+7;
		endIndex = endIndex > dayLeaveBlockList.size() ? dayLeaveBlockList.size() : endIndex;

        // Need to sort each day by clock time.
        List<List<LeaveBlock>> wList = dayLeaveBlockList.subList(startIndex, endIndex);
        for (List<LeaveBlock> dList : wList) {
            Collections.sort(dList, new Comparator<LeaveBlock>() { // Sort the Leave Blocks
                public int compare(LeaveBlock tb1, LeaveBlock tb2) {
                    if (tb1 != null && tb2 != null)
                        return tb1.getCreateTime().compareTo(tb2.getCreateTime());
                    return 0;
                }
            });
        }

		return wList;
	}

	/**
	 * @return the total number of weeks that this object represents.
	 */
	public int numberOfAggregatedWeeks() {
		int weeks = 0;

		if (this.dayLeaveBlockList.size() > 0) {
			weeks = this.dayLeaveBlockList.size() / 7;
			if (this.dayLeaveBlockList.size() % 7 > 0)
				weeks++;
		}

		return weeks;
	}

	/**
	 * The assignment class associated with the LeaveBlockRenderer
	 * 
	 * <p>
	 * dayLeaveBlockList of an LeaveBlockRenderer
	 * <p>
	 * 
	 * @return dayLeaveBlockList for LeaveBlockRenderer
	 */	
	public List<List<LeaveBlock>> getDayLeaveBlockList() {
		return dayLeaveBlockList;
	}

	public CalendarEntry getleaveCalendarEntry() {
		return leaveCalendarEntry;
	}

	public void setleaveCalendarEntry(CalendarEntry leaveCalendarEntry) {
		this.leaveCalendarEntry = leaveCalendarEntry;
	}

	public LeaveCalendar getLeaveCalendar() {
		return leaveCalendar;
	}

	public void setLeaveCalendar(LeaveCalendar leaveCalendar) {
		this.leaveCalendar = leaveCalendar;
	}

}
