package org.kuali.hr.time.workarea.web;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.role.assign.TkRoleAssign;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.hr.time.workarea.WorkAreaMaintenanceDocument;
import org.kuali.hr.time.workarea.service.WorkAreaService;
import org.kuali.rice.core.util.RiceConstants;
import org.kuali.rice.kns.web.struts.action.KualiTransactionalDocumentActionBase;

import uk.ltd.getahead.dwr.util.Logger;

public class WorkAreaAction extends KualiTransactionalDocumentActionBase {

    private static final Logger LOG = Logger.getLogger(WorkAreaAction.class);
    private WorkAreaMaintenanceDocumentRule rule = new WorkAreaMaintenanceDocumentRule();

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
	String workAreaId_s = request.getParameter("workAreaId");
	try {
	    Long workAreaId = (workAreaId_s != null) ? Long.parseLong(workAreaId_s) : null;
	    WorkArea workArea = waService.getWorkArea(workAreaId);

	    if (workArea != null) {
		LOG.debug("Obtained work area: " + workArea.getWorkAreaId());
		workAreaMaintenanceDocument.setWorkArea(workArea);
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

    @Override
    public ActionForward route(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	ActionForward afw;
	WorkAreaService waService = TkServiceLocator.getWorkAreaService();
	WorkAreaActionForm workAreaForm = (WorkAreaActionForm) form;
	WorkAreaMaintenanceDocument wamd = (WorkAreaMaintenanceDocument) workAreaForm.getDocument();
	
	if (rule.validate(wamd)) {
	    waService.saveOrUpdate(wamd.getWorkArea());
	    afw = super.route(mapping, form, request, response);
	} else {
	    afw = mapping.findForward(RiceConstants.MAPPING_BASIC);
	}
	
	return afw;
    }

    public ActionForward addPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	WorkAreaActionForm workAreaForm = (WorkAreaActionForm) form;
	WorkAreaMaintenanceDocument document = (WorkAreaMaintenanceDocument) workAreaForm.getDocument();

	TkRoleAssign tra = workAreaForm.getNewRoleAssignment();
	if (rule.validateRoleAddition(tra, document.getWorkArea().getRoleAssignments())) {
	    LOG.info("Adding role: " + tra.getRoleName() + " to principal " + tra.getPrincipalId());
	    document.getWorkArea().getRoleAssignments().add(tra);
	    workAreaForm.setNewRoleAssignment(new TkRoleAssign());
	}

	return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }

    public ActionForward removePerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	WorkAreaActionForm workAreaForm = (WorkAreaActionForm) form;
	WorkAreaMaintenanceDocument document = (WorkAreaMaintenanceDocument) workAreaForm.getDocument();
	int deleteMe = this.getSelectedLine(request);
	WorkArea workArea = document.getWorkArea();
	TkRoleAssign tra = workArea.getRoleAssignments().remove(deleteMe);
	LOG.info("removed " + tra.getPrincipalId() + " from " + tra.getRoleName());
	workAreaForm.setNewRoleAssignment(new TkRoleAssign());

	return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }
}
