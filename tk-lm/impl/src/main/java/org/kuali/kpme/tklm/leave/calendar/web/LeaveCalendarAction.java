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
package org.kuali.kpme.tklm.leave.calendar.web;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.calendar.Calendar;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.document.calendar.CalendarDocument;
import org.kuali.kpme.core.earncode.EarnCode;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.common.CalendarFormAction;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.block.LeaveBlockAggregate;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendar;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.kpme.tklm.leave.calendar.validation.LeaveCalendarValidationUtil;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.summary.LeaveSummary;
import org.kuali.kpme.tklm.leave.summary.LeaveSummaryRow;
import org.kuali.kpme.tklm.leave.transfer.BalanceTransfer;
import org.kuali.kpme.tklm.leave.transfer.validation.BalanceTransferValidationUtils;
import org.kuali.kpme.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.kpme.tklm.leave.workflow.LeaveRequestDocument;
import org.kuali.kpme.tklm.time.detail.web.ActionFormUtils;
import org.kuali.kpme.tklm.time.util.TkContext;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

public class LeaveCalendarAction extends CalendarFormAction {

	private static final Logger LOG = Logger.getLogger(LeaveCalendarAction.class);

    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
    	LeaveCalendarForm leaveCalendarForm = (LeaveCalendarForm) form;
    	
    	String principalId = GlobalVariables.getUserSession().getPrincipalId();
        
    	if (StringUtils.isNotBlank(leaveCalendarForm.getDocumentId())) {
	    	CalendarDocument leaveCalendarDocument = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(leaveCalendarForm.getDocumentId());
	        if (!HrServiceLocator.getHRPermissionService().canViewCalendarDocument(principalId, leaveCalendarDocument)) {
	            throw new AuthorizationException(GlobalVariables.getUserSession().getPrincipalId(), "LeaveCalendarAction: docid: " + leaveCalendarDocument.getDocumentId(), "");
	        }
    	}
    }
    
    public ActionForward docHandler(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = mapping.findForward("basic");
        String command = request.getParameter("command");
        
    	if (StringUtils.equals(command, "displayDocSearchView") 
    			|| StringUtils.equals(command, "displayActionListView")
    			|| StringUtils.equals(command, "displaySuperUserView")) {
        	String documentId = (String) request.getParameter("docId");
        	LeaveCalendarDocument leaveCalendarDocument = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);
        	String leaveCalendarPrincipalName = KimApiServiceLocator.getPersonService().getPerson(leaveCalendarDocument.getPrincipalId()).getPrincipalName();
        	
        	String principalId = HrContext.getTargetPrincipalId();
        	String principalName = KimApiServiceLocator.getPersonService().getPerson(principalId).getPrincipalName();
        	
        	StringBuilder builder = new StringBuilder();
        	if (!StringUtils.equals(principalName, leaveCalendarPrincipalName)) {
        		if (StringUtils.equals(command, "displayDocSearchView")
        				|| StringUtils.equals(command, "displaySuperUserView")) {
            		builder.append("changeTargetPerson.do?methodToCall=changeTargetPerson");
            		builder.append("&documentId=");
            		builder.append(documentId);
            		builder.append("&principalName=");
            		builder.append(leaveCalendarPrincipalName);
            		builder.append("&targetUrl=LeaveCalendar.do");
            		builder.append("?documentId=" + documentId);
            		builder.append("&returnUrl=LeaveApproval.do");
            	} else {
            		builder.append("LeaveApproval.do");
            	}
        	} else {
        		builder.append("LeaveCalendar.do");
        		builder.append("?documentId=" + documentId);
        	}
        	
        	forward = new ActionRedirect(builder.toString());
        }
    	
    	return forward;
    }
    
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveCalendarForm leaveCalendarForm = (LeaveCalendarForm) form;
		String documentId = leaveCalendarForm.getDocumentId();
		String principalId = HrContext.getTargetPrincipalId();
		CalendarEntry calendarEntry = null;
		LeaveCalendarDocument leaveCalendarDocument = null;
		if (StringUtils.isNotBlank(documentId)) {
			leaveCalendarDocument = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);
			
			if (leaveCalendarDocument != null) {
				calendarEntry = leaveCalendarDocument.getCalendarEntry();
			}
		} else {
			if (StringUtils.isNotBlank(leaveCalendarForm.getHrCalendarEntryId())) {
				calendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarEntry(leaveCalendarForm.getHrCalendarEntryId());
			} else {
				calendarEntry = HrServiceLocator.getCalendarEntryService().getCurrentCalendarDatesForLeaveCalendar(principalId, new LocalDate().toDateTimeAtStartOfDay());
			}
			
			if (calendarEntry != null) {
				if (LmServiceLocator.getLeaveCalendarService().shouldCreateLeaveDocument(principalId, calendarEntry)) {
					leaveCalendarDocument = LmServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument(principalId, calendarEntry);
				} else {
					LeaveCalendarDocumentHeader header = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(principalId, calendarEntry.getBeginPeriodFullDateTime(), calendarEntry.getEndPeriodFullDateTime());
					if (header != null) {
						leaveCalendarDocument = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(header.getDocumentId());
					}
				}
	    	}
		}

        if (calendarEntry != null) {
        	leaveCalendarForm.setHrCalendarEntryId(calendarEntry.getHrCalendarEntryId());
        	leaveCalendarForm.setCalendarEntry(calendarEntry);
        	leaveCalendarForm.setBeginCalendarEntryDate(calendarEntry.getBeginPeriodDateTime());
        	leaveCalendarForm.setEndCalendarEntryDate(DateUtils.addMilliseconds(calendarEntry.getEndPeriodDateTime(), -1));

    		CalendarEntry previousCalendarEntry = HrServiceLocator.getCalendarEntryService().getPreviousCalendarEntryByCalendarId(calendarEntry.getHrCalendarId(), calendarEntry);
            if (previousCalendarEntry != null) {
            	LocalDate previousBeginDate = previousCalendarEntry.getBeginPeriodFullDateTime().toLocalDate();
            	LocalDate previousEndDate = previousCalendarEntry.getEndPeriodFullDateTime().toLocalDate().minusDays(1);

        		PrincipalHRAttributes principalHRAttributes = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, previousEndDate);
        		if (principalHRAttributes != null) {
        			LocalDate serviceDate = principalHRAttributes.getServiceLocalDate();
        			if (serviceDate != null) {
        				if (previousBeginDate.isEqual(serviceDate) || previousBeginDate.isAfter(serviceDate)) {
        					leaveCalendarForm.setPrevHrCalendarEntryId(previousCalendarEntry.getHrCalendarEntryId());
                		} 
                	} else {
                		leaveCalendarForm.setPrevHrCalendarEntryId(previousCalendarEntry.getHrCalendarEntryId());
        			}
        		}
            }

    		int planningMonths = ActionFormUtils.getPlanningMonthsForEmployee(principalId);
            if (planningMonths != 0) {
                List<CalendarEntry> futureCalendarEntries = HrServiceLocator.getCalendarEntryService().getFutureCalendarEntries(calendarEntry.getHrCalendarId(), new LocalDate().toDateTimeAtStartOfDay(), planningMonths);
                if (!futureCalendarEntries.isEmpty()) {
                    CalendarEntry nextCalendarEntry = HrServiceLocator.getCalendarEntryService().getNextCalendarEntryByCalendarId(calendarEntry.getHrCalendarId(), calendarEntry);
                	CalendarEntry lastFutureCalendarEntry = futureCalendarEntries.get(futureCalendarEntries.size() - 1);

                    if (nextCalendarEntry != null && futureCalendarEntries != null) {
                    	DateTime nextCalendarEntryBeginDate = nextCalendarEntry.getBeginPeriodFullDateTime();
                    	DateTime lastFutureCalendarEntryBeginDate = lastFutureCalendarEntry.getBeginPeriodFullDateTime();
                    	if (nextCalendarEntryBeginDate.isBefore(lastFutureCalendarEntryBeginDate) || nextCalendarEntryBeginDate.isEqual(lastFutureCalendarEntryBeginDate)) {
                    		leaveCalendarForm.setNextHrCalendarEntryId(nextCalendarEntry.getHrCalendarEntryId());
                    	}
                    }
                }
            }
	        
	        setCalendarFields(request, leaveCalendarForm);
        } else {
        	EntityNamePrincipalName entityNamePrincipalName = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(principalId);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "error.missing.leaveCalendar", entityNamePrincipalName.getPrincipalName());
        }
        
        ActionForward actionForward = super.execute(mapping, form, request, response);
        
        if (calendarEntry != null) {
			List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignmentsByCalEntryForLeaveCalendar(principalId, calendarEntry);
			List<String> assignmentKeys = new ArrayList<String>();
	        for (Assignment assignment : assignments) {
	        	assignmentKeys.add(assignment.getAssignmentKey());
	        }
	        
	        if (leaveCalendarDocument != null) {
	        	leaveCalendarForm.setLeaveCalendarDocument(leaveCalendarDocument);
	        	leaveCalendarForm.setDocumentId(leaveCalendarDocument.getDocumentId());
	        	leaveCalendarForm.setAssignmentDescriptions(leaveCalendarDocument.getAssignmentDescriptions());
	        } else {
	        	leaveCalendarForm.setAssignmentDescriptions(HrServiceLocator.getAssignmentService().getAssignmentDescriptionsForAssignments(assignments));
	        }
	        
			if (HrServiceLocator.getHRPermissionService().canViewLeaveTabsWithNEStatus()) {
				if (LocalDate.now().isBefore(calendarEntry.getEndPeriodFullDateTime().toLocalDate()) || LocalDate.now().isEqual(calendarEntry.getEndPeriodFullDateTime().toLocalDate())) {
	                setDocEditable(leaveCalendarForm, leaveCalendarDocument);
				}
			} else {
	            setDocEditable(leaveCalendarForm, leaveCalendarDocument);
			}

			runAccrualService(leaveCalendarForm);

			List<LeaveBlock> leaveBlocks = getLeaveBlocks(principalId, calendarEntry, leaveCalendarDocument, assignmentKeys);

			setLeaveBlocks(leaveCalendarForm, principalId, leaveBlocks, assignmentKeys);
	        setLeaveSummary(leaveCalendarForm);
	        setMessages(leaveCalendarForm, leaveBlocks);
        }

		return actionForward;
	}

	public ActionForward addLeaveBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		LeaveCalendarDocument lcd = lcf.getLeaveCalendarDocument();
		
		String principalId = HrContext.getPrincipalId();
		String targetPrincipalId = HrContext.getTargetPrincipalId();
		CalendarEntry calendarEntry = lcf.getCalendarEntry();
		String selectedAssignment = lcf.getSelectedAssignment();
		
		DateTime beginDate = null;
		DateTime endDate = null;
		
		/** -- Jignasha : if earchcode type is 'T' then change the date and time with timezone.
		// Surgery point - Need to construct a Date/Time with Appropriate Timezone.
		 * */
		LOG.debug("Start time is "+lcf.getStartTime());
		LOG.debug("Emnd time is "+lcf.getEndTime());
		if(lcf.getStartTime() != null && lcf.getEndTime() != null) {
			beginDate = TKUtils.convertDateStringToDateTimeWithoutZone(lcf.getStartDate(), lcf.getStartTime());
			endDate   = TKUtils.convertDateStringToDateTimeWithoutZone(lcf.getEndDate(), lcf.getEndTime());
		}  else {
			beginDate = TKUtils.formatDateTimeStringNoTimezone(lcf.getStartDate());
			endDate = TKUtils.formatDateTimeStringNoTimezone(lcf.getEndDate());
		}
        LOG.debug("Begin Date is>> "+beginDate);
        LOG.debug("End Date is>> "+endDate);
		
		String selectedEarnCode = lcf.getSelectedEarnCode();
		BigDecimal hours = lcf.getLeaveAmount();
		String desc = lcf.getDescription();
		String spanningWeeks = lcf.getSpanningWeeks();  // KPME-1446
		
		String documentId = lcd != null ? lcd.getDocumentId() : "";
		
		Assignment assignment = null;
		if(lcd != null) {
			assignment = lcd.getAssignment(AssignmentDescriptionKey.get(selectedAssignment));
			if(assignment == null)
				LOG.warn("No matched assignment found");
		} else {
			List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignmentsByCalEntryForLeaveCalendar(targetPrincipalId, calendarEntry);
			assignment = HrServiceLocator.getAssignmentService().getAssignment(assignments, selectedAssignment, calendarEntry.getBeginPeriodFullDateTime().toLocalDate());
		}
		LmServiceLocator.getLeaveBlockService().addLeaveBlocks(beginDate, endDate, calendarEntry, selectedEarnCode, hours, desc, assignment, spanningWeeks, 
				LMConstants.LEAVE_BLOCK_TYPE.LEAVE_CALENDAR, targetPrincipalId);

		generateLeaveCalendarChangedNotification(principalId, targetPrincipalId, documentId, calendarEntry.getHrCalendarEntryId());
		
		// after adding the leave block, set the fields of this form to null for future new leave blocks
		lcf.setLeaveAmount(null);
		lcf.setDescription(null);
		
		// call accrual service if earn code is not eligible for accrual
		if(calendarEntry != null) {
			this.rerunAccrualForNotEligibleForAccrualChanges(selectedEarnCode, endDate.toLocalDate(), calendarEntry.getBeginPeriodFullDateTime().toLocalDate(), calendarEntry.getEndPeriodFullDateTime().toLocalDate());
		 }
		// recalculate summary
		if (calendarEntry != null) {
			LeaveSummary ls = LmServiceLocator.getLeaveSummaryService().getLeaveSummary(targetPrincipalId, calendarEntry);
		    lcf.setLeaveSummary(ls);
		}
		
		return mapping.findForward("basic");
	}


	public ActionForward deleteLeaveBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		LeaveCalendarDocument lcd = lcf.getLeaveCalendarDocument();

		String principalId = HrContext.getPrincipalId();
		String targetPrincipalId = HrContext.getTargetPrincipalId();
		CalendarEntry calendarEntry = lcf.getCalendarEntry();
		String leaveBlockId = lcf.getLeaveBlockId();
		
		String documentId = lcd != null ? lcd.getDocumentId() : "";

        LeaveBlock blockToDelete = LmServiceLocator.getLeaveBlockService().getLeaveBlock(leaveBlockId);
        if (blockToDelete != null && LmServiceLocator.getLMPermissionService().canDeleteLeaveBlock(HrContext.getPrincipalId(), blockToDelete)) {
        	//if leave block is a pending leave request, cancel the leave request document
        	if(blockToDelete.getRequestStatus().equals(HrConstants.REQUEST_STATUS.REQUESTED)) {
        		List<LeaveRequestDocument> lrdList = LmServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocumentsByLeaveBlockId(blockToDelete.getLmLeaveBlockId());
        		if(CollectionUtils.isNotEmpty(lrdList)) {
        			for(LeaveRequestDocument lrd : lrdList) { 
        				DocumentStatus status = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(lrd.getDocumentNumber());
        				if(DocumentStatus.ENROUTE.getCode().equals(status.getCode())) {
        					// cancel the leave request document as the employee.
        					LmServiceLocator.getLeaveRequestDocumentService().recallAndCancelLeave(lrd.getDocumentNumber(), targetPrincipalId, "Leave block deleted by user " + principalId);
        				}
        			}
        		}
        	}
        	
        	List<String> approverList = new ArrayList<String>();
        	//if leave block is an approved leave request, get list of approver's id
        	if(blockToDelete.getRequestStatus().equals(HrConstants.REQUEST_STATUS.APPROVED)) {
        		List<LeaveRequestDocument> lrdList = LmServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocumentsByLeaveBlockId(blockToDelete.getLmLeaveBlockId());
        		if(CollectionUtils.isNotEmpty(lrdList)) {
        			for(LeaveRequestDocument lrd : lrdList) { 
        				DocumentStatus status = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(lrd.getDocumentNumber());
        				if(DocumentStatus.FINAL.getCode().equals(status.getCode())) {
        					// get approver's id for sending out email notification later
        					approverList = LmServiceLocator.getLeaveRequestDocumentService().getApproverIdList(lrd.getDocumentNumber());
        				}
        			}
        		}
        	}

        	LmServiceLocator.getLeaveBlockService().deleteLeaveBlock(leaveBlockId, principalId);
		    generateLeaveCalendarChangedNotification(principalId, targetPrincipalId, documentId, calendarEntry.getHrCalendarEntryId());
		    if(CollectionUtils.isNotEmpty(approverList)) {
		    	this.generateLeaveBlockDeletionNotification(approverList, targetPrincipalId, principalId, TKUtils.formatDate(blockToDelete.getLeaveLocalDate()), blockToDelete.getLeaveAmount().toString());
		    }
        	
		    // recalculate accruals
		    if(lcf.getCalendarEntry() != null) {
		    	rerunAccrualForNotEligibleForAccrualChanges(blockToDelete.getEarnCode(), blockToDelete.getLeaveLocalDate(), calendarEntry.getBeginPeriodFullDateTime().toLocalDate(), calendarEntry.getEndPeriodFullDateTime().toLocalDate());
		    }	
        }
		// recalculate summary
		if(lcf.getCalendarEntry() != null) {
			LeaveSummary ls = LmServiceLocator.getLeaveSummaryService().getLeaveSummary(targetPrincipalId, calendarEntry);
		    lcf.setLeaveSummary(ls);
		}
		return mapping.findForward("basic");
	}
	
	
    @Override
    protected List<CalendarEntry> getCalendarEntries(CalendarEntry currentCalendarEntry) {
        return HrServiceLocator.getCalendarEntryService().getAllCalendarEntriesForCalendarIdUpToPlanningMonths(currentCalendarEntry.getHrCalendarId(), HrContext.getTargetPrincipalId());
    }
    
    protected void runAccrualService(LeaveCalendarForm leaveCalendarForm) {
    	String principalId = HrContext.getTargetPrincipalId();
    	CalendarEntry calendarEntry = leaveCalendarForm.getCalendarEntry();
    	
        // check configuration setting for allowing accrual service to be ran from leave calendar
		String runAccrualFlag = ConfigContext.getCurrentContextConfig().getProperty(LMConstants.RUN_ACCRUAL_FROM_CALENDAR);
		if (StringUtils.equals(runAccrualFlag, "true")) {
			// run accrual for future dates only, use planning month of leave plan for accrual period
			// only run the accrual if the calendar entry contains future dates
			if (calendarEntry.getEndPeriodDate().after(LocalDate.now().toDate())) {
				if (LmServiceLocator.getLeaveAccrualService().statusChangedSinceLastRun(principalId)) {
					LmServiceLocator.getLeaveAccrualService().calculateFutureAccrualUsingPlanningMonth(principalId, calendarEntry.getBeginPeriodFullDateTime().toLocalDate(), HrContext.getPrincipalId());
				}
			}
		}
    }
    
    protected List<LeaveBlock> getLeaveBlocks(String principalId, CalendarEntry calendarEntry, LeaveCalendarDocument leaveCalendarDocument, List<String> assignmentKeys) {
        List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
        
        if (leaveCalendarDocument != null) {
            leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksForLeaveCalendar(leaveCalendarDocument.getPrincipalId(), leaveCalendarDocument.getAsOfDate(), leaveCalendarDocument.getDocEndDate(), assignmentKeys);
        } else {
            leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksForLeaveCalendar(principalId, calendarEntry.getBeginPeriodFullDateTime().toLocalDate(), calendarEntry.getEndPeriodFullDateTime().toLocalDate(), assignmentKeys);
        }
        
        return leaveBlocks;
    }
    
    protected void setLeaveBlocks(LeaveCalendarForm leaveCalendarForm, String principalId, List<LeaveBlock> leaveBlocks, List<String> assignmentKeys) {
    	CalendarEntry calendarEntry = leaveCalendarForm.getCalendarEntry();
        
        leaveCalendarForm.setLeaveCalendar(new LeaveCalendar(principalId, calendarEntry, assignmentKeys));
        
        LeaveBlockAggregate aggregate = new LeaveBlockAggregate(leaveBlocks, calendarEntry, leaveCalendarForm.getLeaveCalendar());
        leaveCalendarForm.setLeaveBlockString(LeaveActionFormUtils.getLeaveBlocksJson(aggregate.getFlattenedLeaveBlockList()));
    }
    
    protected void setLeaveSummary(LeaveCalendarForm leaveCalendarForm) throws Exception {
    	String principalId = HrContext.getTargetPrincipalId();
    	CalendarEntry calendarEntry = leaveCalendarForm.getCalendarEntry();
    	PrincipalHRAttributes principalHRAttributes = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, calendarEntry.getEndPeriodFullDateTime().toLocalDate());

    	//check to see if we are on a previous leave plan
        if (principalHRAttributes != null) {
            DateTime currentYearBeginDate = HrServiceLocator.getLeavePlanService().getFirstDayOfLeavePlan(principalHRAttributes.getLeavePlan(), LocalDate.now());
            DateTime calEntryEndDate = calendarEntry.getEndPeriodFullDateTime();
            if (calEntryEndDate.getMillis() > currentYearBeginDate.getMillis()) {
            	//current or future year
                LeaveSummary ls = LmServiceLocator.getLeaveSummaryService().getLeaveSummary(principalId, calendarEntry);
                leaveCalendarForm.setLeaveSummary(ls);
            } else {
                //current year roll over date has been passed, all previous calendars belong to the previous leave plan calendar year.
                DateTime effDate = HrServiceLocator.getLeavePlanService().getRolloverDayOfLeavePlan(principalHRAttributes.getLeavePlan(), calEntryEndDate.minusDays(1).toLocalDate());
                LeaveSummary ls = LmServiceLocator.getLeaveSummaryService().getLeaveSummaryAsOfDateWithoutFuture(principalId, effDate.toLocalDate());
                //override title element (based on date passed in)
                DateFormat formatter = new SimpleDateFormat("MMMM d");
                DateFormat formatter2 = new SimpleDateFormat("MMMM d yyyy");
                DateTime entryEndDate = new LocalDateTime(calendarEntry.getEndPeriodDate()).toDateTime();
                if (entryEndDate.getHourOfDay() == 0) {
                    entryEndDate = entryEndDate.minusDays(1);
                }
                String aString = formatter.format(calendarEntry.getBeginPeriodDate()) + " - " + formatter2.format(entryEndDate.toDate());
                ls.setPendingDatesString(aString);
                DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM d, yyyy");
                ls.setNote("Values as of: " + fmt.print(effDate));
                leaveCalendarForm.setLeaveSummary(ls);
            }
        }
    }
    
    protected void setMessages(LeaveCalendarForm leaveCalendarForm, List<LeaveBlock> leaveBlocks) {
    	String principalId = HrContext.getTargetPrincipalId();
    	CalendarEntry calendarEntry = leaveCalendarForm.getCalendarEntry();
    	PrincipalHRAttributes principalHRAttributes = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, calendarEntry.getEndPeriodFullDateTime().toLocalDate());
    	
        Map<String, Set<String>> allMessages = LeaveCalendarValidationUtil.getWarningMessagesForLeaveBlocks(leaveBlocks);

        // add warning message for accrual categories that have exceeded max balance.
        // Could set a flag on the transferable rows here so that LeaveCalendarSubmit.do knows
        // which row(s) to transfer when user submits the calendar for approval.
        List<BalanceTransfer> losses = new ArrayList<BalanceTransfer>();

        Interval calendarInterval = new Interval(calendarEntry.getBeginPeriodDate().getTime(), calendarEntry.getEndPeriodDate().getTime());
        Map<String,Set<LeaveBlock>> maxBalInfractions = new HashMap<String,Set<LeaveBlock>>();
        
        Date effectiveDate = LocalDate.now().toDate();
        if (!calendarInterval.contains(effectiveDate.getTime())) {
        	effectiveDate = calendarEntry.getEndPeriodDate();
        }
        
        if (principalHRAttributes != null) {
	        maxBalInfractions = LmServiceLocator.getAccrualCategoryMaxBalanceService().getMaxBalanceViolations(calendarEntry, principalId);
	        
	        LeaveSummary summary = leaveCalendarForm.getLeaveSummary();
	        for (Entry<String,Set<LeaveBlock>> entry : maxBalInfractions.entrySet()) {
	        	for (LeaveBlock lb : entry.getValue()) {
	        		AccrualCategory accrualCat = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(lb.getAccrualCategory(), lb.getLeaveLocalDate());
		        	AccrualCategoryRule aRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(lb.getAccrualCategoryRuleId());
		        	if (StringUtils.equals(aRule.getActionAtMaxBalance(),HrConstants.ACTION_AT_MAX_BALANCE.LOSE)) {
		        		DateTime aDate = null;
		        		if (StringUtils.equals(aRule.getMaxBalanceActionFrequency(), HrConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
		        			aDate = HrServiceLocator.getLeavePlanService().getRolloverDayOfLeavePlan(principalHRAttributes.getLeavePlan(), lb.getLeaveLocalDate());
		        		} else {
			        		Calendar cal = HrServiceLocator.getCalendarService().getCalendarByPrincipalIdAndDate(principalId, new LocalDate(lb.getLeaveDate()), true);
			        		CalendarEntry leaveEntry = HrServiceLocator.getCalendarEntryService().getCurrentCalendarEntryByCalendarId(cal.getHrCalendarId(), new DateTime(lb.getLeaveDate()));
			        		aDate = new DateTime(leaveEntry.getEndPeriodDate());
		        		}
		        		aDate = aDate.minusDays(1);
		        		if (calendarInterval.contains(aDate.getMillis()) && aDate.toDate().compareTo(calendarEntry.getEndPeriodDate()) <= 0) {
			        		//may want to calculate summary for all rows, displayable or not, and determine displayability via tags.
			    			AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(aRule.getLmAccrualCategoryId());
			    			BigDecimal accruedBalance = LmServiceLocator.getAccrualService().getAccruedBalanceForPrincipal(principalId, accrualCategory, lb.getLeaveLocalDate());
				        	
				        	BalanceTransfer loseTransfer = LmServiceLocator.getBalanceTransferService().initializeTransfer(principalId, lb.getAccrualCategoryRuleId(), accruedBalance, lb.getLeaveLocalDate());
				        	boolean valid = BalanceTransferValidationUtils.validateTransfer(loseTransfer);
				        	if (valid) {
				        		losses.add(loseTransfer);
				        	}
		        		}
		        	} else if (StringUtils.equals(HrConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND, aRule.getMaxBalanceActionFrequency())) {
			        	if (calendarInterval.contains(lb.getLeaveDate().getTime())) {
				        	// accrual categories within the leave plan that are hidden from the leave summary will not appear.
				        	List<LeaveSummaryRow> summaryRows = summary.getLeaveSummaryRows();
				        	List<LeaveSummaryRow> updatedSummaryRows = new ArrayList<LeaveSummaryRow>(summaryRows.size());
				        	//AccrualCategoryRule currentRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(accrualCat, effectiveDate, principalCalendar.getServiceDate());
				        	for (LeaveSummaryRow summaryRow : summaryRows) {
				        		if (StringUtils.equals(summaryRow.getAccrualCategory(),accrualCat.getAccrualCategory())) {
				        			if (StringUtils.equals(aRule.getActionAtMaxBalance(),HrConstants.ACTION_AT_MAX_BALANCE.PAYOUT)) {
				        				summaryRow.setPayoutable(true);
				        			} else {
				        				if (StringUtils.equals(aRule.getActionAtMaxBalance(),HrConstants.ACTION_AT_MAX_BALANCE.TRANSFER)) {
					        				summaryRow.setTransferable(true);
				        				}
				        			}

				        			summaryRow.setInfractingLeaveBlockId(lb.getLmLeaveBlockId());
				        		}
				        		updatedSummaryRows.add(summaryRow);
				        	}
				        	summary.setLeaveSummaryRows(updatedSummaryRows);
			        	}
		        	}

    				if (calendarInterval.contains(lb.getLeaveDate().getTime())) {
    		        	// accrual categories within the leave plan that are hidden from the leave summary WILL appear.
        				String message = "You have exceeded the maximum balance limit for '" + accrualCat.getAccrualCategory() + "' as of " + lb.getLeaveDate() + ". " 
        						+ "Depending upon the accrual category rules, leave over this limit may be forfeited.";
                        //  leave blocks are sorted in getMaxBalanceViolations() method, so we just take the one with the earliest leave date for an accrual category.
        				if (!StringUtils.contains(allMessages.get("warningMessages").toString(), "You have exceeded the maximum balance limit for '" + accrualCat.getAccrualCategory())) {
                            allMessages.get("warningMessages").add(message);
        				}
    				}
	        	}
	        }
        	leaveCalendarForm.setLeaveSummary(summary);
        }
        leaveCalendarForm.setForfeitures(losses);
        
        Map<String,Set<String>> transactionalMessages = LeaveCalendarValidationUtil.validatePendingTransactions(principalId, calendarEntry.getBeginPeriodFullDateTime().toLocalDate(), calendarEntry.getEndPeriodFullDateTime().toLocalDate());
        allMessages.get("infoMessages").addAll(transactionalMessages.get("infoMessages"));
        allMessages.get("warningMessages").addAll(transactionalMessages.get("warningMessages"));
        allMessages.get("actionMessages").addAll(transactionalMessages.get("actionMessages"));

        // add warning messages based on max carry over balances for each accrual category
        if (principalHRAttributes != null) {
			List<AccrualCategory> accrualCategories = HrServiceLocator.getAccrualCategoryService().getActiveLeaveAccrualCategoriesForLeavePlan(principalHRAttributes.getLeavePlan(), calendarEntry.getEndPeriodFullDateTime().toLocalDate());
			for (AccrualCategory accrualCategory : accrualCategories) {
				if (LmServiceLocator.getAccrualCategoryMaxCarryOverService().exceedsAccrualCategoryMaxCarryOver(accrualCategory.getAccrualCategory(), principalId, calendarEntry, calendarEntry.getEndPeriodFullDateTime().toLocalDate())) {
					String message = "Your pending leave balance is greater than the annual max carry over for accrual category '" + accrualCategory.getAccrualCategory() + "' and upon approval, the excess balance will be lost.";
					if (!allMessages.get("warningMessages").contains(message)) {
                        allMessages.get("warningMessages").add(message);
					}
				}
			}
			
			// KPME-1279 check for Absent Earn code to add warning.
			List<EarnCode> earnCodes = HrServiceLocator.getEarnCodeService().getEarnCodesForPrincipal(principalId, calendarEntry.getEndPeriodFullDateTime().toLocalDate(), true);
			if (earnCodes != null && !earnCodes.isEmpty()) {
				for (EarnCode earnCodeObj : earnCodes) {
					if (earnCodeObj != null) {
						if("Y".equalsIgnoreCase(earnCodeObj.getAffectPay()) && "N".equalsIgnoreCase(earnCodeObj.getEligibleForAccrual())) {
							String message = "Absent time cannot be used until other accrual balances are zero or below a specified accrual balance.";
							if (!allMessages.get("warningMessages").contains(message)) {
	                            allMessages.get("warningMessages").add(message);
	                            break;
							}
						}
					}
				}
			}
		}

        leaveCalendarForm.setWarningMessages(new ArrayList<String>(allMessages.get("warningMessages")));
        leaveCalendarForm.setInfoMessages(new ArrayList<String>(allMessages.get("infoMessages")));
        leaveCalendarForm.setActionMessages(new ArrayList<String>(allMessages.get("actionMessages")));
    }
	
	/**
	 * Recalculate accrual when a leave block with not-eligible-for-accrual earn code is added or deleted
	 * calculate accrual only for the calendar entry period
	 * @param earnCode
	 * @param asOfDate
	 * @param startDate
	 * @param endDate
	 */
	private void rerunAccrualForNotEligibleForAccrualChanges(String earnCode, LocalDate asOfDate, LocalDate startDate, LocalDate endDate) {
		EarnCode ec = HrServiceLocator.getEarnCodeService().getEarnCode(earnCode, asOfDate);
		if(ec != null && ec.getEligibleForAccrual().equals("N")) {
			if(startDate != null && endDate != null) {
				// since we are only recalculating accrual for this pay period, we use "false" to not record the accrual run data
				LmServiceLocator.getLeaveAccrualService().runAccrual(HrContext.getTargetPrincipalId(), startDate.toDateTimeAtStartOfDay(), endDate.toDateTimeAtStartOfDay(), false);
			}
		}
	}
	
	// KPME-1447
	public ActionForward updateLeaveBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		LeaveCalendarDocument lcd = lcf.getLeaveCalendarDocument();
		
		String principalId = HrContext.getPrincipalId();
		String targetPrincipalId = HrContext.getTargetPrincipalId();
		CalendarEntry calendarEntry = lcf.getCalendarEntry();
		String selectedEarnCode = lcf.getSelectedEarnCode();
		String leaveBlockId = lcf.getLeaveBlockId();
		
		String documentId = lcd != null ? lcd.getDocumentId() : "";
		
		LeaveBlock updatedLeaveBlock = null;
		updatedLeaveBlock = LmServiceLocator.getLeaveBlockService().getLeaveBlock(leaveBlockId);
        if (updatedLeaveBlock.isEditable()) {
            if (StringUtils.isNotBlank(lcf.getDescription())) {
                updatedLeaveBlock.setDescription(lcf.getDescription().trim());
            }
            if (!updatedLeaveBlock.getLeaveAmount().equals(lcf.getLeaveAmount())) {
                updatedLeaveBlock.setLeaveAmount(lcf.getLeaveAmount());
            }
            
            DateTime beginDate = null;
    		DateTime endDate = null;
            
            EarnCode earnCode =  HrServiceLocator.getEarnCodeService().getEarnCode(selectedEarnCode, updatedLeaveBlock.getLeaveLocalDate()); // selectedEarnCode = hrEarnCodeId
            if(earnCode != null && earnCode.getRecordMethod().equalsIgnoreCase(HrConstants.EARN_CODE_TIME)) {
            	if(lcf.getStartTime() != null && lcf.getEndTime() != null) {
        			beginDate = TKUtils.convertDateStringToDateTimeWithoutZone(lcf.getStartDate(), lcf.getStartTime());
        			endDate   = TKUtils.convertDateStringToDateTimeWithoutZone(lcf.getEndDate(), lcf.getEndTime());
        		}  else {
        			beginDate = TKUtils.formatDateTimeStringNoTimezone(lcf.getStartDate());
        			endDate = TKUtils.formatDateTimeStringNoTimezone(lcf.getEndDate());
        		}
            	updatedLeaveBlock.setBeginTimestamp(new Timestamp(beginDate.getMillis()));
            	updatedLeaveBlock.setEndTimestamp(new Timestamp(endDate.getMillis()));
            	updatedLeaveBlock.setLeaveAmount(TKUtils.getHoursBetween(beginDate.getMillis(), endDate.getMillis()));
            }
            
            if (!updatedLeaveBlock.getEarnCode().equals(earnCode.getEarnCode())) {
                updatedLeaveBlock.setEarnCode(earnCode.getEarnCode());
            }
            
            LmServiceLocator.getLeaveBlockService().updateLeaveBlock(updatedLeaveBlock, principalId);
            generateLeaveCalendarChangedNotification(principalId, targetPrincipalId, documentId, calendarEntry.getHrCalendarEntryId());
            
            lcf.setLeaveAmount(null);
            lcf.setDescription(null);
            lcf.setSelectedEarnCode(null);
    		// recalculate summary
    		if(lcf.getCalendarEntry() != null) {
    			LeaveSummary ls = LmServiceLocator.getLeaveSummaryService().getLeaveSummary(targetPrincipalId, calendarEntry);
    		    lcf.setLeaveSummary(ls);
    		}
        }
        return mapping.findForward("basic");
    }

    private void setDocEditable(LeaveCalendarForm leaveForm, LeaveCalendarDocument lcd) {
    	leaveForm.setDocEditable(false);
    	if(lcd == null) {
    		// working on own calendar
    		 if(HrContext.getTargetPrincipalId().equals(GlobalVariables.getUserSession().getPrincipalId())) {
    			 leaveForm.setDocEditable(true); 
    		 } else {
    			 if(HrContext.isSystemAdmin()
                     || TkContext.isLocationAdmin()
                     || HrContext.isReviewer()
                     || HrContext.isAnyApprover()) {
    				 	leaveForm.setDocEditable(true);
    			 }
             }
    	} else {
    		DocumentStatus documentStatus = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(lcd.getDocumentId());
	        if (HrContext.isSystemAdmin() && !StringUtils.equals(lcd.getPrincipalId(), GlobalVariables.getUserSession().getPrincipalId())) {
	            leaveForm.setDocEditable(true);
	        } else {
	            if (!DocumentStatus.FINAL.equals(documentStatus) 
	            		&& !DocumentStatus.CANCELED.getCode().equals(documentStatus)
	     	 	 	 	&& !DocumentStatus.DISAPPROVED.getCode().equals(documentStatus)) {
	                if(StringUtils.equals(lcd.getPrincipalId(), GlobalVariables.getUserSession().getPrincipalId())
	                        || HrContext.isSystemAdmin()
	                        || TkContext.isLocationAdmin()
	                        || HrContext.isReviewer()
	                        || HrContext.isAnyApprover()) {
	                    leaveForm.setDocEditable(true);
	                }
	
	                //if the leave Calendar has been approved by at least one of the approvers, the employee should not be able to edit it
	                if (StringUtils.equals(lcd.getPrincipalId(), GlobalVariables.getUserSession().getPrincipalId()) && DocumentStatus.ENROUTE.equals(documentStatus)) {
	                    Collection actions = KEWServiceLocator.getActionTakenService().findByDocIdAndAction(lcd.getDocumentId(), HrConstants.DOCUMENT_ACTIONS.APPROVE);
	                    if(!actions.isEmpty()) {
	                        leaveForm.setDocEditable(false);
	                    }
	                }
	            }
	        }
    	}
    }
	
	private void generateLeaveCalendarChangedNotification(String principalId, String targetPrincipalId, String documentId, String hrCalendarEntryId) {
		if (!StringUtils.equals(principalId, targetPrincipalId)) {
			EntityNamePrincipalName person = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(principalId);
			if (person != null && person.getDefaultName() != null) {
				String subject = "Leave Calendar Modification Notice";
				StringBuilder message = new StringBuilder();
				message.append("Your Leave Calendar was changed by ");
				message.append(person.getDefaultName().getCompositeNameUnmasked());
				message.append(" on your behalf.");
				message.append(SystemUtils.LINE_SEPARATOR);
				message.append(getLeaveCalendarURL(documentId, hrCalendarEntryId));
				
				HrServiceLocator.getKPMENotificationService().sendNotification(subject, message.toString(), targetPrincipalId);
			}
		}
	}
	
	private void generateLeaveBlockDeletionNotification(List<String> approverIdList, String employeeId, String userId, String dateString, String hrString) {
        EntityNamePrincipalName employee = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(employeeId);
        EntityNamePrincipalName user = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(userId);
		if (employee != null
                && user != null
                && employee.getDefaultName() != null
                && user.getDefaultName() != null) {
			String subject = "Leave Request Deletion Notice";
			StringBuilder message = new StringBuilder();
			message.append("An Approved leave request of ").append(hrString).append(" hours on Date ").append(dateString);
			message.append(" for ").append(employee.getDefaultName().getCompositeNameUnmasked()).append(" was deleted by ");
			message.append(user.getDefaultName().getCompositeNameUnmasked());
			for(String anId : approverIdList) {
				HrServiceLocator.getKPMENotificationService().sendNotification(subject, message.toString(), anId);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private String getLeaveCalendarURL(String documentId, String hrCalendarEntryId) {
		Properties params = new Properties();
		params.put("documentId", documentId);
		params.put("hrCalendarEntryId", hrCalendarEntryId);
		return UrlFactory.parameterizeUrl(getApplicationBaseUrl() + "/LeaveCalendar.do", params);
	}
    
    /**
	 * Handles the PAYOUT action of balance transfers issued from the leave calendar with frequency "on demand".
	 * 
	 * This action should be triggered after the user submits to a prompt generated by clicking a "PAYOUT" button on the leave
	 * calendar. This button should only be displayed if, for the current pay period, a max balance has been reached
	 * and the max balance action frequency is set to "On-Demand". The prompt must allow the user to edit the transfer amount.
	 * It may or may not need to show the "to" and "from" accrual categories in the initial prompt, but could on a confirmation
	 * prompt - along with the transfer amount adjusted by the max balance conversion factor.
	 * 
	 * Balance transfers with frequency of leave approval should be handled during the submission of the
	 * leave calendar document for approval and should be automated.
	 * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward payoutOnDemandBalanceTransfer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	/**
    	 * TODO: create one new leave block, if applicable; the amount forfeited by this transfer action.
    	 * 
    	 * The amount transfered, pending adjustment via the max balance conversion factor, will be put into a pay out earn code
    	 * that can be redeemed/used by the employee at a later time.
    	 */
    	
    	return mapping.findForward("basic");
    }

    /**
     * Leave Payout
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward leavePayout(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        return mapping.findForward("basic");
    }

}