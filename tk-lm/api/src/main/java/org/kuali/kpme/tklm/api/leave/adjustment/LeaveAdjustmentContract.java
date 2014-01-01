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
package org.kuali.kpme.tklm.api.leave.adjustment;

import java.math.BigDecimal;

import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryContract;
import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributesContract;
import org.kuali.rice.kim.api.identity.Person;

/**
 * <p>LeaveAdjustmentContract interface</p>
 *
 */
public interface LeaveAdjustmentContract extends HrBusinessObjectContract {
	
	/**
	 * The EarnCode name associated with the AccrualCategory and LeaveAdjustment
	 * 
	 * <p>
	 * earnCode of a LeaveAdjustment
	 * <p>
	 * 
	 * @return earnCode for LeaveAdjustment
	 */
	public String getEarnCode();
	
	/**
	 * The EarnCode object associated with the AccrualCategory and the LeaveAdjustment
	 * 
	 * <p>
	 * earnCodeObject of a LeaveAdjustment
	 * <p>
	 * 
	 * @return earnCodeObject for LeaveAdjustment
	 */
	public EarnCodeContract getEarnCodeObj();
	
	/**
	 * The identifier of the employee associated with the LeaveAdjustment
	 * 
	 * <p>
	 * principalId of a LeaveAdjustment
	 * </p>
	 * 
	 * @return principalId for LeaveAdjustment
	 */
	public String getPrincipalId();
	
	/**
	 * The Person object associated with the LeaveAdjustment
	 * 
	 * <p>
	 * principal of a LeaveAdjustment
	 * <p>
	 * 
	 * @return principal for LeaveAdjustment
	 */	
    public Person getPrincipal();
   
	/**
	 * The principalName of the employee associated with the LeaveAdjustment
	 * 
	 * <p>
	 * principal.getName() of a LeaveAdjustment
	 * <p>
	 * 
	 * @return principal.getName() for LeaveAdjustment
	 */
    public String getName();
	
    /**
	 * The LeavePlan name associated with the Principal Id and the AccrualCategory
	 * 
	 * <p>
	 * leavePlan of a LeaveAdjustment
	 * <p>
	 * 
	 * @return leavePlan for LeaveAdjustment
	 */
	public String getLeavePlan();

	/**
	 * The AccrualCategory name associated with the LeaveAdjustment
	 * 
	 * <p>
	 * accrualCategory of a LeaveAdjustment
	 * <p>
	 * 
	 * @return accrualCategory for LeaveAdjustment
	 */
	public String getAccrualCategory();
	
	/**
	 * The descripton of a reason associated with the LeaveAdjustment
	 * 
	 * <p>
	 * description of a LeaveAdjustment
	 * </p>
	 * 
	 * @return description for LeaveAdjustment
	 */
	public String getDescription();
	
	/**
	 * The value of the LeaveAdjustment, positive or negative, associated with the LeaveAdjustment
	 * 
	 * <p>
	 * adjustmentAmount of a LeaveAdjustment
	 * </p>
	 * 
	 * @return adjustmentAmount for LeaveAdjustment
	 */
	public BigDecimal getAdjustmentAmount();
	
	/**
	 * The AccuralCategory object associated with the LeaveAdjustment
	 * 
	 * <p>
	 * accrualCategoryObj of a LeaveAdjustment
	 * <p>
	 * 
	 * @return accrualCategoryObj for LeaveAdjustment
	 */	
	public AccrualCategoryContract getAccrualCategoryObj();
	
	/**
	 * The primary key of a LeaveAdjustment entry saved in a database
	 * 
	 * <p>
	 * lmLeaveAdjustmentId of a LeaveAdjustment
	 * <p>
	 * 
	 * @return lmLeaveAdjustmentId for LeaveAdjustment
	 */
	public String getLmLeaveAdjustmentId();
	
	/**
	 * The PrincipalHRAttributes object associated with the LeaveAdjustment
	 * 
	 * <p>
	 * principalHRAttrObj of a LeaveAdjustment
	 * <p>
	 * 
	 * @return principalHRAttrObj for LeaveAdjustment
	 */	
	public PrincipalHRAttributesContract getPrincipalHRAttrObj();
	
}
