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
package org.kuali.hr.time.department.service;

import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.department.dao.DepartmentDao;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;

import java.sql.Date;
import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {

	private DepartmentDao departmentDao;

    @Override
    public List<Department> getDepartments(String chart, Date asOfDate) {
        List<Department> ds = departmentDao.getDepartments(chart, asOfDate);

        for (Department d : ds) {
            populateDepartmentRoles(d);
        }

        return ds;
    }

    @Override
	public Department getDepartment(String department, Date asOfDate) {
        Department d = departmentDao.getDepartment(department, asOfDate);
        populateDepartmentRoles(d);

		return d;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

    @Override
    public void populateDepartmentRoles(Department department) {
        if (department != null) {
        	department.getRoles().addAll(TkServiceLocator.getTkRoleService().getDepartmentRoles(department.getDept()));
        	department.getInactiveRoles().addAll(TkServiceLocator.getTkRoleService().getDepartmentInactiveRoles(department.getDept()));
        }
    }

	@Override
	public Department getDepartment(String hrDeptId) {
		return departmentDao.getDepartment(hrDeptId);
	}
	
	@Override
	public List<Department> getDepartmentByLocation(String location) {
		return departmentDao.getDepartmentByLocation(location);
	}
	
	@Override
	public int getDepartmentCount(String department) {
		return departmentDao.getDepartmentCount(department);
	}

    @Override
    public List<Department> getDepartments(String department, String location, String descr, String active) {
        return departmentDao.getDepartments(department, location, descr, active);
    }
}
