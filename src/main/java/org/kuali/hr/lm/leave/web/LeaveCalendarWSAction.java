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
package org.kuali.hr.lm.leave.web;

import java.sql.Date;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.leavecalendar.validation.LeaveCalendarValidationUtil;
import org.kuali.hr.lm.leavecalendar.web.LeaveCalendarForm;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.detail.web.ActionFormUtils;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;

public class LeaveCalendarWSAction extends TkAction {

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
        String viewPrincipal = TKUser.getCurrentTargetPerson().getPrincipalId();
        CalendarEntries calendarEntry = null;

        LeaveCalendarDocument lcd = null;
        // By handling the prev/next in the execute method, we are saving one
        // fetch/construction of a TimesheetDocument. If it were broken out into
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
            lcd = TkServiceLocator.getLeaveCalendarService()
                    .getLeaveCalendarDocument(viewPrincipal, calendarEntry);
        } else {
            // Default to whatever is active for "today".
            Date currentDate = TKUtils.getTimelessDate(null);
            calendarEntry = TkServiceLocator.getCalendarService()
                    .getCurrentCalendarDatesForLeaveCalendar(viewPrincipal, currentDate);
            lcd = TkServiceLocator.getLeaveCalendarService()
                    .openLeaveCalendarDocument(viewPrincipal, calendarEntry);
        }

        lcf.setCalendarEntry(calendarEntry);
        lcf.setAssignmentDescriptions(TkServiceLocator.getAssignmentService().getAssignmentDescriptions(lcd));

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
        EarnCode earnCode = TkServiceLocator.getEarnCodeService().getEarnCodeById(lcf.getSelectedEarnCode());
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
        CalendarEntries ce = new CalendarEntries();

        if(request.getParameter("selectedPayPeriod") != null) {
            lcf.setSelectedPayPeriod(request.getParameter("selectedPayPeriod"));
            ce = TkServiceLocator.getCalendarEntriesService().getCalendarEntries(request.getParameter("selectedPayPeriod"));
            lcf.setCalendarEntry(ce);
        }
        lcf.setPrincipalId(TKUser.getCurrentTargetPerson().getPrincipalId());
        boolean isPlanningCal = TkServiceLocator.getLeaveCalendarService().isLeavePlanningCalendar(lcf.getPrincipalId(), lcf.getCalendarEntry().getBeginPeriodDateTime(), lcf.getCalendarEntry().getEndPeriodDateTime());
        lcf.setLeavePlanningCalendar(isPlanningCal);

        List<Map<String, Object>> earnCodeList = new LinkedList<Map<String, Object>>();

        if (StringUtils.isNotBlank(lcf.getSelectedAssignment())) {
            List<Assignment> assignments = lcf.getLeaveCalendarDocument().getAssignments();
            AssignmentDescriptionKey key = new AssignmentDescriptionKey(lcf.getSelectedAssignment());
            for (Assignment assignment : assignments) {
                if (assignment.getJobNumber().compareTo(key.getJobNumber()) == 0 &&
                        assignment.getWorkArea().compareTo(key.getWorkArea()) == 0 &&
                        assignment.getTask().compareTo(key.getTask()) == 0) {
                    List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService().getEarnCodesForLeave(assignment, new java.sql.Date(TKUtils.convertDateStringToTimestamp(lcf.getStartDate()).getTime()), lcf.isLeavePlanningCalendar());
                    for (EarnCode earnCode : earnCodes) {
                        Map<String, Object> earnCodeMap = new HashMap<String, Object>();
                        earnCodeMap.put("assignment", assignment.getAssignmentKey());
                        earnCodeMap.put("earnCode", earnCode.getEarnCode());
                        earnCodeMap.put("desc", earnCode.getDescription());
                        earnCodeMap.put("type", earnCode.getEarnCodeType());
                        earnCodeMap.put("earnCodeId", earnCode.getHrEarnCodeId());
                        AccrualCategory acObj = null;
                        if(earnCode.getAccrualCategory() != null) {
                        	acObj = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(earnCode.getAccrualCategory(), TKUtils.getCurrentDate());
                        }
                        String unitTime = (acObj!= null ? acObj.getUnitOfTime() : earnCode.getRecordMethod()) ;
                        earnCodeMap.put("unitOfTime", unitTime);
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
    		LeaveSummary ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(TKContext.getTargetPrincipalId(), lcf.getCalendarEntry());
		    lcf.setLeaveSummary(ls);
    	}
    	errorMsgList.addAll(LeaveCalendarValidationUtil.validateAvailableLeaveBalance(lcf));
    	//KPME-1263
        errorMsgList.addAll(LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(lcf));

        lcf.setOutputString(JSONValue.toJSONString(errorMsgList));
        
        return mapping.findForward("ws");
    }

    protected void setupDocumentOnFormContext(LeaveCalendarForm leaveForm,
                                              LeaveCalendarDocument lcd) {
        leaveForm.setLeaveCalendarDocument(lcd);
    }

}
