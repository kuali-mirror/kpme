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
package org.kuali.kpme.core.accrualcategory.rule;

import java.math.BigDecimal;

import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.earncode.EarnCode;


public class AccrualCategoryRule extends HrBusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lmAccrualCategoryRuleId;

	private String serviceUnitOfTime;
	private Long start;
	private Long end;
	private BigDecimal accrualRate;
	private BigDecimal maxBalance;
	private String maxBalFlag;
	private String maxBalanceActionFrequency;
	private String actionAtMaxBalance;
	private String maxBalanceTransferToAccrualCategory;
	private BigDecimal maxBalanceTransferConversionFactor;
	private Long maxTransferAmount;
	private Long maxPayoutAmount;
	private String maxPayoutEarnCode;
	private Long maxUsage;
	private Long maxCarryOver;
	private String lmAccrualCategoryId;
	
	private EarnCode earnCodeObj;

	public EarnCode getEarnCodeObj() {
		return earnCodeObj;
	}

	public void setEarnCodeObj(EarnCode earnCodeObj) {
		this.earnCodeObj = earnCodeObj;
	}
	
	private AccrualCategory accrualCategoryObj;
	
	public AccrualCategory getAccrualCategoryObj() {
		return accrualCategoryObj;
	}

	public void setAccrualCategoryObj(AccrualCategory accrualCategoryObj) {
		this.accrualCategoryObj = accrualCategoryObj;
	}

	public String getLmAccrualCategoryRuleId() {
		return lmAccrualCategoryRuleId;
	}

	public void setLmAccrualCategoryRuleId(String lmAccrualCategoryRuleId) {
		this.lmAccrualCategoryRuleId = lmAccrualCategoryRuleId;
	}

	public String getServiceUnitOfTime() {
		return serviceUnitOfTime;
	}

	public void setServiceUnitOfTime(String serviceUnitOfTime) {
		this.serviceUnitOfTime = serviceUnitOfTime;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long end) {
		this.end = end;
	}

	public BigDecimal getAccrualRate() {
		return accrualRate;
	}

	public void setAccrualRate(BigDecimal accrualRate) {
		this.accrualRate = accrualRate;
	}

	public BigDecimal getMaxBalance() {
		return maxBalance;
	}

	public void setMaxBalance(BigDecimal maxBalance) {
		this.maxBalance = maxBalance;
	}

	public String getMaxBalFlag() {
		return maxBalFlag;
	}

	public void setMaxBalFlag(String maxBalFlag) {
		this.maxBalFlag = maxBalFlag;
	}

	public String getMaxBalanceActionFrequency() {
		return maxBalanceActionFrequency;
	}

	public void setMaxBalanceActionFrequency(String maxBalanceActionFrequency) {
		this.maxBalanceActionFrequency = maxBalanceActionFrequency;
	}

	public String getActionAtMaxBalance() {
		return actionAtMaxBalance;
	}

	public void setActionAtMaxBalance(String actionAtMaxBalance) {
		this.actionAtMaxBalance = actionAtMaxBalance;
	}

	public String getMaxBalanceTransferToAccrualCategory() {
		return maxBalanceTransferToAccrualCategory;
	}

	public void setMaxBalanceTransferToAccrualCategory(
			String maxBalanceTransferToAccrualCategory) {
		this.maxBalanceTransferToAccrualCategory = maxBalanceTransferToAccrualCategory;
	}

	public BigDecimal getMaxBalanceTransferConversionFactor() {
		return maxBalanceTransferConversionFactor;
	}

	public void setMaxBalanceTransferConversionFactor(
			BigDecimal maxBalanceTransferConversionFactor) {
		this.maxBalanceTransferConversionFactor = maxBalanceTransferConversionFactor;
	}

	public Long getMaxTransferAmount() {
		return maxTransferAmount;
	}

	public void setMaxTransferAmount(Long maxTransferAmount) {
		this.maxTransferAmount = maxTransferAmount;
	}

	public Long getMaxPayoutAmount() {
		return maxPayoutAmount;
	}

	public void setMaxPayoutAmount(Long maxPayoutAmount) {
		this.maxPayoutAmount = maxPayoutAmount;
	}

	public String getMaxPayoutEarnCode() {
		return maxPayoutEarnCode;
	}

	public void setMaxPayoutEarnCode(String maxPayoutEarnCode) {
		this.maxPayoutEarnCode = maxPayoutEarnCode;
	}

	public Long getMaxUsage() {
		return maxUsage;
	}

	public void setMaxUsage(Long maxUsage) {
		this.maxUsage = maxUsage;
	}

	public Long getMaxCarryOver() {
		return maxCarryOver;
	}

	public void setMaxCarryOver(Long maxCarryOver) {
		this.maxCarryOver = maxCarryOver;
	}

	public String getLmAccrualCategoryId() {
		return lmAccrualCategoryId;
	}

	public void setLmAccrualCategoryId(String lmAccrualCategoryId) {
		this.lmAccrualCategoryId = lmAccrualCategoryId;
	}

	@Override
	protected String getUniqueKey() {
		return accrualRate.toString();
	}

	@Override
	public String getId() {
		return getLmAccrualCategoryRuleId();
	}

	@Override
	public void setId(String id) {
		setLmAccrualCategoryRuleId(id);
		
	}

}
