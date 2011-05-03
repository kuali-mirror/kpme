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

	public void saveBatchJobEntry(BatchJobEntry batchJobEntry);
	
	public List<BatchJobEntry> getBatchJobEntries(Long batchJobId);

    public List<BatchJobEntry> getBatchJobEntries(String ip, String status);
    
      /**
     * Fetch a list of BatchJob objects by the given criteria.
     * @param criteria
     * @return List of BatchJob objects.
     */
    List<BatchJobEntry> getBatchJobEntries(Map<String, Object> criteria);
}
