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
package org.kuali.kpme.tklm.api.leave.override;

import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryContract;
import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributesContract;
import org.kuali.rice.kim.api.identity.Person;

/**
 * <p>EmployeeOverrideContract interface</p>
 *
 */
public interface EmployeeOverrideContract extends HrBusinessObjectContract {
	
	/**
	 * The primary key of an EmployeeOverride entry saved in a database
	 * 
	 * <p>
	 * lmEmployeeOverrideId of an EmployeeOverride
	 * <p>
	 * 
	 * @return lmEmployeeOverrideId for EmployeeOverride
	 */
	public String getLmEmployeeOverrideId();

	/**
	 * The principalID of the employee whom the system overrides the AccrtualCategory limits for
	 * 
	 * <p>
	 * principalId of an EmployeeOverride
	 * </p>
	 * 
	 * @return principalId for EmployeeOverride
	 */
	public String getPrincipalId();	
	
	/**
	 * The principalName of the employee whom the system overrides the AccrtualCategory limits for
	 * 
	 * <p>
	 * principal.getName() of an EmployeeOverride
	 * <p>
	 * 
	 * @return principal.getName() for EmployeeOverride
	 */
	public String getName();

	/**
	 * The AccuralCategory object associated with the EmployeeOverride
	 * 
	 * <p>
	 * accrualCategoryObj of an EmployeeOverride
	 * <p>
	 * 
	 * @return accrualCategoryObj for EmployeeOverride
	 */	
	public AccrualCategoryContract getAccrualCategoryObj();

	/**
	 * The AccrualCategory limit used for the Employee
	 * 
	 * <p>
	 * overrideType of an EmployeeOverride
	 * </p>
	 * 
	 * @return overrideType for EmployeeOverride
	 */
	public String getOverrideType();
	
	/**
	 * The descripton of a reason for the EmployeeOverride
	 * 
	 * <p>
	 * description of an EmployeeOverride
	 * </p>
	 * 
	 * @return description for EmployeeOverride
	 */
	public String getDescription();
	
	/**
	 * The AccrualCategory name associated with the EmployeeOverride
	 * 
	 * <p>
	 * accrualCategory of an EmployeeOverride
	 * <p>
	 * 
	 * @return accrualCategory for EmployeeOverride
	 */
	public String getAccrualCategory();
	
	/**
	 * The LeavePlan name associated with the Principal ID and AccrualCategory
	 * 
	 * <p>
	 * leavePlan of an EmployeeOverride
	 * <p>
	 * 
	 * @return leavePlan for EmployeeOverride
	 */
	public String getLeavePlan();	

	/**
	 * The new limit value to be used for the AccrualCategory
	 * 
	 * <p>
	 * overrideValue of an EmployeeOverride
	 * <p>
	 * 
	 * @return overrideValue for EmployeeOverride
	 */
	public Long getOverrideValue();
	
	/**
	 * The Person object associated with the EmployeeOverride
	 * 
	 * <p>
	 * principal of an EmployeeOverride
	 * <p>
	 * 
	 * @return principal for EmployeeOverride
	 */	
	public Person getPrincipal();	

	/**
	 * The PrincipalHRAttributes object associated with the EmployeeOverride
	 * 
	 * <p>
	 * principalHRAttrObj of an EmployeeOverride
	 * <p>
	 * 
	 * @return principalHRAttrObj for EmployeeOverride
	 */	
	public PrincipalHRAttributesContract getPrincipalHRAttrObj();	

}
