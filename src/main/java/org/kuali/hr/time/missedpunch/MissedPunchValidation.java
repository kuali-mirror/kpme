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
package org.kuali.hr.time.missedpunch;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.TransactionalDocumentRuleBase;
import org.kuali.rice.krad.util.GlobalVariables;

public class MissedPunchValidation extends TransactionalDocumentRuleBase {

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

        // if a clockIn/lunchIn has been put in by missed punch, do not allow missed punch for clockOut/LunchOut
        // missed punch can only be used on a tiemblock once.
        if(lastClock != null 
        		&& (lastClock.getClockAction().equals(TkConstants.CLOCK_IN) || lastClock.getClockAction().equals(TkConstants.LUNCH_IN))) {
        	MissedPunchDocument mpd = TkServiceLocator.getMissedPunchService().getMissedPunchByClockLogId(lastClock.getTkClockLogId());
        	if(mpd != null) {
	       	 	GlobalVariables.getMessageMap().putError("document.clockAction", "clock.mp.onlyOne.action");
	            return false;
        	}
        }
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

        if (lastClock == null)
            return valid;
        
        //Missed Action Date and Missed Action Time are required fields. KPME-1853
        if(mp.getActionTime() == null || mp.getActionDate() == null)
        	return false;
        
        DateTime clockLogDateTime = new DateTime(lastClock.getClockTimestamp().getTime());
        DateTime boundaryMax = clockLogDateTime.plusDays(1);
        DateTime nowTime = new DateTime(TKUtils.getCurrentDate());
        
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String s = formatter.format(mp.getActionDate());
		Date tempDate = formatter.parse(s);
		Timestamp dateLocal = new Timestamp(tempDate.getTime());
        LocalTime timeLocal = new LocalTime(mp.getActionTime().getTime());
        DateTime actionDateTime = new DateTime(dateLocal.getTime());
        actionDateTime = actionDateTime.plus(timeLocal.getMillisOfDay());

        // convert the action time to the system zone 
        Timestamp ts = new Timestamp(actionDateTime.getMillis());
        ClockLog lastLog = TkServiceLocator.getClockLogService().getLastClockLog(mp.getPrincipalId());
        Long zoneOffset = TkServiceLocator.getTimezoneService().getTimezoneOffsetFromServerTime(DateTimeZone.forID(lastLog.getClockTimestampTimezone()));
        Timestamp actionTime = new Timestamp(ts.getTime()-zoneOffset);
        DateTime newDateTime = new DateTime(actionTime.getTime());

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
        
        if ( ((!StringUtils.equals(lastClock.getClockAction(), TkConstants.CLOCK_OUT) && actionDateTime.isAfter(boundaryMax)) 
        		|| newDateTime.isBefore(clockLogDateTime)) && StringUtils.equals(mp.getDocumentStatus(),"R")) {
        	GlobalVariables.getMessageMap().putError("document.actionTime", "clock.mp.invalid.datetime");
            valid = false;
        }

        return valid;
    }
 
    // do not allow a missed punch if the time sheet document is enroute or final
    boolean validateTimeSheet(MissedPunchDocument mp) {
    	boolean valid = true;
    	TimesheetDocument tsd = TkServiceLocator.getTimesheetService().getTimesheetDocument(mp.getTimesheetDocumentId());
    	if(tsd != null 
    			&& (tsd.getDocumentHeader().getDocumentStatus().equals(TkConstants.ROUTE_STATUS.ENROUTE) 
    					|| tsd.getDocumentHeader().getDocumentStatus().equals(TkConstants.ROUTE_STATUS.FINAL))) {
    		GlobalVariables.getMessageMap().putError("document.timesheetDocumentId", "clock.mp.invalid.timesheet");
    		valid = false;
    	}
    	
    	return valid;
    }
	@Override
	public boolean processRouteDocument(Document document) {
        boolean ret = super.processRouteDocument(document);
        MissedPunchDocument mpDoc = (MissedPunchDocument)document;
        if(!validateTimeSheet(mpDoc)) {
        	return false;
        }
        ClockLog lastClock = TkServiceLocator.getClockLogService().getLastClockLog(mpDoc.getPrincipalId());
        ret &= validateClockAction(mpDoc, lastClock);
        try {
			ret &= validateClockTime(mpDoc, lastClock);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return ret;
	}
}
