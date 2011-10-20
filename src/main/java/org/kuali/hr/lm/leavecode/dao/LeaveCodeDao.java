package org.kuali.hr.lm.leavecode.dao;

import org.kuali.hr.lm.leavecode.LeaveCode;


public interface LeaveCodeDao {

		/**
	 * Get leave code from id
	 * @param lmLeaveCodeId
	 * @return LeaveCode
	 */
	public LeaveCode getLeaveCode(Long lmLeaveCodeId);
	
}
