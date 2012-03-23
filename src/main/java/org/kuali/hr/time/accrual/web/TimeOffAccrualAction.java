package org.kuali.hr.time.accrual.web;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TimeOffAccrualAction extends TkAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		TimeOffAccrualActionForm toaaf = (TimeOffAccrualActionForm) form;
		toaaf.setTimeOffAccrualsCalc(TkServiceLocator.getTimeOffAccrualService().getTimeOffAccrualsCalc(TKContext.getTargetPrincipalId(), TKUtils.getCurrentDate()));
		
		return super.execute(mapping, form, request, response);
	}
	
}
