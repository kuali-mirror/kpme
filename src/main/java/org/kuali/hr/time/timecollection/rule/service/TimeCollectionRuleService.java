package org.kuali.hr.time.timecollection.rule.service;

import java.sql.Date;

import org.kuali.hr.time.collection.rule.TimeCollectionRule;

public interface TimeCollectionRuleService {
	public TimeCollectionRule getTimeCollectionRule(String dept, Long workArea,Date asOfDate);
}
