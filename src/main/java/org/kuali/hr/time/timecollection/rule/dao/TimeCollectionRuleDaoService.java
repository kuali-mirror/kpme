package org.kuali.hr.time.timecollection.rule.dao;

import org.kuali.hr.time.collection.rule.TimeCollectionRule;

import java.sql.Date;
import java.util.List;

public interface TimeCollectionRuleDaoService {
	public TimeCollectionRule getTimeCollectionRule(String dept, Long workArea, String payType, Date asOfDate);
	public TimeCollectionRule getTimeCollectionRule(String dept, Long workArea, Date asOfDate);
	public TimeCollectionRule getTimeCollectionRule(String tkTimeCollectionRuleId);

    List<TimeCollectionRule> getTimeCollectionRules(String dept, Long workArea, String payType, String active, String showHistory);
}
