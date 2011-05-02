package org.kuali.hr.time.workarea.service;

import org.apache.log4j.Logger;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.hr.time.workarea.dao.WorkAreaDao;

import java.sql.Date;
import java.util.List;

public class WorkAreaServiceImpl implements WorkAreaService {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(WorkAreaServiceImpl.class);

	private WorkAreaDao workAreaDao;

	public WorkAreaServiceImpl() {
	}

    @Override
    public List<WorkArea> getWorkAreas(String department, Date asOfDate) {
        List<WorkArea> wa = workAreaDao.getWorkArea(department, asOfDate);

        // Load Roles
        // TODO: We may not need to do this, as this method is currently only grabbing WorkArea objects to build role structures for users.
        for (WorkArea w : wa) {
            populateWorkAreaRoles(w);
        }

        return wa;
    }

    @Override
	public WorkArea getWorkArea(Long workArea, Date asOfDate) {
        WorkArea w = workAreaDao.getWorkArea(workArea, asOfDate);
        populateWorkAreaRoles(w);
		return w;
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

    @Override
    public void populateWorkAreaRoles(WorkArea workArea) {
        if (workArea != null) {
            workArea.setRoles(
                    TkServiceLocator.getTkRoleService().getWorkAreaRoles(
                            workArea.getWorkArea(),
                            TkConstants.ROLE_TK_APPROVER,
                            workArea.getEffectiveDate()
                    )
            );
            workArea.setInactiveRoles(
            		TkServiceLocator.getTkRoleService().getInActiveWorkAreaRoles(
            				workArea.getWorkArea(),
            				TkConstants.ROLE_TK_APPROVER,
            				workArea.getEffectiveDate()
            		)
            );
        }
    }
}
