package org.kuali.kpme.pm.positiondepartmentaffiliation.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positiondepartmentaffiliation.PositionDepartmentAffiliation;

public interface PositionDepartmentAffiliationService {

	/**
	 * retrieve the PositionDepartmentAffiliation with given id
	 * @param pmPositionDeptAfflId
	 * @return
	 */
	public PositionDepartmentAffiliation getPositionDepartmentAffiliationById(String pmPositionDeptAfflId);
	
	public List<PositionDepartmentAffiliation> getPositionDepartmentAffiliationList(String positionDeptAfflType, LocalDate asOfDate);
}
