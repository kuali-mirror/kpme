package org.kuali.hr.time.missedpunch.dao;

import org.kuali.hr.time.missedpunch.MissedPunch;

public interface MissedPunchDao {
    public MissedPunch getMissedPunchByRouteHeader(Long headerId);
}
