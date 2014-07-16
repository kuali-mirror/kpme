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
package org.kuali.kpme.tklm.time.detail.web;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.LocalDate;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.earncode.EarnCode;
import org.kuali.kpme.core.api.earncode.security.EarnCodeSecurity;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.paytype.PayType;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.detail.validation.TimeDetailValidationUtil;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.web.TimesheetAction;
import org.kuali.rice.kns.web.struts.form.KualiMaintenanceForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
            EarnCode ec = HrServiceLocator.getEarnCodeService().getEarnCode(tdaf.getSelectedEarnCode(),
            																TKUtils.formatDateTimeStringNoTimezone(tdaf.getEndDate()).toLocalDate());
	        if(ec != null && StringUtils.isNotEmpty(ec.getLeavePlan())) {    // leave blocks changes
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
        boolean containsRegEarnCode = false;
        List<Map<String, Object>> earnCodeList = new LinkedList<Map<String, Object>>();

        if (StringUtils.isNotBlank(tdaf.getSelectedAssignment())) {
            LocalDate asOfDate = tdaf.getTimesheetDocument().getAsOfDate();

            if (tdaf.getStartDate() != null) {
                try {
                    DateTime utilDate = HrConstants.DateTimeFormats.BASIC_DATE_FORMAT.parseDateTime(tdaf.getStartDate());
                    asOfDate = utilDate.toLocalDate();
                } catch (Exception ex) {
                    //ignore and use the timesheet as of date.
                }
            }
            List<Assignment> assignments = tdaf.getTimesheetDocument().getAssignmentMap().get(asOfDate);
            AssignmentDescriptionKey key = AssignmentDescriptionKey.get(tdaf.getSelectedAssignment());
            Map<String, EarnCode> regEarnCodes = getRegularEarnCodes(assignments);
            for (Assignment assignment : assignments) {
            	if (assignment.getJobNumber().equals(key.getJobNumber()) &&
                        assignment.getGroupKeyCode().equals(key.getGroupKeyCode()) &&
            			assignment.getWorkArea().equals(key.getWorkArea()) &&
            			assignment.getTask().equals(key.getTask())) {
            		List<EarnCode> earnCodes = new ArrayList<EarnCode>();
            		if (tdaf.getTimeBlockReadOnly()) {
            			if (regEarnCodes.containsKey(assignment.getAssignmentKey())) {
            				earnCodes.add(regEarnCodes.get(assignment.getAssignmentKey()));
            			}
            		} else {
            			// use endDate to grab earn codes
            			List<EarnCode> aList = TkServiceLocator.getTimesheetService()
            					.getEarnCodesForTime(assignment, asOfDate, tdaf.getTimeBlockReadOnly());
            			
            			for(EarnCode anEarnCode : aList) {
            				// kpme-2570, overtime earn codes should not show in adding/editing time block widget's earn code option list
            				if(anEarnCode != null && !anEarnCode.isOvtEarnCode()) {
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
                        earnCodeMap.put("accrualBalanceAction", earnCode.getAccrualBalanceAction());
                        EarnCode regEarnCode = regEarnCodes.get(assignment.getAssignmentKey());
                        if (regEarnCode != null
                                && StringUtils.equals(regEarnCode.getEarnCode(), earnCode.getEarnCode())) {
                            containsRegEarnCode = true;
                        }
                        earnCodeList.add(earnCodeMap);
                    }
                }
            }
        }

        if (!containsRegEarnCode) {
            Map<String, Object> earnCodeMap = new HashMap<String, Object>();
            earnCodeMap.put("assignment", "");
            earnCodeMap.put("earnCode", "");
            earnCodeMap.put("desc", "-- select an earn code --");
            earnCodeMap.put("type", "");
            // for leave blocks
            earnCodeMap.put("leavePlan", "");
            earnCodeMap.put("eligibleForAccrual", "");

            earnCodeList.add(0, earnCodeMap);
        }
        LOG.info(tdaf.toString());
        tdaf.setOutputString(JSONValue.toJSONString(earnCodeList));
        
        return mapping.findForward("ws");
    }
    
    protected Map<String, EarnCode> getRegularEarnCodes(List<Assignment> assignments) {
    	Map<String, EarnCode> regEarnCodes = new HashMap<String, EarnCode>();
    	if (CollectionUtils.isNotEmpty(assignments)) {
    		for (Assignment a : assignments) {
    			if (a.getJob() != null
    					&& a.getJob().getPayTypeObj() != null) {
    				PayType payType = a.getJob().getPayTypeObj();
                    if (payType.getRegEarnCodeObj() != null) {
                        EarnCode ec = EarnCode.Builder.create(payType.getRegEarnCodeObj()).build();
                        if (ec == null
                                && StringUtils.isNotEmpty(payType.getRegEarnCode()))  {
                            ec =  HrServiceLocator.getEarnCodeService().getEarnCode(payType.getRegEarnCode(), payType.getEffectiveLocalDate());
                        }
                        regEarnCodes.put(a.getAssignmentKey(), ec);
                    }
                }
            }
        }
    	return regEarnCodes;
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

    public ActionForward getOvertimeEarnCodes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeDetailWSActionForm tdaf = (TimeDetailWSActionForm) form;
        List<EarnCode> overtimeEarnCodes = HrServiceLocator.getEarnCodeService().getOvertimeEarnCodes(LocalDate.now());
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
        				
        				List<EarnCodeSecurity> securityList = HrServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurityList(
        						job.getDept(), 
        						job.getHrSalGroup(), 
        						earnCode.getEarnCode(), 
        						employee, 
        						approver, 
        						payrollProcessor,
        						"Y", tb.getEndDateTime().toLocalDate(), job.getGroupKeyCode());
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

    public ActionForward getAssignmentJson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        /*
        http://ci.kpme.kuali.org/kpme-trunk/TimeDetailWS.do?methodToCall=getAssignmentJson&documentId=3194&startDate=7%2F7%2F2014&_=1405538575193
         */


        TimeDetailWSActionForm tdaf = (TimeDetailWSActionForm) form;

        String chosenStartDate = tdaf.getStartDate();
        String chosenEndDate = tdaf.getEndDate();

        DateTime calendarBeginDate = tdaf.getTimesheetDocument().getCalendarEntry().getBeginPeriodFullDateTime();
        DateTime calendarEndDate = tdaf.getTimesheetDocument().getCalendarEntry().getEndPeriodFullDateTime();

        //tdaf.getEndDate();

        String earnCode = tdaf.getSelectedEarnCode();
        LocalDate asOfDate = null;
        LocalDate asOfEndDate = null;
        DateTime utilDate = null;
        DateTime utilEndDate = null;

        if (tdaf.getStartDate() != null) {
            try {
                utilDate = HrConstants.DateTimeFormats.BASIC_DATE_FORMAT.parseDateTime(tdaf.getStartDate());
                asOfDate = utilDate.toLocalDate();

                utilEndDate = HrConstants.DateTimeFormats.BASIC_DATE_FORMAT.parseDateTime(tdaf.getEndDate());
                asOfEndDate = utilDate.toLocalDate();
            } catch (Exception ex) {
                //ignore and use the timesheet as of date.
            }
        }

        List<Map<String, Object>> assignments = new ArrayList<Map<String, Object>>();
        if (asOfDate == null) {
            Map<String, Object> assignmentMap = new HashMap<String, Object>(2);
            assignmentMap.put("assignment", "");
            assignmentMap.put("desc", "-- enter valid date range --");
            assignments.add(assignmentMap);
        }

        boolean invalidStartOrEndDates = false;

        if ( (utilDate.isBefore(calendarBeginDate)) || (utilDate.isAfter(calendarEndDate)))
        {
            invalidStartOrEndDates = true;
        }

        if ( (utilEndDate != null) &&
                ( (utilEndDate.isAfter(calendarEndDate)) || (utilEndDate.isBefore(calendarBeginDate) )) )
        {
            invalidStartOrEndDates = true;
        }

        if ( (utilDate.isEqual(calendarEndDate)) || (utilEndDate.isEqual(calendarEndDate)))
        {
            invalidStartOrEndDates = true;
        }

        if (invalidStartOrEndDates)
        {
            Map<String, Object> assignmentMap = new HashMap<String, Object>(2);
            assignmentMap.put("assignment", "");
            assignmentMap.put("desc", "-- no assignments found --");
            assignments.add(assignmentMap);

            tdaf.setOutputString(JSONValue.toJSONString(assignments));
            return mapping.findForward("ws");
        }

        if (tdaf.getTimesheetDocument() != null
                && asOfDate != null) {
            //check date to see if assignment is active
            String principalId = tdaf.getTimesheetDocument().getPrincipalId();
            CalendarEntry ce = tdaf.getTimesheetDocument().getCalendarEntry();
            Map<LocalDate, List<Assignment>> history = tdaf.getTimesheetDocument().getAssignmentMap();
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
            } else {
                Map<String, Object> assignmentMap = new HashMap<String, Object>(2);
                assignmentMap.put("assignment", "");
                assignmentMap.put("desc", "-- no assignments found --");
                assignments.add(assignmentMap);
            }
        }

        tdaf.setOutputString(JSONValue.toJSONString(assignments));
        return mapping.findForward("ws");
    }
}
