package org.kuali.hr.time.batch.service;

import org.kuali.hr.time.batch.BatchJob;

import java.util.List;

public interface BatchJobService {

	/**
	 * Fetch a BatchJob by a given ID
	 * @param batchJobId Database ID of the BatchJob to fetch.
	 * @return The BatchJob matching batchJobId.
	 */
	public BatchJob getBatchJob(Long batchJobId);

    /**
     * Provides a List of BatchJob objects that match the indicated payCalendarEntryId.
     * @param payCalendarEntryId The id of PayCalendarEntries objects to match.
     * @return List of BatchJob objects.
     */
    public List<BatchJob> getBatchJobs(Long payCalendarEntryId);

    /**
     * Get a List of BatchJob objects for the given parameters.
     *
     * @param payCalendarEntryId The pay calendar entry we are looking for.
     * @param batchJobStatus Only jobs of this status will be returned.
     * @return List of BatchJob objects.
     */
    public List<BatchJob> getBatchJobs(Long payCalendarEntryId, String batchJobStatus);

    /**
     * Saves or updates the provided BatchJob.
     * @param batchJob The object to save.
     */
    public void saveBatchJob(BatchJob batchJob);
}
