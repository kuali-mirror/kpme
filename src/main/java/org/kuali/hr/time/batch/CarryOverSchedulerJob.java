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

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.joda.time.DateTime;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.batch.service.BatchJobService;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class CarryOverSchedulerJob extends QuartzJobBean {

	private static int LEAVE_PLAN_POLLING_WINDOW;
	
	private static BatchJobService batchJobService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Date asOfDate = TKUtils.getCurrentDate();
		List<LeavePlan> leavePlans = TkServiceLocator.getLeavePlanService().getLeavePlansNeedsScheduled(getLeavePlanPollingWindow(), asOfDate);
        try {
        	if(leavePlans!=null && !leavePlans.isEmpty()) {
				DateTime current = new DateTime(asOfDate.getTime());
		        DateTime windowStart = current.minusDays(getLeavePlanPollingWindow());
		        DateTime windowEnd = current.plusDays(getLeavePlanPollingWindow());

        		// schedule batch job for all LeavePlans who fall in leave polling window.
        		for(LeavePlan leavePlan : leavePlans) {
        			if(leavePlan.getBatchPriorYearCarryOverStartDate() != null  && leavePlan.getBatchPriorYearCarryOverStartTime() != null ) {
        				java.util.Date batchDate = getBatchJobStartDateTime(leavePlan);
        				DateTime batchDateTime = new DateTime(batchDate.getTime());
        				if(batchDateTime.compareTo(windowStart) >= 0 && batchDateTime.compareTo(windowEnd) <= 0) {
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
	
	private java.util.Date getBatchJobStartDateTime(LeavePlan leavePlan) {
		
		String batchJobDate = leavePlan.getBatchPriorYearCarryOverStartDate();
		
		java.util.Calendar batchJobTimeCal = java.util.Calendar.getInstance();
		batchJobTimeCal.setTimeInMillis(leavePlan.getBatchPriorYearCarryOverStartTime().getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
		sdf.setLenient(false);
		
		java.util.Date batchJobStart = null;
		
		try {
			batchJobStart = sdf.parse(batchJobDate);
		} catch (ParseException e) {
		}
		
		java.util.Calendar batchJobStartDateTime = java.util.Calendar.getInstance();
		batchJobStartDateTime.setTime(batchJobStart);
		batchJobStartDateTime.set(java.util.Calendar.YEAR,java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));
		batchJobStartDateTime.set(java.util.Calendar.HOUR_OF_DAY,batchJobTimeCal.get(java.util.Calendar.HOUR_OF_DAY));
		batchJobStartDateTime.set(java.util.Calendar.MINUTE,batchJobTimeCal.get(java.util.Calendar.MINUTE));
		batchJobStartDateTime.set(java.util.Calendar.SECOND, 0);
		batchJobStartDateTime.set(java.util.Calendar.MILLISECOND, 0);
		
		return batchJobStartDateTime.getTime();
	}

}