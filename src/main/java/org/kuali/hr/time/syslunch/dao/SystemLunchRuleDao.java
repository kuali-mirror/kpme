package org.kuali.hr.time.syslunch.dao;

import java.sql.Date;

import org.kuali.hr.time.syslunch.rule.SystemLunchRule;

public interface SystemLunchRuleDao {
	public SystemLunchRule getSystemLunchRule(Date asOfDate);
}
