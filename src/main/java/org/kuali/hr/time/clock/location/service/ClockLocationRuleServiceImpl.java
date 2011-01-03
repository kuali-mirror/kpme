package org.kuali.hr.time.clock.location.service;

import java.sql.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.clock.location.ClockLocationRule;
import org.kuali.hr.time.clock.location.dao.ClockLocationDao;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.rice.kns.util.GlobalVariables;

public class ClockLocationRuleServiceImpl implements ClockLocationRuleService {
	private ClockLocationDao clockLocationDao;

	public ClockLocationDao getClockLocationDao() {
		return clockLocationDao;
	}

	public void setClockLocationDao(ClockLocationDao clockLocationDao) {
		this.clockLocationDao = clockLocationDao;
	}
	
	public void processClockLocationRule(ClockLog clockLog, Date asOfDate){
		List<ClockLocationRule> lstClockLocationRules = getClockLocationRule(clockLog.getJob().getDept(), 
										clockLog.getWorkArea(), clockLog.getPrincipalId(), clockLog.getJobNumber(), asOfDate);
		if(lstClockLocationRules.isEmpty()){
			return;
		}
		for(ClockLocationRule clockLocationRule : lstClockLocationRules){
			String ipAddressRule = clockLocationRule.getIpAddress();
			String ipAddressClock = clockLog.getIpAddress();
			
			if(compareIpAddresses(ipAddressRule, ipAddressClock)){
				return;
			}
		}
		GlobalVariables.getMessageMap().putWarning("property", "ipaddress.invalid.format", clockLog.getIpAddress());
		
	}
	
	private boolean compareIpAddresses(String ipAddressRule, String ipAddress){
		String[] rulePieces = StringUtils.split(ipAddressRule, ".");
		String[] ipAddPieces = StringUtils.split(ipAddress,".");
		boolean match = true;
		for(int i = 0;i<ipAddPieces.length;i++){
			if(StringUtils.equals(ipAddPieces[i], rulePieces[i]) || StringUtils.equals("*", rulePieces[i])){
				match &= true;
			} else {
			    return false;
			}
		}
		return match;
	}

	@Override
	@CacheResult
	public List<ClockLocationRule> getClockLocationRule(String dept, Long workArea,
			String principalId, Long jobNumber, Date asOfDate) {
		List<ClockLocationRule> clockLocationRule = clockLocationDao.getClockLocationRule(dept, workArea,principalId,jobNumber,asOfDate);
		if(!clockLocationRule.isEmpty()){
			return clockLocationRule;
		}
		clockLocationRule = clockLocationDao.getClockLocationRule(dept, workArea, principalId, -1L, asOfDate);
		if(!clockLocationRule.isEmpty()){
			return clockLocationRule;
		}
		clockLocationRule = clockLocationDao.getClockLocationRule(dept, workArea, "%", -1L, asOfDate);
		if(!clockLocationRule.isEmpty()){
			return clockLocationRule;
		}
		clockLocationRule = clockLocationDao.getClockLocationRule(dept, -1L, "%", -1L, asOfDate);
		return clockLocationRule;
	}

}
