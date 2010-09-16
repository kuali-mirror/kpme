package org.kuali.hr.time.dept.earncode.service;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.earncode.EarnCode;

public interface DepartmentEarnCodeService {

	/** This should handle wild cards on department and tk_sal_group. 
	 * 
	 */
	public List<EarnCode> getDepartmentEarnCodes(String department, String tk_sal_group, Date asOfDate);

}
