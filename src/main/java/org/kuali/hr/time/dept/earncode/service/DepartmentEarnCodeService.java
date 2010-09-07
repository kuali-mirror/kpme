package org.kuali.hr.time.dept.earncode.service;

import java.util.List;

import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;

public interface DepartmentEarnCodeService {

	public List<DepartmentEarnCode> getDepartmentEarnCodeList(Long salGroup);

}
