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
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.web.TimesheetAction;
import org.kuali.hr.time.timesheet.web.TimesheetActionForm;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.exception.AuthorizationException;

public class ClockAction extends TimesheetAction {

    private static final Logger LOG = Logger.getLogger(ClockAction.class);
    public static final SimpleDateFormat SDF =  new SimpleDateFormat("EEE, MMMM d yyyy HH:mm:ss, zzzz");
    public static final String SEPERATOR = "[****]+";

    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        super.checkTKAuthorization(form, methodToCall); // Checks for read access first.

        TimesheetActionForm taForm = (TimesheetActionForm)form;
        TKUser user = TKContext.getUser();
        UserRoles roles = user.getCurrentRoles(); // either backdoor or actual
        String docid = taForm.getDocumentId();

//        // Check for write access to Timeblock.
        if (StringUtils.equals(methodToCall, "clockAction") ||
                StringUtils.equals(methodToCall, "addTimeBlock") ||
                StringUtils.equals(methodToCall, "editTimeBlock") ||
                StringUtils.equals(methodToCall, "distributeTimeBlocks") ||
                StringUtils.equals(methodToCall, "saveNewTimeBlocks") ||
                StringUtils.equals(methodToCall, "deleteTimeBlock")) {
            if (!roles.isDocumentWritable(docid)) {
                throw new AuthorizationException(roles.getPrincipalId(), "ClockAction", "");
            }
        }
    }


    	@Override
    	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    		ActionForward forward = super.execute(mapping, form, request, response);
    	    ClockActionForm caf = (ClockActionForm) form;
    	    caf.setCurrentServerTime(String.valueOf(new Date().getTime()));
            caf.setShowLunchButton(TkServiceLocator.getSystemLunchRuleService().isShowLunchButton());
    	    caf.setAssignmentDescriptions(TkServiceLocator.getAssignmentService().getAssignmentDescriptions(caf.getTimesheetDocument(), true));
            if (caf.isShowLunchButton()) {
                // We don't need to worry about the assignments and lunch rules
                // if the global lunch rule is turned off.

                // Check for presence of department lunch rule.
                Map<String,Boolean> assignmentDeptLunchRuleMap = new HashMap<String,Boolean>();
                for (Assignment a : caf.getTimesheetDocument().getAssignments()) {
                    String key = AssignmentDescriptionKey.getAssignmentKeyString(a);
                    assignmentDeptLunchRuleMap.put(key, a.getDeptLunchRule() != null);
                }
                caf.setAssignmentLunchMap(assignmentDeptLunchRuleMap);
            }
    	    String principalId = TKContext.getUser().getTargetPrincipalId();
    	    if(principalId != null) {
    	    	caf.setPrincipalId(principalId);
    	    }
    	    caf.isShowDistributeButton();

    	    String tbIdString = caf.getEditTimeBlockId();
    	    if(tbIdString != null) {
    	    	caf.setCurrentTimeBlock(TkServiceLocator.getTimeBlockService().getTimeBlock(Long.valueOf(caf.getEditTimeBlockId())));
    	    }

    	    ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(principalId);
    	    if (lastClockLog != null) {
    	    	Timestamp lastClockTimestamp = lastClockLog.getClockTimestamp();
                String lastClockZone = lastClockLog.getClockTimestampTimezone();
                if (StringUtils.isEmpty(lastClockZone)) {
                    lastClockZone = TkConstants.SYSTEM_TIME_ZONE;
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

    	    if(lastClockLog == null || StringUtils.equals(lastClockLog.getClockAction(), TkConstants.CLOCK_OUT)) {
    	    	caf.setCurrentClockAction(TkConstants.CLOCK_IN);
    	    }
	   	    else {

	   	    	if(StringUtils.equals(lastClockLog.getClockAction(),TkConstants.LUNCH_OUT) && TkServiceLocator.getSystemLunchRuleService().isShowLunchButton()) {
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
    	    	Map<String,String> assignmentDesc = TkServiceLocator.getAssignmentService().getAssignmentDescriptions(assignment);
    	    	caf.setAssignmentDescriptions(assignmentDesc);

	   	    }
    	    return forward;
    	}

    	public ActionForward clockAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	    ClockActionForm caf = (ClockActionForm)form;

    	    // TODO: Validate that clock action is valid for this user
    	    // TODO: this needs to be integrated with the error tag
    	    if(StringUtils.isBlank(caf.getSelectedAssignment())) {
    	    	caf.setErrorMessage("No assignment selected.");
    			return mapping.findForward("basic");
    	    }

    	    // process rules
    	    Timestamp clockTimestamp = TkServiceLocator.getGracePeriodService().processGracePeriodRule(new Timestamp(System.currentTimeMillis()), new java.sql.Date(caf.getPayCalendarDates().getBeginPeriodDateTime().getTime()));
            String ip = TKUtils.getIPAddressFromRequest(request);

    	    ClockLog clockLog = TkServiceLocator.getClockLogService().buildClockLog(clockTimestamp, caf.getSelectedAssignment(),caf.getTimesheetDocument(),caf.getCurrentClockAction(), ip);
    	    TkServiceLocator.getClockLocationRuleService().processClockLocationRule(clockLog, TKUtils.getCurrentDate());

    	    if(StringUtils.equals(caf.getCurrentClockAction(), TkConstants.CLOCK_OUT) || StringUtils.equals(caf.getCurrentClockAction(), TkConstants.LUNCH_OUT)) {
    	    	ClockLog lastLog = null;
    	    	Timestamp lastClockTimestamp = null;
    	    	Long beginClockLogId = null;
    	    	Long endClockLogId = null;

    	    	if(StringUtils.equals(caf.getCurrentClockAction(), TkConstants.LUNCH_OUT)) {
    	    		lastLog = TkServiceLocator.getClockLogService().getLastClockLog(TKContext.getUser().getTargetPrincipalId(), TkConstants.CLOCK_IN);
    	    	} else if(StringUtils.equals(caf.getCurrentClockAction(), TkConstants.CLOCK_OUT)) {
    	    		lastLog = TkServiceLocator.getClockLogService().getLastClockLog(TKContext.getUser().getTargetPrincipalId());
    	    	}
    	    	if(lastLog != null) {
    	    		lastClockTimestamp= lastLog.getClockTimestamp();
    	    		beginClockLogId = lastLog.getTkClockLogId();
    	    	} else {
    	    		caf.setErrorMessage("No Clock action found for " + TKContext.getUser().getTargetPrincipalId());
        			return mapping.findForward("basic");
    	    	}
    	    	//Save current clock log to get id for timeblock building
        	    TkServiceLocator.getClockLogService().saveClockLog(clockLog);
        	    caf.setClockLog(clockLog);
        	    endClockLogId = clockLog.getTkClockLogId();

    			long beginTime = lastClockTimestamp.getTime();
    			Timestamp beginTimestamp = new Timestamp(beginTime);
    			Timestamp endTimestamp = caf.getClockLog().getClockTimestamp();

    			Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(caf.getTimesheetDocument(),
						caf.getSelectedAssignment());

    			String earnCode = TKContext.getUser().getCurrentTargetRoles().isSynchronous() ? assignment.getJob().getPayTypeObj().getRegEarnCode() : caf.getSelectedEarnCode();

    			// New Time Blocks, pointer reference
                List<TimeBlock> newTimeBlocks = caf.getTimesheetDocument().getTimeBlocks();
                List<TimeBlock> referenceTimeBlocks = new ArrayList<TimeBlock>(caf.getTimesheetDocument().getTimeBlocks().size());
                for (TimeBlock tb : caf.getTimesheetDocument().getTimeBlocks()) {
                    referenceTimeBlocks.add(tb.copy());
                }

                // Add TimeBlocks after we store our reference object!
                List<TimeBlock> aList = TkServiceLocator.getTimeBlockService().buildTimeBlocks(assignment,earnCode, caf.getTimesheetDocument(),beginTimestamp, endTimestamp,BigDecimal.ZERO, BigDecimal.ZERO, true);
                for(TimeBlock tb: aList){
                	tb.setClockLogBeginId(beginClockLogId);
                	tb.setClockLogEndId(endClockLogId);
                }
                newTimeBlocks.addAll(aList);

    			//reset time hour details
    			TkServiceLocator.getTimeBlockService().resetTimeHourDetail(newTimeBlocks);
    			//apply any rules for this action
    			TkServiceLocator.getTkRuleControllerService().applyRules(TkConstants.ACTIONS.CLOCK_OUT, newTimeBlocks, caf.getPayCalendarDates(), caf.getTimesheetDocument());

    			//call persist method that only saves added/deleted/changed timeblocks
    			TkServiceLocator.getTimeBlockService().saveTimeBlocks(referenceTimeBlocks, newTimeBlocks);
    	    	//TkServiceLocator.getTimeHourDetailService().saveTimeHourDetail(tb);
    	    	//TkServiceLocator.getTimeBlockHistoryService().saveTimeBlockHistory(caf);
    	    } else {
        	    TkServiceLocator.getClockLogService().saveClockLog(clockLog);
        	    caf.setClockLog(clockLog);
    	    }
    	    return mapping.findForward("basic");
    	}

    public ActionForward distributeTimeBlocks(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
   		 ClockActionForm caf = (ClockActionForm)form;
   		 caf.findTimeBlocksToDistribute();
   		 return mapping.findForward("tb");
   	 }


	public ActionForward editTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		ClockActionForm caf = (ClockActionForm)form;
		TimeBlock tb = caf.getCurrentTimeBlock();
		caf.setCurrentAssignmentKey(tb.getAssignmentKey());

		ActionForward forward = mapping.findForward("et");

		return new ActionForward(forward.getPath() + "?editTimeBlockId=" + tb.getTkTimeBlockId().toString());

	}

	public ActionForward addTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		 ClockActionForm caf = (ClockActionForm)form;
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

}
