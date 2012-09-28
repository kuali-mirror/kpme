/**
 * Copyright 2004-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        //for (WorkArea w : wa) {
           // populateWorkAreaRoles(w);
      //  }

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

	@Override
	public WorkArea getWorkArea(String tkWorkAreaId) {
		return workAreaDao.getWorkArea(tkWorkAreaId);
	}

	@Override
	public Long getNextWorkAreaKey() {
		return workAreaDao.getNextWorkAreaKey();
	}

	@Override
	public List<WorkArea> getWorkAreas(String dept, String workArea,
			String workAreaDescr, Date fromEffdt, Date toEffdt, String active,
			String showHistory) {
		return workAreaDao.getWorkAreas(dept, workArea, workAreaDescr, fromEffdt, toEffdt, active, showHistory);
	}
	
	@Override
    public int getWorkAreaCount(String dept, Long workArea) {
		return workAreaDao.getWorkAreaCount(dept, workArea);
    }
}
