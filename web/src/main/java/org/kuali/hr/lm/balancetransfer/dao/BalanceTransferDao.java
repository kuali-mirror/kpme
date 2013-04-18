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
package org.kuali.hr.lm.balancetransfer.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.hr.lm.balancetransfer.BalanceTransfer;

public interface BalanceTransferDao {

	public List<BalanceTransfer> getAllBalanceTransfersForPrincipalId(String principalId);
	public List<BalanceTransfer> getAllBalanceTransferForPrincipalIdAsOfDate(String principalId, LocalDate effectiveDate);
	public List<BalanceTransfer> getAllBalanceTransferByEffectiveDate(LocalDate effectiveDate);
	public List<BalanceTransfer> getAllBalanceTransfersMarkedPayoutForPrincipalId(String principalId);
	public List<BalanceTransfer> getAllBalanceTransfersForAccrualCategoryRuleByDate(String accrualRuleId, LocalDate asOfDate);
	public BalanceTransfer getBalanceTransferById(String balanceTransferId);
	public List<BalanceTransfer> getBalanceTransfers(String viewPrincipal,
			LocalDate beginPeriodDate, LocalDate endPeriodDate);
	public void saveOrUpdate(BalanceTransfer balanceTransfer);
    public List<BalanceTransfer> getBalanceTransfers(String principalId, String fromAccrualCategory, String transferAmount, String toAccrualCategory, String amountTransferred, String forfeitedAmount, LocalDate fromEffdt, LocalDate toEffdt);
	
}
