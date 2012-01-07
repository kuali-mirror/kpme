package org.kuali.hr.time.dept.earncode.service;

import java.util.List;

import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.dept.earncode.dao.DepartmentEarnCodeDao;
import org.kuali.hr.time.util.TkConstants;

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
	
}
