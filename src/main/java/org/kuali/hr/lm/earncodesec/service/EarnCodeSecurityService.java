package org.kuali.hr.lm.earncodesec.service;

import org.kuali.hr.lm.earncodesec.EarnCodeSecurity;

import java.util.Date;
import java.util.List;

public interface EarnCodeSecurityService {

	/** This should handle wild cards on department and hr_sal_group.
	 * 
	 */
	public List<EarnCodeSecurity> getEarnCodeSecurities(String department, String hr_sal_group, String location, Date asOfDate);
	
	/**
	 * Fetch department earn code by id
	 * @param hrDeptEarnCodeId
	 * @return
	 */
	public EarnCodeSecurity getEarnCodeSecurity(String hrEarnCodeSecId);
	
	public List<EarnCodeSecurity> searchEarnCodeSecurities(String dept, String salGroup, String earnCode, String location,
			java.sql.Date fromEffdt, java.sql.Date toEffdt, String active, String showHistory);
	
    /**
     * get the count of Department Earn Code by given parameters
     * @param earnGroup
     * @return int
     */
	public int getEarnCodeSecurityCount(String dept, String salGroup, String earnCode, String employee, String approver, String location,
			String active, java.sql.Date effdt,String hrDeptEarnCodeId);
	
    /**
     * get the count of newer versions of the given earnCode
     * @param earnCode
     * @param effdt
     * @return int
     */
	public int getNewerEarnCodeSecurityCount(String earnCode, Date effdt);
}
