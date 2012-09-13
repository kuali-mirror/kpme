package org.kuali.hr.lm.leavecalendar.web;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LeaveCalendarSubmitAction extends TkAction {
    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        LeaveCalendarSubmitForm lcf = (LeaveCalendarSubmitForm)form;

        String principal = TKContext.getPrincipalId();
        UserRoles roles = TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId());

        LeaveCalendarDocument document = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(lcf.getDocumentId());
        if (!roles.isDocumentWritable(document)) {
            throw new AuthorizationException(principal, "LeaveCalendarSubmitAction", "");
        }
    }




    public ActionForward approveLeaveCalendar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LeaveCalendarSubmitForm lcf = (LeaveCalendarSubmitForm)form;
        LeaveCalendarDocument document = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(lcf.getDocumentId());

        // Switched to grab the target (chain, resolution: target -> backdoor -> actual) user.
        // Approvals still using backdoor > actual
        if (StringUtils.equals(lcf.getAction(), TkConstants.DOCUMENT_ACTIONS.ROUTE)) {
            if (DocumentStatus.INITIATED.getCode().equals(document.getDocumentHeader().getDocumentStatus())
                    || DocumentStatus.SAVED.getCode().equals(document.getDocumentHeader().getDocumentStatus())) {
                TkServiceLocator.getLeaveCalendarService().routeLeaveCalendar(TKContext.getTargetPrincipalId(), document);
            }
        } else if (StringUtils.equals(lcf.getAction(), TkConstants.DOCUMENT_ACTIONS.APPROVE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals("R")) {
                TkServiceLocator.getLeaveCalendarService().approveLeaveCalendar(TKContext.getPrincipalId(), document);
            }
        } else if (StringUtils.equals(lcf.getAction(), TkConstants.DOCUMENT_ACTIONS.DISAPPROVE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals("R")) {
                TkServiceLocator.getLeaveCalendarService().disapproveLeaveCalendar(TKContext.getPrincipalId(), document);
            }
        }

        TkServiceLocator.getTkSearchableAttributeService().updateSearchableAttribute(document, document.getAsOfDate());
        ActionRedirect rd = new ActionRedirect(mapping.findForward("leaveCalendarRedirect"));
        rd.addParameter("documentId", lcf.getDocumentId());

        return rd;
    }

    public ActionForward approveApprovalTab(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LeaveCalendarSubmitForm lcf = (LeaveCalendarSubmitForm)form;
        LeaveCalendarDocument document = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(lcf.getDocumentId());

        // Switched to grab the target (chain, resolution: target -> backdoor -> actual) user.
        // Approvals still using backdoor > actual
        if (StringUtils.equals(lcf.getAction(), TkConstants.DOCUMENT_ACTIONS.ROUTE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals("I")) {
                TkServiceLocator.getLeaveCalendarService().routeLeaveCalendar(TKContext.getTargetPrincipalId(), document);
            }
        } else if (StringUtils.equals(lcf.getAction(), TkConstants.DOCUMENT_ACTIONS.APPROVE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals("R")) {
                TkServiceLocator.getLeaveCalendarService().approveLeaveCalendar(TKContext.getPrincipalId(), document);
            }
        } else if (StringUtils.equals(lcf.getAction(), TkConstants.DOCUMENT_ACTIONS.DISAPPROVE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals("R")) {
                TkServiceLocator.getLeaveCalendarService().disapproveLeaveCalendar(TKContext.getPrincipalId(), document);
            }
        }
        TKContext.getUser().clearTargetUser();
        return new ActionRedirect(mapping.findForward("approverRedirect"));


    }

}
