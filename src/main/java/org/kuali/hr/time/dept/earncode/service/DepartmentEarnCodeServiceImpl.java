package org.kuali.hr.time.dept.earncode.service;

import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.dept.earncode.dao.DepartmentEarnCodeDao;

import java.util.List;

public class DepartmentEarnCodeServiceImpl implements DepartmentEarnCodeService {

    private DepartmentEarnCodeDao deptEarnCodeDao;

	public void setDeptEarnCodeDao(DepartmentEarnCodeDao deptEarnCodeDao) {
		this.deptEarnCodeDao = deptEarnCodeDao;
	}

	@Override
	/*
	 * Fetch dept earn codes
	 */
	public List<DepartmentEarnCode> getDepartmentEarnCodes(String department, String hrSalGroup, String location, java.util.Date asOfDate) {
		return deptEarnCodeDao.getDepartmentEarnCodes(department, hrSalGroup, location, asOfDate);
	}

	@Override
	public DepartmentEarnCode getDepartmentEarnCode(Long hrDeptEarnCodeId) {
		return deptEarnCodeDao.getDepartmentEarnCode(hrDeptEarnCodeId);
	}
	
}
