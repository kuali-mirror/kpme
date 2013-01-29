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
package org.kuali.hr.lm.leavecalendar.web;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.joda.time.DateTime;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.leavecalendar.validation.LeaveCalendarValidationUtil;
import org.kuali.hr.lm.util.LeaveBlockAggregate;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.lm.workflow.LeaveRequestDocument;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.calendar.LeaveCalendar;
import org.kuali.hr.time.detail.web.ActionFormUtils;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.UrlFactory;

public class LeaveCalendarAction extends TkAction {

	private static final Logger LOG = Logger.getLogger(LeaveCalendarAction.class);

    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        UserRoles roles = TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId());
        LeaveCalendarDocument doc = TKContext.getCurrentLeaveCalendarDocument();

        if (doc != null && !roles.isDocumentReadable(doc)) {
            throw new AuthorizationException(GlobalVariables.getUserSession().getPrincipalId(), "LeaveCalendarAction: docid: " + (doc == null ? "" : doc.getDocumentId()), "");
        }
    }
    
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		String documentId = lcf.getDocumentId();
		
        if (StringUtils.equals(request.getParameter("command"), "displayDocSearchView")
        		|| StringUtils.equals(request.getParameter("command"), "displayActionListView") ) {
        	documentId = (String) request.getParameter("docId");
        }

        LOG.debug("DOCID: " + documentId);
        
		// if the reload was trigger by changing of the selectedPayPeriod, use the passed in parameter as the calendar entry id
		String calendarEntryId = StringUtils.isNotBlank(request.getParameter("selectedPP")) ? request.getParameter("selectedPP") : lcf.getCalEntryId();
		
		// Here - viewPrincipal will be the principal of the user we intend to
		// view, be it target user, backdoor or otherwise.
		String viewPrincipal = TKUser.getCurrentTargetPerson().getPrincipalId();
		CalendarEntries calendarEntry = null;

		LeaveCalendarDocument lcd = null;
		LeaveCalendarDocumentHeader lcdh = null;

		// By handling the prev/next in the execute method, we are saving one
		// fetch/construction of a LeaveCalendarDocument. If it were broken out into
		// methods, we would first fetch the current document, and then fetch
		// the next one instead of doing it in the single action.
		if (StringUtils.isNotBlank(documentId)) {
			lcd = TkServiceLocator.getLeaveCalendarService()
					.getLeaveCalendarDocument(documentId);
			calendarEntry = lcd.getCalendarEntry();
		} else if (StringUtils.isNotBlank(calendarEntryId)) {
			// do further procedure
			calendarEntry = TkServiceLocator.getCalendarEntriesService()
					.getCalendarEntries(calendarEntryId);
		} else {
			// Default to whatever is active for "today".
			Date currentDate = TKUtils.getTimelessDate(null);
			calendarEntry = TkServiceLocator.getCalendarService()
					.getCurrentCalendarDatesForLeaveCalendar(viewPrincipal, currentDate);
		}
		lcf.setCalendarEntry(calendarEntry);
		if(calendarEntry != null) {
			lcf.setCalEntryId(calendarEntry.getHrCalendarEntriesId());
		}
		// check configuration setting for allowing accrual service to be ran from leave calendar
		String runAccrualFlag = ConfigContext.getCurrentContextConfig().getProperty(LMConstants.RUN_ACCRUAL_FROM_CALENDAR);
		if(StringUtils.equals(runAccrualFlag, "true")) {
			// run accrual for future dates only, use planning month of leave plan for accrual period
			// only run the accrual if the calendar entry contains future dates
			if(calendarEntry != null && calendarEntry.getEndPeriodDate().after(TKUtils.getCurrentDate())) {
				if(TkServiceLocator.getLeaveAccrualService().statusChangedSinceLastRun(viewPrincipal)) {
					TkServiceLocator.getLeaveAccrualService().calculateFutureAccrualUsingPlanningMonth(viewPrincipal, calendarEntry.getBeginPeriodDate());
				}
			}
		}
		
		if(lcd == null) {
			// use jobs to find out if this leave calendar should have a document created or not
			boolean createFlag = TkServiceLocator.getLeaveCalendarService().shouldCreateLeaveDocument(viewPrincipal, calendarEntry);
			if(createFlag) {
				lcd = TkServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument(viewPrincipal, calendarEntry);
			}
		}
		List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignmentsByCalEntryForLeaveCalendar(viewPrincipal, calendarEntry);
		List<String> assignmentKeys = new ArrayList<String>();
        for(Assignment assign : assignments) {
        	assignmentKeys.add(assign.getAssignmentKey());
        }
		if (lcd != null) {
			lcf.setDocumentId(lcd.getDocumentId());
			lcf.setAssignmentDescriptions(TkServiceLocator.getAssignmentService().getAssignmentDescriptions(lcd));
            lcdh = lcd.getDocumentHeader();
		} else {
			lcf.setAssignmentDescriptions(TkServiceLocator.getAssignmentService().getAssignmentDescriptionsForAssignments(assignments));  
		}
		setupDocumentOnFormContext(lcf, lcd);
		ActionForward forward = super.execute(mapping, form, request, response);
		//no window exists if mapping->forward = closeBalanceTransferDoc.
		if (forward.getRedirect()) {
			return forward;
		}

        LeaveCalendar calendar = null;
        if (calendarEntry != null) {
            calendar = new LeaveCalendar(viewPrincipal, calendarEntry, assignmentKeys);
            lcf.setLeaveCalendar(calendar);
        }
		
		this.populateCalendarAndPayPeriodLists(request, lcf);

		// KPME-1447
        List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
        if (lcdh != null && lcdh.getPrincipalId() != null && lcdh.getBeginDate() != null && lcdh.getEndDate() != null) {
            leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForLeaveCalendar(lcdh.getPrincipalId(), lcdh.getBeginDate(), lcdh.getEndDate(), assignmentKeys);
        } else if(calendarEntry != null){
            leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForLeaveCalendar(viewPrincipal, calendarEntry.getBeginPeriodDate(), calendarEntry.getEndPeriodDate(), assignmentKeys);
        } 
        
        // leave summary
        if (calendarEntry != null) {
            LeaveSummary ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(viewPrincipal, calendarEntry);
            lcf.setLeaveSummary(ls);
        }
        
        // add warning messages based on earn codes of leave blocks
        List<String> warningMes = LeaveCalendarValidationUtil.getWarningMessagesForLeaveBlocks(leaveBlocks);
        
        // add warning message for accrual categories that have exceeded max balance.
        List<String> transfers = new ArrayList<String>();
        // Could set a flag on the transferable rows here so that LeaveCalendarSubmit.do knows
        // which row(s) to transfer when user submits the calendar for approval.
        transfers.addAll(TkServiceLocator.getBalanceTransferService().getEligibleTransfers(lcf.getLeaveCalendarDocument(), LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE));
        transfers.addAll(TkServiceLocator.getBalanceTransferService().getEligibleTransfers(lcf.getLeaveCalendarDocument(), LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END));
        transfers.addAll(TkServiceLocator.getBalanceTransferService().getEligibleTransfers(lcf.getLeaveCalendarDocument(), LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND));
        boolean btDocExists = false;
        boolean lpDocExists = false;
        boolean pendingBTDocumentExists = !TkServiceLocator.getLeaveCalendarService().isReadyToApprove(lcf.getLeaveCalendarDocument())
                    && lcf.getLeaveCalendarDocument() != null;

        for(LeaveBlock leaveBlock : leaveBlocks) {
        	if(leaveBlock.getLeaveBlockType().equals(LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER) &&
        			!leaveBlock.getDescription().contains("Max carry over adjustment"))
        		btDocExists = true;
            else if(leaveBlock.getLeaveBlockType().equals(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_PAYOUT))
            	lpDocExists = true;
        }
        if(btDocExists
                && !pendingBTDocumentExists ) {
        	warningMes.add("A balance transfer document for this calendar exists");
        }
        if(lpDocExists) {
        	warningMes.add("A leave payout document for this calendar exists");
        }

        if(!transfers.isEmpty()) {
        	warningMes.add("You have exceeded the balance limit for one or more accrual categories within your leave plan.");
        	warningMes.add("Depending upon the rules of your institution, you may lose any leave over this limit.");
        }
        
        // add warning messages based on max carry over balances for each accrual category
        PrincipalHRAttributes principalCalendar = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(viewPrincipal, calendarEntry.getEndPeriodDate());
		if (principalCalendar != null) {
			List<AccrualCategory> accrualCategories = TkServiceLocator.getAccrualCategoryService().getActiveLeaveAccrualCategoriesForLeavePlan(principalCalendar.getLeavePlan(), new java.sql.Date(calendarEntry.getEndPeriodDate().getTime()));
			for (AccrualCategory accrualCategory : accrualCategories) {
				if (TkServiceLocator.getAccrualCategoryMaxCarryOverService().exceedsAccrualCategoryMaxCarryOver(accrualCategory.getAccrualCategory(), viewPrincipal, calendarEntry, calendarEntry.getEndPeriodDate())) {
					String message = "Your pending leave balance is greater than the annual max carry over for accrual category '" + accrualCategory.getAccrualCategory() + "' and upon approval, the excess balance will be lost.";
					if (!warningMes.contains(message)) {
						warningMes.add(message);
					}
				}
			}
		}
		
        lcf.setWarnings(warningMes);
        
		// KPME-1690
//        LeaveCalendar leaveCalender = new LeaveCalendar(viewPrincipal, calendarEntry);
        if (calendarEntry != null) {
            LeaveBlockAggregate aggregate = new LeaveBlockAggregate(leaveBlocks, calendarEntry, calendar);
            lcf.setLeaveBlockString(LeaveActionFormUtils.getLeaveBlocksJson(aggregate.getFlattenedLeaveBlockList()));
        }
        //lcf.setLeaveBlockString(ActionFormUtils.getLeaveBlocksJson(aggregate.getFlattenedLeaveBlockList()));
		
//        System.out.println("Leave block string : "+lcf.getLeaveBlockString());
		return forward;
	}
	
	private void populateCalendarAndPayPeriodLists(HttpServletRequest request, LeaveCalendarForm lcf) {
		
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        // find all the calendar entries up to the planning months of this employee
        List<CalendarEntries> ceList = lcf.getCalendarEntry() == null ? new ArrayList<CalendarEntries>() : TkServiceLocator.getCalendarEntriesService()
        	.getAllCalendarEntriesForCalendarIdUpToPlanningMonths(lcf.getCalendarEntry().getHrCalendarId(), TKUser.getCurrentTargetPerson().getPrincipalId());
        
        if(lcf.getCalendarYears().isEmpty()) {
        	// get calendar year drop down list contents
	        Set<String> yearSet = new HashSet<String>();
	        for(CalendarEntries ce : ceList) {
	        	yearSet.add(sdf.format(ce.getBeginPeriodDate()));
	        }
	        List<String> yearList = new ArrayList<String>(yearSet);
	        Collections.sort(yearList);
	        Collections.reverse(yearList);	// newest on top
	        lcf.setCalendarYears(yearList);
        }
        // if selected calendar year is passed in
        if(request.getParameter("selectedCY")!= null) {
        	lcf.setSelectedCalendarYear(request.getParameter("selectedCY").toString());
        }
        // if there is no selected calendr year, use the year of current pay calendar entry
        if(StringUtils.isEmpty(lcf.getSelectedCalendarYear())
                && lcf.getCalendarEntry() != null) {
        	lcf.setSelectedCalendarYear(sdf.format(lcf.getCalendarEntry().getBeginPeriodDate()));
        }
        if(lcf.getPayPeriodsMap().isEmpty()) {
      	List<CalendarEntries> yearCEList = ActionFormUtils.getAllCalendarEntriesForYear(ceList, lcf.getSelectedCalendarYear());
	        lcf.setPayPeriodsMap(ActionFormUtils.getPayPeriodsMap(yearCEList));
        }
        if(request.getParameter("selectedPP")!= null) {
        	lcf.setSelectedPayPeriod(request.getParameter("selectedPP").toString());
        }
        if(StringUtils.isEmpty(lcf.getSelectedPayPeriod())
                && lcf.getCalendarEntry() != null) {
        	lcf.setSelectedPayPeriod(lcf.getCalendarEntry().getHrCalendarEntriesId());
        }
	}	

	public ActionForward addLeaveBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		LeaveCalendarDocument lcd = lcf.getLeaveCalendarDocument();
		
		String principalId = TKContext.getPrincipalId();
		String targetPrincipalId = TKContext.getTargetPrincipalId();
		CalendarEntries calendarEntry = lcf.getCalendarEntry();
		String selectedAssignment = lcf.getSelectedAssignment();
		DateTime beginDate = new DateTime(TKUtils.convertDateStringToTimestampNoTimezone(lcf.getStartDate()));
		DateTime endDate = new DateTime(TKUtils.convertDateStringToTimestampNoTimezone(lcf.getEndDate()));
		String selectedEarnCode = lcf.getSelectedEarnCode();
		BigDecimal hours = lcf.getLeaveAmount();
		String desc = lcf.getDescription();
		String spanningWeeks = lcf.getSpanningWeeks();  // KPME-1446
		
		String documentId = lcd != null ? lcd.getDocumentId() : "";
		
		Assignment assignment = null;
		if(lcd != null) {
			assignment = TkServiceLocator.getAssignmentService().getAssignment(lcd, selectedAssignment);
		} else {
			List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignmentsByCalEntryForLeaveCalendar(targetPrincipalId, calendarEntry);
			assignment = TkServiceLocator.getAssignmentService().getAssignment(assignments, selectedAssignment, calendarEntry.getBeginPeriodDate());
		}

		TkServiceLocator.getLeaveBlockService().addLeaveBlocks(beginDate, endDate, calendarEntry, selectedEarnCode, hours, desc, assignment, spanningWeeks, 
				LMConstants.LEAVE_BLOCK_TYPE.LEAVE_CALENDAR, targetPrincipalId);

		generateLeaveCalendarChangedNotification(principalId, targetPrincipalId, documentId, calendarEntry.getHrCalendarEntriesId());
		
		// after adding the leave block, set the fields of this form to null for future new leave blocks
		lcf.setLeaveAmount(null);
		lcf.setDescription(null);
		
		// call accrual service if earn code is not eligible for accrual
		if(calendarEntry != null) {
			java.sql.Date sqlDate = new java.sql.Date(endDate.getMillis());
			this.rerunAccrualForNotEligibleForAccrualChanges(selectedEarnCode, sqlDate, calendarEntry.getBeginPeriodDate(), calendarEntry.getEndPeriodDate());
		 }
		// recalculate summary
		if (calendarEntry != null) {
			LeaveSummary ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(targetPrincipalId, calendarEntry);
		    lcf.setLeaveSummary(ls);
		}
		
		return mapping.findForward("basic");
	}

	public ActionForward deleteLeaveBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		LeaveCalendarDocument lcd = lcf.getLeaveCalendarDocument();

		String principalId = TKContext.getPrincipalId();
		String targetPrincipalId = TKContext.getTargetPrincipalId();
		CalendarEntries calendarEntry = lcf.getCalendarEntry();
		String leaveBlockId = lcf.getLeaveBlockId();
		
		String documentId = lcd != null ? lcd.getDocumentId() : "";

        LeaveBlock blockToDelete = TkServiceLocator.getLeaveBlockService().getLeaveBlock(leaveBlockId);
        if (blockToDelete != null && TkServiceLocator.getPermissionsService().canDeleteLeaveBlock(blockToDelete)) {
        	//if leave block is a pending leave request, cancel the leave request document
        	if(blockToDelete.getRequestStatus().equals(LMConstants.REQUEST_STATUS.REQUESTED)) {
        		List<LeaveRequestDocument> lrdList = TkServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocumentsByLeaveBlockId(blockToDelete.getLmLeaveBlockId());
        		if(CollectionUtils.isNotEmpty(lrdList)) {
        			for(LeaveRequestDocument lrd : lrdList) { 
        				DocumentStatus status = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(lrd.getDocumentNumber());
        				if(DocumentStatus.ENROUTE.getCode().equals(status.getCode())) {
        					// cancel the leave request document as the employee.
        					TkServiceLocator.getLeaveRequestDocumentService().recallAndCancelLeave(lrd.getDocumentNumber(), targetPrincipalId, "Leave block deleted by user " + principalId);
        				}
        			}
        		}
        	}
        	
        	List<String> approverList = new ArrayList<String>();
        	//if leave block is an approved leave request, get list of approver's id
        	if(blockToDelete.getRequestStatus().equals(LMConstants.REQUEST_STATUS.APPROVED)) {
        		List<LeaveRequestDocument> lrdList = TkServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocumentsByLeaveBlockId(blockToDelete.getLmLeaveBlockId());
        		if(CollectionUtils.isNotEmpty(lrdList)) {
        			for(LeaveRequestDocument lrd : lrdList) { 
        				DocumentStatus status = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(lrd.getDocumentNumber());
        				if(DocumentStatus.FINAL.getCode().equals(status.getCode())) {
        					// get approver's id for sending out email notification later
        					approverList = TkServiceLocator.getLeaveRequestDocumentService().getApproverIdList(lrd.getDocumentNumber());
        				}
        			}
        		}
        	}
        	
        	TkServiceLocator.getLeaveBlockService().deleteLeaveBlock(leaveBlockId, principalId);
		    generateLeaveCalendarChangedNotification(principalId, targetPrincipalId, documentId, calendarEntry.getHrCalendarEntriesId());
		    if(CollectionUtils.isNotEmpty(approverList)) {
		    	this.generateLeaveBlockDeletionNotification(approverList, targetPrincipalId, principalId, TKUtils.formatDate(blockToDelete.getLeaveDate()), blockToDelete.getLeaveAmount().toString());
		    }
        	
		    // recalculate accruals
		    if(lcf.getCalendarEntry() != null) {
		    	rerunAccrualForNotEligibleForAccrualChanges(blockToDelete.getEarnCode(), blockToDelete.getLeaveDate(), calendarEntry.getBeginPeriodDate(), calendarEntry.getEndPeriodDate());
		    }	
        }
		// recalculate summary
		if(lcf.getCalendarEntry() != null) {
			LeaveSummary ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(targetPrincipalId, calendarEntry);
		    lcf.setLeaveSummary(ls);
		}
		return mapping.findForward("basic");
	}
	
	/**
	 * Recalculate accrual when a leave block with not-eligible-for-accrual earn code is added or deleted
	 * calculate accrual only for the calendar entry period
	 * @param earnCode
	 * @param asOfDate
	 * @param startDate
	 * @param endDate
	 */
	private void rerunAccrualForNotEligibleForAccrualChanges(String earnCode, Date asOfDate, Date startDate, Date endDate) {
		EarnCode ec = TkServiceLocator.getEarnCodeService().getEarnCode(earnCode, asOfDate);
		if(ec != null && ec.getEligibleForAccrual().equals("N")) {
			if(startDate != null && endDate != null) {
				// since we are only recalculating accrual for this pay period, we use "false" to not record the accrual run data
				TkServiceLocator.getLeaveAccrualService().runAccrual(TKContext.getTargetPrincipalId(), startDate, endDate, false);
			}
		}
	}
	
	// KPME-1447
	public ActionForward updateLeaveBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		LeaveCalendarDocument lcd = lcf.getLeaveCalendarDocument();
		
		String principalId = TKContext.getPrincipalId();
		String targetPrincipalId = TKContext.getTargetPrincipalId();
		CalendarEntries calendarEntry = lcf.getCalendarEntry();
		String selectedEarnCode = lcf.getSelectedEarnCode();
		String leaveBlockId = lcf.getLeaveBlockId();
		
		String documentId = lcd != null ? lcd.getDocumentId() : "";
		
		LeaveBlock updatedLeaveBlock = null;
		updatedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(leaveBlockId);
        if (updatedLeaveBlock.isEditable()) {
            if (StringUtils.isNotBlank(lcf.getDescription())) {
                updatedLeaveBlock.setDescription(lcf.getDescription().trim());
            }
            if (!updatedLeaveBlock.getLeaveAmount().equals(lcf.getLeaveAmount())) {
                updatedLeaveBlock.setLeaveAmount(lcf.getLeaveAmount());
            }
            EarnCode earnCode =  TkServiceLocator.getEarnCodeService().getEarnCode(selectedEarnCode, updatedLeaveBlock.getLeaveDate()); // selectedEarnCode = hrEarnCodeId
            if (!updatedLeaveBlock.getEarnCode().equals(earnCode.getEarnCode())) {
                updatedLeaveBlock.setEarnCode(earnCode.getEarnCode());
            }
            TkServiceLocator.getLeaveBlockService().updateLeaveBlock(updatedLeaveBlock, principalId);
            generateLeaveCalendarChangedNotification(principalId, targetPrincipalId, documentId, calendarEntry.getHrCalendarEntriesId());
            
            lcf.setLeaveAmount(null);
            lcf.setDescription(null);
            lcf.setSelectedEarnCode(null);
    		// recalculate summary
    		if(lcf.getCalendarEntry() != null) {
    			LeaveSummary ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(targetPrincipalId, calendarEntry);
    		    lcf.setLeaveSummary(ls);
    		}
        }
        return mapping.findForward("basic");
    }

	protected void setupDocumentOnFormContext(LeaveCalendarForm leaveForm,
			LeaveCalendarDocument lcd) {
		CalendarEntries futureCalEntry = null;
		String viewPrincipal = TKUser.getCurrentTargetPerson().getPrincipalId();
		CalendarEntries calEntry = leaveForm.getCalendarEntry();

		// some leave calendar may not have leaveCalendarDocument created based on the jobs status of this employee
		if(lcd != null) {
			if (lcd.getDocumentHeader() != null) {
				TKContext.setCurrentLeaveCalendarDocumentId(lcd.getDocumentId());
				leaveForm.setDocumentId(lcd.getDocumentId());
			}
			TKContext.setCurrentLeaveCalendarDocument(lcd);
	        TKContext.setCurrentLeaveCalendarDocumentId(lcd.getDocumentId());
			leaveForm.setLeaveCalendarDocument(lcd);
	        leaveForm.setDocumentId(lcd.getDocumentId());
	        calEntry = lcd.getCalendarEntry();
		}
	// -- put condition if it is after current period
		boolean isFutureDate = calEntry != null && TKUtils.getTimelessDate(null).compareTo(calEntry.getEndPeriodDateTime()) <= 0;
		
		// fetch previous entry
        if (calEntry != null) {
            CalendarEntries calPreEntry = TkServiceLocator
                    .getCalendarEntriesService()
                    .getPreviousCalendarEntriesByCalendarId(
                            calEntry.getHrCalendarId(),
                            calEntry);
            if (calPreEntry != null) {
                leaveForm.setPrevCalEntryId(calPreEntry
                        .getHrCalendarEntriesId());
            }

            int planningMonths = ActionFormUtils.getPlanningMonthsForEmployee(viewPrincipal);
            if(planningMonths != 0) {
                List<CalendarEntries> futureCalEntries = TkServiceLocator
                        .getCalendarEntriesService()
                        .getFutureCalendarEntries(
                                calEntry.getHrCalendarId(),
                                TKUtils.getTimelessDate(null),
                                planningMonths);

                if (futureCalEntries != null && !futureCalEntries.isEmpty()) {
                    futureCalEntry = futureCalEntries.get(futureCalEntries
                            .size() - 1);

                    CalendarEntries calNextEntry = TkServiceLocator
                            .getCalendarEntriesService()
                            .getNextCalendarEntriesByCalendarId(
                                    calEntry.getHrCalendarId(),
                                    calEntry);

                    if (calNextEntry != null
                            && futureCalEntries != null
                            && calNextEntry
                                    .getBeginPeriodDateTime()
                                    .compareTo(
                                            futureCalEntry
                                                    .getBeginPeriodDateTime()) <= 0) {
                        leaveForm.setNextCalEntryId(calNextEntry
                                .getHrCalendarEntriesId());
                    }
                }
            }
        }
		if(leaveForm.getViewLeaveTabsWithNEStatus()) {
			if(isFutureDate) {
                setDocEditable(leaveForm, lcd);
			} else {
				// retrieve current pay calendar date
				Date currentDate = TKUtils.getTimelessDate(null);
				CalendarEntries calendarEntry = TkServiceLocator.getCalendarService()
						.getCurrentCalendarDatesForLeaveCalendar(viewPrincipal, currentDate);
				if(calendarEntry != null) {
					leaveForm.setCurrentPayCalStart(calendarEntry.getBeginLocalDateTime().toDateTime(TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback()));
					leaveForm.setCurrentPayCalEnd(calendarEntry.getEndLocalDateTime().toDateTime(TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback()));
				}
			}
		} else {
            setDocEditable(leaveForm, lcd);
		}
		leaveForm.setCalendarEntry(calEntry);
		if(calEntry != null) {
			leaveForm.setCalEntryId(calEntry.getHrCalendarEntriesId());
		}
		leaveForm.setOnCurrentPeriod(ActionFormUtils.getOnCurrentPeriodFlag(calEntry));

	}

    private void setDocEditable(LeaveCalendarForm leaveForm, LeaveCalendarDocument lcd) {
    	leaveForm.setDocEditable(false);
    	if(lcd == null) {
    		// working on own calendar
    		 if(TKUser.getCurrentTargetPerson().getPrincipalId().equals(GlobalVariables.getUserSession().getPrincipalId())) {
    			 leaveForm.setDocEditable(true); 
    		 } else {
    			 if(TKContext.getUser().isSystemAdmin()
                     || TKContext.getUser().isLocationAdmin()
                     || TKContext.getUser().isReviewer()
                     || TKContext.getUser().isApprover()) {
    				 	leaveForm.setDocEditable(true);
    			 }
             }
    	} else {
	        if (TKContext.getUser().isSystemAdmin() && !StringUtils.equals(lcd.getPrincipalId(), GlobalVariables.getUserSession().getPrincipalId())) {
	            leaveForm.setDocEditable(true);
	        } else {
	            boolean docFinal = lcd.getDocumentHeader().getDocumentStatus().equals(TkConstants.ROUTE_STATUS.FINAL);
	            if (!docFinal) {
	                if(StringUtils.equals(lcd.getPrincipalId(), GlobalVariables.getUserSession().getPrincipalId())
	                        || TKContext.getUser().isSystemAdmin()
	                        || TKContext.getUser().isLocationAdmin()
	                        || TKContext.getUser().isReviewer()
	                        || TKContext.getUser().isApprover()) {
	                    leaveForm.setDocEditable(true);
	                }
	
	                //if the leave Calendar has been approved by at least one of the approvers, the employee should not be able to edit it
	                if (StringUtils.equals(lcd.getPrincipalId(), GlobalVariables.getUserSession().getPrincipalId())
	                        && lcd.getDocumentHeader().getDocumentStatus().equals(TkConstants.ROUTE_STATUS.ENROUTE)) {
	                    Collection actions = KEWServiceLocator.getActionTakenService().findByDocIdAndAction(lcd.getDocumentHeader().getDocumentId(), TkConstants.DOCUMENT_ACTIONS.APPROVE);
	                    if(!actions.isEmpty()) {
	                        leaveForm.setDocEditable(false);
	                    }
	                }
	            }
	        }
    	}
    }
	
	public ActionForward gotoCurrentPayPeriod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		String viewPrincipal = TKUser.getCurrentTargetPerson().getPrincipalId();
		Date currentDate = TKUtils.getTimelessDate(null);
		CalendarEntries calendarEntry = TkServiceLocator.getCalendarService().getCurrentCalendarDatesForLeaveCalendar(viewPrincipal, currentDate);
		lcf.setCalendarEntry(calendarEntry);
		if(calendarEntry != null) {
			lcf.setCalEntryId(calendarEntry.getHrCalendarEntriesId());
		}
		lcf.setOnCurrentPeriod(ActionFormUtils.getOnCurrentPeriodFlag(calendarEntry));
	
		LeaveCalendarDocument lcd = null;
		// use jobs to find out if this leave calendar should have a document created or not
		boolean createFlag = TkServiceLocator.getLeaveCalendarService().shouldCreateLeaveDocument(viewPrincipal, calendarEntry);
		if(createFlag) {
			 lcd = TkServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument(viewPrincipal, calendarEntry);
		}
		if (lcd != null) {
			lcf.setAssignmentDescriptions(TkServiceLocator.getAssignmentService().getAssignmentDescriptions(lcd));
		} else {
			List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignmentsByCalEntryForLeaveCalendar(viewPrincipal, calendarEntry);
			lcf.setAssignmentDescriptions(TkServiceLocator.getAssignmentService().getAssignmentDescriptionsForAssignments(assignments));  
		}
		setupDocumentOnFormContext(lcf, lcd);
		return mapping.findForward("basic");
	  }
	
	//Triggered by changes of pay period drop down list, reload the whole page based on the selected pay period
	public ActionForward changeCalendarYear(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		  
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		if(request.getParameter("selectedCY") != null) {
			lcf.setSelectedCalendarYear(request.getParameter("selectedCY").toString());
		}
		return mapping.findForward("basic");
	}
	  
	//Triggered by changes of pay period drop down list, reload the whole page based on the selected pay period
	public ActionForward changePayPeriod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		if(request.getParameter("selectedPP") != null) {
			lcf.setSelectedPayPeriod(request.getParameter("selectedPP").toString());
	        CalendarEntries ce = TkServiceLocator.getCalendarEntriesService()
				.getCalendarEntries(request.getParameter("selectedPP").toString());
			if(ce != null) {
				String viewPrincipal = TKUser.getCurrentTargetPerson().getPrincipalId();
				lcf.setCalEntryId(ce.getHrCalendarEntriesId());
				LeaveCalendarDocument lcd = null;
				// use jobs to find out if this leave calendar should have a document created or not
				boolean createFlag = TkServiceLocator.getLeaveCalendarService().shouldCreateLeaveDocument(viewPrincipal, ce);
				if(createFlag) {
					 lcd = TkServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument(viewPrincipal, ce);
				}
				if(lcd != null) {
					lcf.setAssignmentDescriptions(TkServiceLocator.getAssignmentService().getAssignmentDescriptions(lcd));
				} else {
					List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignmentsByCalEntryForLeaveCalendar(viewPrincipal, ce);
					lcf.setAssignmentDescriptions(TkServiceLocator.getAssignmentService().getAssignmentDescriptionsForAssignments(assignments));  
				}
				setupDocumentOnFormContext(lcf, lcd);
			}
		}
		return mapping.findForward("basic");
	}
	
	private void generateLeaveCalendarChangedNotification(String principalId, String targetPrincipalId, String documentId, String hrCalendarEntryId) {
		if (!StringUtils.equals(principalId, targetPrincipalId)) {
			Person person = KimApiServiceLocator.getPersonService().getPerson(principalId);
			if (person != null) {
				String subject = "Leave Calendar Modification Notice";
				StringBuilder message = new StringBuilder();
				message.append("Your Leave Calendar was changed by ");
				message.append(person.getNameUnmasked());
				message.append(" on your behalf.");
				message.append(SystemUtils.LINE_SEPARATOR);
				message.append(getLeaveCalendarURL(documentId, hrCalendarEntryId));
				
				TkServiceLocator.getKPMENotificationService().sendNotification(subject, message.toString(), targetPrincipalId);
			}
		}
	}
	
	private void generateLeaveBlockDeletionNotification(List<String> approverIdList, String employeeId, String userId, String dateString, String hrString) {
		Person employee = KimApiServiceLocator.getPersonService().getPerson(employeeId);
		Person user = KimApiServiceLocator.getPersonService().getPerson(userId);
		if (employee != null && user != null) {
			String subject = "Leave Request Deletion Notice";
			StringBuilder message = new StringBuilder();
			message.append("An Approved leave request of " + hrString +" hours on Date " + dateString);
			message.append(" for " + employee.getNameUnmasked() +" was deleted by ");
			message.append(user.getNameUnmasked());
			for(String anId : approverIdList) {
				TkServiceLocator.getKPMENotificationService().sendNotification(subject, message.toString(), anId);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private String getLeaveCalendarURL(String documentId, String hrCalendarEntryId) {
		Properties params = new Properties();
		params.put("documentId", documentId);
		params.put("calEntryId", hrCalendarEntryId);
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
    
    public ActionForward docHandler(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = mapping.findForward("basic");
        String command = request.getParameter("command");
        
    	if (StringUtils.equals(command, "displayDocSearchView") || StringUtils.equals(command, "displayActionListView")) {
        	String docId = (String) request.getParameter("docId");
        	LeaveCalendarDocument leaveCalendarDocument = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(docId);
        	String timesheetPrincipalName = KimApiServiceLocator.getPersonService().getPerson(leaveCalendarDocument.getPrincipalId()).getPrincipalName();
        	
        	String principalId = TKUser.getCurrentTargetPerson().getPrincipalId();
        	String principalName = KimApiServiceLocator.getPersonService().getPerson(principalId).getPrincipalName();
        	
        	StringBuilder builder = new StringBuilder();
        	if (!StringUtils.equals(principalName, timesheetPrincipalName)) {
        		if (StringUtils.equals(command, "displayDocSearchView")) {
            		builder.append("changeTargetPerson.do?methodToCall=changeTargetPerson");
            		builder.append("&documentId=");
            		builder.append(docId);
            		builder.append("&principalName=");
            		builder.append(timesheetPrincipalName);
            		builder.append("&targetUrl=LeaveCalendar.do");
            		builder.append("?docmentId=" + docId);
            		builder.append("&returnUrl=LeaveApproval.do");
            	} else {
            		builder.append("LeaveApproval.do");
            	}
        	} else {
        		builder.append("LeaveCalendar.do");
        		builder.append("?docmentId=" + docId);
        	}
        	
        	forward = new ActionRedirect(builder.toString());
        }
    	
    	return forward;
    }

}
