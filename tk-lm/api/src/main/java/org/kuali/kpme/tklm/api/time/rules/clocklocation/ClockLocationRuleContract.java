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
package org.kuali.kpme.tklm.api.time.rules.clocklocation;

import java.util.List;

import org.kuali.kpme.core.api.department.DepartmentContract;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.workarea.WorkAreaContract;
import org.kuali.kpme.tklm.api.time.rules.TkRuleContract;
import org.kuali.rice.kim.api.identity.Person;

/**
 * <p>ClockLocationRuleContract interface</p>
 *
 */
public interface ClockLocationRuleContract extends TkRuleContract {
	
	/**
	 * The work area associated with the ClockLocationRule
	 * 
	 * <p>
	 * If a work area is defined, only entries associated with a job in this work area will be subject to IP address validation.
	 * <p>
	 * 
	 * @return workArea for ClockLocationRule
	 */
	public Long getWorkArea();
	
	/**
	 * The principalId associated with the ClockLocationRule
	 * 
	 * <p>
	 * If a principal id is defined, only this specific employee will be subject to IP address validation.
	 * <p>
	 * 
	 * @return principalId for ClockLocationRule
	 */
	public String getPrincipalId();
	
	/**
	 * TODO: Is this field needed??
	 * The user principal id associated with the ClockLocationRule
	 * 
	 * <p>
	 * userPrincipalId of a ClockLocationRule
	 * <p>
	 * 
	 * @return userPrincipalId for ClockLocationRule
	 */
	public String getUserPrincipalId();

	/**
	 * The jobNumber associated with the ClockLocationRule
	 * 
	 * <p>
	 * If job # is defined, only this specific employees job will be subject to IP address validation.
	 * <p>
	 * 
	 * @return jobNumber for ClockLocationRule
	 */
	public Long getJobNumber();
	
	/**
	 * The Department object associated with the ClockLocationRule
	 * 
	 * <p>
	 * If a department is defined, only entries associated with a job in this department will be subject to IP address validation.
	 * <p>
	 * 
	 * @return department for ClockLocationRule
	 */
	public DepartmentContract getDepartment();
	
	/**
	 * The WorkArea object associated with the ClockLocationRule
	 * 
	 * <p>
	 * If a work area is defined, only entries associated with a job in this work area will be subject to IP address validation.
	 * <p>
	 * 
	 * @return workAreaObj for ClockLocationRule
	 */
	public WorkAreaContract getWorkAreaObj();

	/**
	 * The Job object associated with the ClockLocationRule
	 * 
	 * <p>
	 * If job # is defined, only this specific employees job will be subject to IP address validation.
	 * <p>
	 * 
	 * @return job for ClockLocationRule
	 */
	public JobContract getJob();

	/**
	 * The primary key of a ClockLocationRule entry saved in a database
	 * 
	 * <p>
	 * tkClockLocationRuleId of a ClockLocationRule
	 * <p>
	 * 
	 * @return tkClockLocationRuleId for ClockLocationRule
	 */
	public String getTkClockLocationRuleId();

	/**
	 * The department associated with the ClockLocationRule
	 * 
	 * <p>
	 * If a department is defined, only entries associated with a job in this department will be subject to IP address validation.
	 * <p>
	 * 
	 * @return dept for ClockLocationRule
	 */
	public String getDept();
	
	/**
	 * The history flag of the ClockLocationRule
	 * 
	 * <p>
	 * history flag of a ClockLocationRule
	 * <p>
	 * 
	 * @return Y if on, N if not
	 */
	public Boolean getHistory();

	/**
	 * The Person object associated with the ClockLocationRule
	 * 
	 * <p>
	 * If a principal id is defined, only this specific employee will be subject to IP address validation.
	 * <p>
	 * 
	 * @return principal for ClockLocationRule
	 */
	public Person getPrincipal();

	/**
	 * The hrDeptId associated with the ClockLocationRule
	 * 
	 * <p>
	 * hrDeptId of a ClockLocationRule
	 * <p>
	 * 
	 * @return hrDeptId for ClockLocationRule
	 */
	public String getHrDeptId();
	
	/**
	 * The tkWorkAreaId associated with the ClockLocationRule
	 * 
	 * <p>
	 * tkWorkAreaId of a ClockLocationRule
	 * <p>
	 * 
	 * @return tkWorkAreaId for ClockLocationRule
	 */
	public String getTkWorkAreaId();

	/**
	 * The hrJobId associated with the ClockLocationRule
	 * 
	 * <p>
	 * hrJobId of a ClockLocationRule
	 * <p>
	 * 
	 * @return hrJobId for ClockLocationRule
	 */
	public String getHrJobId();
	
	/**
	 * The list of ClockLocationRuleIpAddress objects associated with the ClockLocationRule
	 * 
	 * <p>
	 * ipAddresses of a ClockLocationRule
	 * <p>
	 * 
	 * @return ipAddresses for ClockLocationRule
	 */
	public List<? extends ClockLocationRuleIpAddressContract> getIpAddresses();

	/**
	 * The IP Address to be used for validation
	 * 
	 * <p>
	 * for lookup and inquiry display only
	 * <p>
	 * 
	 * @return ip addresses string for ClockLocationRule
	 */
	public String getIpAddressesString();

}
