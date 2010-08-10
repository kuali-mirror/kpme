package org.kuali.hr.time.workarea.dao;

import org.apache.log4j.Logger;
import org.kuali.hr.time.workarea.WorkArea;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class WorkAreaDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements WorkAreaDao {

    private static final Logger LOG = Logger.getLogger(WorkAreaDaoSpringOjbImpl.class);
    
    @Override
    public WorkArea getWorkArea(Long workAreaId) {
	return (WorkArea)this.getPersistenceBrokerTemplate().getObjectById(WorkArea.class, workAreaId);
    }
    
    @Override
    public void saveOrUpdate(WorkArea workArea) {
	this.getPersistenceBrokerTemplate().store(workArea);
    }

}
