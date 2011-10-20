package org.kuali.hr.time.detail.web;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.TkCalendar;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesheet.web.TimesheetAction;
import org.kuali.hr.time.timesummary.AssignmentRow;
import org.kuali.hr.time.timesummary.EarnCodeSection;
import org.kuali.hr.time.timesummary.EarnGroupSection;
import org.kuali.hr.time.timesummary.TimeSummary;
import org.kuali.hr.time.util.*;
import org.kuali.rice.kns.exception.AuthorizationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TimeDetailAction extends TimesheetAction {

    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        super.checkTKAuthorization(form, methodToCall); // Checks for read access first.
        TKUser user = TKContext.getUser();
        UserRoles roles = user.getCurrentRoles(); // either backdoor or actual
        TimesheetDocument doc = TKContext.getCurrentTimesheetDoucment();

        // Check for write access to Timeblock.
        if (StringUtils.equals(methodToCall, "addTimeBlock") || StringUtils.equals(methodToCall, "deleteTimeBlock")) {
            if (!roles.isDocumentWritable(doc)) {
                throw new AuthorizationException(roles.getPrincipalId(), "TimeDetailAction", "");
            }
        }
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = super.execute(mapping, form, request, response);
        if(forward.getRedirect()){
        	return forward;
        }
        TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
        tdaf.setAssignmentDescriptions(TkServiceLocator.getAssignmentService().getAssignmentDescriptions(TKContext.getCurrentTimesheetDoucment(), false));

        //List<TimeBlock> timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocks(Long.parseLong(tdaf.getTimesheetDocument().getDocumentHeader().getDocumentId()));
        List<TimeBlock> timeBlocks = TKContext.getCurrentTimesheetDoucment().getTimeBlocks();

        // Handle User preference / timezone information (pushed up from TkCalendar to avoid duplication)
        TimeSummary ts = TkServiceLocator.getTimeSummaryService().getTimeSummary(TKContext.getCurrentTimesheetDoucment());
    	tdaf.setAssignStyleClassMap(ActionFormUtils.buildAssignmentStyleClassMap(TKContext.getCurrentTimesheetDoucment()));
        Map<String, String> aMap = tdaf.getAssignStyleClassMap();
        // set css classes for each assignment row
        for(EarnGroupSection earnGroupSection: ts.getSections()) {
        	for(EarnCodeSection section : earnGroupSection.getEarnCodeSections()){
        		for(AssignmentRow assignRow: section.getAssignmentsRows()) {
        			if(assignRow.getAssignmentKey() != null && aMap.containsKey(assignRow.getAssignmentKey())) {
        				assignRow.setCssClass(aMap.get(assignRow.getAssignmentKey()));
        			} else {
        				assignRow.setCssClass("");
        			}
        		}
        	}

        }
        tdaf.setTimeSummary(ts);
        ActionFormUtils.validateHourLimit(tdaf);

        // Set calendar
        PayCalendarEntries payCalendarEntry = tdaf.getPayCalendarDates();
        PayCalendar payCalendar = TkServiceLocator.getPayCalendarSerivce().getPayCalendar(payCalendarEntry.getHrPyCalendarId());
        TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(timeBlocks, payCalendarEntry, payCalendar, true, 
        		TKUtils.getFullWeekDaySpanForPayCalendarEntry(payCalendarEntry));
        TkCalendar cal = TkCalendar.getCalendar(aggregate);
        cal.assignAssignmentStyle(aMap);
        tdaf.setTkCalendar(cal);

        tdaf.setTimeBlockString(ActionFormUtils.getTimeBlockJSONMap(TKContext.getCurrentTimesheetDoucment(), aggregate.getFlattenedTimeBlockList()));

        tdaf.setOvertimeEarnCodes(TkServiceLocator.getEarnCodeService().getOvertimeEarnCodesStrs(TKContext.getCurrentTimesheetDoucment().getAsOfDate()));
        if(TKContext.getUser().isSystemAdmin()){
        	tdaf.setDocEditable("true");
        } else if(TKContext.getCurrentTimesheetDoucment().getDocumentHeader().getDocumentStatus().equals(TkConstants.ROUTE_STATUS.FINAL)) {
        	tdaf.setDocEditable("false");
        } else if(StringUtils.equals(TKContext.getCurrentTimesheetDoucment().getPrincipalId(), TKContext.getUser().getPrincipalId())){
        	tdaf.setDocEditable("true");
        } else {
        	if(TKContext.getUser().isSystemAdmin() || TKContext.getUser().isLocationAdmin() || TKContext.getUser().isDepartmentAdmin() ||
        			TKContext.getUser().isReviewer() || TKContext.getUser().isApprover()){
        		tdaf.setDocEditable("true");
        	}
        	if(TKContext.getUser().isGlobalViewOnly() || TKContext.getUser().isDepartmentViewOnly()){
        		tdaf.setDocEditable("false");
        	}
        }

        return forward;
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
        TkServiceLocator.getTimeBlockService().saveTimeBlocks(referenceTimeBlocks, newTimeBlocks);

        return mapping.findForward("basic");
    }

    /**
     * This method involves creating an object-copy of every TimeBlock on the
     * time sheet for overtime re-calculation.
     *
     * @throws Exception
     */
    public ActionForward addTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
        Timestamp overtimeBeginTimestamp = null;
        Timestamp overtimeEndTimestamp = null;

        // This is for updating a timeblock or changing
        // If tkTimeBlockId is not null and the new timeblock is valid, delete the existing timeblock and a new one will be created after submitting the form.
        if (tdaf.getTkTimeBlockId() != null) {
            TimeBlock tb = TkServiceLocator.getTimeBlockService().getTimeBlock(tdaf.getTkTimeBlockId());


            if(StringUtils.isNotEmpty(tdaf.getOvertimePref())) {
                overtimeBeginTimestamp =  tb.getBeginTimestamp();
                overtimeEndTimestamp = tb.getEndTimestamp();
            }

            TimeBlockHistory tbh = new TimeBlockHistory(tb);
            if (tb != null) {
                TkServiceLocator.getTimeBlockService().deleteTimeBlock(tb);

//                 mark the original timeblock as deleted in the history table
                tbh.setActionHistory(TkConstants.ACTIONS.DELETE_TIME_BLOCK);
                TkServiceLocator.getTimeBlockHistoryService().saveTimeBlockHistory(tbh);

                // delete the timeblock from the memory
                tdaf.getTimesheetDocument().getTimeBlocks().remove(tb);
            }
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
        if (StringUtils.equals(tdaf.getAcrossDays(), "y")
        		&& !(endTemp.getDayOfYear() - startTemp.getDayOfYear() <= 1
        				&& endTemp.getHourOfDay() == 0)) {
            newTimeBlocks.addAll(TkServiceLocator.getTimeBlockService().buildTimeBlocksSpanDates(assignment,
                    tdaf.getSelectedEarnCode(), tdaf.getTimesheetDocument(), startTime,
                    endTime, tdaf.getHours(), tdaf.getAmount(), false));
        } else {
            newTimeBlocks.addAll(TkServiceLocator.getTimeBlockService().buildTimeBlocks(assignment,
                    tdaf.getSelectedEarnCode(), tdaf.getTimesheetDocument(), startTime,
                    endTime, tdaf.getHours(), tdaf.getAmount(), false));
        }

        //reset time block
        TkServiceLocator.getTimesheetService().resetTimeBlock(newTimeBlocks);

        // apply overtime pref
        for (TimeBlock tb : newTimeBlocks) {
            if(tb.getBeginTimestamp().equals(overtimeBeginTimestamp) && tb.getEndTimestamp().equals(overtimeEndTimestamp) && StringUtils.isNotEmpty(tdaf.getOvertimePref())) {
                tb.setOvertimePref(tdaf.getOvertimePref());
            }
        }

        TkServiceLocator.getTkRuleControllerService().applyRules(TkConstants.ACTIONS.ADD_TIME_BLOCK, newTimeBlocks, tdaf.getPayCalendarDates(), tdaf.getTimesheetDocument(), TKContext.getPrincipalId());
        TkServiceLocator.getTimeBlockService().saveTimeBlocks(referenceTimeBlocks, newTimeBlocks);
        //call history service

        ActionFormUtils.validateHourLimit(tdaf);
        return mapping.findForward("basic");
    }
    
    public ActionForward actualTimeInquiry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
        return mapping.findForward("ati");
    }
}
