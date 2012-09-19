package org.kuali.hr.lm.approval.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONValue;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.approval.web.TimeApprovalActionForm;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.service.base.TkServiceLocator;

public class LeaveApprovalWSAction extends TkAction {
	
	 public ActionForward getLeaveSummary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    	TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
	    	String docId = taaf.getDocumentId();
	    	LeaveCalendarDocumentHeader lcdh = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(docId);
	    	if(lcdh != null) {
	    		List<Map<String, Object>> detailMap = TkServiceLocator.getLeaveApprovalService().getLaveApprovalDetailSectins(lcdh);
	    		 
	    		String jsonString = JSONValue.toJSONString(detailMap);
	    		taaf.setOutputString(jsonString);
	    	}
	    	
	    	return mapping.findForward("ws");
	    }
}
