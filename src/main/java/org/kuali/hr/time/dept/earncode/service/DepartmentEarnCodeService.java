package org.kuali.hr.time.dept.earncode.service;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;

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
}
