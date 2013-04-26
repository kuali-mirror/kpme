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
package org.kuali.hr.core.department.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;
import org.kuali.hr.core.department.Department;
import org.kuali.hr.core.department.dao.DepartmentDao;
import org.kuali.hr.core.role.KPMERole;
import org.kuali.hr.core.role.department.DepartmentPrincipalRoleMemberBo;
import org.kuali.hr.tklm.leave.service.base.LmServiceLocator;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.impl.role.RoleMemberBo;

public class DepartmentServiceImpl implements DepartmentService {

	private DepartmentDao departmentDao;
	
	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}
	
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
	
	@Override
	public Department getDepartment(String hrDeptId) {
		Department departmentObj = departmentDao.getDepartment(hrDeptId);
		
		if (departmentObj != null) {
			populateDepartmentRoleMembers(departmentObj, departmentObj.getEffectiveLocalDate());
		}
		
		return departmentObj;
	}
	
    @Override
    public List<Department> getDepartments(String department, String location, String descr, String active) {
    	List<Department> departmentObjs = departmentDao.getDepartments(department, location, descr, active);
        
        for (Department departmentObj : departmentObjs) {
        	populateDepartmentRoleMembers(departmentObj, departmentObj.getEffectiveLocalDate());
        }
        
        return departmentObjs;
    }
    
	@Override
	public int getDepartmentCount(String department) {
		return departmentDao.getDepartmentCount(department);
	}
	
    @Override
	public Department getDepartment(String department, LocalDate asOfDate) {
        Department departmentObj = departmentDao.getDepartment(department, asOfDate);
        
        if (departmentObj != null) {
        	populateDepartmentRoleMembers(departmentObj, asOfDate);
        }

		return departmentObj;
	}

    @Override
    public List<Department> getDepartments(String location, LocalDate asOfDate) {
        List<Department> departmentObjs = departmentDao.getDepartments(location, asOfDate);

        for (Department departmentObj : departmentObjs) {
        	populateDepartmentRoleMembers(departmentObj, departmentObj.getEffectiveLocalDate());
        }

        return departmentObjs;
    }

    private void populateDepartmentRoleMembers(Department department, LocalDate asOfDate) {
    	Set<RoleMember> roleMembers = new HashSet<RoleMember>();
    	
    	if (department != null && asOfDate != null) {
	    	roleMembers.addAll(TkServiceLocator.getTKRoleService().getRoleMembersInDepartment(KPMERole.TIME_DEPARTMENT_VIEW_ONLY.getRoleName(), department.getDept(), asOfDate.toDateTimeAtStartOfDay(), false));
	    	roleMembers.addAll(TkServiceLocator.getTKRoleService().getRoleMembersInDepartment(KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), department.getDept(), asOfDate.toDateTimeAtStartOfDay(), false));
	    	roleMembers.addAll(LmServiceLocator.getLMRoleService().getRoleMembersInDepartment(KPMERole.LEAVE_DEPARTMENT_VIEW_ONLY.getRoleName(), department.getDept(), asOfDate.toDateTimeAtStartOfDay(), false));
	    	roleMembers.addAll(LmServiceLocator.getLMRoleService().getRoleMembersInDepartment(KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), department.getDept(), asOfDate.toDateTimeAtStartOfDay(), false));
	
	    	for (RoleMember roleMember : roleMembers) {
	    		RoleMemberBo roleMemberBo = RoleMemberBo.from(roleMember);
	    		
	    		if (roleMemberBo.isActive()) {
	    			department.addRoleMember(DepartmentPrincipalRoleMemberBo.from(roleMemberBo, roleMember.getAttributes()));
	    		} else {
	    			department.addInactiveRoleMember(DepartmentPrincipalRoleMemberBo.from(roleMemberBo, roleMember.getAttributes()));
	    		}
	    	}
    	}
    }

}