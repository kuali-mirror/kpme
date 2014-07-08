package org.kuali.kpme.edo.candidate.web;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.kuali.kpme.edo.api.candidate.EdoCandidate;
import org.kuali.kpme.edo.api.dossier.EdoDossier;
import org.kuali.kpme.edo.api.dossier.type.EdoDossierType;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.candidate.EdoSelectedCandidate;
import org.kuali.kpme.edo.checklist.EdoChecklistV;
import org.kuali.kpme.edo.dossier.EdoCandidateDossier;
import org.kuali.kpme.edo.dossier.EdoDossierBo;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoContext;
import org.kuali.kpme.edo.util.EdoRule;
import org.kuali.kpme.edo.util.TagSupport;
import org.kuali.kpme.edo.workflow.DossierProcessDocumentHeader;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kew.actionrequest.ActionRequestValue;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.MessageMap;
//import org.kuali.kpme.edo.dossier.type.EdoDossierType;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/13/12
 * Time: 9:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoCandidateSelectAction extends EdoAction {

    static final Logger LOG = Logger.getLogger(EdoCandidateSelectAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<EdoChecklistV> checklistView = EdoServiceLocator.getChecklistVService().getCheckListView("IU", "ALL", "ALL");
        EdoCandidateSelectForm edoCandidateSelectForm = (EdoCandidateSelectForm) form;

        String cid = request.getParameter("cid");
        String dossier = null;
        EdoDossier currentDossier;
        edoCandidateSelectForm.setAoe();

        if (request.getParameterMap().containsKey("dossier")) {
            dossier = request.getParameter("dossier");
        }

        HttpSession ssn = request.getSession();
        if (ssn.isNew() || (null == ssn.getAttribute("selectedCandidate")) ) {
            ssn.setAttribute("selectedCandidate", new EdoSelectedCandidate() );
        }
        EdoSelectedCandidate selectedCandidate = (EdoSelectedCandidate)ssn.getAttribute("selectedCandidate");

        //EdoCandidate candidate = EdoServiceLocator.getCandidateService().getCandidate(BigDecimal.valueOf(Integer.parseInt(cid)));
        EdoCandidate candidate = EdoServiceLocator.getCandidateService().getCandidate(cid);
        
        selectedCandidate.setSelected(true);
        selectedCandidate.setCandidateID(candidate.getEdoCandidateID());
        selectedCandidate.setCandidateLastname(candidate.getLastName());
        selectedCandidate.setCandidateFirstname(candidate.getFirstName());
        selectedCandidate.setCandidateUsername(candidate.getPrincipalName());
        //selectedCandidate.setCandidateCampusCode(candidate.getCandidacyCampus());
        selectedCandidate.setCandidateDepartmentID(candidate.getPrimaryDeptID());
        selectedCandidate.setCandidateSchoolID(candidate.getCandidacySchool());

        if ("".equals(dossier)) {
            // get current dossier ID for this candidate
            currentDossier = EdoServiceLocator.getEdoDossierService().getCurrentDossierPrincipalName(candidate.getPrincipalName());
        } else {
            // get the requested dossier ID for this candidate
            currentDossier = EdoServiceLocator.getEdoDossierService().getEdoDossierById(dossier);
        }
        // check for permission to view current dossier
        //Get the full dossier list that is open/submitted.
        List<EdoCandidateDossier> candidateDossiers = EdoServiceLocator.getEdoCandidateDossierService().getCurrentCandidateDossierList();
        List<EdoCandidateDossier> dossierList = new LinkedList<EdoCandidateDossier>();

        for (EdoCandidateDossier candidateDossier : candidateDossiers) {
            // if this user is the candidate
            if (candidateDossier.getUsername().equals(EdoContext.getUser().getNetworkId())) {
                dossierList.add(candidateDossier);
            }
            // is this user a delegate for a candidate with a dossier? if so, add the dossier to the list
            List<String> candidatesForThisDelegate = EdoServiceLocator.getEdoMaintenanceService().getDelegatesCandidateList(EdoContext.getUser().getEmplId());
            for (String candidateEmplid : candidatesForThisDelegate) {
                Entity principalEntity = KimApiServiceLocator.getIdentityService().getEntityByPrincipalName(candidateDossier.getUsername());
                if (principalEntity.getId().equals(candidateEmplid)) {
                    dossierList.add(candidateDossier);
                }
            }
            // is this user a guest of the dossier's candidate
            List<String> guestList = EdoServiceLocator.getEdoMaintenanceService().getCandidateExistingGuests(candidateDossier.getDossierId());
            if (CollectionUtils.isNotEmpty(guestList)) {
                if (guestList.contains(EdoContext.getUser().getEmplId())) {
                    dossierList.add(candidateDossier);
                }
            }

            // is this user an administrator with upload external letter permissions?
            if (EdoServiceLocator.getAuthorizationService().isAuthorizedToUploadExternalLetter_W(EdoContext.getPrincipalId(),candidateDossier.getDossierId().intValue())) {
                dossierList.add(candidateDossier);
            }

            if (StringUtils.isNotEmpty(candidateDossier.getDocumentId())) {
                try {
                    List<ActionRequestValue> actionRequestValues = new LinkedList<ActionRequestValue>();
                    List<String> groupIds = new LinkedList<String>();

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

        boolean canViewDossier = false;
        if (CollectionUtils.isNotEmpty(dossierList)) {
            for (EdoCandidateDossier dossierTmp : dossierList) {
                if (dossierTmp.getDossierId().compareTo(new BigDecimal(currentDossier.getEdoDossierID())) == 0) {
                    canViewDossier = true;
                    break;
                }
            }
        }
        if (!canViewDossier && !EdoServiceLocator.getEdoMaintenanceService().hasSuperUserRole_W(EdoContext.getPrincipalId())) {
            MessageMap msgmap = GlobalVariables.getMessageMap();

            LOG.error("User does not have access rights to view this dossier.");
            msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.access.dossier");

            // reset the selected candidate object
            selectedCandidate = new EdoSelectedCandidate();
            ssn.setAttribute("selectedCandidate", selectedCandidate);

            // kick the user back to the home screen
            return new ActionRedirect("EdoIndex.do");

        } else {

            EdoDossierType dossierType = EdoServiceLocator.getEdoDossierTypeService().getEdoDossierTypeById(currentDossier.getEdoDossierTypeID());
            selectedCandidate.setCandidateDossierID( new BigDecimal(currentDossier.getEdoDossierID()) );
            selectedCandidate.setAoe( currentDossier.getAoeCode() );
            selectedCandidate.setRankSought( currentDossier.getRankSought() );
            selectedCandidate.setDossierTypeCode(dossierType.getDossierTypeCode());
            selectedCandidate.setDossierTypeName(dossierType.getDossierTypeName());
            selectedCandidate.setDossierStatus(currentDossier.getDossierStatus());
            selectedCandidate.setDossierWorkflowId(currentDossier.getWorkflowId());
            if (EdoRule.validateDossierForSubmission(checklistView, new BigDecimal(currentDossier.getEdoDossierID()))) {
                request.setAttribute("isValidDossier", 1);
            } else {
                request.setAttribute("isValidDossier", 0);
            }
            // what does this do - TC
            // tcb: this provides the node drop down lists for super actions to route/return a dossier
            if (currentDossier != null && currentDossier.getEdoDossierID() != null) {
                DossierProcessDocumentHeader documentHeader = EdoServiceLocator.getDossierProcessDocumentHeaderService().getDossierProcessDocumentHeader(currentDossier.getEdoDossierID());
                if (documentHeader != null) {
                    //edoCandidateSelectForm.setFutureNodes(KEWServiceLocator.getRouteNodeService().findFutureNodeNames(currentDossier.getDocumentID()));
                    //edoCandidateSelectForm.setPreviousNodes(KEWServiceLocator.getRouteNodeService().findPreviousNodeNames(currentDossier.getDocumentID()));
                }
            }

            // for candidate supplemental submit button
            if (EdoRule.dossierHasSupplementalsPending(new BigDecimal(currentDossier.getEdoDossierID()))) {
                request.setAttribute("dossierHasSupplementalsPending", true);
            } else {
                request.setAttribute("dossierHasSupplementalsPending", false);
            }
            //for candidate reconsider  submit button
            if (EdoRule.dossierHasReconsiderPending(new BigDecimal(currentDossier.getEdoDossierID()))) {
                request.setAttribute("dossierHasReconsiderPending", true);
            } else {
                request.setAttribute("dossierHasReconsiderPending", false);
            }
            
           


            // need to check if any supplemental actions (new vote/letter) have taken place; this is for ack button state
            request.setAttribute("hasTakenSupplementalAction", false);
            // first, is current user allowed to route document?
            boolean canTakeAction = EdoServiceLocator.getAuthorizationService().isAuthorizedToVote_W(EdoContext.getPrincipalId());

            if (canTakeAction) {
                DossierProcessDocumentHeader documentHeader = EdoServiceLocator.getDossierProcessDocumentHeaderService().getDossierProcessDocumentHeader(selectedCandidate.getCandidateDossierID().intValue());

                if (documentHeader != null) {
                    List<DossierProcessDocumentHeader> suppDocHeaders = EdoServiceLocator.getDossierProcessDocumentHeaderService().getPendingSupplementalDocuments(new Integer(documentHeader.getEdoDossierId()));
                    if (CollectionUtils.isNotEmpty(suppDocHeaders)) {
                        boolean hasAddedVoteRecord = false;
                        boolean hasAddedReviewLetter = false;
                        for (DossierProcessDocumentHeader docHeader : suppDocHeaders) {
                            hasAddedReviewLetter = hasAddedReviewLetter || EdoServiceLocator.getEdoSupplementalPendingStatusService().hasAddedSupplementalReviewLetter(docHeader.getDocumentId());
                            hasAddedVoteRecord = hasAddedVoteRecord || EdoServiceLocator.getEdoSupplementalPendingStatusService().hasAddedSupplementalVoteRecord(docHeader.getDocumentId());
                        }
                        if (hasAddedReviewLetter || hasAddedVoteRecord) {
                            request.setAttribute("hasTakenSupplementalAction", true);
                        }
                    }
                }
            }
            request.setAttribute("guidelineURL", ConfigContext.getCurrentContextConfig().getProperty("edo.pt.guidelines.url"));

            request.setAttribute("dossierReadyForRoute", EdoRule.isDossierReadyForRoute(new BigDecimal(currentDossier.getEdoDossierID())));

            return super.execute(mapping, form, request, response);

        }
    }

    public ActionForward updateAoe(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoCandidateSelectForm csf = (EdoCandidateSelectForm) form;
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        new java.sql.Date(t);
        new java.sql.Time(t);
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);

        EdoDossier dossier = EdoServiceLocator.getEdoDossierService().getCurrentDossierPrincipalName(csf.getCandidateUsername());
        // TODO: need to take care of the settings
        //dossier.setAoeCode(csf.getSelectedAoe());
        //dossier.setLastUpdated(sqlTimestamp);
        //dossier.setUpdatedBy(EdoContext.getUser().getNetworkId());
        EdoServiceLocator.getEdoDossierService().saveOrUpdate(dossier);
        HttpSession session = request.getSession();
        EdoSelectedCandidate selectedCandidate = (EdoSelectedCandidate)session.getAttribute("selectedCandidate");
        selectedCandidate.setAoe(csf.getSelectedAoe());
        //update the other columns in the EDO_DOSSIER_T table

        String prevPage = request.getHeader("REFERER");
        return new ActionRedirect(prevPage);
    }
    public boolean isMemberOfGroup(String principalId, String groupId) {
        boolean isMember = false;

        List<String> allPrincipals = EdoContext.getPrincipalDelegators(principalId);

        for (String principal : allPrincipals) {
        	isMember = isMember || KimApiServiceLocator.getGroupService().isMemberOfGroup(principal, groupId);
        }

        return isMember;

    }
    
    public ActionForward closeDossier(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoCandidateSelectForm csf = (EdoCandidateSelectForm) form;
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        new java.sql.Date(t);
        new java.sql.Time(t);
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);

        EdoDossier dossier = EdoServiceLocator.getEdoDossierService().getCurrentDossierPrincipalName(csf.getCandidateUsername());
     // TODO: need to take care of the settings
        //dossier.setDossierStatus(EdoConstants.DOSSIER_STATUS.CLOSED);
        //dossier.setLastUpdated(sqlTimestamp);
        //dossier.setUpdatedBy(EdoContext.getUser().getNetworkId());
        EdoServiceLocator.getEdoDossierService().saveOrUpdate(dossier);
        HttpSession session = request.getSession();
        EdoSelectedCandidate selectedCandidate = (EdoSelectedCandidate)session.getAttribute("selectedCandidate");
        selectedCandidate.setDossierStatus(EdoConstants.DOSSIER_STATUS.CLOSED);

        String prevPage = request.getHeader("REFERER");
        return new ActionRedirect(prevPage);
    }

    public ActionForward reconsiderDossier(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoCandidateSelectForm csf = (EdoCandidateSelectForm) form;
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        new java.sql.Date(t);
        new java.sql.Time(t);
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);

        EdoDossier dossier = EdoServiceLocator.getEdoDossierService().getCurrentDossierPrincipalName(csf.getCandidateUsername());
     // TODO: need to take care of the settings
        //dossier.setDossierStatus(EdoConstants.DOSSIER_STATUS.RECONSIDERATION);
        //dossier.setLastUpdated(sqlTimestamp);
        //dossier.setUpdatedBy(EdoContext.getUser().getNetworkId());
        EdoServiceLocator.getEdoDossierService().saveOrUpdate(dossier);
        HttpSession session = request.getSession();
        EdoSelectedCandidate selectedCandidate = (EdoSelectedCandidate)session.getAttribute("selectedCandidate");
        selectedCandidate.setDossierStatus(EdoConstants.DOSSIER_STATUS.RECONSIDERATION);
        
        //send a notification to the candidate that thier dossier is being reconsidered.
        String subject = "Workflow Notification";
        String content = "This is an FYI email notification : "
        		+ "You have requested a reconsideration of your case. A reconsideration folder is now available in your e-dossier.\n\n " 
				+"You should use this folder to add any materials you deem relevant to the process."
        		+ "For additional help or feedback, email <edossier@indiana.edu>.";        				
        String testEmailAddresses = new String();
		if (!TagSupport.isNonProductionEnvironment()) {
			
				Person person = KimApiServiceLocator
						.getPersonService().getPersonByPrincipalName(csf.getCandidateUsername());
				EdoServiceLocator.getEdoNotificationService()
						.sendMail(person.getEmailAddress(),
								"edossier@indiana.edu",
								subject, content);
			
		} else {
			testEmailAddresses = ConfigContext
					.getCurrentContextConfig()
					.getProperty(
							EdoConstants.TEST_EMAIL_NOTIFICATION);
			EdoServiceLocator.getEdoNotificationService()
					.sendMail(testEmailAddresses,
							"edossier@indiana.edu", subject,
							content);
		}
        	

        String prevPage = request.getHeader("REFERER");
        return new ActionRedirect(prevPage);
    }
}
