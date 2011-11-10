package org.kuali.hr.time.batch.dao;

import org.kuali.hr.time.batch.BatchJob;

import java.util.List;

public interface BatchJobDao {
	/**
	 * Save or update a batch job
	 * @param batchJob
	 */
    public void saveOrUpdate(BatchJob batchJob);
    /**
     * Get batch job by id
     * @param batchJobId
     * @return
     */
	public BatchJob getBatchJob(Long batchJobId);
    /**
     * Get batch jobs by pay calendar id
     * @param hrPyCalendarEntryId
     * @return
     */
	public List<BatchJob> getBatchJobs(Long hrPyCalendarEntryId);
    /**
     * Get pay calendar entries by id and batch job status
     * @param hrPyCalendarEntryId
     * @param batchJobStatus
     * @return
     */
	public List<BatchJob> getCalendarEntries(Long hrPyCalendarEntryId, String batchJobStatus);
}
