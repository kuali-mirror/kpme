package org.kuali.hr.lm.leavecode.service;

import org.kuali.hr.lm.leavecode.LeaveCode;

public interface LeaveCodeService {
    
    /**
     * Fetch LeaveCode by id
     * @param lmLeaveCodeId
     * @return
     */
    public LeaveCode getLeaveCode(Long lmLeaveCodeId);
}
