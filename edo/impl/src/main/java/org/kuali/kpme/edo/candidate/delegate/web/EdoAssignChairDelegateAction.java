package org.kuali.kpme.edo.candidate.delegate.web;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.role.EDORole;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoRule;
import org.kuali.kpme.edo.util.EdoUser;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class EdoAssignChairDelegateAction extends EdoAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        EdoAssignChairDelegateForm assignChairDelegateForm = (EdoAssignChairDelegateForm) form;
        assignChairDelegateForm.setEdoChairDelegatesInfo(EdoServiceLocator.getEdoMaintenanceService().getChairDelegateInfo(EdoUser.getCurrentTargetPerson().getPrincipalId(), EDORole.EDO_CHAIR_DELEGATE.getEdoRole()));
        return super.execute(mapping, form, request, response);
    }

    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        EdoAssignChairDelegateForm assignChairDelegateForm = (EdoAssignChairDelegateForm) form;

        String userId = assignChairDelegateForm.getUserId();
        if (StringUtils.isNotEmpty(userId)) {
            if (EdoRule.validateUserId(userId)) {
                EdoUser userInfo = EdoServiceLocator.getAuthorizationService().getEdoUser(userId);
                assignChairDelegateForm.setName(userInfo.getName());
                assignChairDelegateForm.setEmplId(userInfo.getEmplId());
            }
        }

        return mapping.findForward("basic");
    }

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        EdoAssignChairDelegateForm assignChairDelegateForm = (EdoAssignChairDelegateForm) form;

        if (StringUtils.isEmpty(assignChairDelegateForm.getUserId()) || StringUtils.isEmpty(assignChairDelegateForm.getName())) {
            return mapping.findForward("basic");
        } else {
            List<String> existingDelegates = EdoServiceLocator.getEdoMaintenanceService().getChairDelegatesEmplIds(EdoUser.getCurrentTargetPerson().getPrincipalId(), EDORole.EDO_CHAIR_DELEGATE.getEdoRole());
            if (existingDelegates.contains(assignChairDelegateForm.getEmplToAdd())) {
                GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.delegate.duplicate", assignChairDelegateForm.getName());
            } else {
                boolean isDelegateAdded = EdoServiceLocator.getEdoMaintenanceService().saveDelegateForChair(assignChairDelegateForm.getEmplToAdd(), assignChairDelegateForm.getStartDate(), assignChairDelegateForm.getEndDate(),  EdoUser.getCurrentTargetPerson().getPrincipalId(), EDORole.EDO_CHAIR_DELEGATE.getEdoRole());

                if (isDelegateAdded) {
                    assignChairDelegateForm.setChairDelegateAdded(isDelegateAdded);
                }
                assignChairDelegateForm.setEdoChairDelegatesInfo(EdoServiceLocator.getEdoMaintenanceService().getChairDelegateInfo(EdoUser.getCurrentTargetPerson().getPrincipalId(), EDORole.EDO_CHAIR_DELEGATE.getEdoRole()));
            }
            resetDelegateFormState(assignChairDelegateForm);

            return mapping.findForward("basic");
        }
    }

    public ActionForward deleteChairDelegate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoAssignChairDelegateForm assignChairDelegateForm = (EdoAssignChairDelegateForm) form;
        boolean isDelegateDeleted = false;

        isDelegateDeleted = EdoServiceLocator.getEdoMaintenanceService().deleteChairDelegate(assignChairDelegateForm.getEmplToDelete(), EdoUser.getCurrentTargetPerson().getPrincipalId(), EDORole.EDO_CHAIR_DELEGATE.getEdoRole());
        assignChairDelegateForm.setEdoChairDelegatesInfo(EdoServiceLocator.getEdoMaintenanceService().getChairDelegateInfo(EdoUser.getCurrentTargetPerson().getPrincipalId(), EDORole.EDO_CHAIR_DELEGATE.getEdoRole()));
        assignChairDelegateForm.setChairDelegateDeleted(isDelegateDeleted);

        return mapping.findForward("basic");
    }

    public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        EdoAssignChairDelegateForm assignChairDelegateForm = (EdoAssignChairDelegateForm) form;
        resetDelegateFormState(assignChairDelegateForm);

        return mapping.findForward("basic");
    }

    private void resetDelegateFormState(EdoAssignChairDelegateForm assignChairDelegateForm) {

        if (ObjectUtils.isNotNull(assignChairDelegateForm)) {
            assignChairDelegateForm.setUserId(null);
            assignChairDelegateForm.setName(null);
            assignChairDelegateForm.setStartDate(null);
            assignChairDelegateForm.setEndDate(null);
        }
    }

}

