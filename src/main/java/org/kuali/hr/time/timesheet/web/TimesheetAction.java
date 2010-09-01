package org.kuali.hr.time.timesheet.web;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.service.TimesheetService;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;

public class TimesheetAction extends TkAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = super.execute(mapping, form, request, response);
		TimesheetActionForm taForm = (TimesheetActionForm)form;

		TKUser user = TKContext.getUser();
		TimesheetService timesheetService = TkServiceLocator.getTimesheetService();

		Date payEndDate = TKUtils.getPayEndDate(user, (new DateTime()).toDate());
		// TODO : Re-enable these
		//TimesheetDocument timesheetDocument = timesheetService.openTimesheetDocument(user.getPrincipalId(), payEndDate);
		//taForm.setTimesheetDocument(timesheetDocument);

		return forward;
	}

	protected List<String> getFormattedAssignment() {
		List<Job> jobs = TkServiceLocator.getJobSerivce().getJobs(TKContext.getUser().getPrincipalId());

		List<String> formattedAssignments = new LinkedList<String>();
		for(Job job : jobs) {

			List<Assignment> assignments = job.getAssignments();

			for(Assignment assignment : assignments) {
				String workAreaDesc = assignment.getWorkArea().getDescription();
				// TODO: needs to grab the real value
				String compRate = "CompRate";

				String assignmentForOutput = workAreaDesc + " : " + compRate + " Rcd #" + job.getJobNumber().toString() + " " + job.getDeptId();
				formattedAssignments.add(assignmentForOutput);
			}
		}

		return formattedAssignments;
	}

}
