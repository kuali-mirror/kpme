package org.kuali.hr.time.calendar;

import java.util.List;

import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

public class TimeHourDetailRenderer {
    private TimeHourDetail timeHourDetail;
    private boolean overtimeEarnCode;
    
    public TimeHourDetailRenderer(TimeHourDetail d) {
        this.timeHourDetail = d;
        List<String> ovtEarnCodes = TkServiceLocator.getEarnCodeService().getOvertimeEarnCodesStrs(TKContext.getCurrentTimesheetDoucment().getAsOfDate());
        setOvertimeEarnCode(ovtEarnCodes.contains(d.getEarnCode()));
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
		SystemScheduledTimeOff systemScheduledTimeOff = null;
		String holidayDesc = "";
		TimeBlock timeBlock = TkServiceLocator.getTimeBlockService().getTimeBlock(timeHourDetail.getTkTimeBlockId());
		
		if ( timeBlock != null ){
			if(timeBlock.getEarnCode().equals(TkConstants.HOLIDAY_EARN_CODE)) {
				String documentId = timeBlock.getDocumentId();
				TimesheetDocumentHeader docHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
				PrincipalHRAttributes principalCalendar = TkServiceLocator.getPrincipalHRAttributesService().getPrincipalCalendar(docHeader.getPrincipalId(), new java.sql.Date(timeBlock.getBeginDate().getTime()));				
				if ( principalCalendar.getLeavePlan() != null ){
						systemScheduledTimeOff = TkServiceLocator.getSysSchTimeOffService().getSystemScheduledTimeOffByDate(principalCalendar.getLeavePlan(), timeBlock.getBeginDate());
					if(systemScheduledTimeOff != null) {
						holidayDesc = systemScheduledTimeOff.getDescr();
					}
				}
			}
		}
			
		return holidayDesc;
	}

	public boolean isOvertimeEarnCode() {
		return overtimeEarnCode;
	}

	public void setOvertimeEarnCode(boolean overtimeEarnCode) {
		this.overtimeEarnCode = overtimeEarnCode;
	}

}
