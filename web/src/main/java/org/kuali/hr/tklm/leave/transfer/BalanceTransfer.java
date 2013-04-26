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
package org.kuali.hr.tklm.leave.transfer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.core.HrBusinessObject;
import org.kuali.hr.core.HrConstants;
import org.kuali.hr.core.accrualcategory.AccrualCategory;
import org.kuali.hr.core.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.leave.service.base.LmServiceLocator;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.util.ObjectUtils;

public class BalanceTransfer extends HrBusinessObject {

	private static final long serialVersionUID = 6948695780968441016L;
	
	private String balanceTransferId;
	private String documentHeaderId;
	private String accrualCategoryRule;
	private String principalId;
	private String toAccrualCategory;
	private String fromAccrualCategory;
	private BigDecimal transferAmount;
	private BigDecimal amountTransferred;
	private BigDecimal forfeitedAmount;
	private String leaveCalendarDocumentId;

	private String status;
	private String forfeitedLeaveBlockId;
	private String accruedLeaveBlockId;
	private String debitedLeaveBlockId;
	private String sstoId;
	
	private transient Person principal;
	
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
		return HrServiceLocator.getAccrualCategoryService().getAccrualCategory(toAccrualCategory, getEffectiveLocalDate());
	}

	public AccrualCategory getDebitedAccrualCategory() {
		return HrServiceLocator.getAccrualCategoryService().getAccrualCategory(fromAccrualCategory, getEffectiveLocalDate());
	}

	public String getLeaveCalendarDocumentId() {
		return leaveCalendarDocumentId;
	}

	public void setLeaveCalendarDocumentId(String leaveCalendarDocumentId) {
		this.leaveCalendarDocumentId = leaveCalendarDocumentId;
	}

	/**
	 * Returns a balance transfer object adjusted for the new transfer amount.
	 * 
	 * "this" must be a default initialized balance transfer. i.e. transfer amount plus forfeited amount
	 * equal to the amount of leave in excess of the from accrual category's max balance for the given principal.
	 * 
	 * calling this method without first validating the supplied transfer amount via BalanceTransferValidationUtils may produce undesired results.
	 *
	 * @param transferAmount The desired transfer amount
	 * @return A balance transfer object with forfeited and amount transfer amounts adjusted to transferAmount
	 */
	public BalanceTransfer adjust(BigDecimal transferAmount) {
		BigDecimal difference = this.transferAmount.subtract(transferAmount);
		AccrualCategoryRule aRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualCategoryRule);
		//technically if there is forfeiture, then the transfer amount has already been maximized
		//via BalanceTransferService::initializeTransfer(...)
		//i.o.w. transfer amount cannot be increased.
		//this method is written with the intention of eventually allowing end user to adjust the transfer
		//amount as many times as they wish before submitting. Currently they cannot.
		if(difference.signum() < 0) {
			//transfer amount is being increased.
			if(forfeitedAmount.compareTo(BigDecimal.ZERO) > 0) {
				//transfer amount has already been maximized.
				if(forfeitedAmount.compareTo(difference.abs()) >= 0)
					// there is enough leave in the forfeited amount to take out the difference.
					forfeitedAmount = forfeitedAmount.subtract(difference.abs());
				else
					// the difference zero's the forfeited amount.
					forfeitedAmount = BigDecimal.ZERO;
			}
			// a forfeited amount equal to zero with an increase in the transfer amount
			// does not produce forfeiture.
			// forfeiture cannot be negative.
		}
		else if (difference.signum() > 0) {
			//transfer amount is being decreased
			forfeitedAmount = forfeitedAmount.add(difference);
		}

		this.transferAmount = transferAmount;

		if(ObjectUtils.isNotNull(aRule.getMaxBalanceTransferConversionFactor()))
			this.amountTransferred = transferAmount.multiply(aRule.getMaxBalanceTransferConversionFactor()).setScale(2);
		else
			this.amountTransferred = transferAmount;
		
		return this;
	}

	public List<LeaveBlock> getLeaveBlocks() {
		List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
		if (getForfeitedLeaveBlockId() != null) {
		    leaveBlocks.add(LmServiceLocator.getLeaveBlockService().getLeaveBlock(forfeitedLeaveBlockId));
        }
        if (getAccruedLeaveBlockId() != null) {
		    leaveBlocks.add(LmServiceLocator.getLeaveBlockService().getLeaveBlock(accruedLeaveBlockId));
        }
        if (getDebitedLeaveBlockId() != null) {
		    leaveBlocks.add(LmServiceLocator.getLeaveBlockService().getLeaveBlock(debitedLeaveBlockId));
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
		LmServiceLocator.getLeaveBlockService().updateLeaveBlock(null, principalId);
		setStatus(HrConstants.ROUTE_STATUS.DISAPPROVED);
	}

	public void approve() {

		setStatus(HrConstants.ROUTE_STATUS.FINAL);
	}

	public void cancel() {

		setStatus(HrConstants.ROUTE_STATUS.CANCEL);
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
	
	public String getSstoId() {
		return sstoId;
	}

	public void setSstoId(String sstoId) {
		this.sstoId = sstoId;
	}

	public String getDocumentHeaderId() {
		return documentHeaderId;
	}

	public void setDocumentHeaderId(String documentHeaderId) {
		this.documentHeaderId = documentHeaderId;
	}
	
	//Comparable for order handling of more than one transfer occurring during the same
	//action frequency interval?
	
}
