package org.kuali.hr.time.calendar;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.joda.time.DateTime;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LeaveCalendar extends CalendarParent {

    private Map<String, String> earnCodeList;
    
    public LeaveCalendar(String principalId, CalendarEntries calendarEntry) {
        super(calendarEntry);

        DateTime currDateTime = getBeginDateTime();
        DateTime endDateTime = getEndDateTime();
        DateTime firstDay = getBeginDateTime();

        // Fill in the days if the first day or end day is in the middle of the week
        // Monday = 1; Sunday = 7
        if (currDateTime.getDayOfWeek() > 0 && currDateTime.getDayOfWeek() != 7) {
            currDateTime = currDateTime.minusDays(currDateTime.getDayOfWeek());
            firstDay = currDateTime;
        }
        if (endDateTime.getDayOfWeek() < 7) {
            endDateTime = endDateTime.plusDays(7 - endDateTime.getDayOfWeek());
        }

        LeaveCalendarWeek leaveCalendarWeek = new LeaveCalendarWeek();

        Integer dayNumber = 0;
        while (currDateTime.isBefore(endDateTime)) {
            //Create weeks
            LeaveCalendarDay leaveCalendarDay = new LeaveCalendarDay();

            // If the day is not within the current pay period, mark them as read only (setGray)
            if (currDateTime.isBefore(getBeginDateTime()) || currDateTime.isAfter(getEndDateTime())) {
                leaveCalendarDay.setGray(true);
            } else {
                // This is for the div id of the days on the calendar.
                // It creates a day id like day_11/01/2011 which will make day parsing easier in the javascript.
//                leaveCalendarDay.setDayNumberDelta(currDateTime.toString(TkConstants.DT_BASIC_DATE_FORMAT));
//                leaveCalendarDay.setDayNumberDelta(currDateTime.getDayOfMonth());
                leaveCalendarDay.setDayNumberDelta(dayNumber);
    
               java.util.Date leaveDate = TKUtils.getTimelessDate(currDateTime.toDate());
               List<LeaveBlock> lbs = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForDate(principalId, leaveDate);
               leaveCalendarDay.setLeaveBlocks(lbs); 
            }
            leaveCalendarDay.setDayNumberString(currDateTime.dayOfMonth().getAsShortText());
            leaveCalendarDay.setDateString(currDateTime.toString(TkConstants.DT_BASIC_DATE_FORMAT));

            leaveCalendarWeek.getDays().add(leaveCalendarDay);
            // cut a week on Sat.
            if (currDateTime.getDayOfWeek() == 6 && currDateTime != firstDay) {
                getWeeks().add(leaveCalendarWeek);
                leaveCalendarWeek = new LeaveCalendarWeek();
            }

            dayNumber++;
            currDateTime = currDateTime.plusDays(1);
        }

        if (!leaveCalendarWeek.getDays().isEmpty()) {
            getWeeks().add(leaveCalendarWeek);
        }

        Map<String, String> earnCodes = TkServiceLocator.getEarnCodeService().getEarnCodesForDisplay(TKContext.getTargetPrincipalId());
        setEarnCodeList(earnCodes);
    } 
    
    private Multimap<Date, LeaveBlock> leaveBlockAggregator(String documentId) {
        List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForDocumentId(documentId);
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
