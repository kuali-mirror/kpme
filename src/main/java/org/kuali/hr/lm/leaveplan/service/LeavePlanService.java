package org.kuali.hr.lm.leaveplan.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.springframework.cache.annotation.Cacheable;

public interface LeavePlanService {
    
    /**
     * Fetch LeavePlan by id
     * @param lmLeavePlanId
     * @return
     */
    @Cacheable(value= LeavePlan.CACHE_NAME, key="'lmLeavePlanId=' + #p0")
    public LeavePlan getLeavePlan(String lmLeavePlanId);

    @Cacheable(value= LeavePlan.CACHE_NAME, key="'leavePlan=' + #p0 + '|' + 'asOfDate=' + #p1")
    public LeavePlan getLeavePlan(String leavePlan, Date asOfDate);
    
    public boolean isValidLeavePlan(String leavePlan);
    
    public List<LeavePlan> getAllActiveLeavePlan(String leavePlan, Date asOfDate);
    
    public List<LeavePlan> getAllInActiveLeavePlan(String leavePlan, Date asOfDate);
}
