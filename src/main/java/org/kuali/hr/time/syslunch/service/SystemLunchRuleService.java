package org.kuali.hr.time.syslunch.service;

import java.sql.Date;

import org.kuali.hr.time.syslunch.rule.SystemLunchRule;
import org.springframework.cache.annotation.Cacheable;

public interface SystemLunchRuleService {
	/**
	 * Fetch SystemLunchRule as of a particular date
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= SystemLunchRule.CACHE_NAME, key="'asOfDate=' + #p0")
	public SystemLunchRule getSystemLunchRule(Date asOfDate);
	/**
	 * Determines if the Lunch button should show
	 * @return
	 */
	public boolean isShowLunchButton();

    @Cacheable(value= SystemLunchRule.CACHE_NAME, key="'tkSystemLunchRuleId=' + #p0")
	public SystemLunchRule getSystemLunchRule(String tkSystemLunchRuleId);
}
