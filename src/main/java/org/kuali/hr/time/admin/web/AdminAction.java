package org.kuali.hr.time.admin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.user.service.UserServiceImpl;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.web.TKRequestProcessor;
import org.kuali.rice.kew.web.UserLoginFilter;
import org.kuali.rice.kew.web.session.UserSession;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.exception.AuthorizationException;
import org.kuali.rice.kns.util.GlobalVariables;

public class AdminAction extends TkAction {

	private static final Logger LOG = Logger.getLogger(AdminAction.class);

    @Override
    protected void checkAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        TKUser user = TKContext.getUser();
        if (user == null || !user.getCurrentRoles().isSystemAdmin()) {
            throw new AuthorizationException("", "", "");
        }
    }

    public ActionForward backdoor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AdminActionForm adminForm = (AdminActionForm) form;
        TKUser tkUser = TKContext.getUser();

        if (tkUser.getCurrentRoles().isSystemAdmin()) {
            if (StringUtils.isNotBlank(adminForm.getBackdoorPrincipalName())) {

                Person backdoorPerson = KIMServiceLocator.getPersonService().getPersonByPrincipalName(adminForm.getBackdoorPrincipalName());

                if (backdoorPerson != null && tkUser != null) {
                    UserSession userSession = UserLoginFilter.getUserSession(request);

                    userSession.establishBackdoorWithPrincipalName(backdoorPerson.getPrincipalId());
                    GlobalVariables.getUserSession().setBackdoorUser(backdoorPerson.getPrincipalId());

                    tkUser.setBackdoorPerson(backdoorPerson);

                    UserServiceImpl.loadRoles(tkUser);
                    TKContext.setUser(tkUser);

                    LOG.debug("\n\n" + TKContext.getUser().getActualPerson().getPrincipalName() + " backdoors as : " + backdoorPerson.getPrincipalName() + "\n\n");
                }

            }
        } else {
            LOG.warn("Non-Admin user attempting to backdoor.");
            return mapping.findForward("unauthorized");
        }

		return mapping.findForward("basic");
	}

    public ActionForward changeEmployee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AdminActionForm adminForm = (AdminActionForm) form;
        TKUser tkUser = TKContext.getUser();

        if (tkUser.getCurrentRoles().isSystemAdmin()) {
            if (StringUtils.isNotBlank(adminForm.getBackdoorPrincipalName())) {

                Person backdoorPerson = KIMServiceLocator.getPersonService().getPersonByPrincipalName(adminForm.getBackdoorPrincipalName());

                if (backdoorPerson != null && tkUser != null) {
                    UserSession userSession = UserLoginFilter.getUserSession(request);

                    userSession.establishBackdoorWithPrincipalName(backdoorPerson.getPrincipalId());
                    GlobalVariables.getUserSession().setBackdoorUser(backdoorPerson.getPrincipalId());

                    tkUser.setBackdoorPerson(backdoorPerson);

                    UserServiceImpl.loadRoles(tkUser);
                    TKContext.setUser(tkUser);

                    LOG.debug("\n\n" + TKContext.getUser().getActualPerson().getPrincipalName() + " change employee as : " + backdoorPerson.getPrincipalName() + "\n\n");
                }

            }
        } else {
            LOG.warn("Non-Admin user attempting to backdoor.");
            return mapping.findForward("unauthorized");
        }



    	return mapping.findForward("basic");
    }

    public ActionForward deleteTimesheet(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	AdminActionForm adminForm = (AdminActionForm) form;
    	String documentId = adminForm.getDeleteDocumentId();
    	if(StringUtils.isNotBlank(documentId)){
    		TkServiceLocator.getTimesheetService().deleteTimesheet(documentId);
}
    	return mapping.findForward("basic");
    }
}
