package org.kuali.hr.time.salgroup.service;

import org.kuali.hr.time.salgroup.SalGroup;
import org.kuali.hr.time.salgroup.dao.SalGroupDao;

import java.sql.Date;

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
	public SalGroup getSalGroup(Long hrSalGroupId) {
		return salGroupDao.getSalGroup(hrSalGroupId);
	}

}
