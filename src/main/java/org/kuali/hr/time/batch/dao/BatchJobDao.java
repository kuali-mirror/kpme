package org.kuali.hr.time.batch.dao;

import org.kuali.hr.time.batch.BatchJob;

import java.util.List;

public interface BatchJobDao {
    public void saveOrUpdate(BatchJob batchJob);
	public BatchJob getBatchJob(Long batchJobId);
    public List<BatchJob> getBatchJobs(Long hrPyCalendarEntryId);
    public List<BatchJob> getPayCalendarEntries(Long hrPyCalendarEntryId, String batchJobStatus);
}
