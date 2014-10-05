package org.kuali.kpme.edo.vote.dao;

import org.kuali.kpme.edo.api.vote.EdoVoteRecord;
import org.kuali.kpme.edo.vote.EdoVoteRecordBo;

import java.math.BigDecimal;
import java.util.List;

public interface EdoVoteRecordDao {
	public EdoVoteRecordBo getEdoVoteRecord(String edoVoteRecordID);


    public List<EdoVoteRecordBo> getVoteRecords(String edoDossierID);
    public List<EdoVoteRecordBo> getVoteRecords(String edoDossierID, List<String> edoReviewLayerDefinitionIDs);
    public void saveOrUpdate(EdoVoteRecordBo voteRecord);
    public List<EdoVoteRecordBo> getVoteRecords(String edoDossierID, String edoReviewLayerDefinitionId);
    public EdoVoteRecordBo getVoteRecordMostCurrentRound(String edoDossierID, String edoReviewLayerDefinitionId);
    }
