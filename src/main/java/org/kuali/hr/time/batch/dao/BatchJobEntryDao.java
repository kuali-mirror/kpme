package org.kuali.hr.time.batch.dao;

import org.kuali.hr.time.batch.BatchJobEntry;

public interface BatchJobEntryDao {
	 public void saveOrUpdate(BatchJobEntry batchJobEntry);
	 public BatchJobEntry getBatchJobEntry(Long batchJobEntryId);
}
