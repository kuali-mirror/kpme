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
package org.kuali.kpme.tklm.time.approval.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hsqldb.lib.StringUtil;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.json.simple.JSONValue;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.web.KPMEAction;
import org.kuali.kpme.tklm.common.CalendarApprovalForm;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.detail.web.ActionFormUtils;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.timesummary.AssignmentColumn;
import org.kuali.kpme.tklm.time.timesummary.AssignmentRow;
import org.kuali.kpme.tklm.time.timesummary.EarnCodeSection;
import org.kuali.kpme.tklm.time.timesummary.EarnGroupSection;
import org.kuali.kpme.tklm.time.timesummary.TimeSummary;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;

import com.google.common.collect.Lists;

public class TimeApprovalWSAction extends KPMEAction {

    /**
     * Action called via AJAX. (ajaj really...)
     * <p/>
     * This search returns quick-results to the search box for the user to further
     * refine upon. The end value can then be form submitted.
     */
    public ActionForward searchApprovalRows(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        List<Map<String, String>> results = new LinkedList<Map<String, String>>();

        List<String> workAreaList = new ArrayList<String>();
        if(StringUtil.isEmpty(taaf.getSelectedWorkArea())) {
        	String principalId = HrContext.getTargetPrincipalId();
        	
        	Set<Long> workAreas = new HashSet<Long>();
        	workAreas.addAll(HrServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(principalId, KPMERole.APPROVER.getRoleName(), new DateTime(), true));
            workAreas.addAll(HrServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(principalId, KPMERole.APPROVER_DELEGATE.getRoleName(), new DateTime(), true));

        	for(Long workArea : workAreas) {
        		workAreaList.add(workArea.toString());
        	}
        } else {
        	workAreaList.add(taaf.getSelectedWorkArea());
        }
        LocalDate endDate = taaf.getCalendarEntry().getEndPeriodFullDateTime().toLocalDate();
        LocalDate beginDate = taaf.getCalendarEntry().getBeginPeriodFullDateTime().toLocalDate();

        List<String> principalIds = TkServiceLocator.getTimeApproveService()
			.getTimePrincipalIdsWithSearchCriteria(workAreaList, taaf.getSelectedPayCalendarGroup(),
					endDate, beginDate, endDate); 
        
        
        if (StringUtils.equals(taaf.getSearchField(), CalendarApprovalForm.ORDER_BY_PRINCIPAL)) {
            for (String id : principalIds) {
                if(StringUtils.contains(id, taaf.getSearchTerm())) {
                    Map<String, String> labelValue = new HashMap<String, String>();
                    labelValue.put("id", id);
                    labelValue.put("result", id);
                    results.add(labelValue);
                }
            }
        } else if (StringUtils.equals(taaf.getSearchField(), CalendarApprovalForm.ORDER_BY_DOCID)) {
            Map<String, TimesheetDocumentHeader> principalDocumentHeaders =
            		TkServiceLocator.getTimeApproveService().getPrincipalDocumentHeader(principalIds, beginDate.toDateTimeAtStartOfDay(), endDate.toDateTimeAtStartOfDay());
            
            for (Map.Entry<String,TimesheetDocumentHeader> entry : principalDocumentHeaders.entrySet()) {
                if (StringUtils.contains(entry.getValue().getDocumentId(), taaf.getSearchTerm())) {
                    Map<String, String> labelValue = new HashMap<String, String>();
//                        labelValue.put("id", entry.getValue().getDocumentId() + " (" + entry.getValue().getPrincipalId() + ")");
                    labelValue.put("id", entry.getValue().getDocumentId());
                    labelValue.put("result", entry.getValue().getPrincipalId());
                    results.add(labelValue);
                }
            }
        }
     
        taaf.setOutputString(JSONValue.toJSONString(results));
        return mapping.findForward("ws");
    }
    
    public ActionForward getTimeSummary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        TimesheetDocument td = TkServiceLocator.getTimesheetService().getTimesheetDocument(taaf.getDocumentId());
		TimeSummary ts = TkServiceLocator.getTimeSummaryService().getTimeSummary(td);
		
		List<Assignment> assignments = td.getAssignments();
		List<String> assignmentKeys = new ArrayList<String>();
		for(Assignment assignment : assignments) {
			assignmentKeys.add(assignment.getAssignmentKey());
		}
		List<TimeBlock> timeBlocks = td.getTimeBlocks();
		List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksForTimeCalendar(td.getPrincipalId(), td.getAsOfDate(), td.getDocEndDate(), assignmentKeys);
		Map<String, String> aMap = ActionFormUtils.buildAssignmentStyleClassMap(timeBlocks, leaveBlocks);
		// set css classes for each assignment row
		for (EarnGroupSection earnGroupSection : ts.getSections()) {
			for (EarnCodeSection section : earnGroupSection.getEarnCodeSections()) {
				for (AssignmentRow assignRow : section.getAssignmentsRows()) {
					String assignmentCssStyle = MapUtils.getString(aMap, assignRow.getAssignmentKey());
					assignRow.setCssClass(assignmentCssStyle);
					for (AssignmentColumn assignmentColumn : assignRow.getAssignmentColumns()) {
						assignmentColumn.setCssClass(assignmentCssStyle);
					}
				}
			}
		}
	 	
		//reverse sections for javascripts $parent.after (always inserting directly after parent element, which reverses order)

		ts.setSections(Lists.reverse(ts.getSections()));
        taaf.setOutputString(ts.toJsonString());
        return mapping.findForward("ws");
    }
        
}
