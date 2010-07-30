package org.kuali.hr.time.workarea.web;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.role.assign.service.TkRoleAssignService;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.hr.time.workarea.WorkAreaMaintenanceDocument;
import org.kuali.hr.time.workarea.service.WorkAreaService;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;

public class WorkAreaActionForm extends KualiTransactionalDocumentFormBase {

    private static final Logger LOG = Logger.getLogger(WorkAreaActionForm.class);
    private WorkAreaMaintenanceDocument workAreaMaintenanceDocument;

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
	LOG.debug("Call to reset.");
	super.reset(mapping, request);
    }

    /**
     * Grab our WorkArea and all the roles that currently exist along with the users
     */
    @Override
    public void populate(HttpServletRequest request) {
	super.populate(request);
	Map mofo = this.getDocumentActions();
	
	WorkAreaService waService = TkServiceLocator.getWorkAreaService();
	TkRoleAssignService raService = TkServiceLocator.getRoleAssignmentService();
	String workAreaId_s = request.getParameter("workAreaId");
	try {
	    Long workAreaId = (workAreaId_s != null) ? Long.parseLong(workAreaId_s) : null;
	    WorkArea workArea = waService.getWorkArea(workAreaId);
	    
	    if (workArea != null) {
		LOG.debug("Obtained work area: " + workArea.getWorkAreaId());
		Map<String, Set<String>> roleMap = raService.getTkRoleAssignmentsMap(workAreaId);
		
		workAreaMaintenanceDocument = new WorkAreaMaintenanceDocument();
		workAreaMaintenanceDocument.setWorkArea(workArea);
		
		request.setAttribute("workArea", workArea);
		request.setAttribute("roleMap", roleMap);
	    } else {
		// We need to have a general page that we can forward to that will hold
		// errors like this as well as security violations, since the user can 
		// just arbitrarily change the workAreaId parameter.
	    }
	} catch (NumberFormatException nfe) {
	    LOG.error("nfe", nfe);
	}
    }

    public WorkAreaMaintenanceDocument getWorkAreaMaintenanceDocument() {
        return workAreaMaintenanceDocument;
    }

    public void setWorkAreaMaintenanceDocument(WorkAreaMaintenanceDocument workAreaMaintenanceDocument) {
        this.workAreaMaintenanceDocument = workAreaMaintenanceDocument;
    }

	@Override
	public Document getDocument() {
		return workAreaMaintenanceDocument;
	}
	
	@Override
	public String getDefaultDocumentTypeName(){
		return "WorkAreaMaintenanceDocument";
	}
	
}
