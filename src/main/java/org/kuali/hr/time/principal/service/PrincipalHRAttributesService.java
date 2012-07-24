package org.kuali.hr.time.principal.service;

import java.util.Date;
import java.util.List;
import org.kuali.hr.time.principal.PrincipalHRAttributes;

public interface PrincipalHRAttributesService {
	/**
	 * Fetch PrincipalCalendar object at a particular date
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
	public PrincipalHRAttributes getPrincipalCalendar(String principalId, Date asOfDate);
	
	/**
	 * KPME-1250 Kagata
	 * Get a list of active employees based on leave plan and as of a particular date 
	 * @param leavePlan
	 * @param asOfDate
	 * @return
	 */
    public List<PrincipalHRAttributes> getActiveEmployeesForLeavePlan(String leavePlan, Date asOfDate);
    
	/**
	 * Fetch inactive PrincipalHRAttributes object at a particular date
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
    public PrincipalHRAttributes getInactivePrincipalHRAttributes(String principalId, Date asOfDate);
	/**
	 * Fetch PrincipalHRAttributes object with given id
	 * @param hrPrincipalAttributeId
	 * @return
	 */
    public PrincipalHRAttributes getPrincipalHRAttributes(String hrPrincipalAttributeId);
    
    public List<PrincipalHRAttributes> getAllPrincipalHrAttributesForPrincipalId(String principalId, Date asOfDate);
}
