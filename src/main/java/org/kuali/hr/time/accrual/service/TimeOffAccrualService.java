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
package org.kuali.hr.time.accrual.service;

import org.kuali.hr.time.accrual.TimeOffAccrual;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.springframework.cache.annotation.Cacheable;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface TimeOffAccrualService {
	/**
	 * Get a list of maps that represents a persons accrual balances
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= TimeOffAccrual.CACHE_NAME, key="'{getTimeOffAccrualsCalc}' + 'principalId=' + #p0 + '|' + 'asOfDate=' + #p1")
	public List<Map<String, Object>> getTimeOffAccrualsCalc(String principalId, Date asOfDate);
	
	/**
	 * Validate the accrual hours for the time blocks of the given TimesheetDocument
	 * and returns a JSONArray of warning messages
	 * @param timesheetDocument
	 * @return JSONArray
	 */
	public List<String> validateAccrualHoursLimit(TimesheetDocument timesheetDocument);
	
	public List<String> validateAccrualHoursLimit(String pId, List<TimeBlock> tbList, Date asOfDate);
	
	/**
	 * Validate the accrual hours for the time blocks by earncode of the given TimesheetDocument
	 * and returns a JSONArray of warning messages
	 * @param timesheetDocument
	 * @return JSONArray
	 */
	public List<String> validateAccrualHoursLimitByEarnCode(TimesheetDocument timesheetDocument, String selectEarnCode);
	
	/**
	 * Fetch time off accrual as of a particular unique id
	 * @param laTimeOffAccrualId
	 * @return
	 */
    @Cacheable(value= TimeOffAccrual.CACHE_NAME, key="'laTimeOffAccrualId=' + #p0")
	public TimeOffAccrual getTimeOffAccrual(Long laTimeOffAccrualId);

    @Cacheable(value= TimeOffAccrual.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'asOfDate=' + #p1")
	public List<TimeOffAccrual> getTimeOffAccruals(String principalId, Date asOfDate);

    /**
     * get the count of TimeOffAccrual by given parameters\
     * @param accrualCategory
     * @param effectiveDate
     * @param principalId
     * @param lmAccrualId
     * @return int
     */
	public int getTimeOffAccrualCount(String accrualCategory, Date effectiveDate, String principalId, String lmAccrualId);
}
