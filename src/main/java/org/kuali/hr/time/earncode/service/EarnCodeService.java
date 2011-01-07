package org.kuali.hr.time.earncode.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.earncode.EarnCode;

public interface EarnCodeService {

	public List<EarnCode> getEarnCodes(Assignment a);
	
	public EarnCode getEarnCode(String earnCode, Date asOfDate);
	
	public EarnCode getExactEarnCode(String earnCode, Date asOfDate);
}
