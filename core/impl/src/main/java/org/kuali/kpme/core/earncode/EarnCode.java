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
package org.kuali.kpme.core.earncode;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.earncode.security.EarnCodeSecurity;
import org.kuali.kpme.core.leaveplan.LeavePlan;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class EarnCode extends HrBusinessObject implements EarnCodeContract {

	private static final String EARN_CODE = "earnCode";

	private static final long serialVersionUID = -1470603919624794932L;
	
	public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "EarnCode";
    public static final ImmutableList<String> CACHE_FLUSH = new ImmutableList.Builder<String>()
            .add(EarnCodeSecurity.CACHE_NAME)
            .add(EarnCode.CACHE_NAME)
            .build();
    //KPME-2273/1965 Primary Business Keys List.
    public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(EARN_CODE)
            .build();

	
	private String hrEarnCodeId;
	private String earnCode;
	private String description;
	
    private Boolean ovtEarnCode;
	private String accrualCategory;
	private BigDecimal inflateMinHours;
	private BigDecimal inflateFactor;

	private boolean history;

	private AccrualCategory accrualCategoryObj;
	private EarnCode rollupToEarnCodeObj;
	private LeavePlan leavePlanObj;
	
	private String leavePlan;
	private String accrualBalanceAction;
	private String fractionalTimeAllowed;
	private String roundingOption;
	private String eligibleForAccrual;
	private String affectPay;
	private String allowScheduledLeave;
	private String fmla;
	private String workmansComp;
	private Long defaultAmountofTime;
	private String allowNegativeAccrualBalance;
	private String rollupToEarnCode;
	private String recordMethod;
	private String usageLimit;
	private String countsAsRegularPay;
	
	
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.put(EARN_CODE, this.getEarnCode())		
				.build();
	}

	public String getCountsAsRegularPay() {
		return countsAsRegularPay;
	}

	public void setCountsAsRegularPay(String countsAsRegularPay) {
		this.countsAsRegularPay = countsAsRegularPay;
	}

	public String getUsageLimit() {
		return usageLimit;
	}

	public void setUsageLimit(String usageLimit) {
		this.usageLimit = usageLimit;
	}

	public String getRecordMethod() {
		return recordMethod;
	}

	public void setRecordMethod(String recordMethod) {
		this.recordMethod = recordMethod;
	}

	public String getRollupToEarnCode() {
		return rollupToEarnCode;
	}

	public void setRollupToEarnCode(String rollupToEarnCode) {
		this.rollupToEarnCode = rollupToEarnCode;
	}

	public EarnCode getRollupToEarnCodeObj() {
		return rollupToEarnCodeObj;
	}

	public void setRollupToEarnCodeObj(EarnCode rollupToEarnCodeObj) {
		this.rollupToEarnCodeObj = rollupToEarnCodeObj;
	}

	public String getLeavePlan() {
//		AccrualCategory myAccrualCategoryObj = new AccrualCategory();
//		if(this.accrualCategory != null) {
//			myAccrualCategoryObj =  HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory, getEffectiveDate());
//	    }
//		this.leavePlan =(myAccrualCategoryObj != null) ? myAccrualCategoryObj.getLeavePlan() : ""; 
	    return leavePlan;
	}

	public void setLeavePlan(String leavePlan) {
		this.leavePlan = leavePlan;
	}

	public String getAccrualBalanceAction() {
		return accrualBalanceAction;
	}

	public void setAccrualBalanceAction(String accrualBalanceAction) {
		this.accrualBalanceAction = accrualBalanceAction;
	}

	public String getFractionalTimeAllowed() {
		return fractionalTimeAllowed;
	}

	public void setFractionalTimeAllowed(String fractionalTimeAllowed) {
		this.fractionalTimeAllowed = fractionalTimeAllowed;
	}

	public String getRoundingOption() {
		return roundingOption;
	}

	public void setRoundingOption(String roundingOption) {
		this.roundingOption = roundingOption;
	}

	public String getEligibleForAccrual() {
		return eligibleForAccrual;
	}

	public void setEligibleForAccrual(String eligibleForAccrual) {
		this.eligibleForAccrual = eligibleForAccrual;
	}

	public String getAffectPay() {
		return affectPay;
	}

	public void setAffectPay(String affectPay) {
		this.affectPay = affectPay;
	}

	public String getAllowScheduledLeave() {
		return allowScheduledLeave;
	}

	public void setAllowScheduledLeave(String allowScheduledLeave) {
		this.allowScheduledLeave = allowScheduledLeave;
	}

	public String getFmla() {
		return fmla;
	}

	public void setFmla(String fmla) {
		this.fmla = fmla;
	}

	public String getWorkmansComp() {
		return workmansComp;
	}

	public void setWorkmansComp(String workmansComp) {
		this.workmansComp = workmansComp;
	}

	public Long getDefaultAmountofTime() {
		return defaultAmountofTime;
	}

	public void setDefaultAmountofTime(Long defaultAmountofTime) {
		this.defaultAmountofTime = defaultAmountofTime;
	}

	public String getAllowNegativeAccrualBalance() {
		return allowNegativeAccrualBalance;
	}

	public void setAllowNegativeAccrualBalance(String allowNegativeAccrualBalance) {
		this.allowNegativeAccrualBalance = allowNegativeAccrualBalance;
	}


	public String getEarnCode() {
		return earnCode;
	}

	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}

	public String getHrEarnCodeId() {
		return hrEarnCodeId;
	}

	public void setHrEarnCodeId(String hrEarnCodeId) {
		this.hrEarnCodeId = hrEarnCodeId;
	}

	public String getAccrualCategory() {
		return accrualCategory;
	}

	public void setAccrualCategory(String accrualCategory) {
		this.accrualCategory = accrualCategory;
	}

	public AccrualCategory getAccrualCategoryObj() {
		if(accrualCategoryObj == null && !this.getAccrualCategory().isEmpty()) {
			this.assingAccrualCategoryObj();
		}
		return accrualCategoryObj;
	}
	public void assingAccrualCategoryObj() {
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("accrualCategory", getAccrualCategory());
		Collection<AccrualCategory> c = KRADServiceLocator.getBusinessObjectService().findMatching(AccrualCategory.class, parameters);
		if(!c.isEmpty()) {
			this.setAccrualCategoryObj((AccrualCategory)c.toArray()[0]);
		}
	}

	public void setAccrualCategoryObj(AccrualCategory accrualCategoryObj) {
		this.accrualCategoryObj = accrualCategoryObj;
	}

	public BigDecimal getInflateMinHours() {
		return inflateMinHours;
	}

	public void setInflateMinHours(BigDecimal inflateMinHours) {
		this.inflateMinHours = inflateMinHours;
	}

	public BigDecimal getInflateFactor() {
		return inflateFactor;
	}

	public void setInflateFactor(BigDecimal inflateFactor) {
		this.inflateFactor = inflateFactor;
	}

    public Boolean getOvtEarnCode() {
        return ovtEarnCode;
    }

    public void setOvtEarnCode(Boolean ovtEarnCode) {
        this.ovtEarnCode = ovtEarnCode;
    }

    /**
	 * This is used by the timeblock json object.
	 * The purpose of this function is to create a string based on the record_* fields which can be used to render hour / begin(end) time input box
	 * @return String fieldType
	 */
	public String getEarnCodeType() {
//		if(getRecordHours()) {
//			return HrConstants.EARN_CODE_HOUR;
//		}
//		else if(getRecordTime()) {
//			return HrConstants.EARN_CODE_TIME;
//		}
//		else if(getRecordAmount()) {
//			return HrConstants.EARN_CODE_AMOUNT;
//		}
//		else {
//			return "";
//		}
		return this.recordMethod;
	}

	@Override
	public String getUniqueKey() {
		return earnCode;
	}

	@Override
	public String getId() {
		return getHrEarnCodeId();
	}

	@Override
	public void setId(String id) {
		setHrEarnCodeId(id);
	}
	
    public String getEarnCodeKeyForDisplay() {
//    	String unitTime = null;
//    	AccrualCategory acObj = null;
//    	if(this.accrualCategory != null) {
//    		acObj = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory, this.effectiveDate);
//    	}
//    	unitTime = (acObj!= null ? acObj.getUnitOfTime() : this.recordMethod) ;
//        return hrEarnCodeId + ":" + unitTime;
    	return hrEarnCodeId;
    }
    
    public String getEarnCodeValueForDisplay() {
        return earnCode + " : " + description;
    }

	public LeavePlan getLeavePlanObj() {
		return leavePlanObj;
	}

	public void setLeavePlanObj(LeavePlan leavePlanObj) {
		this.leavePlanObj = leavePlanObj;
	}    
    
}
