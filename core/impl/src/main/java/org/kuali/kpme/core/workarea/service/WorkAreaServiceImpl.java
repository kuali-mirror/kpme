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
package org.kuali.kpme.core.workarea.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.department.Department;
import org.kuali.kpme.core.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.role.workarea.WorkAreaPositionRoleMemberBo;
import org.kuali.kpme.core.role.workarea.WorkAreaPrincipalRoleMemberBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.workarea.WorkArea;
import org.kuali.kpme.core.workarea.dao.WorkAreaDao;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.role.RoleMemberBo;
import org.springframework.util.CollectionUtils;

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
		WorkArea workAreaObj = workAreaDao.getWorkArea(tkWorkAreaId);
		
		if (workAreaObj != null) {
			populateWorkAreaTasks(workAreaObj);
	        populateWorkAreaRoleMembers(workAreaObj, workAreaObj.getEffectiveLocalDate());
		}
		
		return workAreaObj;
	}

	@Override
	public Long getNextWorkAreaKey() {
		return workAreaDao.getNextWorkAreaKey();
	}

	@Override
	public List<WorkArea> getWorkAreas(String userPrincipalId, String dept, String workArea, String workAreaDescr, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
        List<WorkArea> results = new ArrayList<WorkArea>();
		
		List<WorkArea> workAreaObjs = workAreaDao.getWorkAreas(dept, workArea, workAreaDescr, fromEffdt, toEffdt, active, showHistory);
		
        for (WorkArea workAreaObj : workAreaObjs) {
        	String department = workAreaObj.getDept();
        	Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, workAreaObj.getEffectiveLocalDate());
        	String location = departmentObj != null ? departmentObj.getLocation() : null;
        	
        	Map<String, String> roleQualification = new HashMap<String, String>();
        	roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, userPrincipalId);
        	roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
        	roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
        	
        	if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
    		  || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(userPrincipalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				  KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>(), roleQualification)) {
        		results.add(workAreaObj);
        	}
        }
        
        for (WorkArea result : results) {
        	populateWorkAreaTasks(result);
        	populateWorkAreaRoleMembers(result, result.getEffectiveLocalDate());
        }

        return results;
	}
	
	@Override
    public int getWorkAreaCount(String dept, Long workArea) {
		return workAreaDao.getWorkAreaCount(dept, workArea);
    }
	
    @Override
	public WorkArea getWorkArea(Long workArea, LocalDate asOfDate) {
        WorkArea workAreaObj = workAreaDao.getWorkArea(workArea, asOfDate);
        
        if (workAreaObj != null) {
	        populateWorkAreaTasks(workAreaObj);
	        populateWorkAreaRoleMembers(workAreaObj, asOfDate);
        }
        
		return workAreaObj;
	}

    @Override
    public List<WorkArea> getWorkAreas(String department, LocalDate asOfDate) {
        List<WorkArea> workAreas = workAreaDao.getWorkArea(department, asOfDate);

        for (WorkArea workArea : workAreas) {
        	populateWorkAreaTasks(workArea);
        	populateWorkAreaRoleMembers(workArea, asOfDate);
        }

        return workAreas;
    }
    
	private void populateWorkAreaTasks(WorkArea workArea) {
		workArea.setTasks(HrServiceLocator.getTaskService().getTasks(null, null, String.valueOf(workArea.getWorkArea()), workArea.getEffectiveLocalDate(), null));
	}

    private void populateWorkAreaRoleMembers(WorkArea workArea, LocalDate asOfDate) {
    	if (workArea != null && asOfDate != null 
    			&& CollectionUtils.isEmpty(workArea.getPrincipalRoleMembers()) && CollectionUtils.isEmpty(workArea.getInactivePrincipalRoleMembers())
    			&& CollectionUtils.isEmpty(workArea.getPositionRoleMembers()) && CollectionUtils.isEmpty(workArea.getInactivePositionRoleMembers())) {
    		Set<RoleMember> roleMembers = new HashSet<RoleMember>();
    		
	    	roleMembers.addAll(HrServiceLocator.getKPMERoleService().getRoleMembersInWorkArea(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.REVIEWER.getRoleName(), workArea.getWorkArea(), asOfDate.toDateTimeAtStartOfDay(), false));
	    	roleMembers.addAll(HrServiceLocator.getKPMERoleService().getRoleMembersInWorkArea(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), workArea.getWorkArea(), asOfDate.toDateTimeAtStartOfDay(), false));
	    	roleMembers.addAll(HrServiceLocator.getKPMERoleService().getRoleMembersInWorkArea(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName(), workArea.getWorkArea(), asOfDate.toDateTimeAtStartOfDay(), false));
	    
	    	for (RoleMember roleMember : roleMembers) {
	    		RoleMemberBo roleMemberBo = RoleMemberBo.from(roleMember);
	    		
	    		if (!roleMember.getAttributes().containsKey(KPMERoleMemberAttribute.POSITION.getRoleMemberAttributeName())) {
	        		if (roleMemberBo.isActive()) {
	        			workArea.addPrincipalRoleMember(WorkAreaPrincipalRoleMemberBo.from(roleMemberBo, roleMember.getAttributes()));
	        		} else {
	        			workArea.addInactivePrincipalRoleMember(WorkAreaPrincipalRoleMemberBo.from(roleMemberBo, roleMember.getAttributes()));
	        		}
	    		} else {
	        		if (roleMemberBo.isActive()) {
	        			workArea.addPositionRoleMember(WorkAreaPositionRoleMemberBo.from(roleMemberBo, roleMember.getAttributes()));
	        		} else {
	        			workArea.addInactivePositionRoleMember(WorkAreaPositionRoleMemberBo.from(roleMemberBo, roleMember.getAttributes()));
	        		}
	    		}
	    	}
    	}
    }
    
	@Override
	public void saveOrUpdate(WorkArea workArea) {
		workAreaDao.saveOrUpdate(workArea);
	}
    
}