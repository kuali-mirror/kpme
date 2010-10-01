package org.kuali.hr.time.workarea.service;

import java.sql.Date;

import org.apache.log4j.Logger;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.hr.time.workarea.dao.WorkAreaDao;

public class WorkAreaServiceImpl implements WorkAreaService {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(WorkAreaServiceImpl.class);
	
	private WorkAreaDao workAreaDao;
	
	public WorkAreaServiceImpl() {
	}

	@Override
	public WorkArea getWorkArea(Long workAreaId, Date asOfDate) {
		return workAreaDao.getWorkArea(workAreaId, asOfDate);
	}

	@Override
	public void saveOrUpdate(WorkArea workArea) {
		workAreaDao.saveOrUpdate(workArea);
	}

	public WorkAreaDao getWorkAreaDao() {
		return workAreaDao;
	}

	public void setWorkAreaDao(WorkAreaDao workAreaDao) {
		this.workAreaDao = workAreaDao;
	}

}
