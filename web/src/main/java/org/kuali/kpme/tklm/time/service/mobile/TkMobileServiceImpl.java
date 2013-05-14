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
package org.kuali.kpme.tklm.time.service.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.bo.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.kpme.tklm.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
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
		List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignments(principalId, LocalDate.now());

		for(Assignment assignment : assignments){
			TimeCollectionRule tcr = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(assignment.getDept(), assignment.getWorkArea(), LocalDate.now());
			if(tcr == null || tcr.isClockUserFl()){
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
	public Map<String,List<String>> addClockAction(String principalId, String assignmentKey, String clockAction, String ipAddress) {
		HashMap<String,List<String>> errorWarningMap = new HashMap<String,List<String>>();

		Assignment assignment = HrServiceLocator.getAssignmentService().getAssignment(AssignmentDescriptionKey.get(assignmentKey), LocalDate.now());
        // Set person on the context
        // This is primary for getting the assignment, since we get the assignment by using the target principal id on the context
        HrContext.setTargetPrincipalId(principalId);

        CalendarEntry calendarEntry = HrServiceLocator.getCalendarService().getCurrentCalendarDates(principalId, new LocalDate().toDateTimeAtStartOfDay());
        TimesheetDocument td;
		try {
			td = TkServiceLocator.getTimesheetService().openTimesheetDocument(principalId, calendarEntry);
		} catch (WorkflowException e) {
			throw new RuntimeException("Could not open timesheet");
		}
        
        // processClockLog is the correct method to use. It creates and persists a clock log and a time block if necessary.
        // buildClockLog just creates a clock log object.
        TkServiceLocator.getClockLogService().processClockLog(new DateTime(), assignment, td.getCalendarEntry(), ipAddress,
                LocalDate.now(), td, getCurrentClockAction(), principalId);

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
			
			lastClockDescription += TKUtils.formatDateTimeLong(lastClockLog.getClockDateTime());
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
        ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(HrContext.getTargetPrincipalId());
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
