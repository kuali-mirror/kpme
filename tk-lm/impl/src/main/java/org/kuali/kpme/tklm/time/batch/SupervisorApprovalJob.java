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
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.kuali.kpme.core.api.calendar.Calendar;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.batch.BatchJob;
import org.kuali.kpme.core.batch.BatchJobUtil;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunchDocument;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.action.ActionRequest;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.quartz.*;

import java.util.List;

public class SupervisorApprovalJob extends BatchJob {
	
	private static final Logger LOG = Logger.getLogger(SupervisorApprovalJob.class);
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String batchUserPrincipalId = getBatchUserPrincipalId();
        
		if (batchUserPrincipalId != null) {
			JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
	
			String hrCalendarEntryId = jobDataMap.getString("hrCalendarEntryId");
	
			CalendarEntry calendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarEntry(hrCalendarEntryId);
			Calendar calendar = HrServiceLocator.getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
			DateTime beginDate = calendarEntry.getBeginPeriodFullDateTime();
	    	DateTime endDate = calendarEntry.getEndPeriodFullDateTime();
	    	
	    	// used to flag if any document has be routed by this batch job. If true, 
			// we need to reschedule this job since the newly routed documents are not approved by this job, we need a new job to approve them
	    	boolean needToReschedule = false;
			if (StringUtils.equals(calendar.getCalendarTypes(), "Pay")) {	
				List<TimesheetDocumentHeader> timesheetDocumentHeaders = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeaders(beginDate, endDate);
		        for (TimesheetDocumentHeader timesheetDocumentHeader : timesheetDocumentHeaders) {
		        	if (timesheetDocumentHeader != null) {
		        		String docId = timesheetDocumentHeader.getDocumentId();
			        	TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(docId);
						String documentStatus = KEWServiceLocator.getRouteHeaderService().getDocumentStatus(docId);
						
						// only approve documents in enroute status
		        		if(documentStatus.equals(DocumentStatus.ENROUTE.getCode())) {
							PrincipalHRAttributes phraRecord = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(timesheetDocument.getPrincipalId(), endDate.toLocalDate());
							if(phraRecord != null && StringUtils.isNotBlank(phraRecord.getPayCalendar()) && phraRecord.getPayCalendar().equals(calendar.getCalendarName())) {
								// before approve the enroute timesheet doc, we need to find all enroute missed punch docs associated with this timesheet and approve them first
								List<MissedPunchDocument> missedPunchDocuments = TkServiceLocator.getMissedPunchDocumentService().getMissedPunchDocumentsByTimesheetDocumentId(docId);
								for (MissedPunchDocument missedPunchDocument : missedPunchDocuments) {
									if(missedPunchDocument != null 
											&& DocumentStatus.ENROUTE.equals(KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(missedPunchDocument.getDocumentNumber())) ){
										TkServiceLocator.getMissedPunchDocumentService().approveMissedPunchDocument(missedPunchDocument);
									}
									
									TkServiceLocator.getTimesheetService().approveTimesheet(batchUserPrincipalId, timesheetDocument, HrConstants.BATCH_JOB_ACTIONS.BATCH_JOB_APPROVE);
								}
								// find all request actions and approve the document as the users with the request action
								List<ActionRequest> requestList = KewApiServiceLocator.getWorkflowDocumentService().getPendingActionRequests(docId);
								for(ActionRequest aRequest : requestList) {
									if(aRequest.getActionRequested() != null 
											&& aRequest.getActionRequested().getCode().equals(KewApiConstants.ACTION_REQUEST_APPROVE_REQ)
											&& StringUtils.isNotBlank(aRequest.getPrincipalId())
											&& StringUtils.equals(aRequest.getQualifiedRoleNameLabel(), KPMERole.APPROVER.getRoleName())) {
										TkServiceLocator.getTimesheetService().approveTimesheet(aRequest.getPrincipalId(), timesheetDocument, HrConstants.BATCH_JOB_ACTIONS.BATCH_JOB_APPROVE);
									}
								}
							}
		        		} else if(documentStatus.equals(DocumentStatus.INITIATED.getCode()) || documentStatus.equals(DocumentStatus.SAVED.getCode())) {
		        			// if there are documents still not submitted by the time supervisor approval batch job runs, we need to route the document, then reschedule the supervisor job
		        			String principalId = timesheetDocument.getPrincipalId();
							PrincipalHRAttributes phraRecord = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, endDate.toLocalDate());
							if(phraRecord != null && phraRecord.getPayCalendar().equals(calendar.getCalendarName())) {		
								TkServiceLocator.getTimesheetService().routeTimesheet(batchUserPrincipalId, timesheetDocument, HrConstants.BATCH_JOB_ACTIONS.BATCH_JOB_ROUTE);
								needToReschedule = true;
				            }
		        		}
		        	}
		        }
			} else if (StringUtils.equals(calendar.getCalendarTypes(), "Leave")) {
		        List<LeaveCalendarDocumentHeader> leaveCalendarDocumentHeaders = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeaders(beginDate, endDate);
		        for (LeaveCalendarDocumentHeader leaveCalendarDocumentHeader : leaveCalendarDocumentHeaders) {
		        	if (leaveCalendarDocumentHeader != null) {
		        		LeaveCalendarDocument leaveCalendarDocument = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(leaveCalendarDocumentHeader.getDocumentId());
						String documentStatus = KEWServiceLocator.getRouteHeaderService().getDocumentStatus(leaveCalendarDocument.getDocumentId());
						// only approve documents in enroute status
						if (documentStatus.equals(DocumentStatus.ENROUTE.getCode())) {
							PrincipalHRAttributes phraRecord = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(leaveCalendarDocument.getPrincipalId(), endDate.toLocalDate());
							if(phraRecord != null && StringUtils.isNotBlank(phraRecord.getLeaveCalendar()) && phraRecord.getLeaveCalendar().equals(calendar.getCalendarName())) {
								// find all request actions and approve the document as the users with the request action
								List<ActionRequest> requestList = KewApiServiceLocator.getWorkflowDocumentService().getPendingActionRequests(leaveCalendarDocument.getDocumentId());
								for(ActionRequest aRequest : requestList) {
									if(aRequest.getActionRequested() != null 
											&& aRequest.getActionRequested().getCode().equals(KewApiConstants.ACTION_REQUEST_APPROVE_REQ)
											&& StringUtils.isNotBlank(aRequest.getPrincipalId())
											&& StringUtils.equals(aRequest.getQualifiedRoleNameLabel(), KPMERole.APPROVER.getRoleName())) {
										LmServiceLocator.getLeaveCalendarService().approveLeaveCalendar(aRequest.getPrincipalId(), leaveCalendarDocument, HrConstants.BATCH_JOB_ACTIONS.BATCH_JOB_APPROVE);
									}
								}
							}
						} else if(documentStatus.equals(DocumentStatus.INITIATED.getCode()) || documentStatus.equals(DocumentStatus.SAVED.getCode())) {
							String principalId = leaveCalendarDocument.getPrincipalId();
							PrincipalHRAttributes phraRecord = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, endDate.toLocalDate());
							if(phraRecord != null && phraRecord.getLeaveCalendar().equals(calendar.getCalendarName())) {	
								LmServiceLocator.getLeaveCalendarService().routeLeaveCalendar(batchUserPrincipalId, leaveCalendarDocument, HrConstants.BATCH_JOB_ACTIONS.BATCH_JOB_ROUTE);
								needToReschedule = true;
							}
						}
					}
		        }
	    	
			}
			
			if(needToReschedule) {
				rescheduleJob(context);
			}
			
        } else {
        	String principalName = getBatchUserPrincipalName();
        	LOG.error("Could not run batch jobs due to missing batch user " + principalName);
        }
	}
	
	private boolean missedPunchDocumentsEnroute(String documentId) {
		boolean missedPunchDocumentsEnroute = false;
		
		List<MissedPunchDocument> missedPunchDocuments = TkServiceLocator.getMissedPunchDocumentService().getMissedPunchDocumentsByTimesheetDocumentId(documentId);
		for (MissedPunchDocument missedPunchDocument : missedPunchDocuments) {
			DocumentStatus documentStatus = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(missedPunchDocument.getDocumentNumber());
			if (DocumentStatus.ENROUTE.equals(documentStatus)) {
				missedPunchDocumentsEnroute = true;
				break;
			}
		}
		
		return missedPunchDocumentsEnroute;
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
			LOG.error("Failure to execute job due to SchedulerException", se);
//			throw new JobExecutionException(se);
		}
	}
	
}
