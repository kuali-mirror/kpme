package org.kuali.hr.time.timesheet.validation;

import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimeSheetInitiate;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class TimeSheetInitiateValidation extends MaintenanceDocumentRuleBase {
    @Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = true;
        TimeSheetInitiate timeInit = (TimeSheetInitiate)this.getNewBo();

       // need to check if the current date is between the begin and end dates of pay calendar entries
        PayCalendarEntries payCalEntries = (PayCalendarEntries) timeInit.getPayCalendarEntriesObj();
        if(payCalEntries == null) {
        	this.putFieldError("payCalendarEntriesId", "timeSheetInit.payCalEntriesId.Invalid");
        	valid = false;
        	return valid;
        }

		if(valid) {
			this.createTimeSheetDocument(timeInit, payCalEntries);
		}

        return valid;
    }

    protected void createTimeSheetDocument(TimeSheetInitiate timeInit, PayCalendarEntries entries) {
    	try {
			TkServiceLocator.getTimesheetService().openTimesheetDocument(timeInit.getPrincipalId(), entries);

		} catch (WorkflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
