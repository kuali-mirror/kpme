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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.validation.LeaveCalendarValidationUtil;
import org.kuali.hr.lm.util.LeaveBlockAggregate;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.calendar.TkCalendar;
import org.kuali.hr.time.earncode.EarnCode;
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

public class TimeDetailAction extends TimesheetAction {

    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        super.checkTKAuthorization(form, methodToCall); // Checks for read access first.
        TKUser user = TKContext.getUser();
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
        List<LeaveBlock> balanceTransferLeaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocksByType(TKContext.getCurrentTimesheetDocument().getPrincipalId(),
        		LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER, payCalendarEntry.getBeginPeriodDate(), payCalendarEntry.getEndPeriodDate());
        List<String> warnings = tdaf.getWarnings();
        warnings.addAll(LeaveCalendarValidationUtil.getWarningMessagesForLeaveBlocks(balanceTransferLeaveBlocks));
        tdaf.setWarnings(warnings);
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
        if (TKContext.getUser().isSystemAdmin()) {
            tdaf.setDocEditable("true");
        } else {
            boolean docFinal = TKContext.getCurrentTimesheetDocument().getDocumentHeader().getDocumentStatus().equals(TkConstants.ROUTE_STATUS.FINAL);
            if (!docFinal) {
            	if(StringUtils.equals(TKContext.getCurrentTimesheetDocument().getPrincipalId(), GlobalVariables.getUserSession().getPrincipalId())
	            		|| TKContext.getUser().isSystemAdmin() 
	            		|| TKContext.getUser().isLocationAdmin() 
	            		|| TKContext.getUser().isDepartmentAdmin() 
	            		|| TKContext.getUser().isReviewer() 
	            		|| TKContext.getUser().isApprover()) {
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
        ActionFormUtils.validateHourLimit(tdaf);
        ActionFormUtils.addWarningTextFromEarnGroup(tdaf);
        ActionFormUtils.addUnapprovedIPWarningFromClockLog(tdaf);
	}

	private void populateCalendarAndPayPeriodLists(HttpServletRequest request, TimeDetailActionForm tdaf) {
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
                    CalendarEntries pe = TkServiceLocator.getCalendarEntriesService().getCalendarEntriesByBeginAndEndDate(tdh.getBeginDate(), tdh.getEndDate());
	        		payPeriodList.add(pe);
	        	}
	        }
	        tdaf.setPayPeriodsMap(ActionFormUtils.getPayPeriodsMap(payPeriodList));
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
        	if(ec != null && ec.getLeavePlan() != null) {	// leave blocks changes
        		this.changeLeaveBlocks(tdaf);
        	} else {	// time blocks changes
        		this.changeTimeBlocks(tdaf);
        	}
        }
        
        ActionFormUtils.validateHourLimit(tdaf);
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
        for (TimeBlock tb : newTimeBlocks) {
            if (tb.getBeginTimestamp().equals(overtimeBeginTimestamp) && tb.getEndTimestamp().equals(overtimeEndTimestamp) && StringUtils.isNotEmpty(tdaf.getOvertimePref())) {
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
        TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
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
	  String viewPrincipal = TKUser.getCurrentTargetPerson().getPrincipalId();
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
			  String viewPrincipal = TKUser.getCurrentTargetPerson().getPrincipalId();
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
		
      return mapping.findForward("basic");
	}

}
