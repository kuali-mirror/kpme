package org.kuali.hr.time.timecollection.rule.dao;

import java.sql.Date;

import org.kuali.hr.time.collection.rule.TimeCollectionRule;

public interface TimeCollectionRuleDaoService {
	public TimeCollectionRule getTimeCollectionRule(String dept, Long workArea, String payType, Date asOfDate);
	public TimeCollectionRule getTimeCollectionRule(String dept, Long workArea, Date asOfDate);
	public TimeCollectionRule getTimeCollectionRule(String tkTimeCollectionRuleId);
}
