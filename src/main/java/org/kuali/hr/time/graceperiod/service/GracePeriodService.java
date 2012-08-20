package org.kuali.hr.time.graceperiod.service;

import java.sql.Date;
import java.sql.Timestamp;

import org.kuali.hr.time.graceperiod.rule.GracePeriodRule;
import org.springframework.cache.annotation.Cacheable;

public interface GracePeriodService {
	/**
	 * Fetch GracePeriodRule as of a particular date
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= GracePeriodRule.CACHE_NAME, key="'asOfDate=' + #p0")
	public GracePeriodRule getGracePeriodRule(Date asOfDate);
	/**
	 * Process grace period rule as of a particular date with the corresponding timestamp
	 * @param actualTime
	 * @param asOfDate
	 * @return
	 */
	public Timestamp processGracePeriodRule(Timestamp actualTime, Date asOfDate);
	
	/**
	 * Fetch Grace period rule by id
	 * @param tkGracePeriodId
	 * @return
	 */
    @Cacheable(value= GracePeriodRule.CACHE_NAME, key="'tkGracePeriodId=' + #p0")
	public GracePeriodRule getGracePeriodRule(String tkGracePeriodId);
}
