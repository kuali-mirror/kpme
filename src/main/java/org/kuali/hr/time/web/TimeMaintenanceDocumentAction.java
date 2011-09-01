package org.kuali.hr.time.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.rice.kns.web.struts.action.KualiMaintenanceDocumentAction;

public class TimeMaintenanceDocumentAction extends
		KualiMaintenanceDocumentAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		((TimeMaintenanceForm) form).registerEditableProperty("methodToCall.refresh");
		return super.execute(mapping, form, request, response);
	}

	@Override
	public ActionForward refresh(ActionMapping arg0, ActionForm arg1,
			HttpServletRequest arg2, HttpServletResponse arg3) throws Exception {
		// TODO Auto-generated method stub
		return super.refresh(arg0, arg1, arg2, arg3);
	}

}
