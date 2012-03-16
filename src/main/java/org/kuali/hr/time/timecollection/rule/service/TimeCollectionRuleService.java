package org.kuali.hr.time.timecollection.rule.service;

import org.kuali.hr.time.collection.rule.TimeCollectionRule;

import java.sql.Date;
import java.util.List;

public interface TimeCollectionRuleService {
	/**
	 * Fetch TimeCollectionRule for a given dept and work area as of a particular date
	 * @param dept
	 * @param workArea
	 * @param asOfDate
	 * @return
	 */
	public TimeCollectionRule getTimeCollectionRule(String dept, Long workArea,Date asOfDate);
	public TimeCollectionRule getTimeCollectionRule(String dept, Long workArea, String payType, Date asOfDate);
	public TimeCollectionRule getTimeCollectionRule(String tkTimeCollectionRuleId);

    List<TimeCollectionRule> getTimeCollectionRules(String dept, String workArea, String payType, String active);
}
