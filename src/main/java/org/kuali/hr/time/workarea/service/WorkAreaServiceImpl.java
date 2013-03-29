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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.kuali.hr.core.role.KPMERole;
import org.kuali.hr.core.role.KPMERoleMemberAttribute;
import org.kuali.hr.core.role.workarea.WorkAreaPositionRoleMemberBo;
import org.kuali.hr.core.role.workarea.WorkAreaPrincipalRoleMemberBo;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.hr.time.workarea.dao.WorkAreaDao;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.impl.role.RoleMemberBo;

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
	        populateWorkAreaRoleMembers(workAreaObj, workAreaObj.getEffectiveDate());
		}
		
		return workAreaObj;
	}

	@Override
	public Long getNextWorkAreaKey() {
		return workAreaDao.getNextWorkAreaKey();
	}

	@Override
	public List<WorkArea> getWorkAreas(String dept, String workArea, String workAreaDescr, Date fromEffdt, Date toEffdt, String active, String showHistory) {
		List<WorkArea> workAreaObjs = workAreaDao.getWorkAreas(dept, workArea, workAreaDescr, fromEffdt, toEffdt, active, showHistory);
		
        for (WorkArea workAreaObj : workAreaObjs) {
        	populateWorkAreaTasks(workAreaObj);
        	populateWorkAreaRoleMembers(workAreaObj, workAreaObj.getEffectiveDate());
        }

        return workAreaObjs;
	}
	
	@Override
    public int getWorkAreaCount(String dept, Long workArea) {
		return workAreaDao.getWorkAreaCount(dept, workArea);
    }
	
    @Override
	public WorkArea getWorkArea(Long workArea, Date asOfDate) {
        WorkArea workAreaObj = workAreaDao.getWorkArea(workArea, asOfDate);
        
        if (workAreaObj != null) {
	        populateWorkAreaTasks(workAreaObj);
	        populateWorkAreaRoleMembers(workAreaObj, asOfDate);
        }
        
		return workAreaObj;
	}

    @Override
    public List<WorkArea> getWorkAreas(String department, Date asOfDate) {
        List<WorkArea> workAreas = workAreaDao.getWorkArea(department, asOfDate);

        for (WorkArea workArea : workAreas) {
        	populateWorkAreaTasks(workArea);
        	populateWorkAreaRoleMembers(workArea, asOfDate);
        }

        return workAreas;
    }
    
	private void populateWorkAreaTasks(WorkArea workArea) {
		workArea.setTasks(TkServiceLocator.getTaskService().getTasks(null, null, String.valueOf(workArea.getWorkArea()), workArea.getEffectiveDate(), null));
	}

    private void populateWorkAreaRoleMembers(WorkArea workArea, Date asOfDate) {
    	Set<RoleMember> roleMembers = new HashSet<RoleMember>();
    	
    	roleMembers.addAll(TkServiceLocator.getHRRoleService().getRoleMembersInWorkArea(KPMERole.REVIEWER.getRoleName(), workArea.getWorkArea(), new DateTime(asOfDate), false));
    	roleMembers.addAll(TkServiceLocator.getHRRoleService().getRoleMembersInWorkArea(KPMERole.APPROVER.getRoleName(), workArea.getWorkArea(), new DateTime(asOfDate), false));
    	roleMembers.addAll(TkServiceLocator.getHRRoleService().getRoleMembersInWorkArea(KPMERole.APPROVER_DELEGATE.getRoleName(), workArea.getWorkArea(), new DateTime(asOfDate), false));
    
    	for (RoleMember roleMember : roleMembers) {
    		RoleMemberBo roleMemberBo = RoleMemberBo.from(roleMember);
    		
    		if (!roleMember.getAttributes().containsKey(KPMERoleMemberAttribute.POSITION.getRoleMemberAttributeName())) {
        		if (roleMemberBo.isActive()) {
        			workArea.addPrincipalRoleMember(WorkAreaPrincipalRoleMemberBo.from(roleMemberBo));
        		} else {
        			workArea.addInactivePrincipalRoleMember(WorkAreaPrincipalRoleMemberBo.from(roleMemberBo));
        		}
    		} else {
        		if (roleMemberBo.isActive()) {
        			workArea.addPositionRoleMember(WorkAreaPositionRoleMemberBo.from(roleMemberBo));
        		} else {
        			workArea.addInactivePositionRoleMember(WorkAreaPositionRoleMemberBo.from(roleMemberBo));
        		}
    		}
    	}
    }
    
    @Override
    public List<Long> getReviewerWorkAreas(String principalId) {
    	return TkServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(principalId, KPMERole.REVIEWER.getRoleName(), new DateTime(), true);
    }
    
    @Override
    public List<Long> getApproverWorkAreas(String principalId) {
    	return TkServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(principalId, KPMERole.APPROVER.getRoleName(), new DateTime(), true);
    }
    
    @Override
    public List<Long> getApproverDelegateWorkAreas(String principalId) {
    	return TkServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(principalId, KPMERole.APPROVER_DELEGATE.getRoleName(), new DateTime(), true);
    }
    
    @Override
    public List<Long> getApproverAndApproverDelegateWorkAreas(String principalId) {
    	Set<Long> workAreas = new HashSet<Long>();
    	
    	workAreas.addAll(getApproverWorkAreas(principalId));
    	workAreas.addAll(getApproverDelegateWorkAreas(principalId));
    	
    	return new ArrayList<Long>(workAreas);
    }
    
	@Override
	public void saveOrUpdate(WorkArea workArea) {
		workAreaDao.saveOrUpdate(workArea);
	}
    
}