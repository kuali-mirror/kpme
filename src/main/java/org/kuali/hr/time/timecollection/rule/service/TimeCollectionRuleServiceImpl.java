package org.kuali.hr.time.timecollection.rule.service;

import java.sql.Date;

import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.hr.time.timecollection.rule.dao.TimeCollectionRuleDaoService;

public class TimeCollectionRuleServiceImpl implements TimeCollectionRuleService{
	private TimeCollectionRuleDaoService timeCollectRuleDao;
	
	public TimeCollectionRule getTimeCollectionRule(String dept, Long workArea,Date asOfDate){
		return timeCollectRuleDao.getTimeCollectionRule(dept, workArea, asOfDate);
	}

	public TimeCollectionRuleDaoService getTimeCollectRuleDao() {
		return timeCollectRuleDao;
	}

	public void setTimeCollectRuleDao(
			TimeCollectionRuleDaoService timeCollectRuleDao) {
		this.timeCollectRuleDao = timeCollectRuleDao;
	}
}
