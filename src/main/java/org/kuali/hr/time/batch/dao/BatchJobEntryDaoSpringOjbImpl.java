package org.kuali.hr.time.batch.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.batch.BatchJobEntry;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class BatchJobEntryDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements BatchJobEntryDao {

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
}
