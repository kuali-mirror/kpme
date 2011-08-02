package org.kuali.hr.time.missedpunch.service;

import org.kuali.hr.time.missedpunch.MissedPunchDocument;


public interface MissedPunchService {
    public MissedPunchDocument getMissedPunchByRouteHeader(String headerId);
}
