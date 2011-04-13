package org.kuali.hr.time.earncode.service;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.earncode.EarnCode;

import java.sql.Date;
import java.util.List;

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

    /**
     * Fetch the earn code type of a particular date
     * @param earnCode
     * @param asOfDate
     * @return
     */
    String getEarnCodeType(String earnCode, Date asOfDate);
}
