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
package org.kuali.kpme.core.api.accrualcategory.rule;

import java.math.BigDecimal;

import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryContract;
import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.mo.Effective;
import org.kuali.kpme.core.api.mo.UserModified;
import org.kuali.kpme.core.api.util.HrApiConstants;
import org.kuali.rice.core.api.mo.common.GloballyUnique;
import org.kuali.rice.core.api.mo.common.Identifiable;
import org.kuali.rice.core.api.mo.common.Versioned;
import org.kuali.rice.core.api.mo.common.active.Inactivatable;

/**
 * <p>AccrualCategoryRuleContract interface.</p>
 *
 */
public interface AccrualCategoryRuleContract extends Versioned, GloballyUnique, Inactivatable, Identifiable, Effective, UserModified {
	
	public static final String CACHE_NAME = HrApiConstants.CacheNamespace.NAMESPACE_PREFIX + "AccrualCategoryRule";
	
	/**
	 * The earnCodeObject associated with an AccrualCategoryRule
	 * 
	 * <p>
	 * earnCodeObject of an AccrualCategoryRule
	 * <p>
	 * 
	 * @return earnCodeObject for AccrualCategoryRule
	 */
	//public EarnCodeContract getEarnCodeObj();
	
	/**
	 *  The accrualCategoryObject associated with an AccrualCategoryRule
	 * 
	 * <p>
	 * accrualCategoryObj of an AccrualCategoryRule
	 * <p>
	 * 
	 * @return accrualCategoryObj for AccrualCategoryRule
	 */
	//public AccrualCategoryContract getAccrualCategoryObj();
	
	/**
	 * The Primary Key of an AccrualCategoryRule entry saved in a database
	 * 
	 * <p>
	 * lmAccrualCategoryRuleId of an AccrualCategoryRule
	 * <p>
	 * 
	 * @return lmAccrualCategoryRuleId for AccrualCategoryRule
	 */
	public String getLmAccrualCategoryRuleId();

	/**
	 * The Unit of time for measuring service start and end amounts for an AccrualCategoryRule
	 * 
	 * <p>
	 * serviceUnitOfTime of an AccrualCategoryRule
	 * <p>
	 * 
	 * @return serviceUnitOfTime for AccrualCategoryRule
	 */
	public String getServiceUnitOfTime();

	/**
	 * The service milestone an AccrualCategoryRule begins on
	 * 
	 * <p>
	 * start milestone of an AccrualCategoryRule
	 * <p>
	 * 
	 * @return start for AccrualCategoryRule
	 */
	public Long getStart();

	/**
	 * The service milestone an AccrualCategoryRule ends on
	 * 
	 * <p>
	 * end milestone of an AccrualCategoryRule
	 * <p>
	 * 
	 * @return end for AccrualCategoryRule
	 */
	public Long getEnd();

	/**
	 * The accrual rate of an AccrualCategoryRule
	 * 
	 * <p>
	 * accrualRate of an AccrualCategoryRule
	 * <p>
	 * 
	 * @return accrualRate for AccrualCategoryRule
	 */
	public BigDecimal getAccrualRate();

	/**
	 * The maximum amount that can be accrued with an AccrualCategoryRule
	 * 
	 * <p>
	 * maxBalance of an AccrualCategoryRule
	 * <p>
	 * 
	 * @return maxBalance for AccrualCategoryRule
	 */
	public BigDecimal getMaxBalance();

	/**
	 * The maximum balance flag of an AccrualCategoryRule. If on, then there's a maxBalance defined for the rule
	 * 
	 * <p>
	 * maxBalance flag of an AccrualCategoryRule
	 * <p>
	 * 
	 * @return maxBalFlag for AccrualCategoryRule
	 */
	public String getMaxBalFlag();	
	
	/**
	 * The Action Frequency indicates when system checks to see if max balance is reached then performs the Action at Max Balance 
	 * 
	 * <p>
	 * maxBalanceActionFrequency of an AccrualCategoryRule
	 * <p>
	 * 
	 * @return maxBalanceActionFrequency for AccrualCategoryRule
	 */
	public String getMaxBalanceActionFrequency();
	
	/**
	 * The Action taken with the leave accrued that is over the Max Balance amount 
	 * 
	 * <p>
	 * actionAtMaxBalance of an AccrualCategoryRule
	 * <p>
	 * 
	 * @return actionAtMaxBalance for AccrualCategoryRule
	 */
	public String getActionAtMaxBalance();

	/**
	 * The AccrualCategory the over amount should be transferred to if accrued is over the maxBalance
	 * and "Transfer" is selected for actionAtMaxBalance
	 * 
	 * <p>
	 * maxBalanceTransferToAccrualCategory of an AccrualCategoryRule
	 * <p>
	 * 
	 * @return maxBalanceTransferToAccrualCategory for AccrualCategoryRule
	 */	
	public String getMaxBalanceTransferToAccrualCategory();

	/**
	 * The  conversion factor for the accrued leave being transferred if "Transfer" is selected for actionAtMaxBalance
	 * 
	 * <p>
	 * maxBalanceTransferConversionFactor of an AccrualCategoryRule
	 * <p>
	 * 
	 * @return maxBalanceTransferConversionFactor for AccrualCategoryRule
	 */	
	public BigDecimal getMaxBalanceTransferConversionFactor();

	/**
	 * The maximum amount of leave that can be transfered if "Transfer" is selected for actionAtMaxBalance
	 * 
	 * <p>
	 * maxTransferAmount of an AccrualCategoryRule
	 * <p>
	 * 
	 * @return maxTransferAmount for AccrualCategoryRule
	 */	
	public Long getMaxTransferAmount();
	
	/**
	 * The maximum amount of leave that can be paid out if "payout" is selected for actionAtMaxBalance
	 * 
	 * <p>
	 * maxPayoutAmount of an AccrualCategoryRule
	 * <p>
	 * 
	 * @return maxPayoutAmount for AccrualCategoryRule
	 */	
	public Long getMaxPayoutAmount();

	/**
	 * The EarnCode that the pay out should be transferred to that can be paid out if "payout"
	 * is selected for actionAtMaxBalance
	 * 
	 * <p>
	 * maxPayoutEarnCode of an AccrualCategoryRule
	 * <p>
	 * 
	 * @return maxPayoutEarnCode for AccrualCategoryRule
	 */	
	public String getMaxPayoutEarnCode();

	/**
	 *  The maximum amount of leave the can be used over each year for an AccrualCategoryRule
	 * 
	 * <p>
	 * masUsage of an AccrualCategoryRule
	 * <p>
	 * 
	 * @return masUsage for AccrualCategoryRule
	 */	
	public Long getMaxUsage();

	/**
	 *  The maximum amount of leave the can be carried over each year for an AccrualCategoryRule
	 * 
	 * <p>
	 * maxCarryOver of an AccrualCategoryRule
	 * <p>
	 * 
	 * @return maxCarryOver for AccrualCategoryRule
	 */	
	public Long getMaxCarryOver();

	/**
	 * The Primary Key of an AccrualCategoryRule entry saved in a database
	 * 
	 * <p>
	 * lmAccrualCategoryId of an AccrualCategoryRule
	 * <p>
	 * 
	 * @return lmAccrualCategoryId for AccrualCategoryRule
	 */	
	public String getLmAccrualCategoryId();

}
