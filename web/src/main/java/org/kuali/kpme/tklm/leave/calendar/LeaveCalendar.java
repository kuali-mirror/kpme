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
package org.kuali.kpme.tklm.leave.calendar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.calendar.CalendarParent;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class LeaveCalendar extends CalendarParent {

    private Map<String, String> earnCodeList;
    
    public LeaveCalendar(String principalId, CalendarEntry calendarEntry, List<String> assignmentKeys) {
        super(calendarEntry);

        DateTime currentDisplayDateTime = getBeginDateTime();
        DateTime endDisplayDateTime = getEndDateTime();

        // Fill in the days if the first day or end day is in the middle of the week
        if (currentDisplayDateTime.getDayOfWeek() != DateTimeConstants.SUNDAY) {
            currentDisplayDateTime = currentDisplayDateTime.minusDays(currentDisplayDateTime.getDayOfWeek());
        }
        if (endDisplayDateTime.getDayOfWeek() != DateTimeConstants.SATURDAY) {
            endDisplayDateTime = endDisplayDateTime.plusDays(DateTimeConstants.SATURDAY - endDisplayDateTime.getDayOfWeek());
        }

        LeaveCalendarWeek leaveCalendarWeek = new LeaveCalendarWeek();
        Integer dayNumber = 0;
        List<LeaveBlock> blocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, calendarEntry.getBeginPeriodFullDateTime().toLocalDate(), calendarEntry.getEndPeriodFullDateTime().toLocalDate());
        Map<String, List<LeaveBlock>> leaveBlockMap = new HashMap<String, List<LeaveBlock>>();
        for (LeaveBlock lb : blocks) {
            String key = new LocalDate(lb.getLeaveDate()).toString();
            if (leaveBlockMap.containsKey(key)) {
                leaveBlockMap.get(key).add(lb);
            } else {
                leaveBlockMap.put(key, createNewLeaveBlockList(lb));
            }
        }

        while (currentDisplayDateTime.isBefore(endDisplayDateTime) || currentDisplayDateTime.isEqual(endDisplayDateTime)) {
            LeaveCalendarDay leaveCalendarDay = new LeaveCalendarDay();
            
            // If the day is not within the current pay period, mark them as read only (gray)
            if (currentDisplayDateTime.isBefore(getBeginDateTime()) 
            		|| currentDisplayDateTime.isEqual(getEndDateTime()) 
            		|| currentDisplayDateTime.isAfter(getEndDateTime())) {
                leaveCalendarDay.setGray(true);
            } else {
                // This is for the div id of the days on the calendar.
                // It creates a day id like day_11/01/2011 which will make day parsing easier in the javascript.
//                leaveCalendarDay.setDayNumberDelta(currDateTime.toString(HrConstants.DT_BASIC_DATE_FORMAT));
//                leaveCalendarDay.setDayNumberDelta(currDateTime.getDayOfMonth());
                leaveCalendarDay.setDayNumberDelta(dayNumber);
    
               LocalDate leaveDate = currentDisplayDateTime.toLocalDate();
               List<LeaveBlock> lbs = leaveBlockMap.get(currentDisplayDateTime.toLocalDate().toString());
               if (lbs == null) {
                   lbs = Collections.emptyList();
               }
               // use given assignmentKeys to control leave blocks displayed on the calendar
               if(CollectionUtils.isNotEmpty(lbs) && CollectionUtils.isNotEmpty(assignmentKeys)) {
            	   List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().filterLeaveBlocksForLeaveCalendar(lbs, assignmentKeys);
            	   leaveCalendarDay.setLeaveBlocks(leaveBlocks);
               } else {
            	   leaveCalendarDay.setLeaveBlocks(lbs);
               }
               
               if (HrServiceLocator.getHRPermissionService().canViewLeaveTabsWithNEStatus()) {
	               TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeaderForDate(principalId, leaveDate.toDateTimeAtStartOfDay());
	               if (tdh != null) {
	            	   if (DateUtils.isSameDay(leaveDate.toDate(), tdh.getEndDate()) || leaveDate.isAfter(LocalDate.fromDateFields(tdh.getEndDate()))) {
	            		   leaveCalendarDay.setDayEditable(true);
	            	   }
	               } else {
	            	   leaveCalendarDay.setDayEditable(true);
	               }
               } else {
                   leaveCalendarDay.setDayEditable(true);
               }
               
               dayNumber++;
            }
            leaveCalendarDay.setDayNumberString(currentDisplayDateTime.dayOfMonth().getAsShortText());
            leaveCalendarDay.setDateString(currentDisplayDateTime.toString(HrConstants.DT_BASIC_DATE_FORMAT));

            leaveCalendarWeek.getDays().add(leaveCalendarDay);
            
            if (leaveCalendarWeek.getDays().size() == DateTimeConstants.DAYS_PER_WEEK) {
                getWeeks().add(leaveCalendarWeek);
                leaveCalendarWeek = new LeaveCalendarWeek();
            }

            currentDisplayDateTime = currentDisplayDateTime.plusDays(1);
        }

        if (!leaveCalendarWeek.getDays().isEmpty()) {
            getWeeks().add(leaveCalendarWeek);
        }

        boolean isPlanningCal = LmServiceLocator.getLeaveCalendarService().isLeavePlanningCalendar(principalId, calendarEntry.getBeginPeriodFullDateTime().toLocalDate(), calendarEntry.getEndPeriodFullDateTime().toLocalDate());
        Map<String, String> earnCodes = HrServiceLocator.getEarnCodeService().getEarnCodesForDisplay(principalId, isPlanningCal);
        setEarnCodeList(earnCodes);
    }

    private List<LeaveBlock> createNewLeaveBlockList(LeaveBlock lb){
        List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
        leaveBlocks.add(lb);
        return leaveBlocks;
    }
    
    private Multimap<Date, LeaveBlock> leaveBlockAggregator(String documentId) {
        List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksForDocumentId(documentId);
        Multimap<Date, LeaveBlock> leaveBlockAggregrate = HashMultimap.create();
        for (LeaveBlock leaveBlock : leaveBlocks) {
            leaveBlockAggregrate.put(leaveBlock.getLeaveDate(), leaveBlock);
        }

        return leaveBlockAggregrate;
    }

   	public Map<String, String> getEarnCodeList() {
		return earnCodeList;
	}

	public void setEarnCodeList(Map<String, String> earnCodeList) {
		this.earnCodeList = earnCodeList;
	}
}
