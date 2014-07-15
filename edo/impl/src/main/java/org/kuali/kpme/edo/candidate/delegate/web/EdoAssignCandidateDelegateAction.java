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


public class EdoAssignCandidateDelegateAction extends EdoAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        EdoAssignCandidateDelegateForm assignCandidateDelegateForm = (EdoAssignCandidateDelegateForm) form;

        assignCandidateDelegateForm.setEdoCandidateDelegatesInfo(EdoServiceLocator.getEdoMaintenanceService().getCandidateDelegateInfo(EdoUser.getCurrentTargetPerson().getPrincipalId(), EDORole.EDO_CANDIDATE_DELEGATE.getEdoRole()));
        return super.execute(mapping, form, request, response);
    }

    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        EdoAssignCandidateDelegateForm assignCandidateDelegateForm = (EdoAssignCandidateDelegateForm) form;

        String userId = assignCandidateDelegateForm.getUserId();
        if (StringUtils.isNotEmpty(userId)) {
            if (EdoRule.validateUserId(userId)) {
                EdoUser userInfo = EdoServiceLocator.getAuthorizationService().getEdoUser(userId);
                assignCandidateDelegateForm.setName(userInfo.getName());
                assignCandidateDelegateForm.setEmplId(userInfo.getEmplId());
            }
        }

        return mapping.findForward("basic");
    }

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoAssignCandidateDelegateForm assignCandidateDelegateForm = (EdoAssignCandidateDelegateForm) form;

        if (StringUtils.isEmpty(assignCandidateDelegateForm.getUserId()) || StringUtils.isEmpty(assignCandidateDelegateForm.getName())) {
            return mapping.findForward("basic");
        } else {
            List<String> existingDelegates = EdoServiceLocator.getEdoMaintenanceService().getCandidateDelegatesEmplIds(EdoUser.getCurrentTargetPerson().getPrincipalId(), EDORole.EDO_CANDIDATE_DELEGATE.getEdoRole());
            if (existingDelegates.contains(assignCandidateDelegateForm.getEmplToAdd())) {
                GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.delegate.duplicate", assignCandidateDelegateForm.getName());
            } else {
                boolean isDelegateAdded = EdoServiceLocator.getEdoMaintenanceService().saveDelegateForCandidate(assignCandidateDelegateForm.getEmplToAdd(), assignCandidateDelegateForm.getStartDate(), assignCandidateDelegateForm.getEndDate(),  EdoUser.getCurrentTargetPerson().getPrincipalId(), EDORole.EDO_CANDIDATE_DELEGATE.getEdoRole());

                if (isDelegateAdded) {
                    assignCandidateDelegateForm.setCandidateDelegateAdded(isDelegateAdded);
                }
                //	assignCandidateDelegateForm.setCandidateDelegates(EdoServiceLocator.getEdoMaintenanceService().getCandidateDelegates(EdoUser.getCurrentTargetPerson().getPrincipalId()));
                assignCandidateDelegateForm.setEdoCandidateDelegatesInfo(EdoServiceLocator.getEdoMaintenanceService().getCandidateDelegateInfo(EdoUser.getCurrentTargetPerson().getPrincipalId(), EDORole.EDO_CANDIDATE_DELEGATE.getEdoRole()));
            }
            resetDelegateFormState(assignCandidateDelegateForm);
            return mapping.findForward("basic");
        }
    }

    public ActionForward deleteFacultyDelegate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoAssignCandidateDelegateForm assignCandidateDelegateForm = (EdoAssignCandidateDelegateForm) form;
        boolean isDelegateDeleted = false;

        isDelegateDeleted = EdoServiceLocator.getEdoMaintenanceService().deleteCandidateDelegate(assignCandidateDelegateForm.getEmplToDelete(), EdoUser.getCurrentTargetPerson().getPrincipalId(), EDORole.EDO_CANDIDATE_DELEGATE.getEdoRole());
        //assignCandidateDelegateForm.setCandidateDelegates(EdoServiceLocator.getEdoMaintenanceService().getCandidateDelegates(EdoUser.getCurrentTargetPerson().getPrincipalId()));
        assignCandidateDelegateForm.setEdoCandidateDelegatesInfo(EdoServiceLocator.getEdoMaintenanceService().getCandidateDelegateInfo(EdoUser.getCurrentTargetPerson().getPrincipalId(), EDORole.EDO_CANDIDATE_DELEGATE.getEdoRole()));
        assignCandidateDelegateForm.setCandidateDelegateDeleted(isDelegateDeleted);

        return mapping.findForward("basic");
    }

    public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoAssignCandidateDelegateForm assignCandidateDelegateForm = (EdoAssignCandidateDelegateForm) form;
        resetDelegateFormState(assignCandidateDelegateForm);
        return mapping.findForward("basic");
    }

    private void resetDelegateFormState(EdoAssignCandidateDelegateForm assignCandidateDelegateForm) {
        if (ObjectUtils.isNotNull(assignCandidateDelegateForm)) {
            assignCandidateDelegateForm.setUserId(null);
            assignCandidateDelegateForm.setName(null);
            assignCandidateDelegateForm.setStartDate(null);
            assignCandidateDelegateForm.setEndDate(null);
        }
    }
}
