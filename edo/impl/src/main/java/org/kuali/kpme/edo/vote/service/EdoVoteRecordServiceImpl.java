package org.kuali.kpme.edo.vote.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.kuali.kpme.edo.api.vote.EdoVoteRecord;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoContext;
import org.kuali.kpme.edo.vote.EdoVoteRecordBo;
import org.kuali.kpme.edo.vote.dao.EdoVoteRecordDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.util.ObjectUtils;

public class EdoVoteRecordServiceImpl implements EdoVoteRecordService {

    private EdoVoteRecordDao edoVoteRecordDao;

    public EdoVoteRecord getEdoVoteRecord(String edoVoteRecordID) {
    	EdoVoteRecordBo edoVoteRecordBo = edoVoteRecordDao.getEdoVoteRecord(edoVoteRecordID);
    	
    	if ( edoVoteRecordBo == null){
    		return null;
    	}
    	
    	EdoVoteRecord.Builder builder = EdoVoteRecord.Builder.create(edoVoteRecordBo);
    	
    	return builder.build();
    }

    @Override
    public List<EdoVoteRecord> getVoteRecords(String edoDossierID, List<EdoReviewLayerDefinition> edoReviewLayerDefinitions) {
        List<EdoVoteRecord> voteRecords = new LinkedList<EdoVoteRecord>();

        if (ObjectUtils.isNotNull(edoDossierID)) {
            //Get the doc header.
            List<BigDecimal> authorizedViewLevels = EdoContext.getAuthorizedViewVoteRecordLevels();
            List<String> reviewLayerIds = new LinkedList<String>();

            //Get the review layer ids for vote record.
            for(EdoReviewLayerDefinition reviewLayerDefinition : edoReviewLayerDefinitions) {
            	System.out.println("get in here!!!!!!!!!");
                if (authorizedViewLevels.contains(reviewLayerDefinition.getReviewLevel())) {
                    reviewLayerIds.add(reviewLayerDefinition.getReviewLayerDefinitionId().toString());
                }
            }
            System.out.println("number of voteRecord returned>>>>>>" + edoVoteRecordDao.getVoteRecords(edoDossierID, reviewLayerIds).size());
            return ModelObjectUtils.transform(edoVoteRecordDao.getVoteRecords(edoDossierID, reviewLayerIds), EdoVoteRecordBo.toImmutable);  
        }

        return voteRecords;
    }

    public boolean isNegativeVote(EdoVoteRecord edoVoteRecord) {
        boolean isNegative = false;
//        if ((edoVoteRecord.getNoCountTenure() != null && edoVoteRecord.getYesCountTenure() != null && (edoVoteRecord.getNoCountTenure() > edoVoteRecord.getYesCountTenure())) ||
//           (edoVoteRecord.getNoCountPromotion() != null && edoVoteRecord.getYesCountPromotion() != null && (edoVoteRecord.getNoCountPromotion() > edoVoteRecord.getYesCountPromotion()))) {
//      
        if ((edoVoteRecord.getNoCount() != null && edoVoteRecord.getYesCount() != null && (edoVoteRecord.getNoCount() > edoVoteRecord.getYesCount()))) {
                 
        	isNegative = true;
        }
        return isNegative;
    }

    public EdoReviewLayerDefinition findFirstNegativeReviewLayerByVote(String edoDossierID) {
        EdoReviewLayerDefinition rld = null;
        String workflowId = EdoServiceLocator.getEdoDossierService().getEdoDossierById(edoDossierID).getWorkflowId();

        Collection<EdoReviewLayerDefinition> reviewLayerDefinitions = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions(workflowId);
        List<EdoReviewLayerDefinition> voteRecordLayerDefinitions = new LinkedList<EdoReviewLayerDefinition>();

        for (EdoReviewLayerDefinition reviewLayerDefinition : reviewLayerDefinitions) {
            if (!reviewLayerDefinition.getVoteType().equals(EdoConstants.VOTE_TYPE_NONE)) {
                voteRecordLayerDefinitions.add(reviewLayerDefinition);
            }
        }
        for (EdoReviewLayerDefinition voteRLD : voteRecordLayerDefinitions) {
            EdoVoteRecord voteRecord = getVoteRecordMostCurrentRound(edoDossierID, voteRLD.getReviewLayerDefinitionId().toString());
            if (isNegativeVote(voteRecord)) {
                if (rld == null) {
                    rld = voteRLD;
                    continue;
                }
                if (voteRLD.getReviewLevel().compareTo(rld.getReviewLevel()) == -1 ) {
                    rld = voteRLD;
                }
            }
        }
        return rld;
    }

    public EdoVoteRecord getVoteRecordMostCurrentRound(String edoDossierID, String edoReviewLayerDefinitionID) {
    	EdoVoteRecordBo edoVoteRecordBo = edoVoteRecordDao.getVoteRecordMostCurrentRound(edoDossierID, edoReviewLayerDefinitionID);
    	
    	if ( edoVoteRecordBo == null){
    		return null;
    	}
    	
    	EdoVoteRecord.Builder builder = EdoVoteRecord.Builder.create(edoVoteRecordBo);
    	
    	return builder.build();
    }

    public List<EdoVoteRecord> getVoteRecords(String edoDossierID, String edoReviewLayerDefinitionID) {
    	
    	return ModelObjectUtils.transform(edoVoteRecordDao.getVoteRecords(edoDossierID, edoReviewLayerDefinitionID), EdoVoteRecordBo.toImmutable); 
    }

    public void setEdoVoteRecordDao(EdoVoteRecordDao edoVoteRecordDao) {
    	
        this.edoVoteRecordDao = edoVoteRecordDao;
    }

    public EdoVoteRecordDao getEdoVoteRecordDao() {
    	
        return edoVoteRecordDao;
    }

    @Override
    public void saveOrUpdate(EdoVoteRecord voteRecord) {
    	
    	EdoVoteRecordBo bo = EdoVoteRecordBo.from(voteRecord);
    	
        this.edoVoteRecordDao.saveOrUpdate(bo);
    }
}
