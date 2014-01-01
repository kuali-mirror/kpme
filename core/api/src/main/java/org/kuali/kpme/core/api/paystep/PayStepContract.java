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
package org.kuali.kpme.core.api.paystep;

import java.math.BigDecimal;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;

/**
 * <p>PayStepContract interface</p>
 *
 */
public interface PayStepContract extends HrBusinessObjectContract {

	/**
	 * The text field used to identify the PayStep
	 * 
	 * <p>
	 * payStep of a PayStep 
	 * </p>
	 * 
	 * @return payStep for PayStep
	 */
	public String getPayStep();
	
	/**
	 * The institution the PayStep is associated with
	 * 
	 * <p>
	 * institution of a PayStep 
	 * </p>
	 * 
	 * @return institution for PayStep
	 */
	public String getInstitution();
	
	/**
	 * The location the PayStep is associated with
	 * 
	 * <p>
	 * location of a PayStep 
	 * </p>
	 * 
	 * @return location for PayStep
	 */
	public String getLocation();
	
	/**
	 * The salary group the PayStep is associated with
	 * 
	 * <p>
	 * salaryGroup of a PayStep 
	 * </p>
	 * 
	 * @return salaryGroup for PayStep
	 */
	public String getSalaryGroup();

	/**
	 * The pay group the PayStep is associated with
	 * 
	 * <p>
	 * payGrade of a PayStep 
	 * </p>
	 * 
	 * @return payGrade for PayStep
	 */
	public String getPayGrade();
	
	/**
	 * The order that the steps are applied
	 * 
	 * <p>
	 * stepNumber of a PayStep 
	 * </p>
	 * 
	 * @return stepNumber for PayStep
	 */
	public int getStepNumber();
	
	/**
	 * The compensation rate that will be applied to the position's pay rate
	 * 
	 * <p>
	 * compRate of a PayStep 
	 * </p>
	 * 
	 * @return compRate for PayStep
	 */
	public BigDecimal getCompRate();

	/**
	 * The amount of time from position incumbent's service date to when the step is applied
	 * 
	 * <p>
	 * serviceAmount of a PayStep 
	 * </p>
	 * 
	 * @return serviceAmount for PayStep
	 */
	public int getServiceAmount();
	
	/**
	 * The unit of measurement for time to apply the step
	 * 
	 * <p>
	 * serviceUnit of a PayStep 
	 * </p>
	 * 
	 * @return serviceUnit for PayStep
	 */
	public String getServiceUnit();
	
	/**
	 * The primary key of a PayStep entry saved in a database
	 * 
	 * <p>
	 * pmPayStepId of a PayStep
	 * <p>
	 * 
	 * @return pmPayStepId for PayStep
	 */
	public String getPmPayStepId();	

}
