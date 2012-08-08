package org.kuali.hr.time.clock.location.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.clock.location.ClockLocationRule;
import org.kuali.hr.time.clocklog.ClockLog;
import org.springframework.cache.annotation.Cacheable;

public interface ClockLocationRuleService {
	/**
	 * Fetch Clock Location Rule based on criteria
	 * @param dept
	 * @param workArea
	 * @param principalId
	 * @param jobNumber
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= ClockLocationRule.CACHE_NAME,
            key="'dept=' + #p0" +
                    "+ '|' + 'workArea=' + #p1" +
                    "+ '|' + 'principalId=' + #p2" +
                    "+ '|' + 'jobNumber=' + #p3" +
                    "+ '|' + 'asOfDate=' + #p4")
	public List<ClockLocationRule> getClockLocationRule(String dept, Long workArea,
			String principalId, Long jobNumber, Date asOfDate);
	/**
	 * Process clock location rule based on clock log passed in
	 * @param clockLog
	 * @param asOfDate
	 */
	public void processClockLocationRule(ClockLog clockLog, Date asOfDate);
	
	/**
	 * 
	 * @param dept
	 * @param workArea
	 * @param principalId
	 * @param jobNumber
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= ClockLocationRule.CACHE_NAME,
            key="'{getNewerVersionClockLocationRule}' + 'dept=' + #p0" +
                    "+ '|' + 'workArea=' + #p1" +
                    "+ '|' + 'principalId=' + #p2" +
                    "+ '|' + 'jobNumber=' + #p3" +
                    "+ '|' + 'asOfDate=' + #p4")
	public List<ClockLocationRule> getNewerVersionClockLocationRule(String dept, Long workArea, String principalId, 
			Long jobNumber, Date asOfDate);
	/**
	 * 
	 * @param tkClockLocationRuleId
	 * @return
	 */
    @Cacheable(value= ClockLocationRule.CACHE_NAME, key="'tkClockLocationRuleId=' + #p0")
	public ClockLocationRule getClockLocationRule(String tkClockLocationRuleId);

	/**
	 * populate ip addresses for given ClockLocationRule
	 * @param clr
	 * @return
	 */
	public void populateIPAddressesForCLR(ClockLocationRule clr);
}
