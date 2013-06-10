/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.core.principal.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.springframework.cache.annotation.Cacheable;

public interface PrincipalHRAttributesService {
	/**
	 * Fetch PrincipalCalendar object at a particular date
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= PrincipalHRAttributes.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'asOfDate=' + #p1")
	public PrincipalHRAttributes getPrincipalCalendar(String principalId, LocalDate asOfDate);
	
	/**
	 * Get a list of active employees based on pay calendar and as of a particular date 
	 * @param payCalendarName
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= PrincipalHRAttributes.CACHE_NAME, key="'payCalendarName=' + #p0 + '|' + 'asOfDate=' + #p1")
    public List<PrincipalHRAttributes> getActiveEmployeesForPayCalendar(String payCalendarName, LocalDate asOfDate);
    
	/**
	 * Get a list of active employees based on leave calendar and as of a particular date 
	 * @param leaveCalendarName
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= PrincipalHRAttributes.CACHE_NAME, key="'leaveCalendarName=' + #p0 + '|' + 'asOfDate=' + #p1")
    public List<PrincipalHRAttributes> getActiveEmployeesForLeaveCalendar(String leaveCalendarName, LocalDate asOfDate);
    
    /**
     * Get a list of unique principal ids that match given criteria, used by leave approval and leave request approval pages
     * @param leaveCalendarName
     * @param pidList
     * @param asOfDate
     * @return
     */
    @Cacheable(value= PrincipalHRAttributes.CACHE_NAME, key="'leaveCalendarName=' + #p0 + '|' + 'asOfDate=' + #p1")
    public List<String> getActiveEmployeesIdForLeaveCalendarAndIdList(String leaveCalendarName, List<String> pidList, LocalDate asOfDate);
	
    /**
     * Get a list of unique principal ids that match given criteria, used by Time approval pages
     * @param timeCalendarName
     * @param pidList
     * @param asOfDate
     * @return
     */
    @Cacheable(value= PrincipalHRAttributes.CACHE_NAME, key="'timeCalendarName=' + #p0 + '|' + 'asOfDate=' + #p1")
    public List<String> getActiveEmployeesIdForTimeCalendarAndIdList(String timeCalendarName, List<String> pidList, LocalDate asOfDate);
    
    /**
	 * KPME-1250 Kagata
	 * Get a list of active employees based on leave plan and as of a particular date 
	 * @param leavePlan
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= PrincipalHRAttributes.CACHE_NAME, key="'leavePlan=' + #p0 + '|' + 'asOfDate=' + #p1")
    public List<PrincipalHRAttributes> getActiveEmployeesForLeavePlan(String leavePlan, LocalDate asOfDate);
    
	/**
	 * Fetch inactive PrincipalHRAttributes object at a particular date
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
    public PrincipalHRAttributes getInactivePrincipalHRAttributes(String principalId, LocalDate asOfDate);
	/**
	 * Fetch PrincipalHRAttributes object with given id
	 * @param hrPrincipalAttributeId
	 * @return
	 */
    public PrincipalHRAttributes getPrincipalHRAttributes(String hrPrincipalAttributeId);
    
    public List<PrincipalHRAttributes> getAllActivePrincipalHrAttributesForPrincipalId(String principalId, LocalDate asOfDate);
    
    public List<PrincipalHRAttributes> getAllInActivePrincipalHrAttributesForPrincipalId(String principalId, LocalDate asOfDate);
    
    public PrincipalHRAttributes getMaxTimeStampPrincipalHRAttributes(String principalId);
    
    /*
     * Fetch list of PrincipalHRAttributes that become active for given principalId and date range
     */
    public List<PrincipalHRAttributes> getActivePrincipalHrAttributesForRange(String principalId, LocalDate startDate, LocalDate endDate);
    /*
     * Fetch list of PrincipalHRAttributes that become inactive for given principalId and date range
     */
    public List<PrincipalHRAttributes> getInactivePrincipalHRAttributesForRange(String principalId, LocalDate startDate, LocalDate endDate);
    /**
     * Fetch list of PrincipalHRAttributes using given parameters
     *
     * @param principalId
     * @param leavePlan
     *@param fromEffdt
     * @param toEffdt
     * @param active
     * @param showHistory     @return
     */
    public List<PrincipalHRAttributes> getPrincipalHrAtributes(String userPrincipalId, String principalId, String leavePlan, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory);

    /**
     * Get List of all active pay calendars for the given principal ids.
     * @return
     */
    public List<String> getUniquePayCalendars(List<String> principalIds);
    
    /**
     * Get List of all active leave calendars for the given principal ids.
     * @return
     */
    public List<String> getUniqueLeaveCalendars(List<String> principalIds);
    
}