package org.kuali.hr.lm.leaveplan.dao;

import java.sql.Date;

import org.kuali.hr.lm.leaveplan.LeavePlan;


public interface LeavePlanDao {

		/**
	 * Get leave plan from id
	 * @param lmLeavePlanId
	 * @return LeavePlan
	 */
	public LeavePlan getLeavePlan(String lmLeavePlanId);
	
	public LeavePlan getLeavePlan(String leavePlan, Date asOfDate);
	
	public int getNumberLeavePlan(String leavePlan);
}
