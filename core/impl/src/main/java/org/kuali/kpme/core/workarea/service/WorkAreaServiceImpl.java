/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.workarea.service;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.workarea.WorkArea;
import org.kuali.kpme.core.api.workarea.service.WorkAreaService;
import org.kuali.kpme.core.workarea.WorkAreaBo;
import org.kuali.kpme.core.workarea.dao.WorkAreaDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkAreaServiceImpl implements WorkAreaService {

	private WorkAreaDao workAreaDao;

	
	public WorkAreaDao getWorkAreaDao() {
		return workAreaDao;
	}

	public void setWorkAreaDao(WorkAreaDao workAreaDao) {
		this.workAreaDao = workAreaDao;
	}
	
	@Override
	public WorkArea getWorkArea(String tkWorkAreaId) {
		return WorkAreaBo.to(workAreaDao.getWorkArea(tkWorkAreaId));
	}

	@Override
	public Long getNextWorkAreaKey() {
		return workAreaDao.getNextWorkAreaKey();
	}


	
	@Override
    public int getWorkAreaCount(String dept, Long workArea) {
		return workAreaDao.getWorkAreaCount(dept, workArea);
    }
	
    @Override
	public WorkArea getWorkArea(Long workArea, LocalDate asOfDate) {
        return WorkAreaBo.to(workAreaDao.getWorkArea(workArea, asOfDate));
	}

    @Override
    public List<WorkArea> getWorkAreasForList(List<Long> workAreas, LocalDate asOfDate) {
        if (CollectionUtils.isEmpty(workAreas)) {
            return Collections.emptyList();
        }
        return ModelObjectUtils.transform(workAreaDao.getWorkAreas(workAreas, asOfDate), WorkAreaBo.toWorkArea);
    }

    @Override
    public List<Long> getWorkAreasForDepartment(String department, LocalDate asOfDate) {
        List<WorkAreaBo> workAreas = workAreaDao.getWorkArea(department, asOfDate);
        List<Long> was = new ArrayList<Long>();
        for (WorkAreaBo wa : workAreas) {
            was.add(wa.getWorkArea());
        }
        return was;
    }

    @Override
    public List<Long> getWorkAreasForDepartments(List<String> departments, LocalDate asOfDate) {
        if (CollectionUtils.isEmpty(departments)) {
            return Collections.emptyList();
        }
        List<WorkAreaBo> workAreas = workAreaDao.getWorkAreaForDepartments(departments, asOfDate);
        List<Long> was = new ArrayList<Long>();
        for (WorkAreaBo wa : workAreas) {
            was.add(wa.getWorkArea());
        }
        return was;
    }
    @Override
    public List<WorkArea> getWorkAreas(String department, LocalDate asOfDate) {
         return ModelObjectUtils.transform(workAreaDao.getWorkArea(department, asOfDate), WorkAreaBo.toWorkArea);
    }
    
	//protected void populateWorkAreaTasks(WorkAreaBo workArea) {
	//	workArea.setTasks(ModelObjectUtils.transform(HrServiceLocator.getTaskService().getTasks(null, null, String.valueOf(workArea.getWorkArea()), workArea.getEffectiveLocalDate(), null), TaskBo.toTaskBo));
	//}


    
	@Override
	public WorkArea saveOrUpdate(WorkArea workArea) {
        if (workArea == null) {
            return null;
        }
        WorkAreaBo bo = WorkAreaBo.from(workArea);
		return WorkAreaBo.to(KRADServiceLocator.getBusinessObjectService().save(bo));
	}
    
}