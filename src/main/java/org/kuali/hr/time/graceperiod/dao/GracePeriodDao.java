package org.kuali.hr.time.graceperiod.dao;

import java.sql.Date;

import org.kuali.hr.time.graceperiod.rule.GracePeriodRule;

public interface GracePeriodDao {
	public GracePeriodRule getGracePeriodRule(Date asOfDate);
}
