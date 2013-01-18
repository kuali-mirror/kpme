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
package org.kuali.hr.lm.balancetransfer;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kim.api.identity.Person;

public class BalanceTransfer extends HrBusinessObject {

	private static final long serialVersionUID = 6948695780968441016L;
	
    //public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "BalanceTransfer";

	private String balanceTransferId;
	private String documentHeaderId;
	private String accrualCategoryRule;
	private String principalId;
	private String toAccrualCategory;
	private String fromAccrualCategory;
	private BigDecimal transferAmount;
	private BigDecimal amountTransferred;
	private BigDecimal forfeitedAmount;
	private Date effectiveDate;
	//private String leaveCalendarDocumentId;

	private String status;
	private String forfeitedLeaveBlockId;
	private String accruedLeaveBlockId;
	private String debitedLeaveBlockId;
	
	private Person principal;

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

	public AccrualCategory getDebitedAccrualCategory() {
		return TkServiceLocator.getAccrualCategoryService().getAccrualCategory(fromAccrualCategory, effectiveDate);
	}

	/*public String getLeaveCalendarDocumentId() {
		return leaveCalendarDocumentId;
	}

	public void setLeaveCalendarDocumentId(String leaveCalendarDocumentId) {
		this.leaveCalendarDocumentId = leaveCalendarDocumentId;
	}*/

	/**
	 * Returns a balance transfer object adjusted for the new transfer amount.
	 *
	 * @param balanceTransfer
	 * @return
	 */
	public BalanceTransfer adjust(BigDecimal transferAmount) {
		BigDecimal difference = this.transferAmount.subtract(transferAmount);
		//if difference is negative, transfer amount was increased. forfeiture, if applicable, will be reduced.
		if(difference.compareTo(BigDecimal.ZERO) < 0) {
			//reduce forfeiture if necessary.
			if(forfeitedAmount.compareTo(BigDecimal.ZERO) > 0) {
				if(forfeitedAmount.compareTo(difference.abs()) >= 0)
					forfeitedAmount = forfeitedAmount.subtract(difference.abs());
				else
					forfeitedAmount = BigDecimal.ZERO;
			}
		}
		else 
			forfeitedAmount = forfeitedAmount.add(difference);

		this.transferAmount = transferAmount;
		return this;
	}

	public List<LeaveBlock> getLeaveBlocks() {
		List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
		if (getForfeitedLeaveBlockId() != null) {
		    leaveBlocks.add(TkServiceLocator.getLeaveBlockService().getLeaveBlock(forfeitedLeaveBlockId));
        }
        if (getAccruedLeaveBlockId() != null) {
		    leaveBlocks.add(TkServiceLocator.getLeaveBlockService().getLeaveBlock(accruedLeaveBlockId));
        }
        if (getDebitedLeaveBlockId() != null) {
		    leaveBlocks.add(TkServiceLocator.getLeaveBlockService().getLeaveBlock(debitedLeaveBlockId));
        }

		return leaveBlocks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void disapprove() {
		TkServiceLocator.getLeaveBlockService().updateLeaveBlock(null, principalId);
		setStatus(TkConstants.ROUTE_STATUS.DISAPPROVED);
	}

	public void approve() {

		setStatus(TkConstants.ROUTE_STATUS.FINAL);
	}

	public void cancel() {

		setStatus(TkConstants.ROUTE_STATUS.CANCEL);
	}

	public String getAccruedLeaveBlockId() {
		return accruedLeaveBlockId;
	}

	public void setAccruedLeaveBlockId(String accruedLeaveBlockId) {
		this.accruedLeaveBlockId = accruedLeaveBlockId;
	}

	public String getForfeitedLeaveBlockId() {
		return forfeitedLeaveBlockId;
	}

	public void setForfeitedLeaveBlockId(String forfeitedLeaveBlockId) {
		this.forfeitedLeaveBlockId = forfeitedLeaveBlockId;
	}

	public String getDebitedLeaveBlockId() {
		return debitedLeaveBlockId;
	}

	public void setDebitedLeaveBlockId(String debitedLeaveBlockId) {
		this.debitedLeaveBlockId = debitedLeaveBlockId;
	}

	public BigDecimal getAmountTransferred() {
		return amountTransferred;
	}

	public void setAmountTransferred(BigDecimal amountTransferrerd) {
		this.amountTransferred = amountTransferrerd;
	}
	
	//Comparable for order handling of more than one transfer occurring during the same
	//action frequency interval?
	
}
