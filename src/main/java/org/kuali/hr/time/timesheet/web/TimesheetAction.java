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
import org.joda.time.DateTime;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.dao.AssignmentDaoSpringOjbImpl;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesheet.service.TimesheetService;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;

public class TimesheetAction extends TkAction {

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
		return forward;
	}

	private Map<Long,List<String>> getFormattedAssignment() {
		List<Job> jobs = TkServiceLocator.getJobSerivce().getJobs(TKContext.getUser().getPrincipalId(), TKUtils.getTimelessDate(null));

		Map<Long,List<String>> formattedAssignments = new LinkedHashMap<Long,List<String>>();
//		for(Job job : jobs) {
//
//			List<Assignment> assignments = job.getAssignments();
//			List<String> assignmentList = new LinkedList<String>();
//
//			if(assignments.size() > 0) {
//				for(Assignment assignment : assignments) {
//					try {
//
//						String workAreaDesc = assignment.getWorkAreaObj().getDescription();
//						// TODO: needs to grab the real value
//						String compRate = "CompRate";
//
//						String assignmentStr = workAreaDesc + " : " + compRate + " Rcd #" + job.getJobNumber().toString() + " " + job.getDept();
//						assignmentList.add(assignmentStr);
//
//					} catch(NullPointerException npe) {
//						LOG.error("one of the values is missing for the assignment " + npe.getMessage());
//					}
//				}
//			}
//			formattedAssignments.put(job.getJobNumber(), assignmentList);
//		}

		return formattedAssignments;
	}

	private Map<Long,List<String>> getFormattedDeptEarnCodes() {
//		List<Job> jobs = TkServiceLocator.getJobSerivce().getJobs(TKContext.getUser().getPrincipalId(), TKUtils.getTimelessDate(null));
		Map<Long,List<String>> formattedDeptEarnCodes = new LinkedHashMap<Long,List<String>>();
//		TODO fix this madness

//		for(Job job : jobs) {
//
//			List<DepartmentEarnCode> deptEarnCodes = job.getDeptEarnCodes();
//			List<String> earnCodeList = new LinkedList<String>();
//
//			if(deptEarnCodes.size() > 0) {
//				for(DepartmentEarnCode deptEarnCode : deptEarnCodes) {
//
//					try {
//
//						String earnCode = TkServiceLocator.getEarnCodeService().getEarnCodeById(deptEarnCode.getEarnCode()).getEarnCode();
//						String earnCodeDesc = TkServiceLocator.getEarnCodeService().getEarnCodeById(deptEarnCode.getEarnCodeId()).getDescription();
//
//						String earnCodeStr = earnCode + " : " + earnCodeDesc;
//						earnCodeList.add(earnCodeStr);
//					} catch(NullPointerException npe) {
//						LOG.error("can't find the dept earn code. id: " + deptEarnCode.getEarnCodeId());
//					}
//				}
//			}
//
//			formattedDeptEarnCodes.put(job.getJobNumber(), earnCodeList);
//		}

		return formattedDeptEarnCodes;

	}
}
