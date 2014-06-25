package org.kuali.kpme.edo.vote.web;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.candidate.EdoSelectedCandidate;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoContext;
import org.kuali.kpme.edo.util.EdoRule;
import org.kuali.kpme.edo.util.EdoUtils;
import org.kuali.kpme.edo.vote.EdoVoteRecord;
import org.kuali.kpme.edo.vote.validation.EdoVoteRecordValidation;
import org.kuali.kpme.edo.workflow.DossierProcessDocumentHeader;
import org.kuali.rice.kew.service.KEWServiceLocator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class EdoVoteRecordAction extends EdoAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = super.execute(mapping, form, request, response);
        EdoVoteRecordForm voteRecordForm = (EdoVoteRecordForm) form;
        EdoSelectedCandidate selectedCandidate = EdoContext.getSelectedCandidate();
        String workflowId = EdoServiceLocator.getEdoDossierService().getEdoDossierById(selectedCandidate.getCandidateDossierID().toString()).getWorkflowId();
        List<EdoReviewLayerDefinition> reviewLayerDefinitions = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitionsByWorkflowId(workflowId);

        //Collection<EdoReviewLayerDefinition> reviewLayerDefinitions = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions();
        List<EdoReviewLayerDefinition> voteRecordLayerDefinitions = new LinkedList<EdoReviewLayerDefinition>();
        Integer currentDossierId = 0;

        for (EdoReviewLayerDefinition reviewLayerDefinition : reviewLayerDefinitions) {
            if (!reviewLayerDefinition.getVoteType().equals(EdoConstants.VOTE_TYPE_NONE)) {
                voteRecordLayerDefinitions.add(reviewLayerDefinition);
            }
        }

        if (selectedCandidate != null && CollectionUtils.isNotEmpty(reviewLayerDefinitions)) {
            //Set the selected candidate
            voteRecordForm.setSelectedCandidate(selectedCandidate);

            //Get the vote records that the user is able to see.
            List<EdoVoteRecord> voteRecords = EdoServiceLocator.getEdoVoteRecordService().getVoteRecords(selectedCandidate.getCandidateDossierID().intValue(), voteRecordLayerDefinitions);
            voteRecordForm.setVoteRecords(voteRecords);

            //Determine the current review layer definition
            DossierProcessDocumentHeader documentHeader = EdoServiceLocator.getDossierProcessDocumentHeaderService().getDossierProcessDocumentHeader(selectedCandidate.getCandidateDossierID().intValue());
            EdoReviewLayerDefinition currentReviewLayerDefinition = null;
            if (documentHeader != null) {
                currentDossierId = documentHeader.getDossierId();
                List<String> currentNodes = KEWServiceLocator.getRouteNodeService().getCurrentRouteNodeNames(documentHeader.getDocumentId());
                Set<String> currentNodesSet = new HashSet<String>();
                currentNodesSet.addAll(currentNodes);
                Collection<EdoReviewLayerDefinition> currentReviewLayerDefinitions = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions(workflowId, currentNodesSet);
                //List<EdoReviewLayerDefinition> currentReviewLayerDefinitions = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitionsByWorkflowId(workflowId);
                if (CollectionUtils.isNotEmpty(currentReviewLayerDefinitions)) {
                   // currentReviewLayerDefinition = currentReviewLayerDefinitions.get(0);
                    currentReviewLayerDefinition = (EdoReviewLayerDefinition)CollectionUtils.get(currentReviewLayerDefinitions, 0);
                }

                if (currentReviewLayerDefinition != null) {
                    voteRecordForm.setCurrentReviewLayerDefinition(currentReviewLayerDefinition);
                }

                //Determine the current vote record.
                for (EdoVoteRecord voteRecord : voteRecords) {
                    if (currentReviewLayerDefinition != null && voteRecord.getReviewLayerDefinitionId().equals(currentReviewLayerDefinition.getId())) {
                        voteRecordForm.setCurrentVoteRecord(voteRecord);
                    }
                }
            }

            request.setAttribute("hasSupplementalWaiting", false);

            // check to see if there are pending supplemental documents that need a vote
            List<DossierProcessDocumentHeader> suppDocHeaders = EdoServiceLocator.getDossierProcessDocumentHeaderService().getPendingSupplementalDocuments(currentDossierId);
            if (CollectionUtils.isNotEmpty(suppDocHeaders)) {
                boolean isWaiting = false;
                for (DossierProcessDocumentHeader docHeader : suppDocHeaders) {
                    isWaiting = isWaiting || EdoServiceLocator.getEdoSupplementalPendingStatusService().isWaiting(docHeader.getDocumentId(),EdoContext.getPrincipalId());
                }
                if (isWaiting) {
                    request.setAttribute("hasSupplementalWaiting", true);
                    //List<BigDecimal> authorizedVoteRecordLevels = EdoContext.getAuthorizedEditVoteRecordLevels();
                    for (EdoReviewLayerDefinition reviewLayerDefinition : voteRecordLayerDefinitions) {
                        if (reviewLayerDefinition.getReviewLevel() != null && reviewLayerDefinition.getReviewLevel().equals(EdoContext.getPrincipalMaxReviewLevel())) {
                            voteRecordForm.setPrincipalReviewLayerDefinition(reviewLayerDefinition);
                            EdoVoteRecord vr = EdoServiceLocator.getEdoVoteRecordService().getVoteRecordMostCurrentRound(selectedCandidate.getCandidateDossierID().intValue(),reviewLayerDefinition.getId());
                            voteRecordForm.setCurrentVoteRound(vr.getVoteRound());
                            voteRecordForm.setCurrentVoteSubRound(vr.getVoteSubRound());
                        }
                    }
                }
            }

            // check for RECONSIDER status on the dossier
            request.setAttribute("hasReconsiderStatus", false);
            if (selectedCandidate.getDossierStatus().equalsIgnoreCase(EdoConstants.DOSSIER_STATUS.RECONSIDERATION)) {
                EdoReviewLayerDefinition firstNegativeRLD = null;
                // if the RECONSIDER dossier has not been submitted by the candidate, there is no eDoc, so find first negative vote without reference to route level
                if (documentHeader == null) {
                    firstNegativeRLD = EdoServiceLocator.getEdoVoteRecordService().findFirstNegativeReviewLayerByVote(selectedCandidate.getCandidateDossierID().intValue());
                } else {
                    // otherwise, use the current review layer as determined from workflow, as above
                    firstNegativeRLD = currentReviewLayerDefinition;
                }

                request.setAttribute("hasReconsiderStatus", true);
                for (EdoReviewLayerDefinition reviewLayerDefinition : voteRecordLayerDefinitions) {
                    if (reviewLayerDefinition.getReviewLevel() != null
                            && (reviewLayerDefinition.getReviewLevel().compareTo(EdoContext.getPrincipalMaxReviewLevel()) < 1 )
                            && reviewLayerDefinition.getId().equals(firstNegativeRLD.getId())) {
                        voteRecordForm.setPrincipalReviewLayerDefinition(reviewLayerDefinition);
                        EdoVoteRecord vr = EdoServiceLocator.getEdoVoteRecordService().getVoteRecordMostCurrentRound(selectedCandidate.getCandidateDossierID().intValue(),reviewLayerDefinition.getId());
                        voteRecordForm.setCurrentVoteRound(vr.getVoteRound());
                    }
                }
            }
        }

        if (selectedCandidate != null) {
            request.setAttribute("dossierReadyForRoute", EdoRule.isDossierReadyForRoute(selectedCandidate.getCandidateDossierID()));
        } else {
            request.setAttribute("dossierReadyForRoute", false);
        }

        return forward;
    }

    public ActionForward saveVoteRecord(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoVoteRecordForm voteRecordForm = (EdoVoteRecordForm) form;

        EdoSelectedCandidate selectedCandidate = EdoContext.getSelectedCandidate();
        String workflowId = EdoServiceLocator.getEdoDossierService().getEdoDossierById(selectedCandidate.getCandidateDossierID().toString()).getWorkflowId();

        EdoReviewLayerDefinition reviewLayerDefinition = null;
        if (voteRecordForm.getReviewLayerDefinitionId() != null) {
            reviewLayerDefinition = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinition(workflowId, voteRecordForm.getReviewLayerDefinitionId());
        }

        if (selectedCandidate != null && reviewLayerDefinition != null) {
            EdoVoteRecord voteRecord = new EdoVoteRecord();
            //If this is an update
            if (voteRecordForm.getVoteRecordId() != null) {
                voteRecord = EdoServiceLocator.getEdoVoteRecordService().getVoteRecord(voteRecordForm.getVoteRecordId());
            } else {
                String[] voteParts = voteRecordForm.getVoteRoundString().split("\\.");
                voteRecord.setCreatedAt(EdoUtils.getNowTS());
                voteRecord.setCreatedBy(EdoContext.getUser().getNetworkId());
                voteRecord.setDossierId(selectedCandidate.getCandidateDossierID().intValue());
                voteRecord.setReviewLayerDefinitionId(reviewLayerDefinition.getReviewLayerDefinitionId());
                voteRecord.setVoteType(reviewLayerDefinition.getVoteType());
                voteRecord.setVoteRound(Integer.parseInt(voteParts[0]));
                voteRecord.setVoteSubRound(Integer.parseInt(voteParts[1]));
            }

            //Update fields.
            if (selectedCandidate.getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE) || selectedCandidate.getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE_PROMOTION)) {
                // tenure votes
                if (voteRecord.getVoteType().equals(EdoConstants.VOTE_TYPE_SINGLE)) {
                    voteRecord.setAbsentCountTenure(0);
                    voteRecord.setAbstainCountTenure(0);
                } else {
                    voteRecord.setAbsentCountTenure(voteRecordForm.getAbsentCountTenure());
                    voteRecord.setAbstainCountTenure(voteRecordForm.getAbstainCountTenure());
                }
                voteRecord.setNoCountTenure(voteRecordForm.getNoCountTenure());
                voteRecord.setYesCountTenure(voteRecordForm.getYesCountTenure());
            }
            if (selectedCandidate.getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_PROMOTION) || selectedCandidate.getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE_PROMOTION)) {

                // promotion votes
                if (voteRecord.getVoteType().equals(EdoConstants.VOTE_TYPE_SINGLE)) {
                    voteRecord.setAbsentCountPromotion(0);
                    voteRecord.setAbstainCountPromotion(0);
                } else {
                    voteRecord.setAbsentCountPromotion(voteRecordForm.getAbsentCountPromotion());
                    voteRecord.setAbstainCountPromotion(voteRecordForm.getAbstainCountPromotion());
                }
                voteRecord.setNoCountPromotion(voteRecordForm.getNoCountPromotion());
                voteRecord.setYesCountPromotion(voteRecordForm.getYesCountPromotion());
            }

            voteRecord.setAoeCode(voteRecordForm.getAoeCode());
            voteRecord.setUpdatedAt(EdoUtils.getNowTS());
            voteRecord.setUpdatedBy(EdoContext.getUser().getNetworkId());

            if (!EdoVoteRecordValidation.validateVoteRecord(voteRecord)) {
                return mapping.findForward("basic");
            }

            EdoServiceLocator.getEdoVoteRecordService().saveOrUpdate(voteRecord);
        }

        String prevPage = request.getHeader("REFERER");
        return new ActionRedirect(prevPage);
    }
}
