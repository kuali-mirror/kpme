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
package org.kuali.kpme.tklm.time.missedpunch;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.kpme.tklm.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.TransactionalDocumentRuleBase;
import org.kuali.rice.krad.util.GlobalVariables;

public class MissedPunchDocumentRule extends TransactionalDocumentRuleBase {
	
	@Override
	protected boolean processCustomSaveDocumentBusinessRules(Document document) {
        boolean valid = true;
        
        MissedPunchDocument missedPunchDocument = (MissedPunchDocument) document;
        MissedPunch missedPunch = missedPunchDocument.getMissedPunch();
        DocumentStatus documentStatus = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(document.getDocumentNumber());
        
        if (DocumentStatus.INITIATED.equals(documentStatus) || DocumentStatus.SAVED.equals(documentStatus)) {
            valid &= getDictionaryValidationService().isBusinessObjectValid(missedPunch);

	        if (valid) {
		        valid &= validateTimesheet(missedPunch);
	        	valid &= validateClockAction(missedPunch);
	        	valid &= validateClockTime(missedPunch);
	        }
        }
	    
        return valid;
	}
	
	/**
	 * Validates whether the Timesheet associated with the Missed Punch is not ENROUTE or FINAL.
	 * 
	 * @param missedPunch The Missed Punch to check
	 * 
	 * @return true if the Timesheet associated with the Missed Punch is not ENROUTE or FINAL, false otherwise
	 */
    protected boolean validateTimesheet(MissedPunch missedPunch) {
    	boolean valid = true;
    	
    	if (missedPunch.getTimesheetDocumentId() != null) {
	    	DocumentStatus documentStatus = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(missedPunch.getTimesheetDocumentId());
	    	
	    	if (DocumentStatus.ENROUTE.equals(documentStatus) || DocumentStatus.FINAL.equals(documentStatus)) {
	    		GlobalVariables.getMessageMap().putError("document.timesheetDocumentId", "clock.mp.invalid.timesheet");
	    		valid = false;
	    	}
    	}
    	
    	return valid;
    }

    /**
     * Validates whether the Clock Action associated with the Missed Punch correctly transitions from the applicable last Clock Logs.
     *
     * @param missedPunch The Missed Punch to check
     *
     * @return true if the Clock Action associated with the Missed Punch correctly transitions from the applicable last Clock Logs, false otherwise
     */
	boolean validateClockAction(MissedPunch missedPunch) {
        boolean valid = true;
        
        if (TkConstants.CLOCK_OUT.equals(missedPunch.getClockAction()) || TkConstants.LUNCH_OUT.equals(missedPunch.getClockAction())) {
            ClockLog lastClockIn = TkServiceLocator.getClockLogService().getLastClockLog(missedPunch.getPrincipalId(),TkConstants.CLOCK_IN);
            ClockLog lastLunchIn = TkServiceLocator.getClockLogService().getLastClockLog(missedPunch.getPrincipalId(),TkConstants.LUNCH_IN);
            
            if (lastClockIn != null) {
                MissedPunch lastMissedPunch = TkServiceLocator.getMissedPunchService().getMissedPunchByClockLogId(lastClockIn.getTkClockLogId());
                if (lastMissedPunch != null) {
                    GlobalVariables.getMessageMap().putError("document.clockAction", "clock.mp.onlyOne.action");
                    valid = false;
                }
            } else if (lastLunchIn != null) {
            	MissedPunch lastMissedPunch = TkServiceLocator.getMissedPunchService().getMissedPunchByClockLogId(lastLunchIn.getTkClockLogId());
                if (lastMissedPunch != null) {
                    GlobalVariables.getMessageMap().putError("document.clockAction", "clock.mp.onlyOne.action");
                    valid = false;
                }
            }
        }
        
        return valid;
    }

    /**
     * Validates whether the MissedPunch has a valid time relative to the last Clock Log.
     *
     * @param missedPunch The Missed Punch to check
     *
     * @return true if the MissedPunch has a valid time relative to the last Clock Log, false otherwise
     */
    protected boolean validateClockTime(MissedPunch missedPunch) {
        boolean valid = true;

    	ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(missedPunch.getPrincipalId());
        if (lastClockLog != null && missedPunch.getActionDateTime() != null) {
	        DateTime clockLogDateTime = lastClockLog.getClockDateTime();
	        DateTime boundaryMax = clockLogDateTime.plusDays(1);
	        long offset = HrServiceLocator.getTimezoneService().getTimezoneOffsetFromServerTime(HrServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
	        long dateTimeLocal = missedPunch.getActionFullDateTime().getMillis() - offset;
	
	        //this will be in system's timezone, but offset with user's timezone
	        DateTime actionDateTime = new DateTime(dateTimeLocal);
	
	        if (actionDateTime.toLocalDate().isAfter(LocalDate.now())) {
	        	GlobalVariables.getMessageMap().putError("document.actionDate", "clock.mp.future.date");
	        	valid = false;
	        }
	
	        if (actionDateTime.toLocalDate().isEqual(LocalDate.now()) && actionDateTime.isAfterNow()) {
	        	GlobalVariables.getMessageMap().putError("document.actionTime", "clock.mp.future.time");
	        	valid = false;
	        }
	        
	        if ((!StringUtils.equals(lastClockLog.getClockAction(), TkConstants.CLOCK_OUT) && actionDateTime.isAfter(boundaryMax)) 
	        		|| actionDateTime.isBefore(clockLogDateTime)) {
	        	GlobalVariables.getMessageMap().putError("document.actionTime", "clock.mp.invalid.datetime");
	            valid = false;
	        }
        }

        return valid;
    }

}