package org.kuali.hr.time.detail.web;


import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TimesheetSubmitAction extends TkAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        String timesheetDocumentId = TKContext.getCurrentTimesheetDocumentId();
        String principal = TKContext.getPrincipalId();
        TimesheetDocument document = TkServiceLocator.getTimesheetService().getTimesheetDocument(timesheetDocumentId);

        if (StringUtils.equals(action, TkConstants.TIMESHEET_ACTIONS.ROUTE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals("I")) {
                TkServiceLocator.getTimesheetService().routeTimesheet(principal, document);
            }
        } else if (StringUtils.equals(action, TkConstants.TIMESHEET_ACTIONS.APPROVE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals("R")) {
                TkServiceLocator.getTimesheetService().approveTimesheet(principal, document);
            }
        } else if (StringUtils.equals(action, TkConstants.TIMESHEET_ACTIONS.DISAPPROVE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals("R")) {
                TkServiceLocator.getTimesheetService().disapproveTimesheet(principal, document);
            }
        }

        return new ActionRedirect(mapping.findForward("timesheetRedirect"));
    }
}
