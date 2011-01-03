package org.kuali.hr.time.accrual.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.hr.time.accrual.TimeOffAccrual;
import org.kuali.hr.time.accrual.dao.TimeOffAccrualDao;
import org.kuali.hr.time.cache.CacheResult;

public class TimeOffAccrualServiceImpl implements TimeOffAccrualService {

	private static final Logger LOG = Logger.getLogger(TimeOffAccrualServiceImpl.class);
	private TimeOffAccrualDao timeOffAccrualDao;

	public void setTimeOffAccrualDao(TimeOffAccrualDao timeOffAccrualDao) {
		this.timeOffAccrualDao = timeOffAccrualDao;
	}
	
	@Override
	@CacheResult
	public List<TimeOffAccrual> getTimeOffAccruals(String principalId) {
		return timeOffAccrualDao.getTimeOffAccruals(principalId);
	}
	
	@Override
	public List<Map<String, Object>> getTimeOffAccrualsCalc(String principalId) {
		
		List<Map<String, Object>> timeOffAccrualsCalc = new ArrayList<Map<String, Object>>();
		
		for(TimeOffAccrual timeOffAccrual : getTimeOffAccruals(principalId)) {
			Map<String, Object> output = new LinkedHashMap<String, Object>();
			output.put("accrualCategory", timeOffAccrual.getAccrualCategory());
			output.put("hoursAccrued", timeOffAccrual.getHoursAccrued());
			output.put("hoursTaken", timeOffAccrual.getHoursTaken());
			output.put("hoursAdjust", timeOffAccrual.getHoursAdjust());
			BigDecimal totalHours = timeOffAccrual.getHoursAccrued().subtract(timeOffAccrual.getHoursTaken()).add(timeOffAccrual.getHoursAdjust());
			output.put("totalHours", totalHours);
			output.put("effdt", timeOffAccrual.getEffectiveDate());
			
			timeOffAccrualsCalc.add(output);
		}
		
		return timeOffAccrualsCalc;
	}
}
