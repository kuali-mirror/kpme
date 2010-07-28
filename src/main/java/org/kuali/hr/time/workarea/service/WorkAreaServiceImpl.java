package org.kuali.hr.time.workarea.service;

import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.hr.time.workarea.dao.WorkAreaDao;

public class WorkAreaServiceImpl implements WorkAreaService {

    WorkAreaDao workAreaDao;

    public WorkAreaServiceImpl() {
    }
    
    @Override
    public WorkArea getWorkArea(Long workAreaId) {
	return workAreaDao.getWorkArea(workAreaId);
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
