package org.kuali.hr.time.mobile.service;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	public HashMap<String,List<String>> addClockAction(String principalId, String assignmentKey, String clockAction) {
		HashMap<String,List<String>> errorWarningMap = new HashMap<String,List<String>>();

        // Set person on the context
        // This is primary for getting the assignment, since we get the assignment by using the target principal id on the context
        TKUser user = new TKUser();
        Person person = KIMServiceLocator.getPersonService().getPerson(principalId);
        user.setActualPerson(person);
        TKContext.setUser(user);

		Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(new AssignmentDescriptionKey(assignmentKey), TKUtils.getCurrentDate());
        Date currentDate = TKUtils.getCurrentDate();
        CalendarEntries calendarEntries = TkServiceLocator.getCalendarSerivce().getCurrentCalendarDates(principalId,  currentDate);
        TimesheetDocument td;
		try {
			td = TkServiceLocator.getTimesheetService().openTimesheetDocument(principalId, calendarEntries);
		} catch (WorkflowException e) {
			throw new RuntimeException("Could not open timesheet");
		}
        
		String ip = TKUtils.getIPAddressFromRequest(TKContext.getHttpServletRequest());
        Timestamp currentTs = new Timestamp(System.currentTimeMillis());

        // processClockLog is the correct method to use. It creates and persists a clock log and a time block if necessary.
        // buildClockLog just creates a clock log object.
        TkServiceLocator.getClockLogService().processClockLog(currentTs, assignment, td.getCalendarEntry(), ip,
                new java.sql.Date(currentTs.getTime()), td, getCurrentClockAction(), principalId);

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
