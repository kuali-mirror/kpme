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
package org.kuali.hr.lm.accrual;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.kuali.hr.core.KPMEConstants;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.earncode.EarnCode;

public class AccrualCategory extends HrBusinessObject {
    public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "AccrualCategory";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lmAccrualCategoryId;
	private String leavePlan;
	private String accrualCategory;
	private String descr;
	private String accrualEarnInterval;
	private String proration;
	private String donation;
	private String showOnGrid;
	private String unitOfTime;
	private Boolean history;
	private String hasRules;

	private LeavePlan leavePlanObj;
	private List<AccrualCategoryRule> accrualCategoryRules = new LinkedList<AccrualCategoryRule>();

	private BigDecimal minPercentWorked;
	
	// KPME-1347 Kagata
	private String earnCode;
	private EarnCode earnCodeObj;
	

	public String getHasRules() {
		return hasRules;
	}

	public void setHasRules(String hasRules) {
		this.hasRules = hasRules;
	}
	
	public BigDecimal getMinPercentWorked() {
		return minPercentWorked;
	}

	public void setMinPercentWorked(BigDecimal minPercentWorked) {
		this.minPercentWorked = minPercentWorked;
	}

	public LeavePlan getLeavePlanObj() {
		return leavePlanObj;
	}

	public void setLeavePlanObj(LeavePlan leavePlanObj) {
		this.leavePlanObj = leavePlanObj;
	}

	public List<AccrualCategoryRule> getAccrualCategoryRules() {
		return accrualCategoryRules;
	}

	public void setAccrualCategoryRules(
			List<AccrualCategoryRule> accrualCategoryRules) {
		this.accrualCategoryRules = accrualCategoryRules;
	}

	public String getLmAccrualCategoryId() {
		return lmAccrualCategoryId;
	}

	public void setLmAccrualCategoryId(String lmAccrualCategoryId) {
		this.lmAccrualCategoryId = lmAccrualCategoryId;
	}

	public String getLeavePlan() {
		return leavePlan;
	}

	public void setLeavePlan(String leavePlan) {
		this.leavePlan = leavePlan;
	}

	public String getAccrualCategory() {
		return accrualCategory;
	}

	public void setAccrualCategory(String accrualCategory) {
		this.accrualCategory = accrualCategory;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getAccrualEarnInterval() {
		return accrualEarnInterval;
	}

	public void setAccrualEarnInterval(String accrualEarnInterval) {
		this.accrualEarnInterval = accrualEarnInterval;
	}

	public String getProration() {
		return proration;
	}

	public void setProration(String proration) {
		this.proration = proration;
	}

	public String getDonation() {
		return donation;
	}

	public void setDonation(String donation) {
		this.donation = donation;
	}

	public String getShowOnGrid() {
		return showOnGrid;
	}

	public void setShowOnGrid(String showOnGrid) {
		this.showOnGrid = showOnGrid;
	}

	public String getUnitOfTime() {
		return unitOfTime;
	}

	public void setUnitOfTime(String unitOfTime) {
		this.unitOfTime = unitOfTime;
	}

	@Override
	protected String getUniqueKey() {
		return lmAccrualCategoryId;
	}

	public Boolean getHistory() {
		return history;
	}

	public void setHistory(Boolean history) {
		this.history = history;
	}

	public String getEarnCode() {
		return earnCode;
	}

	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}

	public EarnCode getEarnCodeObj() {
		return earnCodeObj;
	}

	public void setEarnCodeObj(EarnCode earnCodeObj) {
		this.earnCodeObj = earnCodeObj;
	}

	@Override
	public void setId(String id) {
		setLmAccrualCategoryId(id);
	}

	@Override
	public String getId() {
		return getLmAccrualCategoryId();
	}
	
}
