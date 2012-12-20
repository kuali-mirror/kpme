/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.lm.balancetransfer;

import java.math.BigDecimal;
import java.sql.Date;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kim.api.identity.Person;

public class BalanceTransfer extends HrBusinessObject {

	private static final long serialVersionUID = 6948695780968441016L;
	
    //public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "BalanceTransfer";

	private String balanceTransferId;
	private String accrualCategoryRule;
	private String principalId;
	private String toAccrualCategory;
	private String fromAccrualCategory;
	private BigDecimal transferAmount;
	private BigDecimal forfeitedAmount;
	private Date effectiveDate;
	private String leaveCalendarDocumentId;
	
	private Person principal;
	private AccrualCategory creditedAccrualCategory;
	private AccrualCategory debitedAccrualCategory;
	private boolean transferRuleOverride;

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public String getToAccrualCategory() {
		return toAccrualCategory;
	}

	public void setToAccrualCategory(String toAccrualCategory) {
		this.toAccrualCategory = toAccrualCategory;
	}

	public String getFromAccrualCategory() {
		return fromAccrualCategory;
	}

	public void setFromAccrualCategory(String fromAccrualCategory) {
		this.fromAccrualCategory = fromAccrualCategory;
	}

	public BigDecimal getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}

	public BigDecimal getForfeitedAmount() {
		return forfeitedAmount;
	}

	public void setForfeitedAmount(BigDecimal forfeitedAmount) {
		this.forfeitedAmount = forfeitedAmount;
	}
	
	public String getBalanceTransferId() {
		return balanceTransferId;
	}

	public void setBalanceTransferId(String balanceTransferId) {
		this.balanceTransferId = balanceTransferId;
	}
	
	public String getAccrualCategoryRule() {
		return accrualCategoryRule;
	}

	public void setAccrualCategoryRule(String accrualCategoryRule) {
		this.accrualCategoryRule = accrualCategoryRule;
	}

	@Override
	protected String getUniqueKey() {
		return balanceTransferId;
	}

	@Override
	public String getId() {
		return getBalanceTransferId();
	}

	@Override
	public void setId(String id) {
		setBalanceTransferId(id);
	}

	public Person getPrincipal() {
		return principal;
	}

	public void setPrincipal(Person principal) {
		this.principal = principal;
	}

	public AccrualCategory getCreditedAccrualCategory() {
		return TkServiceLocator.getAccrualCategoryService().getAccrualCategory(toAccrualCategory, effectiveDate);
	}

	public void setCreditedAccrualCategory(AccrualCategory creditedAccrualCategory) {
		this.creditedAccrualCategory = creditedAccrualCategory;
	}

	public AccrualCategory getDebitedAccrualCategory() {
		return TkServiceLocator.getAccrualCategoryService().getAccrualCategory(fromAccrualCategory, effectiveDate);
	}

	public void setDebitedAccrualCategory(AccrualCategory debitedAccrualCategory) {
		this.debitedAccrualCategory = debitedAccrualCategory;
	}

	public boolean isTransferRuleOverride() {
		return transferRuleOverride;
	}

	public void setTransferRuleOverride(boolean transferRuleOverride) {
		this.transferRuleOverride = transferRuleOverride;
	}

	public String getLeaveCalendarDocumentId() {
		return leaveCalendarDocumentId;
	}

	public void setLeaveCalendarDocumentId(String leaveCalendarDocumentId) {
		this.leaveCalendarDocumentId = leaveCalendarDocumentId;
	}
	
	//Comparable for order handling of more than one transfer occurring during the same
	//action frequency interval?
	
}
