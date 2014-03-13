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
package org.kuali.kpme.core.workarea;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import org.kuali.kpme.core.api.paytype.PayType;
import org.kuali.kpme.core.api.workarea.WorkArea;
import org.kuali.kpme.core.api.workarea.WorkAreaContract;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.department.DepartmentBo;
import org.kuali.kpme.core.earncode.EarnCodeBo;
import org.kuali.kpme.core.role.workarea.WorkAreaPositionRoleMemberBo;
import org.kuali.kpme.core.role.workarea.WorkAreaPrincipalRoleMemberBo;
import org.kuali.kpme.core.task.TaskBo;
import org.kuali.kpme.core.util.HrConstants;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class WorkAreaBo extends HrBusinessObject implements WorkAreaContract {

	private static final String WORK_AREA = "workArea";

	private static final long serialVersionUID = 2637145083387914260L;

	public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "WorkArea";
	//KPME-2273/1965 Primary Business Keys List.	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
	            .add(WORK_AREA)
	            .build();
    public static final ModelObjectUtils.Transformer<WorkAreaBo, WorkArea> toWorkArea =
            new ModelObjectUtils.Transformer<WorkAreaBo, WorkArea>() {
                public WorkArea transform(WorkAreaBo input) {
                    return WorkAreaBo.to(input);
                };
            };
    public static final ModelObjectUtils.Transformer<WorkArea, WorkAreaBo> toWorkAreaBo =
            new ModelObjectUtils.Transformer<WorkArea, WorkAreaBo>() {
                public WorkAreaBo transform(WorkArea input) {
                    return WorkAreaBo.from(input);
                };
            };

    private String tkWorkAreaId;
    private Long workArea;
    private String description;
    private String overtimeEditRole;
    private String defaultOvertimeEarnCode;
    private Boolean ovtEarnCode;
    private String dept;
    private String adminDescr;
	private boolean history;
	private boolean hrsDistributionF;	
	
    private EarnCodeBo defaultOvertimeEarnCodeObj;
    private DepartmentBo department;
    
    @Transient
    private List<TaskBo> tasks = new ArrayList<TaskBo>();

    @Transient
    private List<WorkAreaPrincipalRoleMemberBo> principalRoleMembers = new ArrayList<WorkAreaPrincipalRoleMemberBo>();
    
    @Transient
    private List<WorkAreaPrincipalRoleMemberBo> inactivePrincipalRoleMembers = new ArrayList<WorkAreaPrincipalRoleMemberBo>();
    
    @Transient
    private List<WorkAreaPositionRoleMemberBo> positionRoleMembers = new ArrayList<WorkAreaPositionRoleMemberBo>();
    
    @Transient
    private List<WorkAreaPositionRoleMemberBo> inactivePositionRoleMembers = new ArrayList<WorkAreaPositionRoleMemberBo>();
    
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.put(WORK_AREA, this.getWorkArea())		
				.build();
	}
    
    
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
		return isOvtEarnCode();
	}
    public Boolean isOvtEarnCode() {
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

	public boolean isHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}

    public EarnCodeBo getDefaultOvertimeEarnCodeObj() {
        return defaultOvertimeEarnCodeObj;
    }

    public void setDefaultOvertimeEarnCodeObj(EarnCodeBo defaultOvertimeEarnCodeObj) {
        this.defaultOvertimeEarnCodeObj = defaultOvertimeEarnCodeObj;
    }
    
	public DepartmentBo getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentBo department) {
		this.department = department;
	}

	public List<TaskBo> getTasks() {
	    return tasks;
	}

	public void setTasks(List<TaskBo> tasks) {
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

	public boolean isHrsDistributionF() {
		return hrsDistributionF;
	}

	public void setHrsDistributionF(boolean hrsDistributionF) {
		this.hrsDistributionF = hrsDistributionF;
	}

    public static WorkAreaBo from(WorkArea im) {
        if (im == null) {
            return null;
        }
        WorkAreaBo wa = new WorkAreaBo();

        wa.setTkWorkAreaId(im.getTkWorkAreaId());
        wa.setWorkArea(im.getWorkArea());
        wa.setDescription(im.getDescription());
        wa.setOvertimeEditRole(im.getOvertimeEditRole());
        wa.setDefaultOvertimeEarnCode(im.getDefaultOvertimeEarnCode());
        wa.setOvtEarnCode(im.isOvtEarnCode());
        wa.setDept(im.getDept());
        wa.setAdminDescr(im.getAdminDescr());
        wa.setHrsDistributionF(im.isHrsDistributionF());
        wa.setDefaultOvertimeEarnCodeObj(im.getDefaultOvertimeEarnCodeObj() == null ? null : EarnCodeBo.from(im.getDefaultOvertimeEarnCodeObj()));
        wa.setDepartment(im.getDepartment() == null ? null : DepartmentBo.from(im.getDepartment()));

        wa.setEffectiveDate(im.getEffectiveLocalDate() == null ? null : im.getEffectiveLocalDate().toDate());
        wa.setActive(im.isActive());
        if (im.getCreateTime() != null) {
            wa.setTimestamp(new Timestamp(im.getCreateTime().getMillis()));
        }
        wa.setUserPrincipalId(im.getUserPrincipalId());
        wa.setVersionNumber(im.getVersionNumber());
        wa.setObjectId(im.getObjectId());

        return wa;
    }

    public static WorkArea to(WorkAreaBo bo) {
        if (bo == null) {
            return null;
        }

        return WorkArea.Builder.create(bo).build();
    }
}