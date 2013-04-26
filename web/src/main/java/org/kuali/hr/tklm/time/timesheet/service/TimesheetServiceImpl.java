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
package org.kuali.hr.tklm.time.timesheet.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.hr.core.HrConstants;
import org.kuali.hr.core.assignment.Assignment;
import org.kuali.hr.core.calendar.CalendarEntry;
import org.kuali.hr.core.job.Job;
import org.kuali.hr.core.principal.PrincipalHRAttributes;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.common.TKContext;
import org.kuali.hr.tklm.common.TKUtils;
import org.kuali.hr.tklm.common.TkConstants;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.leave.service.base.LmServiceLocator;
import org.kuali.hr.tklm.leave.timeoff.SystemScheduledTimeOff;
import org.kuali.hr.tklm.leave.util.LMConstants;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.timeblock.TimeBlock;
import org.kuali.hr.tklm.time.timesheet.TimesheetDocument;
import org.kuali.hr.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kew.api.note.Note;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

public class TimesheetServiceImpl implements TimesheetService {

    private static final Logger LOG = Logger.getLogger(TimesheetServiceImpl.class);

    @Override
    public void routeTimesheet(String principalId, TimesheetDocument timesheetDocument) {
        routeTimesheet(principalId, timesheetDocument, HrConstants.DOCUMENT_ACTIONS.ROUTE);
    }

    @Override
    public void routeTimesheet(String principalId, TimesheetDocument timesheetDocument, String action) {
        timesheetAction(action, principalId, timesheetDocument);
    }

    @Override
    public void approveTimesheet(String principalId, TimesheetDocument timesheetDocument) {
        timesheetAction(HrConstants.DOCUMENT_ACTIONS.APPROVE, principalId, timesheetDocument);
    }

    @Override
    public void approveTimesheet(String principalId, TimesheetDocument timesheetDocument, String action) {
        timesheetAction(action, principalId, timesheetDocument);
    }

    @Override
    public void disapproveTimesheet(String principalId, TimesheetDocument timesheetDocument) {
        timesheetAction(HrConstants.DOCUMENT_ACTIONS.DISAPPROVE, principalId, timesheetDocument);
    }

    protected void timesheetAction(String action, String principalId, TimesheetDocument timesheetDocument) {
        WorkflowDocument wd = null;
        if (timesheetDocument != null) {
            String rhid = timesheetDocument.getDocumentId();
            wd = WorkflowDocumentFactory.loadDocument(principalId, rhid);

            if (StringUtils.equals(action, HrConstants.DOCUMENT_ACTIONS.ROUTE)) {
                wd.route("Routing for Approval");
            } else if (StringUtils.equals(action, HrConstants.BATCH_JOB_ACTIONS.BATCH_JOB_ROUTE)) {
            	Note.Builder builder = Note.Builder.create(rhid, principalId);
                builder.setCreateDate(new DateTime());
                builder.setText("Routed via Employee Approval batch job");
            	KewApiServiceLocator.getNoteService().createNote(builder.build());
            	
            	wd.route("Batch job routing timesheet");
            } else if (StringUtils.equals(action, HrConstants.DOCUMENT_ACTIONS.APPROVE)) {
                if (TkServiceLocator.getTKPermissionService().canSuperUserAdministerTimesheet(GlobalVariables.getUserSession().getPrincipalId(), rhid) 
                		&& !TkServiceLocator.getTKPermissionService().canApproveTimesheet(GlobalVariables.getUserSession().getPrincipalId(), rhid)) {
                    wd.superUserBlanketApprove("Superuser approving timesheet.");
                } else {
                    wd.approve("Approving timesheet.");
                }
            } else if (StringUtils.equals(action, HrConstants.BATCH_JOB_ACTIONS.BATCH_JOB_APPROVE)) {
            	Note.Builder builder = Note.Builder.create(rhid, principalId);
           	 	builder.setCreateDate(new DateTime());
           	 	builder.setText("Approved via Supervisor Approval batch job");
           	 	KewApiServiceLocator.getNoteService().createNote(builder.build());
            	
            	wd.superUserBlanketApprove("Batch job approving timesheet.");
            } else if (StringUtils.equals(action, HrConstants.DOCUMENT_ACTIONS.DISAPPROVE)) {
                if (TkServiceLocator.getTKPermissionService().canSuperUserAdministerTimesheet(GlobalVariables.getUserSession().getPrincipalId(), rhid) 
                		&& !TkServiceLocator.getTKPermissionService().canApproveTimesheet(GlobalVariables.getUserSession().getPrincipalId(), rhid)) {
                    wd.superUserDisapprove("Superuser disapproving timesheet.");
                } else {
                    wd.disapprove("Disapproving timesheet.");
                }
            }
        }
    }

    @Override
    public TimesheetDocument openTimesheetDocument(String principalId, CalendarEntry calendarDates) throws WorkflowException {
        TimesheetDocument timesheetDocument = null;

        DateTime begin = calendarDates.getBeginPeriodFullDateTime();
        DateTime end = calendarDates.getEndPeriodFullDateTime();

        TimesheetDocumentHeader header = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, begin, end);

        if (header == null) {
            List<Assignment> activeAssignments = HrServiceLocator.getAssignmentService().getAssignmentsByCalEntryForTimeCalendar(principalId, calendarDates);
            //HrServiceLocator.getAssignmentService().getAssignments(principalId, TKUtils.getTimelessDate(payCalendarDates.getEndPeriodDate()));
            if (activeAssignments.size() == 0) {
                LOG.warn("No active assignments for " + principalId + " for " + calendarDates.getEndPeriodDate());
                return null;
                //throw new RuntimeException("No active assignments for " + principalId + " for " + calendarDates.getEndPeriodDate());
            }
            
            EntityNamePrincipalName person = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(principalId);
            String principalName = person != null && person.getDefaultName() != null ? person.getDefaultName().getCompositeName() : StringUtils.EMPTY;
            String endDateString = TKUtils.formatDate(end.toLocalDate());
            String timesheetDocumentTitle = TimesheetDocument.TIMESHEET_DOCUMENT_TYPE + " - " + principalName + " (" + principalId + ") - " + endDateString;
            
            timesheetDocument = this.initiateWorkflowDocument(principalId, begin, end, calendarDates, TimesheetDocument.TIMESHEET_DOCUMENT_TYPE, timesheetDocumentTitle);
            //timesheetDocument.setPayCalendarEntry(calendarDates);
            //this.loadTimesheetDocumentData(timesheetDocument, principalId, calendarDates);
            //TODO switch this to scheduled time offs
            //this.loadHolidaysOnTimesheet(timesheetDocument, principalId, begin, end);
        } else {
            timesheetDocument = this.getTimesheetDocument(header.getDocumentId());
            timesheetDocument.setCalendarEntry(calendarDates);
        }

        timesheetDocument.setTimeSummary(TkServiceLocator.getTimeSummaryService().getTimeSummary(timesheetDocument));
        return timesheetDocument;
    }

    public void loadHolidaysOnTimesheet(TimesheetDocument timesheetDocument, String principalId, LocalDate beginDate, LocalDate endDate) {
        PrincipalHRAttributes principalCalendar = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, beginDate);
        if (principalCalendar != null && StringUtils.isNotEmpty(principalCalendar.getLeavePlan())) {
        	List<SystemScheduledTimeOff> sstoList = LmServiceLocator.getSysSchTimeOffService()
        		.getSystemScheduledTimeOffForPayPeriod(principalCalendar.getLeavePlan(), beginDate, endDate);
        	Assignment sstoAssign = HrServiceLocator.getAssignmentService().getAssignmentToApplyScheduledTimeOff(timesheetDocument, endDate);
        	if (sstoAssign != null) {
        		for(SystemScheduledTimeOff ssto : sstoList) {
                  BigDecimal sstoCalcHours = LmServiceLocator.getSysSchTimeOffService().calculateSysSchTimeOffHours(sstoAssign.getJob(), ssto.getAmountofTime());
                  TimeBlock timeBlock = TkServiceLocator.getTimeBlockService().createTimeBlock(timesheetDocument, new Timestamp(ssto.getScheduledTimeOffDate().getTime()),
                          new Timestamp(ssto.getScheduledTimeOffDate().getTime()), sstoAssign, TkConstants.HOLIDAY_EARN_CODE, sstoCalcHours, BigDecimal.ZERO, false, false, TKContext.getPrincipalId());
                  timesheetDocument.getTimeBlocks().add(timeBlock);
              }
	            //If system scheduled time off are loaded will need to save them to the database
		        if (CollectionUtils.isNotEmpty(sstoList)) {
		           TkServiceLocator.getTimeBlockService().saveTimeBlocks(new LinkedList<TimeBlock>(), timesheetDocument.getTimeBlocks(), TKContext.getPrincipalId());
		        }
        	}
        }
    }

    protected TimesheetDocument initiateWorkflowDocument(String principalId, DateTime payBeginDate,  DateTime payEndDate, CalendarEntry calendarEntry, String documentType, String title) throws WorkflowException {
        TimesheetDocument timesheetDocument = null;
        WorkflowDocument workflowDocument = null;

        workflowDocument = WorkflowDocumentFactory.createDocument(principalId, documentType, title);

        String status = workflowDocument.getStatus().getCode();
        TimesheetDocumentHeader documentHeader = new TimesheetDocumentHeader(workflowDocument.getDocumentId(), principalId, payBeginDate.toDate(), payEndDate.toDate(), status);

        documentHeader.setDocumentId(workflowDocument.getDocumentId().toString());
        documentHeader.setDocumentStatus("I");

        TkServiceLocator.getTimesheetDocumentHeaderService().saveOrUpdate(documentHeader);
        timesheetDocument = new TimesheetDocument(documentHeader);
        timesheetDocument.setCalendarEntry(calendarEntry);
        loadTimesheetDocumentData(timesheetDocument, principalId, calendarEntry);
        TkServiceLocator.getTkSearchableAttributeService().updateSearchableAttribute(timesheetDocument, payEndDate.toLocalDate());

        if (LmServiceLocator.getLeaveApprovalService().isActiveAssignmentFoundOnJobFlsaStatus(principalId, TkConstants.FLSA_STATUS_NON_EXEMPT, true)) {
        	deleteNonApprovedLeaveBlocks(principalId, calendarEntry.getBeginPeriodFullDateTime().toLocalDate(), calendarEntry.getEndPeriodFullDateTime().toLocalDate());
        }
        
        return timesheetDocument;
    }
    
    private void deleteNonApprovedLeaveBlocks(String principalId, LocalDate beginDate, LocalDate endDate) {
    	String batchUserPrincipalId = getBatchUserPrincipalId();
        
        if (batchUserPrincipalId != null) {
	    	List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, beginDate, endDate);
	
	    	for (LeaveBlock leaveBlock : leaveBlocks) {
	    		if (!StringUtils.equals(leaveBlock.getRequestStatus(), LMConstants.REQUEST_STATUS.APPROVED)) {
	    			LmServiceLocator.getLeaveBlockService().deleteLeaveBlock(leaveBlock.getLmLeaveBlockId(), batchUserPrincipalId);
	    		}
	    	}
        } else {
        	String principalName = ConfigContext.getCurrentContextConfig().getProperty(TkConstants.BATCH_USER_PRINCIPAL_NAME);
        	LOG.error("Could not delete leave request blocks due to missing batch user " + principalName);
        }
    }
    
    private String getBatchUserPrincipalId() {
    	String principalName = ConfigContext.getCurrentContextConfig().getProperty(TkConstants.BATCH_USER_PRINCIPAL_NAME);
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(principalName);
        return principal == null ? null : principal.getPrincipalId();
    }

    public List<TimeBlock> getPrevDocumentTimeBlocks(String principalId, DateTime payBeginDate) {
        TimesheetDocumentHeader prevTdh = TkServiceLocator.getTimesheetDocumentHeaderService().getPreviousDocumentHeader(principalId, payBeginDate);
        if (prevTdh == null) {
            return new ArrayList<TimeBlock>();
        }
        return TkServiceLocator.getTimeBlockService().getTimeBlocks(prevTdh.getDocumentId());
    }

    @Override
    public TimesheetDocument getTimesheetDocument(String documentId) {
        TimesheetDocument timesheetDocument = null;
        TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);

        if (tdh != null) {
            timesheetDocument = new TimesheetDocument(tdh);
            CalendarEntry pce = HrServiceLocator.getCalendarService().getCalendarDatesByPayEndDate(tdh.getPrincipalId(), new DateTime(tdh.getEndDate()), TkConstants.PAY_CALENDAR_TYPE);
            loadTimesheetDocumentData(timesheetDocument, tdh.getPrincipalId(), pce);

            timesheetDocument.setCalendarEntry(pce);
        } else {
            throw new RuntimeException("Could not find TimesheetDocumentHeader for DocumentID: " + documentId);
        }
        return timesheetDocument;
    }

    protected void loadTimesheetDocumentData(TimesheetDocument tdoc, String principalId, CalendarEntry payCalEntry) {
        List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignmentsByCalEntryForTimeCalendar(principalId, payCalEntry);
        List<Job> jobs = HrServiceLocator.getJobService().getJobs(principalId, payCalEntry.getEndPeriodFullDateTime().toLocalDate());
        List<TimeBlock> timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocks(tdoc.getDocumentHeader().getDocumentId());

        tdoc.setAssignments(assignments);
        tdoc.setJobs(jobs);
        tdoc.setTimeBlocks(timeBlocks);
    }

    public boolean isSynchronousUser() {
        List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignments(TKContext.getTargetPrincipalId(), LocalDate.now());
        boolean isSynchronousUser = true;
        for (Assignment assignment : assignments) {
            isSynchronousUser &= assignment.isSynchronous();
        }
        return isSynchronousUser;
    }

    //this is an admin function used for testing
    public void deleteTimesheet(String documentId) {
        TkServiceLocator.getTimeBlockService().deleteTimeBlocksAssociatedWithDocumentId(documentId);
        TkServiceLocator.getTimesheetDocumentHeaderService().deleteTimesheetHeader(documentId);
    }

    public TimeBlock resetWorkedHours(TimeBlock timeBlock) {
        if (timeBlock.getBeginTime() != null && timeBlock.getEndTime() != null && StringUtils.equals(timeBlock.getEarnCodeType(), TkConstants.EARN_CODE_TIME)) {
            BigDecimal hours = TKUtils.getHoursBetween(timeBlock.getBeginTime().getTime(), timeBlock.getEndTime().getTime());
            timeBlock.setHours(hours);
        }
        return timeBlock;
    }

    @Override
    public void resetTimeBlock(List<TimeBlock> timeBlocks) {
        for (TimeBlock tb : timeBlocks) {
            resetWorkedHours(tb);
        }
        TkServiceLocator.getTimeBlockService().resetTimeHourDetail(timeBlocks);
    }

	@Override
	public boolean isReadyToApprove(TimesheetDocument document) {
        if (document == null) {
            return false;
        }
        List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksWithType(document.getPrincipalId(),
        		document.getCalendarEntry().getBeginPeriodFullDateTime().toLocalDate(), document.getCalendarEntry().getEndPeriodFullDateTime().toLocalDate(), LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER);
        leaveBlocks.addAll(LmServiceLocator.getLeaveBlockService().getLeaveBlocksWithType(document.getPrincipalId(),
        		document.getCalendarEntry().getBeginPeriodFullDateTime().toLocalDate(), document.getCalendarEntry().getEndPeriodFullDateTime().toLocalDate(), LMConstants.LEAVE_BLOCK_TYPE.LEAVE_PAYOUT));
        for(LeaveBlock lb : leaveBlocks) {
        	if(!StringUtils.equals(lb.getRequestStatus(),LMConstants.REQUEST_STATUS.APPROVED) &&
        			!StringUtils.equals(lb.getRequestStatus(), LMConstants.REQUEST_STATUS.DISAPPROVED))
        		return false;
        }
        return true;
/*        List<BalanceTransfer> balanceTransfers = LmServiceLocator.getBalanceTransferService().getBalanceTransfers(document.getPrincipalId(),
                document.getCalendarEntry().getBeginPeriodDate(),
                document.getCalendarEntry().getEndPeriodDate());
        if (!CollectionUtils.isEmpty(balanceTransfers))   {
	        for(BalanceTransfer balanceTransfer : balanceTransfers) {
	        	if(StringUtils.equals(HrConstants.DOCUMENT_STATUS.get(balanceTransfer.getStatus()), HrConstants.ROUTE_STATUS.ENROUTE))
	        		return false;
	            if (!StringUtils.equals(LMConstants.REQUEST_STATUS.APPROVED, balanceTransfer.getStatus())
	                    && !StringUtils.equals(LMConstants.REQUEST_STATUS.DISAPPROVED, balanceTransfer.getStatus())) {
	                return false;
	            }
	        }
        }
        List<LeavePayout> leavePayouts = LmServiceLocator.getLeavePayoutService().getLeavePayouts(document.getPrincipalId(),
        		document.getCalendarEntry().getBeginPeriodDate(),
        		document.getCalendarEntry().getEndPeriodDate());
        if (!CollectionUtils.isEmpty(leavePayouts)) {
        	for(LeavePayout payout : leavePayouts) {
	        	if(StringUtils.equals(HrConstants.DOCUMENT_STATUS.get(payout.getStatus()), HrConstants.ROUTE_STATUS.ENROUTE))
	        		return false;
	            if (!StringUtils.equals(LMConstants.REQUEST_STATUS.APPROVED, payout.getStatus())
	                    && !StringUtils.equals(LMConstants.REQUEST_STATUS.DISAPPROVED, payout.getStatus())) {
	                return false;
	            }
        	}
        }
        return true;*/
	}

}