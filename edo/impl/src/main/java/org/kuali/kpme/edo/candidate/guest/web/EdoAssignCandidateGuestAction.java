package org.kuali.kpme.edo.candidate.guest.web;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoRule;
import org.kuali.kpme.edo.util.EdoUser;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EdoAssignCandidateGuestAction extends EdoAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoAssignCandidateGuestForm assignCandidateGuestForm = (EdoAssignCandidateGuestForm) form;
        assignCandidateGuestForm.setCandidateGuests(EdoServiceLocator.getEdoMaintenanceService().getCandidateGuests(EdoUser.getCurrentTargetPerson().getPrincipalName()));
        return super.execute(mapping, form, request, response);
    }

    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        EdoAssignCandidateGuestForm assignCandidateGuestForm = (EdoAssignCandidateGuestForm) form;

        String userId = assignCandidateGuestForm.getUserId();
        if (StringUtils.isNotEmpty(userId)) {

            if (EdoRule.validateUserId(userId)) {
                EdoUser userInfo = EdoServiceLocator.getAuthorizationService().getEdoUser(userId);
                assignCandidateGuestForm.setName(userInfo.getName());
                assignCandidateGuestForm.setEmplId(userInfo.getEmplId());
                assignCandidateGuestForm.setDossierDropDown(EdoServiceLocator.getEdoDossierService().getDossierListByUserName(EdoUser.getCurrentTargetPerson().getPrincipalName()));
            }
        }

        return mapping.findForward("basic");

    }

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoAssignCandidateGuestForm assignCandidateGuestForm = (EdoAssignCandidateGuestForm) form;
        if(StringUtils.isEmpty(assignCandidateGuestForm.getUserId()) || StringUtils.isEmpty(assignCandidateGuestForm.getName())) {
            return mapping.findForward("basic");
        }

        if (ObjectUtils.isNotNull(assignCandidateGuestForm.getDossierId())) {
            List<String> existingDelegates = EdoServiceLocator.getEdoMaintenanceService().getCandidateExistingGuests(assignCandidateGuestForm.getDossierId());
            if (existingDelegates.contains(assignCandidateGuestForm.getEmplToAdd())) {
                GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.guest.duplicate", assignCandidateGuestForm.getName());
            } else {
                boolean isGuestAdded = EdoServiceLocator.getEdoMaintenanceService().saveGuestForDossierId(assignCandidateGuestForm.getEmplToAdd(), assignCandidateGuestForm.getStartDate(), assignCandidateGuestForm.getEndDate(), assignCandidateGuestForm.getDossierId());

                if (isGuestAdded) {
                    assignCandidateGuestForm.setCandidateGuestAdded(isGuestAdded);
                }
                assignCandidateGuestForm.setCandidateGuests(EdoServiceLocator.getEdoMaintenanceService().getCandidateGuests(EdoUser.getCurrentTargetPerson().getPrincipalName()));
            }
        } else {
            GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.guest.dossier.invalid");
        }

        resetGuestFormState(assignCandidateGuestForm);
        return mapping.findForward("basic");
    }


    public ActionForward deleteFacultyGuest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoAssignCandidateGuestForm assignCandidateGuestForm = (EdoAssignCandidateGuestForm) form;
        boolean isGuestDeleted = false;
        isGuestDeleted = EdoServiceLocator.getEdoMaintenanceService().deleteGuestForCandidateDossier(assignCandidateGuestForm.getEmplToDelete(), assignCandidateGuestForm.getDossierIdToDelete());
        assignCandidateGuestForm.setCandidateGuests(EdoServiceLocator.getEdoMaintenanceService().getCandidateGuests(EdoUser.getCurrentTargetPerson().getPrincipalName()));

        assignCandidateGuestForm.setCandidateGuestDeleted(isGuestDeleted);

        return mapping.findForward("basic");
    }

    public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoAssignCandidateGuestForm assignCandidateGuestForm = (EdoAssignCandidateGuestForm) form;
        resetGuestFormState(assignCandidateGuestForm);
        return mapping.findForward("basic");

    }

    private void resetGuestFormState(EdoAssignCandidateGuestForm assignCandidateGuestForm) {
        if (ObjectUtils.isNotNull(assignCandidateGuestForm)) {
            assignCandidateGuestForm.setUserId(null);
            assignCandidateGuestForm.setName(null);
            assignCandidateGuestForm.setStartDate(null);
            assignCandidateGuestForm.setEndDate(null);
        }
    }

}
