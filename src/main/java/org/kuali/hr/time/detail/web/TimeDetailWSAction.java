package org.kuali.hr.time.detail.web;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import org.kuali.hr.time.timesheet.web.TimesheetAction;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TimeDetailWSAction extends TimesheetAction {

    private static final Logger LOG = Logger.getLogger(TimeDetailWSAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.execute(mapping, form, request, response);
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

        TimeDetailActionFormBase tdaf = (TimeDetailActionFormBase) form;
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
            List<Interval> dayInt = new ArrayList<Interval>();

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
        ActionFormUtils.validateHourLimit(tdaf);

        tdaf.setOutputString(JSONValue.toJSONString(errorMsgList));

        return mapping.findForward("ws");
    }

    // this is an ajax call
    public ActionForward getEarnCodes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeDetailWSActionForm tdaf = (TimeDetailWSActionForm) form;
        StringBuilder earnCodeString = new StringBuilder();
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
        LOG.info(tdaf.toString());
        tdaf.setOutputString(earnCodeString.toString());
        return mapping.findForward("ws");
    }

}
