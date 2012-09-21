package org.kuali.hr.lm.approval.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONValue;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.base.web.TkApprovalForm;
import org.kuali.hr.time.person.TKPerson;
import org.kuali.hr.time.service.base.TkServiceLocator;

public class LeaveApprovalWSAction extends TkAction {

	 public ActionForward getLeaveSummary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	LeaveApprovalWSActionForm laaf = (LeaveApprovalWSActionForm) form;
    	String docId = laaf.getDocumentId();
    	LeaveCalendarDocumentHeader lcdh = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(docId);
    	if(lcdh != null) {
    		List<Map<String, Object>> detailMap = TkServiceLocator.getLeaveApprovalService().getLaveApprovalDetailSections(lcdh);
    		
    		String jsonString = JSONValue.toJSONString(detailMap);
    		laaf.setOutputString(jsonString);
    	}
    	
    	return mapping.findForward("ws");
	 }
	 
	  public ActionForward searchApprovalRows(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		  LeaveApprovalWSActionForm laaf = (LeaveApprovalWSActionForm) form;
	        List<Map<String, String>> results = new LinkedList<Map<String, String>>();
	        if(StringUtils.isNotEmpty(laaf.getPayBeginDateForSearch()) 
	        		&& StringUtils.isNotEmpty(laaf.getPayEndDateForSearch()) ) {
		        Date beginDate = new SimpleDateFormat("MM/dd/yyyy").parse(laaf.getPayBeginDateForSearch());
		        Date endDate = new SimpleDateFormat("MM/dd/yyyy").parse(laaf.getPayEndDateForSearch());
		        
		        List<String> principalIds = TkServiceLocator.getLeaveApprovalService().getPrincipalIdsByDeptWorkAreaRolename(laaf.getRoleName(), laaf.getSelectedDept(), laaf.getSelectedWorkArea(), new java.sql.Date(beginDate.getTime()), new java.sql.Date(endDate.getTime()), laaf.getSelectedPayCalendarGroup());
		        List<TKPerson> persons = TkServiceLocator.getPersonService().getPersonCollection(principalIds);
		        
		        if (StringUtils.equals(laaf.getSearchField(), TkApprovalForm.ORDER_BY_PRINCIPAL)) {
		            for (String id : principalIds) {
		                if(StringUtils.contains(id, laaf.getSearchTerm())) {
		                    Map<String, String> labelValue = new HashMap<String, String>();
		                    labelValue.put("id", id);
		                    labelValue.put("result", id);
		                    results.add(labelValue);
		                }
		            }
		        } else if (StringUtils.equals(laaf.getSearchField(), TkApprovalForm.ORDER_BY_DOCID)) {
		            Map<String, LeaveCalendarDocumentHeader> principalDocumentHeaders =
		                    TkServiceLocator.getLeaveApprovalService().getPrincipalDocumehtHeader(persons, beginDate, endDate);
	
		            for (Map.Entry<String,LeaveCalendarDocumentHeader> entry : principalDocumentHeaders.entrySet()) {
		                if (StringUtils.contains(entry.getValue().getDocumentId(), laaf.getSearchTerm())) {
		                    Map<String, String> labelValue = new HashMap<String, String>();
		                    labelValue.put("id", entry.getValue().getDocumentId() + " (" + entry.getValue().getPrincipalId() + ")");
		                    labelValue.put("result", entry.getValue().getPrincipalId());
		                    results.add(labelValue);
		                }
		            }
		        }
	        }
		
	      laaf.setOutputString(JSONValue.toJSONString(results));
	        
	      return mapping.findForward("ws");
	    }
}
