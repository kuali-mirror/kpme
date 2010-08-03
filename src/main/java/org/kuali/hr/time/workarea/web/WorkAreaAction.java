package org.kuali.hr.time.workarea.web;

import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.role.assign.service.TkRoleAssignService;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.hr.time.workarea.WorkAreaMaintenanceDocument;
import org.kuali.hr.time.workarea.service.WorkAreaService;
import org.kuali.rice.kns.web.struts.action.KualiTransactionalDocumentActionBase;

import uk.ltd.getahead.dwr.util.Logger;

public class WorkAreaAction extends KualiTransactionalDocumentActionBase {

    private static final Logger LOG = Logger.getLogger(WorkAreaAction.class);

    @Override
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	log.debug("Call to save.");
	return super.save(mapping, form, request, response);
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, ServletRequest request, ServletResponse response) throws Exception {
	// TODO Auto-generated method stub
	return super.execute(mapping, form, request, response);
    }

    @Override
    public ActionForward docHandler(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	ActionForward actionForw = super.docHandler(mapping, form, request, response);
	WorkAreaActionForm workAreaForm = (WorkAreaActionForm) form;
	WorkAreaMaintenanceDocument workAreaMaintenanceDocument = (WorkAreaMaintenanceDocument) workAreaForm.getDocument();
	WorkAreaService waService = TkServiceLocator.getWorkAreaService();
	TkRoleAssignService raService = TkServiceLocator.getRoleAssignmentService();
	String workAreaId_s = request.getParameter("workAreaId");
	try {
	    Long workAreaId = (workAreaId_s != null) ? Long.parseLong(workAreaId_s) : null;
	    WorkArea workArea = waService.getWorkArea(workAreaId);

	    if (workArea != null) {
		LOG.debug("Obtained work area: " + workArea.getWorkAreaId());
		Map<String, Set<String>> roleMap = raService.getTkRoleAssignmentsMap(workAreaId);
		workAreaMaintenanceDocument.setWorkArea(workArea);

		request.setAttribute("workArea", workArea);
		request.setAttribute("roleMap", roleMap);
	    } else {
		//TODO 
		// We need to have a general page that we can forward to that will hold
		// errors like this as well as security violations, since the user can 
		// just arbitrarily change the workAreaId parameter.
	    }
	} catch (NumberFormatException nfe) {
	    LOG.error("nfe", nfe);
	}

	return actionForw;
    }

}
