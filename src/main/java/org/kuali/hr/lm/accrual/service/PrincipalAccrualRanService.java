package org.kuali.hr.lm.accrual.service;

import org.kuali.hr.lm.accrual.PrincipalAccrualRan;

public interface PrincipalAccrualRanService {
	
	public PrincipalAccrualRan getLastPrincipalAccrualRan(String principalId);
	
	// update accrual ran info for the given principal id with new timestamp
	public void updatePrincipalAccrualRanInfo(String principalId);

}
