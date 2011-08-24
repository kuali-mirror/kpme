package org.kuali.hr.time.calendar;

import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.kuali.hr.time.holidaycalendar.HolidayCalendarDateEntry;
import org.kuali.hr.time.principal.calendar.PrincipalCalendar;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

public class TimeHourDetailRenderer {
    private TimeHourDetail timeHourDetail;
    
    public TimeHourDetailRenderer(TimeHourDetail d) {
        this.timeHourDetail = d;
    }

    public TimeHourDetail getTimeHourDetail() {
        return timeHourDetail;
    }

    public String getTitle() {
        return timeHourDetail.getEarnCode();
    }

    public String getHours() {
        return timeHourDetail.getHours().toString();
    }

    public String getAmount() {
        return timeHourDetail.getAmount().toString();
    }
    
    public String getHolidayName() {
		HolidayCalendarDateEntry holidayCalendarDateEntry = null;
		String holidayDesc = "";
		TimeBlock timeBlock = TkServiceLocator.getTimeBlockService().getTimeBlock(timeHourDetail.getTkTimeBlockId());
		
		if ( timeBlock != null ){
			if(timeBlock.getEarnCode().equals(TkConstants.HOLIDAY_EARN_CODE)) {
				String documentId = timeBlock.getDocumentId();
				TimesheetDocumentHeader docHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
				PrincipalCalendar principalCalendar = TkServiceLocator.getPrincipalCalendarService().getPrincipalCalendar(docHeader.getPrincipalId(), new java.sql.Date(timeBlock.getBeginDate().getTime()));
				
				if ( principalCalendar.getHolidayCalendarGroup() != null ){
					HolidayCalendar holidayCalendar = TkServiceLocator.getHolidayCalendarService().getHolidayCalendarByGroup(principalCalendar.getHolidayCalendarGroup());
					
					if ( holidayCalendar != null ){
						holidayCalendarDateEntry = TkServiceLocator.getHolidayCalendarService().getHolidayCalendarDateEntryByDate(holidayCalendar.getHolidayCalendarId(), timeBlock.getBeginDate());
						
						if(holidayCalendarDateEntry != null) {
							holidayDesc = holidayCalendarDateEntry.getHolidayDescr();
						}
					}
				}
			}
		}
			
		return holidayDesc;
	}

}
