package org.kuali.hr.time.batch.dao;

import java.util.List;

import org.kuali.hr.time.batch.BatchJobEntry;

public interface BatchJobEntryDao {
	 public void saveOrUpdate(BatchJobEntry batchJobEntry);
	 public BatchJobEntry getBatchJobEntry(Long batchJobEntryId);
	 public List<BatchJobEntry> getBatchJobEntries(Long batchJobEntryId);
}
