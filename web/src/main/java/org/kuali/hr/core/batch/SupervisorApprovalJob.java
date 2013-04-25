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
package org.kuali.hr.core.batch;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.kuali.hr.core.calendar.Calendar;
import org.kuali.hr.core.calendar.CalendarEntry;
import org.kuali.hr.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.hr.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.tklm.time.missedpunch.MissedPunchDocument;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.timesheet.TimesheetDocument;
import org.kuali.hr.tklm.time.util.TkConstants;
import org.kuali.hr.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kew.actionitem.ActionItemActionListExtension;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.quartz.*;

import java.util.Collection;
import java.util.List;

public class SupervisorApprovalJob implements Job {
	
	private static final Logger LOG = Logger.getLogger(SupervisorApprovalJob.class);
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String batchUserPrincipalId = getBatchUserPrincipalId();
        
		if (batchUserPrincipalId != null) {
			JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
	
			String hrCalendarEntryId = jobDataMap.getString("hrCalendarEntryId");
			String documentId = jobDataMap.getString("documentId");
	
			CalendarEntry calendarEntry = TkServiceLocator.getCalendarEntryService().getCalendarEntry(hrCalendarEntryId);
			Calendar calendar = TkServiceLocator.getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
					
			if (StringUtils.equals(calendar.getCalendarTypes(), "Pay")) {
				TimesheetDocumentHeader timesheetDocumentHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
				if (timesheetDocumentHeader != null) {
					if (missedPunchDocumentsNotFinal(documentId) || documentNotEnroute(documentId)) {
						rescheduleJob(context);
					} else {
						TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
						TkServiceLocator.getTimesheetService().approveTimesheet(batchUserPrincipalId, timesheetDocument, TkConstants.BATCH_JOB_ACTIONS.BATCH_JOB_APPROVE);
					}
				}
			} else if (StringUtils.equals(calendar.getCalendarTypes(), "Leave")) {
				LeaveCalendarDocumentHeader leaveCalendarDocumentHeader = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(documentId);
				if (leaveCalendarDocumentHeader != null) {
					if (documentNotEnroute(documentId)) {
						rescheduleJob(context);
					} else {
						LeaveCalendarDocument leaveCalendarDocument = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);
						TkServiceLocator.getLeaveCalendarService().approveLeaveCalendar(batchUserPrincipalId, leaveCalendarDocument, TkConstants.BATCH_JOB_ACTIONS.BATCH_JOB_APPROVE);
					}
				}
			}
        } else {
        	String principalName = ConfigContext.getCurrentContextConfig().getProperty(TkConstants.BATCH_USER_PRINCIPAL_NAME);
        	LOG.error("Could not run batch jobs due to missing batch user " + principalName);
        }
	}
	
    private String getBatchUserPrincipalId() {
    	String principalName = ConfigContext.getCurrentContextConfig().getProperty(TkConstants.BATCH_USER_PRINCIPAL_NAME);
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(principalName);
        return principal == null ? null : principal.getPrincipalId();
    }
	
	private boolean missedPunchDocumentsNotFinal(String documentId) {
		boolean missedPunchDocumentsNotFinal = false;
		
		List<MissedPunchDocument> missedPunchDocuments = TkServiceLocator.getMissedPunchService().getMissedPunchDocsByTimesheetDocumentId(documentId);
		for (MissedPunchDocument missedPunchDocument : missedPunchDocuments) {
			Collection<ActionItemActionListExtension> actionItems = KEWServiceLocator.getActionListService().getActionListForSingleDocument(missedPunchDocument.getDocumentNumber());
			for (ActionItemActionListExtension actionItem : actionItems) {
				if (!actionItem.getRouteHeader().isFinal()) {
					missedPunchDocumentsNotFinal = true;
					break;
				}
			}
		}
		
		return missedPunchDocumentsNotFinal;
	}
	
	private boolean documentNotEnroute(String documentId) {
		boolean documentNotInAStateToBeApproved = false;
		
		Collection<ActionItemActionListExtension> actionItems = KEWServiceLocator.getActionListService().getActionListForSingleDocument(documentId);
		for (ActionItemActionListExtension actionItem : actionItems) {
			if (!actionItem.getRouteHeader().isEnroute()) {
				documentNotInAStateToBeApproved = true;
				break;
			}
		}
		
		return documentNotInAStateToBeApproved;
	}
	
	private void rescheduleJob(JobExecutionContext context) throws JobExecutionException {
		try {
			Scheduler scheduler = context.getScheduler();
			Trigger oldTrigger = context.getTrigger();
			
			DateTime newStartTime = new DateTime().plusMinutes(5);
			String newTriggerName = BatchJobUtil.getTriggerName(SupervisorApprovalJob.class, newStartTime);
			Trigger newTrigger = new SimpleTrigger(newTriggerName, oldTrigger.getGroup(), newStartTime.toDate());
			newTrigger.setJobName(oldTrigger.getJobName());
			newTrigger.setJobGroup(oldTrigger.getJobGroup());
			
			LOG.info("Rescheduing " + newTrigger.getFullJobName() + " to be run on " + newTrigger.getStartTime());
			
			scheduler.rescheduleJob(oldTrigger.getName(), oldTrigger.getGroup(), newTrigger);
		} catch (SchedulerException se) {
			throw new JobExecutionException(se);
		}
	}
	
}