package org.kuali.hr.time.missedpunch.dao;

import org.kuali.hr.time.missedpunch.MissedPunchDocument;

public interface MissedPunchDao {
    public MissedPunchDocument getMissedPunchByRouteHeader(String headerId);
    public MissedPunchDocument getMissedPunchByClockLogId(Long clockLogId);
}
