package org.kuali.hr.lm.accrual.dao;

import org.kuali.hr.lm.accrual.LeaveAccrualCategory;


public interface LeaveAccrualCategoryDao {

		/**
	 * Get leave accrual Category from id
	 * @param lmAccrualCategoryId
	 * @return LeaveAccrualCategory
	 */
	public LeaveAccrualCategory getLeaveAccrualCategory(Long lmAccrualCategoryId);
	
}
