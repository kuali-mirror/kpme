package org.kuali.hr.time.batch.dao;

import org.kuali.hr.time.batch.BatchJob;

public interface BatchJobDao {
	
	    public void saveOrUpdate(BatchJob batchJob);
	    public BatchJob getBatchJob(Long batchJobId);
}
