package org.kuali.hr.time.batch.service;

import org.kuali.hr.time.batch.BatchJob;

import java.util.List;

public interface BatchJobService {

	/**
	 * Fetch a BatchJob by a given ID
	 * @param batchJobId
	 * @return
	 */
	public BatchJob getBatchJob(Long batchJobId);

    /**
     * Provides a List of BatchJob objects that match the indicated payCalendarEntryId.
     * @param payCalendarEntryId The id of PayCalendarEntries objects to match.
     * @return List of BatchJob objects.
     */
    public List<BatchJob> getBatchJobs(Long payCalendarEntryId);

    public List<BatchJob> getBatchJobs(Long payCalendarEntryId, String batchJobStatus);
    
    public void saveBatchJob(BatchJob batchJob);
}
