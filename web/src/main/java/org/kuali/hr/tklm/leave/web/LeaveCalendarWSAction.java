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
package org.kuali.hr.tklm.leave.web;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.LocalDate;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.kuali.hr.core.bo.assignment.Assignment;
import org.kuali.hr.core.bo.assignment.AssignmentDescriptionKey;
import org.kuali.hr.core.bo.calendar.entry.CalendarEntry;
import org.kuali.hr.core.bo.earncode.EarnCode;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.core.web.HrAction;
import org.kuali.hr.tklm.common.TKContext;
import org.kuali.hr.tklm.common.TKUtils;
import org.kuali.hr.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.hr.tklm.leave.calendar.validation.LeaveCalendarValidationUtil;
import org.kuali.hr.tklm.leave.calendar.web.LeaveCalendarForm;
import org.kuali.hr.tklm.leave.service.LmServiceLocator;
import org.kuali.hr.tklm.leave.summary.LeaveSummary;
import org.kuali.hr.tklm.time.detail.web.ActionFormUtils;

public class LeaveCalendarWSAction extends HrAction {

    private static final Logger LOG = Logger.getLogger(LeaveCalendarWSAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //return super.execute(mapping, form, request, response);
        LeaveCalendarWSForm lcf = (LeaveCalendarWSForm) form;

        String documentId = lcf.getDocumentId();
        // if the reload was trigger by changing of the selectedPayPeriod, use the passed in parameter as the calendar entry id
        String calendarEntryId = StringUtils.isNotBlank(request.getParameter("selectedPP")) ? request.getParameter("selectedPP") : lcf.getCalEntryId();

        // Here - viewPrincipal will be the principal of the user we intend to
        // view, be it target user, backdoor or otherwise.
        String viewPrincipal = TKContext.getTargetPrincipalId();
        CalendarEntry calendarEntry = null;

        LeaveCalendarDocument lcd = null;
        // By handling the prev/next in the execute method, we are saving one
        // fetch/construction of a TimesheetDocument. If it were broken out into
        // methods, we would first fetch the current document, and then fetch
        // the next one instead of doing it in the single action.
        if (StringUtils.isNotBlank(documentId)) {
            lcd = LmServiceLocator.getLeaveCalendarService()
                    .getLeaveCalendarDocument(documentId);
            calendarEntry = lcd.getCalendarEntry();
        } else if (StringUtils.isNotBlank(calendarEntryId)) {
            // do further procedure
            calendarEntry = HrServiceLocator.getCalendarEntryService()
                    .getCalendarEntry(calendarEntryId);
            lcd = LmServiceLocator.getLeaveCalendarService()
                    .getLeaveCalendarDocument(viewPrincipal, calendarEntry);
        } else {
            // Default to whatever is active for "today".
            calendarEntry = HrServiceLocator.getCalendarService()
                    .getCurrentCalendarDatesForLeaveCalendar(viewPrincipal, new LocalDate().toDateTimeAtStartOfDay());
            lcd = LmServiceLocator.getLeaveCalendarService()
                    .openLeaveCalendarDocument(viewPrincipal, calendarEntry);
        }

        lcf.setCalendarEntry(calendarEntry);
        lcf.setAssignmentDescriptions(HrServiceLocator.getAssignmentService().getAssignmentDescriptions(lcd));

        if (lcd != null) {
            setupDocumentOnFormContext(lcf, lcd);
        } else {
            LOG.error("Null leave calendar document in LeaveCalendarAction.");
        }

        ActionForward forward = super.execute(mapping, form, request, response);
        if (forward.getRedirect()) {
            return forward;
        }

        return forward;
    }

        
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
        //TODO: copied from TimeDetailWSAction.  Need to reduce code duplication
        LeaveCalendarWSForm lcf = (LeaveCalendarWSForm) form;


        if(request.getParameter("selectedPayPeriod") != null) {
            lcf.setSelectedPayPeriod(request.getParameter("selectedPayPeriod"));
            CalendarEntry ce = HrServiceLocator.getCalendarEntryService().getCalendarEntry(request.getParameter("selectedPayPeriod"));
            lcf.setCalendarEntry(ce);
        }
        lcf.setPrincipalId(TKContext.getTargetPrincipalId());
        boolean isPlanningCal = LmServiceLocator.getLeaveCalendarService().isLeavePlanningCalendar(lcf.getPrincipalId(), lcf.getCalendarEntry().getBeginPeriodFullDateTime().toLocalDate(), lcf.getCalendarEntry().getEndPeriodFullDateTime().toLocalDate());
        lcf.setLeavePlanningCalendar(isPlanningCal);

        List<Map<String, Object>> earnCodeList = new LinkedList<Map<String, Object>>();

        if (StringUtils.isNotBlank(lcf.getSelectedAssignment())) {
            List<Assignment> assignments = lcf.getLeaveCalendarDocument().getAssignments();
            AssignmentDescriptionKey key = new AssignmentDescriptionKey(lcf.getSelectedAssignment());
            for (Assignment assignment : assignments) {
            	if (assignment.getJobNumber().compareTo(key.getJobNumber()) == 0 &&
                        assignment.getWorkArea().compareTo(key.getWorkArea()) == 0 &&
                        assignment.getTask().compareTo(key.getTask()) == 0) {
                	List<EarnCode> earnCodes = HrServiceLocator.getEarnCodeService().getEarnCodesForLeave(assignment, TKUtils.formatDateTimeString(lcf.getStartDate()).toLocalDate(), lcf.isLeavePlanningCalendar());
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
        //LOG.info(lcf.toString());
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

    	if(lcf.getLeaveSummary() == null && lcf.getCalendarEntry() != null) {
    		LeaveSummary ls = LmServiceLocator.getLeaveSummaryService().getLeaveSummary(TKContext.getTargetPrincipalId(), lcf.getCalendarEntry());
		    lcf.setLeaveSummary(ls);
    	}
    	
    	errorMsgList.addAll(LeaveCalendarValidationUtil.validateParametersAccordingToSelectedEarnCodeRecordMethod(lcf));
    	
    	errorMsgList.addAll(LeaveCalendarValidationUtil.validateAvailableLeaveBalance(lcf));
    	//KPME-1263
        errorMsgList.addAll(LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(lcf));

        //KPME-2010                
        errorMsgList.addAll(LeaveCalendarValidationUtil.validateSpanningWeeks(lcf));
        
        lcf.setOutputString(JSONValue.toJSONString(errorMsgList));
        
        return mapping.findForward("ws");
    }

    protected void setupDocumentOnFormContext(LeaveCalendarForm leaveForm,
                                              LeaveCalendarDocument lcd) {
        leaveForm.setLeaveCalendarDocument(lcd);
    }

}
