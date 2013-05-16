package org.kuali.kpme.pm.positiondepartmentaffiliation.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positiondepartmentaffiliation.PositionDepartmentAffiliation;
import org.kuali.kpme.pm.positiondepartmentaffiliation.dao.PositionDepartmentAffiliationDao;

public class PositionDepartmentAffiliationServiceImpl implements PositionDepartmentAffiliationService {

	private PositionDepartmentAffiliationDao positionDepartmentAffiliationDao;

	/**
	 * @return the positionDepartmentAffiliationDao
	 */
	public PositionDepartmentAffiliationDao getPositionDepartmentAffiliationDao() {
		return positionDepartmentAffiliationDao;
	}

	/**
	 * @param positionDepartmentAffiliationDao the positionDepartmentAffiliationDao to set
	 */
	public void setPositionDepartmentAffiliationDao(
			PositionDepartmentAffiliationDao positionDepartmentAffiliationDao) {
		this.positionDepartmentAffiliationDao = positionDepartmentAffiliationDao;
	}

	@Override
	public PositionDepartmentAffiliation getPositionDepartmentAffiliationById(
			String pmPositionDeptAfflId) {
		return this.positionDepartmentAffiliationDao.getPositionDepartmentAffiliationById(pmPositionDeptAfflId);
	}
	
	@Override
	public List<PositionDepartmentAffiliation> getPositionDepartmentAffiliationList(String positionDeptAfflType, LocalDate asOfDate) {
		return this.positionDepartmentAffiliationDao.getPositionDepartmentAffiliationList(positionDeptAfflType, asOfDate);
	}


}
