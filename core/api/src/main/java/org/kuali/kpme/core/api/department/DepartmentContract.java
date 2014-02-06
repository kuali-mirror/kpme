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
package org.kuali.kpme.core.api.department;

import java.util.List;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.kfs.coa.businessobject.ChartContract;
import org.kuali.kpme.core.api.kfs.coa.businessobject.OrganizationContract;
import org.kuali.kpme.core.api.location.LocationContract;
import org.kuali.kpme.core.api.role.department.DepartmentPrincipalRoleMemberBoContract;
import org.kuali.kpme.core.api.util.HrApiConstants;

/**
 * <p>DepartmentContract interface.</p>
 *
 */
public interface DepartmentContract extends HrBusinessObjectContract {

	public static final String CACHE_NAME = HrApiConstants.CacheNamespace.NAMESPACE_PREFIX + "Department";
	
	/**
	 * The Primary Key of a Department entry saved in a database
	 * 
	 * <p>
	 * hrDeptId of Department
	 * <p>
	 * 
	 * @return hrDeptId for Department
	 */
	public String getHrDeptId();
	
	/**
	 * Text field used to identify the Department 
	 * 
	 * <p>
	 * dept of Department
	 * <p>
	 * 
	 * @return dept for Department
	 */
	public String getDept();
	
	/**
	 * Text which describes the department value 
	 * 
	 * <p>
	 * description of Department
	 * <p>
	 * 
	 * @return description for Department
	 */
    public String getDescription();
    
    /**
	 * The name of the Location object associated with this Department
	 * 
	 * <p>
	 * location of Department
	 * <p>
	 * 
	 * @return location for Department
	 */
	public String getLocation();
	 
    /**
	 * Chart value under which the Department is defined
	 * 
	 * <p>
	 * chart of Department
	 * <p>
	 * 
	 * @return chart for Department
	 */
    public String getChart();
    
    /**
	 * Organization value under which the Department is defined
	 * 
	 * <p>
	 * org of Department
	 * <p>
	 * 
	 * @return org for Department
	 */
    public String getOrg();
    
    /**
	 * Location object associated with this Department
	 * 
	 * <p>
	 * locationObj of Department
	 * <p>
	 * 
	 * @return locationObj for Department
	 */
	public LocationContract getLocationObj();
	 
    /**
	 * Chart object under which the Department is defined
	 * 
	 * <p>
	 * chartObj of Department
	 * <p>
	 * 
	 * @return chartObj for Department
	 */
	public ChartContract getChartObj();
	
	/**
	 * Organization under which the Department is defined
	 * 
	 * <p>
	 * org of Department
	 * <p>
	 * 
	 * @return org for Department
	 */
	public OrganizationContract getOrgObj();

	/**
   	 * List of Active principal role approvers for this Department
   	 *
   	 * <p>
   	 * roleMembers of WorkArea
   	 * </p>
   	 * 
   	 * @return roleMembers for WorkArea
   	 */
	public List<? extends DepartmentPrincipalRoleMemberBoContract> getRoleMembers();
	
	/**
   	 * List of Inactive principal role approvers for this Department
   	 *
   	 * <p>
   	 * inactiveRoleMembers of WorkArea
   	 * </p>
   	 * 
   	 * @return inactiveRoleMembers for WorkArea
   	 */
	public List<? extends DepartmentPrincipalRoleMemberBoContract> getInactiveRoleMembers();


    /**
	 * Indicates if this Department needs payroll approval
	 * 
	 * <p>
	 * payrollApproval of Department
	 * </p>
	 * 
	 * @return true if needs payroll approval, false if not
	 */
	public boolean isPayrollApproval();
}
