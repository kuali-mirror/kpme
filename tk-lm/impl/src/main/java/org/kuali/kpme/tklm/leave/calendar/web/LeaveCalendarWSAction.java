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
package org.kuali.kpme.tklm.leave.calendar.web;

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
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.earncode.EarnCode;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.kpme.tklm.leave.calendar.validation.LeaveCalendarValidationUtil;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.summary.LeaveSummary;
import org.kuali.kpme.tklm.time.detail.web.ActionFormUtils;

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

        if (StringUtils.isNotBlank(lcf.getSelectedAssignment())) {
        	List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignmentsByCalEntryForLeaveCalendar(HrContext.getTargetPrincipalId(), lcf.getCalendarEntry());
            AssignmentDescriptionKey key = AssignmentDescriptionKey.get(lcf.getSelectedAssignment());
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

    	if(lcf.getLeaveSummary() == null && lcf.getCalendarEntry() != null) {
    		LeaveSummary ls = LmServiceLocator.getLeaveSummaryService().getLeaveSummary(HrContext.getTargetPrincipalId(), lcf.getCalendarEntry());
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
