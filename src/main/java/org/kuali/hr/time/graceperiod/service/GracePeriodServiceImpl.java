package org.kuali.hr.time.graceperiod.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.graceperiod.dao.GracePeriodDao;
import org.kuali.hr.time.graceperiod.rule.GracePeriodRule;

public class GracePeriodServiceImpl implements GracePeriodService {
	private GracePeriodDao gracePeriodDao;

	public GracePeriodDao getGracePeriodDao() {
		return gracePeriodDao;
	}

	public void setGracePeriodDao(GracePeriodDao gracePeriodDao) {
		this.gracePeriodDao = gracePeriodDao;
	}
	
	@CacheResult
	public GracePeriodRule getGracePeriodRule(Date asOfDate){
		return gracePeriodDao.getGracePeriodRule(asOfDate);
	}

	
	@SuppressWarnings("deprecation")
	public Timestamp processGracePeriodRule(Timestamp actualTime, Date asOfDate){
		GracePeriodRule gracePeriodRule = getGracePeriodRule(asOfDate);
		if(gracePeriodRule!=null){
			//go ahead and round off seconds
			actualTime.setSeconds(0);
			
			//BigDecimal minuteIncrement = gracePeriodRule.getHourFactor();
			// xichen. 06/13/11
			BigDecimal minuteIncrement = gracePeriodRule.getHourFactor().multiply(new BigDecimal(60));
			BigDecimal minutes = new BigDecimal(actualTime.getMinutes());
			int bottomIntervalFactor = minutes.divide(minuteIncrement, 0, BigDecimal.ROUND_FLOOR).intValue();
			BigDecimal bottomInterval = new BigDecimal(bottomIntervalFactor).multiply(minuteIncrement);
	        BigDecimal topInterval = new BigDecimal(bottomIntervalFactor + 1).multiply(minuteIncrement);
	        if(bottomInterval.subtract(minutes).abs().intValue() < topInterval.subtract(minutes).abs().intValue()){
	        	actualTime.setMinutes(bottomInterval.setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
	        } else {
	        	if(topInterval.equals(new BigDecimal(60))){
	        		actualTime.setHours(actualTime.getHours()+1);
	        		actualTime.setMinutes(0);
	        	} else {
	        		actualTime.setMinutes(topInterval.setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
	        	}
	        }
		}
		return actualTime;
	}
}
