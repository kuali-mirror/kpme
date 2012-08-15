package org.kuali.hr.time.clock.web;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesheet.web.TimesheetAction;
import org.kuali.hr.time.timesheet.web.TimesheetActionForm;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;

public class ClockAction extends TimesheetAction {

    private static final Logger LOG = Logger.getLogger(ClockAction.class);
    public static final SimpleDateFormat SDF = new SimpleDateFormat("EEE, MMMM d yyyy HH:mm:ss, zzzz");
    public static final String SEPERATOR = "[****]+";

    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        super.checkTKAuthorization(form, methodToCall); // Checks for read access first.

        TimesheetActionForm taForm = (TimesheetActionForm) form;
        UserRoles roles = TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId());
        TimesheetDocument doc = TKContext.getCurrentTimesheetDoucment();

        // Check for write access to Timeblock.
        if (StringUtils.equals(methodToCall, "clockAction") ||
                StringUtils.equals(methodToCall, "addTimeBlock") ||
                StringUtils.equals(methodToCall, "editTimeBlock") ||
                StringUtils.equals(methodToCall, "distributeTimeBlocks") ||
                StringUtils.equals(methodToCall, "saveNewTimeBlocks") ||
                StringUtils.equals(methodToCall, "deleteTimeBlock")) {
            if (!roles.isDocumentWritable(doc)) {
                throw new AuthorizationException(roles.getPrincipalId(), "ClockAction", "");
            }
        }
    }


    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = super.execute(mapping, form, request, response);
        ClockActionForm caf = (ClockActionForm) form;
        caf.setCurrentServerTime(String.valueOf(new Date().getTime()));
        caf.getUserSystemOffsetServerTime();
        caf.setShowLunchButton(TkServiceLocator.getSystemLunchRuleService().isShowLunchButton());
        caf.setAssignmentDescriptions(TkServiceLocator.getAssignmentService().getAssignmentDescriptions(caf.getTimesheetDocument(), true));
        if (caf.isShowLunchButton()) {
            // We don't need to worry about the assignments and lunch rules
            // if the global lunch rule is turned off.

            // Check for presence of department lunch rule.
            Map<String, Boolean> assignmentDeptLunchRuleMap = new HashMap<String, Boolean>();
            for (Assignment a : caf.getTimesheetDocument().getAssignments()) {
                String key = AssignmentDescriptionKey.getAssignmentKeyString(a);
                assignmentDeptLunchRuleMap.put(key, a.getDeptLunchRule() != null);
            }
            caf.setAssignmentLunchMap(assignmentDeptLunchRuleMap);
        }
        String principalId = TKUser.getCurrentTargetPerson().getPrincipalId();
        if (principalId != null) {
            caf.setPrincipalId(principalId);
        }
        this.assignShowDistributeButton(caf);
        // if the time sheet document is final or enroute, do not allow missed punch
        if(caf.getTimesheetDocument().getDocumentHeader().getDocumentStatus().equals(TkConstants.ROUTE_STATUS.ENROUTE)
        		|| caf.getTimesheetDocument().getDocumentHeader().getDocumentStatus().equals(TkConstants.ROUTE_STATUS.FINAL)) {
        	caf.setShowMissedPunchButton(false);
        } else {
        	caf.setShowMissedPunchButton(true);
        }

        String tbIdString = caf.getEditTimeBlockId();
        if (tbIdString != null) {
            caf.setCurrentTimeBlock(TkServiceLocator.getTimeBlockService().getTimeBlock(caf.getEditTimeBlockId()));
        }

        ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(principalId);
        if (lastClockLog != null) {
            Timestamp lastClockTimestamp = lastClockLog.getClockTimestamp();
            String lastClockZone = lastClockLog.getClockTimestampTimezone();
            if (StringUtils.isEmpty(lastClockZone)) {
                lastClockZone = TKUtils.getSystemTimeZone();
            }
            // zone will not be null. At this point is Valid or Exception.
            // Exception would indicate bad data stored in the system. We can wrap this, but
            // for now, the thrown exception is probably more valuable.
            DateTimeZone zone = DateTimeZone.forID(lastClockZone);
            DateTime clockWithZone = new DateTime(lastClockTimestamp, zone);
            caf.setLastClockTimeWithZone(clockWithZone.toDate());
            caf.setLastClockTimestamp(lastClockTimestamp);
            caf.setLastClockAction(lastClockLog.getClockAction());
        }

        if (lastClockLog == null || StringUtils.equals(lastClockLog.getClockAction(), TkConstants.CLOCK_OUT)) {
            caf.setCurrentClockAction(TkConstants.CLOCK_IN);
        } else {

            if (StringUtils.equals(lastClockLog.getClockAction(), TkConstants.LUNCH_OUT) && TkServiceLocator.getSystemLunchRuleService().isShowLunchButton()) {
                caf.setCurrentClockAction(TkConstants.LUNCH_IN);
            }
//	   	    	else if(StringUtils.equals(lastClockLog.getClockAction(),TkConstants.LUNCH_OUT)) {
//	   	    		caf.setCurrentClockAction(TkConstants.LUNCH_IN);
//	   	    	}
            else {
                caf.setCurrentClockAction(TkConstants.CLOCK_OUT);
            }
            // if the current clock action is clock out, displays only the clocked-in assignment
            String selectedAssignment = new AssignmentDescriptionKey(lastClockLog.getJobNumber(), lastClockLog.getWorkArea(), lastClockLog.getTask()).toAssignmentKeyString();
            caf.setSelectedAssignment(selectedAssignment);
            Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(caf.getTimesheetDocument(), selectedAssignment);
            Map<String, String> assignmentDesc = TkServiceLocator.getAssignmentService().getAssignmentDescriptions(assignment);
            caf.setAssignmentDescriptions(assignmentDesc);

        }
        return forward;
    }
    
    public void assignShowDistributeButton(ClockActionForm caf) {
    	TimesheetDocument timesheetDocument = caf.getTimesheetDocument();
    	if(timesheetDocument != null) {
    		List<Assignment> assignments = timesheetDocument.getAssignments();
    		if(assignments.size() <= 1) {
    			caf.setShowDistrubuteButton(false);
    			return;
    		}
    		List<TimeCollectionRule> ruleList = new ArrayList<TimeCollectionRule> ();
    		for(Assignment assignment: assignments) {
    			TimeCollectionRule rule = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(assignment.getJob().getDept(), assignment.getWorkArea(), assignment.getJob().getHrPayType(),assignment.getEffectiveDate());
		    	if(rule != null) {
		    		if(rule.isHrsDistributionF()) {
		    			ruleList.add(rule);
		    		}
		    	}
    		}
    		// if there's only one eligible assignment, don't show the distribute button
    		if(ruleList.size() <= 1) {
    			caf.setShowDistrubuteButton(false);
    			return;	
    		} else {
	    		caf.setShowDistrubuteButton(true);
				return;
    		}
    	}
    	caf.setShowDistrubuteButton(false);
    }
    

    public ActionForward clockAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ClockActionForm caf = (ClockActionForm) form;

        // TODO: Validate that clock action is valid for this user
        // TODO: this needs to be integrated with the error tag
        if (StringUtils.isBlank(caf.getSelectedAssignment())) {
            caf.setErrorMessage("No assignment selected.");
            return mapping.findForward("basic");
        }
        ClockLog previousClockLog = TkServiceLocator.getClockLogService().getLastClockLog(TKUser.getCurrentTargetPerson().getPrincipalId());
        if(previousClockLog != null && StringUtils.equals(caf.getCurrentClockAction(), previousClockLog.getClockAction())){
        	caf.setErrorMessage("The operation is already performed.");
            return mapping.findForward("basic");
        }
        String ip = TKUtils.getIPAddressFromRequest(request);
        Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(caf.getTimesheetDocument(), caf.getSelectedAssignment());
        
        List<Assignment> lstAssingmentAsOfToday = TkServiceLocator.getAssignmentService().getAssignments(TKContext.getTargetPrincipalId(), TKUtils.getCurrentDate());
        boolean foundValidAssignment = false;
        for(Assignment assign : lstAssingmentAsOfToday){
        	if((assign.getJobNumber().compareTo(assignment.getJobNumber()) ==0) &&
        		(assign.getWorkArea().compareTo(assignment.getWorkArea()) == 0) &&
        		(assign.getTask().compareTo(assignment.getTask()) == 0)){
        		foundValidAssignment = true;
        		break;
        	}
        }
        
        if(!foundValidAssignment){
        	caf.setErrorMessage("Assignment is not effective as of today");
        	return mapping.findForward("basic");
        }
        
               
        ClockLog clockLog = TkServiceLocator.getClockLogService().processClockLog(new Timestamp(System.currentTimeMillis()), assignment, caf.getPayCalendarDates(), ip,
                TKUtils.getCurrentDate(), caf.getTimesheetDocument(), caf.getCurrentClockAction(), TKUser.getCurrentTargetPerson().getPrincipalId());

        caf.setClockLog(clockLog);

        return mapping.findForward("basic");
    }

    public ActionForward distributeTimeBlocks(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ClockActionForm caf = (ClockActionForm) form;
        caf.findTimeBlocksToDistribute();
        return mapping.findForward("tb");
    }


    public ActionForward editTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ClockActionForm caf = (ClockActionForm) form;
        TimeBlock tb = caf.getCurrentTimeBlock();
        caf.setCurrentAssignmentKey(tb.getAssignmentKey());

        ActionForward forward = mapping.findForward("et");

        return new ActionForward(forward.getPath() + "?editTimeBlockId=" + tb.getTkTimeBlockId().toString());

    }
    public ActionForward addTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ClockActionForm caf = (ClockActionForm) form;
        TimeBlock currentTb = caf.getCurrentTimeBlock();
        List<TimeBlock> newTimeBlocks = caf.getTimesheetDocument().getTimeBlocks();
        List<TimeBlock> referenceTimeBlocks = new ArrayList<TimeBlock>(caf.getTimesheetDocument().getTimeBlocks().size());
        for (TimeBlock tb : caf.getTimesheetDocument().getTimeBlocks()) {
            referenceTimeBlocks.add(tb.copy());
        }
        //call persist method that only saves added/deleted/changed timeblocks
        TkServiceLocator.getTimeBlockService().saveTimeBlocks(referenceTimeBlocks, newTimeBlocks);

        ActionForward forward = mapping.findForward("et");

        return new ActionForward(forward.getPath() + "?editTimeBlockId=" + currentTb.getTkTimeBlockId().toString());
    }
    
    public ActionForward saveNewTimeBlocks(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		ClockActionForm caf = (ClockActionForm)form;
		String tbId = caf.getTbId();
		String timesheetDocId = caf.getTsDocId();

		String[] assignments = caf.getNewAssignDesCol().split(SEPERATOR);
		String[] beginDates = caf.getNewBDCol().split(SEPERATOR);
		String[] beginTimes = caf.getNewBTCol().split(SEPERATOR);
		String[] endDates = caf.getNewEDCol().split(SEPERATOR);
		String[] endTimes = caf.getNewETCol().split(SEPERATOR);
		String[] hrs = caf.getNewHrsCol().split(SEPERATOR);
		String earnCode = TkServiceLocator.getTimeBlockService().getTimeBlock(tbId).getEarnCode();

		List<TimeBlock> newTbList = new ArrayList<TimeBlock>();
		for(int i = 0; i < hrs.length; i++) {
			TimeBlock tb = new TimeBlock();
			BigDecimal hours = new BigDecimal(hrs[i]);
			Timestamp beginTS = TKUtils.convertDateStringToTimestamp(beginDates[i], beginTimes[i]);
			Timestamp endTS = TKUtils.convertDateStringToTimestamp(endDates[i], endTimes[i]);
			String assignString = assignments[i];
			Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(assignString);
			
			TimesheetDocument tsDoc = TkServiceLocator.getTimesheetService().getTimesheetDocument(timesheetDocId);
			
			tb = TkServiceLocator.getTimeBlockService().createTimeBlock(tsDoc, beginTS, endTS, assignment, earnCode, hours,BigDecimal.ZERO, false, false);
			newTbList.add(tb);
		}
		TkServiceLocator.getTimeBlockService().resetTimeHourDetail(newTbList);
		TkServiceLocator.getTimeBlockService().saveTimeBlocks(newTbList);
		TimeBlock oldTB = TkServiceLocator.getTimeBlockService().getTimeBlock(tbId);
		TkServiceLocator.getTimeBlockService().deleteTimeBlock(oldTB);
		return mapping.findForward("basic");
	}
	
	public ActionForward validateNewTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		ClockActionForm caf = (ClockActionForm)form;
		String tbId = caf.getTbId();
		String[] assignments = caf.getNewAssignDesCol().split(SEPERATOR);
		String[] beginDates = caf.getNewBDCol().split(SEPERATOR);
		String[] beginTimes = caf.getNewBTCol().split(SEPERATOR);
		String[] endDates = caf.getNewEDCol().split(SEPERATOR);
		String[] endTimes = caf.getNewETCol().split(SEPERATOR);
		String[] hrs = caf.getNewHrsCol().split(SEPERATOR);

		List<Interval> newIntervals = new ArrayList<Interval>();
		JSONArray errorMsgList = new JSONArray();

		// validates that all fields are available
		if(assignments.length != beginDates.length ||
				assignments.length!= beginTimes.length ||
				assignments.length != endDates.length ||
				assignments.length != endTimes.length ||
				assignments.length != hrs.length) {
			errorMsgList.add("All fields are required");
		    caf.setOutputString(JSONValue.toJSONString(errorMsgList));
		    return mapping.findForward("ws");
		}

		for(int i = 0; i < hrs.length; i++) {
			String index = String.valueOf(i+1);

			// validate the hours field
			BigDecimal dc = new BigDecimal(hrs[i]);
		    if (dc.compareTo(new BigDecimal("0")) == 0) {
		        errorMsgList.add("The entered hours for entry " + index + " is not valid.");
		        caf.setOutputString(JSONValue.toJSONString(errorMsgList));
		        return mapping.findForward("ws");
		    }

		    // check if the begin / end time are valid
		    // should not include time zone in consideration when conparing time intervals
		    Timestamp beginTS = TKUtils.convertDateStringToTimestampWithoutZone(beginDates[i], beginTimes[i]);
			Timestamp endTS = TKUtils.convertDateStringToTimestampWithoutZone(endDates[i], endTimes[i]);
		    if ((beginTS.compareTo(endTS) > 0 || endTS.compareTo(beginTS) < 0)) {
		        errorMsgList.add("The time or date for entry " + index + " is not valid.");
		        caf.setOutputString(JSONValue.toJSONString(errorMsgList));
		        return mapping.findForward("ws");
		    }

		    // check if new time blocks overlap with existing time blocks
		    DateTime start = new DateTime(beginTS);
		    DateTime end = new DateTime(endTS);
		    Interval addedTimeblockInterval = new Interval(start, end);
		    newIntervals.add(addedTimeblockInterval);
		    for (TimeBlock timeBlock : caf.getTimesheetDocument().getTimeBlocks()) {
		    	if(timeBlock.getTkTimeBlockId().equals(tbId)) {	// ignore the original time block
		    		continue;
		    	}
		    	if(timeBlock.getHours().compareTo(BigDecimal.ZERO) == 0) { // ignore time blocks with zero hours
		    		continue;
		    	}
			    Interval timeBlockInterval = new Interval(timeBlock.getBeginTimestamp().getTime(), timeBlock.getEndTimestamp().getTime());
			    if (timeBlockInterval.overlaps(addedTimeblockInterval)) {
			        errorMsgList.add("The time block you are trying to add for entry " + index + " overlaps with an existing time block.");
			        caf.setOutputString(JSONValue.toJSONString(errorMsgList));
			        return mapping.findForward("ws");
			    }
		    }
		}
		// check if new time blocks overlap with each other
		if(newIntervals.size() > 1 ) {
			for(Interval intv1 : newIntervals) {
				for(Interval intv2 : newIntervals) {
					if(intv1.equals(intv2)) {
						continue;
					}
					if (intv1.overlaps(intv2)) {
						errorMsgList.add("There is time overlap between the entries.");
				        caf.setOutputString(JSONValue.toJSONString(errorMsgList));
				        return mapping.findForward("ws");
					}
				}
			}
		}

	    caf.setOutputString(JSONValue.toJSONString(errorMsgList));
		return mapping.findForward("ws");
 	}

    public ActionForward closeMissedPunchDoc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
        return mapping.findForward("closeMissedPunchDoc");
    }
    
}
