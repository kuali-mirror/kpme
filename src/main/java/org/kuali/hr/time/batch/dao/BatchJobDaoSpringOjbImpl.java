package org.kuali.hr.time.batch.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.batch.BatchJob;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BatchJobDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements BatchJobDao {

    public void saveOrUpdate(BatchJob batchJob) {
        this.getPersistenceBrokerTemplate().store(batchJob);
    }

    public BatchJob getBatchJob(Long batchJobId){
        Criteria currentRecordCriteria = new Criteria();
        currentRecordCriteria.addEqualTo("tkBatchJobId", batchJobId);

        return (BatchJob) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(BatchJob.class, currentRecordCriteria));
    }

    @Override
    public List<BatchJob> getBatchJobs(Long payCalendarEntryId) {
        Criteria root = new Criteria();
        root.addEqualTo("payCalendarEntryId", payCalendarEntryId);
        Query query = QueryFactory.newQuery(BatchJob.class, root);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        List<BatchJob> jobs = new ArrayList<BatchJob>();
        jobs.addAll(c);

        return jobs;
    }

    @Override
    public List<BatchJob> getPayCalendarEntries(Long payCalendarEntryId, String batchJobStatus) {
        Criteria root = new Criteria();
        root.addEqualTo("payCalendarEntryId", payCalendarEntryId);
        root.addEqualTo("batchJobStatus", batchJobStatus);
        Query query = QueryFactory.newQuery(BatchJob.class, root);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        List<BatchJob> jobs = new ArrayList<BatchJob>();
        jobs.addAll(c);

        return jobs;
    }
}
