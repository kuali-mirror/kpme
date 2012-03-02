package org.kuali.hr.time.principal.dao;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.principal.PrincipalHRAttributes;

public interface PrincipalHRAttributesDao {
	public void saveOrUpdate(PrincipalHRAttributes principalCalendar);

	public void saveOrUpdate(List<PrincipalHRAttributes> lstPrincipalCalendar);

	public PrincipalHRAttributes getPrincipalCalendar(String principalId, Date asOfDate);
	
	/**
	 * KPME-1250 Kagata
	 * Get a list of active employees based on leave plan and as of a particular date 
	 * @param leavePlan
	 * @param asOfDate
	 * @return
	 */
	public List<PrincipalHRAttributes> getActiveEmployeesForLeavePlan(String leavePlan, Date asOfDate);

	public PrincipalHRAttributes getPrincipalHRAttributes(String principalId);
}
