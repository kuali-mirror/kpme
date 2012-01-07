package org.kuali.hr.time.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kew.web.UserLoginFilter;
import org.kuali.rice.kew.web.session.UserSession;
import org.kuali.rice.kns.exception.AuthorizationException;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.web.struts.action.KualiAction;

public class TkAction extends KualiAction {

    private static final Logger LOG = Logger.getLogger(TkAction.class);


    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String methodToCall = null;
            if (form instanceof TkForm) {
                methodToCall = ((TkForm)form).getMethodToCall();
            }
            checkTKAuthorization(form, methodToCall);
        } catch (AuthorizationException e) {
            LOG.error("User: " + TKContext.getPrincipalId() + " Target: " + TKContext.getTargetPrincipalId(), e);
            return mapping.findForward("unauthorized");
        }

        // Run our logic / security first - For some reason kuali
        // dispatches actions BEFORE checking the security...

        return super.execute(mapping, form, request, response);
    }

    /**
	 * Action to clear the current users back door setting.  Clears both
	 * workflow and TK backdoor settings.
	 */
	public ActionForward clearBackdoor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserSession userSession = UserLoginFilter.getUserSession(request);

		// There are two different UserSession objects in rice.
		// We will clear them both.
		if (userSession != null) {
			userSession.clearBackdoor();
			GlobalVariables.getUserSession().clearBackdoorUser();
		}

		TKUser tkUser = TKContext.getUser();
		if (tkUser != null) {
			tkUser.clearBackdoorUser();
		}

		return mapping.findForward("basic");
	}

	public ActionForward clearChangeUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserSession userSession = UserLoginFilter.getUserSession(request);

        String returnAction = (String)userSession.getObjectMap().get(TkConstants.TK_TARGET_USER_RETURN);
        if (returnAction == null) returnAction = "/PersonInfo.do";

        userSession.getObjectMap().remove(TkConstants.TK_TARGET_USER_PERSON);
        TKContext.getUser().clearTargetUser();

        return new ActionRedirect(returnAction);
	}

	public ActionForward userLogout(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TKContext.clear();
		request.getSession().invalidate();
		return new ActionRedirect(mapping.findForward("basic"));
	}

}
