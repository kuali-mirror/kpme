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
package org.kuali.kpme.core.leaveplan.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.leaveplan.LeavePlanBo;

public interface LeavePlanDao {

		/**
	 * Get leave plan from id
	 * @param lmLeavePlanId
	 * @return LeavePlan
	 */
	public LeavePlanBo getLeavePlan(String lmLeavePlanId);
	
	public LeavePlanBo getLeavePlan(String leavePlan, LocalDate asOfDate);

    public List<LeavePlanBo> getLeavePlans(List<String> leavePlan, LocalDate asOfDate);
	
	public int getNumberLeavePlan(String leavePlan);
	
	public List<LeavePlanBo> getAllActiveLeavePlan(String leavePlan, LocalDate asOfDate);
	
	public List<LeavePlanBo> getAllInActiveLeavePlan(String leavePlan, LocalDate asOfDate);

    List<LeavePlanBo> getLeavePlans(String leavePlan, String calendarYearStart, String descr, String planningMonths, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory);
    
    public List<LeavePlanBo> getLeavePlansNeedsScheduled(int thresholdDays, LocalDate asOfDate);

}
