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
        	List<TkRole> deptAdminRoles = TkServiceLocator.getTkRoleService().getDepartmentRoles(
                    department.getDept(),
                    TkConstants.ROLE_TK_DEPT_ADMIN,
                    department.getEffectiveDate()); 
        	List<TkRole> deptViewOnlyRoles = TkServiceLocator.getTkRoleService().getDepartmentRoles(department.getDept(),
                    TkConstants.ROLE_TK_DEPT_VO,
                    department.getEffectiveDate());
        	List<TkRole> deptAdminInactiveRoles = TkServiceLocator.getTkRoleService().getDepartmentInactiveRoles(
                    department.getDept(),
                    TkConstants.ROLE_TK_DEPT_ADMIN,
                    department.getEffectiveDate()); 
        	List<TkRole> deptViewOnlyInactiveRoles = TkServiceLocator.getTkRoleService().getDepartmentInactiveRoles(department.getDept(),
                    TkConstants.ROLE_TK_DEPT_VO,
                    department.getEffectiveDate());
        	
        	department.getRoles().addAll(deptAdminRoles);
        	department.getRoles().addAll(deptViewOnlyRoles);
        	department.getInactiveRoles().addAll(deptAdminInactiveRoles);
        	department.getInactiveRoles().addAll(deptViewOnlyInactiveRoles);
        	
        	//kpme1411, chen, 05/08/12
        	List<TkRole> leaveDeptAdminRoles = TkServiceLocator.getTkRoleService().getDepartmentRoles(
                    department.getDept(),
                    TkConstants.ROLE_LV_DEPT_ADMIN,
                    department.getEffectiveDate()); 
        	List<TkRole> leaveDeptViewOnlyRoles = TkServiceLocator.getTkRoleService().getDepartmentRoles(department.getDept(),
                    TkConstants.ROLE_LV_DEPT_VO,
                    department.getEffectiveDate());
        	List<TkRole> leaveDeptAdminInactiveRoles = TkServiceLocator.getTkRoleService().getDepartmentInactiveRoles(
                    department.getDept(),
                    TkConstants.ROLE_LV_DEPT_ADMIN,
                    department.getEffectiveDate()); 
        	List<TkRole> leaveDeptViewOnlyInactiveRoles = TkServiceLocator.getTkRoleService().getDepartmentInactiveRoles(department.getDept(),
                    TkConstants.ROLE_LV_DEPT_VO,
                    department.getEffectiveDate());
        	
        	department.getRoles().addAll(leaveDeptAdminRoles);
        	department.getRoles().addAll(leaveDeptViewOnlyRoles);
        	department.getInactiveRoles().addAll(leaveDeptAdminInactiveRoles);
        	department.getInactiveRoles().addAll(leaveDeptViewOnlyInactiveRoles);
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
