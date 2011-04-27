package org.kuali.hr.time.batch.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.batch.BatchJobEntry;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class BatchJobEntryDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements BatchJobEntryDao {
	 public void saveOrUpdate(BatchJobEntry batchJobEntry) {
		 this.getPersistenceBrokerTemplate().store(batchJobEntry);
	 }
	 public BatchJobEntry getBatchJobEntry(Long batchJobEntryId) {
		 Criteria currentRecordCriteria = new Criteria();
	        currentRecordCriteria.addEqualTo("tkBatchJobEntryId", batchJobEntryId);

	        return (BatchJobEntry) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(BatchJobEntry.class, currentRecordCriteria));
	 }
}
