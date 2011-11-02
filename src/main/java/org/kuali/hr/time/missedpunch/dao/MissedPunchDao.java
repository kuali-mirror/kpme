package org.kuali.hr.time.missedpunch.dao;

import java.util.List;

import org.kuali.hr.time.batch.BatchJobEntry;
import org.kuali.hr.time.missedpunch.MissedPunchDocument;

public interface MissedPunchDao {
    public MissedPunchDocument getMissedPunchByRouteHeader(String headerId);
    public MissedPunchDocument getMissedPunchByClockLogId(Long clockLogId);
    public List<MissedPunchDocument> getMissedPunchDocsByBatchJobEntry(BatchJobEntry batchJobEntry);
}
