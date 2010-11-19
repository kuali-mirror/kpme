package org.kuali.hr.time.detail.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONValue;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.web.TimesheetAction;
import org.kuali.hr.time.util.TkConstants;

public class TimeDetailAction extends TimesheetAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = super.execute(mapping, form, request, response);
		TimeDetailActionForm tdaf = (TimeDetailActionForm) form;

		tdaf.setBeginPeriodDateTime(tdaf.getPayCalendarDates().getBeginPeriodDateTime());
		tdaf.setEndPeriodDateTime(tdaf.getPayCalendarDates().getEndPeriodDateTime());
		
		// TODO: may need to revisit this:
		// when adding / removing timeblocks, it should update the timeblocks on the timesheet document, 
		// so that we can directly fetch the timeblocks from the document
		List<TimeBlock> timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocks(tdaf.getTimesheetDocument().getDocumentHeader().getDocumentId());
		tdaf.setTimeSummary(TkServiceLocator.getTimeSummaryService().getTimeSummary(tdaf.getTimesheetDocument(), timeBlocks));
		
		// for visually impaired users 
		// TimesheetDocument td = tdaf.getTimesheetDocument();
		// List<TimeBlock> timeBlocks = td.getTimeBlocks();
		// tdaf.setTimeBlocks(timeBlocks);
		
		return forward;
	}
	
	// this is an ajax call
	public ActionForward getEarnCodes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
		String earnCodeString = "";

		if(StringUtils.isBlank(tdaf.getSelectedAssignment())) {
			earnCodeString += "<option value=''>-- select an assignment --</option>";
		}
		else {
			List<Assignment> assignments = tdaf.getTimesheetDocument().getAssignments();
			AssignmentDescriptionKey key = new AssignmentDescriptionKey(tdaf.getSelectedAssignment());
			for(Assignment assignment : assignments) {
				if(assignment.getJobNumber().compareTo(key.getJobNumber()) == 0 &&
				    assignment.getWorkArea().compareTo(key.getWorkArea()) == 0 &&
				    assignment.getTask().compareTo(key.getTask()) == 0) {
					
					List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService().getEarnCodes(assignment);
					for(EarnCode earnCode: earnCodes) {
						earnCodeString += "<option value='" + earnCode.getEarnCode() + "_" + earnCode.getEarnCodeType() +"'>" + earnCode.getEarnCode() + " : " + earnCode.getDescription() + "</option>";
					}
				}
			}
		}
		
		tdaf.setOutputString(earnCodeString);
		return mapping.findForward("ws");
	}

	public ActionForward getTimeblocks(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		TimeDetailActionForm timeDetailForm = (TimeDetailActionForm) form;
		
		List<Map<String,Object>> timeBlockList = TkServiceLocator.getTimeBlockService().getTimeBlocksForOurput(timeDetailForm.getTimesheetDocument());
		timeDetailForm.setOutputString(JSONValue.toJSONString(timeBlockList));
		
		return mapping.findForward("ws");
	}

	public ActionForward deleteTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// the corresponding js code resides in the fullcalendar-1.4.7.js somewhere around #1664
		TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
		//Grab timeblock to be deleted from form
		List<TimeBlock> timeBlocks = tdaf.getTimesheetDocument().getTimeBlocks();
		TimeBlock deletedTimeBlock = null;
		for(TimeBlock tb : timeBlocks) {
			if(tb.getTkTimeBlockId().compareTo(tdaf.getTkTimeBlockId()) == 0) {
				deletedTimeBlock = tb;
				break;
			}
		}
		//Remove from the list of timeblocks
		List<TimeBlock> lstNewTimeBlocks = new ArrayList<TimeBlock>();
		lstNewTimeBlocks.addAll(tdaf.getTimesheetDocument().getTimeBlocks());
		lstNewTimeBlocks.remove(deletedTimeBlock);
		//Delete timeblock
		TkServiceLocator.getTimeBlockService().deleteTimeBlock(deletedTimeBlock);
		//Reset time hour details on timeblocks for rule processing
		lstNewTimeBlocks = TkServiceLocator.getTimeBlockService().resetTimeHourDetail(lstNewTimeBlocks);
		//apply any rules for this action
		lstNewTimeBlocks = TkServiceLocator.getTkRuleControllerService().applyRules(TkConstants.ACTIONS.ADD_TIME_BLOCK, lstNewTimeBlocks, tdaf.getPayCalendarDates());
		
		//call persist method that only saves added/deleted/changed timeblocks
		TkServiceLocator.getTimeBlockService().saveTimeBlocks(tdaf.getTimesheetDocument().getTimeBlocks(), lstNewTimeBlocks);
		
		//TODO call timesheet history service
		
		return mapping.findForward("basic");
	}

	public ActionForward addTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
		Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(tdaf.getTimesheetDocument(), 
									tdaf.getSelectedAssignment());
		//create the list of timeblocks based on the range passed in
		
		List<TimeBlock> lstNewTimeBlocks = null;
		if(StringUtils.equals(tdaf.getAcrossDays(), "y")){
			lstNewTimeBlocks = TkServiceLocator.getTimeBlockService().buildTimeBlocksSpanDates(assignment, 
											tdaf.getSelectedEarnCode(), tdaf.getTimesheetDocument(),new Timestamp(tdaf.getStartTime()), 
												new Timestamp(tdaf.getEndTime()),tdaf.getHours());
		} else {
			lstNewTimeBlocks = TkServiceLocator.getTimeBlockService().buildTimeBlocks(assignment, 
											tdaf.getSelectedEarnCode(), tdaf.getTimesheetDocument(),new Timestamp(tdaf.getStartTime()), 
											new Timestamp(tdaf.getEndTime()),tdaf.getHours());
		}

		//concat delta of timeblocks (new and original)
		lstNewTimeBlocks.addAll(tdaf.getTimesheetDocument().getTimeBlocks());
		//TODO do any server side validation of adding checking for overlapping timeblocks etc
		//return if any issues
		//TODO add validation to not allow apply to everyday and a span that overlaps the 24 hr offset days
		//TODO need to save timeblocks to the hist table 
		
		//reset time hour details
		lstNewTimeBlocks = TkServiceLocator.getTimeBlockService().resetTimeHourDetail(lstNewTimeBlocks);
		//apply any rules for this action
		lstNewTimeBlocks = TkServiceLocator.getTkRuleControllerService().applyRules(TkConstants.ACTIONS.ADD_TIME_BLOCK, lstNewTimeBlocks, tdaf.getPayCalendarDates());
		
		//call persist method that only saves added/deleted/changed timeblocks
		TkServiceLocator.getTimeBlockService().saveTimeBlocks(tdaf.getTimesheetDocument().getTimeBlocks(), lstNewTimeBlocks);
		//call history service
		
		return mapping.findForward("basic");
	}
}
