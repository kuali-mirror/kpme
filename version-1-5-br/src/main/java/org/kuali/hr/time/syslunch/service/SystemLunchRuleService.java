package org.kuali.hr.time.syslunch.service;

import java.sql.Date;

import org.kuali.hr.time.syslunch.rule.SystemLunchRule;

public interface SystemLunchRuleService {
	/**
	 * Fetch SystemLunchRule as of a particular date
	 * @param asOfDate
	 * @return
	 */
	public SystemLunchRule getSystemLunchRule(Date asOfDate);
	/**
	 * Determines if the Lunch button should show
	 * @return
	 */
	public boolean isShowLunchButton();
	
	public SystemLunchRule getSystemLunchRule(String tkSystemLunchRuleId);
}
