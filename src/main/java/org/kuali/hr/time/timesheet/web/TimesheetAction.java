package org.kuali.hr.time.timesheet.web;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
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

		//Date payEndDate = TKUtils.getPayEndDate(user, (new DateTime()).toDate());
		
		// TODO : Re-enable these
		//TimesheetDocument timesheetDocument = timesheetService.openTimesheetDocument(user.getPrincipalId(), payEndDate);
		//taForm.setTimesheetDocument(timesheetDocument);

		// set assignments
		taForm.setFormattedAssignments(getFormattedAssignment());
		// set earn codes
		taForm.setDeptEarnCode(getFormattedDeptEarnCodes());

		return forward;
	}

	private Map<Long,List<String>> getFormattedAssignment() {
		List<Job> jobs = TkServiceLocator.getJobSerivce().getJobs(TKContext.getUser().getPrincipalId(), TKUtils.getTimelessDate(null));

		Map<Long,List<String>> formattedAssignments = new LinkedHashMap<Long,List<String>>();
		for(Job job : jobs) {

			List<Assignment> assignments = job.getAssignments();
			List<String> assignmentList = new LinkedList<String>();

			for(Assignment assignment : assignments) {
				String workAreaDesc = assignment.getWorkArea().getDescription();
				// TODO: needs to grab the real value
				String compRate = "CompRate";

				String assignmentStr = workAreaDesc + " : " + compRate + " Rcd #" + job.getJobNumber().toString() + " " + job.getDeptId();
				assignmentList.add(assignmentStr);
			}

			formattedAssignments.put(job.getJobNumber(), assignmentList);
		}

		return formattedAssignments;
	}

	private Map<Long,List<String>> getFormattedDeptEarnCodes() {
		List<Job> jobs = TkServiceLocator.getJobSerivce().getJobs(TKContext.getUser().getPrincipalId(), TKUtils.getTimelessDate(null));

		Map<Long,List<String>> formattedDeptEarnCodes = new LinkedHashMap<Long,List<String>>();
		for(Job job : jobs) {

			List<DepartmentEarnCode> deptEarnCodes = job.getDeptEarnCodes();
			List<String> earnCodeList = new LinkedList<String>();

			for(DepartmentEarnCode deptEarnCode : deptEarnCodes) {
				String earnCode = TkServiceLocator.getEarnCodeService().getEarnCodeById(deptEarnCode.getEarnCodeId()).getEarnCode();
				String earnCodeDesc = TkServiceLocator.getEarnCodeService().getEarnCodeById(deptEarnCode.getEarnCodeId()).getDescription();

				String earnCodeStr = earnCode + " : " + earnCodeDesc;
				earnCodeList.add(earnCodeStr);
			}

			formattedDeptEarnCodes.put(job.getJobNumber(), earnCodeList);
		}

		return formattedDeptEarnCodes;

	}
}
