package org.kuali.hr.time.batch.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.batch.BatchJob;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class BatchJobDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements BatchJobDao {
	
	 public void saveOrUpdate(BatchJob batchJob) {
		this.getPersistenceBrokerTemplate().store(batchJob);
	 }
	 
	 public BatchJob getBatchJob(Long batchJobId){
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("tkBatchJobId", batchJobId);

		return (BatchJob) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(BatchJob.class, currentRecordCriteria));
	 }
}
