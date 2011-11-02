package org.kuali.hr.time.missedpunch.service;

import java.util.List;

import org.kuali.hr.time.missedpunch.MissedPunchDocument;
import org.kuali.hr.time.batch.BatchJobEntry;


public interface MissedPunchService {
	/**
	 * Fetch missed punch by header id
	 * @param headerId
	 * @return
	 */
    public MissedPunchDocument getMissedPunchByRouteHeader(String headerId);
    /**
     * Add clock log for missed punch
     * @param missedPunch
     */
    public void addClockLogForMissedPunch(MissedPunchDocument missedPunch);
    /**
     * Update clock log and time block if necessary
     * @param missedPunch
     */
    public void updateClockLogAndTimeBlockIfNecessary(MissedPunchDocument missedPunch);
    /**
     * Get missed punch by clock id
     * @param clockLogId
     * @return
     */
    public MissedPunchDocument getMissedPunchByClockLogId(Long clockLogId);
    /**
     * Approve missed punch document
     * @param document
     * @return
     */
    public void approveMissedPunch(MissedPunchDocument document);
    /**
     * Get missed punch documents by batchJobEntry
     * @param batchJobEntry
     * @return
     */
    public List<MissedPunchDocument> getMissedPunchDocsByBatchJobEntry(BatchJobEntry batchJobEntry);
}
