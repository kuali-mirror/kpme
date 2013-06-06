package org.kuali.kpme.tklm.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.web.KPMEAction;
import org.kuali.kpme.tklm.time.detail.web.ActionFormUtils;

public abstract class CalendarFormAction extends KPMEAction {
	
    protected void setCalendarFields(HttpServletRequest request, CalendarForm calendarForm) {
		Set<String> calendarYears = new TreeSet<String>(Collections.reverseOrder());
		List<CalendarEntry> calendarEntries = getCalendarEntries(calendarForm.getCalendarEntry());
		
	    for (CalendarEntry calendarEntry : calendarEntries) {
	    	String calendarEntryYear = calendarEntry.getBeginPeriodFullDateTime().toString("yyyy");
	    	calendarYears.add(calendarEntryYear);
	    }
	    
        String currentCalendarYear = calendarForm.getCalendarEntry().getBeginPeriodFullDateTime().toString("yyyy");
        String selectedCalendarYear = StringUtils.isNotBlank(calendarForm.getSelectedCalendarYear()) ? calendarForm.getSelectedCalendarYear() : currentCalendarYear;
	    
	    calendarForm.setCalendarYears(new ArrayList<String>(calendarYears));
	    calendarForm.setPayPeriodsMap(ActionFormUtils.getPayPeriodsMap(ActionFormUtils.getAllCalendarEntriesForYear(calendarEntries, selectedCalendarYear), null));
	    
	    calendarForm.setSelectedCalendarYear(selectedCalendarYear);
	    calendarForm.setSelectedPayPeriod(calendarForm.getCalendarEntry().getHrCalendarEntryId());
	}
    
    protected abstract List<CalendarEntry> getCalendarEntries(CalendarEntry currentCalendarEntry);

}
