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
package org.kuali.hr.time.mobile.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.calendar.CalendarEntry;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kew.api.exception.WorkflowException;

import com.google.gson.Gson;

public class TkMobileServiceImpl implements TkMobileService {

	@Override
	public String getClockEntryInfo(String principalId) {
		ClockEntryInfo clockEntryInfo = new ClockEntryInfo();
		ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(principalId);
		if(lastClockLog != null){
			clockEntryInfo.setLastClockLogDescription(getLastClockLogDescription(principalId));
		}
		List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(principalId, LocalDate.now());

		for(Assignment assignment : assignments){
			if(assignment.isSynchronous()){
				String key = new AssignmentDescriptionKey(assignment).toAssignmentKeyString();
				String desc = assignment.getAssignmentDescription();
				clockEntryInfo.getAssignKeyToAssignmentDescriptions().put(key, desc);
			}
		}
		List<String> clockActions = getClockActions(principalId);
		clockEntryInfo.setClockActions(clockActions);
		return new Gson().toJson(clockEntryInfo);
	}

	@Override
	public Map<String,List<String>> addClockAction(String principalId, String assignmentKey, String clockAction) {
		HashMap<String,List<String>> errorWarningMap = new HashMap<String,List<String>>();

        // Set person on the context
        // This is primary for getting the assignment, since we get the assignment by using the target principal id on the context
        TKContext.setTargetPrincipalId(principalId);

		Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(new AssignmentDescriptionKey(assignmentKey), LocalDate.now());
        CalendarEntry calendarEntry = TkServiceLocator.getCalendarService().getCurrentCalendarDates(principalId, new LocalDate().toDateTimeAtStartOfDay());
        TimesheetDocument td;
		try {
			td = TkServiceLocator.getTimesheetService().openTimesheetDocument(principalId, calendarEntry);
		} catch (WorkflowException e) {
			throw new RuntimeException("Could not open timesheet");
		}
        
		String ip = TKUtils.getIPAddressFromRequest(TKContext.getHttpServletRequest());
        Timestamp currentTs = new Timestamp(System.currentTimeMillis());

        // processClockLog is the correct method to use. It creates and persists a clock log and a time block if necessary.
        // buildClockLog just creates a clock log object.
        TkServiceLocator.getClockLogService().processClockLog(currentTs, assignment, td.getCalendarEntry(), ip,
                LocalDate.fromDateFields(currentTs), td, getCurrentClockAction(), principalId);

        // TODO: not sure what we want to return for the errorWarningMap

		return errorWarningMap;
	}
	
	private String getLastClockLogDescription(String principalId){
		ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(principalId);
		if(lastClockLog != null){
			String lastClockDescription;
			if(StringUtils.equals(lastClockLog.getClockAction(), "CI")){
				lastClockDescription = "Clocked in since : ";
			} else if(StringUtils.equals(lastClockLog.getClockAction(), "CO")){
				lastClockDescription = "Clocked out since : ";
			} else if(StringUtils.equals(lastClockLog.getClockAction(), "LI")){
				lastClockDescription = "Returned from lunch since :";
			} else {
				lastClockDescription = "At lunch since :";
			}
			//TODO convert for timezone
			
			lastClockDescription += TKUtils.formatTimestamp(lastClockLog.getClockTimestamp());
			return lastClockDescription;
		}
		return "";
	}
	
	private List<String> getClockActions(String principalId){
		ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(principalId);
		List<String> clockActions = new ArrayList<String>();
		if(lastClockLog != null){
			if(StringUtils.equals(lastClockLog.getClockAction(), "CI")){
				clockActions.add("Clock Out");
				clockActions.add("Lunch Out");
			} else if(StringUtils.equals(lastClockLog.getClockAction(), "CO")){
				clockActions.add("Clock In");
			} else if(StringUtils.equals(lastClockLog.getClockAction(), "LI")){
				clockActions.add("Clock Out");
			} else {
				clockActions.add("Lunch In");
			}
		}
		return clockActions;
	}

    private String getCurrentClockAction() {
        ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(TKContext.getTargetPrincipalId());
        String currentClockAction = "";
        if(lastClockLog != null){
			if(StringUtils.equals(lastClockLog.getClockAction(), TkConstants.CLOCK_IN)){
				currentClockAction = TkConstants.CLOCK_OUT;
			} else if(StringUtils.equals(lastClockLog.getClockAction(), TkConstants.CLOCK_OUT)){
				currentClockAction = TkConstants.CLOCK_IN;
			} else if(StringUtils.equals(lastClockLog.getClockAction(), TkConstants.LUNCH_IN)){
				currentClockAction = TkConstants.LUNCH_OUT;
			} else {
				currentClockAction = TkConstants.LUNCH_IN;
			}
		}
		return currentClockAction;
    }

}
