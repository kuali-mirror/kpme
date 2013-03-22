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
package org.kuali.hr.lm.leavecalendar.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.leavecalendar.dao.LeaveCalendarDao;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.lm.workflow.LeaveRequestDocument;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntry;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kew.api.note.Note;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class LeaveCalendarServiceImpl implements LeaveCalendarService {
	
	private static final Logger LOG = Logger.getLogger(LeaveCalendarServiceImpl.class);

    private LeaveCalendarDao leaveCalendarDao;

    @Override
    public LeaveCalendarDocument getLeaveCalendarDocument(String documentId) {
        LeaveCalendarDocument lcd = null;
        LeaveCalendarDocumentHeader lcdh = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(documentId);

        if (lcdh != null) {
            lcd = new LeaveCalendarDocument(lcdh);
            CalendarEntry pce = TkServiceLocator.getCalendarService().getCalendarDatesByPayEndDate(lcdh.getPrincipalId(), lcdh.getEndDate(), LMConstants.LEAVE_CALENDAR_TYPE);
            lcd.setCalendarEntry(pce);
        } else {
            throw new RuntimeException("Could not find LeaveCalendarDocumentHeader for DocumentID: " + documentId);
        }

        List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForDocumentId(documentId);
        lcd.setLeaveBlocks(leaveBlocks);

        // Fetching assignments
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignmentsByCalEntryForLeaveCalendar(lcdh.getPrincipalId(), lcd.getCalendarEntry());
        lcd.setAssignments(assignments);
        
        return lcd;
    }

    @Override
    public LeaveCalendarDocument openLeaveCalendarDocument(String principalId, CalendarEntry calEntry) throws WorkflowException {
        LeaveCalendarDocument doc;

        Date begin = calEntry.getBeginPeriodDateTime();
        Date end = calEntry.getEndPeriodDateTime();

        LeaveCalendarDocumentHeader header = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(principalId, begin, end);
        if (header == null) {
            EntityNamePrincipalName person = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(principalId);
            String principalName = person != null && person.getDefaultName() != null ? person.getDefaultName().getCompositeName() : StringUtils.EMPTY;
            String beginDateString = TKUtils.formatDate(new java.sql.Date(begin.getTime()));
            String endDateString = TKUtils.formatDate(new java.sql.Date(end.getTime()));
            String leaveCalendarDocumentTitle = LeaveCalendarDocument.LEAVE_CALENDAR_DOCUMENT_TYPE + " - " + principalName + " (" + principalId + ") - " + beginDateString + "-" + endDateString;
            
            doc = initiateWorkflowDocument(principalId, begin, end, calEntry, LeaveCalendarDocument.LEAVE_CALENDAR_DOCUMENT_TYPE, leaveCalendarDocumentTitle);
        } else {
            doc = getLeaveCalendarDocument(header.getDocumentId());
        }
        doc.setCalendarEntry(calEntry);
        // TODO: need to set the summary
        return doc;
    }
    
    //Should only create leave calendar document if active jobs were found with flsa elig = no and ben elg = yes
    public boolean shouldCreateLeaveDocument(String principalId, CalendarEntry calEntry){
        if (StringUtils.isEmpty(principalId) || calEntry == null) {
            return false;
        }
        
        boolean isPlanningCalendar = TkServiceLocator.getLeaveCalendarService().isLeavePlanningCalendar(principalId, calEntry.getBeginPeriodDateTime(), calEntry.getEndPeriodDateTime());
    	if (isPlanningCalendar) {
    		return false;
    	}
        
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignmentsByPayEntry(principalId, calEntry);
    	List<Assignment> results = TkServiceLocator.getAssignmentService().filterAssignments(assignments, TkConstants.FLSA_STATUS_EXEMPT, true);
    	return CollectionUtils.isNotEmpty(results);
    }
    
    protected LeaveCalendarDocument initiateWorkflowDocument(String principalId, Date payBeginDate, Date payEndDate, CalendarEntry calendarEntry, String documentType, String title) throws WorkflowException {
        LeaveCalendarDocument leaveCalendarDocument = null;
        WorkflowDocument workflowDocument = null;

        workflowDocument =  WorkflowDocumentFactory.createDocument(principalId, documentType, title);

        String status = workflowDocument.getStatus().getCode();
        LeaveCalendarDocumentHeader documentHeader = new LeaveCalendarDocumentHeader(workflowDocument.getDocumentId(), principalId, payBeginDate, payEndDate, status);

        documentHeader.setDocumentId(workflowDocument.getDocumentId());
        documentHeader.setDocumentStatus(TkConstants.ROUTE_STATUS.INITIATED);

        KRADServiceLocator.getBusinessObjectService().save(documentHeader);
        
        leaveCalendarDocument = new LeaveCalendarDocument(documentHeader);
        leaveCalendarDocument.setCalendarEntry(calendarEntry);
        loadLeaveCalendarDocumentData(leaveCalendarDocument, principalId, calendarEntry);
        TkServiceLocator.getTkSearchableAttributeService().updateSearchableAttribute(leaveCalendarDocument, payEndDate);
        
        updateLeaveBlockDocumentIds(principalId, payBeginDate, payEndDate, workflowDocument.getDocumentId());
        
        updatePlannedLeaveBlocks(principalId, payBeginDate, payEndDate);

        return leaveCalendarDocument;
    }
    
    private void updateLeaveBlockDocumentIds(String principalId, Date beginDate, Date endDate, String documentId) {
        List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, beginDate, endDate);
        
        for (LeaveBlock leaveBlock : leaveBlocks) {
        	leaveBlock.setDocumentId(documentId);
        }
        
        TkServiceLocator.getLeaveBlockService().saveLeaveBlocks(leaveBlocks);
    }
    
    private void updatePlannedLeaveBlocks(String principalId, Date beginDate, Date endDate) {
        String batchUserPrincipalId = getBatchUserPrincipalId();
        
        if (batchUserPrincipalId != null) {
	    	List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, beginDate, endDate);
	
	    	for (LeaveBlock leaveBlock : leaveBlocks) {
	    		if (StringUtils.equals(leaveBlock.getRequestStatus(), LMConstants.REQUEST_STATUS.PLANNED) 
	    				|| StringUtils.equals(leaveBlock.getRequestStatus(), LMConstants.REQUEST_STATUS.DEFERRED)) {
	    			TkServiceLocator.getLeaveBlockService().deleteLeaveBlock(leaveBlock.getLmLeaveBlockId(), batchUserPrincipalId);
	    		} else if (StringUtils.equals(leaveBlock.getRequestStatus(), LMConstants.REQUEST_STATUS.REQUESTED)) {
	    	        if (StringUtils.equals(getInitiateLeaveRequestAction(), LMConstants.INITIATE_LEAVE_REQUEST_ACTION_OPTIONS.DELETE)) {
	    	        	TkServiceLocator.getLeaveBlockService().deleteLeaveBlock(leaveBlock.getLmLeaveBlockId(), batchUserPrincipalId);
	    	        } else if (StringUtils.equals(getInitiateLeaveRequestAction(), LMConstants.INITIATE_LEAVE_REQUEST_ACTION_OPTIONS.APPROVE)) {
	    	        	List<LeaveRequestDocument> leaveRequestDocuments = TkServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocumentsByLeaveBlockId(leaveBlock.getLmLeaveBlockId());
	    	        	for (LeaveRequestDocument leaveRequestDocument : leaveRequestDocuments) {
	    	        		TkServiceLocator.getLeaveRequestDocumentService().suBlanketApproveLeave(leaveRequestDocument.getDocumentNumber(), batchUserPrincipalId);
	    	        	}
	    	        }
	    		}
	    	}
        } else {
        	String principalName = ConfigContext.getCurrentContextConfig().getProperty(TkConstants.BATCH_USER_PRINCIPAL_NAME);
        	LOG.error("Could not update leave request blocks due to missing batch user " + principalName);
        }
    }
    
    private String getBatchUserPrincipalId() {
    	String principalName = ConfigContext.getCurrentContextConfig().getProperty(TkConstants.BATCH_USER_PRINCIPAL_NAME);
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(principalName);
        return principal == null ? null : principal.getPrincipalId();
    }
    
    private String getInitiateLeaveRequestAction() {
    	return ConfigContext.getCurrentContextConfig().getProperty(LMConstants.INITIATE_LEAVE_REQUEST_ACTION);
    }

    /**
     * Preload the document data. It preloads:
     * - LeaveBlocks on the document.
     * @param ldoc
     * @param principalId
     * @param calEntry
     */
    protected void loadLeaveCalendarDocumentData(LeaveCalendarDocument ldoc, String principalId, CalendarEntry calEntry) {
        List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForDocumentId(ldoc.getDocumentId());
        ldoc.setLeaveBlocks(leaveBlocks);
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignmentsByCalEntryForLeaveCalendar(principalId, calEntry);
        ldoc.setAssignments(assignments);
    }

    public LeaveCalendarDao getLeaveCalendarDao() {
        return leaveCalendarDao;
    }

    public void setLeaveCalendarDao(LeaveCalendarDao leaveCalendarDao) {
        this.leaveCalendarDao = leaveCalendarDao;
    }

	@Override
	public LeaveCalendarDocument getLeaveCalendarDocument(
			String principalId, CalendarEntry calendarEntry) {
		LeaveCalendarDocument leaveCalendarDocument = new LeaveCalendarDocument(calendarEntry);
		LeaveCalendarDocumentHeader lcdh = new LeaveCalendarDocumentHeader();
		lcdh.setBeginDate(calendarEntry.getBeginPeriodDateTime());
		lcdh.setEndDate(calendarEntry.getEndPeriodDateTime());
		leaveCalendarDocument.setDocumentHeader(lcdh);
		// Fetching assignments
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignmentsByCalEntryForLeaveCalendar(principalId, calendarEntry);
        leaveCalendarDocument.setAssignments(assignments);
		return leaveCalendarDocument;
	}

    @Override
    public void routeLeaveCalendar(String principalId, LeaveCalendarDocument leaveCalendarDocument) {
        leaveCalendarDocumentAction(TkConstants.DOCUMENT_ACTIONS.ROUTE, principalId, leaveCalendarDocument);
    }
    
    @Override
    public void routeLeaveCalendar(String principalId, LeaveCalendarDocument leaveCalendarDocument, String action) {
        leaveCalendarDocumentAction(action, principalId, leaveCalendarDocument);
    }

    @Override
    public void approveLeaveCalendar(String principalId, LeaveCalendarDocument leaveCalendarDocument) {
        leaveCalendarDocumentAction(TkConstants.DOCUMENT_ACTIONS.APPROVE, principalId, leaveCalendarDocument);
    }
    
    @Override
    public void approveLeaveCalendar(String principalId, LeaveCalendarDocument leaveCalendarDocument, String action) {
        leaveCalendarDocumentAction(action, principalId, leaveCalendarDocument);
    }

    @Override
    public void disapproveLeaveCalendar(String principalId, LeaveCalendarDocument leaveCalendarDocument) {
        leaveCalendarDocumentAction(TkConstants.DOCUMENT_ACTIONS.DISAPPROVE, principalId, leaveCalendarDocument);
    }

    public boolean isReadyToApprove(LeaveCalendarDocument document) {
        if (document == null) {
            return false;
        }
        List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocksWithType(document.getPrincipalId(),
        		document.getCalendarEntry().getBeginPeriodDate(), document.getCalendarEntry().getEndPeriodDate(), LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER);
        leaveBlocks.addAll(TkServiceLocator.getLeaveBlockService().getLeaveBlocksWithType(document.getPrincipalId(),
        		document.getCalendarEntry().getBeginPeriodDate(), document.getCalendarEntry().getEndPeriodDate(), LMConstants.LEAVE_BLOCK_TYPE.LEAVE_PAYOUT));
        for(LeaveBlock lb : leaveBlocks) {
        	if(!StringUtils.equals(lb.getRequestStatus(),LMConstants.REQUEST_STATUS.APPROVED) &&
        			!StringUtils.equals(lb.getRequestStatus(), LMConstants.REQUEST_STATUS.DISAPPROVED))
        		return false;
        }
        // check if there are any pending calendars are there
        LeaveCalendarDocumentHeader lcdh = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getMinBeginDatePendingLeaveCalendar(document.getPrincipalId());
        if (lcdh != null){             //if there were any pending document
            //check to see if it's before the current document. if it is, then this document is not approvable.
            if (TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(document.getDocumentId()).getBeginDate().compareTo(lcdh.getEndDate()) >= 0){
                return false;
            }
        }

        return true;
/*        List<BalanceTransfer> balanceTransfers = TkServiceLocator.getBalanceTransferService().getBalanceTransfers(document.getPrincipalId(),
                document.getCalendarEntry().getBeginPeriodDate(),
                document.getCalendarEntry().getEndPeriodDate());
        if (!CollectionUtils.isEmpty(balanceTransfers))   {
	        for(BalanceTransfer balanceTransfer : balanceTransfers) {
	        	if(StringUtils.equals(TkConstants.DOCUMENT_STATUS.get(balanceTransfer.getStatus()), TkConstants.ROUTE_STATUS.ENROUTE))
	        		return false;
	            if (!StringUtils.equals(LMConstants.REQUEST_STATUS.APPROVED, balanceTransfer.getStatus())
	                    && !StringUtils.equals(LMConstants.REQUEST_STATUS.DISAPPROVED, balanceTransfer.getStatus())) {
	                return false;
	            }
	        }
        }
        List<LeavePayout> leavePayouts = TkServiceLocator.getLeavePayoutService().getLeavePayouts(document.getPrincipalId(),
        		document.getCalendarEntry().getBeginPeriodDate(),
        		document.getCalendarEntry().getEndPeriodDate());
        if (!CollectionUtils.isEmpty(leavePayouts)) {
        	for(LeavePayout payout : leavePayouts) {
	        	if(StringUtils.equals(TkConstants.DOCUMENT_STATUS.get(payout.getStatus()), TkConstants.ROUTE_STATUS.ENROUTE))
	        		return false;
	            if (!StringUtils.equals(LMConstants.REQUEST_STATUS.APPROVED, payout.getStatus())
	                    && !StringUtils.equals(LMConstants.REQUEST_STATUS.DISAPPROVED, payout.getStatus())) {
	                return false;
	            }
        	}
        }
        return true;*/
    }

    protected void leaveCalendarDocumentAction(String action, String principalId, LeaveCalendarDocument leaveCalendarDocument) {
        WorkflowDocument wd = null;
        if (leaveCalendarDocument != null) {
            String rhid = leaveCalendarDocument.getDocumentId();
            wd = WorkflowDocumentFactory.loadDocument(principalId, rhid);

            if (StringUtils.equals(action, TkConstants.DOCUMENT_ACTIONS.ROUTE)) {
                wd.route("Routing for Approval");
            } else if (StringUtils.equals(action, TkConstants.BATCH_JOB_ACTIONS.BATCH_JOB_ROUTE)) {
                Note.Builder builder = Note.Builder.create(rhid, principalId);
                builder.setCreateDate(new DateTime());
                builder.setText("Routed via Employee Approval batch job");
            	KewApiServiceLocator.getNoteService().createNote(builder.build());
            	
            	wd.route("Batch job routing leave calendar");
            } else if (StringUtils.equals(action, TkConstants.DOCUMENT_ACTIONS.APPROVE)) {
                if (TKUser.getCurrentTargetRoles().isSystemAdmin() &&
                        !TKUser.getCurrentTargetRoles().isApproverForTimesheet(leaveCalendarDocument)) {
                    wd.superUserBlanketApprove("Superuser approving timesheet.");
                } else {
                    wd.approve("Approving timesheet.");
                }
            } else if (StringUtils.equals(action, TkConstants.BATCH_JOB_ACTIONS.BATCH_JOB_APPROVE)) {
            	 Note.Builder builder = Note.Builder.create(rhid, principalId);
            	 builder.setCreateDate(new DateTime());
            	 builder.setText("Approved via Supervisor Approval batch job");
            	 KewApiServiceLocator.getNoteService().createNote(builder.build());
            	
            	wd.superUserBlanketApprove("Batch job approving leave calendar");
            } else if (StringUtils.equals(action, TkConstants.DOCUMENT_ACTIONS.DISAPPROVE)) {
                if (TKUser.getCurrentTargetRoles().isSystemAdmin()
                        && !TKUser.getCurrentTargetRoles().isApproverForTimesheet(leaveCalendarDocument)) {
                    wd.superUserDisapprove("Superuser disapproving timesheet.");
                } else {
                    wd.disapprove("Disapproving timesheet.");
                }
            }
        }
    }

    public boolean isLeavePlanningCalendar(String principalId, Date beginDate, Date endDate) {
        Date today = new Date();

        List<Job> jobs = TkServiceLocator.getJobService().getJobs(principalId, endDate);
        for (Job job : jobs) {
            //  Check for Leave eligibility.
            if (job.isEligibleForLeave()) {
                //  Check for Time (FLSA nonexempt) jobs. If one exists, then the Leave Calendar is always a Leave Planning Calendar
                if (job.getFlsaStatus().equalsIgnoreCase(TkConstants.FLSA_STATUS_NON_EXEMPT)) {
                    return true;
                } else {
                    //  If leave eligible and FLSA exempt, then report leave in the Leave Calendar. Use the date to determine Planning vs Recording Calendars.
                    if ( beginDate.after(today) ) {
                        //  future period, this is a Planning Calendar.
                        return true;
                    } else {
                        //  not a future period, this is a Reporting Calendar.
                        return false;
                    }
                }
            } else {
            //  not leave eligible
                return false;
            }
        }
        return false;
    }

	@Override
	public BigDecimal getCarryOverForCurrentCalendar(String principalId) {
		// TODO Auto-generated method stub
		return null;
	}

}

