package org.kuali.hr.time.dept.earncode.service;

import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.dept.earncode.dao.DepartmentEarnCodeDao;
import org.kuali.hr.time.util.TkConstants;

import java.sql.Date;
import java.util.List;

public class DepartmentEarnCodeServiceImpl implements DepartmentEarnCodeService {

    private DepartmentEarnCodeDao deptEarnCodeDao;

	public void setDeptEarnCodeDao(DepartmentEarnCodeDao deptEarnCodeDao) {
		this.deptEarnCodeDao = deptEarnCodeDao;
	}

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public List<DepartmentEarnCode> getDepartmentEarnCodes(String department, String hrSalGroup, String location, java.util.Date asOfDate) {
		return deptEarnCodeDao.getDepartmentEarnCodes(department, hrSalGroup, location, asOfDate);
	}

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public DepartmentEarnCode getDepartmentEarnCode(String hrDeptEarnCodeId) {
		return deptEarnCodeDao.getDepartmentEarnCode(hrDeptEarnCodeId);
	}

	@Override
	public List<DepartmentEarnCode> searchDepartmentEarnCodes(String dept,
			String salGroup, String earnCode, String location, Date fromEffdt,
			Date toEffdt, String active, String showHistory) {
		return deptEarnCodeDao.searchDepartmentEarnCodes(dept, salGroup, earnCode, location, fromEffdt,
								toEffdt, active, showHistory);
	}
	@Override
	public int getDepartmentEarnCodeCount(String dept, String salGroup, String earnCode, String employee, String approver, String location,
			String active, java.sql.Date effdt,String hrDeptEarnCodeId) {
		return deptEarnCodeDao.getDepartmentEarnCodeCount(dept, salGroup, earnCode, employee, approver, location,
				active, effdt, hrDeptEarnCodeId);
	}
}
