package org.kuali.hr.time.detail.web;


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
        String timesheetDocumentId = TKContext.getCurrentTimesheetDocumentId();
        String principal = TKContext.getPrincipalId();
        TimesheetDocument document = TkServiceLocator.getTimesheetService().getTimesheetDocument(timesheetDocumentId);
        if (document.getDocumentHeader().getDocumentStatus().equals("I")) {
            // Route if in initiated status.
            TkServiceLocator.getTimesheetService().routeTimesheet(principal, document);
        }

        return new ActionRedirect(mapping.findForward("timesheetRedirect"));
    }
}
