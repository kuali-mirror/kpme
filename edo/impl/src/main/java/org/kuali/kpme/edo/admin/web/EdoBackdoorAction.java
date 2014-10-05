package org.kuali.kpme.edo.admin.web;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.base.web.EdoLoginFilter;
import org.kuali.kpme.edo.util.EdoContext;
import org.kuali.kpme.edo.util.EdoRule;
import org.kuali.kpme.edo.candidate.EdoSelectedCandidate;
import org.kuali.kpme.edo.util.EdoUser;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.exception.RiceRuntimeException;
import org.kuali.rice.kim.api.permission.Permission;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EdoBackdoorAction extends EdoAction {

    private static final Logger LOG = Logger.getLogger(EdoBackdoorAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        return super.execute(mapping, form, request, response);
    }

    public ActionForward changeBackdoor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserSession uSession = GlobalVariables.getUserSession();
        EdoBackdoorForm backdoorForm = (EdoBackdoorForm) form;
        String returnAction = "EdoIndex.do";
        if (StringUtils.isNotBlank(backdoorForm.getPrincipalName())) {
            //validate the userid
            if (EdoRule.validateUserId(backdoorForm.getPrincipalName())) {

                // first thing is make sure this is a valid action for this user
                /*if(!isBackdoorAuthorized(uSession, request)){
                    request.setAttribute("badbackdoor", "User (" + backdoorForm.getPrincipalName() + ") not permitted to use backdoor functionality inside application: '" + ConfigContext.getCurrentContextConfig().getProperty("app.code") + "'.");
                    return clearBackdoor(mapping, form, request, response);
                }*/

                //if backdoor Id is empty or equal to currently logged in user, clear backdoor id
                if (uSession.isBackdoorInUse() &&
                            (StringUtils.isEmpty(backdoorForm.getPrincipalName())
                                     || uSession.getLoggedInUserPrincipalName().equals(backdoorForm.getPrincipalName()))) {
                    return clearBackdoor(mapping, form, request, response);
                }

                try {
                    uSession.setBackdoorUser(backdoorForm.getPrincipalName());
                } catch (RiceRuntimeException e) {
                    LOG.warn("invalid backdoor id " + backdoorForm.getPrincipalName(), e);
                    //Commenting this out since it is not being read anywhere
                    //request.setAttribute("badbackdoor", "Invalid backdoor Id given '" + backdoorForm.getBackdoorId() + "'");
                    return new ActionRedirect("EdoGenAdminHome.do");
                }

                // setFormGroupPermission(backdoorForm, request);


                if (StringUtils.isNotEmpty(backdoorForm.getTargetUrl())) {
                    returnAction = backdoorForm.getTargetUrl();
                }

                EdoUser edoUser = EdoServiceLocator.getAuthorizationService().getEdoUser(backdoorForm.getPrincipalName());

                EdoContext.setUser(edoUser);
                //clearing the session variables
                HttpSession ssn = request.getSession();
                ssn.setAttribute("selectedCandidate", new EdoSelectedCandidate());
            } else {
                return mapping.findForward("basic");
            }
        }

        return new ActionRedirect(returnAction);
    }

    public ActionForward clearBackdoor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        LOG.debug("logout");

        //String returnAction = "EdoGenAdminHome.do";
        String returnAction = "EdoGenAdminHome.do?tabId=gadmin&nid=gadmin_0_0";

        UserSession uSession = GlobalVariables.getUserSession();

        if (uSession.isBackdoorInUse()) {
            uSession.clearBackdoorUser();
            // setFormGroupPermission((EdoBackdoorForm)form, request);
            //request.setAttribute("reloadPage","true");

            org.kuali.rice.krad.UserSession KnsUserSession;
            KnsUserSession = GlobalVariables.getUserSession();
            KnsUserSession.clearBackdoorUser();
        }
        EdoUser edoUser = null;

        edoUser = EdoServiceLocator.getAuthorizationService().getEdoUser(EdoLoginFilter.getRemoteUser(request));

        EdoContext.setUser(edoUser);
        //clearing the session variables
        HttpSession ssn = request.getSession();
        ssn.setAttribute("selectedCandidate", new EdoSelectedCandidate());


        return new ActionRedirect(returnAction);
    }

    /*private void setFormGroupPermission(EdoBackdoorForm backdoorForm, HttpServletRequest request) {
        // based on whether or not they have permission to use the fictional "AdministrationAction", kind of a hack for now since I don't have time to
        // split this single action up and I can't pass the methodToCall to the permission check
        Map<String, String> permissionDetails = new HashMap<String, String>();
        permissionDetails.put(KimConstants.AttributeConstants.NAMESPACE_CODE, KewApiConstants.KEW_NAMESPACE);
        permissionDetails.put(KimConstants.AttributeConstants.ACTION_CLASS, "org.kuali.rice.kew.web.backdoor.AdministrationAction");
        boolean isAdmin = KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(GlobalVariables.getUserSession()
                .getPrincipalId(), KRADConstants.KNS_NAMESPACE, KimConstants.PermissionTemplateNames.USE_SCREEN,
                permissionDetails, new HashMap<String, String>());
        backdoorForm.setIsAdmin(isAdmin);
    }*/

    public boolean isBackdoorAuthorized(UserSession uSession, HttpServletRequest request) {
        boolean isAuthorized = true;

        //we should check to see if a kim permission exists for the requested application first
        Map<String, String> permissionDetails = new HashMap<String, String>();
        String requestAppCode = ConfigContext.getCurrentContextConfig().getProperty("app.code");
        permissionDetails.put("appCode", requestAppCode);
        List<Permission> perms = KimApiServiceLocator.getPermissionService().findPermissionsByTemplate(KRADConstants.KUALI_RICE_SYSTEM_NAMESPACE, "Backdoor Restriction");
        for (Permission kpi : perms) {
            if (kpi.getAttributes().values().contains(requestAppCode)) {

                //if a permission exists, is the user granted permission to use backdoor?
                boolean tempcheck = KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(uSession.getActualPerson().getPrincipalId(), KRADConstants.KUALI_RICE_SYSTEM_NAMESPACE, "Backdoor Restriction", permissionDetails, Collections.<String, String>emptyMap());
                isAuthorized = tempcheck;
            }
        }
        if (!isAuthorized) {
            LOG.warn("Attempt to backdoor was made by user: " + uSession.getPerson().getPrincipalId() + " into application with app.code: " + requestAppCode + " but they do not have appropriate permissions. Backdoor processing aborted.");
        }
        return isAuthorized;
    }

}

