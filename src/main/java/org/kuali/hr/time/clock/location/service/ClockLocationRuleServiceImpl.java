package org.kuali.hr.time.clock.location.service;

import java.sql.Date;

import org.kuali.hr.time.clock.location.ClockLocationRule;
import org.kuali.hr.time.clock.location.dao.ClockLocationDao;

public class ClockLocationRuleServiceImpl implements ClockLocationRuleService {
	private ClockLocationDao clockLocationDao;

	@Override
	public ClockLocationRule getClockLocationRule(String principalId,
			Date effectiveDate) {
		return clockLocationDao.getClockLocationRule(principalId, effectiveDate);
	}

	public ClockLocationDao getClockLocationDao() {
		return clockLocationDao;
	}

	public void setClockLocationDao(ClockLocationDao clockLocationDao) {
		this.clockLocationDao = clockLocationDao;
	}

}
