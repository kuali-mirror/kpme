package org.kuali.hr.time.earncode.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.earncode.EarnCode;

public interface EarnCodeDao {

	/** Get an earn code by "row id" explicitly */
	public EarnCode getEarnCodeById(Long earnCodeId);
	
	/** Provides access to earn code by name, using effdt, timestamp and active as qualifiers */
	public EarnCode getEarnCode(String earnCode, Date asOfDate);
	
	public List<EarnCode> getOvertimeEarnCodes(Date asOfDate);

}
