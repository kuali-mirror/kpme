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
package org.kuali.hr.time.timesheet.validation;

import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimeSheetInitiate;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class TimeSheetInitiateValidation extends MaintenanceDocumentRuleBase {
    @Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = true;
        TimeSheetInitiate timeInit = (TimeSheetInitiate)this.getNewBo();
        Calendar pc = TkServiceLocator.getCalendarService().getCalendarByGroup(timeInit.getPyCalendarGroup());
        if(pc == null) {
        	this.putFieldError("pyCalendarGroup", "timeSheetInit.payCalendar.Invalid");
        	valid = false;
        } else {
        	if(pc.getCalendarTypes() != null && !pc.getCalendarTypes().equalsIgnoreCase(TkConstants.CALENDAR_TYPE_PAY)) {
        		this.putFieldError("pyCalendarGroup", "timeSheetInit.payCalendar.Invalid");
            	valid = false;
        	}
        }
        
    	CalendarEntries pce = TkServiceLocator.getCalendarEntriesService().getCalendarEntries(timeInit.getHrPyCalendarEntriesId());
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
