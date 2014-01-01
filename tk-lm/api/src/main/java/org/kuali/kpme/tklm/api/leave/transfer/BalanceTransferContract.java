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
package org.kuali.kpme.tklm.api.leave.transfer;

import java.math.BigDecimal;
import java.util.List;

import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryContract;
import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlockContract;
import org.kuali.rice.kim.api.identity.Person;

/**
 * <p>BalanceTransferContract interface.</p>
 *
 */
public interface BalanceTransferContract extends HrBusinessObjectContract {

	/**
	 * The principalId of the user associated with the BalanceTransfer
	 * 
	 * <p>
	 * principalId of a BalanceTransfer
	 * <p>
	 * 
	 * @return principalId for BalanceTransfer
	 */
	public String getPrincipalId();

	/**
	 * The AccrualCategory name that the leave is transfered to
	 * 
	 * <p>
	 * toAccrualCategory of a BalanceTransfer
	 * <p>
	 * 
	 * @return toAccrualCategory for BalanceTransfer
	 */
	public String getToAccrualCategory();
	
	/**
	 * The AccrualCategory name that the leave is transfered from
	 * 
	 * <p>
	 * fromAccrualCategory of a BalanceTransfer
	 * <p>
	 * 
	 * @return fromAccrualCategory for BalanceTransfer
	 */
	public String getFromAccrualCategory();
	
	/**
	 * The amount of accrued leave to transfer 
	 * 
	 * <p>
	 * transferAmount of a BalanceTransfer
	 * <p>
	 * 
	 * @return transferAmount for BalanceTransfer
	 */
	public BigDecimal getTransferAmount();

	/**
	 * The computed amount of accrued leave that will be forfeited 
	 * 
	 * <p>
	 * forfeitedAmount of a BalanceTransfer
	 * <p>
	 * 
	 * @return forfeitedAmount for BalanceTransfer
	 */
	public BigDecimal getForfeitedAmount();
	
	/**
	 * The primary key of a BalanceTransfer entry saved in a database
	 * 
	 * <p>
	 * balanceTransferId of a BalanceTransfer
	 * <p>
	 * 
	 * @return balanceTransferId for BalanceTransfer
	 */
	public String getBalanceTransferId();

	/**
	 * The AccrualCategoryRule name associated with the BalanceTransfer
	 * 
	 * <p>
	 * accrualCategoryRule of a BalanceTransfer
	 * <p>
	 * 
	 * @return accrualCategoryRule for BalanceTransfer
	 */
	public String getAccrualCategoryRule();

	/**
	 * The Person object of the user associated with the BalanceTransfer
	 * 
	 * <p>
	 * principal of a BalanceTransfer
	 * <p>
	 * 
	 * @return principal for BalanceTransfer
	 */
	public Person getPrincipal();
	
	/**
	 * The AccrualCategory object that the leave is transfered to
	 * 
	 * <p>
	 * AccrualCategory object based on toAccrualCategory
	 * <p>
	 * 
	 * @return AccrualCategory object based on toAccrualCategory
	 */
	public AccrualCategoryContract getCreditedAccrualCategory();

	/**
	 * The AccrualCategory object that the leave is transfered from
	 * 
	 * <p>
	 * AccrualCategory object based on fromAccrualCategory
	 * <p>
	 * 
	 * @return AccrualCategory object based on fromAccrualCategory
	 */
	public AccrualCategoryContract getDebitedAccrualCategory();

	/**
	 * The leave calendar document id associated with the BalanceTransfer
	 * 
	 * <p>
	 * leaveCalendarDocumentId of a BalanceTransfer
	 * <p>
	 * 
	 * @return leaveCalendarDocumentId for BalanceTransfer
	 */
	public String getLeaveCalendarDocumentId();

	/**
	 * The list of LeaveBlock objects associated with the BalanceTransfer
	 * 
	 * <p>
	 * The list contains LeaveBlock objects based on forfeitedLeaveBlockId, accruedLeaveBlockId, and debitedLeaveBlockId
	 * <p>
	 * 
	 * @return  a list of LeaveBlock objects for BalanceTransfer
	 */
	public List<? extends LeaveBlockContract> getLeaveBlocks();

	/**
	 * The status associated with the BalanceTransfer
	 * 
	 * <p>
	 * status of a BalanceTransfer
	 * <p>
	 * 
	 * @return status for BalanceTransfer
	 */
	public String getStatus();

	/**
	 * The accrued leave block id associated with the BalanceTransfer
	 * 
	 * <p>
	 * accruedLeaveBlockId of a BalanceTransfer
	 * <p>
	 * 
	 * @return accruedLeaveBlockId for BalanceTransfer
	 */
	public String getAccruedLeaveBlockId();
	
	/**
	 * The forfeited leave block id associated with the BalanceTransfer
	 * 
	 * <p>
	 * forfeitedLeaveBlockId of a BalanceTransfer
	 * <p>
	 * 
	 * @return forfeitedLeaveBlockId for BalanceTransfer
	 */
	public String getForfeitedLeaveBlockId();

	/**
	 * The debited leave block id associated with the BalanceTransfer
	 * 
	 * <p>
	 * debitedLeaveBlockId of a BalanceTransfer
	 * <p>
	 * 
	 * @return debitedLeaveBlockId for BalanceTransfer
	 */
	public String getDebitedLeaveBlockId();

	/**
	 * The amount transfered
	 * 
	 * <p>
	 * amountTransferred of a BalanceTransfer
	 * <p>
	 * 
	 * @return amountTransferred for BalanceTransfer
	 */
	public BigDecimal getAmountTransferred();

	/**
	 * TODO: Put a better comment
	 * 
	 * <p>
	 * sstoId of a BalanceTransfer
	 * <p>
	 * 
	 * @return sstoId for BalanceTransfer
	 */
	public String getSstoId();

	/**
	 * The document header id associated with the BalanceTransfer
	 * 
	 * <p>
	 * documentHeaderId of a BalanceTransfer
	 * <p>
	 * 
	 * @return documentHeaderId for BalanceTransfer
	 */
	public String getDocumentHeaderId();

}
