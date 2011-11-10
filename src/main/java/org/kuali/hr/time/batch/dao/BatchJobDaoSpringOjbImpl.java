package org.kuali.hr.time.batch.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.batch.BatchJob;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BatchJobDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements BatchJobDao {

    public void saveOrUpdate(final BatchJob batchJob) {
    	TkServiceLocator.getTransactionTemplate().execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				getPersistenceBrokerTemplate().store(batchJob);
			}
    	});
    }

    public BatchJob getBatchJob(Long batchJobId){
        Criteria currentRecordCriteria = new Criteria();
        currentRecordCriteria.addEqualTo("tkBatchJobId", batchJobId);

        return (BatchJob) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(BatchJob.class, currentRecordCriteria));
    }

    @Override
    public List<BatchJob> getBatchJobs(Long hrPyCalendarEntryId) {
        Criteria root = new Criteria();
        root.addEqualTo("hrPyCalendarEntryId", hrPyCalendarEntryId);
        Query query = QueryFactory.newQuery(BatchJob.class, root);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        List<BatchJob> jobs = new ArrayList<BatchJob>();
        jobs.addAll(c);

        return jobs;
    }

    @Override
    public List<BatchJob> getCalendarEntries(Long hrPyCalendarEntryId, String batchJobStatus) {
        Criteria root = new Criteria();
        root.addEqualTo("hrPyCalendarEntryId", hrPyCalendarEntryId);
        root.addEqualTo("batchJobStatus", batchJobStatus);
        Query query = QueryFactory.newQuery(BatchJob.class, root);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        List<BatchJob> jobs = new ArrayList<BatchJob>();
        jobs.addAll(c);

        return jobs;
    }
}
