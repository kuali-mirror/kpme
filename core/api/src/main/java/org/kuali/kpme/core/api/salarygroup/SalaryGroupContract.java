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
package org.kuali.kpme.core.api.salarygroup;

import org.kuali.kpme.core.api.mo.KpmeEffectiveDataTransferObject;
import org.kuali.kpme.core.api.util.HrApiConstants;

import java.math.BigDecimal;

/**
 * <p>SalaryGroupContract interface.</p>
 *
 */
public interface SalaryGroupContract extends KpmeEffectiveDataTransferObject {
	
	public static final String CACHE_NAME = HrApiConstants.CacheNamespace.NAMESPACE_PREFIX + "SalaryGroup";

	/**
	 * The Primary Key of a SalaryGroup entry saved in a database
	 * 
	 * <p>
	 * hrSalGroupId of a SalaryGroup
	 * <p>
	 * 
	 * @return hrSalGroupId for SalaryGroup
	 */
	public String getHrSalGroupId();

	/**
	 * Text field used to identify the salary group
	 * 
	 * <p>
	 * hrSalGroup of a SalaryGroup
	 * <p>
	 * 
	 * @return hrSalGroup for SalaryGroup
	 */
	public String getHrSalGroup();
	
	/**
	 * Description of the salary group
	 * 
	 * <p>
	 * descr of a SalaryGroup
	 * <p>
	 * 
	 * @return descr for SalaryGroup
	 */
	public String getDescr();
	
	/**
	 * The name of the institution the salary group is associated with.
	 * 
	 * <p>
	 * institution of a SalaryGroup
	 * <p>
	 * 
	 * @return institution for SalaryGroup
	 */
	public String getInstitution();
	
	/**
	 * Maximum percentage of time worked for the SalaryGroup. When defining individual positions and jobs, 
	 * the percent time will be less than the Salary Group's Percent Time. Percentage will be entered.  
	 * Uses:  leave accrual, payroll calculations, FTE reporting, etc...
	 * i.e. 40 hours per week is 100% percent time, 20 hours per week is 50% percent time
	 * 
	 * <p>
	 * percentTime of a SalaryGroup
	 * <p>
	 * 
	 * @return percentTime for SalaryGroup
	 */
	public BigDecimal getPercentTime();
	
	/**
	 * Flag indicating if the SalaryGroup is eligible for benefits
	 * 
	 * <p>
	 * benefitsEligible of a SalaryGroup
	 * <p>
	 * 
	 * @return Y if eligible for benefits, N if not
	 */
	public String getBenefitsEligible();

	/**
	 * Flag indicating if the SalaryGroup is eligible for leave benefits
	 * 
	 * <p>
	 * leaveEligible of a SalaryGroup
	 * <p>
	 * 
	 * @return Y if eligible for leave benefits, N if not
	 */
	public String getLeaveEligible();

	/**
	 * The name of the LeavePlan the salary group is associated with.
	 * 
	 * <p>
	 * leavePlan of a SalaryGroup
	 * <p>
	 * 
	 * @return leavePlan for SalaryGroup
	 */
	public String getLeavePlan();

	/**
	 * The name of the Location the salary group is associated with.
	 * 
	 * <p>
	 * location of a SalaryGroup
	 * <p>
	 * 
	 * @return location for SalaryGroup
	 */
	public String getLocation();
}
