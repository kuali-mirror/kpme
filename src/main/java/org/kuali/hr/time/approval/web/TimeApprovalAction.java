package org.kuali.hr.time.approval.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;

public class TimeApprovalAction extends TkAction { 

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		return super.execute(mapping, form, request, response);
	}
	
	public ActionForward load(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeApprovalActionForm taf = (TimeApprovalActionForm)form;
		TKUser tkUser = TKContext.getUser();
		taf.setName(tkUser.getCurrentPerson().getName());
		taf.setPayBeginDate(TKUtils.createDate(10, 1, 2011, 0, 0, 0));
		taf.setPayEndDate(TKUtils.createDate(10, 15, 2011, 23, 59, 59));

		taf.setPayCalendarLabels(TkServiceLocator.getTimeApproveService().getPayCalendarLabelsForApprovalTab(taf.getPayBeginDate(), taf.getPayEndDate()));
		taf.setApprovalTimeSummaryRows(TkServiceLocator.getTimeApproveService().getApprovalSummaryRows(taf.getPayBeginDate(), taf.getPayEndDate()));
		return mapping.findForward("basic");
	}

}
