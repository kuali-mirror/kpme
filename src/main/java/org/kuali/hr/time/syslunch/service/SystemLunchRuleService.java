package org.kuali.hr.time.syslunch.service;

import java.sql.Date;

import org.kuali.hr.time.syslunch.rule.SystemLunchRule;

public interface SystemLunchRuleService {
	public SystemLunchRule getSystemLunchRule(Date asOfDate);
}
