package org.kuali.hr.time.graceperiod.service;

import java.sql.Date;
import java.sql.Timestamp;

import org.kuali.hr.time.graceperiod.rule.GracePeriodRule;

public interface GracePeriodService {
	/**
	 * Fetch GracePeriodRule as of a particular date
	 * @param asOfDate
	 * @return
	 */
	public GracePeriodRule getGracePeriodRule(Date asOfDate);
	/**
	 * Process grace period rule as of a particular date with the corresponding timestamp
	 * @param actualTime
	 * @param asOfDate
	 * @return
	 */
	public Timestamp processGracePeriodRule(Timestamp actualTime, Date asOfDate);
	
	public GracePeriodRule getGracePeriodRule(Long tkGracePeriodId);
}
