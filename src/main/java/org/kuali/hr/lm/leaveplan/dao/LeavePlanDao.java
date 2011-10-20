package org.kuali.hr.lm.leaveplan.dao;

import org.kuali.hr.lm.leaveplan.LeavePlan;


public interface LeavePlanDao {

		/**
	 * Get leave plan from id
	 * @param lmLeavePlanId
	 * @return LeavePlan
	 */
	public LeavePlan getLeavePlan(Long lmLeavePlanId);
	
}
