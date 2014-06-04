package org.kuali.kpme.edo.vote.dao;

import org.kuali.kpme.edo.vote.EdoVoteRecord;

import java.math.BigDecimal;
import java.util.List;

public interface EdoVoteRecordDao {

    public EdoVoteRecord getVoteRecord(BigDecimal voteRecordId);
    public List<EdoVoteRecord> getVoteRecords(Integer dossierId);
    public List<EdoVoteRecord> getVoteRecords(Integer dossierId, List<BigDecimal> reviewLayerDefinitionIds);
    public void saveOrUpdate(EdoVoteRecord voteRecord);
    public List<EdoVoteRecord> getVoteRecords(Integer dossierId, BigDecimal reviewLayerDefinitionId);
    public EdoVoteRecord getVoteRecordMostCurrentRound(Integer dossierId, BigDecimal reviewLayerDefinitionId);
    }
