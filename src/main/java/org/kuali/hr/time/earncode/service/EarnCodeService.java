package org.kuali.hr.time.earncode.service;

import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.earncode.EarnCode;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public interface EarnCodeService {
	/**
	 * Fetch a List of earn codes for a particular assignment
	 * @param a
     * @param asOfDate
	 * @return
	 */
	public List<EarnCode> getEarnCodes(Assignment a, Date asOfDate);

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
    
    /**
     * Fetch earn code by id
     * @param earnCodeId
     * @return
     */
    public EarnCode getEarnCodeById(String earnCodeId);
    
    /**
     * Fetch list of system defined overtime earn codes
     * @param asOfDate
     * @return
     */
    public List<EarnCode> getOvertimeEarnCodes(Date asOfDate);
    
    public List<String> getOvertimeEarnCodesStrs(Date asOfDate);
    /**
	 * get count of earn code with give earnCode
	 * @param earnCode
	 * @return int
	 */
    public int getEarnCodeCount(String earnCode);
    /**
	 * get count of newer version of earn code with give earnCode and date
	 * @param earnCode
	 * @param effdt
	 * @return int
	 */
    public int getNewerEarnCodeCount(String earnCode, Date effdt);
    
    /**
     * roundHrsWithLEarnCode
     * @param hours
     * @param earnCode
     * @return
     */
    public BigDecimal roundHrsWithEarnCode(BigDecimal hours, EarnCode earnCode);
}
