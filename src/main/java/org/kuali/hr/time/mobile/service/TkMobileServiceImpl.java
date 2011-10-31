package org.kuali.hr.time.mobile.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kew.exception.WorkflowException;

import com.google.gson.Gson;

public class TkMobileServiceImpl implements TkMobileService {

	@Override
	public String getClockEntryInfo(String principalId) {
		ClockEntryInfo clockEntryInfo = new ClockEntryInfo();
		ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(principalId);
		if(lastClockLog != null){
			clockEntryInfo.setLastClockLogDescription(getLastClockLogDescription(principalId));
		}
		List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(principalId, TKUtils.getCurrentDate());
		for(Assignment assignment : assignments){
			String key = new AssignmentDescriptionKey(assignment).toAssignmentKeyString();
			String desc = assignment.getAssignmentDescription();
			clockEntryInfo.getAssignKeyToAssignmentDescriptions().put(key, desc);
		}
		List<String> clockActions = getClockActions(principalId);
		clockEntryInfo.setClockActions(clockActions);
		return new Gson().toJson(clockEntryInfo);
	}

	@Override
	public Map<String,List<String>> addClockAction(String principalId, String assignmentKey,
			String clockAction) {
		Map<String,List<String>> errorWarningMap = new HashMap<String,List<String>>();
		Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(new AssignmentDescriptionKey(assignmentKey), TKUtils.getCurrentDate());
        Date currentDate = TKUtils.getCurrentDate();
        CalendarEntries payCalendarEntries = TkServiceLocator.getPayCalendarSerivce().getCurrentPayCalendarDates(principalId,  currentDate);
        TimesheetDocument td;
		try {
			td = TkServiceLocator.getTimesheetService().openTimesheetDocument(principalId, payCalendarEntries);
		} catch (WorkflowException e) {
			throw new RuntimeException("Could not open timesheet");
		}
        String ip = TKUtils.getIPAddressFromRequest(TKContext.getHttpServletRequest());
        TkServiceLocator.getClockLogService().buildClockLog(new Timestamp(System.currentTimeMillis()), assignment, 
						td, clockAction, ip);
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
			lastClockDescription += lastClockLog.getClockTimestamp();
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

}
