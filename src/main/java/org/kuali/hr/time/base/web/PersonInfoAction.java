package org.kuali.hr.time.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.timesheet.web.TimesheetAction;

public class PersonInfoAction extends TimesheetAction {

    public ActionForward showInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	return mapping.findForward("basic");
    }
}
