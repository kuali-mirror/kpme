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
package org.kuali.kpme.core.api.earncode;

import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryContract;
import org.kuali.kpme.core.api.mo.KpmeEffectiveDataTransferObject;
import org.kuali.kpme.core.api.util.HrApiConstants;

import java.math.BigDecimal;

/**
 * <p>EarnCodeContract interface.</p>
 *
 */
public interface EarnCodeContract extends KpmeEffectiveDataTransferObject {
	public static final String CACHE_NAME = HrApiConstants.CacheNamespace.NAMESPACE_PREFIX + "EarnCode";
	/**
	 * The flag that indicates if the worked hours under an EarnCode should be counted as regular pay
	 * Use this field to calculate the "Worked Hours in the Time Summary.
	 * 
	 * <p>
	 * countsAsRegularPay flag of EarnCode
	 * </p>
	 * 
	 * @return Y if has rules, N if not
	 */
	public String getCountsAsRegularPay();
	
	/**
	 * The flag that indicates if usage should be included or excluded toward the usage limit 
	 * of the AccrualCategory associated with an EarnCode
	 * 
	 * <p>
	 * countsAsRegularPay flag of EarnCode
	 * </p>
	 * 
	 * @return I if Include, E if Exclude
	 */
	public String getUsageLimit();

	/**
	 * The flag that determines the value type that will be entered for the earn code 
	 * 
	 * <p>
	 * recordMethod of EarnCode
	 * </p>
	 * 
	 * @return H for Hours, T for Time, D for Days, A for Amount
	 */
	public String getRecordMethod();
	
	/**
	 * The earn code name of the EarnCode object that this EarnCode rolls up to
	 * 
	 * <p>
	 * rollupToEarnCode of EarnCode
	 * </p>
	 * 
	 * @return rollupToEarnCode for EarnCode
	 */
	public String getRollupToEarnCode();

	/**
	 * The EarnCode Object that this EarnCode rolls up to
	 * 
	 * <p>
	 * rollupToEarnCodeObj of EarnCode
	 * </p>
	 * 
	 * @return rollupToEarnCodeObj for EarnCode
	 */
	public EarnCodeContract getRollupToEarnCodeObj();

	/**
	 * The name of the LeavePlan that this EarnCode associates with
	 * 
	 * <p>
	 * leavePlan of EarnCode
	 * </p>
	 * 
	 * @return leavePlan for EarnCode
	 */
	public String getLeavePlan();

	/**
	 * The action that can be taken for the balance of the AccrualCategory of an EarnCode
	 * 
	 * <p>
	 * accrualBalanceAction of EarnCode
	 * </p>
	 * 
	 * @return N for None, U for usage, A for Adjustment
	 */
	public String getAccrualBalanceAction();

	/**
	 * Defines fractional unit of time used for reporting leave 
	 * 
	 * <p>
	 * fractionalTimeAllowed of EarnCode
	 * </p>
	 * 
	 * @return fractionalTimeAllowed for EarnCode
	 */
	public String getFractionalTimeAllowed() ;

	/**
	 * Indicated the rounding option when calculating leave accruals and reporting.
	 * 
	 * <p>
	 * roundingOption of EarnCode
	 * </p>
	 * 
	 * @return T for Traditional, R for Truncate
	 */
	public String getRoundingOption();

	/**
	 * Flag indicating this type of leave is eligible for accrual or not
	 * 
	 * <p>
	 * eligibleForAccrual of EarnCode
	 * </p>
	 * 
	 * @return Y for Yes, N for No
	 */
	public String getEligibleForAccrual();

	/**
	 * Flag indicating use of this leave code will affect the employee's pay
	 * 
	 * <p>
	 * affectPay of EarnCode
	 * </p>
	 * 
	 * @return Y for Yes, N for No
	 */
	public String getAffectPay();

	/**
	 * Flag indicating if scheduling of leave on calendar is allowed
	 * 
	 * <p>
	 * allowScheduledLeave of EarnCode
	 * </p>
	 * 
	 * @return Y for Yes, N for No
	 */
	public String getAllowScheduledLeave() ;

	/**
	 * FLMA Leave Code indicator. If Principal Calendar is flagged for FMLA, 
	 * employee will have option to select this Leave Code.
	 * 
	 * <p>
	 * fmla flag of EarnCode
	 * </p>
	 * 
	 * @return Y for Yes, N for No
	 */
	public String getFmla();

	/**
	 * Workman's Comp Leave Code indicator.  If Principal Calendar is flagged for Workman’s Comp, 
	 * employee will have option to select this Leave Code.
	 * 
	 * <p>
	 * workmansComp flag of EarnCode
	 * </p>
	 * 
	 * @return Y for Yes, N for No
	 */
	public String getWorkmansComp();

	/**
	 * When a user selects leave code the specified amount of time will appear in the amount of leave taken.
	 * 
	 * <p>
	 * defaultAmountofTime of EarnCode
	 * </p>
	 * 
	 * @return defaultAmountofTime for EarnCode
	 */
	public Long getDefaultAmountofTime();

	/**
	 * Flag that allows usage to take the balance of the Accrual Category into the negative.
	 * 
	 * <p>
	 * allowNegativeAccrualBalance flag of EarnCode
	 * </p>
	 * 
	 * @return Y for Yes, N for No
	 */
	public String getAllowNegativeAccrualBalance();

	/**
	 * The Alpha/Numeric code used to identify an EarnCode
	 * 
	 * <p>
	 * earnCode of EarnCode
	 * </p>
	 * 
	 * @return earnCode for EarnCode
	 */
	public String getEarnCode();

	/**
	 * The description of an EarnCode
	 * 
	 * <p>
	 * description of EarnCode
	 * </p>
	 * 
	 * @return description for EarnCode
	 */
	public String getDescription() ;

	/**
	 * The Primary Key of an EarnCode entry saved in a database
	 * 
	 * <p>
	 * hrEarnCodeId of an EarnCode
	 * <p>
	 * 
	 * @return hrEarnCodeId for EarnCode
	 */
	public String getHrEarnCodeId();

	/**
	 * The AccrualCategory name of the AccuralCategoryObject associated with an EarnCode
	 * 
	 * <p>
	 * accrualCategory of an EarnCode
	 * <p>
	 * 
	 * @return accrualCategory for EarnCode
	 */	
	public String getAccrualCategory() ;

	/**
	 * The AccuralCategoryObject associated with an EarnCode
	 * 
	 * <p>
	 * accrualCategoryObj of an EarnCode
	 * <p>
	 * 
	 * @return accrualCategoryObj for EarnCode
	 */	
	public AccrualCategoryContract getAccrualCategoryObj();

	/**
	 * Hours incurred will be inflated to this minimum hours value
	 * 
	 * <p>
	 * inflateMinHours of an EarnCode
	 * <p>
	 * 
	 * @return inflateMinHours for EarnCode
	 */	
	public BigDecimal getInflateMinHours();
	
	/**
	 * 	The hours incurred will be multiplied by this factor
	 * 
	 * <p>
	 * InflateFactor of an EarnCode
	 * <p>
	 * 
	 * @return InflateFactor for EarnCode
	 */	
	public BigDecimal getInflateFactor() ;

	/**
	 * 	Indicates this EarnCode may be used for overtime
	 * 
	 * <p>
	 * ovtEarnCode flag of an EarnCode
	 * <p>
	 * 
	 * @return Y for Yes, N for No
	 */	
    public boolean isOvtEarnCode() ;

    /**
	 * 	The LeavePlan object associated with an EarnCode
	 * 
	 * <p>
	 * leavePlanObj of an EarnCode
	 * <p>
	 * 
	 * @return leavePlanObj for EarnCode
	 */	
	//public LeavePlanContract getLeavePlanObj();
	
	/**
	 * Indicates the record method time, hour or amount
	 * 
	 * <p>
	 * recordmethod of an EarnCode
	 * <p>
	 * @return
	 */
	public String getEarnCodeType();
}
