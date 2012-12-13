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
package org.kuali.hr.lm.leaveplan.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.lm.leaveplan.LeavePlan;


public interface LeavePlanDao {

		/**
	 * Get leave plan from id
	 * @param lmLeavePlanId
	 * @return LeavePlan
	 */
	public LeavePlan getLeavePlan(String lmLeavePlanId);
	
	public LeavePlan getLeavePlan(String leavePlan, Date asOfDate);
	
	public int getNumberLeavePlan(String leavePlan);
	
	public List<LeavePlan> getAllActiveLeavePlan(String leavePlan, Date asOfDate);
	
	public List<LeavePlan> getAllInActiveLeavePlan(String leavePlan, Date asOfDate);

    List<LeavePlan> getLeavePlans(String leavePlan, String calendarYearStart, String descr, String planningMonths, Date fromEffdt, Date toEffdt, String active, String showHistory);
}
