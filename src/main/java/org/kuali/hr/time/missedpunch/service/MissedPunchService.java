package org.kuali.hr.time.missedpunch.service;

import org.kuali.hr.time.missedpunch.MissedPunch;

public interface MissedPunchService {
    public MissedPunch getMissedPunchByRouteHeader(Long headerId);
}
