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
package org.kuali.kpme.tklm.common;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class CalendarEntrySchedulerJob extends QuartzJobBean {

	private static int CALENDAR_ENTRIES_POLLING_WINDOW;
	
	private static BatchJobService batchJobService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		DateTime asOfDate = new LocalDate().toDateTimeAtStartOfDay();
        List<CalendarEntry> calendarEntries = HrServiceLocator.getCalendarEntryService().getCurrentCalendarEntriesNeedsScheduled(getCalendarEntriesPollingWindow(), asOfDate);

        try {
	        for (CalendarEntry calendarEntry : calendarEntries) {
	            if (calendarEntry.getEndPeriodDateTime() != null) {
	            	getBatchJobService().scheduleEndReportingPeriodJobs(calendarEntry);
	            }
	        	
	            if (calendarEntry.getBatchInitiateDateTime() != null) {
	            	getBatchJobService().scheduleInitiateJobs(calendarEntry);
	            }
	            
	            if (calendarEntry.getBatchEndPayPeriodDateTime() != null) {
	            	getBatchJobService().scheduleEndPayPeriodJobs(calendarEntry);
	            }
	            
	            if (calendarEntry.getBatchEmployeeApprovalDateTime() != null) {
	            	getBatchJobService().scheduleEmployeeApprovalJobs(calendarEntry);
	            }
	            
	            if (calendarEntry.getBatchSupervisorApprovalDateTime() != null) {
	            	getBatchJobService().scheduleMissedPunchApprovalJobs(calendarEntry);
	            }
	            
	            if (calendarEntry.getBatchSupervisorApprovalDateTime() != null) {
	            	getBatchJobService().scheduleSupervisorApprovalJobs(calendarEntry);
	            }
	        }
        } catch (SchedulerException se) {
        	throw new JobExecutionException("Exception thrown during job scheduling", se);
        }
	}
	
	public int getCalendarEntriesPollingWindow() {
		return CALENDAR_ENTRIES_POLLING_WINDOW;
	}

	public void setCalendarEntriesPollingWindow(int calendarEntriesPollingWindow) {
		CALENDAR_ENTRIES_POLLING_WINDOW = calendarEntriesPollingWindow;
	}

	public static BatchJobService getBatchJobService() {
		return CalendarEntrySchedulerJob.batchJobService;
	}

	public void setBatchJobService(BatchJobService batchJobService) {
		CalendarEntrySchedulerJob.batchJobService = batchJobService;
	}

}