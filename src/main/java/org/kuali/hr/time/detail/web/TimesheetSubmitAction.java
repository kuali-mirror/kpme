package org.kuali.hr.time.detail.web;


import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kew.service.WorkflowDocument;
import org.kuali.rice.kns.exception.AuthorizationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TimesheetSubmitAction extends TkAction {

    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        TimesheetSubmitActionForm tsaf = (TimesheetSubmitActionForm)form;

        String principal = TKContext.getPrincipalId();
        UserRoles roles = TKContext.getUser().getCurrentRoles();
        
        TimesheetDocument document = TkServiceLocator.getTimesheetService().getTimesheetDocument(tsaf.getDocumentId());
        if (!roles.isDocumentWritable(document)) {
            throw new AuthorizationException(principal, "TimesheetSubmitAction", "");
        }
    }
    
    
    
    
    public ActionForward approveTimesheet(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimesheetSubmitActionForm tsaf = (TimesheetSubmitActionForm)form;
        TimesheetDocument document = TkServiceLocator.getTimesheetService().getTimesheetDocument(tsaf.getDocumentId());

//        WorkflowDocument workflowDocument = new WorkflowDocument(TKContext.getTargetPrincipalId(), Long.parseLong(tsaf.getDocumentId()));
//        workflowDocument.setApplicationContent(TkServiceLocator.getTkSearchableAttributeService().createSearchableAttributeXml(document));
//        workflowDocument.saveDocument("");
        // Switched to grab the target (chain, resolution: target -> backdoor -> actual) user.
        // Approvals still using backdoor > actual
        if (StringUtils.equals(tsaf.getAction(), TkConstants.TIMESHEET_ACTIONS.ROUTE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals("I")) {
                TkServiceLocator.getTimesheetService().routeTimesheet(TKContext.getTargetPrincipalId(), document);
            }
        } else if (StringUtils.equals(tsaf.getAction(), TkConstants.TIMESHEET_ACTIONS.APPROVE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals("R")) {
                TkServiceLocator.getTimesheetService().approveTimesheet(TKContext.getPrincipalId(), document);
            }
        } else if (StringUtils.equals(tsaf.getAction(), TkConstants.TIMESHEET_ACTIONS.DISAPPROVE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals("R")) {
                TkServiceLocator.getTimesheetService().disapproveTimesheet(TKContext.getPrincipalId(), document);
            }
        }

        return new ActionRedirect(mapping.findForward("timesheetRedirect"));

    }
    
    public ActionForward approveApprovalTab(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	TimesheetSubmitActionForm tsaf = (TimesheetSubmitActionForm)form;
        TimesheetDocument document = TkServiceLocator.getTimesheetService().getTimesheetDocument(tsaf.getDocumentId());

        // Switched to grab the target (chain, resolution: target -> backdoor -> actual) user.
        // Approvals still using backdoor > actual
        if (StringUtils.equals(tsaf.getAction(), TkConstants.TIMESHEET_ACTIONS.ROUTE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals("I")) {
                TkServiceLocator.getTimesheetService().routeTimesheet(TKContext.getTargetPrincipalId(), document);
            }
        } else if (StringUtils.equals(tsaf.getAction(), TkConstants.TIMESHEET_ACTIONS.APPROVE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals("R")) {
                TkServiceLocator.getTimesheetService().approveTimesheet(TKContext.getPrincipalId(), document);
            }
        } else if (StringUtils.equals(tsaf.getAction(), TkConstants.TIMESHEET_ACTIONS.DISAPPROVE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals("R")) {
                TkServiceLocator.getTimesheetService().disapproveTimesheet(TKContext.getPrincipalId(), document);
            }
        }
        TKContext.getUser().clearTargetUserFromSession();
        return new ActionRedirect(mapping.findForward("approverRedirect"));

    	
    }
}
