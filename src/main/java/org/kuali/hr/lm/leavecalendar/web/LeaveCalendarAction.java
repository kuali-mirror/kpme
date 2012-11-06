/**
 * Copyright 2004-2012 The Kuali Foundation
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
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.lm.util.LeaveBlockAggregate;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.calendar.LeaveCalendar;
import org.kuali.hr.time.detail.web.ActionFormUtils;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

public class LeaveCalendarAction extends TkAction {

	private static final Logger LOG = Logger
			.getLogger(LeaveCalendarAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;

		String documentId = lcf.getDocumentId();
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
		if (forward.getRedirect()) {
			return forward;
		}

		LeaveCalendar calendar = new LeaveCalendar(viewPrincipal, calendarEntry, assignmentKeys);
		lcf.setLeaveCalendar(calendar);
		
		this.populateCalendarAndPayPeriodLists(request, lcf);
		// KPME-1447
        List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
        if (lcdh != null && lcdh.getPrincipalId() != null && lcdh.getBeginDate() != null && lcdh.getEndDate() != null) {
            leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForLeaveCalendar(lcdh.getPrincipalId(), lcdh.getBeginDate(), lcdh.getEndDate(), assignmentKeys);
        } else if(calendarEntry != null){
            leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForLeaveCalendar(viewPrincipal, calendarEntry.getBeginPeriodDate(), calendarEntry.getEndPeriodDate(), assignmentKeys);
        } 
        
        // add warning messages based on earn codes of leave blocks
        List<String> warningMes = ActionFormUtils.fmlaWarningTextForLeaveBlocks(leaveBlocks);
        lcf.setWarnings(warningMes);
        
        // leave summary 
        LeaveSummary ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(viewPrincipal, calendarEntry);
        lcf.setLeaveSummary(ls);
        
		// KPME-1690
//        LeaveCalendar leaveCalender = new LeaveCalendar(viewPrincipal, calendarEntry);
        LeaveBlockAggregate aggregate = new LeaveBlockAggregate(leaveBlocks, calendarEntry, calendar);
        lcf.setLeaveBlockString(LeaveActionFormUtils.getLeaveBlocksJson(aggregate.getFlattenedLeaveBlockList()));
        //lcf.setLeaveBlockString(ActionFormUtils.getLeaveBlocksJson(aggregate.getFlattenedLeaveBlockList()));
		
//        System.out.println("Leave block string : "+lcf.getLeaveBlockString());
		return forward;
	}
	
	private void populateCalendarAndPayPeriodLists(HttpServletRequest request, LeaveCalendarForm lcf) {
		
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        // find all the calendar entries up to the planning months of this employee
        List<CalendarEntries> ceList = TkServiceLocator.getCalendarEntriesService()
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
        if(StringUtils.isEmpty(lcf.getSelectedCalendarYear())) {
        	lcf.setSelectedCalendarYear(sdf.format(lcf.getCalendarEntry().getBeginPeriodDate()));
        }
        if(lcf.getPayPeriodsMap().isEmpty()) {
      	List<CalendarEntries> yearCEList = ActionFormUtils.getAllCalendarEntriesForYear(ceList, lcf.getSelectedCalendarYear());
	        lcf.setPayPeriodsMap(ActionFormUtils.getPayPeriodsMap(yearCEList));
        }
        if(request.getParameter("selectedPP")!= null) {
        	lcf.setSelectedPayPeriod(request.getParameter("selectedPP").toString());
        }
        if(StringUtils.isEmpty(lcf.getSelectedPayPeriod())) {
        	lcf.setSelectedPayPeriod(lcf.getCalendarEntry().getHrCalendarEntriesId());
        }
	}
	

	public ActionForward addLeaveBlock(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		LeaveCalendarDocument lcd = lcf.getLeaveCalendarDocument();
		DateTime beginDate = new DateTime(
				TKUtils.convertDateStringToTimestampNoTimezone(lcf.getStartDate()));
		DateTime endDate = new DateTime(
				TKUtils.convertDateStringToTimestampNoTimezone(lcf.getEndDate()));
		String selectedEarnCode = lcf.getSelectedEarnCode();
		BigDecimal hours = lcf.getLeaveAmount();
		String desc = lcf.getDescription();
		String spanningWeeks = lcf.getSpanningWeeks();  // KPME-1446
		Assignment assignment = null;
		if(lcd != null) {
			assignment = TkServiceLocator.getAssignmentService().getAssignment(lcd, lcf.getSelectedAssignment());
		} else {
			List<Assignment> assignments = TkServiceLocator.getAssignmentService()
					.getAssignmentsByCalEntryForLeaveCalendar(TKUser.getCurrentTargetPerson().getPrincipalId(), lcf.getCalendarEntry());
			assignment = TkServiceLocator.getAssignmentService()
					.getAssignment(assignments, lcf.getSelectedAssignment(), lcf.getCalendarEntry().getBeginPeriodDate());
		}

		TkServiceLocator.getLeaveBlockService().addLeaveBlocks(beginDate,
				endDate, lcf.getCalendarEntry(), selectedEarnCode, hours, desc, assignment, spanningWeeks, LMConstants.LEAVE_BLOCK_TYPE.LEAVE_CALENDAR); // KPME-1446
		// after adding the leave block, set the fields of this form to null for future new leave blocks
		lcf.setLeaveAmount(null);
		lcf.setDescription(null);
		
		// call accrual service if earn code is not eligible for accrual
		if(lcf.getCalendarEntry() != null) {
			java.sql.Date sqlDate = new java.sql.Date(endDate.getMillis());
			this.rerunAccrualForNotEligibleForAccrualChanges(selectedEarnCode, sqlDate, lcf.getCalendarEntry().getBeginPeriodDate());
		 }
		// recalculate summary
		if(lcf.getCalendarEntry() != null) {
			LeaveSummary ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(TKContext.getTargetPrincipalId(), lcf.getCalendarEntry());
		    lcf.setLeaveSummary(ls);
		}
		
		return mapping.findForward("basic");
	}

	public ActionForward deleteLeaveBlock(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		String leaveBlockId = lcf.getLeaveBlockId();

        LeaveBlock blockToDelete = TkServiceLocator.getLeaveBlockService().getLeaveBlock(leaveBlockId);
        if (blockToDelete != null
                && TkServiceLocator.getPermissionsService().canDeleteLeaveBlock(blockToDelete)) {
		    TkServiceLocator.getLeaveBlockService().deleteLeaveBlock(leaveBlockId);
			 // recalculate accruals
		    if(lcf.getCalendarEntry() != null) {
		    	this.rerunAccrualForNotEligibleForAccrualChanges(blockToDelete.getEarnCode(), blockToDelete.getLeaveDate(), 
		    		lcf.getCalendarEntry().getBeginPeriodDate());
		    }	
        }
		// recalculate summary
		if(lcf.getCalendarEntry() != null) {
			LeaveSummary ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(TKContext.getTargetPrincipalId(), lcf.getCalendarEntry());
		    lcf.setLeaveSummary(ls);
		}
		return mapping.findForward("basic");
	}
	
	/**
	 * Recalculate accrual when a leave block with not-eligible-for-accrual earn code is added or deleted
	 * calculate accrual starting from the begin date of the affected calendar entry up to planningMonths in the future
	 * @param earnCode
	 * @param asOfDate
	 * @param startDate
	 */
	private void rerunAccrualForNotEligibleForAccrualChanges(String earnCode, Date asOfDate, Date startDate) {
		EarnCode ec = TkServiceLocator.getEarnCodeService().getEarnCode(earnCode, asOfDate);
		if(ec != null && ec.getEligibleForAccrual().equals("N")) {
			if(startDate != null) {
				int planningMonth = ActionFormUtils.getPlanningMonthsForEmployee(TKContext.getTargetPrincipalId());
				Date endDate = new java.sql.Date(TKUtils.addMonths(TKUtils.getCurrentDate(), planningMonth).getTime());
				// since we are only recalculation accrual for this pay period and future, we do not record the accrual run data
				TkServiceLocator.getLeaveAccrualService().runAccrual(TKContext.getTargetPrincipalId(), startDate, endDate, false);
			}
		}
	}
	
	// KPME-1447
	public ActionForward updateLeaveBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		String selectedEarnCode = lcf.getSelectedEarnCode();
		String leaveBlockId = lcf.getLeaveBlockId();
		
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
            TkServiceLocator.getLeaveBlockService().updateLeaveBlock(updatedLeaveBlock);
            lcf.setLeaveAmount(null);
            lcf.setDescription(null);
            lcf.setSelectedEarnCode(null);
    		// recalculate summary
    		if(lcf.getCalendarEntry() != null) {
    			LeaveSummary ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(TKContext.getTargetPrincipalId(), lcf.getCalendarEntry());
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
		boolean isFutureDate = TKUtils.getTimelessDate(null).compareTo(
				calEntry.getBeginPeriodDateTime()) >= 0;
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
	// -- put condition if it is not after current period
		isFutureDate = TKUtils.getTimelessDate(null).compareTo(calEntry.getBeginPeriodDateTime()) >= 0;

		// fetch previous entry
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
		if(leaveForm.getViewLeaveTabsWithNEStatus()) {
			if(!isFutureDate) {
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
        if (TKContext.getUser().isSystemAdmin()) {
            leaveForm.setDocEditable(true);
        } else {
            boolean docFinal = lcd.getDocumentHeader().getDocumentStatus().equals(TkConstants.ROUTE_STATUS.FINAL);
            if (!docFinal) {
                if(StringUtils.equals(lcd.getPrincipalId(), GlobalVariables.getUserSession().getPrincipalId())
                        || TKContext.getUser().isSystemAdmin()
                        || TKContext.getUser().isLocationAdmin()
                        || TKContext.getUser().isDepartmentAdmin()
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
	
}
