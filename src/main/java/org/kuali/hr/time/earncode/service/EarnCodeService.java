package org.kuali.hr.time.earncode.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.earncode.EarnCode;

public interface EarnCodeService {
	/**
	 * Fetch a List of earn codes for a particular assignment
	 * @param a
	 * @return
	 */
	public List<EarnCode> getEarnCodes(Assignment a);
	/**
	 * Fetch an EarnCode of a particular date
	 * @param earnCode
	 * @param asOfDate
	 * @return
	 */
	public EarnCode getEarnCode(String earnCode, Date asOfDate);
	
}
