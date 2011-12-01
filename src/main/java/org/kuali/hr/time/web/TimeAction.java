package org.kuali.hr.time.web;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.kuali.hr.time.admin.web.AdminActionForm;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.rice.kns.exception.AuthorizationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TimeAction extends TkAction {

	private static final Logger LOG = Logger.getLogger(TimeAction.class);

    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        TKUser user = TKContext.getUser();
        AdminActionForm adminForm = (AdminActionForm) form;

        if (StringUtils.equals(methodToCall, "targetEmployee") || StringUtils.equals(methodToCall, "changeEmployee") || StringUtils.equals(methodToCall, "clearBackdoor") || StringUtils.equals(methodToCall, "clearChangeUser")) {
            // Handle security validation in targetEmployee action, we may need
            // to check the document for validity, since the user may not
            // necessarily be a system administrator.
        } else {
            if (user == null ||
            		(!user.isSystemAdmin()
            			&& !user.isLocationAdmin()
            			&& !user.isDepartmentAdmin()
            			&& !user.isGlobalViewOnly()
            			&& !user.isDepartmentViewOnly()
            			&& (adminForm.getDocumentId() != null && !user.getCurrentRoles().isApproverForTimesheet(adminForm.getDocumentId()))
            			&& (adminForm.getDocumentId() != null && !user.getCurrentRoles().isDocumentReadable(adminForm.getDocumentId()))
            		))  {
                throw new AuthorizationException("", "AdminAction", "");
            }
        }
    }

    
    @Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
    	TKUser user = TKContext.getUser();
		if (user != null) {
			if (user.isSystemAdmin()) {
				return new ActionRedirect("/Admin.do");
			} else if (user.isDepartmentAdmin()
					&& !user.getCurrentRoles().isSynchronous()) {
				return new ActionRedirect("/Admin.do");
			} else if (user.isApprover()
					&& !user.getCurrentRoles().isSynchronous()) {
				return new ActionRedirect("/TimeApproval.do");
			} else if (user.isReviewer()
					&& !user.getCurrentRoles().isSynchronous()) {
				return new ActionRedirect("/TimeApproval.do");
			} else if (user.getCurrentRoles().isActiveEmployee()
					&& !user.getCurrentRoles().isSynchronous()) {
				return new ActionRedirect("/TimeDetail.do");
			} else if (user.getCurrentRoles().isSynchronous()) {
				return new ActionRedirect("/Clock.do");
			} else {
				return new ActionRedirect("/PersonInfo.do");
			}
		}
	return super.execute(mapping, form, request, response);
}
    
}
