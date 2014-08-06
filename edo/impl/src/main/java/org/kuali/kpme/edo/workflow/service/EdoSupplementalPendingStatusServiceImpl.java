package org.kuali.kpme.edo.workflow.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.kuali.kpme.edo.api.item.EdoItem;
import org.kuali.kpme.edo.api.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.api.vote.EdoVoteRecord;
import org.kuali.kpme.edo.candidate.EdoSelectedCandidate;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoContext;
import org.kuali.kpme.edo.util.TagSupport;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kew.actionrequest.ActionRequestValue;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.document.Document;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

/**
 * $HeadURL$ $Revision$ Created with IntelliJ IDEA. User: bradleyt Date: 9/20/13
 * Time: 4:07 PM
 */
public class EdoSupplementalPendingStatusServiceImpl implements	EdoSupplementalPendingStatusService {

	private static final Logger LOG = Logger.getLogger(EdoSupplementalPendingStatusServiceImpl.class);

	public boolean isWaiting(String supplementalDocumentId, String principalId) {
		boolean isWaiting = false;
		Collection<String> groupIds = new TreeSet<String>();
		BigDecimal currentDossierRouteLevel = EdoServiceLocator.getDossierProcessDocumentHeaderService().getCurrentRouteLevel(EdoContext.getSelectedCandidate().getCandidateDossierID().intValue());

		List<ActionRequestValue> actionRequests = KEWServiceLocator.getActionRequestService().findPendingByDoc(supplementalDocumentId);

		if (CollectionUtils.isNotEmpty(actionRequests)) {
			LOG.info("Found action requests");
			for (ActionRequestValue req : actionRequests) {
                // TODO: verify that commenting this out doesn't break anything
				//EdoReviewLayerDefinition reviewLayerDefinition = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitionBySupplementalNode(req.getNodeInstance().getName());

				// now, NOT including current node action requests, so no need
				// to filter

				// add the group ID to the list if this node is NOT at the same
				// node level as the parent dossier
				LOG.info("Node instance: " + req.getNodeInstance().getName());
				String gid = req.getGroupId();
                if (StringUtils.isNotEmpty(gid)) {
				    groupIds.add(gid);
                } else {
                    LOG.info("No group ID found at node");
                }
			}

			for (String grp : groupIds) {
				if (KimApiServiceLocator.getGroupService().isMemberOfGroup(principalId, grp)) {
					LOG.info("Checking principal ID: " + principalId + " against group ID: " + grp);
					isWaiting = true;
				}
			}
		} else {
			LOG.info("No action requests found");
		}
		return isWaiting;
	}

	public void notifyCurrentEdossierLevelForSuppDoc(String dossierDocumentId, String currentRouteLevel, String contentSingle, String contentMultiple) {
		Collection<String> groupIds = new TreeSet<String>();
		List<ActionRequestValue> actionRequests = KEWServiceLocator.getActionRequestService().findPendingByDoc(dossierDocumentId);
        String workflowId = EdoServiceLocator.getEdoDossierService().getDossier(dossierDocumentId).getWorkflowId();

		//make a service call to get the vote_type by passing in the currentRouteLevel
		EdoReviewLayerDefinition edoReviewLayerDefinition = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinition(workflowId, currentRouteLevel);
		//from this object we should get the vote type
		//after we get the vote type we should compare with what?
		//ask TC about this
		
		if (CollectionUtils.isNotEmpty(actionRequests)) {
			LOG.info("Found action requests");
			for (ActionRequestValue req : actionRequests) {
				
				if (req.getNodeInstance().getName().contains(currentRouteLevel)) {
					LOG.info("Node instance: " + req.getNodeInstance().getName());
					// get the groups in that level
					if(StringUtils.isNotEmpty(req.getGroupId())) {
					    String gid = req.getGroupId();
					    groupIds.add(gid);
					}
				}
			}

			for (String grp : groupIds) {
				if (!KimApiServiceLocator.getGroupService().getMemberPrincipalIds(grp).isEmpty()) {
					List<String> principalIds = KimApiServiceLocator.getGroupService().getMemberPrincipalIds(grp);
					String testEmailAddresses = new String();
					String subject = "Workflow Notification";

					if (!TagSupport.isNonProductionEnvironment()) {
						for (String principalId : principalIds) {
							// do the email notification
							Person person = KimApiServiceLocator.getPersonService().getPerson(principalId);

							LOG.info(" Notifying  [" + person.getPrincipalName() + "] about Supplemental Documents being available for review");
							if(StringUtils.equals(edoReviewLayerDefinition.getVoteType(), "Single")) {
							    EdoServiceLocator.getEdoNotificationService().sendMail( person.getEmailAddress(), "edossier@indiana.edu", subject, contentSingle);
							}
							if(StringUtils.equals(edoReviewLayerDefinition.getVoteType(), "Multiple")) {
							    EdoServiceLocator.getEdoNotificationService().sendMail(person.getEmailAddress(), "edossier@indiana.edu", subject, contentMultiple);
							}
						}
					} else {
						// LOG.info("In non-production environment, not sending notification.");
						testEmailAddresses = ConfigContext.getCurrentContextConfig().getProperty(EdoConstants.TEST_EMAIL_NOTIFICATION);
						if(StringUtils.equals(edoReviewLayerDefinition.getVoteType(), "Single")) {
							EdoServiceLocator.getEdoNotificationService().sendMail(testEmailAddresses, "edossier@indiana.edu", subject,	contentSingle);
						}
						if(StringUtils.equals(edoReviewLayerDefinition.getVoteType(), "Multiple")) {
						    EdoServiceLocator.getEdoNotificationService().sendMail(testEmailAddresses, "edossier@indiana.edu", subject,	contentMultiple);
						}
					}
				}
			}
		} else {
			LOG.info("No action requests found");
		}
	}

	public boolean hasAddedSupplementalReviewLetter(String supplementalDocumentId) {

		BigDecimal dossierId = EdoContext.getSelectedCandidate().getCandidateDossierID();
        String workflowId = EdoServiceLocator.getEdoDossierService().getEdoDossierById(dossierId.toString()).getWorkflowId();

		Map<String, EdoReviewLayerDefinition> reviewLevelMap = EdoServiceLocator.getEdoReviewLayerDefinitionService().buildReviewLevelMap(EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions(workflowId));
		
		Document document = KewApiServiceLocator.getWorkflowDocumentService().getDocument(supplementalDocumentId);
		DateTime documentCreated = document.getDateCreated();

		BigDecimal highestAuthorizedViewReviewLevel = BigDecimal.ZERO;
		List<BigDecimal> authorizedViewReviewLetterLevels = EdoContext.getAuthorizedViewReviewLetterLevels();
		if (CollectionUtils.isNotEmpty(authorizedViewReviewLetterLevels)) {
			highestAuthorizedViewReviewLevel = Collections.max(authorizedViewReviewLetterLevels);
		} else {
            // no authorized view review letter levels?  probably shouldn't be here, but just in case return false
            return false;
        }

		List<EdoItem> letters = EdoServiceLocator.getEdoItemService().getReviewLetterEdoItems(dossierId.toString(), reviewLevelMap.get(highestAuthorizedViewReviewLevel).getEdoReviewLayerDefinitionId());

        // no letters?  then obviously hasn't added any letters
        if (CollectionUtils.isEmpty(letters)) {
            return false;
        }

		DateTime maxDate = new DateTime(new Date(1000));
		EdoItem.Builder mostCurrentLetter = EdoItem.Builder.create();

		for (EdoItem letter : letters) {
			if (letter.getActionFullDateTime().isAfter(maxDate)) {
				maxDate = letter.getActionFullDateTime();
				mostCurrentLetter = EdoItem.Builder.create(letter);
			}
		}

		DateTime ltrUpdatedDT = mostCurrentLetter.getActionFullDateTime();

		if (documentCreated.isAfter(ltrUpdatedDT)) {
			return false;
		} else {
			return true;
		}
	}

	public boolean hasAddedSupplementalVoteRecord(String supplementalDocumentId) {

		List<BigDecimal> authLevels = EdoContext.getAuthorizedViewVoteRecordLevels();
		BigDecimal maxLevel = Collections.max(authLevels);
		List<EdoReviewLayerDefinition> voteRecordLayerDefinitions = new LinkedList<EdoReviewLayerDefinition>();
		EdoSelectedCandidate selectedCandidate = EdoContext.getSelectedCandidate();
		Document document = KewApiServiceLocator.getWorkflowDocumentService().getDocument(supplementalDocumentId);
		DateTime documentCreated = document.getDateCreated();
        String workflowId = EdoServiceLocator.getEdoDossierService().getEdoDossierById(selectedCandidate.getCandidateDossierID().toString()).getWorkflowId();

		Collection<EdoReviewLayerDefinition> reviewLayerDefinitions = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions(workflowId);
		for (EdoReviewLayerDefinition reviewLayerDefinition : reviewLayerDefinitions) {
			if ((reviewLayerDefinition.getReviewLevel() != null) && reviewLayerDefinition.getReviewLevel().equals(maxLevel)) {
				voteRecordLayerDefinitions.add(reviewLayerDefinition);
			}
		}
		List<EdoVoteRecord> voteRecords = EdoServiceLocator.getEdoVoteRecordService().getVoteRecords(selectedCandidate.getCandidateDossierID().toString(), voteRecordLayerDefinitions);

		//Date maxDate = new Date(1000);
		DateTime maxDate = new DateTime(1000);
		//EdoVoteRecord mostCurrentVoteRecord = new EdoVoteRecord();
		EdoVoteRecord.Builder builder = EdoVoteRecord.Builder.create();
        EdoVoteRecord mostCurrentVoteRecord =  builder.build();

		for (EdoVoteRecord voteRec : voteRecords) {
			// if (voteRec.getUpdatedAt().after(new TimeStamp(maxDate))) {
			if (voteRec.getUpdatedAt() != null ) {
				maxDate = new DateTime(voteRec.getUpdatedAt());
				mostCurrentVoteRecord = voteRec;
			}
		}
		DateTime vrUpdatedDT = new DateTime(mostCurrentVoteRecord.getCreatedAt());

		if (documentCreated.isAfter(vrUpdatedDT)) {
			// supp doc created after last updated vote record, so has NOT added
			// new vote record since supp edoc update
			return false;
		} else {
			// supp doc created before last updated vote record, so HAS added
			// new vote record since supp edoc update
			return true;
		}

	}

}
