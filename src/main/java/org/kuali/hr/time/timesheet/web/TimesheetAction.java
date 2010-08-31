package org.kuali.hr.time.timesheet.web;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
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

}
