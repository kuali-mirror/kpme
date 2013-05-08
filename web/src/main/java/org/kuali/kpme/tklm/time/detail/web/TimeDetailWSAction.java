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
package org.kuali.kpme.tklm.time.detail.web;

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
import org.joda.time.LocalDate;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.bo.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.bo.earncode.EarnCode;
import org.kuali.kpme.core.bo.job.Job;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.calendar.validation.LeaveCalendarValidationUtil;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.summary.LeaveSummary;
import org.kuali.kpme.tklm.time.detail.validation.TimeDetailValidationUtil;
import org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.web.TimesheetAction;
import org.kuali.rice.kns.web.struts.form.KualiMaintenanceForm;
import org.kuali.rice.krad.util.ObjectUtils;

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
        List<String> errors;
        
    	EarnCode ec = HrServiceLocator.getEarnCodeService().getEarnCode(tdaf.getSelectedEarnCode(), tdaf.getTimesheetDocument().getAsOfDate());
    	if(ec != null 
    			&& (ec.getLeavePlan() != null || ec.getEligibleForAccrual().equals("N"))) {	// leave blocks changes
    		errors = this.validateLeaveEntry(tdaf);
    	} else {	// time blocks changes
    		errors = TimeDetailValidationUtil.validateTimeEntryDetails(tdaf);
    	}
        
//        List<String> errors = TimeDetailValidationService.validateTimeEntryDetails(tdaf);
        errorMsgList.addAll(errors);

        tdaf.setOutputString(JSONValue.toJSONString(errorMsgList));
        return mapping.findForward("ws");
    }

    public List<String> validateLeaveEntry(TimeDetailActionFormBase tdaf) throws Exception {
    	List<String> errorMsgList = new ArrayList<String>();
    	CalendarEntry payCalendarEntry = tdaf.getPayCalendarDates();
    	if(ObjectUtils.isNotNull(payCalendarEntry)) {
			LeaveSummary ls = LmServiceLocator.getLeaveSummaryService().getLeaveSummary(HrContext.getTargetPrincipalId(), tdaf.getPayCalendarDates());
			LeaveBlock lb = null;
			if(StringUtils.isNotEmpty(tdaf.getLmLeaveBlockId())) {
				lb = LmServiceLocator.getLeaveBlockService().getLeaveBlock(tdaf.getLmLeaveBlockId());
			}
			
			// Validate LeaveBlock timings and all that
			errorMsgList.addAll(LeaveCalendarValidationUtil.validateParametersForLeaveEntry(tdaf.getSelectedEarnCode(), tdaf.getPayCalendarDates(),
					tdaf.getStartDate(), tdaf.getEndDate(), tdaf.getStartTime(), tdaf.getEndTime(), tdaf.getSelectedAssignment(), null, tdaf.getLmLeaveBlockId()));
			
			errorMsgList.addAll(LeaveCalendarValidationUtil.validateAvailableLeaveBalanceForUsage(tdaf.getSelectedEarnCode(), 
					tdaf.getStartDate(), tdaf.getEndDate(), tdaf.getLeaveAmount(), lb));
			//Validate leave block does not exceed max usage. Leave Calendar Validators at this point rely on a leave summary.
	        errorMsgList.addAll(LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(ls, tdaf.getSelectedEarnCode(),
                    tdaf.getStartDate(), tdaf.getEndDate(), tdaf.getLeaveAmount(), lb));
		}
		return errorMsgList;
    }
    
    
    //this is an ajax call for the assignment maintenance page
    public ActionForward getDepartmentForJobNumber(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KualiMaintenanceForm kualiForm = (KualiMaintenanceForm) form;

        String principalId = (String) request.getAttribute("principalId");
        Long jobNumber = (Long) request.getAttribute("jobNumber");

        Job job = HrServiceLocator.getJobService().getJob(principalId, jobNumber, LocalDate.now());
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
                    List<EarnCode> earnCodes = TkServiceLocator.getTimesheetService().getEarnCodesForTime(assignment, tdaf.getTimesheetDocument().getAsOfDate());
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
                        earnCodeMap.put("eligibleForAccrual", earnCode.getEligibleForAccrual());
                        earnCodeList.add(earnCodeMap);
                    }
                }
            }
        }
        LOG.info(tdaf.toString());
        tdaf.setOutputString(JSONValue.toJSONString(earnCodeList));
        return mapping.findForward("ws");
    }

    public ActionForward getOvertimeEarnCodes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeDetailWSActionForm tdaf = (TimeDetailWSActionForm) form;
        List<EarnCode> overtimeEarnCodes = HrServiceLocator.getEarnCodeService().getOvertimeEarnCodes(LocalDate.now());
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
