package org.kuali.kpme.edo.vote.dao;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.vote.EdoVoteRecord;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;
import org.kuali.rice.krad.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EdoVoteRecordDaoImpl extends PlatformAwareDaoBaseOjb implements EdoVoteRecordDao {
    private static final Logger LOG = Logger.getLogger(EdoVoteRecordDaoImpl.class);

    public EdoVoteRecord getVoteRecord(BigDecimal voteRecordId) {
        Criteria criteria = new Criteria();
        criteria.addEqualTo("voteRecordId", voteRecordId);

        QueryByCriteria query = QueryFactory.newQuery(EdoVoteRecord.class, criteria);

        return (EdoVoteRecord) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<EdoVoteRecord> getVoteRecords(Integer dossierId) {
        List<EdoVoteRecord> voteRecords = new ArrayList<EdoVoteRecord>();

        Criteria criteria = new Criteria();
        criteria.addEqualTo("dossierId", dossierId);

        QueryByCriteria query = QueryFactory.newQuery(EdoVoteRecord.class, criteria);
        query.addOrderByAscending("reviewLayerDefinition.reviewLevel");
        query.addOrderByAscending("vote_round");
        query.addOrderByAscending("vote_subround");

        voteRecords.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return voteRecords;
    }

    public List<EdoVoteRecord> getVoteRecords(Integer dossierId, List<BigDecimal> reviewLayerDefinitionIds) {
        List<EdoVoteRecord> voteRecords = new ArrayList<EdoVoteRecord>();

        if (ObjectUtils.isNotNull(dossierId) && CollectionUtils.isNotEmpty(reviewLayerDefinitionIds)) {
            Criteria criteria = new Criteria();
            criteria.addEqualTo("dossierId", dossierId);
            criteria.addIn("reviewLayerDefinitionId", reviewLayerDefinitionIds);

            QueryByCriteria query = QueryFactory.newQuery(EdoVoteRecord.class, criteria);
            query.addOrderByAscending("reviewLayerDefinition.reviewLevel");
            query.addOrderByAscending("vote_round");
            query.addOrderByAscending("vote_subround");

            voteRecords.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));
        }

        return voteRecords;
    }

    public List<EdoVoteRecord> getVoteRecords(Integer dossierId, BigDecimal reviewLayerDefinitionId) {
        List<EdoVoteRecord> voteRecords = new ArrayList<EdoVoteRecord>();

        if (ObjectUtils.isNotNull(dossierId) && reviewLayerDefinitionId != null) {
            Criteria criteria = new Criteria();
            criteria.addEqualTo("dossierId", dossierId);
            criteria.addEqualTo("reviewLayerDefinitionId", reviewLayerDefinitionId);

            QueryByCriteria query = QueryFactory.newQuery(EdoVoteRecord.class, criteria);
            query.addOrderByAscending("vote_round");
            query.addOrderByAscending("vote_subround");

            voteRecords.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));
        }

        return voteRecords;
    }

    public EdoVoteRecord getVoteRecordMostCurrentRound(Integer dossierId, BigDecimal reviewLayerDefinitionId) {
        EdoVoteRecord voteRecord = new EdoVoteRecord();

        if (ObjectUtils.isNotNull(dossierId) && reviewLayerDefinitionId != null) {
            Criteria criteria = new Criteria();
            criteria.addEqualTo("dossierId", dossierId);
            criteria.addEqualTo("reviewLayerDefinitionId", reviewLayerDefinitionId);

            QueryByCriteria query = QueryFactory.newQuery(EdoVoteRecord.class, criteria);
            query.addOrderByDescending("vote_round");
            query.addOrderByDescending("vote_subround");

            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

            if (CollectionUtils.isNotEmpty(c)) {
                voteRecord = (EdoVoteRecord)c.toArray()[0];
            }
        }

        return voteRecord;
    }

    public void saveOrUpdate(EdoVoteRecord voteRecord) {
        this.getPersistenceBrokerTemplate().store(voteRecord);
    }

    public void saveOrUpdate(List<EdoVoteRecord> voteRecords) {
        if (voteRecords != null && voteRecords.size() > 0) {
            for (EdoVoteRecord voteRecord : voteRecords) {
                this.getPersistenceBrokerTemplate().store(voteRecord);
            }
        }
    }

}
