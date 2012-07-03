package org.kuali.hr.time.batch.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.batch.BatchJobEntry;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class BatchJobEntryDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements BatchJobEntryDao {

    @Override
    public void saveOrUpdate(BatchJobEntry batchJobEntry) {
        this.getPersistenceBrokerTemplate().store(batchJobEntry);
    }

    @Override
    public BatchJobEntry getBatchJobEntry(Long batchJobEntryId) {
        Criteria currentRecordCriteria = new Criteria();
        currentRecordCriteria.addEqualTo("tkBatchJobEntryId", batchJobEntryId);

        return (BatchJobEntry) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(BatchJobEntry.class, currentRecordCriteria));
    }

    @Override
    public List<BatchJobEntry> getBatchJobEntries(Long batchJobEntryId) {
        Criteria root = new Criteria();
        root.addEqualTo("tkBatchJobId", batchJobEntryId);
        Query query = QueryFactory.newQuery(BatchJobEntry.class, root);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        List<BatchJobEntry> entries = new ArrayList<BatchJobEntry>();
        entries.addAll(c);

        return entries;
    }

    @Override
    public List<BatchJobEntry> getBatchJobEntries(String ip, String status) {
        Criteria root = new Criteria();
        root.addEqualTo("ipAddress", ip);
        root.addEqualTo("batchJobEntryStatus", status);
        Query query = QueryFactory.newQuery(BatchJobEntry.class, root);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        List<BatchJobEntry> entries = new ArrayList<BatchJobEntry>();
        entries.addAll(c);

        return entries;
    }
    
    @Override
    public List<BatchJobEntry> getBatchJobEntries(Map<String, Object> criteria) {
        Criteria root = new Criteria();
        for (Map.Entry<String, Object> crit : criteria.entrySet()) {
            if(crit.getValue() != null){
                if(StringUtils.equals("ipAddress", crit.getKey()) || StringUtils.equals("batchJobName", crit.getKey())) {
                    root.addLike(crit.getKey(), "%" + crit.getValue() + "%");
                }
                else {
                    root.addEqualTo(crit.getKey(), crit.getValue());
                }
            }
        }

        Query query = QueryFactory.newQuery(BatchJobEntry.class, root);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        List<BatchJobEntry> entries = new ArrayList<BatchJobEntry>();
        entries.addAll(c);

        return entries;
    }
}
