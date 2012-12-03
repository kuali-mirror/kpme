/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.principal.dao;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.principal.PrincipalHRAttributes;

public interface PrincipalHRAttributesDao {
	public void saveOrUpdate(PrincipalHRAttributes principalCalendar);

	public void saveOrUpdate(List<PrincipalHRAttributes> lstPrincipalCalendar);

	public PrincipalHRAttributes getPrincipalCalendar(String principalId, Date asOfDate);
	
	public List<PrincipalHRAttributes> getActiveEmployeesForPayCalendar(String payCalendarName, Date asOfDate);

	public List<PrincipalHRAttributes> getActiveEmployeesForLeaveCalendar(String leaveCalendarName, Date asOfDate);

	/**
	 * KPME-1250 Kagata
	 * Get a list of active employees based on leave plan and as of a particular date 
	 * @param leavePlan
	 * @param asOfDate
	 * @return
	 */
	public List<PrincipalHRAttributes> getActiveEmployeesForLeavePlan(String leavePlan, Date asOfDate);
	
// this method is not needed anymore since we changed the primary key of
//	PrincipalHRAttributes table from principalId to hrPrincipalAttributeId
//	use getPrincipalCalendar(String principalId, Date asOfDate) instead
//	public PrincipalHRAttributes getPrincipalHRAttributes(String principalId);
	
	public PrincipalHRAttributes getInactivePrincipalHRAttributes(String principalId, Date asOfDate);
	
	public PrincipalHRAttributes getPrincipalHRAttributes(String hrPrincipalAttributeId);
	
	public List<PrincipalHRAttributes> getAllActivePrincipalHrAttributesForPrincipalId(String principalId, Date asOfDate);
	
	public List<PrincipalHRAttributes> getAllInActivePrincipalHrAttributesForPrincipalId(String principalId, Date asOfDate);
	
	public PrincipalHRAttributes getMaxTimeStampPrincipalHRAttributes(String principalId);
	
	public List<PrincipalHRAttributes> getActivePrincipalHrAttributesForRange(String principalId, Date startDate, Date endDate);
	 
	public List<PrincipalHRAttributes> getInactivePrincipalHRAttributesForRange(String principalId, Date startDate, Date endDate);

    public List<String> getUniqueLeavePayGroupsForPrincipalIds(List<String> principalIds);
    
    public List<PrincipalHRAttributes> getPrincipalHrAtributes(String principalId, java.sql.Date fromEffdt, java.sql.Date toEffdt,String active, String showHistory);
    
}
