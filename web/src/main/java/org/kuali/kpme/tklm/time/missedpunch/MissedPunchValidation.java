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

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TkConstants;
import org.kuali.kpme.tklm.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.TransactionalDocumentRuleBase;
import org.kuali.rice.krad.util.GlobalVariables;

public class MissedPunchValidation extends TransactionalDocumentRuleBase {
	
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(Document document) {
        boolean valid = true;
        
        MissedPunchDocument missedPunchDocument = (MissedPunchDocument) document;
        DocumentStatus documentStatus = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(missedPunchDocument.getDocumentNumber());
        
        if (DocumentStatus.INITIATED.equals(DocumentStatus.fromCode(documentStatus.getCode()))
        		|| DocumentStatus.SAVED.equals(DocumentStatus.fromCode(documentStatus.getCode()))) {
	        valid &= validateTimeSheet(missedPunchDocument);
	        
	        if (valid) {
	        	ClockLog lastClock = TkServiceLocator.getClockLogService().getLastClockLog(missedPunchDocument.getPrincipalId());
		        try {
		        	valid &= validateClockAction(missedPunchDocument, lastClock);
		        	valid &= validateClockTime(missedPunchDocument, lastClock);
				} catch (ParseException e) {
					e.printStackTrace();
				}
	        }
        }
	    
        return valid;
	}
	
    // do not allow a missed punch if the time sheet document is enroute or final
    boolean validateTimeSheet(MissedPunchDocument mp) {
    	boolean valid = true;
    	TimesheetDocument tsd = TkServiceLocator.getTimesheetService().getTimesheetDocument(mp.getTimesheetDocumentId());
    	if(tsd != null 
    			&& (tsd.getDocumentHeader().getDocumentStatus().equals(HrConstants.ROUTE_STATUS.ENROUTE) 
    					|| tsd.getDocumentHeader().getDocumentStatus().equals(HrConstants.ROUTE_STATUS.FINAL))) {
    		GlobalVariables.getMessageMap().putError("document.timesheetDocumentId", "clock.mp.invalid.timesheet");
    		valid = false;
    	}
    	
    	return valid;
    }

    /**
     * Checks the provided MissedPunch for a valid ClockAction.
     *
     * @param mp The MissedPunch we are validating.
     * @param lastClock The ClockLog entry that happened before the one we want to create.
     *
     * @return true if valid, false otherwise.
     */
    boolean validateClockAction(MissedPunchDocument mp, ClockLog lastClock) {
        boolean valid = true;
        Set<String> validActions = (lastClock != null) ? TkConstants.CLOCK_ACTION_TRANSITION_MAP.get(lastClock.getClockAction()) : new HashSet<String>();


        if (mp.getClockAction().equals(TkConstants.CLOCK_OUT) || mp.getClockAction().equals(TkConstants.LUNCH_OUT)) {
            ClockLog lci = TkServiceLocator.getClockLogService().getLastClockLog(mp.getPrincipalId(),TkConstants.CLOCK_IN); //last clock in
            ClockLog lli = TkServiceLocator.getClockLogService().getLastClockLog(mp.getPrincipalId(),TkConstants.LUNCH_IN); //last lunch in
            if (lci != null) {
                MissedPunchDocument mpd = TkServiceLocator.getMissedPunchService().getMissedPunchByClockLogId(lci.getTkClockLogId());
                if(mpd != null) {
                    GlobalVariables.getMessageMap().putError("document.clockAction", "clock.mp.onlyOne.action");
                    return false;
                }
            } else if(lli != null) {
                MissedPunchDocument mpd = TkServiceLocator.getMissedPunchService().getMissedPunchByClockLogId(lli.getTkClockLogId());
                if(mpd != null) {
                    GlobalVariables.getMessageMap().putError("document.clockAction", "clock.mp.onlyOne.action");
                    return false;
                }
            }
        }

        // if a clockIn/lunchIn has been put in by missed punch, do not allow missed punch for clockOut/LunchOut
        // missed punch can only be used on a tiemblock once.
//        if(lastClock != null
//        		&& (lastClock.getClockAction().equals(TkConstants.CLOCK_IN) || lastClock.getClockAction().equals(TkConstants.LUNCH_IN))) {
//        	MissedPunchDocument mpd = TkServiceLocator.getMissedPunchService().getMissedPunchByClockLogId(lastClock.getTkClockLogId());
//        	if(mpd != null) {
//	       	 	GlobalVariables.getMessageMap().putError("document.clockAction", "clock.mp.onlyOne.action");
//	            return false;
//        	}
//        }
        if (!StringUtils.equals("A", mp.getDocumentStatus()) && !validActions.contains(mp.getClockAction())) {
            GlobalVariables.getMessageMap().putError("document.clockAction", "clock.mp.invalid.action");
            valid = false;
        }
        
        return valid;
    }

    /**
     * Checks whether the provided MissedPunch has a valid time based on the
     * previous ClockLog.
     *
     * @param mp The MissedPunch we are validating.
     * @param lastClock The ClockLog entry that happened before the one we want to create.
     *
     * @return true if valid, false otherwise.
     * @throws ParseException 
     */
    boolean validateClockTime(MissedPunchDocument mp, ClockLog lastClock) throws ParseException {
        boolean valid = true;

        if (lastClock == null) {
            return valid;
        }
        
        //Missed Action Date and Missed Action Time are required fields. KPME-1853
        if(mp.getActionTime() == null || mp.getActionDate() == null)
        	return false;
        
        DateTime clockLogDateTime = new DateTime(lastClock.getClockTimestamp().getTime());
        DateTime boundaryMax = clockLogDateTime.plusDays(1);
        DateTime nowTime = new DateTime(LocalDate.now().toDate());
        long offset = HrServiceLocator.getTimezoneService().getTimezoneOffsetFromServerTime(HrServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
        long dateTimeLocal = new LocalTime(mp.getActionTime()).getMillisOfDay() + mp.getActionDate().getTime() - offset;

        //this will be in system's timezone, but offset with user's timezone
        DateTime actionDateTime = new DateTime(dateTimeLocal);

        // if date is a future date
        if(actionDateTime.getYear()> nowTime.getYear()
        		|| (actionDateTime.getYear()==nowTime.getYear() && actionDateTime.getDayOfYear() > nowTime.getDayOfYear())) {
        	GlobalVariables.getMessageMap().putError("document.actionDate", "clock.mp.future.date");
        	return false;
        }

        // if time is a future time
        if(actionDateTime.getMillis() > nowTime.getMillis()) {
        	GlobalVariables.getMessageMap().putError("document.actionTime", "clock.mp.future.time");
        	return false;
        }
        
        if ((!StringUtils.equals(lastClock.getClockAction(), TkConstants.CLOCK_OUT) && actionDateTime.isAfter(boundaryMax)) 
        		|| actionDateTime.isBefore(clockLogDateTime)) {
        	GlobalVariables.getMessageMap().putError("document.actionTime", "clock.mp.invalid.datetime");
            valid = false;
        }

        return valid;
    }

}