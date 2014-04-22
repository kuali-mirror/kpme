/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.principal.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.principal.PrincipalHRAttributesBo;

public interface PrincipalHRAttributesDao {
	public void saveOrUpdate(PrincipalHRAttributesBo principalCalendar);

	public void saveOrUpdate(List<PrincipalHRAttributesBo> lstPrincipalCalendar);

	public PrincipalHRAttributesBo getPrincipalCalendar(String principalId, LocalDate asOfDate);
	
	public List<PrincipalHRAttributesBo> getActiveEmployeesForPayCalendar(String payCalendarName, LocalDate asOfDate);

	public List<PrincipalHRAttributesBo> getActiveEmployeesForLeaveCalendar(String leaveCalendarName, LocalDate asOfDate);
	
	public List<String> getActiveEmployeesIdForLeaveCalendarAndIdList(String leaveCalendarName, List<String> pidList, LocalDate asOfDate);
	
    public List<String> getActiveEmployeesIdForTimeCalendarAndIdList(String timeCalendarName, List<String> pidList, LocalDate asOfDate);

	/**
	 * KPME-1250 Kagata
	 * Get a list of active employees based on leave plan and as of a particular date 
	 * @param leavePlan
	 * @param asOfDate
	 * @return
	 */
	public List<PrincipalHRAttributesBo> getActiveEmployeesForLeavePlan(String leavePlan, LocalDate asOfDate);
	
// this method is not needed anymore since we changed the primary key of
//	PrincipalHRAttributes table from principalId to hrPrincipalAttributeId
//	use getPrincipalCalendar(String principalId, Date asOfDate) instead
//	public PrincipalHRAttributes getPrincipalHRAttributes(String principalId);
	
	public PrincipalHRAttributesBo getInactivePrincipalHRAttributes(String principalId, LocalDate asOfDate);
	
	public PrincipalHRAttributesBo getPrincipalHRAttributes(String hrPrincipalAttributeId);
	
	public List<PrincipalHRAttributesBo> getAllActivePrincipalHrAttributesForPrincipalId(String principalId, LocalDate asOfDate);
	
	public List<PrincipalHRAttributesBo> getAllInActivePrincipalHrAttributesForPrincipalId(String principalId, LocalDate asOfDate);
	
	public PrincipalHRAttributesBo getMaxTimeStampPrincipalHRAttributes(String principalId);
	
	public List<PrincipalHRAttributesBo> getActivePrincipalHrAttributesForRange(String principalId, LocalDate startDate, LocalDate endDate);
	 
	public List<PrincipalHRAttributesBo> getInactivePrincipalHRAttributesForRange(String principalId, LocalDate startDate, LocalDate endDate);
    
    public List<PrincipalHRAttributesBo> getPrincipalHrAtributes(String principalId, String leavePlan, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory);
    
    public List<String> getUniquePayCalendars(List<String> principalIds);
    
    public List<String> getUniqueLeaveCalendars(List<String> principalIds);
    
}
