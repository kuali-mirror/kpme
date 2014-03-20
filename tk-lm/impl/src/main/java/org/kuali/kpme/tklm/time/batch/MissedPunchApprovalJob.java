/**
 * Copyright 2004-2014 The Kuali Foundation
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
import org.joda.time.DateTime;
import org.kuali.kpme.core.api.calendar.Calendar;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributesContract;
import org.kuali.kpme.core.batch.BatchJob;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunchDocument;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

public class MissedPunchApprovalJob extends BatchJob {
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

		String hrCalendarEntryId = jobDataMap.getString("hrCalendarEntryId");

        CalendarEntry calendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarEntry(hrCalendarEntryId);
		Calendar calendar = HrServiceLocator.getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
		DateTime beginDate = calendarEntry.getBeginPeriodFullDateTime();
		DateTime endDate = calendarEntry.getEndPeriodFullDateTime();
   	
		List<TimesheetDocumentHeader> timesheetDocumentHeaders = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeaders(beginDate, endDate);
		for (TimesheetDocumentHeader timesheetDocumentHeader : timesheetDocumentHeaders) {
			if(timesheetDocumentHeader != null) {
        		String timeDocId = timesheetDocumentHeader.getDocumentId();
        		TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(timeDocId);
        		if(timesheetDocument != null
        				&& TkConstants.MISSEDPUNCH_APPROVAL_TIME_DOC_STATUS.contains(KEWServiceLocator.getRouteHeaderService().getDocumentStatus(timeDocId))) {
        			PrincipalHRAttributesContract phraRecord = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(timesheetDocument.getPrincipalId(), endDate.toLocalDate());
					if(phraRecord != null && StringUtils.isNotBlank(phraRecord.getPayCalendar()) && phraRecord.getPayCalendar().equals(calendar.getCalendarName())) {	
						List<MissedPunchDocument> missedPunchDocuments = TkServiceLocator.getMissedPunchService().getMissedPunchDocumentsByTimesheetDocumentId(timeDocId);
						for (MissedPunchDocument missedPunchDocument : missedPunchDocuments) {
							if(missedPunchDocument != null 
									&& DocumentStatus.ENROUTE.equals(KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(missedPunchDocument.getDocumentNumber())) ){
								TkServiceLocator.getMissedPunchService().approveMissedPunchDocument(missedPunchDocument);
							}
						}
					}
        		}
			}
		}
	}
	
}
