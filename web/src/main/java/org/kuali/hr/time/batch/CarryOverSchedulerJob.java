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
package org.kuali.hr.time.batch;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.batch.service.BatchJobService;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class CarryOverSchedulerJob extends QuartzJobBean {

	private static int LEAVE_PLAN_POLLING_WINDOW;
	
	private static BatchJobService batchJobService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		LocalDate asOfDate = LocalDate.now();
		List<LeavePlan> leavePlans = TkServiceLocator.getLeavePlanService().getLeavePlansNeedsCarryOverScheduled(getLeavePlanPollingWindow(), asOfDate);
        try {
        	if(leavePlans!=null && !leavePlans.isEmpty()) {
				DateTime current = asOfDate.toDateTimeAtStartOfDay();
		        DateTime windowStart = current.minusDays(getLeavePlanPollingWindow());
		        DateTime windowEnd = current.plusDays(getLeavePlanPollingWindow());

        		// schedule batch job for all LeavePlans who fall in leave polling window.
        		for(LeavePlan leavePlan : leavePlans) {
        			if(leavePlan.getBatchPriorYearCarryOverStartDate() != null  && leavePlan.getBatchPriorYearCarryOverStartTime() != null ) {
        				DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd");
        				DateTime batchPriorYearCarryOverStartDateTime = formatter.parseDateTime(leavePlan.getBatchPriorYearCarryOverStartDate()).plus(leavePlan.getBatchPriorYearCarryOverStartTime().getTime());
        				if (batchPriorYearCarryOverStartDateTime.compareTo(windowStart) >= 0 && batchPriorYearCarryOverStartDateTime.compareTo(windowEnd) <= 0) {
        					getBatchJobService().scheduleLeaveCarryOverJobs(leavePlan);
        				}
        			}
        		}
        	}
        } catch (SchedulerException se) {
        	throw new JobExecutionException("Exception thrown during job scheduling of Carry over for Leave", se);
        }
	}
	
	public int getLeavePlanPollingWindow() {
		return LEAVE_PLAN_POLLING_WINDOW;
	}

	public void setLeavePlanPollingWindow(int leavePlanPollingWindow) {
		LEAVE_PLAN_POLLING_WINDOW = leavePlanPollingWindow;
	}

	public static BatchJobService getBatchJobService() {
		return CarryOverSchedulerJob.batchJobService;
	}

	public void setBatchJobService(BatchJobService batchJobService) {
		CarryOverSchedulerJob.batchJobService = batchJobService;
	}

}