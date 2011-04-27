package org.kuali.hr.time.batch.service;

import org.kuali.hr.time.batch.BatchJobEntry;

public interface BatchJobEntryService {
	/**
	 * Fetch a BatchJobEntry by a given ID
	 * @param batchJobEntryId
	 * @return
	 */
	public BatchJobEntry getBatchJobEntry(Long batchJobEntryId);

}
