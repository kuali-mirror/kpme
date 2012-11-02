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
package org.kuali.hr.time.detail.web;

import java.util.ArrayList;
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
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.validation.LeaveCalendarValidationService;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.detail.validation.TimeDetailValidationService;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.web.TimesheetAction;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.web.struts.form.KualiMaintenanceForm;

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
        List<String> errors = new ArrayList<String>();
        
    	EarnCode ec = TkServiceLocator.getEarnCodeService().getEarnCode(tdaf.getSelectedEarnCode(), tdaf.getTimesheetDocument().getAsOfDate());
    	if(ec != null && ec.getLeavePlan() != null) {	// leave blocks changes
    		errors = LeaveCalendarValidationService.validateLeaveEntryDetails(tdaf.getStartDate(), tdaf.getEndDate(), tdaf.getSpanningWeeks().equalsIgnoreCase("y"));

    		//Validate leave block does not exceed max usage. Leave Calendar Validators at this point rely on a leave summary.
    		LeaveSummary leaveSummary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(TKContext.getTargetPrincipalId(), tdaf.getPayCalendarDates());
    		LeaveBlock updatedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(tdaf.getLmLeaveBlockId());
    		errors.addAll(LeaveCalendarValidationService.validateLeaveAccrualRuleMaxUsage(leaveSummary, tdaf.getSelectedEarnCode(), tdaf.getStartDate(),
        			tdaf.getEndDate(), tdaf.getLeaveAmount(), updatedLeaveBlock));

    	} else {	// time blocks changes
    		errors = TimeDetailValidationService.validateTimeEntryDetails(tdaf);
    	}
        
//        List<String> errors = TimeDetailValidationService.validateTimeEntryDetails(tdaf);
        errorMsgList.addAll(errors);

        tdaf.setOutputString(JSONValue.toJSONString(errorMsgList));
        return mapping.findForward("ws");
    }

    //this is an ajax call for the assignment maintenance page
    public ActionForward getDepartmentForJobNumber(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KualiMaintenanceForm kualiForm = (KualiMaintenanceForm) form;

        String principalId = (String) request.getAttribute("principalId");
        Long jobNumber = (Long) request.getAttribute("jobNumber");

        Job job = TkServiceLocator.getJobService().getJob(principalId, jobNumber, TKUtils.getCurrentDate());
        kualiForm.setAnnotation(job.getDept());

        return mapping.findForward("ws");
    }

    public ActionForward getEarnCodeJson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeDetailWSActionForm tdaf = (TimeDetailWSActionForm) form;
        List<Map<String, Object>> earnCodeList = new LinkedList<Map<String, Object>>();

        if (StringUtils.isNotBlank(tdaf.getSelectedAssignment())) {
            List<Assignment> assignments = tdaf.getTimesheetDocument().getAssignments();
            AssignmentDescriptionKey key = new AssignmentDescriptionKey(tdaf.getSelectedAssignment());
            for (Assignment assignment : assignments) {
                if (assignment.getJobNumber().compareTo(key.getJobNumber()) == 0 &&
                        assignment.getWorkArea().compareTo(key.getWorkArea()) == 0 &&
                        assignment.getTask().compareTo(key.getTask()) == 0) {
                    List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService().getEarnCodesForTime(assignment, tdaf.getTimesheetDocument().getAsOfDate());
                    for (EarnCode earnCode : earnCodes) {
                        Map<String, Object> earnCodeMap = new HashMap<String, Object>();
                        earnCodeMap.put("assignment", assignment.getAssignmentKey());
                        earnCodeMap.put("earnCode", earnCode.getEarnCode());
                        earnCodeMap.put("desc", earnCode.getDescription());
                        earnCodeMap.put("type", earnCode.getEarnCodeType());
                        // for leave blocks
                        earnCodeMap.put("leavePlan", earnCode.getLeavePlan());
                        if(StringUtils.isNotEmpty(earnCode.getLeavePlan())) {
                            earnCodeMap.put("fractionalTimeAllowed", earnCode.getFractionalTimeAllowed());
                            earnCodeMap.put("unitOfTime", ActionFormUtils.getUnitOfTimeForEarnCode(earnCode));
                        }
                        earnCodeList.add(earnCodeMap);
                    }
                }
            }
        }
        LOG.info(tdaf.toString());
        tdaf.setOutputString(JSONValue.toJSONString(earnCodeList));
        return mapping.findForward("ws");
    }

    private boolean shouldAddEarnCode(Assignment assignment, EarnCode earnCode, boolean isTimeBlockReadOnly) {

        Boolean shouldAddEarnCode;

        shouldAddEarnCode = earnCode.getEarnCode().equals(TkConstants.HOLIDAY_EARN_CODE)
                && !(TKContext.getUser().isSystemAdmin() || TKContext.getUser().isTimesheetApprover());

        // If the timeblock is readonly (happens when a sync user is editing a sync timeblock) and the earn code is RGH,
        // it should still add the RGH earn code.
        shouldAddEarnCode |= isTimeBlockReadOnly;

        return shouldAddEarnCode;

    }

    public ActionForward getOvertimeEarnCodes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeDetailWSActionForm tdaf = (TimeDetailWSActionForm) form;
        List<EarnCode> overtimeEarnCodes = TkServiceLocator.getEarnCodeService().getOvertimeEarnCodes(TKUtils.getCurrentDate());
        List<Map<String, Object>> overtimeEarnCodeList = new LinkedList<Map<String, Object>>();

        for (EarnCode earnCode : overtimeEarnCodes) {
            Map<String, Object> earnCodeMap = new HashMap<String, Object>();
            earnCodeMap.put("earnCode", earnCode.getEarnCode());
            earnCodeMap.put("desc", earnCode.getDescription());

            overtimeEarnCodeList.add(earnCodeMap);
        }

        LOG.info(tdaf.toString());
        tdaf.setOutputString(JSONValue.toJSONString(overtimeEarnCodeList));
        return mapping.findForward("ws");
    }

}
