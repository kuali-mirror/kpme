package org.kuali.hr.lm.leaveplan.service;

import java.sql.Date;

import org.kuali.hr.lm.leaveplan.LeavePlan;

public interface LeavePlanService {
    
    /**
     * Fetch LeavePlan by id
     * @param lmLeavePlanId
     * @return
     */
    public LeavePlan getLeavePlan(Long lmLeavePlanId);
    
    public LeavePlan getLeavePlan(String leavePlan, Date asOfDate);
}
