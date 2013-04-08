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

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.hr.core.notification.service.KPMENotificationService;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.lm.workflow.service.LeaveCalendarDocumentHeaderService;
import org.kuali.hr.time.calendar.CalendarEntry;
import org.kuali.hr.time.calendar.service.CalendarEntryService;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.principal.service.PrincipalHRAttributesService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class LeaveCalendarDelinquencyJob implements Job {
	
	private static int CALENDAR_ENTRIES_POLLING_WINDOW;

	private static CalendarEntryService CALENDAR_ENTRY_SERVICE;
	private static KPMENotificationService KPME_NOTIFICATION_SERVICE;
	private static LeaveCalendarDocumentHeaderService LEAVE_CALENDAR_DOCUMENT_HEADER_SERVICE;
	private static PrincipalHRAttributesService PRINCIPAL_HR_ATTRIBUTES_SERVICE;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Set<String> principalIds = new HashSet<String>();
		
		DateTime asOfDate = new LocalDate().toDateTimeAtStartOfDay();
		List<CalendarEntry> calendarEntries = getCalendarEntryService().getCurrentCalendarEntriesNeedsScheduled(getCalendarEntriesPollingWindow(), asOfDate);
		
		for (CalendarEntry calendarEntry : calendarEntries) {
			String hrCalendarId = calendarEntry.getHrCalendarId();
			Date currentBeginDate = calendarEntry.getBeginPeriodDateTime();
			
			if (currentBeginDate.before(asOfDate.toDate()) || DateUtils.isSameDay(currentBeginDate, asOfDate.toDate())) {
				CalendarEntry previousCalendarEntry = getCalendarEntryService().getPreviousCalendarEntryByCalendarId(hrCalendarId, calendarEntry);
				
				if (previousCalendarEntry != null) {
					String calendarName = previousCalendarEntry.getCalendarName();
					Date previousBeginDate = previousCalendarEntry.getBeginPeriodDateTime();
					List<PrincipalHRAttributes> principalHRAttributes = getPrincipalHRAttributesService().getActiveEmployeesForLeaveCalendar(calendarName, previousBeginDate);
					
					for (PrincipalHRAttributes principalHRAttribute : principalHRAttributes) {
						String principalId = principalHRAttribute.getPrincipalId();
						
						if (!principalIds.contains(principalId)) {
							List<LeaveCalendarDocumentHeader> leaveCalendarDocumentHeaders = getLeaveCalendarDocumentHeaderService().getSubmissionDelinquentDocumentHeaders(principalId, currentBeginDate);
			
							if (!leaveCalendarDocumentHeaders.isEmpty()) {
								principalIds.add(principalId);
					        }
						}
					}
				}
			}
		}
		
		for (String principalId : principalIds) {
			String subject = "Delinquent Leave Calendar Reminder";
			StringBuilder message = new StringBuilder();
			message.append("You currently have one or more DELINQUENT Leave Calendars.");
			message.append(SystemUtils.LINE_SEPARATOR);
			message.append(SystemUtils.LINE_SEPARATOR);
			message.append("Please review/enter your time-off for any prior unapproved months and submit your calendar(s).");
			
			getKpmeNotificationService().sendNotification(subject, message.toString(), principalId);
		}
	}
	
	public static int getCalendarEntriesPollingWindow() {
		return CALENDAR_ENTRIES_POLLING_WINDOW;
	}

	public static void setCalendarEntriesPollingWindow(int calendarEntriesPollingWindow) {
		CALENDAR_ENTRIES_POLLING_WINDOW = calendarEntriesPollingWindow;
	}

	public static CalendarEntryService getCalendarEntryService() {
		return CALENDAR_ENTRY_SERVICE;
	}

	public static void setCalendarEntryService(CalendarEntryService calendarEntryService) {
		CALENDAR_ENTRY_SERVICE = calendarEntryService;
	}

	public static KPMENotificationService getKpmeNotificationService() {
		return KPME_NOTIFICATION_SERVICE;
	}

	public static void setKpmeNotificationService(KPMENotificationService kpmeNotificationService) {
		KPME_NOTIFICATION_SERVICE = kpmeNotificationService;
	}

	public static LeaveCalendarDocumentHeaderService getLeaveCalendarDocumentHeaderService() {
		return LEAVE_CALENDAR_DOCUMENT_HEADER_SERVICE;
	}

	public static void setLeaveCalendarDocumentHeaderService(LeaveCalendarDocumentHeaderService leaveCalendarDocumentHeaderService) {
		LEAVE_CALENDAR_DOCUMENT_HEADER_SERVICE = leaveCalendarDocumentHeaderService;
	}

	public static PrincipalHRAttributesService getPrincipalHRAttributesService() {
		return PRINCIPAL_HR_ATTRIBUTES_SERVICE;
	}

	public static void setPrincipalHRAttributesService(PrincipalHRAttributesService principalHRAttributesService) {
		PRINCIPAL_HR_ATTRIBUTES_SERVICE = principalHRAttributesService;
	}
	
}