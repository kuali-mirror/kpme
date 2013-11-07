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
package org.kuali.kpme.tklm.time.batch;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.kpme.core.calendar.Calendar;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class InitiateJob implements Job {
	
	private static final Logger LOG = Logger.getLogger(InitiateJob.class);
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

		String hrCalendarEntryId = jobDataMap.getString("hrCalendarEntryId");
		String principalId = jobDataMap.getString("principalId");
		
		CalendarEntry calendarEntry = (CalendarEntry) HrServiceLocator.getCalendarEntryService().getCalendarEntry(hrCalendarEntryId);
		Calendar calendar = (Calendar) HrServiceLocator.getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
		
		try {
			if (StringUtils.equals(calendar.getCalendarTypes(), "Pay")) {
				TkServiceLocator.getTimesheetService().openTimesheetDocument(principalId, calendarEntry);
			} else if (StringUtils.equals(calendar.getCalendarTypes(), "Leave")) {
				LmServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument(principalId, calendarEntry);
			}
		} catch (WorkflowException we) {
			LOG.error("Failure to execute job due to WorkflowException", we);
//			throw new JobExecutionException("Failure to execute job due to WorkflowException", we);
		}
	}
	
}
