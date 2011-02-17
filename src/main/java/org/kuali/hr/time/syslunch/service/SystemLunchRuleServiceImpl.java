package org.kuali.hr.time.syslunch.service;

import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.syslunch.dao.SystemLunchRuleDao;
import org.kuali.hr.time.syslunch.rule.SystemLunchRule;

import java.sql.Date;

public class SystemLunchRuleServiceImpl implements SystemLunchRuleService {
	public SystemLunchRuleDao systemLunchRuleDao;

	@Override
	//@CacheResult
	public SystemLunchRule getSystemLunchRule(Date asOfDate) {
		return systemLunchRuleDao.getSystemLunchRule(asOfDate);
	}

	public SystemLunchRuleDao getSystemLunchRuleDao() {
		return systemLunchRuleDao;
	}

	public void setSystemLunchRuleDao(SystemLunchRuleDao systemLunchRuleDao) {
		this.systemLunchRuleDao = systemLunchRuleDao;
	}

	@Override
	public boolean isShowLunchButton() {

    	Boolean isShowLunchButton = false;
    	SystemLunchRule systemLunchrule = TkServiceLocator.getSystemLunchRuleService().getSystemLunchRule(new java.sql.Date(System.currentTimeMillis()));
    	if(systemLunchrule != null) {
    		isShowLunchButton = systemLunchrule.getShowLunchButton();
    	}

		return isShowLunchButton;
	}

}
