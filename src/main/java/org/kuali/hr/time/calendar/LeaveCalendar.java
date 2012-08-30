package org.kuali.hr.time.calendar;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class LeaveCalendar extends CalendarParent {

    private Map<String, String> earnCodeList;
    
    public LeaveCalendar(String principalId, CalendarEntries calendarEntry) {
        super(calendarEntry);

        DateTime currDateTime = getBeginDateTime();
        DateTime endDateTime = getEndDateTime();
        DateTime firstDay = getBeginDateTime();

        // Fill in the days if the first day or end day is in the middle of the week
        if (currDateTime.getDayOfWeek() != DateTimeConstants.SUNDAY) {
            currDateTime = currDateTime.minusDays(currDateTime.getDayOfWeek());
            firstDay = currDateTime;
        }
        if (endDateTime.getDayOfWeek() != DateTimeConstants.SATURDAY) {
            endDateTime = endDateTime.plusDays(DateTimeConstants.SATURDAY - endDateTime.getDayOfWeek());
            if(endDateTime.getHourOfDay() == 0) {
            	 endDateTime = endDateTime.plusDays(1);
        	}
        }

        LeaveCalendarWeek leaveCalendarWeek = new LeaveCalendarWeek();

        Integer dayNumber = 0;
        while (currDateTime.isBefore(endDateTime)) {
            //Create weeks
            LeaveCalendarDay leaveCalendarDay = new LeaveCalendarDay();

            leaveCalendarDay.setGray(false);
            // if the last day time is the beginning of a day, make it gray
            if(currDateTime.equals(getEndDateTime()) && getEndDateTime().getHourOfDay() == 0) {
            	leaveCalendarDay.setGray(true);
            }
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
               dayNumber++;  // KPME-1664
            }
            leaveCalendarDay.setDayNumberString(currDateTime.dayOfMonth().getAsShortText());
            leaveCalendarDay.setDateString(currDateTime.toString(TkConstants.DT_BASIC_DATE_FORMAT));

            leaveCalendarWeek.getDays().add(leaveCalendarDay);
            // cut a week on Sat.
            if (currDateTime.getDayOfWeek() == 6 && currDateTime != firstDay) {
                getWeeks().add(leaveCalendarWeek);
                leaveCalendarWeek = new LeaveCalendarWeek();
            }

            // KPME-1664 increment dayNumber inside the else condition above to eliminate "grey" days.
            //dayNumber++;
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
