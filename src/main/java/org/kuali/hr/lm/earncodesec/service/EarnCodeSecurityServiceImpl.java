package org.kuali.hr.lm.earncodesec.service;

import org.kuali.hr.lm.earncodesec.EarnCodeSecurity;
import org.kuali.hr.lm.earncodesec.dao.EarnCodeSecurityDao;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.util.TkConstants;

import java.sql.Date;
import java.util.List;

public class EarnCodeSecurityServiceImpl implements EarnCodeSecurityService {

    private EarnCodeSecurityDao earnCodeSecurityDao;

	public void setEarnCodeSecurityDao(EarnCodeSecurityDao earnCodeSecurityDao) {
		this.earnCodeSecurityDao = earnCodeSecurityDao;
	}

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public List<EarnCodeSecurity> getEarnCodeSecurities(String department, String hrSalGroup, String location, java.util.Date asOfDate) {
		return earnCodeSecurityDao.getEarnCodeSecurities(department, hrSalGroup, location, asOfDate);
	}

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public EarnCodeSecurity getEarnCodeSecurity(String hrEarnCodeSecId) {
		return earnCodeSecurityDao.getEarnCodeSecurity(hrEarnCodeSecId);
	}

	@Override
	public List<EarnCodeSecurity> searchEarnCodeSecurities(String dept,
			String salGroup, String earnCode, String location, Date fromEffdt,
			Date toEffdt, String active, String showHistory) {
		return earnCodeSecurityDao.searchEarnCodeSecurities(dept, salGroup, earnCode, location, fromEffdt,
								toEffdt, active, showHistory);
	}
	@Override
	public int getEarnCodeSecurityCount(String dept, String salGroup, String earnCode, String employee, String approver, String location,
			String active, java.sql.Date effdt,String hrDeptEarnCodeId) {
		return earnCodeSecurityDao.getEarnCodeSecurityCount(dept, salGroup, earnCode, employee, approver, location,
				active, effdt, hrDeptEarnCodeId);
	}
	@Override
	public int getNewerEarnCodeSecurityCount(String earnCode, java.util.Date effdt) {
		return earnCodeSecurityDao.getNewerEarnCodeSecurityCount(earnCode, effdt);
	}
}
