package org.kuali.hr.time.batch.service;

import org.kuali.hr.time.batch.BatchJobEntry;

import java.util.List;
import java.util.Map;

public interface BatchJobEntryService {
	/**
	 * Fetch a BatchJobEntry by a given ID
	 * @param batchJobEntryId
	 * @return
	 */
	public BatchJobEntry getBatchJobEntry(Long batchJobEntryId);

    /**
     * Saves or updates the provided BatchJobEntry.
     * @param batchJobEntry The object we want to save.
     */
	public void saveBatchJobEntry(BatchJobEntry batchJobEntry);

    /**
     * For the indicated batchJobId, grab a List of BatchJobEntry objects.
     * @param batchJobId The ID to query against.
     * @return A List of BatchJobEntry objects.
     */
	public List<BatchJobEntry> getBatchJobEntries(Long batchJobId);

    /**
     * For the given parameters, return a List of BatchJobEntry objects.
     * @param ip The IP address we are interested in.
     * @param status The status.
     * @return A List of BatchJobEntry objects.
     */
    public List<BatchJobEntry> getBatchJobEntries(String ip, String status);

      /**
     * Fetch a list of BatchJob objects by the given criteria.
     * @param criteria
     * @return List of BatchJob objects.
     */
    List<BatchJobEntry> getBatchJobEntries(Map<String, Object> criteria);

    void updateIpAddress(String batchJobEntryId, String ipAddress);
}
