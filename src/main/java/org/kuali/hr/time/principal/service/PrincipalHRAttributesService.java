package org.kuali.hr.time.principal.service;

import java.util.Date;
import java.util.List;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.springframework.cache.annotation.Cacheable;

public interface PrincipalHRAttributesService {
	/**
	 * Fetch PrincipalCalendar object at a particular date
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= PrincipalHRAttributes.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'asOfDate=' + #p1")
	public PrincipalHRAttributes getPrincipalCalendar(String principalId, Date asOfDate);
	
	/**
	 * KPME-1250 Kagata
	 * Get a list of active employees based on leave plan and as of a particular date 
	 * @param leavePlan
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= PrincipalHRAttributes.CACHE_NAME, key="'leavePlan=' + #p0 + '|' + 'asOfDate=' + #p1")
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
    
    public List<PrincipalHRAttributes> getAllActivePrincipalHrAttributesForPrincipalId(String principalId, Date asOfDate);
    
    public List<PrincipalHRAttributes> getAllInActivePrincipalHrAttributesForPrincipalId(String principalId, Date asOfDate);
    
    public PrincipalHRAttributes getMaxTimeStampPrincipalHRAttributes(String principalId);
    
    /*
     * Fetch list of PrincipalHRAttributes that become active for given principalId and date range
     */
    public List<PrincipalHRAttributes> getActivePrincipalHrAttributesForRange(String principalId, Date startDate, Date endDate);
    /*
     * Fetch list of PrincipalHRAttributes that become inactive for given principalId and date range
     */
    public List<PrincipalHRAttributes> getInactivePrincipalHRAttributesForRange(String principalId, Date startDate, Date endDate);
}
