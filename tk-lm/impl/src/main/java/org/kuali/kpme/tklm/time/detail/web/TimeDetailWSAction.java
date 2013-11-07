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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.LocalDate;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.earncode.EarnCode;
import org.kuali.kpme.core.earncode.security.EarnCodeSecurity;
import org.kuali.kpme.core.paytype.PayType;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.time.detail.validation.TimeDetailValidationUtil;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.timesheet.web.TimesheetAction;
import org.kuali.rice.kns.web.struts.form.KualiMaintenanceForm;

public class TimeDetailWSAction extends TimesheetAction {

    private static final Logger LOG = Logger.getLogger(TimeDetailWSAction.class);

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
        
        // validates the selected earn code exists on every day within the date range
        errors = TimeDetailValidationUtil.validateEarnCode(tdaf.getSelectedEarnCode(), tdaf.getStartDate(), tdaf.getEndDate());
        if(errors.isEmpty()) {
            EarnCodeContract ec = HrServiceLocator.getEarnCodeService().getEarnCode(tdaf.getSelectedEarnCode(),
            																TKUtils.formatDateTimeStringNoTimezone(tdaf.getEndDate()).toLocalDate());
	        if(ec != null && ec.getLeavePlan() != null) {    // leave blocks changes
	    		errors = TimeDetailValidationUtil.validateLeaveEntry(tdaf);
	    	} else {	// time blocks changes
	    		errors = TimeDetailValidationUtil.validateTimeEntryDetails(tdaf);
	    	}
        }
        errorMsgList.addAll(errors);

        tdaf.setOutputString(JSONValue.toJSONString(errorMsgList));
        return mapping.findForward("ws");
    }

/*
 * Moved to TimeDetailValidationUtil
 * 
 *     public List<String> validateLeaveEntry(TimeDetailActionFormBase tdaf) throws Exception {
    	List<String> errorMsgList = new ArrayList<String>();
    	CalendarEntry payCalendarEntry = tdaf.getCalendarEntry();
    	if(ObjectUtils.isNotNull(payCalendarEntry)) {
			LeaveBlock lb = null;
			if(StringUtils.isNotEmpty(tdaf.getLmLeaveBlockId())) {
				lb = LmServiceLocator.getLeaveBlockService().getLeaveBlock(tdaf.getLmLeaveBlockId());
			}
			
			// Validate LeaveBlock timings and all that
			errorMsgList.addAll(LeaveCalendarValidationUtil.validateParametersForLeaveEntry(tdaf.getSelectedEarnCode(), tdaf.getCalendarEntry(),
					tdaf.getStartDate(), tdaf.getEndDate(), tdaf.getStartTime(), tdaf.getEndTime(), tdaf.getSelectedAssignment(), null, tdaf.getLmLeaveBlockId()));
			errorMsgList.addAll(LeaveCalendarValidationUtil.validateAvailableLeaveBalanceForUsage(tdaf.getSelectedEarnCode(), 
					tdaf.getStartDate(), tdaf.getEndDate(), tdaf.getLeaveAmount(), lb));
			//Validate leave block does not exceed max usage. Leave Calendar Validators at this point rely on a leave summary.
	        errorMsgList.addAll(LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(tdaf.getSelectedEarnCode(),
                    tdaf.getStartDate(), tdaf.getEndDate(), tdaf.getLeaveAmount(), lb));
	        errorMsgList.addAll(LeaveCalendarValidationUtil.validateHoursUnderTwentyFour(tdaf.getSelectedEarnCode(),
	        		tdaf.getStartDate(), tdaf.getEndDate(), tdaf.getLeaveAmount()));
		}
		return errorMsgList;
    }*/
    
    
    //this is an ajax call for the assignment maintenance page
    public ActionForward getDepartmentForJobNumber(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KualiMaintenanceForm kualiForm = (KualiMaintenanceForm) form;

        String principalId = (String) request.getAttribute("principalId");
        Long jobNumber = (Long) request.getAttribute("jobNumber");

        JobContract job = HrServiceLocator.getJobService().getJob(principalId, jobNumber, LocalDate.now());
        kualiForm.setAnnotation(job.getDept());

        return mapping.findForward("ws");
    }

    public ActionForward getEarnCodeJson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeDetailWSActionForm tdaf = (TimeDetailWSActionForm) form;
        
        List<Map<String, Object>> earnCodeList = new LinkedList<Map<String, Object>>();

        if (StringUtils.isNotBlank(tdaf.getSelectedAssignment())) {
            List<Assignment> assignments = tdaf.getTimesheetDocument().getAssignments();
            AssignmentDescriptionKey key = AssignmentDescriptionKey.get(tdaf.getSelectedAssignment());
            Map<String, EarnCode> regEarnCodes = getRegularEarnCodes(tdaf.getTimesheetDocument());
            for (Assignment assignment : assignments) {
            	if (assignment.getJobNumber().equals(key.getJobNumber()) &&
            			assignment.getWorkArea().equals(key.getWorkArea()) &&
            			assignment.getTask().equals(key.getTask())) {
            		List<EarnCode> earnCodes = new ArrayList<EarnCode>();
            		if (tdaf.getTimeBlockReadOnly()) {
            			if (regEarnCodes.containsKey(assignment.getAssignmentKey())) {
            				earnCodes.add(regEarnCodes.get(assignment.getAssignmentKey()));
            			}
            		} else {
            			LocalDate endDate = tdaf.getTimesheetDocument().getDocEndDate();
            			if(StringUtils.isNotBlank(tdaf.getEndDate())) {
            				LocalDate tempDate = TKUtils.formatDateString(tdaf.getEndDate());
            				if(tempDate != null) {
            					endDate = tempDate;
            				}
            			}
            			
            			// use endDate to grab earn codes
            			List<EarnCode> aList = TkServiceLocator.getTimesheetService()
            					.getEarnCodesForTime(assignment, endDate, tdaf.getTimeBlockReadOnly());
            			
            			for(EarnCode anEarnCode : aList) {
            				// kpme-2570, overtime earn codes should not show in adding/editing time block widget's earn code option list
            				if(anEarnCode != null && !anEarnCode.getOvtEarnCode()) {
            					earnCodes.add(anEarnCode);
            				}
            			}
            		}
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
    
    private Map<String, EarnCode> getRegularEarnCodes(TimesheetDocument td) {
    	Map<String, EarnCode> regEarnCodes = new HashMap<String, EarnCode>();
    	if (td != null) {
    		for (Assignment a : td.getAssignments()) {
    			if (a.getJob() != null
    					&& a.getJob().getPayTypeObj() != null) {
    				PayType payType = a.getJob().getPayTypeObj();
    				EarnCode ec = payType.getRegEarnCodeObj();
    				if (ec == null
    						&& StringUtils.isNotEmpty(payType.getRegEarnCode()))  {
    					ec = (EarnCode) HrServiceLocator.getEarnCodeService().getEarnCode(payType.getRegEarnCode(), payType.getEffectiveLocalDate());
    				}
    				regEarnCodes.put(a.getAssignmentKey(), ec);
	    			}
    			}
    		}
    	return regEarnCodes;
	}

	private List<Map<String, Object>> getAssignmentsForRegEarnCode(TimesheetDocument td, String earnCode) {
		List<Map<String, Object>> assignments = new ArrayList<Map<String, Object>>();
		if (td != null) {
			for (Assignment a : td.getAssignments()) {
				Map<String, Object> assignment = new HashMap<String, Object>();
				if (earnCode.equals(a.getJob().getPayTypeObj().getRegEarnCode())) {
					assignment.put("assignment", a.getAssignmentKey());
					assignments.add(assignment);
				}
			}
		}
		return assignments;
	}

	public ActionForward getValidAssignments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeDetailWSActionForm tdaf = (TimeDetailWSActionForm) form;

		String earnCode = tdaf.getSelectedEarnCode();

		List<Map<String, Object>> assignments = new ArrayList<Map<String, Object>>();
		if (tdaf.getTimesheetDocument() != null && StringUtils.isNotEmpty(earnCode)) {
			assignments = getAssignmentsForRegEarnCode(tdaf.getTimesheetDocument(), earnCode);
		}
		tdaf.setOutputString(JSONValue.toJSONString(assignments));
		return mapping.findForward("ws");
	}

    public ActionForward getOvertimeEarnCodes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeDetailWSActionForm tdaf = (TimeDetailWSActionForm) form;
        List<EarnCode> overtimeEarnCodes = (List<EarnCode>) HrServiceLocator.getEarnCodeService().getOvertimeEarnCodes(LocalDate.now());
        List<Map<String, Object>> overtimeEarnCodeList = new LinkedList<Map<String, Object>>();
        
        if(StringUtils.isNotEmpty(tdaf.getTkTimeBlockId())) {
        	TimeBlock tb = TkServiceLocator.getTimeBlockService().getTimeBlock(tdaf.getTkTimeBlockId());
        	if(tb != null) {
        		JobContract job = HrServiceLocator.getJobService().getJob(HrContext.getTargetPrincipalId(), tb.getJobNumber(), tb.getEndDateTime().toLocalDate());
        		if(job != null) {
        			for (EarnCode earnCode : overtimeEarnCodes) {
        				String employee = HrContext.isActiveEmployee() ? "Y" : null;
        				String approver = HrContext.isApprover() ? "Y" : null;
        				String payrollProcessor = HrContext.isPayrollProcessor() ? "Y" : null; // KPME-2532
        				
        				List<EarnCodeSecurity> securityList = (List<EarnCodeSecurity>) HrServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurityList(job.getDept(), job.getHrSalGroup(), earnCode.getEarnCode(), employee, approver, payrollProcessor, job.getLocation(),
        									"Y", tb.getEndDateTime().toLocalDate());
        				if(CollectionUtils.isNotEmpty(securityList)) {
        					Map<String, Object> earnCodeMap = new HashMap<String, Object>();
	        	            earnCodeMap.put("earnCode", earnCode.getEarnCode());
	        	            earnCodeMap.put("desc", earnCode.getDescription());
	        	            overtimeEarnCodeList.add(earnCodeMap);
        				}
        	        }
        		}
        	}
        
        }
        LOG.info(tdaf.toString());
        tdaf.setOutputString(JSONValue.toJSONString(overtimeEarnCodeList));
        return mapping.findForward("ws");
    }

}
