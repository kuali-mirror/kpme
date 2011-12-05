package org.kuali.hr.lm.leavecode.dao;

import org.kuali.hr.lm.leavecode.LeaveCode;

import java.sql.Date;
import java.util.List;


public interface LeaveCodeDao {

		/**
	 * Get leave code from id
	 * @param lmLeaveCodeId
	 * @return LeaveCode
	 */
	public LeaveCode getLeaveCode(Long lmLeaveCodeId);
	
	public List<LeaveCode> getLeaveCodes(String leavePlan, Date asOfDate);
	
}
