package org.kuali.hr.time.detail.web;

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
import org.kuali.hr.time.timesheet.web.TimesheetAction;
import org.kuali.hr.time.util.TkConstants;

public class TimeDetailAction extends TimesheetAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = super.execute(mapping, form, request, response);
		TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
		
		tdaf.setBeginPeriodDate(tdaf.getPayCalendarDates().getBeginPeriodDate());
		tdaf.setEndPeriodDate(tdaf.getPayCalendarDates().getEndPeriodDate());

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
						earnCodeString += "<option value='" + earnCode.getEarnCode() + "'>" + earnCode.getEarnCode() + " : " + earnCode.getDescription() + "</option>";
					}
				}
			}
		}
		
		tdaf.setOutputString(earnCodeString);
		return mapping.findForward("ws");
	}

	public ActionForward getTimeblocks(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		TimeDetailActionForm timeDetailForm = (TimeDetailActionForm) form;
		
		List<Map<String,Object>> timeBlockList = TkServiceLocator.getTimeBlockService().getTimeBlocksForOurput(timeDetailForm);
		timeDetailForm.setOutputString(JSONValue.toJSONString(timeBlockList));
		
		return mapping.findForward("ws");
	}

	public ActionForward deleteTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// the corresponding js code resides in the fullcalendar-1.4.7.js somewhere around #1664
		TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
		tdaf.setTimeBlock(TkServiceLocator.getTimeBlockService().deleteTimeBlock(tdaf));
		tdaf.setClockAction(TkConstants.DELETE);
		TkServiceLocator.getTimeBlockHistoryService().saveTimeBlockHistory(tdaf);

		return mapping.findForward("basic");
	}

	public ActionForward addTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
		tdaf.setClockAction(TkConstants.ADD);

		if(StringUtils.equals(tdaf.getAcrossDays(),"y")) {
			tdaf.setTimeBlockList(TkServiceLocator.getTimeBlockService().saveTimeBlockList(tdaf));
			TkServiceLocator.getTimeBlockHistoryService().saveTimeBlockHistoryList(tdaf);
		}
		else {
	    	tdaf.setTimeBlock(TkServiceLocator.getTimeBlockService().saveTimeBlock(tdaf));
	    	TkServiceLocator.getTimeBlockHistoryService().saveTimeBlockHistory(tdaf);
		}

		return mapping.findForward("basic");
	}
}
