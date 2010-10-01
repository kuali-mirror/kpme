package org.kuali.hr.time.earngroup.service;

import java.sql.Date;

import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.earngroup.dao.EarnGroupDaoService;

public class EarnGroupServiceImpl implements EarnGroupService {
	private EarnGroupDaoService earnGroupDao;
	
	@Override
	public EarnGroup getEarnGroup(String earnGroup, Date asOfDate) {
		return earnGroupDao.getEarnGroup(earnGroup, asOfDate);
	}

	public EarnGroupDaoService getEarnGroupDao() {
		return earnGroupDao;
	}

	public void setEarnGroupDao(EarnGroupDaoService earnGroupDao) {
		this.earnGroupDao = earnGroupDao;
	}

	@Override
	public EarnGroup getEarnGroupSummaryForEarnCode(String earnCode, Date asOfDate) {
		return earnGroupDao.getEarnGroupSummaryForEarnCode(earnCode, asOfDate);
	}

}
