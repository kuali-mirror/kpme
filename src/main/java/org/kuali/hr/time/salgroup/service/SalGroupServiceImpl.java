package org.kuali.hr.time.salgroup.service;

import java.sql.Date;

import org.kuali.hr.time.salgroup.SalGroup;
import org.kuali.hr.time.salgroup.dao.SalGroupDao;

public class SalGroupServiceImpl implements SalGroupService {
	
	private SalGroupDao salGroupDao;

	@Override
	public SalGroup getSalGroup(String salGroup, Date asOfDate) {
		return salGroupDao.getSalGroup(salGroup, asOfDate);
	}

	public void setSalGroupDao(SalGroupDao salGroupDao) {
		this.salGroupDao = salGroupDao;
	}

	@Override
	public SalGroup getSalGroup(Long tkSalGroupId) {
		return salGroupDao.getSalGroup(tkSalGroupId);
	}

}
