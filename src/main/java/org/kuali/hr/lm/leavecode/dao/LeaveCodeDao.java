package org.kuali.hr.lm.leavecode.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.lm.leavecode.LeaveCode;


public interface LeaveCodeDao {

		/**
	 * Get leave code from id
	 * @param lmLeaveCodeId
	 * @return LeaveCode
	 */
	public LeaveCode getLeaveCode(String lmLeaveCodeId);
	
	public List<LeaveCode> getLeaveCodes(String leavePlan, Date asOfDate);
	
}
