package org.kuali.hr.time.dept.earncode.service;

import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;

import java.util.Date;
import java.util.List;

public interface DepartmentEarnCodeService {

	/** This should handle wild cards on department and hr_sal_group.
	 * 
	 */
	public List<DepartmentEarnCode> getDepartmentEarnCodes(String department, String hr_sal_group, String location, Date asOfDate);
	
	/**
	 * Fetch department earn code by id
	 * @param hrDeptEarnCodeId
	 * @return
	 */
	public DepartmentEarnCode getDepartmentEarnCode(String hrDeptEarnCodeId);
	
	public List<DepartmentEarnCode> searchDepartmentEarnCodes(String dept, String salGroup, String earnCode, String location,
			java.sql.Date fromEffdt, java.sql.Date toEffdt, String active, String showHistory);
	
    /**
     * get the count of Department Earn Code by given parameters
     * @param earnGroup
     * @return int
     */
	public int getDepartmentEarnCodeCount(String dept, String salGroup, String earnCode, String employee, String approver, String location,
			String active, java.sql.Date effdt,String hrDeptEarnCodeId);
	
    /**
     * get the count of newer versions of the given earnCode
     * @param earnCode
     * @param effdt
     * @return int
     */
	public int getNewerDeptEarnCodeCount(String earnCode, Date effdt);
}
