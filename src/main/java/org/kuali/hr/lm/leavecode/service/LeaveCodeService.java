package org.kuali.hr.lm.leavecode.service;

import org.kuali.hr.lm.leavecode.LeaveCode;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface LeaveCodeService {
    
    /**
     * Fetch LeaveCode by id
     * @param lmLeaveCodeId
     * @return
     */
    public LeaveCode getLeaveCode(Long lmLeaveCodeId);

    List<LeaveCode> getLeaveCodes(String principalId, Date asOfDate);

    Map<String, String> getLeaveCodesForDisplay(String principalId);
}
