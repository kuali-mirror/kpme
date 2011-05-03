package org.kuali.hr.time.batch.service;

import java.util.List;

import org.kuali.hr.time.batch.BatchJobEntry;

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
}
