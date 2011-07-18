package org.kuali.hr.time.earngroup.service;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.earngroup.EarnGroupDefinition;
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

	@Override
	public EarnGroup getEarnGroupForEarnCode(String earnCode, Date asOfDate) {
		return earnGroupDao.getEarnGroupForEarnCode(earnCode, asOfDate);
	}
	
	public Set<String> getEarnCodeListForEarnGroup(String earnGroup, Date asOfDate) {
		Set<String> earnCodes = new HashSet<String>();
		EarnGroup earnGroupObj = earnGroupDao.getEarnGroup(earnGroup, asOfDate);
		for(EarnGroupDefinition earnGroupDef : earnGroupObj.getEarnGroups()){
			if(!earnCodes.contains(earnGroupDef.getEarnCode())){
				earnCodes.add(earnGroupDef.getEarnCode());
			}
		}
		return earnCodes;
	}

	@Override
	public EarnGroup getEarnGroup(Long tkEarnGroupId) {
		return earnGroupDao.getEarnGroup(tkEarnGroupId);
	}

}
