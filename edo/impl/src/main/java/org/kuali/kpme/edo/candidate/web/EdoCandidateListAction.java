package org.kuali.kpme.edo.candidate.web;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.checklist.EdoChecklistV;
import org.kuali.kpme.edo.dossier.EdoCandidateDossier;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoContext;
import org.kuali.rice.kew.actionrequest.ActionRequestValue;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * $HeadURL: https://svn.uits.iu.edu/hr/edo/trunk/src/main/java/org/kuali/kpme/edo/candidate/web/EdoCandidateListAction.java $
 * $Id: EdoCandidateListAction.java 11337 2014-03-25 16:32:01Z nrmalae $
 * Created with IntelliJ IDEA.
 * User: tcbradley
 * Date: 10/19/12
 * Time: 1:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class EdoCandidateListAction extends EdoAction {

    private List<EdoChecklistV> checklistView;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoCandidateListForm edoCandidateListForm = (EdoCandidateListForm) form;
        List<EdoCandidateDossier> dossierList = new LinkedList<EdoCandidateDossier>();
        String dossierListJSON = "";

        checklistView = edoCandidateListForm.getChecklistView();

        //When the user is a super user, get all open dossiers
        if (EdoContext.getUser().getCurrentRoleList().contains(EdoConstants.ROLE_SUPER_USER) || EdoContext.getUser().getCurrentRoleList().contains(EdoConstants.ROLE_FINAL_ADMINISTRATOR)) {
            List<EdoCandidateDossier> edoCandidateDossierList = EdoServiceLocator.getEdoCandidateDossierService().getCurrentCandidateDossierList();
            if (edoCandidateDossierList != null) {
                dossierList.addAll(edoCandidateDossierList);
            }
        } else {
            //Get reviewer dossiers
            if (EdoContext.canUserReviewerScreen() && StringUtils.equals(edoCandidateListForm.getTabId(), EdoConstants.DOSSIER_TAB.REVIEWS)) {
                //Get the full dossier list that is open/submitted.
                List<EdoCandidateDossier> candidateDossiers = EdoServiceLocator.getEdoCandidateDossierService().getCurrentCandidateDossierList();

                for (EdoCandidateDossier candidateDossier : candidateDossiers) {
                    // is this user an administrator with upload external letter permissions?
                    // doing this check before the docID check to give ADMINISTRATORs access to OPEN dossiers
                    if (EdoServiceLocator.getAuthorizationService().isAuthorizedToUploadExternalLetter_W(EdoContext.getUser().getEmplId(), candidateDossier.getDossierId().intValue()) && !(StringUtils.equals(candidateDossier.getDossierStatus(), EdoConstants.DOSSIER_STATUS.CLOSED))) {
                        dossierList.add(candidateDossier);
                    } else {
                        if (StringUtils.isNotEmpty(candidateDossier.getDocumentId()) && !(StringUtils.equals(candidateDossier.getDossierStatus(), EdoConstants.DOSSIER_STATUS.CLOSED))) {
                            try {
                                List<ActionRequestValue> actionRequestValues = new LinkedList<ActionRequestValue>();
                                List<String> groupIds = new LinkedList<String>();

                                //Get the requested action requests.
                                actionRequestValues.addAll(KEWServiceLocator.getActionRequestService().findAllActionRequestsByDocumentId(candidateDossier.getDocumentId()));

                                //Find the group ids to check.
                                for (ActionRequestValue actionRequestValue : actionRequestValues) {
                                    if (StringUtils.isNotEmpty(actionRequestValue.getGroupId())) {
                                        groupIds.add(actionRequestValue.getGroupId());
                                    }
                                }

                                for (String groupId : groupIds) {
                                    if (isMemberOfGroup(EdoContext.getPrincipalId(), groupId)) {
                                        dossierList.add(candidateDossier);
                                        break;
                                    }
                                }
                            } catch (Exception ex) {
                                //Catch the exception.
                            }
                        }
                    }
                }
            }
        }

        //remove duplicate edossiers : i dont know if this is necessary or not
        HashSet<EdoCandidateDossier> set = new HashSet<EdoCandidateDossier>(dossierList);
        dossierList.clear();
        dossierList.addAll(set);

        if (!dossierList.isEmpty()) {
            for (EdoCandidateDossier dossier : dossierList) {
                dossierListJSON = dossierListJSON.concat(dossier.getCandidateDossierJSONString() + ",");
            }
        }
        request.setAttribute("candidateJSON", dossierListJSON);
        request.setAttribute("checklist", checklistView);

        return super.execute(mapping, form, request, response);
    }
    public boolean isMemberOfGroup(String principalId, String groupId) {
        boolean isMember = false;

        List<String> allPrincipals = EdoContext.getPrincipalDelegators(principalId);

        for (String principal : allPrincipals) {
        	isMember = isMember || KimApiServiceLocator.getGroupService().isMemberOfGroup(principal, groupId);
        }

        return isMember;

    }
}

