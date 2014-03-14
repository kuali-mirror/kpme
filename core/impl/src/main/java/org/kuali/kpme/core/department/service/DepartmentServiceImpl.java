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
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.department.DepartmentBo;
import org.kuali.kpme.core.department.dao.DepartmentDao;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import java.util.*;

public class DepartmentServiceImpl implements DepartmentService {

	private DepartmentDao departmentDao;
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
    public List<Department> getDepartments(String userPrincipalId, String department, String location, String descr, String active, String showHistory, String payrollApproval) {
    	List<DepartmentBo> results = new ArrayList<DepartmentBo>();
    	
    	List<DepartmentBo> departmentObjs = departmentDao.getDepartments(department, location, descr, active, showHistory, payrollApproval);
        
    	for (DepartmentBo departmentObj : departmentObjs) {
        	Map<String, String> roleQualification = new HashMap<String, String>();
        	roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, userPrincipalId);
        	roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), departmentObj.getDept());
        	roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), departmentObj.getLocation());
        	
        	if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
    		  || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(userPrincipalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				  KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>(), roleQualification)) {
        		results.add(departmentObj);
        	}
    	}
        
        return ModelObjectUtils.transform(results, toDepartment);
    }
    
	@Override
	public int getDepartmentCount(String department) {
		return departmentDao.getDepartmentCount(department);
	}

    @Override
    public Department getDepartment(String department, LocalDate asOfDate) {
        return DepartmentBo.to(getDepartmentBo(department, asOfDate));
    }

    protected DepartmentBo getDepartmentBo(String department, LocalDate asOfDate) {
        return departmentDao.getDepartment(department, asOfDate);
    }

    @Override
    public List<String> getDepartmentValuesWithLocation(String location, LocalDate asOfDate) {
        List<DepartmentBo> departmentObjs = departmentDao.getDepartments(location, asOfDate);
        List<String> depts = new ArrayList<String>();
        for (DepartmentBo departmentObj : departmentObjs) {
            depts.add(departmentObj.getDept());
        }

        return depts;
    }

    @Override
    public List<String> getDepartmentValuesWithLocations(List<String> locations, LocalDate asOfDate) {
        if (CollectionUtils.isEmpty(locations)) {
            return Collections.emptyList();
        }
        List<DepartmentBo> departmentObjs = departmentDao.getDepartmentsForLocations(locations, asOfDate);
        List<String> depts = new ArrayList<String>();
        for (DepartmentBo departmentObj : departmentObjs) {
            depts.add(departmentObj.getDept());
        }

        return depts;
    }

    @Override
    public List<Department> getDepartmentsWithLocation(String location, LocalDate asOfDate) {
        return ModelObjectUtils.transform(departmentDao.getDepartments(location, asOfDate), toDepartment);
    }
    
    @Override
	public List<Department> getDepartments(String department) {
		return ModelObjectUtils.transform(departmentDao.getDepartments(department), toDepartment);
	}

    @Override
    public Department getDepartmentWithDeptAndLocation(String department, String location, LocalDate asOfDate) {
    	return DepartmentBo.to(departmentDao.getDepartment(department, location, asOfDate));
    }
    
    @Override
    public List<String> getLocationsValuesWithInstitution(String institution, LocalDate asOfDate) {
        List<DepartmentBo> departmentObjs = departmentDao.getDepartmentsForInstitution(institution, asOfDate);
        List<String> locations = new ArrayList<String>();
        for (DepartmentBo departmentObj : departmentObjs) {
        	if (!locations.contains(departmentObj.getLocation())) {
            	locations.add(departmentObj.getLocation());        		
        	}
        }

        return locations;
    }

}