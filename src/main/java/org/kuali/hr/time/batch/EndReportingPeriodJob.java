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

import java.text.DateFormat;

import org.joda.time.DateTime;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class EndReportingPeriodJob implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		
		String hrCalendarEntriesId = jobDataMap.getString("hrCalendarEntriesId");
		String principalId = jobDataMap.getString("principalId");
		
		CalendarEntries calendarEntry = TkServiceLocator.getCalendarEntriesService().getCalendarEntries(hrCalendarEntriesId);
		DateTime endPeriodDateTime = new DateTime(calendarEntry.getEndPeriodDateTime());
		
		String subject = "End of Reporting Period Reminder";
		StringBuilder message = new StringBuilder();
		message.append("Please submit your Leave Calendar for the period ending on ");
		message.append(DateFormat.getDateInstance(DateFormat.LONG).format(endPeriodDateTime.minusDays(1).toDate()));
		message.append(", so it can be reviewed and approved by your supervisor.");
		
		TkServiceLocator.getKPMENotificationService().sendNotification(subject, message.toString(), principalId);
	}
	
}