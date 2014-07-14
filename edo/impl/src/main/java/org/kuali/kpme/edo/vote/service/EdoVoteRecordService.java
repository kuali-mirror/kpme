package org.kuali.kpme.edo.vote.service;

import java.util.List;

import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinitionBo;
import org.kuali.kpme.edo.api.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.api.vote.EdoVoteRecord;

public interface EdoVoteRecordService {

    public EdoVoteRecord getEdoVoteRecord(String edoVoteRecordID);
    public List<EdoVoteRecord> getVoteRecords(String edoDossierID, List<EdoReviewLayerDefinition> edoReviewLayerDefinitions);    
    public void saveOrUpdate(EdoVoteRecord voteRecord);
    public List<EdoVoteRecord> getVoteRecords(String edoDossierID, String edoReviewLayerDefinitionId);
    public EdoVoteRecord getVoteRecordMostCurrentRound(String edoDossierID, String edoReviewLayerDefinitionId);
    public boolean isNegativeVote(EdoVoteRecord edoVoteRecord);
    public EdoReviewLayerDefinition findFirstNegativeReviewLayerByVote(String edoDossierID);
    
}
