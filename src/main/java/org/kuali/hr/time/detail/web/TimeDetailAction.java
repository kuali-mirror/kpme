package org.kuali.hr.time.detail.web;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.kuali.hr.time.timesheet.web.TimesheetAction;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TimeDetailAction extends TimesheetAction {

    @Override
 	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = super.execute(mapping, form, request, response);
		TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
		tdaf.setAssignmentDescriptions(TkServiceLocator.getAssignmentService().getAssignmentDescriptions(tdaf.getTimesheetDocument(),false));
//		tdaf.setBeginPeriodDateTime(td.getPayCalendarEntry().getBeginPeriodDateTime());
//		tdaf.setEndPeriodDateTime(td.getPayCalendarEntry().getEndPeriodDateTime());
		
		// TODO: may need to revisit this:
		// when adding / removing timeblocks, it should update the timeblocks on the timesheet document,e
		// so that we can directly fetch the timeblocks from the document
		List<TimeBlock> timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocks(Long.parseLong(tdaf.getTimesheetDocument().getDocumentHeader().getDocumentId()));
		tdaf.setTimeSummary(TkServiceLocator.getTimeSummaryService().getTimeSummary(tdaf.getTimesheetDocument(), timeBlocks));

		// for visually impaired users
		// TimesheetDocument td = tdaf.getTimesheetDocument();
		// List<TimeBlock> timeBlocks = td.getTimeBlocks();
		// tdaf.setTimeBlocks(timeBlocks);
		
		return forward;
	}

    // this is an ajax call
    public ActionForward getEarnCodes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
        String earnCodeString = "";
        if (StringUtils.isBlank(tdaf.getSelectedAssignment())) {
            earnCodeString += "<option value=''>-- select an assignment --</option>";
        } else {
            List<Assignment> assignments = tdaf.getTimesheetDocument().getAssignments();
            AssignmentDescriptionKey key = new AssignmentDescriptionKey(tdaf.getSelectedAssignment());
            for (Assignment assignment : assignments) {
                if (assignment.getJobNumber().compareTo(key.getJobNumber()) == 0 &&
                        assignment.getWorkArea().compareTo(key.getWorkArea()) == 0 &&
                        assignment.getTask().compareTo(key.getTask()) == 0) {

                    List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService().getEarnCodes(assignment);
                    for (EarnCode earnCode : earnCodes) {
                        earnCodeString += "<option value='" + earnCode.getEarnCode() + "_" + earnCode.getEarnCodeType() + "'>" + earnCode.getEarnCode() + " : " + earnCode.getDescription() + "</option>";
                    }
                }
            }
        }

        tdaf.setOutputString(earnCodeString);
        return mapping.findForward("ws");
    }

    public ActionForward getTimeBlocks(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeDetailActionForm timeDetailForm = (TimeDetailActionForm) form;
        String timeBlockJson = TkServiceLocator.getTimeBlockService().getTimeBlocksForOutput(timeDetailForm.getTimesheetDocument());
        timeDetailForm.setOutputString(timeBlockJson);

        return mapping.findForward("ws");
    }

    /**
     * This method involves creating an object-copy of every TimeBlock on the
     * time sheet for overtime re-calculation.
     *
     * @throws Exception
     */
    public ActionForward deleteTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // the corresponding js code resides in the fullcalendar-1.4.7.js somewhere around #1664
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
        TkServiceLocator.getTimeBlockService().resetTimeHourDetail(newTimeBlocks);
        TkServiceLocator.getTkRuleControllerService().applyRules(TkConstants.ACTIONS.ADD_TIME_BLOCK, newTimeBlocks, tdaf.getPayCalendarDates(), tdaf.getTimesheetDocument());
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
        Timestamp startTime;
        Timestamp endTime;

        // This is for updating a timeblock
        // If tkTimeBlockId is not null and the new timeblock is valid, delete the existing timeblock and a new one will be created after submitting the form.
        if (tdaf.getTkTimeBlockId() != null) {
            TimeBlock tb = TkServiceLocator.getTimeBlockService().getTimeBlock(tdaf.getTkTimeBlockId());
            TimeBlockHistory tbh = new TimeBlockHistory(tb);
            if (tb != null) {
                TkServiceLocator.getTimeBlockService().deleteTimeBlock(tb);

                // mark the original timeblock as updated in the history table
                tbh.setActionHistory(TkConstants.ACTIONS.UPDATE_TIME_BLOCK);
                TkServiceLocator.getTimeBlockHistoryService().saveTimeBlockHistory(tbh);

                // delete the timeblock from the memory
                tdaf.getTimesheetDocument().getTimeBlocks().remove(tb);
            }
        }

        Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(tdaf.getTimesheetDocument(),
                tdaf.getSelectedAssignment());

        if (tdaf.getHours() != null || tdaf.getAmount() != null) {
            startTime = TKUtils.convertDateStringToTimestamp(tdaf.getStartDate() + " 0:0");
            endTime = TKUtils.convertDateStringToTimestamp(tdaf.getEndDate() + " 0:0");
        } else {
            startTime = TKUtils.convertDateStringToTimestamp(tdaf.getStartTime());
            endTime = TKUtils.convertDateStringToTimestamp(tdaf.getEndTime());
        }

        // We need a  cloned reference set so we know whether or not to
        // persist any potential changes without making hundreds of DB calls.
        List<TimeBlock> referenceTimeBlocks = new ArrayList<TimeBlock>(tdaf.getTimesheetDocument().getTimeBlocks().size());
        for (TimeBlock tb : tdaf.getTimesheetDocument().getTimeBlocks()) {
            referenceTimeBlocks.add(tb.copy());
        }

        // This is just a reference, for code clarity, the above list is actually
        // separate at the object level.
        List<TimeBlock> newTimeBlocks = tdaf.getTimesheetDocument().getTimeBlocks();
        if (StringUtils.equals(tdaf.getAcrossDays(), "y")) {
            newTimeBlocks.addAll(TkServiceLocator.getTimeBlockService().buildTimeBlocksSpanDates(assignment,
                    tdaf.getSelectedEarnCode(), tdaf.getTimesheetDocument(), startTime,
                    endTime, tdaf.getHours(), false));
        } else {
            newTimeBlocks.addAll(TkServiceLocator.getTimeBlockService().buildTimeBlocks(assignment,
                    tdaf.getSelectedEarnCode(), tdaf.getTimesheetDocument(), startTime,
                    endTime, tdaf.getHours(), false));
        }

        TkServiceLocator.getTimeBlockService().resetTimeHourDetail(newTimeBlocks);
        TkServiceLocator.getTkRuleControllerService().applyRules(TkConstants.ACTIONS.ADD_TIME_BLOCK, newTimeBlocks, tdaf.getPayCalendarDates(), tdaf.getTimesheetDocument());
        TkServiceLocator.getTimeBlockService().saveTimeBlocks(referenceTimeBlocks, newTimeBlocks);
        //call history service

        return mapping.findForward("basic");
    }

    /**
     * This is method called via ajax which is triggered after a user submits the time entry form.
     * If there is any error, it will add the error messages into a json object and the javascript will grab the error messages
     * and render them directly on the form before it is submitted.
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return jsonObj
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ActionForward validateTimeBlocks(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
        JSONArray errorMsgList = new JSONArray();

        //------------------------
        // validate the hour field
        //------------------------
        if (tdaf.getHours() != null) {
            if (tdaf.getHours().compareTo(new BigDecimal("0")) == 0) {
                errorMsgList.add("The entered hours is not valid.");
            }

            tdaf.setOutputString(JSONValue.toJSONString(errorMsgList));
            return mapping.findForward("ws");
        }

        //------------------------
        // some of the simple validations are in the js side in order to reduce the server calls
        // 1. check if the begin / end time is empty - tk.calenadr.js
        // 2. check the time format - timeparse.js
        // 3. only allows decimals to be entered in the hour field
        //------------------------
        Long startTime = TKUtils.convertDateStringToTimestamp(tdaf.getStartTime()).getTime();
        Long endTime = TKUtils.convertDateStringToTimestamp(tdaf.getEndTime()).getTime();

        // this is for the output of the error message
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss z");
        String beginDateTime = sdf.format(new java.util.Date(startTime));
        String endDateTime = sdf.format(new java.util.Date(endTime));

        //------------------------
        // check if the begin / end time are valid
        //------------------------
        if (tdaf.getHours() == null && startTime.compareTo(endTime) > 0 || endTime.compareTo(startTime) < 0) {
            errorMsgList.add("The begin time / end time is not valid.");
            tdaf.setOutputString(JSONValue.toJSONString(errorMsgList));
            return mapping.findForward("ws");
        }

        //------------------------	
        // check if the overnight shift is across days
        //------------------------
        if(StringUtils.equals(tdaf.getAcrossDays(), "y")) {
            //Interval timeInterval = new Interval(startTime, endTime);
            DateTime start = new DateTime(startTime);
            DateTime end = new DateTime(endTime);
            if(start.getHourOfDay() >= end.getHourOfDay()) {
                errorMsgList.add("The \"apply to each day\" box should not be checked.");
                tdaf.setOutputString(JSONValue.toJSONString(errorMsgList));
                return mapping.findForward("ws");
            }

        }
        //------------------------
        // check if time blocks overlap with each other. Note that the tkTimeBlockId is used to determine is it's an update action or not
        //------------------------
        if (tdaf.getTkTimeBlockId() == null) {
            Interval addedTimeblockInterval = new Interval(startTime, endTime);

            for (TimeBlock timeBlock : tdaf.getTimesheetDocument().getTimeBlocks()) {
                Interval timeBlockInterval = new Interval(timeBlock.getBeginTimestamp().getTime(), timeBlock.getEndTimestamp().getTime());

                if (timeBlockInterval.overlaps(addedTimeblockInterval)) {
                    errorMsgList.add("The time block you are trying to add overlaps with an existing time block.");
                    break;
                }
            }
        }
        tdaf.setOutputString(JSONValue.toJSONString(errorMsgList));

        return mapping.findForward("ws");
    }
}
