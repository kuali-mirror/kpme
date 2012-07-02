package org.kuali.hr.time.earncode.dao;

import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.time.earncode.EarnCode;

import java.sql.Date;
import java.util.List;

public interface EarnCodeDao {

	/** Get an earn code by "row id" explicitly */
	public EarnCode getEarnCodeById(String earnCodeId);
	
	/** Provides access to earn code by name, using effdt, timestamp and active as qualifiers */
	public EarnCode getEarnCode(String earnCode, Date asOfDate);
	
	public List<EarnCode> getOvertimeEarnCodes(Date asOfDate);

	public int getEarnCodeCount(String earnCode);
	
	public int getNewerEarnCodeCount(String earnCode, Date effdt);
	
	public List<EarnCode> getEarnCodes(String leavePlan, Date asOfDate);
}
