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
package org.kuali.hr.time.detail.web;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.lm.balancetransfer.validation.BalanceTransferValidationUtils;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.validation.LeaveCalendarValidationUtil;
import org.kuali.hr.lm.util.LeaveBlockAggregate;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.calendar.TkCalendar;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesheet.web.TimesheetAction;
import org.kuali.hr.time.timesheet.web.TimesheetActionForm;
import org.kuali.hr.time.timesummary.AssignmentRow;
import org.kuali.hr.time.timesummary.EarnCodeSection;
import org.kuali.hr.time.timesummary.EarnGroupSection;
import org.kuali.hr.time.timesummary.TimeSummary;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

public class TimeDetailAction extends TimesheetAction {

    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        super.checkTKAuthorization(form, methodToCall); // Checks for read access first.
        UserRoles roles = TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId());
        TimesheetDocument doc = TKContext.getCurrentTimesheetDocument();

        // Check for write access to Timeblock.
        if (StringUtils.equals(methodToCall, "addTimeBlock") || StringUtils.equals(methodToCall, "deleteTimeBlock") || StringUtils.equals(methodToCall, "updateTimeBlock")) {
            if (!roles.isDocumentWritable(doc)) {
                throw new AuthorizationException(roles.getPrincipalId(), "TimeDetailAction", "");
            }
        }
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = super.execute(mapping, form, request, response);
        if (forward.getRedirect()) {
            return forward;
        }
        if (TKContext.getCurrentTimesheetDocument() == null) {
            return forward;
        }
        TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
        tdaf.setAssignmentDescriptions(TkServiceLocator.getAssignmentService().getAssignmentDescriptions(TKContext.getCurrentTimesheetDocument(), false));

        // Handle User preference / timezone information (pushed up from TkCalendar to avoid duplication)
        // Set calendar
        CalendarEntries payCalendarEntry = tdaf.getPayCalendarDates();
        Calendar payCalendar = TkServiceLocator.getCalendarService().getCalendar(payCalendarEntry != null ? payCalendarEntry.getHrCalendarId() : null);
        
        //List<TimeBlock> timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocks(Long.parseLong(tdaf.getTimesheetDocument().getDocumentHeader().getTimesheetDocumentId()));
        List<TimeBlock> timeBlocks = TKContext.getCurrentTimesheetDocument().getTimeBlocks();
        // get leave blocks
        List<Assignment> timeAssignments = TKContext.getCurrentTimesheetDocument().getAssignments();
        List<String> tAssignmentKeys = new ArrayList<String>();
        for(Assignment assign : timeAssignments) {
        	tAssignmentKeys.add(assign.getAssignmentKey());
        }
        List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForTimeCalendar(TKContext.getCurrentTimesheetDocument().getPrincipalId(), 
        					payCalendarEntry.getBeginPeriodDate(), payCalendarEntry.getEndPeriodDate(), tAssignmentKeys);
        List<LeaveBlock> balanceTransferLeaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocksWithType(TKContext.getCurrentTimesheetDocument().getPrincipalId(),
        		 payCalendarEntry.getBeginPeriodDate(), payCalendarEntry.getEndPeriodDate(), LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER);
        
//        List<String> warnings = tdaf.getWarnings();
/*        List<String> infoMessages = tdaf.getInfoMessages();
        List<String> warningMessages = tdaf.getWarningMessages();
        List<String> actionMessages = tdaf.getActionMessages();*/
        
        Map<String, Set<String>> allMessages = LeaveCalendarValidationUtil.getWarningMessagesForLeaveBlocks(balanceTransferLeaveBlocks);
        Map<String, Set<String>> transactionalMessages = LeaveCalendarValidationUtil.validatePendingTransactions(TKContext.getTargetPrincipalId(),
        		payCalendarEntry.getBeginPeriodDate(), payCalendarEntry.getEndPeriodDate());
        
        List<String> warnings = new ArrayList<String>();
        //placing the following "validation" further down in this method will overwrite messages added prior to this call.
        //allMessages.putAll(LeaveCalendarValidationUtil.validatePendingTransactions(viewPrincipal, payCalendarEntry.getBeginPeriodDate(), payCalendarEntry.getEndPeriodDate()));
        
        // add warning messages based on max carry over balances for each accrual category for non-exempt leave users
        String viewPrincipal = TKContext.getTargetPrincipalId();
        List<BalanceTransfer> losses = new ArrayList<BalanceTransfer>();
        if (TkServiceLocator.getLeaveApprovalService().isActiveAssignmentFoundOnJobFlsaStatus(viewPrincipal, TkConstants.FLSA_STATUS_NON_EXEMPT, true)) {
            PrincipalHRAttributes principalCalendar = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(viewPrincipal, payCalendarEntry.getEndPeriodDate());

	        Interval calendarInterval = new Interval(payCalendarEntry.getBeginPeriodDate().getTime(), payCalendarEntry.getEndPeriodDate().getTime());
	        Map<String,Set<LeaveBlock>> maxBalInfractions = new HashMap<String,Set<LeaveBlock>>();
	        
            if(ObjectUtils.isNotNull(principalCalendar)) {
    	        maxBalInfractions = TkServiceLocator.getAccrualCategoryMaxBalanceService().getMaxBalanceViolations(payCalendarEntry, viewPrincipal);
    	        
    	        for(Entry<String,Set<LeaveBlock>> entry : maxBalInfractions.entrySet()) {
    	        	for(LeaveBlock lb : entry.getValue()) {
        				if(calendarInterval.contains(lb.getLeaveDate().getTime())) {
	    	        		AccrualCategory accrualCat = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(lb.getAccrualCategory(), lb.getLeaveDate());
				        	AccrualCategoryRule aRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(lb.getAccrualCategoryRuleId());
				        	if(StringUtils.equals(aRule.getActionAtMaxBalance(),LMConstants.ACTION_AT_MAX_BAL.LOSE)) {
				        		DateTime aDate = null;
				        		if(StringUtils.equals(aRule.getMaxBalanceActionFrequency(), LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
				        			aDate = TkServiceLocator.getLeavePlanService().getRolloverDayOfLeavePlan(principalCalendar.getLeavePlan(), lb.getLeaveDate());
				        		}
				        		else {
					        		Calendar cal = TkServiceLocator.getCalendarService().getCalendarByPrincipalIdAndDate(viewPrincipal, lb.getLeaveDate(), true);
					        		CalendarEntries leaveEntry = TkServiceLocator.getCalendarEntriesService().getCurrentCalendarEntriesByCalendarId(cal.getHrCalendarId(), lb.getLeaveDate());
					        		aDate = new DateTime(leaveEntry.getEndPeriodDate());
				        		}
				        		aDate = aDate.minusDays(1);
				        		if(calendarInterval.contains(aDate.getMillis()) && aDate.toDate().compareTo(payCalendarEntry.getEndPeriodDate()) <= 0) {
					        		//may want to calculate summary for all rows, displayable or not, and determine displayability via tags.
					    			AccrualCategory accrualCategory = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(aRule.getLmAccrualCategoryId());
					    			BigDecimal accruedBalance = TkServiceLocator.getAccrualCategoryService().getAccruedBalanceForPrincipal(viewPrincipal, accrualCategory, lb.getLeaveDate());
						        	
						        	BalanceTransfer loseTransfer = TkServiceLocator.getBalanceTransferService().initializeTransfer(viewPrincipal, lb.getAccrualCategoryRuleId(), accruedBalance, lb.getLeaveDate());
						        	boolean valid = BalanceTransferValidationUtils.validateTransfer(loseTransfer);
						        	if(valid)
						        		losses.add(loseTransfer);
				        		}
				        	}
	
	
	    		        	// accrual categories within the leave plan that are hidden from the leave summary WILL appear.
	        				String message = "You have exceeded the maximum balance limit for '" + accrualCat.getAccrualCategory() + "' as of " + lb.getLeaveDate() + ". "+
	                    			"Depending upon the accrual category rules, leave over this limit may be forfeited.";
                            //  leave blocks are sorted in getMaxBalanceViolations() method, so we just take the one with the earliest leave date for an accrual category.
                            if(!StringUtils.contains(allMessages.get("warningMessages").toString(),"You have exceeded the maximum balance limit for '"+accrualCat.getAccrualCategory())) {
	                            allMessages.get("warningMessages").add(message);
	        				}
        				}
    	        	}
    	        }
        	}
            tdaf.setForfeitures(losses);
            
        	if (principalCalendar != null) {
	        	Calendar calendar = TkServiceLocator.getCalendarService().getCalendarByPrincipalIdAndDate(viewPrincipal, tdaf.getEndPeriodDateTime(), true);
					
				if (calendar != null) {
					List<CalendarEntries> leaveCalendarEntries = TkServiceLocator.getCalendarEntriesService().getCalendarEntriesEndingBetweenBeginAndEndDate(calendar.getHrCalendarId(), tdaf.getBeginPeriodDateTime(), tdaf.getEndPeriodDateTime());
					
					List<AccrualCategory> accrualCategories = TkServiceLocator.getAccrualCategoryService().getActiveLeaveAccrualCategoriesForLeavePlan(principalCalendar.getLeavePlan(), new java.sql.Date(tdaf.getEndPeriodDateTime().getTime()));
					for (AccrualCategory accrualCategory : accrualCategories) {
						if (TkServiceLocator.getAccrualCategoryMaxCarryOverService().exceedsAccrualCategoryMaxCarryOver(accrualCategory.getAccrualCategory(), viewPrincipal, leaveCalendarEntries, tdaf.getEndPeriodDateTime())) {
							String message = "Your pending leave balance is greater than the annual max carry over for accrual category '" + accrualCategory.getAccrualCategory() + "' and upon approval, the excess balance will be lost.";
							if (!warnings.contains(message)) {
								warnings.add(message);
							}
						}
					}
				}
			}
        }
/*		warnings.addAll(allMessages.get("infoMessages"));
		warnings.addAll(allMessages.get("actionMessages"));
		warnings.addAll(allMessages.get("warningMessages"));*/
        allMessages.get("warningMessages").addAll(warnings);
        
        List<String> infoMessages = tdaf.getInfoMessages();
        infoMessages.addAll(allMessages.get("infoMessages"));
        infoMessages.addAll(transactionalMessages.get("infoMessages"));

        List<String> warningMessages = tdaf.getWarningMessages();
        warningMessages.addAll(allMessages.get("warningMessages"));
        warningMessages.addAll(transactionalMessages.get("warningMessages"));

        List<String> actionMessages = tdaf.getActionMessages();
        actionMessages.addAll(allMessages.get("actionMessages"));
        actionMessages.addAll(transactionalMessages.get("actionMessages"));

        tdaf.setInfoMessages(infoMessages);
        tdaf.setWarningMessages(warningMessages);
        tdaf.setActionMessages(actionMessages);

        this.assignStypeClassMapForTimeSummary(tdaf,timeBlocks, leaveBlocks);
        
        List<Interval> intervals = TKUtils.getFullWeekDaySpanForCalendarEntry(payCalendarEntry);
        LeaveBlockAggregate lbAggregate = new LeaveBlockAggregate(leaveBlocks, payCalendarEntry, intervals);
        TkTimeBlockAggregate tbAggregate = new TkTimeBlockAggregate(timeBlocks, payCalendarEntry, payCalendar, true,intervals);
        // use both time aggregate and leave aggregate to populate the calendar
        TkCalendar cal = TkCalendar.getCalendar(tbAggregate, lbAggregate);
        cal.assignAssignmentStyle(tdaf.getAssignStyleClassMap());
        tdaf.setTkCalendar(cal);
     
        this.populateCalendarAndPayPeriodLists(request, tdaf);

        tdaf.setTimeBlockString(ActionFormUtils.getTimeBlocksJson(tbAggregate.getFlattenedTimeBlockList()));
        tdaf.setLeaveBlockString(ActionFormUtils.getLeaveBlocksJson(lbAggregate.getFlattenedLeaveBlockList()));

        tdaf.setOvertimeEarnCodes(TkServiceLocator.getEarnCodeService().getOvertimeEarnCodesStrs(TKContext.getCurrentTimesheetDocument().getAsOfDate()));

        if (StringUtils.equals(TKContext.getCurrentTimesheetDocument().getPrincipalId(), GlobalVariables.getUserSession().getPrincipalId())) {
        	tdaf.setWorkingOnItsOwn("true");
        }
        
        tdaf.setDocEditable("false");
        if (TKUser.isSystemAdmin()) {
            tdaf.setDocEditable("true");
        } else {
            boolean docFinal = TKContext.getCurrentTimesheetDocument().getDocumentHeader().getDocumentStatus().equals(TkConstants.ROUTE_STATUS.FINAL);
            if (!docFinal) {
            	if(StringUtils.equals(TKContext.getCurrentTimesheetDocument().getPrincipalId(), GlobalVariables.getUserSession().getPrincipalId())
	            		|| TKUser.isSystemAdmin()
	            		|| TKUser.isLocationAdmin()
	            		|| TKUser.isDepartmentAdmin()
	            		|| TKUser.isReviewer()
	            		|| TKUser.isApprover()) {
                    tdaf.setDocEditable("true");
                }
            	
	            //if the timesheet has been approved by at least one of the approvers, the employee should not be able to edit it
	            if (StringUtils.equals(TKContext.getCurrentTimesheetDocument().getPrincipalId(), GlobalVariables.getUserSession().getPrincipalId())
	            		&& TKContext.getCurrentTimesheetDocument().getDocumentHeader().getDocumentStatus().equals(TkConstants.ROUTE_STATUS.ENROUTE)) {
		        	Collection actions = KEWServiceLocator.getActionTakenService().findByDocIdAndAction(TKContext.getCurrentTimesheetDocument().getDocumentHeader().getDocumentId(), TkConstants.DOCUMENT_ACTIONS.APPROVE);
	        		if(!actions.isEmpty()) {
	        			tdaf.setDocEditable("false");  
	        		}
		        }
            }
        }

        return forward;
    }

    // use lists of time blocks and leave blocks to build the style class map and assign css class to associated summary rows
	private void assignStypeClassMapForTimeSummary(TimeDetailActionForm tdaf, List<TimeBlock> timeBlocks, List<LeaveBlock> leaveBlocks) throws Exception {
		TimeSummary ts = TkServiceLocator.getTimeSummaryService().getTimeSummary(TKContext.getCurrentTimesheetDocument());
        tdaf.setAssignStyleClassMap(ActionFormUtils.buildAssignmentStyleClassMap(timeBlocks, leaveBlocks));
        Map<String, String> aMap = tdaf.getAssignStyleClassMap();
        // set css classes for each assignment row
        for (EarnGroupSection earnGroupSection : ts.getSections()) {
            for (EarnCodeSection section : earnGroupSection.getEarnCodeSections()) {
                for (AssignmentRow assignRow : section.getAssignmentsRows()) {
                    if (assignRow.getAssignmentKey() != null && aMap.containsKey(assignRow.getAssignmentKey())) {
                        assignRow.setCssClass(aMap.get(assignRow.getAssignmentKey()));
                    } else {
                        assignRow.setCssClass("");
                    }
                }
            }

        }
        tdaf.setTimeSummary(ts);
      //  ActionFormUtils.validateHourLimit(tdaf);
        ActionFormUtils.addWarningTextFromEarnGroup(tdaf);
        ActionFormUtils.addUnapprovedIPWarningFromClockLog(tdaf);
	}

	private void populateCalendarAndPayPeriodLists(HttpServletRequest request, TimeDetailActionForm tdaf) {
		String viewPrincipal = TKContext.getTargetPrincipalId();
		List<TimesheetDocumentHeader> documentHeaders = (List<TimesheetDocumentHeader>) TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeadersForPrincipalId(TKContext.getTargetPrincipalId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        if(tdaf.getCalendarYears().isEmpty()) {
        	// get calendar year drop down list contents
	        Set<String> yearSet = new HashSet<String>();
	        
	        for(TimesheetDocumentHeader tdh : documentHeaders) {
	        	yearSet.add(sdf.format(tdh.getBeginDate()));
	        }
	        List<String> yearList = new ArrayList<String>(yearSet);
	        Collections.sort(yearList);
	        Collections.reverse(yearList);	// newest on top
	        tdaf.setCalendarYears(yearList);
        }
        // if selected calendar year is passed in
        if(request.getParameter("selectedCY")!= null) {
        	tdaf.setSelectedCalendarYear(request.getParameter("selectedCY").toString());
        }
        // if there is no selected calendr year, use the year of current pay calendar entry
        if(StringUtils.isEmpty(tdaf.getSelectedCalendarYear())) {
        	tdaf.setSelectedCalendarYear(sdf.format(tdaf.getPayCalendarDates().getBeginPeriodDate()));
        }
        if(tdaf.getPayPeriodsMap().isEmpty()) {
	        List<CalendarEntries> payPeriodList = new ArrayList<CalendarEntries>();
	        for(TimesheetDocumentHeader tdh : documentHeaders) {
	        	if(sdf.format(tdh.getBeginDate()).equals(tdaf.getSelectedCalendarYear())) {
                    CalendarEntries pe = TkServiceLocator.getCalendarService().getCalendarDatesByPayEndDate(tdh.getPrincipalId(), tdh.getEndDate(), TkConstants.PAY_CALENDAR_TYPE);
                    //CalendarEntries pe = TkServiceLocator.getCalendarEntriesService().getCalendarEntriesByBeginAndEndDate(tdh.getBeginDate(), tdh.getEndDate());
	        		payPeriodList.add(pe);
	        	}
	        }
	        tdaf.setPayPeriodsMap(ActionFormUtils.getPayPeriodsMap(payPeriodList, viewPrincipal));
        }
        if(request.getParameter("selectedPP")!= null) {
        	tdaf.setSelectedPayPeriod(request.getParameter("selectedPP").toString());
        }
        if(StringUtils.isEmpty(tdaf.getSelectedPayPeriod())) {
        	tdaf.setSelectedPayPeriod(tdaf.getPayCalendarDates().getHrCalendarEntriesId());
        }
	}


    /**
     * This method involves creating an object-copy of every TimeBlock on the
     * time sheet for overtime re-calculation.
     *
     * @throws Exception
     */
    public ActionForward deleteTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
        //Grab timeblock to be deleted from form
        List<TimeBlock> timeBlocks = tdaf.getTimesheetDocument().getTimeBlocks();
        TimeBlock deletedTimeBlock = null;
        for (TimeBlock tb : timeBlocks) {
            if (tb.getTkTimeBlockId().compareTo(tdaf.getTkTimeBlockId()) == 0) {
                deletedTimeBlock = tb;
                break;
            }
        }
        if (deletedTimeBlock == null) {
            return mapping.findForward("basic");
        }
        //Remove from the list of timeblocks
        List<TimeBlock> referenceTimeBlocks = new ArrayList<TimeBlock>(tdaf.getTimesheetDocument().getTimeBlocks().size());
        for (TimeBlock b : tdaf.getTimesheetDocument().getTimeBlocks()) {
            referenceTimeBlocks.add(b.copy());
        }

        // simple pointer, for clarity
        List<TimeBlock> newTimeBlocks = tdaf.getTimesheetDocument().getTimeBlocks();
        newTimeBlocks.remove(deletedTimeBlock);

        //Delete timeblock
        TkServiceLocator.getTimeBlockService().deleteTimeBlock(deletedTimeBlock);
        // Add a row to the history table
        TimeBlockHistory tbh = new TimeBlockHistory(deletedTimeBlock);
        tbh.setActionHistory(TkConstants.ACTIONS.DELETE_TIME_BLOCK);
        TkServiceLocator.getTimeBlockHistoryService().saveTimeBlockHistory(tbh);
        //reset time block
        TkServiceLocator.getTimesheetService().resetTimeBlock(newTimeBlocks);
        TkServiceLocator.getTkRuleControllerService().applyRules(TkConstants.ACTIONS.ADD_TIME_BLOCK, newTimeBlocks, tdaf.getPayCalendarDates(), tdaf.getTimesheetDocument(), TKContext.getPrincipalId());
        TkServiceLocator.getTimeBlockService().saveTimeBlocks(referenceTimeBlocks, newTimeBlocks, TKContext.getPrincipalId());

        return mapping.findForward("basic");
    }

    /**
     * This method involves creating an object-copy of every TimeBlock on the
     * time sheet for overtime re-calculation.
     * Based on the form's timeBlockId or leaveBlockId, existing Time/Leave blocks will be deleted and new ones created
     *
     * @throws Exception
     */
    public ActionForward addTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
        
        if(StringUtils.isNotEmpty(tdaf.getTkTimeBlockId())) {
        	// the user is changing an existing time block, so need to delete this time block
        	this.removeOldTimeBlock(tdaf);
        } else if(StringUtils.isNotEmpty(tdaf.getLmLeaveBlockId())) {
        	// the user is changing an existing leave block, so need to delete this leave block
        	this.removeOldLeaveBlock(tdaf.getLmLeaveBlockId());
        }
        if(StringUtils.isNotEmpty(tdaf.getSelectedEarnCode())) {
        	EarnCode ec = TkServiceLocator.getEarnCodeService().getEarnCode(tdaf.getSelectedEarnCode(), tdaf.getTimesheetDocument().getAsOfDate());
        	if(ec != null && (ec.getLeavePlan() != null || ec.getEligibleForAccrual().equals("N"))) {	// leave blocks changes
        		this.changeLeaveBlocks(tdaf);
        	} else {	// time blocks changes
        		this.changeTimeBlocks(tdaf);
        	}
        }
        
       // ActionFormUtils.validateHourLimit(tdaf);
        ActionFormUtils.addWarningTextFromEarnGroup(tdaf);

        return mapping.findForward("basic");
    }
    
    private void removeOldTimeBlock(TimeDetailActionForm tdaf) {
	  if (tdaf.getTkTimeBlockId() != null) {
	      TimeBlock tb = TkServiceLocator.getTimeBlockService().getTimeBlock(tdaf.getTkTimeBlockId());
	      if (tb != null) {
	          TimeBlockHistory tbh = new TimeBlockHistory(tb);
	          TkServiceLocator.getTimeBlockService().deleteTimeBlock(tb);
	
	          // mark the original timeblock as deleted in the history table
			  tbh.setActionHistory(TkConstants.ACTIONS.DELETE_TIME_BLOCK);
			  TkServiceLocator.getTimeBlockHistoryService().saveTimeBlockHistory(tbh);
	
			  // delete the timeblock from the memory
	          tdaf.getTimesheetDocument().getTimeBlocks().remove(tb);
	      }
	  }
    }
    
    private void removeOldLeaveBlock(String lbId) {
  	  if (lbId != null) {
  	      LeaveBlock lb = TkServiceLocator.getLeaveBlockService().getLeaveBlock(lbId);
  	      if (lb != null) {
  	          TkServiceLocator.getLeaveBlockService().deleteLeaveBlock(lbId, TKContext.getPrincipalId());
  	      }
  	  }
    }
    
    // add/update leave blocks 
	private void changeLeaveBlocks(TimeDetailActionForm tdaf) {
		DateTime beginDate = new DateTime(TKUtils.convertDateStringToTimestamp(tdaf.getStartDate()));
		DateTime endDate = new DateTime(TKUtils.convertDateStringToTimestamp(tdaf.getEndDate()));
		String selectedEarnCode = tdaf.getSelectedEarnCode();
		BigDecimal leaveAmount = tdaf.getLeaveAmount();
		
		String desc = "";	// there's no description field in time calendar pop window
		String spanningWeeks = tdaf.getSpanningWeeks();
		Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(tdaf.getTimesheetDocument(), tdaf.getSelectedAssignment());
		TkServiceLocator.getLeaveBlockService().addLeaveBlocks(beginDate, endDate, tdaf.getPayCalendarDates(), selectedEarnCode, leaveAmount, desc, assignment, 
				spanningWeeks, LMConstants.LEAVE_BLOCK_TYPE.TIME_CALENDAR, TKContext.getTargetPrincipalId());
	}
	
    // add/update time blocks
	private void changeTimeBlocks(TimeDetailActionForm tdaf) {
		Timestamp overtimeBeginTimestamp = null;
        Timestamp overtimeEndTimestamp = null;
        boolean isClockLogCreated = false;
        
        // This is for updating a timeblock or changing
        // If tkTimeBlockId is not null and the new timeblock is valid, delete the existing timeblock and a new one will be created after submitting the form.
        if (tdaf.getTkTimeBlockId() != null) {
            TimeBlock tb = TkServiceLocator.getTimeBlockService().getTimeBlock(tdaf.getTkTimeBlockId());
            if (tb != null) {
	            isClockLogCreated = tb.getClockLogCreated();
	            if (StringUtils.isNotEmpty(tdaf.getOvertimePref())) {
                    //TODO:  This doesn't do anything!!! these variables are never used.  Should they be?
	                overtimeBeginTimestamp = tb.getBeginTimestamp();
	                overtimeEndTimestamp = tb.getEndTimestamp();
	            }
            }
            // old time block is deleted from addTimeBlock method
            // this.removeOldTimeBlock(tdaf);
        }

        Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(tdaf.getTimesheetDocument(), tdaf.getSelectedAssignment());


        // Surgery point - Need to construct a Date/Time with Appropriate Timezone.
        Timestamp startTime = TKUtils.convertDateStringToTimestamp(tdaf.getStartDate(), tdaf.getStartTime());
        Timestamp endTime = TKUtils.convertDateStringToTimestamp(tdaf.getEndDate(), tdaf.getEndTime());

        // We need a  cloned reference set so we know whether or not to
        // persist any potential changes without making hundreds of DB calls.
        List<TimeBlock> referenceTimeBlocks = new ArrayList<TimeBlock>(tdaf.getTimesheetDocument().getTimeBlocks().size());
        for (TimeBlock tb : tdaf.getTimesheetDocument().getTimeBlocks()) {
            referenceTimeBlocks.add(tb.copy());
        }

        // This is just a reference, for code clarity, the above list is actually
        // separate at the object level.
        List<TimeBlock> newTimeBlocks = tdaf.getTimesheetDocument().getTimeBlocks();
        DateTime startTemp = new DateTime(startTime);
        DateTime endTemp = new DateTime(endTime);
        // KPME-1446 add spanningweeks to the calls below 
        if (StringUtils.equals(tdaf.getAcrossDays(), "y")
                && !(endTemp.getDayOfYear() - startTemp.getDayOfYear() <= 1
                && endTemp.getHourOfDay() == 0)) {
            List<TimeBlock> timeBlocksToAdd = TkServiceLocator.getTimeBlockService().buildTimeBlocksSpanDates(assignment,
                    tdaf.getSelectedEarnCode(), tdaf.getTimesheetDocument(), startTime,
                    endTime, tdaf.getHours(), tdaf.getAmount(), isClockLogCreated, Boolean.parseBoolean(tdaf.getLunchDeleted()), tdaf.getSpanningWeeks(), TKContext.getPrincipalId());
            for (TimeBlock tb : timeBlocksToAdd) {
                if (!newTimeBlocks.contains(tb)) {
                    newTimeBlocks.add(tb);
                }
            }
        } else {
            List<TimeBlock> timeBlocksToAdd = TkServiceLocator.getTimeBlockService().buildTimeBlocks(assignment,
                    tdaf.getSelectedEarnCode(), tdaf.getTimesheetDocument(), startTime,
                    endTime, tdaf.getHours(), tdaf.getAmount(), isClockLogCreated, Boolean.parseBoolean(tdaf.getLunchDeleted()), TKContext.getPrincipalId());
            for (TimeBlock tb : timeBlocksToAdd) {
                if (!newTimeBlocks.contains(tb)) {
                    newTimeBlocks.add(tb);
                }
            }
        }

        //reset time block
        TkServiceLocator.getTimesheetService().resetTimeBlock(newTimeBlocks);

        // apply overtime pref
        // I changed start and end times comparison below. it used to be overtimeBeginTimestamp and overtimeEndTimestamp but
        // for some reason, they're always null because, we have removed the time block before getting here. KPME-2162
        for (TimeBlock tb : newTimeBlocks) {
            if (tb.getBeginTimestamp().equals(startTime) && tb.getEndTimestamp().equals(endTime) && StringUtils.isNotEmpty(tdaf.getOvertimePref())) {
                tb.setOvertimePref(tdaf.getOvertimePref());
            }

        }

        TkServiceLocator.getTkRuleControllerService().applyRules(TkConstants.ACTIONS.ADD_TIME_BLOCK, newTimeBlocks, tdaf.getPayCalendarDates(), tdaf.getTimesheetDocument(), TKContext.getPrincipalId());
        TkServiceLocator.getTimeBlockService().saveTimeBlocks(referenceTimeBlocks, newTimeBlocks, TKContext.getPrincipalId());
	}

    public ActionForward updateTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
        Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(tdaf.getTimesheetDocument(), tdaf.getSelectedAssignment());

        //Grab timeblock to be updated from form
        List<TimeBlock> timeBlocks = tdaf.getTimesheetDocument().getTimeBlocks();
        TimeBlock updatedTimeBlock = null;
        for (TimeBlock tb : timeBlocks) {
            if (tb.getTkTimeBlockId().compareTo(tdaf.getTkTimeBlockId()) == 0) {
                updatedTimeBlock = tb;
                tb.setJobNumber(assignment.getJobNumber());
                tb.setWorkArea(assignment.getWorkArea());
                tb.setTask(assignment.getTask());
                break;
            }
        }

        TkServiceLocator.getTimeBlockService().updateTimeBlock(updatedTimeBlock);

        TimeBlockHistory tbh = new TimeBlockHistory(updatedTimeBlock);
        tbh.setActionHistory(TkConstants.ACTIONS.UPDATE_TIME_BLOCK);
        TkServiceLocator.getTimeBlockHistoryService().saveTimeBlockHistory(tbh);
        tdaf.setMethodToCall("addTimeBlock");
        return mapping.findForward("basic");
    }


    public ActionForward actualTimeInquiry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward("ati");
    }

    public ActionForward deleteLunchDeduction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
        String timeHourDetailId = tdaf.getTkTimeHourDetailId();
        TkServiceLocator.getTimeBlockService().deleteLunchDeduction(timeHourDetailId);

        List<TimeBlock> newTimeBlocks = tdaf.getTimesheetDocument().getTimeBlocks();
        
        TkServiceLocator.getTimesheetService().resetTimeBlock(newTimeBlocks);
        
        // KPME-1340
        TkServiceLocator.getTkRuleControllerService().applyRules(TkConstants.ACTIONS.ADD_TIME_BLOCK, newTimeBlocks, tdaf.getPayCalendarDates(), tdaf.getTimesheetDocument(), TKContext.getPrincipalId());
        TkServiceLocator.getTimeBlockService().saveTimeBlocks(newTimeBlocks);
        TKContext.getCurrentTimesheetDocument().setTimeBlocks(newTimeBlocks);
        
        return mapping.findForward("basic");
    }
      
  public ActionForward gotoCurrentPayPeriod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	  String viewPrincipal = TKUser.getCurrentTargetPersonId();
	  Date currentDate = TKUtils.getTimelessDate(null);
      CalendarEntries pce = TkServiceLocator.getCalendarService().getCurrentCalendarDates(viewPrincipal, currentDate);
      TimesheetDocument td = TkServiceLocator.getTimesheetService().openTimesheetDocument(viewPrincipal, pce);
      setupDocumentOnFormContext((TimesheetActionForm)form, td);
	  return mapping.findForward("basic");
  }
  
  //Triggered by changes of pay period drop down list, reload the whole page based on the selected pay period
  public ActionForward changeCalendarYear(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	  
	  TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
	  if(request.getParameter("selectedCY") != null) {
		  tdaf.setSelectedCalendarYear(request.getParameter("selectedCY").toString());
	  }
	  return mapping.findForward("basic");
  }
  
  //Triggered by changes of pay period drop down list, reload the whole page based on the selected pay period
  public ActionForward changePayPeriod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	  TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
	  if(request.getParameter("selectedPP") != null) {
		  tdaf.setSelectedPayPeriod(request.getParameter("selectedPP").toString());
          CalendarEntries pce = TkServiceLocator.getCalendarEntriesService()
		  	.getCalendarEntries(request.getParameter("selectedPP").toString());
		  if(pce != null) {
			  String viewPrincipal = TKUser.getCurrentTargetPersonId();
			  TimesheetDocument td = TkServiceLocator.getTimesheetService().openTimesheetDocument(viewPrincipal, pce);
			  setupDocumentOnFormContext((TimesheetActionForm)form, td);
		  }
	  }
	  return mapping.findForward("basic");
  }
  
  public ActionForward deleteLeaveBlock(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	  TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
	  String leaveBlockId = tdaf.getLmLeaveBlockId();

      LeaveBlock blockToDelete = TkServiceLocator.getLeaveBlockService().getLeaveBlock(leaveBlockId);
      if (blockToDelete != null && TkServiceLocator.getPermissionsService().canDeleteLeaveBlock(blockToDelete)) {
		    TkServiceLocator.getLeaveBlockService().deleteLeaveBlock(leaveBlockId, TKContext.getPrincipalId());
      }
      
      // if the leave block is NOT eligible for accrual, rerun accrual service for the leave calendar the leave block is on
      EarnCode ec = TkServiceLocator.getEarnCodeService().getEarnCode(blockToDelete.getEarnCode(), blockToDelete.getLeaveDate());
      if(ec != null && ec.getEligibleForAccrual().equals("N")) {
    	  CalendarEntries ce = TkServiceLocator.getCalendarService()
					.getCurrentCalendarDatesForLeaveCalendar(blockToDelete.getPrincipalId(), blockToDelete.getLeaveDate());
    	  if(ce != null) {
    		  TkServiceLocator.getLeaveAccrualService().runAccrual(blockToDelete.getPrincipalId(), ce.getBeginPeriodDate(), ce.getEndPeriodDate(), false);
    	  }
      }
		
      return mapping.findForward("basic");
	}

}
