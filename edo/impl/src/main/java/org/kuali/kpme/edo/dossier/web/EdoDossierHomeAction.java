package org.kuali.kpme.edo.dossier.web;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.candidate.EdoCandidate;
import org.kuali.kpme.edo.dossier.EdoCandidateDossier;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoContext;
import org.kuali.kpme.edo.util.EdoUser;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * $HeadURL$ $Revision$ Created with IntelliJ IDEA. User: bradleyt Date: 2/14/13
 * Time: 10:45 AM To change this template use File | Settings | File Templates.
 */
public class EdoDossierHomeAction extends EdoAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EdoDossierHomeForm edoDossierHomeForm = (EdoDossierHomeForm)form;
	
		List<EdoCandidateDossier> dossierList = new LinkedList<EdoCandidateDossier>();

		String dossierListJSON = "";
		String currentCandidateID = "";
	
        if (EdoUser.getCurrentTargetPerson() != null) {

            if(EdoUser.isTargetInUse()) {

                if (EdoUser.getCurrentTargetRoles().contains("Candidate Delegate")) {
                    List<String> candidateList = EdoServiceLocator.getEdoMaintenanceService().getDelegatesCandidateList(EdoUser.getCurrentTargetPerson().getPrincipalId());
                    for (String candidate : candidateList) {
                        Person person = KimApiServiceLocator.getPersonService().getPerson(candidate);
                        List<EdoCandidateDossier> edoCandidateDossierList = EdoServiceLocator.getEdoCandidateDossierService().getCandidateDelegateDossierByUsername(person.getPrincipalName());
                        if (edoCandidateDossierList != null) {
                            dossierList.addAll(edoCandidateDossierList);
                        }
                    }
                }

                if (EdoUser.getCurrentTargetRoles().contains("Candidate")) {
                    List<EdoCandidateDossier> edoCandidateDossierList = EdoServiceLocator.getEdoCandidateDossierService().getCandidateDossierByUsername(EdoUser.getCurrentTargetPerson().getPrincipalName());
                    if (edoCandidateDossierList != null) {
                        dossierList.addAll(edoCandidateDossierList);
                    }
                    EdoCandidate edoC = EdoServiceLocator.getCandidateService().getCandidateByUsername(EdoContext.getUser().getNetworkId());
                    currentCandidateID = edoC.getCandidateID().toString();
                }
                //guest role
                if (EdoUser.getCurrentTargetRoles().contains("Guest Dossier")) {
                    List<String> candidateList = EdoServiceLocator.getEdoMaintenanceService().getGuestDossierList(EdoUser.getCurrentTargetPerson().getPrincipalId());
                    for (String candidate : candidateList) {
                        Person person = KimApiServiceLocator.getPersonService().getPerson(candidate);
                        List<EdoCandidateDossier> edoCandidateDossierList = EdoServiceLocator.getEdoCandidateDossierService().getCandidateDossierByUsername(person.getPrincipalName());
                        if (edoCandidateDossierList != null) {
                            dossierList.addAll(edoCandidateDossierList);
                        }
                    }
                }

                if (EdoUser.getCurrentTargetRoles().contains("Super User")) {

                    // List<EdoCandidateDossier> edoCandidateDossierList =
                    // EdoServiceLocator.getEdoCandidateDossierService().getCandidateDossierByUsername(EdoContext.getUser().getNetworkId());
                    List<EdoCandidateDossier> edoCandidateDossierList = EdoServiceLocator.getEdoCandidateDossierService().getCandidateDossierByUsername(EdoUser.getCurrentTargetPerson().getPrincipalName());

                    if (edoCandidateDossierList != null) {
                        dossierList.addAll(edoCandidateDossierList);
                    }
                }
            } else {

                if (EdoContext.getUser().getCurrentRoleList().contains("Candidate Delegate")) {
                    List<String> candidateList = EdoServiceLocator.getEdoMaintenanceService().getDelegatesCandidateList(EdoContext.getUser().getEmplId());
                    for (String candidate : candidateList) {
                        Person person = KimApiServiceLocator.getPersonService().getPerson(candidate);
                        List<EdoCandidateDossier> edoCandidateDossierList = EdoServiceLocator.getEdoCandidateDossierService().getCandidateDelegateDossierByUsername(person.getPrincipalName());
                        if (edoCandidateDossierList != null) {
                            dossierList.addAll(edoCandidateDossierList);
                        }
                    }
                }

                if (EdoContext.getUser().getCurrentRoleList().contains("Candidate")) {
                    List<EdoCandidateDossier> edoCandidateDossierList = EdoServiceLocator.getEdoCandidateDossierService().getCandidateDossierByUsername(EdoContext.getUser().getNetworkId());
                    if (edoCandidateDossierList != null) {
                        dossierList.addAll(edoCandidateDossierList);
                    }
                    EdoCandidate edoC = EdoServiceLocator.getCandidateService().getCandidateByUsername(EdoContext.getUser().getNetworkId());
                    currentCandidateID = edoC.getCandidateID().toString();
                }
                //guest role
                if (EdoContext.getUser().getCurrentRoleList().contains("Guest Dossier")) {
                    List<String> dossierIdsList = EdoServiceLocator.getEdoMaintenanceService().getGuestDossierList(EdoContext.getUser().getEmplId());
                    for (String dossierId : dossierIdsList) {

                        EdoCandidateDossier edoCandidateDossierList = EdoServiceLocator.getEdoCandidateDossierService().getCandidateDossierByDossierId(dossierId);

                        if (edoCandidateDossierList != null) {
                            dossierList.add(edoCandidateDossierList);
                        }
                    }
                }

                if (EdoContext.getUser().getCurrentRoleList().contains("Super User")) {

                     List<EdoCandidateDossier> edoCandidateDossierList = EdoServiceLocator.getEdoCandidateDossierService().getCandidateDossierByUsername(EdoContext.getUser().getNetworkId());

                    if (edoCandidateDossierList != null) {
                        dossierList.addAll(edoCandidateDossierList);
                    }
                }
           }
        }

		// remove duplicate edossiers : this may occur if the logged in person has multiple roles
	    HashSet<EdoCandidateDossier> set = new
		HashSet<EdoCandidateDossier>(dossierList); dossierList.clear();
		dossierList.addAll(set);

		if (!dossierList.isEmpty()) {
			for (EdoCandidateDossier dossier : dossierList) {
				dossierListJSON = dossierListJSON.concat(dossier.getCandidateDossierJSONString() + ",");
			}
		}
        request.setAttribute("dossierJSON", dossierListJSON);
		request.setAttribute("currentCandidateID", currentCandidateID);

		return super.execute(mapping, form, request, response);
	}
}
