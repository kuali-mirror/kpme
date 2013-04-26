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

import java.util.List;

import org.joda.time.DateTime;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunchDocument;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MissedPunchApprovalJob implements Job {
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

		String hrCalendarEntryId = jobDataMap.getString("hrCalendarEntryId");

		CalendarEntry calendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarEntry(hrCalendarEntryId);

		DateTime beginDate = calendarEntry.getBeginPeriodFullDateTime();
		DateTime endDate = calendarEntry.getEndPeriodFullDateTime();
   	
		List<TimesheetDocumentHeader> timesheetDocumentHeaders = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeaders(beginDate, endDate);
		for (TimesheetDocumentHeader timesheetDocumentHeader : timesheetDocumentHeaders) {
			List<MissedPunchDocument> missedPunchDocuments = TkServiceLocator.getMissedPunchService().getMissedPunchDocsByTimesheetDocumentId(timesheetDocumentHeader.getDocumentId());
			for (MissedPunchDocument missedPunchDocument : missedPunchDocuments) {
				TkServiceLocator.getMissedPunchService().approveMissedPunch(missedPunchDocument);
			}
		}
	}
	
}