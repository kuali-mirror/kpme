package org.kuali.kpme.edo.dossier.web;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.edo.api.candidate.EdoCandidate;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.dossier.EdoCandidateDossier;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoContext;
import org.kuali.kpme.edo.util.EdoUser;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

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
//                    EdoCandidate edoC = EdoServiceLocator.getCandidateService().getCandidateByUsername(EdoContext.getUser().getNetworkId());
                    EdoCandidate edoC = EdoServiceLocator.getCandidateService().getCandidateByUsername(HrContext.getPrincipalName());
                    currentCandidateID = edoC.getEdoCandidateID().toString();
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

//                if (EdoContext.getUser().getCurrentRoleList().contains("Candidate Delegate")) {
                if (HrContext.isUserCandidateDelegate()) {
//                    List<String> candidateList = EdoServiceLocator.getEdoMaintenanceService().getDelegatesCandidateList(EdoContext.getUser().getEmplId());
                    List<String> candidateList = EdoServiceLocator.getEdoMaintenanceService().getDelegatesCandidateList(HrContext.getTargetPrincipalId());
                    for (String candidate : candidateList) {
                        Person person = KimApiServiceLocator.getPersonService().getPerson(candidate);
                        List<EdoCandidateDossier> edoCandidateDossierList = EdoServiceLocator.getEdoCandidateDossierService().getCandidateDelegateDossierByUsername(person.getPrincipalName());
                        if (edoCandidateDossierList != null) {
                            dossierList.addAll(edoCandidateDossierList);
                        }
                    }
                }

//                if (EdoContext.getUser().getCurrentRoleList().contains("Candidate")) {
                if (HrContext.isUserCandidate()) {
//                    List<EdoCandidateDossier> edoCandidateDossierList = EdoServiceLocator.getEdoCandidateDossierService().getCandidateDossierByUsername(EdoContext.getUser().getNetworkId());
                    List<EdoCandidateDossier> edoCandidateDossierList = EdoServiceLocator.getEdoCandidateDossierService().getCandidateDossierByUsername(HrContext.getTargetName());
                    if (edoCandidateDossierList != null) {
                        dossierList.addAll(edoCandidateDossierList);
                    }
//                    EdoCandidate edoC = EdoServiceLocator.getCandidateService().getCandidateByUsername(EdoContext.getUser().getNetworkId());
                    EdoCandidate edoC = EdoServiceLocator.getCandidateService().getCandidateByUsername(HrContext.getPrincipalName());
                    currentCandidateID = edoC.getEdoCandidateID().toString();
                }
                //guest role
//                if (EdoContext.getUser().getCurrentRoleList().contains("Guest Dossier")) {
                if (HrContext.isUserGuestDossier()) {
//                    List<String> dossierIdsList = EdoServiceLocator.getEdoMaintenanceService().getGuestDossierList(EdoContext.getUser().getEmplId());
                    List<String> dossierIdsList = EdoServiceLocator.getEdoMaintenanceService().getGuestDossierList(HrContext.getPrincipalId());
                    for (String dossierId : dossierIdsList) {

                        EdoCandidateDossier edoCandidateDossierList = EdoServiceLocator.getEdoCandidateDossierService().getCandidateDossierByDossierId(dossierId);

                        if (edoCandidateDossierList != null) {
                            dossierList.add(edoCandidateDossierList);
                        }
                    }
                }

//                if (EdoContext.getUser().getCurrentRoleList().contains("Super User")) {
                if (HrContext.isUserEdoSuperUser()) {
//                     List<EdoCandidateDossier> edoCandidateDossierList = EdoServiceLocator.getEdoCandidateDossierService().getCandidateDossierByUsername(EdoContext.getUser().getNetworkId());
                     List<EdoCandidateDossier> edoCandidateDossierList = EdoServiceLocator.getEdoCandidateDossierService().getCandidateDossierByUsername(HrContext.getPrincipalName());

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
