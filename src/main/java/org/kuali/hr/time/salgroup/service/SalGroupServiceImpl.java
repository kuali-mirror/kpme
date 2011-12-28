package org.kuali.hr.time.salgroup.service;

import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.salgroup.SalGroup;
import org.kuali.hr.time.salgroup.dao.SalGroupDao;
import org.kuali.hr.time.util.TkConstants;

import java.sql.Date;

public class SalGroupServiceImpl implements SalGroupService {
	
	private SalGroupDao salGroupDao;

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public SalGroup getSalGroup(String salGroup, Date asOfDate) {
		return salGroupDao.getSalGroup(salGroup, asOfDate);
	}

	public void setSalGroupDao(SalGroupDao salGroupDao) {
		this.salGroupDao = salGroupDao;
	}

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public SalGroup getSalGroup(String hrSalGroupId) {
		return salGroupDao.getSalGroup(hrSalGroupId);
	}

}
