package org.kuali.kpme.edo.vote.web;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.kuali.kpme.edo.api.dossier.EdoDossierDocumentInfo;
import org.kuali.kpme.edo.api.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.api.vote.EdoVoteRecord;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.candidate.EdoSelectedCandidate;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinitionBo;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoContext;
import org.kuali.kpme.edo.util.EdoRule;
import org.kuali.kpme.edo.vote.EdoVoteRecordBo;
import org.kuali.kpme.edo.vote.validation.EdoVoteRecordValidation;
import org.kuali.rice.kew.service.KEWServiceLocator;

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
            List<EdoVoteRecord> voteRecords = EdoServiceLocator.getEdoVoteRecordService().getVoteRecords(selectedCandidate.getCandidateDossierID().toString(), voteRecordLayerDefinitions);
            voteRecordForm.setVoteRecords(voteRecords);

            //Determine the current review layer definition
            EdoDossierDocumentInfo documentHeader = EdoServiceLocator.getEdoDossierDocumentInfoService().getEdoDossierDocumentInfoByDossierId(selectedCandidate.getCandidateDossierID().toString());
            EdoReviewLayerDefinition currentReviewLayerDefinition = null;
            if (documentHeader != null) {
                currentDossierId = new Integer(documentHeader.getEdoDossierId());
                List<String> currentNodes = KEWServiceLocator.getRouteNodeService().getCurrentRouteNodeNames(documentHeader.getEdoDocumentId());
                Set<String> currentNodesSet = new HashSet<String>();
                currentNodesSet.addAll(currentNodes);
                Collection<EdoReviewLayerDefinition> currentReviewLayerDefinitions = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions(workflowId, currentNodesSet);
                //List<EdoReviewLayerDefinition> currentReviewLayerDefinitions = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitionsByWorkflowId(workflowId);
                if (CollectionUtils.isNotEmpty(currentReviewLayerDefinitions)) {
                   // currentReviewLayerDefinition = currentReviewLayerDefinitions.get(0);
                    currentReviewLayerDefinition = (EdoReviewLayerDefinition)CollectionUtils.get(currentReviewLayerDefinitions, 0);
                }

                if (currentReviewLayerDefinition != null) {
                    voteRecordForm.setCurrentReviewLayerDefinition(EdoReviewLayerDefinitionBo.from(currentReviewLayerDefinition));
                }

                //Determine the current vote record.
                for (EdoVoteRecord voteRecord : voteRecords) {
                    if (currentReviewLayerDefinition != null && voteRecord.getEdoReviewLayerDefinitionId().equals(currentReviewLayerDefinition.getId().toString())) {
                        voteRecordForm.setCurrentVoteRecord(voteRecord);
                    }
                }
            }

            request.setAttribute("hasSupplementalWaiting", false);

            // check to see if there are pending supplemental documents that need a vote
            List<EdoDossierDocumentInfo> suppDocHeaders = EdoServiceLocator.getEdoDossierDocumentInfoService().getPendingSupplementalDocuments(currentDossierId.toString());
            if (CollectionUtils.isNotEmpty(suppDocHeaders)) {
                boolean isWaiting = false;
                for (EdoDossierDocumentInfo docHeader : suppDocHeaders) {
                    isWaiting = isWaiting || EdoServiceLocator.getEdoSupplementalPendingStatusService().isWaiting(docHeader.getEdoDocumentId(),EdoContext.getPrincipalId());
                }
                if (isWaiting) {
                    request.setAttribute("hasSupplementalWaiting", true);
                    //List<BigDecimal> authorizedVoteRecordLevels = EdoContext.getAuthorizedEditVoteRecordLevels();
                    for (EdoReviewLayerDefinition reviewLayerDefinition : voteRecordLayerDefinitions) {
                        if (reviewLayerDefinition.getReviewLevel() != null && reviewLayerDefinition.getReviewLevel().equals(EdoContext.getPrincipalMaxReviewLevel())) {
                            voteRecordForm.setPrincipalReviewLayerDefinition(EdoReviewLayerDefinitionBo.from(reviewLayerDefinition));
                            EdoVoteRecord vr = EdoServiceLocator.getEdoVoteRecordService().getVoteRecordMostCurrentRound(selectedCandidate.getCandidateDossierID().toString(),reviewLayerDefinition.getId().toString());
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
                    firstNegativeRLD = EdoServiceLocator.getEdoReviewLayerDefinitionService().findFirstNegativeReviewLayerByVote(selectedCandidate.getCandidateDossierID().toString());
                } else {
                    // otherwise, use the current review layer as determined from workflow, as above
                    firstNegativeRLD = currentReviewLayerDefinition;
                }

                request.setAttribute("hasReconsiderStatus", true);
                for (EdoReviewLayerDefinition reviewLayerDefinition : voteRecordLayerDefinitions) {
                    if (reviewLayerDefinition.getReviewLevel() != null
                            && (new BigDecimal(reviewLayerDefinition.getReviewLevel()).compareTo(EdoContext.getPrincipalMaxReviewLevel()) < 1 )
                            && reviewLayerDefinition.getId().equals(firstNegativeRLD.getId())) {
                        voteRecordForm.setPrincipalReviewLayerDefinition(EdoReviewLayerDefinitionBo.from(reviewLayerDefinition));
                        EdoVoteRecord vr = EdoServiceLocator.getEdoVoteRecordService().getVoteRecordMostCurrentRound(selectedCandidate.getCandidateDossierID().toString(),reviewLayerDefinition.getId().toString());
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
            EdoVoteRecordBo voteRecordBo = new EdoVoteRecordBo();
            EdoVoteRecord.Builder builder = EdoVoteRecord.Builder.create();
            EdoVoteRecord voteRecordImmutable =  builder.build();
        	
            //If this is an update
            if (voteRecordForm.getVoteRecordId() != null) {
            	voteRecordImmutable = EdoServiceLocator.getEdoVoteRecordService().getEdoVoteRecord(voteRecordForm.getVoteRecordId());
                voteRecordBo = EdoVoteRecordBo.from(voteRecordImmutable);
            } else {
                String[] voteParts = voteRecordForm.getVoteRoundString().split("\\.");
//                voteRecord.setCreatedAt(EdoUtils.getNowTS());
//                voteRecord.setCreatedBy(EdoContext.getUser().getNetworkId());
//                voteRecord.setDossierId(selectedCandidate.getCandidateDossierID().intValue());
//                voteRecord.setReviewLayerDefinitionId(reviewLayerDefinition.getReviewLayerDefinitionId());
                
                voteRecordBo.setEdoDossierId(selectedCandidate.getCandidateDossierID().toString());
                voteRecordBo.setEdoReviewLayerDefinitionId(reviewLayerDefinition.getEdoReviewLayerDefinitionId());
                
                voteRecordBo.setVoteType(reviewLayerDefinition.getVoteType());
                voteRecordBo.setVoteRound(Integer.parseInt(voteParts[0]));
                voteRecordBo.setVoteSubRound(Integer.parseInt(voteParts[1]));
            }

            //Update fields.
            if (selectedCandidate.getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE) || selectedCandidate.getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE_PROMOTION)) {
                // tenure votes
                if (voteRecordBo.getVoteType().equals(EdoConstants.VOTE_TYPE_SINGLE)) {
                	voteRecordBo.setAbsentCount(0);
                	voteRecordBo.setAbstainCount(0);
                } else {
                	voteRecordBo.setAbsentCount(voteRecordForm.getAbsentCountTenure());
                	voteRecordBo.setAbstainCount(voteRecordForm.getAbstainCountTenure());
                }
                voteRecordBo.setNoCount(voteRecordForm.getNoCountTenure());
                voteRecordBo.setYesCount(voteRecordForm.getYesCountTenure());
            }
            if (selectedCandidate.getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_PROMOTION) || selectedCandidate.getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE_PROMOTION)) {

                // promotion votes
                if (voteRecordBo.getVoteType().equals(EdoConstants.VOTE_TYPE_SINGLE)) {
                	voteRecordBo.setAbsentCount(0);
                	voteRecordBo.setAbstainCount(0);
                } else {
                	voteRecordBo.setAbsentCount(voteRecordForm.getAbsentCountPromotion());
                	voteRecordBo.setAbstainCount(voteRecordForm.getAbstainCountPromotion());
                }
                voteRecordBo.setNoCount(voteRecordForm.getNoCountPromotion());
                voteRecordBo.setYesCount(voteRecordForm.getYesCountPromotion());
            }

            voteRecordBo.setAoeCode(voteRecordForm.getAoeCode());
//            voteRecord.setUpdatedAt(EdoUtils.getNowTS());
//            voteRecord.setUpdatedBy(EdoContext.getUser().getNetworkId());
//            voteRecordBo.setTimestamp(EdoUtils.getNowTS());
//            voteRecordBo.setUserPrincipalId(EdoContext.getUser().getNetworkId());

            if (!EdoVoteRecordValidation.validateVoteRecord(voteRecordBo)) {
                return mapping.findForward("basic");
            }

            //EdoServiceLocator.getEdoVoteRecordService().saveOrUpdate(voteRecordBo);
            EdoServiceLocator.getEdoVoteRecordService().saveOrUpdate(EdoVoteRecordBo.to(voteRecordBo));
        }

        String prevPage = request.getHeader("REFERER");
        return new ActionRedirect(prevPage);
    }
}
