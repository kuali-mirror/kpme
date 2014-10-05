package org.kuali.kpme.edo.admin.web;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.candidate.EdoSelectedCandidate;
import org.kuali.kpme.edo.util.EdoUser;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EdoChangeTargetPersonAction extends EdoAction {

    public ActionForward changeTargetPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ActionForward forward = mapping.findForward("basic");

        EdoChangeTargetPersonForm changeTargetPersonForm = (EdoChangeTargetPersonForm) form;

        if (StringUtils.isNotBlank(changeTargetPersonForm.getPrincipalName())) {
            Person targetPerson = KimApiServiceLocator.getPersonService().getPersonByPrincipalName(changeTargetPersonForm.getPrincipalName());

            if (targetPerson != null) {
                EdoUser.setTargetPerson(targetPerson);

                String returnAction = "EdoIndex.do";
                if (StringUtils.isNotEmpty(changeTargetPersonForm.getTargetUrl())) {
                    returnAction = changeTargetPersonForm.getTargetUrl();
                }
                forward = new ActionRedirect(returnAction);
            } else {
                GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.userInfObject.null");
            }
        }

        return forward;
    }

    public ActionForward clearTargetPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        EdoUser.clearTargetUser();

        String returnAction = null;
        if (StringUtils.isNotBlank((String) GlobalVariables.getUserSession().retrieveObject(EdoConstants.EDO_TARGET_USER_RETURN))) {
            returnAction = (String) GlobalVariables.getUserSession().retrieveObject(EdoConstants.EDO_TARGET_USER_RETURN);
        }

        if (StringUtils.isBlank(returnAction)) {
            returnAction = "changeTargetPerson.do";
        }
        //clearing the session variables
        HttpSession ssn = request.getSession();
        ssn.setAttribute("selectedCandidate", new EdoSelectedCandidate());

        return new ActionRedirect(returnAction);
    }
}