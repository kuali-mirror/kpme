package org.kuali.hr.time.detail.web;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.calendar.TkCalendar;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesheet.web.TimesheetAction;
import org.kuali.hr.time.timesheet.web.TimesheetActionForm;
import org.kuali.hr.time.timesummary.AssignmentRow;
import org.kuali.hr.time.timesummary.EarnGroupSection;
import org.kuali.hr.time.timesummary.TimeSummary;
import org.kuali.hr.time.util.*;
import org.kuali.rice.kns.exception.AuthorizationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimeDetailAction extends TimesheetAction {

    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        super.checkTKAuthorization(form, methodToCall); // Checks for read access first.

        TimesheetActionForm taForm = (TimesheetActionForm)form;
        TKUser user = TKContext.getUser();
        UserRoles roles = user.getCurrentRoles(); // either backdoor or actual
        String docid = taForm.getDocumentId();

        // Check for write access to Timeblock.
        if (StringUtils.equals(methodToCall, "addTimeBlock") || StringUtils.equals(methodToCall, "deleteTimeBlock")) {
            if (!roles.isDocumentWritable(docid)) {
                throw new AuthorizationException(roles.getPrincipalId(), "TimeDetailAction", "");
            }
        }
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = super.execute(mapping, form, request, response);
        TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
        tdaf.setAssignmentDescriptions(TkServiceLocator.getAssignmentService().getAssignmentDescriptions(tdaf.getTimesheetDocument(), false));

        // TODO: may need to revisit this:
        // when adding / removing timeblocks, it should update the timeblocks on the timesheet document,e
        // so that we can directly fetch the timeblocks from the document
        List<TimeBlock> timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocks(Long.parseLong(tdaf.getTimesheetDocument().getDocumentHeader().getDocumentId()));
        TimeSummary ts = TkServiceLocator.getTimeSummaryService().getTimeSummary(tdaf.getTimesheetDocument(), timeBlocks);
    	tdaf.setAssignStyleClassMap(buildAssignmentStyleClassMap(tdaf.getTimesheetDocument()));
        Map<String, String> aMap = tdaf.getAssignStyleClassMap();
        // set css classes for each assignment row
        for(EarnGroupSection section: ts.getSections()) {
        	for(AssignmentRow assignRow: section.getAssignmentRows()) {
        		if(assignRow.getAssignmentKey() != null && aMap.containsKey(assignRow.getAssignmentKey())) {
                	assignRow.setCssClass(aMap.get(assignRow.getAssignmentKey()));
                } else {
                	assignRow.setCssClass("");
                }
        	}

        }
        tdaf.setTimeSummary(ts);
        this.validateHourLimit(tdaf);

        // Set calendar
        TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(timeBlocks, tdaf.getTimesheetDocument().getPayCalendarEntry());
        tdaf.setTkCalendar(TkCalendar.getCalendar(aggregate));
        tdaf.setTimeBlockString(getTimeBlockJSONMap(tdaf.getTimesheetDocument(), aggregate.getFlattenedTimeBlockList()));

        // for visually impaired users
        // TimesheetDocument td = tdaf.getTimesheetDocument();
        // List<TimeBlock> timeBlocks = td.getTimeBlocks();
        // tdaf.setTimeBlocks(timeBlocks);

        return forward;
    }

    public void validateHourLimit(TimeDetailActionForm tdaf) throws Exception {
    	tdaf.setWarningJason("");
        JSONArray warnMsgJson = new JSONArray();
        warnMsgJson = TkServiceLocator.getTimeOffAccrualService().validateAccrualHoursLimit(tdaf.getTimesheetDocument());
        if (!warnMsgJson.isEmpty()) {
        	tdaf.setWarningJason(JSONValue.toJSONString(warnMsgJson));
        }
    }

    // this is an ajax call
    public ActionForward getEarnCodes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
        StringBuffer earnCodeString = new StringBuffer();
        if (StringUtils.isBlank(tdaf.getSelectedAssignment())) {
            earnCodeString.append("<option value=''>-- select an assignment first --</option>");
        } else {
            List<Assignment> assignments = tdaf.getTimesheetDocument().getAssignments();
            AssignmentDescriptionKey key = new AssignmentDescriptionKey(tdaf.getSelectedAssignment());
            for (Assignment assignment : assignments) {
                if (assignment.getJobNumber().compareTo(key.getJobNumber()) == 0 &&
                        assignment.getWorkArea().compareTo(key.getWorkArea()) == 0 &&
                        assignment.getTask().compareTo(key.getTask()) == 0) {
                    List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService().getEarnCodes(assignment, tdaf.getTimesheetDocument().getAsOfDate());
                    for (EarnCode earnCode : earnCodes) {
                    	if(earnCode.getEarnCode().equals(TkConstants.HOLIDAY_EARN_CODE)
                    			&& !(TKContext.getUser().getCurrentRoles().isSystemAdmin() || TKContext.getUser().getCurrentRoles().isTimesheetApprover())) {
                    		continue;
                    	}
						if ( !(assignment.getTimeCollectionRule().isClockUserFl() &&
                                StringUtils.equals(assignment.getJob().getPayTypeObj().getRegEarnCode(), earnCode.getEarnCode())) ) {
                            earnCodeString.append("<option value='").append(earnCode.getEarnCode()).append("_").append(earnCode.getEarnCodeType());
                            earnCodeString.append("'>").append(earnCode.getEarnCode()).append(" : ").append(earnCode.getDescription());
                            earnCodeString.append("</option>");
                        }
                    }
                }
            }
        }

        tdaf.setOutputString(earnCodeString.toString());
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

        TkServiceLocator.getTimeBlockService().resetTimeHourDetail(newTimeBlocks);

        TkServiceLocator.getTkRuleControllerService().applyRules(TkConstants.ACTIONS.ADD_TIME_BLOCK, newTimeBlocks, tdaf.getPayCalendarDates(), tdaf.getTimesheetDocument());
        TkServiceLocator.getTimeBlockService().saveTimeBlocks(referenceTimeBlocks, newTimeBlocks);
        //call history service

        this.validateHourLimit(tdaf);
        return mapping.findForward("basic");
    }

    /**
     * This is an ajax call triggered after a user submits the time entry form.
     * If there is any error, it will return error messages as a json object.
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return jsonObj
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ActionForward validateTimeEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
        JSONArray errorMsgList = new JSONArray();

        //------------------------
        // validate the hour field
        //------------------------
//        if (tdaf.getHours() != null && tdaf.getHours().compareTo(BigDecimal.ZERO) > 0) {
//            if (tdaf.getHours().compareTo(new BigDecimal("0")) == 0) {
//                errorMsgList.add("The entered hours is not valid.");
//                tdaf.setOutputString(JSONValue.toJSONString(errorMsgList));
//                return mapping.findForward("ws");
//            }
//        }

        //------------------------
        // some of the simple validations are in the js side in order to reduce the server calls
        // 1. check if the begin / end time is empty - tk.calenadr.js
        // 2. check the time format - timeparse.js
        // 3. only allows decimals to be entered in the hour field
        //------------------------
        Long startTime = TKUtils.convertDateStringToTimestamp(tdaf.getStartDate(), tdaf.getStartTime()).getTime();
        Long endTime = TKUtils.convertDateStringToTimestamp(tdaf.getEndDate(), tdaf.getEndTime()).getTime();

        // this is for the output of the error message
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss z");

        //------------------------
        // check if the begin / end time are valid
        //------------------------
        if ((startTime.compareTo(endTime) > 0 || endTime.compareTo(startTime) < 0)) {
            errorMsgList.add("The time or date is not valid.");
            tdaf.setOutputString(JSONValue.toJSONString(errorMsgList));
            return mapping.findForward("ws");
        }

        //------------------------
        // check if the overnight shift is across days
        //------------------------
        DateTime startTemp = new DateTime(startTime);
        DateTime endTemp = new DateTime(endTime);
        if (StringUtils.equals(tdaf.getAcrossDays(), "y") && tdaf.getHours() == null && tdaf.getAmount() == null) {
            //Interval timeInterval = new Interval(startTime, endTime);
            if (startTemp.getHourOfDay() >= endTemp.getHourOfDay()
            		&& !(endTemp.getDayOfYear() - startTemp.getDayOfYear() <= 1
            				&& endTemp.getHourOfDay() == 0)) {
                errorMsgList.add("The \"apply to each day\" box should not be checked.");
                tdaf.setOutputString(JSONValue.toJSONString(errorMsgList));
                return mapping.findForward("ws");
            }

        }
        //------------------------
        // check if time blocks overlap with each other. Note that the tkTimeBlockId is used to
        // determine is it's updating an existing time block or adding a new one
        //------------------------
        if (tdaf.getTkTimeBlockId() == null) {
            Interval addedTimeblockInterval = new Interval(startTime, endTime);
            List<Interval> dayInt = new ArrayList<Interval> ();

            if(StringUtils.equals(tdaf.getAcrossDays(), "y")) {
            	DateTime start = new DateTime(startTime);
            	DateTime end = new DateTime(TKUtils.convertDateStringToTimestamp(tdaf.getStartDate(), tdaf.getEndTime()).getTime());
            	if(endTemp.getDayOfYear() - startTemp.getDayOfYear() <= 1) {
            		end = new DateTime(endTime);
            	}
            	DateTime groupEnd = new DateTime(endTime);
            	Long startLong = start.getMillis();
            	Long endLong = end.getMillis();
            	while(start.isBefore(groupEnd.getMillis())) {
            		Interval tempInt = new Interval(startLong, endLong);
            		dayInt.add(tempInt);
            		start = start.plusDays(1);
            		end = end.plusDays(1);
            		startLong = start.getMillis();
                	endLong = end.getMillis();
            	}
            } else {
            	dayInt.add(addedTimeblockInterval);
            }

            for (TimeBlock timeBlock : tdaf.getTimesheetDocument().getTimeBlocks()) {
            	if(StringUtils.equals(timeBlock.getEarnCodeType(), "TIME")) {
	                Interval timeBlockInterval = new Interval(timeBlock.getBeginTimestamp().getTime(), timeBlock.getEndTimestamp().getTime());
	                for(Interval intv: dayInt) {
		                if (timeBlockInterval.overlaps(intv)) {
		                	errorMsgList.add("The time block you are trying to add overlaps with an existing time block.");
		                	tdaf.setOutputString(JSONValue.toJSONString(errorMsgList));
		                    return mapping.findForward("ws");
		                }
	                }
            	}
            }
        }
        this.validateHourLimit(tdaf);

        tdaf.setOutputString(JSONValue.toJSONString(errorMsgList));

        return mapping.findForward("ws");
    }

    // Some of this needs to move to util classes.

    public static String getTimeBlockJSONMap(TimesheetDocument tsd, List<TimeBlock> blocks) {
        List<Map<String, Object>> jsonList = getTimeBlocksJson(blocks, null);
        Map<String, Map<String, Object>> jsonMappedList = new HashMap<String, Map<String, Object>>();
        for (Map<String,Object> tbm : jsonList) {
            String id = (String)tbm.get("id");
            jsonMappedList.put(id, tbm);
        }
        return JSONValue.toJSONString(jsonMappedList);
    }

    public static String getTimeBlocksForOutput(TimesheetDocument tsd, List<TimeBlock> timeBlocks) {
        return JSONValue.toJSONString(getTimeBlocksJson(timeBlocks, buildAssignmentStyleClassMap(tsd)));
    }

    public static Map<String, String> buildAssignmentStyleClassMap(TimesheetDocument tsd) {
          Map<String, String> aMap = new HashMap<String, String>();
          List<String> assignmentKeys = new ArrayList<String> ();
          List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(tsd.getPrincipalId(), tsd.getAsOfDate());

          for(Assignment assignment: assignments) {
              AssignmentDescriptionKey aKey = new AssignmentDescriptionKey(assignment.getJobNumber(),
                      assignment.getWorkArea(), assignment.getTask());
              assignmentKeys.add(aKey.toAssignmentKeyString());
          }
          Collections.sort(assignmentKeys);

          for(int i = 0; i< assignmentKeys.size(); i++) {
              aMap.put(assignmentKeys.get(i), "assignment"+ Integer.toString(i));
          }

          return aMap;
    }

    private static List<Map<String, Object>> getTimeBlocksJson(List<TimeBlock> timeBlocks, Map<String, String> assignmentStyleClassMap) {

        if (timeBlocks == null || timeBlocks.size() == 0) {
            return new ArrayList<Map<String, Object>>();
        }

        List<Map<String, Object>> timeBlockList = new LinkedList<Map<String, Object>>();
        String timezone = TkServiceLocator.getTimezoneService().getUserTimeZone();
        timeBlocks = TkServiceLocator.getTimezoneService().translateForTimezone(timeBlocks, timezone);

        for (TimeBlock timeBlock : timeBlocks) {
            Map<String, Object> timeBlockMap = new LinkedHashMap<String, Object>();

            //String assignmentKey = TKUtils.formatAssignmentKey(timeBlock.getJobNumber(), timeBlock.getWorkArea(), timeBlock.getTask());
            String workAreaDesc = TkServiceLocator.getWorkAreaService().getWorkArea(timeBlock.getWorkArea(), new java.sql.Date(timeBlock.getEndTimestamp().getTime())).getDescription();

            String cssClass = "";
            if(assignmentStyleClassMap != null && assignmentStyleClassMap.containsKey(timeBlock.getAssignmentKey())) {
            	cssClass = assignmentStyleClassMap.get(timeBlock.getAssignmentKey());
            }
            timeBlockMap.put("assignmentCss", cssClass);
            timeBlockMap.put("editable",  TkServiceLocator.getTimeBlockService().isTimeBlockEditable(timeBlock.getUserPrincipalId()));

            // DateTime object in jodatime is immutable. If manipulation of a datetime obj is necessary, use MutableDateTime instead.
            // ^^ to whoever wrote this: The point of immutable objects is to ensure consistency, if you're going to alter the object
            //    and you start with an immutable, just create another immutable. Similar to how you would use a BigDecimal. We are not
            //    tracking any kind of 'mutating' state with this object, it's just a one off modification under a specific circumstance.
            DateTime start = timeBlock.getBeginTimeDisplay();
            DateTime end = timeBlock.getEndTimeDisplay();

            /**
             * This is the timeblock backward pushing logic.
             * the purpose of this is to accommodate the virtual day mode where the start/end period time is not from 12a to 12a.
             * A timeblock will be pushed back if the timeblock is still within the previous interval
             */
            if (timeBlock.isPushBackward()) {
                start = start.minusDays(1);
                end = end.minusDays(1);
            }

            timeBlockMap.put("title", workAreaDesc);
            timeBlockMap.put("earnCode", timeBlock.getEarnCode());
            //TODO: need to cache this or pre-load it when the app boots up
            // EarnCode earnCode = TkServiceLocator.getEarnCodeService().getEarnCode(timeBlock.getEarnCode(), new java.sql.Date(timeBlock.getBeginTimestamp().getTime()));
            timeBlockMap.put("earnCodeType", timeBlock.getEarnCodeType());

            timeBlockMap.put("start", start.toDateTime().toString(ISODateTimeFormat.dateTimeNoMillis()));
            timeBlockMap.put("end", end.toDateTime().toString(ISODateTimeFormat.dateTimeNoMillis()));
            timeBlockMap.put("id", timeBlock.getTkTimeBlockId().toString());
            timeBlockMap.put("hours", timeBlock.getHours());
            timeBlockMap.put("amount", timeBlock.getAmount());
            timeBlockMap.put("timezone", timezone);
            timeBlockMap.put("assignment", new AssignmentDescriptionKey(timeBlock.getJobNumber(), timeBlock.getWorkArea(), timeBlock.getTask()).toAssignmentKeyString());
            timeBlockMap.put("tkTimeBlockId", timeBlock.getTkTimeBlockId() != null ? timeBlock.getTkTimeBlockId() : "");

            List<Map<String, Object>> timeHourDetailList = new LinkedList<Map<String, Object>>();
            for (TimeHourDetail timeHourDetail : timeBlock.getTimeHourDetails()) {
                Map<String, Object> timeHourDetailMap = new LinkedHashMap<String, Object>();
                timeHourDetailMap.put("earnCode", timeHourDetail.getEarnCode());
                timeHourDetailMap.put("hours", timeHourDetail.getHours());
                timeHourDetailMap.put("amount", timeHourDetail.getAmount());

                // if there is a lunch hour deduction, add a flag to the timeBlockMap
                if(StringUtils.equals(timeHourDetail.getEarnCode(), "LUN")) {
                    timeBlockMap.put("lunchDeduction", true);
                }

                timeHourDetailList.add(timeHourDetailMap);
            }
            timeBlockMap.put("timeHourDetails", JSONValue.toJSONString(timeHourDetailList));

            timeBlockList.add(timeBlockMap);
        }

        return timeBlockList;
    }

}
