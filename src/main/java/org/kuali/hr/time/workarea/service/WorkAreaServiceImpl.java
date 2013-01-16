/**
 * Copyright 2004-2013 The Kuali Foundation
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

import java.sql.Date;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.hr.time.workarea.dao.WorkAreaDao;

public class WorkAreaServiceImpl implements WorkAreaService {

	private WorkAreaDao workAreaDao;

    @Override
    public List<WorkArea> getWorkAreas(String department, Date asOfDate) {
        List<WorkArea> workAreas = workAreaDao.getWorkArea(department, asOfDate);

        for (WorkArea workArea : workAreas) {
        	populateWorkAreaTasks(workArea);
        	populateWorkAreaRoles(workArea);
        }

        return workAreas;
    }

    @Override
	public WorkArea getWorkArea(Long workArea, Date asOfDate) {
        WorkArea workAreaObj = workAreaDao.getWorkArea(workArea, asOfDate);
        populateWorkAreaTasks(workAreaObj);
        populateWorkAreaRoles(workAreaObj);
		return workAreaObj;
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
	public void populateWorkAreaTasks(WorkArea workArea) {
		if (workArea != null) {
			workArea.setTasks(
					TkServiceLocator.getTaskService().getTasks(
                            null,
                            null,
                            ObjectUtils.toString(workArea.getWorkArea()),
                            workArea.getEffectiveDate(),
                            null
                    )
            );
		}
	}

    @Override
    public void populateWorkAreaRoles(WorkArea workArea) {
        if (workArea != null) {
            workArea.setRoles(TkServiceLocator.getTkRoleService().getWorkAreaRoles(workArea.getWorkArea()));
            workArea.setInactiveRoles(TkServiceLocator.getTkRoleService().getInActiveWorkAreaRoles(workArea.getWorkArea()));
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
