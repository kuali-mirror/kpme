package org.kuali.hr.lm.accrual.service;

import org.kuali.hr.lm.accrual.LeaveAccrualCategory;

public interface LeaveAccrualCategoryService {
    
    /**
     * Fetch LeaveAccrualCategory by id
     * @param lmAccrualCategoryId
     * @return
     */
    public LeaveAccrualCategory getLeaveAccrualCategory(Long lmAccrualCategoryId);
}
