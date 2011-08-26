package org.kuali.hr.time.dept.earncode.service;

import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;

import java.util.Date;
import java.util.List;

public interface DepartmentEarnCodeService {

	/** This should handle wild cards on department and hr_sal_group.
	 * 
	 */
	public List<DepartmentEarnCode> getDepartmentEarnCodes(String department, String hr_sal_group, String location, Date asOfDate);

	public DepartmentEarnCode getDepartmentEarnCode(Long hrDeptEarnCodeId);
}
