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
package org.kuali.kpme.core.api.earncode.security;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.department.DepartmentContract;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.location.LocationContract;
import org.kuali.kpme.core.api.salarygroup.SalaryGroupContract;
import org.kuali.kpme.core.api.util.HrApiConstants;

/**
 * <p>EarnCodeSecurityContract interface</p>
 *
 */
public interface EarnCodeSecurityContract extends HrBusinessObjectContract {

	public static final String CACHE_NAME = HrApiConstants.CacheNamespace.NAMESPACE_PREFIX + "EarnCodeSecurity";
	
	/**
	 * The primary key of an EarnCodeSecurity entry saved in a database
	 * 
	 * <p>
	 * hrEarnCodeSecurityId of an EarnCodeSecurity
	 * <p>
	 * 
	 * @return hrEarnCodeSecurityId for EarnCodeSecurity
	 */
	public String getHrEarnCodeSecurityId();

	/**
	 * Determines if Earn Code should be displayed on Timesheet, Leave Calendar, or Both
	 * 
	 * <p>
	 * earnCodeType of an EarnCodeSecurity
	 * </p>
	 * 
	 * @return earnCodeType for EarnCodeSecurity
	 */
	public String getEarnCodeType();
	
	/**
	 * The SalaryGroup object the EarnCodeSecurity is associated with
	 * 
	 * <p>
	 * If a salary group is defined, only entries associated with a job rcd in this salary group will be subject. 
	 * </p>
	 * 
	 * @return salaryGroupObj for EarnCodeSecurity
	 */
	public SalaryGroupContract getSalaryGroupObj();

	/**
	 * The Department object the EarnCodeSecurity is associated with
	 * 
	 * <p>
	 * If a department is defined, only entries associated with a job record in this department will be subject.  
	 * </p>
	 * 
	 * @return departmentObj for EarnCodeSecurity
	 */
	public DepartmentContract getDepartmentObj();
	
	/**
	 * The flag to indicate employee role will see specific earn codes on Timesheet, Leave Calendar, or Both
	 * 
	 * <p>
	 * employee of EarnCodeSecurity
	 * </p>
	 * 
	 * @return true if emploeye role will see specific earn codes, false if not
	 */
	public boolean isEmployee();

	/**
	 * The flag to indicate approver role will see specific earn codes on Timesheet, Leave Calendar, or Both
	 * 
	 * <p>
	 * approver of EarnCodeSecurity
	 * </p>
	 * 
	 * @return true if approver role will see specific earn codes, false if not
	 */
	public boolean isApprover();

	/**
	 * The flag to indicate payrollProcessor role will see specific earn codes on Timesheet, Leave Calendar, or Both
	 * 
	 * <p>
	 * payrollProcessor of EarnCodeSecurity
	 * </p>
	 * 
	 * @return true if payrollProcessor role will see specific earn codes, false if not
	 */
	public boolean isPayrollProcessor();
	
	/**
	 * The EarnCode object the EarnCodeSecurity is associated with
	 * 
	 * <p>
	 * earnCodeObj of an EarnCodeSecurity
	 * </p>
	 * 
	 * @return earnCodeObj for EarnCodeSecurity
	 */
	public EarnCodeContract getEarnCodeObj();

	/**
	 * The department name the EarnCodeSecurity is associated with
	 * 
	 * <p>
	 * dept of an EarnCodeSecurity
	 * </p>
	 * 
	 * @return dept for EarnCodeSecurity
	 */
	public String getDept();
	
	/**
	 * The hrSalGroup the EarnCodeSecurity is associated with
	 * 
	 * <p>
	 * hrSalGroup of an EarnCodeSecurity
	 * </p>
	 * 
	 * @return hrSalGroup for EarnCodeSecurity
	 */
	public String getHrSalGroup();
	
	/**
	 * The EarnCode name the EarnCodeSecurity is associated with
	 * 
	 * <p>
	 * earnCode of an EarnCodeSecurity
	 * </p>
	 * 
	 * @return earnCode for EarnCodeSecurity
	 */
	public String getEarnCode();
	
	/**
	 * The Job object the EarnCodeSecurity is associated with
	 * 
	 * <p>
	 * jobObj of an EarnCodeSecurity
	 * </p>
	 * 
	 * @return jobObj for EarnCodeSecurity
	 */
	public JobContract getJobObj();
	
	/**
	 * The Location object the EarnCodeSecurity is associated with
	 * 
	 * <p>
	 * If a location is defined, only entries associated with a job rcd in this location will be subject.
	 * </p>
	 * 
	 * @return jobObj for EarnCodeSecurity
	 */
	public LocationContract getLocationObj();
	
	/**
	 * The Location name the EarnCodeSecurity is associated with
	 * 
	 * <p>
	 * location of an EarnCodeSecurity
	 * </p>
	 * 
	 * @return location for EarnCodeSecurity
	 */
	public String getLocation();

}
