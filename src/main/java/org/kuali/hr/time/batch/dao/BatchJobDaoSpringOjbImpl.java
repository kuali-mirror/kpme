/**
 * Copyright 2004-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.time.batch.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.batch.BatchJob;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

public class BatchJobDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements BatchJobDao {

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
    public List<BatchJob> getBatchJobs(String hrPyCalendarEntryId) {
        Criteria root = new Criteria();
        root.addEqualTo("hrPyCalendarEntryId", hrPyCalendarEntryId);
        Query query = QueryFactory.newQuery(BatchJob.class, root);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        List<BatchJob> jobs = new ArrayList<BatchJob>();
        jobs.addAll(c);

        return jobs;
    }

    @Override
    public List<BatchJob> getPayCalendarEntries(String hrPyCalendarEntryId, String batchJobStatus) {
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
