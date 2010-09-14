package org.kuali.hr.time.dept.earncode.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;

public interface DepartmentEarnCodeService {

	public List<DepartmentEarnCode> getDepartmentEarnCodeList(String dept, String salGroup, boolean employee, 
													boolean approver, boolean orgAdmin, Date payEndDate);

	/**
	 * Provides a list of DepartmentEarnCodes for the given salGroupId and payPeriodEndDate.
	 * PayPeriodEndDate is used when pulling the actual earnCodes.
	 * @param salGroupId
	 * @param payPeriodEndDate
	 * @return
	 */
	public List<DepartmentEarnCode> getDepartmentEarnCodes(Long salGroupId, Date payPeriodEndDate);
	
}
