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
package org.kuali.hr.tklm.time.calendar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.kuali.hr.core.calendar.CalendarDay;
import org.kuali.hr.core.calendar.CalendarEntry;
import org.kuali.hr.core.calendar.CalendarParent;
import org.kuali.hr.core.calendar.CalendarWeek;
import org.kuali.hr.core.earncode.EarnCode;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.leave.block.LeaveBlockRenderer;
import org.kuali.hr.tklm.leave.util.LeaveBlockAggregate;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.timeblock.TimeBlock;
import org.kuali.hr.tklm.time.timeblock.TimeBlockRenderer;
import org.kuali.hr.tklm.time.timehourdetail.TimeHourDetailRenderer;
import org.kuali.hr.tklm.time.util.TkConstants;
import org.kuali.hr.tklm.time.util.TkTimeBlockAggregate;

public class TkCalendar extends CalendarParent {
    private CalendarEntry payCalEntry;
    private DateTime beginDateTime;
    private DateTime endDateTime;

    public TkCalendar() {}

    public TkCalendar(CalendarEntry calendarEntry) {
        super(calendarEntry);
    }

    public static TkCalendar getCalendar(TkTimeBlockAggregate tbAggregate, LeaveBlockAggregate lbAggregate) {
    	TkCalendar tc = new TkCalendar();
    	 
        if (tbAggregate != null && lbAggregate != null) {
        	if(tbAggregate.getDayTimeBlockList().size() != lbAggregate.getDayLeaveBlockList().size()){
        		throw new RuntimeException("TimeBlockAggregate and LeaveBlockAggregate should have the same size of Day Blocks List");
        	}
        		
        	List<CalendarWeek> weeks = new ArrayList<CalendarWeek>();
	        tc.setPayCalEntry(tbAggregate.getPayCalendarEntry());
	
	         int firstDay = 0;
	         if (tc.getBeginDateTime().getDayOfWeek() != 7) {
	             firstDay = 0 - tc.getBeginDateTime().getDayOfWeek();   // always render calendar weeks from Sundays
	         }
	         for (int i = 0; i < tbAggregate.numberOfAggregatedWeeks(); i++) {
	             TkCalendarWeek week = new TkCalendarWeek();
	             List<List<TimeBlock>> weekBlocks = tbAggregate.getWeekTimeBlocks(i);
	             List<List<LeaveBlock>> weekLeaveBlocks = lbAggregate.getWeekLeaveBlocks(i);
	             List<CalendarDay> days = new ArrayList<CalendarDay>(7);
	
	             for (int j = 0; j < weekBlocks.size(); j++) {
	                 List<TimeBlock> dayBlocks = weekBlocks.get(j);
	                 List<LeaveBlock> dayLeaveBlocks = weekLeaveBlocks.get(j);
	                 // Create the individual days.
	                 TkCalendarDay day = new TkCalendarDay();
	                 day.setTimeblocks(dayBlocks);
	                 day.setLeaveBlocks(dayLeaveBlocks);
	                 day.setDayNumberString(tc.getDayNumberString(i * 7 + j + firstDay));
	                 day.setDayNumberDelta(i * 7 + j + firstDay);
	                 day.setDateString(tc.getDateString(day.getDayNumberDelta()));
	                 assignDayLunchLabel(day);
	                 int dayIndex = i * 7 + j + firstDay;
	                 DateTime beginDateTemp = tc.getBeginDateTime().plusDays(dayIndex);
	                 day.setGray(false);
	                 if (beginDateTemp.isBefore(tc.getBeginDateTime().getMillis())
	                         || beginDateTemp.isAfter(tc.getEndDateTime().getMillis())) {
	                     day.setGray(true);
	                 }
	                 if (tc.getEndDateTime().getHourOfDay() == 0 && beginDateTemp.equals(tc.getEndDateTime())) {
	                     day.setGray(true);
	                 }
	                 days.add(day);
	             }
	             week.setDays(days);
	             weeks.add(week);
	         }
	         tc.setWeeks(weeks);
	     } else {
	         return null;
	     }

        return tc;
    }
    
    public static TkCalendar getCalendar(TkTimeBlockAggregate aggregate) {
        TkCalendar tc = new TkCalendar();

        if (aggregate != null) {
            List<CalendarWeek> weeks = new ArrayList<CalendarWeek>();
            tc.setPayCalEntry(aggregate.getPayCalendarEntry());

            int firstDay = 0;
            if (tc.getBeginDateTime().getDayOfWeek() != 7) {
                firstDay = 0 - tc.getBeginDateTime().getDayOfWeek();   // always render calendar weeks from Sundays
            }
            for (int i = 0; i < aggregate.numberOfAggregatedWeeks(); i++) {
                TkCalendarWeek week = new TkCalendarWeek();
                List<List<TimeBlock>> weekBlocks = aggregate.getWeekTimeBlocks(i);
                List<CalendarDay> days = new ArrayList<CalendarDay>(7);

                for (int j = 0; j < weekBlocks.size(); j++) {
                    List<TimeBlock> dayBlocks = weekBlocks.get(j);
                    // Create the individual days.
                    TkCalendarDay day = new TkCalendarDay();
                    day.setTimeblocks(dayBlocks);
                    day.setDayNumberString(tc.getDayNumberString(i * 7 + j + firstDay));
                    day.setDayNumberDelta(i * 7 + j + firstDay);
                    day.setDateString(tc.getDateString(day.getDayNumberDelta()));
                    assignDayLunchLabel(day);
                    int dayIndex = i * 7 + j + firstDay;
                    DateTime beginDateTemp = tc.getBeginDateTime().plusDays(dayIndex);
                    day.setGray(false);
                    if (beginDateTemp.isBefore(tc.getBeginDateTime().getMillis())
                            || beginDateTemp.isAfter(tc.getEndDateTime().getMillis())) {
                        day.setGray(true);
                    }
                    if (tc.getEndDateTime().getHourOfDay() == 0 && beginDateTemp.equals(tc.getEndDateTime())) {
                        day.setGray(true);
                    }
                    days.add(day);
                }
                week.setDays(days);
                weeks.add(week);
            }
            tc.setWeeks(weeks);
        } else {
            return null;
        }

        return tc;
    }

    public static void assignDayLunchLabel(TkCalendarDay day) {
        EarnCode ec = null;
        String label = "";
        String id = "";
        for (TimeBlockRenderer tbr : day.getBlockRenderers()) {
            for (TimeHourDetailRenderer thdr : tbr.getDetailRenderers()) {
                if (thdr.getTitle().equals(TkConstants.LUNCH_EARN_CODE)) {
                    ec = HrServiceLocator.getEarnCodeService().getEarnCode(thdr.getTitle(), tbr.getTimeBlock().getBeginDateTime().toLocalDate());
                    if (ec != null) {
                        label = ec.getDescription() + " : " + thdr.getHours() + " hours";
                        id = thdr.getTkTimeHourDetailId();
                    }
                }
            }
            tbr.setLunchLabel(label);
            tbr.setLunchLabelId(id);

            label = "";
        }
    }

    
    public void assignAssignmentStyle(Map<String, String> styleMap) {
        for (CalendarWeek aWeek : this.getWeeks()) {
            for (CalendarDay aDay : aWeek.getDays()) {
                for (TimeBlockRenderer tbr : ((TkCalendarDay)aDay).getBlockRenderers()) {
                    String assignmentKey = tbr.getTimeBlock().getAssignmentKey();
                    if (assignmentKey != null && styleMap.containsKey(assignmentKey)) {
                        tbr.setAssignmentClass(styleMap.get(assignmentKey));
                    } else {
                        tbr.setAssignmentClass("");
                    }
                }
                for (LeaveBlockRenderer lbr : ((TkCalendarDay)aDay).getLeaveBlockRenderers()) {
                    String assignmentKey = lbr.getLeaveBlock().getAssignmentKey();
                    if (assignmentKey != null && styleMap.containsKey(assignmentKey)) {
                        lbr.setAssignmentClass(styleMap.get(assignmentKey));
                    } else {
                        lbr.setAssignmentClass("");
                    }
                } 
                
            }
        }
    }

    public CalendarEntry getPayCalEntry() {
        return payCalEntry;
    }

    public void setPayCalEntry(CalendarEntry payCalEntry) {
        this.payCalEntry = payCalEntry;
        // Relative time, with time zone added.
        this.beginDateTime = payCalEntry.getBeginPeriodLocalDateTime().toDateTime(TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
        this.endDateTime = payCalEntry.getEndPeriodLocalDateTime().toDateTime(TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
    }

    public DateTime getBeginDateTime() {
        return beginDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Provides the calendar title / heading. If the Pay Calendar entry spans
     * multiple months, you get Abbreviated Month name + year of both the
     * beginning month and the ending month.
     *
     * @return String for calendar title use.
     */
    public String getCalendarTitle() {
        StringBuilder sb = new StringBuilder();

        if (getBeginDateTime().getMonthOfYear() == getEndDateTime().getMonthOfYear() ||
                (getBeginDateTime().getMonthOfYear() != getEndDateTime().getMonthOfYear()
                        && getEndDateTime().getDayOfMonth() == 1 && getEndDateTime().getSecondOfDay() == 0)) {
            sb.append(getBeginDateTime().toString("MMMM y"));
        } else {
            sb.append(getBeginDateTime().toString("MMM y"));
            sb.append(" - ");
            sb.append(getEndDateTime().toString("MMM y"));
        }

        return sb.toString();
    }

    /**
     * Assumption of 7 "days" per week, or 7 "blocks" per row.
     *
     * @return A list of string titles for each row block (day)
     */
    public List<String> getCalendarDayHeadings() {
        List<String> dayStrings = new ArrayList<String>(7);
        // always render from Sunday
        int firstDay = 0 - getBeginDateTime().getDayOfWeek();
        int lastDay = firstDay + 7;

        if (getBeginDateTime().getMinuteOfDay() == 0) {
            // "Standard" days.
            for (int i = firstDay; i < lastDay; i++) {
                DateTime currDay = getBeginDateTime().plusDays(i);
                dayStrings.add(currDay.toString("E"));
            }
        } else {
            // Day Split Strings

            for (int i = firstDay; i < lastDay; i++) {
                StringBuilder builder = new StringBuilder("");
                DateTime currStart = getBeginDateTime().plusDays(i);
                DateTime currEnd = getBeginDateTime().plusDays(i);

                builder.append(currStart.toString("E HH:mm"));
                builder.append(" - ");
                builder.append(currEnd.toString("E HH:mm"));

                dayStrings.add(builder.toString());
            }
        }

        return dayStrings;
    }

    public String getCalenadrYear() {
        return getBeginDateTime().toString("yyyy");
    }

    public String getCalendarMonth() {
        return getBeginDateTime().toString("M");
    }

    private String getDayNumberString(int dayDelta) {
        StringBuilder b = new StringBuilder();

        if (getBeginDateTime().getMinuteOfDay() == 0) {
            DateTime currStart = getBeginDateTime().plusDays(dayDelta);
            b.append(currStart.toString("d"));
        } else {
            DateTime currStart = getBeginDateTime().plusDays(dayDelta);
            DateTime currEnd = getBeginDateTime().plusDays(dayDelta);

            b.append(currStart.toString("d HH:mm"));
            b.append(" - ");
            //TODO: should this be currEnd???
            b.append(currStart.toString("d HH:mm"));
        }

        return b.toString();
    }

    private String getDateString(int dayDelta) {
        return getBeginDateTime().plusDays(dayDelta).toString("M/d/yyyy");
    }

}
