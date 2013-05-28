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
package org.kuali.kpme.tklm.leave.timeoff.service;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.tklm.leave.timeoff.SystemScheduledTimeOff;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.springframework.cache.annotation.Cacheable;

public interface SystemScheduledTimeOffService {
    
    /**
     * Fetch SystemScheduledTimeOff by id
     * @param lmSystemScheduledTimeOffId
     * @return
     */
    @Cacheable(value= SystemScheduledTimeOff.CACHE_NAME, key="'lmSystemScheduledTimeOffId=' + #p0")
    public SystemScheduledTimeOff getSystemScheduledTimeOff(String lmSystemScheduledTimeOffId);
    
    public List<SystemScheduledTimeOff> getSystemScheduledTimeOffForPayPeriod(
			String leavePlan, LocalDate startDate, LocalDate endDate);

	public SystemScheduledTimeOff getSystemScheduledTimeOffByDate(String leavePlan, LocalDate startDate);

	Assignment getAssignmentToApplyHolidays(
			TimesheetDocument timesheetDocument, LocalDate payEndDate);

	/**
	 * Calculate System Scheduled Time Off hours based on given hours and fte of job
	 * @param job
	 * @param sstoHours
	 * @return
	 */
    public BigDecimal calculateSysSchTimeOffHours(Job job, BigDecimal sstoHours);

    List<SystemScheduledTimeOff> getSystemScheduledTimeOffs(String userPrincipalId, LocalDate fromEffdt, LocalDate toEffdt, String earnCode, LocalDate fromAccruedDate, LocalDate toAccruedDate, 
    		LocalDate fromSchTimeOffDate, LocalDate toSchTimeOffDate, String active, String showHist);
    /**
     * Get the list of all active system scheduled time off for the given leave plan and dates
     * @param fromEffdt
     * @param toEffdt
     * @param fromAccruedDate
     * @param toAccruedDate
     * @param leavePlan
     * @return
     */
    public List<SystemScheduledTimeOff> getSystemScheduledTimeOffsForLeavePlan(LocalDate fromAccruedDate,LocalDate toAccruedDate, String leavePlan);

    /**
     * 
     * @param leavePlan
     * @param localDate
     * @return Returns the description of the system scheduled time off for the given leave plan and date, or the empty string if no ssto exists.
     */
	public String getSSTODescriptionForDate(String leavePlan, LocalDate localDate);
}
