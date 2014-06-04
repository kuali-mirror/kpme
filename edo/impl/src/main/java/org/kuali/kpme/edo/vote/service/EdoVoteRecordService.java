package org.kuali.kpme.edo.vote.service;

import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.vote.EdoVoteRecord;

import java.math.BigDecimal;
import java.util.List;

public interface EdoVoteRecordService {

    public EdoVoteRecord getVoteRecord(BigDecimal voteRecordId);
    public List<EdoVoteRecord> getVoteRecords(Integer dossierId, List<EdoReviewLayerDefinition> reviewLayerDefinitions);
    public void saveOrUpdate(EdoVoteRecord voteRecord);
    public List<EdoVoteRecord> getVoteRecords(Integer dossierId, BigDecimal reviewLayerDefinitionId);
    public EdoVoteRecord getVoteRecordMostCurrentRound(Integer dossierId, BigDecimal reviewLayerDefinitionId);
    public boolean isNegativeVote(EdoVoteRecord edoVoteRecord);
    public EdoReviewLayerDefinition findFirstNegativeReviewLayerByVote(Integer dossierId);
}
