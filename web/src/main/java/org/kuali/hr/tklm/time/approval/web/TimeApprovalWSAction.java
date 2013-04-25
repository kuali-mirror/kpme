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
package org.kuali.hr.tklm.time.approval.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hsqldb.lib.StringUtil;
import org.joda.time.DateTime;
import org.json.simple.JSONValue;
import org.kuali.hr.core.ApprovalForm;
import org.kuali.hr.core.role.KPMERole;
import org.kuali.hr.tklm.time.base.web.TkAction;
import org.kuali.hr.tklm.time.person.TKPerson;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.timesheet.TimesheetDocument;
import org.kuali.hr.tklm.time.timesummary.TimeSummary;
import org.kuali.hr.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.krad.util.GlobalVariables;

public class TimeApprovalWSAction extends TkAction {

    private static final Logger LOG = Logger.getLogger(TimeApprovalWSAction.class);

    /**
     * Action called via AJAX. (ajaj really...)
     * <p/>
     * This search returns quick-results to the search box for the user to further
     * refine upon. The end value can then be form submitted.
     */
    public ActionForward searchApprovalRows(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        List<Map<String, String>> results = new LinkedList<Map<String, String>>();
        if(StringUtils.isNotEmpty(taaf.getPayBeginDateForSearch()) 
        		&& StringUtils.isNotEmpty(taaf.getPayEndDateForSearch()) ) {
	        DateTime beginDate = new DateTime(new SimpleDateFormat("MM/dd/yyyy").parse(taaf.getPayBeginDateForSearch()));
	        DateTime endDate = new DateTime(new SimpleDateFormat("MM/dd/yyyy").parse(taaf.getPayEndDateForSearch()));
            //the endDate we get here is coming from approval.js and is extracted from html. we need to add a day to cover the last day in the pay period.
            endDate = endDate.plusDays(1);
            List<String> workAreaList = new ArrayList<String>();
	        if(StringUtil.isEmpty(taaf.getSelectedWorkArea())) {
	        	String principalId = GlobalVariables.getUserSession().getPrincipalId();
	        	
	        	Set<Long> workAreas = new HashSet<Long>();
	        	workAreas.addAll(TkServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(principalId, KPMERole.APPROVER.getRoleName(), new DateTime(), true));
	            workAreas.addAll(TkServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(principalId, KPMERole.APPROVER_DELEGATE.getRoleName(), new DateTime(), true));

	        	for(Long workArea : workAreas) {
	        		workAreaList.add(workArea.toString());
	        	}
	        } else {
	        	workAreaList.add(taaf.getSelectedWorkArea());
	        }
	        List<String> principalIds = TkServiceLocator.getTimeApproveService()
				.getTimePrincipalIdsWithSearchCriteria(workAreaList, taaf.getSelectedPayCalendarGroup(),
					endDate.toLocalDate(), beginDate.toLocalDate(), endDate.toLocalDate()); 
	        
	        List<TKPerson> persons = TkServiceLocator.getPersonService().getPersonCollection(principalIds);
	        
	        if (StringUtils.equals(taaf.getSearchField(), ApprovalForm.ORDER_BY_PRINCIPAL)) {
	            for (String id : principalIds) {
	                if(StringUtils.contains(id, taaf.getSearchTerm())) {
	                    Map<String, String> labelValue = new HashMap<String, String>();
	                    labelValue.put("id", id);
	                    labelValue.put("result", id);
	                    results.add(labelValue);
	                }
	            }
	        } else if (StringUtils.equals(taaf.getSearchField(), ApprovalForm.ORDER_BY_DOCID)) {
	            Map<String, TimesheetDocumentHeader> principalDocumentHeaders =
	                    TkServiceLocator.getTimeApproveService().getPrincipalDocumehtHeader(persons, beginDate, endDate);

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
        }
     
        taaf.setOutputString(JSONValue.toJSONString(results));
        return mapping.findForward("ws");
    }
    
    public ActionForward getTimeSummary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        TimesheetDocument td = TkServiceLocator.getTimesheetService().getTimesheetDocument(taaf.getDocumentId());
		TimeSummary ts = TkServiceLocator.getTimeSummaryService().getTimeSummary(td);
		
        taaf.setOutputString(ts.toJsonString());
        return mapping.findForward("ws");
    }
        
}
