package org.kuali.hr.time.timecollection.rule.service;

import java.sql.Date;

import org.kuali.hr.time.collection.rule.TimeCollectionRule;

public interface TimeCollectionRuleService {
	/**
	 * Fetch TimeCollectionRule for a given dept and work area as of a particular date
	 * @param dept
	 * @param workArea
	 * @param asOfDate
	 * @return
	 */
	public TimeCollectionRule getTimeCollectionRule(String dept, Long workArea,Date asOfDate);
	
	public TimeCollectionRule getTimeCollectionRule(Long tkTimeCollectionRuleId);
}
