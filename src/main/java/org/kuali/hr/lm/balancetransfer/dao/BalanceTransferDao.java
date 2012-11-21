package org.kuali.hr.lm.balancetransfer.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.lm.balancetransfer.BalanceTransfer;

public interface BalanceTransferDao {

	public List<BalanceTransfer> getAllBalanceTransfersForPrincipalId(String principalId);
	public List<BalanceTransfer> getAllBalanceTransferForPrincipalIdAsOfDate(String principalId, Date effectiveDate);
	public List<BalanceTransfer> getAllBalanceTransferByEffectiveDate(Date effectiveDate);
	public List<BalanceTransfer> getAllBalanceTransfersMarkedPayoutForPrincipalId(String principalId);
	public List<BalanceTransfer> getAllBalanceTransfersForAccrualCategoryRuleByDate(String accrualRuleId, Date asOfDate);
	public BalanceTransfer getBalanceTransferById(String balanceTransferId);
	
}
