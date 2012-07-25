package org.kuali.hr.lm.accrual.service;

import org.kuali.hr.lm.accrual.PrincipalAccrualRan;
import org.kuali.hr.lm.accrual.dao.PrincipalAccrualRanDao;

public class PrincipalAccrualRanServiceImpl implements PrincipalAccrualRanService{

	private PrincipalAccrualRanDao principalAccrualRanDao;
	
	@Override
	public PrincipalAccrualRan getLastPrincipalAccrualRan(String principalId) {
		return principalAccrualRanDao.getLastPrincipalAccrualRan(principalId);
	}
	
	@Override
	public void updatePrincipalAccrualRanInfo(String principalId) {
		principalAccrualRanDao.updatePrincipalAccrualRanInfo(principalId);
	}

	public PrincipalAccrualRanDao getPrincipalAccrualRanDao() {
		return principalAccrualRanDao;
	}

	public void setPrincipalAccrualRanDao(
			PrincipalAccrualRanDao principalAccrualRanDao) {
		this.principalAccrualRanDao = principalAccrualRanDao;
	}
}
