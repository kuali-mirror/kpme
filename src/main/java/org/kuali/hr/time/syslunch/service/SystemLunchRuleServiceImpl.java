package org.kuali.hr.time.syslunch.service;

import java.sql.Date;

import org.kuali.hr.time.syslunch.dao.SystemLunchRuleDao;
import org.kuali.hr.time.syslunch.rule.SystemLunchRule;

public class SystemLunchRuleServiceImpl implements SystemLunchRuleService {
	public SystemLunchRuleDao systemLunchRuleDao;
	
	@Override
	public SystemLunchRule getSystemLunchRule(Date asOfDate) {
		return systemLunchRuleDao.getSystemLunchRule(asOfDate);
	}

	public SystemLunchRuleDao getSystemLunchRuleDao() {
		return systemLunchRuleDao;
	}

	public void setSystemLunchRuleDao(SystemLunchRuleDao systemLunchRuleDao) {
		this.systemLunchRuleDao = systemLunchRuleDao;
	}

}
