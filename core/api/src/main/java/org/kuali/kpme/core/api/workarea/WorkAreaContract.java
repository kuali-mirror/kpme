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
package org.kuali.kpme.core.api.workarea;

import java.util.List;

import org.kuali.kpme.core.api.authorization.DepartmentalRule;
import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.department.DepartmentContract;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.mo.KpmeEffectiveDataTransferObject;
import org.kuali.kpme.core.api.role.workarea.WorkAreaPositionRoleMemberBoContract;
import org.kuali.kpme.core.api.role.workarea.WorkAreaPrincipalRoleMemberBoContract;
import org.kuali.kpme.core.api.task.TaskContract;
import org.kuali.kpme.core.api.util.HrApiConstants;

/**
 * <p>WorkAreaContract interface.</p>
 *
 */
public interface WorkAreaContract extends DepartmentalRule, KpmeEffectiveDataTransferObject {
	
	public static final String CACHE_NAME = HrApiConstants.CacheNamespace.NAMESPACE_PREFIX + "WorkArea";
	
	/**
	 * The Primary Key of a WorkArea entry saved in a database
	 *
	 * <p>
	 * tkWorkAreaId of WorkArea
	 * </p>
	 * 
	 * @return tkWorkAreaId for WorkArea
	 */
	public String getTkWorkAreaId();
	
	/**
	 * This unique numeric value is assigned by the system. 
	 *
	 * <p>
	 * workArea of WorkArea
	 * </p>
	 * 
	 * @return workArea for WorkArea
	 */
	//public Long getWorkArea();
	
	/**
	 * Text field which describes the work area. This description is presented to 
	 * the employee when selecting the assignment to clock in or manually record their time.  
	 *
	 * <p>
	 * description of WorkArea
	 * </p>
	 * 
	 * @return description for WorkArea
	 */
    public String getDescription();
    
    /**
	 * Defines role that can edit the overtime code.  
	 *
	 * <p>
	 * overtimeEditRole of WorkArea
	 * </p>
	 * 
	 * @return overtimeEditRole for WorkArea
	 */
    public String getOvertimeEditRole();
    
    /**
   	 * Defines the default earn code for overtime earnings. Only earn codes that are designated
   	 * as an Overtime Earn Code can be used (see Earn Code Maintenance Document). If no earn code 
   	 * is submitted then overtime rule's earn codes will apply to the overtime hours.   
   	 *
   	 * <p>
   	 * defaultOvertimeEdit of WorkArea
   	 * </p>
   	 * 
   	 * @return defaultOvertimeEdit for WorkArea
   	 */
    public String getDefaultOvertimeEarnCode();
    
    /**
	 * Indicates if this WorkArea has overtime earn codes
	 * 
	 * <p>
	 * ovtEarnCode of WorkArea
	 * </p>
	 * 
	 * @return true if workArea has overtime earn code, false if not
	 */
	public Boolean isOvtEarnCode();
	
	/**
   	 * The department this work area is associated with  
   	 *
   	 * <p>
   	 * dept of WorkArea
   	 * </p>
   	 * 
   	 * @return dept for WorkArea
   	 */
	//public String getDept();

	/**
   	 * Additional description field. This could be longer since it is not going to 
   	 * be displayed on the timesheet assignment drop down.   
   	 *
   	 * <p>
   	 * adminDescr of WorkArea
   	 * </p>
   	 * 
   	 * @return adminDescr for WorkArea
   	 */
    public String getAdminDescr();

    /**
   	 *
   	 * @return userPrincipalId for WorkArea
   	 */
    public String getUserPrincipalId();
    

	/**
   	 * The default overtime EarnCode object this work area is associated with  
   	 *
   	 * <p>
   	 * defaultOvertimeEarnCodeObj of WorkArea
   	 * </p>
   	 * 
   	 * @return defaultOvertimeEarnCodeObj for WorkArea
   	 */
    public EarnCodeContract getDefaultOvertimeEarnCodeObj();
    
    /**
   	 * The Department object this work area is associated with  
   	 *
   	 * <p>
   	 * department of WorkArea
   	 * </p>
   	 * 
   	 * @return department for WorkArea
   	 */
	public DepartmentContract getDepartment();

	 /**
   	 * List of Task objects this work area is associated with  
   	 *
   	 * <p>
   	 * tasks of WorkArea
   	 * </p>
   	 * 
   	 * @return tasks for WorkArea
   	 */
	//public List<? extends TaskContract> getTasks();
	
	/**
   	 * List of Active principal role approvers for this work area
   	 *
   	 * <p>
   	 * principalRoleMembers of WorkArea
   	 * </p>
   	 * 
   	 * @return principalRoleMembers for WorkArea
   	 */
	//public List<? extends WorkAreaPrincipalRoleMemberBoContract> getPrincipalRoleMembers();
	
	/**
   	 * List of Inactive principal role approvers for this work area
   	 *
   	 * <p>
   	 * inactivePrincipalRoleMembers of WorkArea
   	 * </p>
   	 * 
   	 * @return inactivePrincipalRoleMembers for WorkArea
   	 */
	//public List<? extends WorkAreaPrincipalRoleMemberBoContract> getInactivePrincipalRoleMembers();;

	/**
   	 * List of Active Position role approvers for this work area
   	 *
   	 * <p>
   	 * positionRoleMembers of WorkArea
   	 * </p>
   	 * 
   	 * @return positionRoleMembers for WorkArea
   	 */
	//public List<? extends WorkAreaPositionRoleMemberBoContract> getPositionRoleMembers();

	/**
   	 * List of Inactive Position role approvers for this work area
   	 *
   	 * <p>
   	 * inactivePositionRoleMembers of WorkArea
   	 * </p>
   	 * 
   	 * @return inactivePositionRoleMembers for WorkArea
   	 */
	//public List<? extends WorkAreaPositionRoleMemberBoContract> getInactivePositionRoleMembers();

	 /**
	 * Indicates if the hours logged under this work area can be distributed or not
	 * 
	 * <p>
	 * hrsDistributionF of WorkArea
	 * </p>
	 * 
	 * @return true if hours can be distributed, false if not
	 */
	public boolean isHrsDistributionF();
}
