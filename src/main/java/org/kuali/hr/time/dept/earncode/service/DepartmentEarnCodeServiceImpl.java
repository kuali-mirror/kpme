package org.kuali.hr.time.dept.earncode.service;

import java.util.LinkedList;
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
	 * Handles the wildcarding.
	 */
	public List<DepartmentEarnCode> getDepartmentEarnCodes(String department, String tkSalGroup, String location, java.util.Date asOfDate) {
		List<DepartmentEarnCode> decs = new LinkedList<DepartmentEarnCode>();
		
		decs = deptEarnCodeDao.getDepartmentEarnCodes(department, tkSalGroup, location, asOfDate);
		if (!decs.isEmpty()) {
			return decs;
		}
		
		decs = deptEarnCodeDao.getDepartmentEarnCodes("%", tkSalGroup, location, asOfDate);
		if (!decs.isEmpty()) {
			return decs;
		}
		
		decs = deptEarnCodeDao.getDepartmentEarnCodes(department, "%", location, asOfDate);
		if (!decs.isEmpty()) {
			return decs;
		}

		decs = deptEarnCodeDao.getDepartmentEarnCodes("%", "%", location, asOfDate);
		if (!decs.isEmpty()) {
			return decs;
		}

		return decs;
	}
	
}
