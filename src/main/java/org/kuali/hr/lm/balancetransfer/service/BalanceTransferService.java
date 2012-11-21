package org.kuali.hr.lm.balancetransfer.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.lm.leavedonation.LeaveDonation;
import org.springframework.cache.annotation.Cacheable;

public interface BalanceTransferService {

	public List<BalanceTransfer> getAllBalanceTransfersForPrincipalId(String principalId);
	public List<BalanceTransfer> getAllBalanceTransferForPrincipalIdAsOfDate(String principalId, Date effectiveDate);
	public List<BalanceTransfer> getAllBalanceTransferByEffectiveDate(Date effectiveDate);

	//@Cacheable(value= LeaveDonation.CACHE_NAME, key="'balanceTransferId=' + #p0")
	public BalanceTransfer getBalanceTransferById(String balanceTransferId);
	
}
