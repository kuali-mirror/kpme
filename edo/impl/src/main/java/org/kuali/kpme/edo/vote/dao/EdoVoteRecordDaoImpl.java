package org.kuali.kpme.edo.vote.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.vote.EdoVoteRecordBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;
import org.kuali.rice.krad.util.ObjectUtils;

public class EdoVoteRecordDaoImpl extends PlatformAwareDaoBaseOjb implements EdoVoteRecordDao {
    private static final Logger LOG = Logger.getLogger(EdoVoteRecordDaoImpl.class);

    public EdoVoteRecordBo getEdoVoteRecord(String edoVoteRecordID) {
 
        Criteria criteria = new Criteria();
        criteria.addEqualTo("edoVoteRecordID", edoVoteRecordID);

        Query query = QueryFactory.newQuery(EdoVoteRecordBo.class, criteria);

        return (EdoVoteRecordBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);        
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<EdoVoteRecordBo> getVoteRecords(String edoDossierID) {
        List<EdoVoteRecordBo> voteRecords = new ArrayList<EdoVoteRecordBo>();

        Criteria criteria = new Criteria();
        criteria.addEqualTo("edoDossierID", edoDossierID);

        QueryByCriteria query = QueryFactory.newQuery(EdoVoteRecordBo.class, criteria);
        // TODO: add sorting after reviewLayerDefinition is done
        //query.addOrderByAscending("reviewLayerDefinition.reviewLevel");
        query.addOrderByAscending("vote_round");
        query.addOrderByAscending("vote_subround");

        Collection c =  this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        
        if (c != null && c.size() != 0) {
        	voteRecords.addAll(c);
        }
        
        return voteRecords;
    }

    
    public List<EdoVoteRecordBo> getVoteRecords(String edoDossierID, List<String> edoReviewLayerDefinitionIDs) {
        List<EdoVoteRecordBo> voteRecords = new ArrayList<EdoVoteRecordBo>();

        if (ObjectUtils.isNotNull(edoDossierID) && CollectionUtils.isNotEmpty(edoReviewLayerDefinitionIDs)) {
            Criteria criteria = new Criteria();
            criteria.addEqualTo("edoDossierID", edoDossierID);
            criteria.addIn("edoReviewLayerDefinitionID", edoReviewLayerDefinitionIDs);

            QueryByCriteria query = QueryFactory.newQuery(EdoVoteRecordBo.class, criteria);
            // TODO: add sorting after reviewLayerDefinition is done
            //query.addOrderByAscending("reviewLayerDefinition.reviewLevel");
            query.addOrderByAscending("vote_round");
            query.addOrderByAscending("vote_subround");

            Collection c =  this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            
            if (c != null && c.size() != 0) {
            	voteRecords.addAll(c);
            }
            
            return voteRecords;
        }

        return voteRecords;
    }

    public List<EdoVoteRecordBo> getVoteRecords(String edoDossierID, String edoReviewLayerDefinitionID) {
        List<EdoVoteRecordBo> voteRecords = new ArrayList<EdoVoteRecordBo>();

        if (ObjectUtils.isNotNull(edoDossierID) && edoReviewLayerDefinitionID != null) {
            Criteria criteria = new Criteria();
            criteria.addEqualTo("edoDossierID", edoDossierID);
            criteria.addEqualTo("edoReviewLayerDefinitionID", edoReviewLayerDefinitionID);

            QueryByCriteria query = QueryFactory.newQuery(EdoVoteRecordBo.class, criteria);
            query.addOrderByAscending("vote_round");
            query.addOrderByAscending("vote_subround");

            Collection c =  this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            
            if (c != null && c.size() != 0) {
            	voteRecords.addAll(c);
            }
        }

        return voteRecords;
    }

    public EdoVoteRecordBo getVoteRecordMostCurrentRound(String edoDossierID, String edoReviewLayerDefinitionID) {
        EdoVoteRecordBo voteRecord = new EdoVoteRecordBo();

        if (ObjectUtils.isNotNull(edoDossierID) && edoReviewLayerDefinitionID != null) {
            Criteria criteria = new Criteria();
            criteria.addEqualTo("edoDossierID", edoDossierID);
            criteria.addEqualTo("edoReviewLayerDefinitionID", edoReviewLayerDefinitionID);

            QueryByCriteria query = QueryFactory.newQuery(EdoVoteRecordBo.class, criteria);
            query.addOrderByDescending("vote_round");
            query.addOrderByDescending("vote_subround");

            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

            if (CollectionUtils.isNotEmpty(c)) {
                voteRecord = (EdoVoteRecordBo)c.toArray()[0];
            }
        }

        return voteRecord;
    }

    public void saveOrUpdate(EdoVoteRecordBo voteRecord) {
        this.getPersistenceBrokerTemplate().store(voteRecord);
    }

    public void saveOrUpdate(List<EdoVoteRecordBo> voteRecords) {
        if (voteRecords != null && voteRecords.size() > 0) {
            for (EdoVoteRecordBo voteRecord : voteRecords) {
                this.getPersistenceBrokerTemplate().store(voteRecord);
            }
        }
    }

}
