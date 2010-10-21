package org.kuali.hr.time.graceperiod.service;

import java.sql.Date;
import java.sql.Timestamp;

import org.kuali.hr.time.graceperiod.rule.GracePeriodRule;

public interface GracePeriodService {
	public GracePeriodRule getGracePeriodRule(Date asOfDate);
	public Timestamp processGracePeriodRule(Timestamp actualTime, Date asOfDate);
}
