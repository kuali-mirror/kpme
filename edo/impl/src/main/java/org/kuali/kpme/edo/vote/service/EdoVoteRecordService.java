package org.kuali.kpme.edo.vote.service;

import java.util.List;

import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.api.vote.EdoVoteRecord;

public interface EdoVoteRecordService {

    public EdoVoteRecord getEdoVoteRecord(String edoVoteRecordID);
    public List<EdoVoteRecord> getVoteRecords(String edoDossierID, List<EdoReviewLayerDefinition> edoReviewLayerDefinitions);    
    public void saveOrUpdate(EdoVoteRecord voteRecord);
    public List<EdoVoteRecord> getVoteRecords(String edoDossierID, String edoReviewLayerDefinitionID);
    public EdoVoteRecord getVoteRecordMostCurrentRound(String edoDossierID, String edoReviewLayerDefinitionID);
    public boolean isNegativeVote(EdoVoteRecord edoVoteRecord);
    public EdoReviewLayerDefinition findFirstNegativeReviewLayerByVote(String edoDossierID);
    
}
