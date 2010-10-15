package org.kuali.hr.time.clock.web;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.web.TimesheetAction;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;

public class ClockAction extends TimesheetAction {

    	private static final Logger LOG = Logger.getLogger(ClockAction.class);
    	public static final SimpleDateFormat SDF =  new SimpleDateFormat("EEE, MMMM d yyyy HH:mm:ss, zzzz");

    	@Override
    	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    		ActionForward forward = super.execute(mapping, form, request, response);
    	    ClockActionForm caf = (ClockActionForm) form;
    	    String principalId = TKContext.getUser().getPrincipalId();

    	    ClockLog clockLog = TkServiceLocator.getClockLogService().getLastClockLog(principalId);

    	    if(clockLog == null || StringUtils.equals(clockLog.getClockAction(), TkConstants.CLOCK_OUT)) {
    	    	caf.setCurrentClockAction(TkConstants.CLOCK_IN);
    	    }
	   	    else {
    	    	caf.setCurrentClockAction(TkConstants.CLOCK_OUT);
    	    	// if the current clock action is clock out, displays only the clocked-in assignment
    	    	String selectedAssignment = new AssignmentDescriptionKey(clockLog.getJobNumber(), clockLog.getWorkArea(), clockLog.getTask()).toAssignmentKeyString();
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
    	    	throw new RuntimeException("no assignment selected");
    	    }
    	    
    	    // this logic is required in order to get the last clocked-in timestamp for the hour calculation when saving the time block.
    	    ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(TKContext.getUser().getPrincipalId());
    	    if(lastClockLog != null) {
	    	    Timestamp lastClockTimestamp = TkServiceLocator.getClockLogService().getLastClockLog(TKContext.getUser().getPrincipalId()).getTimestamp();
	    	    caf.setLastClockTimestamp(lastClockTimestamp);
    	    }
    	    
    	    ClockLog clockLog = TkServiceLocator.getClockLogService().saveClockAction(caf);
    	    caf.setClockLog(clockLog);
    	    
    	    if(StringUtils.equals(caf.getCurrentClockAction(), "CO")) {
    			long beginTime = caf.getLastClockTimestamp().getTime();
    			Timestamp beginTimestamp = new Timestamp(beginTime);
    			Timestamp endTimestamp = caf.getClockLog().getClockTimestamp();
    			
    			Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(caf.getTimesheetDocument(), 
						caf.getSelectedAssignment());
    			//create the list of timeblocks based on the range passed in
    			List<TimeBlock> lstNewTimeBlocks = TkServiceLocator.getTimeBlockService().buildTimeBlocks(assignment, 
    					caf.getSelectedEarnCode(), caf.getTimesheetDocument(),beginTimestamp, endTimestamp);
    			//concat delta of timeblocks (new and original)
    			lstNewTimeBlocks.addAll(caf.getTimesheetDocument().getTimeBlocks());
    			//TODO do any server side validation of adding checking for overlapping timeblocks etc
    			//return if any issues

    			//reset time hour details
    			lstNewTimeBlocks = TkServiceLocator.getTimeBlockService().resetTimeHourDetail(lstNewTimeBlocks);
    			//apply any rules for this action
    			lstNewTimeBlocks = TkServiceLocator.getTkRuleControllerService().applyRules(TkConstants.ACTIONS.CLOCK_OUT, lstNewTimeBlocks);

    			//call persist method that only saves added/deleted/changed timeblocks
    			TkServiceLocator.getTimeBlockService().saveTimeBlocks(caf.getTimesheetDocument().getTimeBlocks(), lstNewTimeBlocks);
    	    	//TkServiceLocator.getTimeHourDetailService().saveTimeHourDetail(tb);
    	    	//TkServiceLocator.getTimeBlockHistoryService().saveTimeBlockHistory(caf);
    	    }
    	    return mapping.findForward("basic");
    	}
}
