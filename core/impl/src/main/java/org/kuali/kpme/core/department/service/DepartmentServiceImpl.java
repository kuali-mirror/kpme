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
package org.kuali.kpme.core.department.service;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.department.DepartmentService;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.api.groupkey.HrGroupKeyService;
import org.kuali.kpme.core.department.DepartmentBo;
import org.kuali.kpme.core.department.dao.DepartmentDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

import java.util.*;

public class DepartmentServiceImpl implements DepartmentService {

	private DepartmentDao departmentDao;
    private HrGroupKeyService hrGroupKeyService;
    private static final ModelObjectUtils.Transformer<DepartmentBo, Department> toDepartment =
            new ModelObjectUtils.Transformer<DepartmentBo, Department>() {
                public Department transform(DepartmentBo input) {
                    return DepartmentBo.to(input);
                };
            };
	
	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}
	
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
	
	@Override
    public Department getDepartment(String hrDeptId) {
        return DepartmentBo.to(getDepartmentBo(hrDeptId));
    }


	protected DepartmentBo getDepartmentBo(String hrDeptId) {
		DepartmentBo departmentObj = departmentDao.getDepartment(hrDeptId);

		return departmentObj;
	}
    
	@Override
	public int getDepartmentCount(String department, String groupKeyCode) {
		return departmentDao.getDepartmentCount(department, groupKeyCode);
	}

    @Override
    public Department getDepartment(String department, String groupKeyCode, LocalDate asOfDate) {
        return DepartmentBo.to(getDepartmentBo(department, groupKeyCode, asOfDate));
    }

    protected DepartmentBo getDepartmentBo(String department,  String groupKeyCode, LocalDate asOfDate) {
        return departmentDao.getDepartment(department, groupKeyCode, asOfDate);
    }

    @Override
    public List<String> getDepartmentValuesWithLocation(String location, LocalDate asOfDate) {
        List<String> groupKeyCodes = getGroupKeyCodesFromList(hrGroupKeyService.getHrGroupKeysWithLocation(location, asOfDate));
        List<DepartmentBo> departmentObjs = departmentDao.getDepartmentsWithGroupKeys(groupKeyCodes, asOfDate);
        List<String> depts = new ArrayList<String>();
        for (DepartmentBo departmentObj : departmentObjs) {
            depts.add(departmentObj.getBusinessKeyId());
        }

        return depts;
    }

    @Override
    public List<String> getDepartmentValuesWithLocations(List<String> locations, LocalDate asOfDate) {
        if (CollectionUtils.isEmpty(locations)) {
            return Collections.emptyList();
        }
        
        List<HrGroupKey> groupKeys = hrGroupKeyService.getHrGroupKeysForLocations(locations, asOfDate);
        List<String> groupKeyCodes = getGroupKeyCodesFromList(groupKeys);
        List<DepartmentBo> departmentObjs = departmentDao.getDepartmentsWithGroupKeys(groupKeyCodes, asOfDate);
        List<String> depts = new ArrayList<String>();
        for (DepartmentBo departmentObj : departmentObjs) {
            depts.add(departmentObj.getBusinessKeyId());
        }

        return depts;
    }

    @Override
    public List<Department> getDepartmentsWithLocation(String location, LocalDate asOfDate) {
      	List<HrGroupKey> groupKeys = hrGroupKeyService.getHrGroupKeysWithLocation(location, asOfDate);
        List<String> groupKeyCodes = getGroupKeyCodesFromList(groupKeys);

    	List<DepartmentBo> departmentObjs = departmentDao.getDepartmentsWithGroupKeys(groupKeyCodes, asOfDate);
    	
        return ModelObjectUtils.transform(departmentObjs, toDepartment);
    }

    @Override
    public List<Department> getDepartmentsWithGroupKey(String groupKeyCode, LocalDate asOfDate)
    {
        List<String> groupKeyCodes = new ArrayList<String>();
        groupKeyCodes.add(groupKeyCode);

        List<DepartmentBo> departmentObjs = departmentDao.getDepartmentsWithGroupKeys(groupKeyCodes, asOfDate);

        return ModelObjectUtils.transform(departmentObjs, toDepartment);
    }


    @Override
    public List<Department> getDepartments(String department, String location, LocalDate asOfDate) {
        List<HrGroupKey> groupKeys = hrGroupKeyService.getHrGroupKeysWithLocation(location, asOfDate);
        List<String> groupKeyCodes = getGroupKeyCodesFromList(groupKeys);
    	List<DepartmentBo> departmentObjs = departmentDao.getDepartmentsWithDepartmentAndGroupKeys(department, groupKeyCodes, asOfDate);

        return ModelObjectUtils.transform(departmentObjs, toDepartment);
    }


    @Override
    public List<Department> getDepartments(LocalDate asOfDate)
    {
        List<DepartmentBo> departmentObjs = departmentDao.getDepartments(asOfDate);

        return ModelObjectUtils.transform(departmentObjs, toDepartment);
    }


    public void setHrGroupKeyService(HrGroupKeyService hrGroupKeyService) {
        this.hrGroupKeyService = hrGroupKeyService;
    }

    protected List<String> getGroupKeyCodesFromList(List<HrGroupKey> groupKeys) {
        List<String> groupKeyCodes = new ArrayList<String>();
        for (HrGroupKey key : groupKeys) {
            groupKeyCodes.add(key.getGroupKeyCode());
        }
        return groupKeyCodes;
    }
}