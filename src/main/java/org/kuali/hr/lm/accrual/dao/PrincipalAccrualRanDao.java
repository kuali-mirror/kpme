package org.kuali.hr.lm.accrual.dao;

import org.kuali.hr.lm.accrual.PrincipalAccrualRan;

public interface PrincipalAccrualRanDao {
	
	public PrincipalAccrualRan getLastPrincipalAccrualRan(String principalId);
	
	public void updatePrincipalAccrualRanInfo(String principalId);

}
