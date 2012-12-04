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
package org.kuali.hr.time.batch;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class InitiateJob implements Job {
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

		String hrCalendarEntriesId = jobDataMap.getString("hrCalendarEntriesId");
		String principalId = jobDataMap.getString("principalId");
		
		CalendarEntries calendarEntry = TkServiceLocator.getCalendarEntriesService().getCalendarEntries(hrCalendarEntriesId);
		Calendar calendar = TkServiceLocator.getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
		
		try {
			if (StringUtils.equals(calendar.getCalendarTypes(), "Pay")) {
				TkServiceLocator.getTimesheetService().openTimesheetDocument(principalId, calendarEntry);
			} else if (StringUtils.equals(calendar.getCalendarTypes(), "Leave")) {
				TkServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument(principalId, calendarEntry);
			}
		} catch (WorkflowException we) {
			throw new JobExecutionException("Failure to execute job due to WorkflowException", we);
		}
	}
	
}