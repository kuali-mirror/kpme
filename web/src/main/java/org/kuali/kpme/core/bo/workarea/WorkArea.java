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
package org.kuali.kpme.core.bo.workarea;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import org.kuali.kpme.core.KPMEConstants;
import org.kuali.kpme.core.authorization.DepartmentalRule;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.department.Department;
import org.kuali.kpme.core.bo.earncode.EarnCode;
import org.kuali.kpme.core.bo.task.Task;
import org.kuali.kpme.core.role.workarea.WorkAreaPositionRoleMemberBo;
import org.kuali.kpme.core.role.workarea.WorkAreaPrincipalRoleMemberBo;

public class WorkArea extends HrBusinessObject implements DepartmentalRule {

	private static final long serialVersionUID = 2637145083387914260L;

	public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "WorkArea";

    private String tkWorkAreaId;
    private Long workArea;
    private String description;
    private String overtimeEditRole;
    private String defaultOvertimeEarnCode;
    private Boolean ovtEarnCode;
    private String dept;
    private String adminDescr;
    private String userPrincipalId;
	private boolean history;
	
    private EarnCode defaultOvertimeEarnCodeObj;
    private Department department;
    
    @Transient
    private List<Task> tasks = new ArrayList<Task>();

    @Transient
    private List<WorkAreaPrincipalRoleMemberBo> principalRoleMembers = new ArrayList<WorkAreaPrincipalRoleMemberBo>();
    
    @Transient
    private List<WorkAreaPrincipalRoleMemberBo> inactivePrincipalRoleMembers = new ArrayList<WorkAreaPrincipalRoleMemberBo>();
    
    @Transient
    private List<WorkAreaPositionRoleMemberBo> positionRoleMembers = new ArrayList<WorkAreaPositionRoleMemberBo>();
    
    @Transient
    private List<WorkAreaPositionRoleMemberBo> inactivePositionRoleMembers = new ArrayList<WorkAreaPositionRoleMemberBo>();
    
	@Override
	public String getUniqueKey() {
		return workArea != null ? workArea.toString() : "" + "_" + dept;
	}
    
	@Override
	public String getId() {
		return getTkWorkAreaId();
	}

	@Override
	public void setId(String id) {
		setTkWorkAreaId(id);
	}
	
	public String getTkWorkAreaId() {
		return tkWorkAreaId;
	}

	public void setTkWorkAreaId(String tkWorkAreaId) {
		this.tkWorkAreaId = tkWorkAreaId;
	}
	
	public Long getWorkArea() {
		return workArea;
	}

	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
	}
	
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getOvertimeEditRole() {
        return overtimeEditRole;
    }

    public void setOvertimeEditRole(String overtimeEditRole) {
        this.overtimeEditRole = overtimeEditRole;
    }
    
    public String getDefaultOvertimeEarnCode() {
        return defaultOvertimeEarnCode;
    }

    public void setDefaultOvertimeEarnCode(String defaultOvertimeEarnCode) {
        this.defaultOvertimeEarnCode = defaultOvertimeEarnCode;
    }
    
	public Boolean getOvtEarnCode() {
		return ovtEarnCode;
	}

	public void setOvtEarnCode(Boolean ovtEarnCode) {
		this.ovtEarnCode = ovtEarnCode;
	}
	
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

    public String getAdminDescr() {
        return adminDescr;
    }

    public void setAdminDescr(String adminDescr) {
        this.adminDescr = adminDescr;
    }

    public String getUserPrincipalId() {
        return userPrincipalId;
    }

    public void setUserPrincipalId(String userPrincipalId) {
        this.userPrincipalId = userPrincipalId;
    }
    
	public boolean isHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}

    public EarnCode getDefaultOvertimeEarnCodeObj() {
        return defaultOvertimeEarnCodeObj;
    }

    public void setDefaultOvertimeEarnCodeObj(EarnCode defaultOvertimeEarnCodeObj) {
        this.defaultOvertimeEarnCodeObj = defaultOvertimeEarnCodeObj;
    }
    
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<Task> getTasks() {
	    return tasks;
	}

	public void setTasks(List<Task> tasks) {
	    this.tasks = tasks;
	}
	
	public List<WorkAreaPrincipalRoleMemberBo> getPrincipalRoleMembers() {
		return principalRoleMembers;
	}
	
	public void addPrincipalRoleMember(WorkAreaPrincipalRoleMemberBo principalRoleMember) {
		principalRoleMembers.add(principalRoleMember);
	}
	
	public void removePrincipalRoleMember(WorkAreaPrincipalRoleMemberBo principalRoleMember) {
		principalRoleMembers.remove(principalRoleMember);
	}
	
	public void setPrincipalRoleMembers(List<WorkAreaPrincipalRoleMemberBo> principalRoleMembers) {
		this.principalRoleMembers = principalRoleMembers;
	}

	public List<WorkAreaPrincipalRoleMemberBo> getInactivePrincipalRoleMembers() {
		return inactivePrincipalRoleMembers;
	}
	
	public void addInactivePrincipalRoleMember(WorkAreaPrincipalRoleMemberBo inactivePrincipalRoleMember) {
		inactivePrincipalRoleMembers.add(inactivePrincipalRoleMember);
	}
	
	public void removeInactivePrincipalRoleMember(WorkAreaPrincipalRoleMemberBo inactivePrincipalRoleMember) {
		inactivePrincipalRoleMembers.remove(inactivePrincipalRoleMember);
	}

	public void setInactivePrincipalRoleMembers(List<WorkAreaPrincipalRoleMemberBo> inactivePrincipalRoleMembers) {
		this.inactivePrincipalRoleMembers = inactivePrincipalRoleMembers;
	}

	public List<WorkAreaPositionRoleMemberBo> getPositionRoleMembers() {
		return positionRoleMembers;
	}
	
	public void addPositionRoleMember(WorkAreaPositionRoleMemberBo positionRoleMember) {
		positionRoleMembers.add(positionRoleMember);
	}
	
	public void removePositionRoleMember(WorkAreaPositionRoleMemberBo positionRoleMember) {
		positionRoleMembers.remove(positionRoleMember);
	}

	public void setPositionRoleMembers(List<WorkAreaPositionRoleMemberBo> positionRoleMembers) {
		this.positionRoleMembers = positionRoleMembers;
	}

	public List<WorkAreaPositionRoleMemberBo> getInactivePositionRoleMembers() {
		return inactivePositionRoleMembers;
	}
	
	public void addInactivePositionRoleMember(WorkAreaPositionRoleMemberBo inactivePositionRoleMember) {
		inactivePositionRoleMembers.add(inactivePositionRoleMember);
	}
	
	public void removeInactivePositionRoleMember(WorkAreaPositionRoleMemberBo inactivePositionRoleMember) {
		inactivePositionRoleMembers.remove(inactivePositionRoleMember);
	}

	public void setInactivePositionRoleMembers(List<WorkAreaPositionRoleMemberBo> inactivePositionRoleMembers) {
		this.inactivePositionRoleMembers = inactivePositionRoleMembers;
	}
	
}