package org.kuali.hr.lm.leaveplan.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.lm.leaveplan.LeavePlan;

public interface LeavePlanService {
    
    /**
     * Fetch LeavePlan by id
     * @param lmLeavePlanId
     * @return
     */
    public LeavePlan getLeavePlan(String lmLeavePlanId);
    
    public LeavePlan getLeavePlan(String leavePlan, Date asOfDate);
    
    public boolean isValidLeavePlan(String leavePlan);
    
    public List<LeavePlan> getAllActiveLeavePlan(String leavePlan, Date asOfDate);
    
    public List<LeavePlan> getAllInActiveLeavePlan(String leavePlan, Date asOfDate);
}
