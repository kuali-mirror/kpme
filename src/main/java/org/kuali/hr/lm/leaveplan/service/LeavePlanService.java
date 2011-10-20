package org.kuali.hr.lm.leaveplan.service;

import org.kuali.hr.lm.leaveplan.LeavePlan;

public interface LeavePlanService {
    
    /**
     * Fetch LeavePlan by id
     * @param lmLeavePlanId
     * @return
     */
    public LeavePlan getLeavePlan(Long lmLeavePlanId);
}
