/**
 * Copyright 2004-2014 The Kuali Foundation
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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.earncode.EarnCode;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.kpme.tklm.leave.calendar.validation.LeaveCalendarValidationUtil;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.detail.web.ActionFormUtils;
import org.kuali.kpme.tklm.time.detail.web.TimeDetailWSActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class LeaveCalendarWSAction extends LeaveCalendarAction {

    private static final Logger LOG = Logger.getLogger(LeaveCalendarWSAction.class);
        
    public ActionForward getEarnCodeInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	LeaveCalendarWSForm lcf = (LeaveCalendarWSForm) form;
        LOG.info(lcf.toString());
        EarnCode earnCode = HrServiceLocator.getEarnCodeService().getEarnCodeById(lcf.getSelectedEarnCode());
    	String unitTime = ActionFormUtils.getUnitOfTimeForEarnCode(earnCode);
        Map<String, Object> earnCodeMap = new HashMap<String, Object>();
        earnCodeMap.put("unitOfTime", unitTime);
        earnCodeMap.put("defaultAmountofTime", earnCode.getDefaultAmountofTime());
        earnCodeMap.put("fractionalTimeAllowed", earnCode.getFractionalTimeAllowed());
        lcf.setOutputString(JSONValue.toJSONString(earnCodeMap));
        return mapping.findForward("ws");
    }

    public ActionForward getEarnCodeJson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LeaveCalendarWSForm lcf = (LeaveCalendarWSForm) form;

        List<Map<String, Object>> earnCodeList = new LinkedList<Map<String, Object>>();

        CalendarEntry calendarEntry = lcf.getCalendarEntry();
        if (StringUtils.isNotBlank(lcf.getSelectedAssignment())) {
            LocalDate asOfDate = calendarEntry.getEndPeriodFullDateTime().toLocalDate();

            if (lcf.getStartDate() != null) {
                try {
                    DateTime utilDate = HrConstants.DateTimeFormats.BASIC_DATE_FORMAT.parseDateTime(lcf.getStartDate());
                    asOfDate = utilDate.toLocalDate();
                } catch (Exception ex) {
                    //ignore and use the timesheet as of date.
                }
            }
        	Map<LocalDate, List<Assignment>> assignments = HrServiceLocator.getAssignmentService().getAssignmentsByCalEntryForLeaveCalendar(HrContext.getTargetPrincipalId(), calendarEntry);
        	boolean leavePlanningCalendar = LmServiceLocator.getLeaveCalendarService().isLeavePlanningCalendar(HrContext.getTargetPrincipalId(), calendarEntry.getBeginPeriodFullDateTime().toLocalDate(), calendarEntry.getEndPeriodFullDateTime().toLocalDate());
            AssignmentDescriptionKey key = AssignmentDescriptionKey.get(lcf.getSelectedAssignment());
            List<Assignment> dayAssigns = assignments.get(asOfDate);
            for (Assignment assignment : dayAssigns) {
            	if (assignment.getJobNumber().compareTo(key.getJobNumber()) == 0 &&
                        assignment.getWorkArea().compareTo(key.getWorkArea()) == 0 &&
                        assignment.getTask().compareTo(key.getTask()) == 0) {
            		List<EarnCode> earnCodes = HrServiceLocator.getEarnCodeService().getEarnCodesForLeave(assignment,asOfDate , leavePlanningCalendar);
                    for (EarnCode earnCode : earnCodes) {
                        Map<String, Object> earnCodeMap = new HashMap<String, Object>();
                        earnCodeMap.put("assignment", assignment.getAssignmentKey());
                        earnCodeMap.put("earnCode", earnCode.getEarnCode());
                        earnCodeMap.put("desc", earnCode.getDescription());
                        earnCodeMap.put("type", earnCode.getEarnCodeType());
                        earnCodeMap.put("earnCodeId", earnCode.getHrEarnCodeId());
                        earnCodeMap.put("unitOfTime", earnCode.getRecordMethod());
                        earnCodeMap.put("defaultAmountofTime", earnCode.getDefaultAmountofTime());
                        earnCodeMap.put("fractionalTimeAllowed", earnCode.getFractionalTimeAllowed());
                        earnCodeList.add(earnCodeMap);
                    }
                }
            }
        }
        
        LOG.info(lcf.toString());
        lcf.setOutputString(JSONValue.toJSONString(earnCodeList));
        
        return mapping.findForward("ws");
    }
    
    /**
     * This is an ajax call triggered after a user submits the leave entry form.
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
    public ActionForward validateLeaveEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	LeaveCalendarWSForm lcf = (LeaveCalendarWSForm) form;
    	JSONArray errorMsgList = new JSONArray();

    	errorMsgList.addAll(LeaveCalendarValidationUtil.validateLeaveEntry(lcf));

    	
        lcf.setOutputString(JSONValue.toJSONString(errorMsgList));
        
        return mapping.findForward("ws");
    }

    protected void setupDocumentOnFormContext(LeaveCalendarForm leaveForm,
                                              LeaveCalendarDocument lcd) {
        leaveForm.setLeaveCalendarDocument(lcd);
    }
    
    
    public ActionForward getValidAssignments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeDetailWSActionForm tdaf = (TimeDetailWSActionForm) form;

        //need an asOfDate here
        LocalDate asOfDate = tdaf.getTimesheetDocument().getAsOfDate();

        if (tdaf.getStartDate() != null) {
            try {
                DateTime utilDate = HrConstants.DateTimeFormats.BASIC_DATE_FORMAT.parseDateTime(tdaf.getStartDate());
                asOfDate = utilDate.toLocalDate();
            } catch (Exception ex) {
                //ignore and use the timesheet as of date.
            }
        }
		String earnCode = tdaf.getSelectedEarnCode();

		List<Map<String, Object>> assignments = new ArrayList<Map<String, Object>>();
		if (tdaf.getTimesheetDocument() != null && StringUtils.isNotEmpty(earnCode)) {
			assignments = getAssignmentsForRegEarnCode(tdaf.getTimesheetDocument().getAssignmentMap().get(asOfDate), earnCode, asOfDate);
		}
		tdaf.setOutputString(JSONValue.toJSONString(assignments));
		return mapping.findForward("ws");
	}
    
    protected List<Map<String, Object>> getAssignmentsForRegEarnCode(List<Assignment> assigns, String earnCode, LocalDate asOfDate) {
        List<Map<String, Object>> assignments = new ArrayList<Map<String, Object>>();
        if (CollectionUtils.isNotEmpty(assigns)) {
			for (Assignment a : assigns) {
				Map<String, Object> assignment = new HashMap<String, Object>();
				if (earnCode.equals(a.getJob().getPayTypeObj().getRegEarnCode())) {
					assignment.put("assignment", a.getAssignmentKey());
                    assignment.put("desc", a.getAssignmentDescription());
                    assignments.add(assignment);
				}
			}
		}
		return assignments;
	}
    
    public ActionForward getAssignmentJson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LeaveCalendarWSForm lcf = (LeaveCalendarWSForm) form;
        String earnCode = lcf.getSelectedEarnCode();
        LocalDate asOfDate = null;
        if (lcf.getStartDate() != null) {
            try {
                DateTime utilDate = HrConstants.DateTimeFormats.BASIC_DATE_FORMAT.parseDateTime(lcf.getStartDate());
                asOfDate = utilDate.toLocalDate();
            } catch (Exception ex) {
                //ignore and use the leaveCalendar as of date.
            }
        }
        List<Map<String, Object>> assignments = new ArrayList<Map<String, Object>>();
        lcf.setLeaveCalendarDocument(LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(lcf.getDocumentId()));
        if (lcf.getLeaveCalendarDocument()!=null && asOfDate != null) {
            //check date to see if assignment is active
            String principalId = lcf.getLeaveCalendarDocument().getPrincipalId();
            CalendarEntry ce = lcf.getLeaveCalendarDocument().getCalendarEntry();
            Map<LocalDate, List<Assignment>> history = lcf.getLeaveCalendarDocument().getAssignmentMap();
            List<Assignment> dayAssignments = history.get(asOfDate);
            dayAssignments = HrServiceLocator.getAssignmentService().filterAssignmentListForUser(HrContext.getPrincipalId(), dayAssignments);
            if (CollectionUtils.isNotEmpty(dayAssignments)) {
                if (dayAssignments.size() > 1) {
                    Map<String, Object> assignmentMap = new HashMap<String, Object>(2);
                    assignmentMap.put("assignment", "");
                    assignmentMap.put("desc", "-- select an assignment --");
                    assignments.add(assignmentMap);
                }
                for (Assignment a : dayAssignments) {
                    Map<String, Object> assignmentMap = new HashMap<String, Object>(2);
                    assignmentMap.put("assignment", a.getAssignmentKey());
                    assignmentMap.put("desc", HrServiceLocator.getAssignmentService().getAssignmentDescriptionForAssignment(a, asOfDate));
                    assignments.add(assignmentMap);
                }
            }
        }else{
        	
        }

        lcf.setOutputString(JSONValue.toJSONString(assignments));
        return mapping.findForward("ws");
    }

}
