package org.kuali.hr.time.dept.earncode.service;

import java.util.List;

import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.dept.earncode.dao.DepartmentEarnCodeDao;

public class DepartmentEarnCodeServiceImpl implements DepartmentEarnCodeService {

    private DepartmentEarnCodeDao deptEarnCodeDao;

	public void setDeptEarnCodeDao(DepartmentEarnCodeDao deptEarnCodeDao) {
		this.deptEarnCodeDao = deptEarnCodeDao;
	}

	@Override
	/*
	 * Fetch dept earn codes
	 */
	public List<DepartmentEarnCode> getDepartmentEarnCodes(String department, String tkSalGroup, String location, java.util.Date asOfDate) {
		return deptEarnCodeDao.getDepartmentEarnCodes(department, tkSalGroup, location, asOfDate);
	}
	
}
