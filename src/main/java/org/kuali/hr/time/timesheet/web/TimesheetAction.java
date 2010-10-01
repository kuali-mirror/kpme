package org.kuali.hr.time.timesheet.web;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.base.web.TkAction;
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
		TimesheetActionForm taForm = (TimesheetActionForm)form;
		
		TKUser user = TKContext.getUser();
		Date currentDate = TKUtils.getTimelessDate(null);
		
		List<Job> jobs = TkServiceLocator.getJobSerivce().getJobs(user.getPrincipalId(), currentDate);
		if (jobs.size() < 1)
			throw new RuntimeException("No jobs for a user.");
		
		PayCalendarDates payCalendarDates = TkServiceLocator.getPayCalendarSerivce().getCurrentPayCalendarDates(user.getPrincipalId(), jobs.get(0), currentDate);
		taForm.setPayCalendarDates(payCalendarDates);
	
		TimesheetDocument tdoc = TkServiceLocator.getTimesheetService().openTimesheetDocument(user.getPrincipalId(), payCalendarDates);
		taForm.setTimesheetDocument(tdoc);
		
		taForm.setAssignmentDescriptions(TkServiceLocator.getAssignmentService().getAssignmentDescriptions(tdoc));
		return super.execute(mapping, form, request, response);
	}

}
