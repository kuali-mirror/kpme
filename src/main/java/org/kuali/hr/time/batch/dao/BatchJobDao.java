package org.kuali.hr.time.batch.dao;

import org.kuali.hr.time.batch.BatchJob;

import java.util.List;

public interface BatchJobDao {
    public void saveOrUpdate(BatchJob batchJob);
	public BatchJob getBatchJob(Long batchJobId);
    public List<BatchJob> getBatchJobs(Long payCalendarEntryId);
    public List<BatchJob> getPayCalendarEntries(Long payCalendarEntryId, String batchJobStatus);
}
