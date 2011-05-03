package org.kuali.hr.time.batch.dao;

import org.kuali.hr.time.batch.BatchJobEntry;

import java.util.List;
import java.util.Map;

public interface BatchJobEntryDao {
    public void saveOrUpdate(BatchJobEntry batchJobEntry);
    public BatchJobEntry getBatchJobEntry(Long batchJobEntryId);
    public List<BatchJobEntry> getBatchJobEntries(Long batchJobEntryId);
    public List<BatchJobEntry> getBatchJobEntries(String ip, String status);
    List<BatchJobEntry> getBatchJobEntries(Map<String, Object> criteria);
}
