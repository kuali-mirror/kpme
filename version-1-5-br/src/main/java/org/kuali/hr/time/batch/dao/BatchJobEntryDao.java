package org.kuali.hr.time.batch.dao;

import java.util.List;
import java.util.Map;

import org.kuali.hr.time.batch.BatchJobEntry;

public interface BatchJobEntryDao {
	/**
	 * Save or update batch job entry
	 * @param batchJobEntry
	 */
    public void saveOrUpdate(BatchJobEntry batchJobEntry);
    /**
     * Get batch job entry by id
     * @param batchJobEntryId
     * @return
     */
    public BatchJobEntry getBatchJobEntry(Long batchJobEntryId);
    /**
     * Get batch job entry by batch job id
     * @param batchJobEntryId
     * @return
     */
    public List<BatchJobEntry> getBatchJobEntries(Long batchJobEntryId);
    /**
     * Get batch job entries for ip and status
     * @param ip
     * @param status
     * @return
     */
    public List<BatchJobEntry> getBatchJobEntries(String ip, String status);
    List<BatchJobEntry> getBatchJobEntries(Map<String, Object> criteria);
}
