package org.kuali.hr.lm.util;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.calendar.LeaveCalendar;
import org.kuali.hr.time.flsa.FlsaDay;
import org.kuali.hr.time.flsa.FlsaWeek;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.lm.leaveblock.LeaveBlock;

public class LeaveBlockAggregate {
	public List<List<LeaveBlock>> dayLeaveBlockList = new ArrayList<List<LeaveBlock>>();
	private CalendarEntries leaveCalendarEntry;
	private LeaveCalendar leaveCalendar;

    
    /**
     * Provides the option to refer to the time zone adjusted time for the current
     * user.
     * @param LeaveBlocks
     * @param leaveCalendarEntry
     * @param leaveCalendar
     */
    public LeaveBlockAggregate(List<LeaveBlock> leaveBlocks, CalendarEntries leaveCalendarEntry, LeaveCalendar leaveCalendar) {
		this.leaveCalendarEntry = leaveCalendarEntry;
		this.leaveCalendar = leaveCalendar;
		List<Interval> dayIntervals = TKUtils.getDaySpanForCalendarEntry(leaveCalendarEntry);
		for(Interval dayInt : dayIntervals){
			List<LeaveBlock> dayLeaveBlocks = new ArrayList<LeaveBlock>();
			for(LeaveBlock leaveBlock : leaveBlocks){
				DateTime dateTime = new DateTime(leaveBlock.getTimestamp());
				if(dayInt.contains(dateTime)){
					dayLeaveBlocks.add(leaveBlock);
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

		Collections.sort(lstLeaveBlocks, new Comparator<LeaveBlock>() { // Sort the Leave Blocks
			public int compare(LeaveBlock tb1, LeaveBlock tb2) {
				if (tb1 != null && tb2 != null)
					return tb1.getTimestamp().compareTo(tb2.getTimestamp());
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
                        return tb1.getTimestamp().compareTo(tb2.getTimestamp());
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

	public List<List<LeaveBlock>> getDayLeaveBlockList() {
		return dayLeaveBlockList;
	}

	public CalendarEntries getleaveCalendarEntry() {
		return leaveCalendarEntry;
	}

	public void setleaveCalendarEntry(CalendarEntries leaveCalendarEntry) {
		this.leaveCalendarEntry = leaveCalendarEntry;
	}

	public LeaveCalendar getLeaveCalendar() {
		return leaveCalendar;
	}

	public void setLeaveCalendar(LeaveCalendar leaveCalendar) {
		this.leaveCalendar = leaveCalendar;
	}

}
