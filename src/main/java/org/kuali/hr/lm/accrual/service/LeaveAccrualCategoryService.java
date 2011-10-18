package org.kuali.hr.lm.accrual.service;

import org.kuali.hr.lm.accrual.LeaveAccrualCategory;

public interface LeaveAccrualCategoryService {
    
    /**
     * Fetch assignment by id
     * @param tkAssignmentId
     * @return
     */
    public LeaveAccrualCategory getLeaveAccrualCategory(Long lmAccrualCategoryId);
}
