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
package org.kuali.kpme.tklm.api.leave.payout;

import java.math.BigDecimal;
import java.util.List;

import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryContract;
import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributesContract;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlockContract;
import org.kuali.rice.kim.api.identity.Person;

/**
 * <p>LeaveDonationContract interface</p>
 *
 */
public interface LeavePayoutContract extends HrBusinessObjectContract {
	
	/**
	 * The EarnCode name associated with the LeavePayout
	 * 
	 * <p>
	 * earnCode of a LeavePayout
	 * </p>
	 * 
	 * @return earnCode for LeavePayout
	 */
	public String getEarnCode();
	
	/**
	 * The EarnCode object associated with the LeavePayout
	 * 
	 * <p>
	 * earnCodeObject of a LeavePayout
	 * <p>
	 * 
	 * @return earnCodeObject for LeavePayout
	 */
	public EarnCodeContract getEarnCodeObj();
	
	/**
	 * The principalID of the employee whom accruals are payed out to
	 * 
	 * <p>
	 * principalId of a LeavePayout
	 * </p>
	 * 
	 * @return principalId for LeavePayout
	 */
	public String getPrincipalId();
	
	/**
	 * The Person object associated with the LeavePayout
	 * 
	 * <p>
	 * principal of a LeavePayout
	 * <p>
	 * 
	 * @return principal for LeavePayout
	 */	
    public Person getPrincipal();
    
	/**
	 * The principalName of the employee
	 * 
	 * <p>
	 * principal.getName() of a LeavePayout
	 * <p>
	 * 
	 * @return principal.getName() for LeavePayout
	 */
	public String getName();
	
	/**
	 * The LeavePlan name of the PrincipalHRAttributes of the employee
	 * 
	 * <p>
	 * principalHRAttrObj.getLeavePlan() of a LeavePayout
	 * <p>
	 * 
	 * @return principalHRAttrObj.getLeavePlan() for LeavePayout
	 */
	public String getLeavePlan();

	/**
	 * The AccrualCategory name that leave payout is made from
	 * 
	 * <p>
	 * fromAccrualCategory of a LeavePayout
	 * </p>
	 * 
	 * @return fromAccrualCategory for LeavePayout
	 */
	public String getFromAccrualCategory();
	
	/**
	 * The descripton of the LeavePayout
	 * 
	 * <p>
	 * description of a LeavePayout
	 * </p>
	 * 
	 * @return description for LeavePayout
	 */
	public String getDescription();
	
	/**
	 * The amount of accrued leave to payout
	 * 
	 * <p>
	 * payoutAmount of a LeavePayout
	 * </p>
	 * 
	 * @return payoutAmount for LeavePayout
	 */
	public BigDecimal getPayoutAmount();
	
	/**
	 * The computed amount of accrued leave that will be forfeited
	 * 
	 * <p>
	 * forfeitedAmount of a LeavePayout
	 * </p>
	 * 
	 * @return forfeitedAmount for LeavePayout
	 */
    public BigDecimal getForfeitedAmount();
    
    /**
	 * The AccuralCategory object associated with the LeavePayout
	 * 
	 * <p>
	 * accrualCategoryObj of a LeavePayout
	 * <p>
	 * 
	 * @return accrualCategoryObj for LeavePayout
	 */	
	public AccrualCategoryContract getFromAccrualCategoryObj();

	/**
	 * The primary key of a LeavePayout entry saved in a database
	 * 
	 * <p>
	 * hrEarnCodeId of a LeavePayout
	 * <p>
	 * 
	 * @return lmLeavePlanId for LeavePayout
	 */
	public String getLmLeavePayoutId();
	
	/**
	 * The PrincipalHRAttributes object associated with the LeavePayout
	 * 
	 * <p>
	 * principalHRAttrObj of a LeavePayout
	 * <p>
	 * 
	 * @return principalHRAttrObj for LeavePayout
	 */	
	public PrincipalHRAttributesContract getPrincipalHRAttrObj();
	
	/**
	 * The AccrualCategoryRule name associated with the LeavePayout
	 * 
	 * <p>
	 * accrualCategoryRule of a LeavePayout
	 * <p>
	 * 
	 * @return accrualCategoryRule for LeavePayout
	 */
	public String getAccrualCategoryRule();
	
	/**
	 * The forfeitedLeaveBlockId associated with the LeavePayout
	 * 
	 * <p>
	 * forfeitedLeaveBlockId of a LeavePayout
	 * <p>
	 * 
	 * @return forfeitedLeaveBlockId for LeavePayout
	 */
	public String getForfeitedLeaveBlockId();
	
	/**
	 * The payoutFromLeaveBlockId associated with the LeavePayout
	 * 
	 * <p>
	 * payoutFromLeaveBlockId of a LeavePayout
	 * <p>
	 * 
	 * @return payoutFromLeaveBlockId for LeavePayout
	 */
	public String getPayoutLeaveBlockId();
	
	/**
	 * The payoutLeaveBlockId associated with the LeavePayout
	 * 
	 * <p>
	 * payoutLeaveBlockId of a LeavePayout
	 * <p>
	 * 
	 * @return payoutLeaveBlockId for LeavePayout
	 */
	public String getPayoutFromLeaveBlockId();
	
	/**
	 * The list of LeaveBlock objects associated with the LeavePayout
	 * 
	 * <p>
	 * a list of LeaveBlock objects
	 * <p>
	 * 
	 * @return a list that contains LeaveBlock objects based on forfeitedLeaveBlockId, payoutLeaveBlockId, and payoutFromLeaveBlockId
	 */
    public List<? extends LeaveBlockContract> getLeaveBlocks();

	/**
	 * The leaveCalendarDocumentId associated with the LeavePayout
	 * 
	 * <p>
	 * leaveCalendarDocumentId of a LeavePayout
	 * <p>
	 * 
	 * @return leaveCalendarDocumentId for LeavePayout
	 */
    public String getLeaveCalendarDocumentId();
	
    /**
	 * The status associated with the LeavePayout
	 * 
	 * <p>
	 * status of a LeavePayout
	 * <p>
	 * 
	 * @return status for LeavePayout
	 */
	public String getStatus();
	
	/**
	 * The documentHeaderId associated with the LeavePayout
	 * 
	 * <p>
	 * documentHeaderId of a LeavePayout
	 * <p>
	 * 
	 * @return documentHeaderId for LeavePayout
	 */
	public String getDocumentHeaderId();

}
