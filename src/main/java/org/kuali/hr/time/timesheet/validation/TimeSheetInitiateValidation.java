package org.kuali.hr.time.timesheet.validation;

import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimeSheetInitiate;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class TimeSheetInitiateValidation extends MaintenanceDocumentRuleBase {
    @Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = true;
        TimeSheetInitiate timeInit = (TimeSheetInitiate)this.getNewBo();
        Calendar pc = TkServiceLocator.getCalendarSerivce().getCalendarByGroup(timeInit.getPyCalendarGroup());
        if(pc == null) {
        	this.putFieldError("pyCalendarGroup", "timeSheetInit.calendar.Invalid");
        	valid = false;
        }
        
    	CalendarEntries pce = TkServiceLocator.getCalendarEntriesSerivce().getCalendarEntries(timeInit.getHrPyCalendarEntriesId());
    	if(pce == null) {
    		this.putFieldError("hrPyCalendarEntriesId", "timeSheetInit.payCalEntriesId.Invalid");
        	valid = false;
    	}
    	
    	if(valid) {
			this.createTimeSheetDocument(timeInit, pce);
		}
        return valid;
    }

    protected void createTimeSheetDocument(TimeSheetInitiate timeInit, CalendarEntries entries) {
    	try {
    		TimesheetDocument tsd = TkServiceLocator.getTimesheetService().openTimesheetDocument(timeInit.getPrincipalId(), entries);
    		if(tsd != null) {
    			timeInit.setDocumentId(tsd.getDocumentId());
    		}
		} catch (WorkflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
