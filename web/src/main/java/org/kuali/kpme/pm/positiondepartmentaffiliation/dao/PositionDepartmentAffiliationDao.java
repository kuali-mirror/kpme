package org.kuali.kpme.pm.positiondepartmentaffiliation.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positiondepartmentaffiliation.PositionDepartmentAffiliation;

public interface PositionDepartmentAffiliationDao {
	
	public PositionDepartmentAffiliation getPositionDepartmentAffiliationById(String pmPositionDeptAfflId);
	public List<PositionDepartmentAffiliation> getPositionDepartmentAffiliationList(String positionDeptAfflType, LocalDate asOfDate);
	
}
