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
package org.kuali.kpme.core.api.leaveplan;

import java.sql.Time;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.util.HrApiConstants;

/**
 * <p>LeavePlanContract interface.</p>
 *
 */
public interface LeavePlanContract extends HrBusinessObjectContract {
	
	public static final String CACHE_NAME = HrApiConstants.CacheNamespace.NAMESPACE_PREFIX + "LeavePlan";
	
	/**
	 * The date batch job should run to create a carry over leave block 
	 * for each accrual category balance from the prior year under a LeavePlan
	 * 
	 * <p>
	 * batchPriorYearCarryOverStartDate of a LeavePlan
	 * <p>
	 * 
	 * @return batchPriorYearCarryOverStartDate for LeavePlan
	 */
	public String getBatchPriorYearCarryOverStartDate();
	
	/**
	 * The Time batch job should run to create a carry over leave block 
	 * for each accrual category balance from the prior year under a LeavePlan
	 * 
	 * <p>
	 * batchPriorYearCarryOverStartTime of a LeavePlan
	 * <p>
	 * 
	 * @return batchPriorYearCarryOverStartTime for LeavePlan
	 */
	public Time getBatchPriorYearCarryOverStartTime();
	
	/**
	 * The Number of months to build accruals for for a LeavePlan
	 * 
	 * <p>
	 * planningMonths of a LeavePlan
	 * <p>
	 * 
	 * @return planningMonths for LeavePlan
	 */
	public String getPlanningMonths();

	/**
	 * The Primary Key of a LeavePlan entry saved in a database
	 * 
	 * <p>
	 * lmLeavePlanId of an LeavePlan
	 * <p>
	 * 
	 * @return lmLeavePlanId for LeavePlan
	 */
	public String getLmLeavePlanId();
	
	/**
	 * The name of a LeavePlan
	 * 
	 * <p>
	 * leavePlan field of a LeavePlan
	 * <p>
	 * 
	 * @return leavePlan for LeavePlan
	 */
	public String getLeavePlan();

	/**
	 * The description of a LeavePlan
	 * 
	 * <p>
	 * description of a LeavePlan
	 * <p>
	 * 
	 * @return description for LeavePlan
	 */
	public String getDescr();
	
	/**
	 * The Month and Day (MM/DD) of the start of the year for a LeavePlan
	 * 
	 * <p>
	 * calendarYearStart of a LeavePlan
	 * <p>
	 * 
	 * @return calendarYearStart for LeavePlan
	 */
	public String getCalendarYearStart() ;

	/**
	 * The history flag of a LeavePlan
	 * 
	 * <p>
	 * history flag of a LeavePlan
	 * <p>
	 * 
	 * @return Y if on, N if not
	 */
	public Boolean getHistory();
}
