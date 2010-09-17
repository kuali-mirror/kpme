package org.kuali.hr.time.timesheet.web;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;

public class TimesheetAction extends TkAction {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(TimesheetAction.class);

	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = super.execute(mapping, form, request, response);
		TimesheetActionForm taForm = (TimesheetActionForm)form;
		
		TKUser user = TKContext.getUser();
		Date currentDate = TKUtils.getTimelessDate(null);
		
		List<Job> jobs = TkServiceLocator.getJobSerivce().getJobs(user.getPrincipalId(), currentDate);
		if (jobs.size() < 1)
			throw new RuntimeException("No jobs for a user.");
		PayCalendarDates payCalendarDates = TkServiceLocator.getPayCalendarSerivce().getCurrentPayCalendarDates(user.getPrincipalId(), jobs.get(0), currentDate);
	
		TimesheetDocument tdoc = TkServiceLocator.getTimesheetService().openTimesheetDocument(user.getPrincipalId(), payCalendarDates);
		taForm.setTimesheetDocument(tdoc);
		
		taForm.setAssignmentDescriptions(getAssignmentDescriptions(tdoc));
		// only get the earn codes for the first assignment when the form is loaded
//		taForm.setEarnCodesDescriptions(getEarnCodeDescriptions(tdoc.getAssignments().get(0)));
		
		return forward;
	}

	protected Map<String,String> getAssignmentDescriptions(TimesheetDocument td) {
		List<Assignment> assignments = td.getAssignments();
		Map<String,String> assignmentDescriptions = new LinkedHashMap<String,String>();
		
		if(assignments.size() < 1) {
			throw new RuntimeException("No assignment on the timesheet document.");
		}
		
		for(Assignment assignment : assignments) {
			String assignmentDescKey  = assignment.getJobNumber() + ":" + assignment.getWorkArea() + ":" + assignment.getTask();
			// TODO: need to use real values
			String assignmentDescValue =  "workAreaDesc : compRate Rcd #1 BL-UITS";
			assignmentDescriptions.put(assignmentDescKey, assignmentDescValue);
		}
		
		return assignmentDescriptions;
	}

	protected Map<Long,String> getEarnCodeDescriptions(Assignment a) {
		List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService().getEarnCodes(a);
		
		if(earnCodes.size() < 1) {
			throw new RuntimeException("No earn codes for assignment : " + a.getTkAssignmentId());
		}
		
		Map<Long,String> earnCodeDescriptions = new LinkedHashMap<Long,String>();

		for(EarnCode earnCode : earnCodes) {
			earnCodeDescriptions.put(a.getJobNumber(),earnCode.getDescription());
		}
		
		return earnCodeDescriptions;
	}
}
