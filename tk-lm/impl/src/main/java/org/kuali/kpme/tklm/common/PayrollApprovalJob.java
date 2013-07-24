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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.batch.BatchJobUtil;
import org.kuali.kpme.core.calendar.Calendar;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kew.actionitem.ActionItemActionListExtension;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

public class PayrollApprovalJob implements Job {

	private static final Logger LOG = Logger.getLogger(PayrollApprovalJob.class);

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String batchUserPrincipalId = getBatchUserPrincipalId();
        
		if (batchUserPrincipalId != null) {
			JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
	
			String hrCalendarEntryId = jobDataMap.getString("hrCalendarEntryId");
			String documentId = jobDataMap.getString("documentId");
	
			CalendarEntry calendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarEntry(hrCalendarEntryId);
			Calendar calendar = HrServiceLocator.getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
			
			List<RoleMember> roleMembers = new ArrayList<RoleMember>();
			String subject = new String();
			List<Long> workAreas = new ArrayList<Long>();

			if (StringUtils.equals(calendar.getCalendarTypes(), "Pay")) {
				TimesheetDocumentHeader timesheetDocumentHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
				if (timesheetDocumentHeader != null) {
					TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
					if (!TkServiceLocator.getTimesheetService().isReadyToApprove(timesheetDocument) || documentNotEnroute(documentId)) {
						rescheduleJob(context);
					} else {
						TkServiceLocator.getTimesheetService().approveTimesheet(batchUserPrincipalId, timesheetDocument, HrConstants.BATCH_JOB_ACTIONS.BATCH_JOB_APPROVE);
						roleMembers = getRoleMembersInDepartment(timesheetDocument.getAssignments());
						subject = "Timesheet Document " + documentId;
					}
				}
			} else if (StringUtils.equals(calendar.getCalendarTypes(), "Leave")) {
				LeaveCalendarDocumentHeader leaveCalendarDocumentHeader = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(documentId);
				if (leaveCalendarDocumentHeader != null) {
					LeaveCalendarDocument leaveCalendarDocument = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);
					if (!LmServiceLocator.getLeaveCalendarService().isReadyToApprove(leaveCalendarDocument) || documentNotEnroute(documentId)) {
						rescheduleJob(context);
					} else {
						LmServiceLocator.getLeaveCalendarService().approveLeaveCalendar(batchUserPrincipalId, leaveCalendarDocument, HrConstants.BATCH_JOB_ACTIONS.BATCH_JOB_APPROVE);
						roleMembers = getRoleMembersInDepartment(leaveCalendarDocument.getAssignments());
						subject = "Leave Calendar Document " + documentId;
					}
				}
			}
			sendNotifications(subject, roleMembers, workAreas);
        } else {
        	String principalName = ConfigContext.getCurrentContextConfig().getProperty(TkConstants.BATCH_USER_PRINCIPAL_NAME);
        	LOG.error("Could not run batch jobs due to missing batch user " + principalName);
        }
	}
	
    private List<RoleMember> getRoleMembersInDepartment(
			List<Assignment> assignments) {
		Set<String> departments = new HashSet<String>();
		List<RoleMember> roleMembers = new ArrayList<RoleMember>();
		for(Assignment assignment : assignments) {
			departments.add(assignment.getDept());
		}
		for(String dept : departments) {
			roleMembers = HrServiceLocator.getKPMERoleService().getRoleMembersInDepartment(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR.getRoleName(), dept, LocalDate.now().toDateTime(LocalTime.now()), true);
		}
		return roleMembers;
	}

	private void sendNotifications(String subject, List<RoleMember> roleMembers, List<Long> workAreas) {
		String message = new String("FYI, the document listed in the subject of this email has been batch approved by an automated Payroll approval job");
		//roleMembers should be non empty only if approval of leave calendar or timesheet has occured.
		List<String> roleMemberIdList = new ArrayList<String>();
		for(RoleMember roleMember : roleMembers) {
			roleMemberIdList.add(roleMember.getMemberId());
		}
		String [] roleMemberIds = new String [roleMemberIdList.size()];
		HrServiceLocator.getKPMENotificationService().sendNotification(subject, message, roleMemberIdList.toArray(roleMemberIds));
	}

	private String getBatchUserPrincipalId() {
    	String principalName = ConfigContext.getCurrentContextConfig().getProperty(TkConstants.BATCH_USER_PRINCIPAL_NAME);
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(principalName);
        return principal == null ? null : principal.getPrincipalId();
    }
	
	private boolean documentNotEnroute(String documentId) {
		//TODO: Determine if the document has been approved by the "work area"
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
			String newTriggerName = BatchJobUtil.getTriggerName(PayrollApprovalJob.class, newStartTime);
			Trigger newTrigger = new SimpleTrigger(newTriggerName, oldTrigger.getGroup(), newStartTime.toDate());
			newTrigger.setJobName(oldTrigger.getJobName());
			newTrigger.setJobGroup(oldTrigger.getJobGroup());
			
			LOG.info("Rescheduing " + newTrigger.getFullJobName() + " to be run on " + newTrigger.getStartTime());
			
			scheduler.rescheduleJob(oldTrigger.getName(), oldTrigger.getGroup(), newTrigger);
		} catch (SchedulerException se) {
			LOG.error("Failure to execute job due to SchedulerException", se);
//			throw new JobExecutionException(se);
		}
	}

}
