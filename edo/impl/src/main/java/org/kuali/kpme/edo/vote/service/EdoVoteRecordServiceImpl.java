package org.kuali.kpme.edo.vote.service;

import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoContext;
import org.kuali.kpme.edo.vote.EdoVoteRecord;
import org.kuali.kpme.edo.vote.dao.EdoVoteRecordDao;
import org.kuali.rice.krad.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class EdoVoteRecordServiceImpl implements EdoVoteRecordService {

    private EdoVoteRecordDao edoVoteRecordDao;

    public EdoVoteRecord getVoteRecord(BigDecimal voteRecordId) {
        return this.getEdoVoteRecordDao().getVoteRecord(voteRecordId);
    }

    @Override
    public List<EdoVoteRecord> getVoteRecords(Integer dossierId, List<EdoReviewLayerDefinition> reviewLayerDefinitions) {
        List<EdoVoteRecord> voteRecords = new LinkedList<EdoVoteRecord>();

        if (ObjectUtils.isNotNull(dossierId)) {
            //Get the doc header.
            List<BigDecimal> authorizedViewLevels = EdoContext.getAuthorizedViewVoteRecordLevels();
            List<BigDecimal> reviewLayerIds = new LinkedList<BigDecimal>();

            //Get the review layer ids for vote record.
            for(EdoReviewLayerDefinition reviewLayerDefinition : reviewLayerDefinitions) {
                if (authorizedViewLevels.contains(reviewLayerDefinition.getReviewLevel())) {
                    reviewLayerIds.add(reviewLayerDefinition.getReviewLayerDefinitionId());
                }
            }

            //Return the records.
            return this.edoVoteRecordDao.getVoteRecords(dossierId, reviewLayerIds);
        }

        return voteRecords;
    }

    public boolean isNegativeVote(EdoVoteRecord edoVoteRecord) {
        boolean isNegative = false;
        if ((edoVoteRecord.getNoCountTenure() != null && edoVoteRecord.getYesCountTenure() != null && (edoVoteRecord.getNoCountTenure() > edoVoteRecord.getYesCountTenure())) ||
           (edoVoteRecord.getNoCountPromotion() != null && edoVoteRecord.getYesCountPromotion() != null && (edoVoteRecord.getNoCountPromotion() > edoVoteRecord.getYesCountPromotion()))) {
            isNegative = true;
        }
        return isNegative;
    }

    public EdoReviewLayerDefinition findFirstNegativeReviewLayerByVote(Integer dossierId) {
        EdoReviewLayerDefinition rld = null;
        String workflowId = EdoServiceLocator.getEdoDossierService().getDossierById(BigDecimal.valueOf(dossierId)).getWorkflowId();

        Collection<EdoReviewLayerDefinition> reviewLayerDefinitions = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions(workflowId);
        List<EdoReviewLayerDefinition> voteRecordLayerDefinitions = new LinkedList<EdoReviewLayerDefinition>();

        for (EdoReviewLayerDefinition reviewLayerDefinition : reviewLayerDefinitions) {
            if (!reviewLayerDefinition.getVoteType().equals(EdoConstants.VOTE_TYPE_NONE)) {
                voteRecordLayerDefinitions.add(reviewLayerDefinition);
            }
        }
        for (EdoReviewLayerDefinition voteRLD : voteRecordLayerDefinitions) {
            EdoVoteRecord voteRecord = getVoteRecordMostCurrentRound(dossierId, voteRLD.getReviewLayerDefinitionId());
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

    public EdoVoteRecord getVoteRecordMostCurrentRound(Integer dossierId, BigDecimal reviewLayerDefinitionId) {
        return edoVoteRecordDao.getVoteRecordMostCurrentRound(dossierId, reviewLayerDefinitionId);
    }

    public List<EdoVoteRecord> getVoteRecords(Integer dossierId, BigDecimal reviewLayerDefinitionId) {
        return edoVoteRecordDao.getVoteRecords(dossierId, reviewLayerDefinitionId);
    }

    public void setEdoVoteRecordDao(EdoVoteRecordDao edoVoteRecordDao) {
        this.edoVoteRecordDao = edoVoteRecordDao;
    }

    public EdoVoteRecordDao getEdoVoteRecordDao() {
        return edoVoteRecordDao;
    }

    @Override
    public void saveOrUpdate(EdoVoteRecord voteRecord) {
        this.edoVoteRecordDao.saveOrUpdate(voteRecord);
    }
}
