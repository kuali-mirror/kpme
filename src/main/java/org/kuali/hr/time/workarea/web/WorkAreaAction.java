package org.kuali.hr.time.workarea.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.hr.time.workarea.WorkAreaMaintenanceDocument;
import org.kuali.hr.time.workarea.service.WorkAreaService;
import org.kuali.rice.core.util.RiceConstants;
import org.kuali.rice.kns.web.struts.action.KualiTransactionalDocumentActionBase;

public class WorkAreaAction extends KualiTransactionalDocumentActionBase {

	private static final Logger LOG = Logger.getLogger(WorkAreaAction.class);
	private WorkAreaMaintenanceDocumentRule rule = new WorkAreaMaintenanceDocumentRule();

//	@Override
//	public ActionForward docHandler(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		ActionForward actionForw = super.docHandler(mapping, form, request, response);
//
//		WorkAreaActionForm workAreaForm = (WorkAreaActionForm) form;
//		WorkAreaMaintenanceDocument workAreaMaintenanceDocument = (WorkAreaMaintenanceDocument) workAreaForm.getDocument();
//		WorkAreaService waService = TkServiceLocator.getWorkAreaService();
//		String workAreaId_s = request.getParameter("workAreaId");
//		if (request.getParameter("command").equals("initiate") && !Boolean.parseBoolean(request.getParameter("newWorkArea"))) {
//			try {
//				Long workAreaId = (workAreaId_s != null) ? Long.parseLong(workAreaId_s) : null;
//				WorkArea workArea = waService.getWorkArea(workAreaId, TKUtils.getCurrentDate());
//				// TODO : Implement this - Need Roles
//				
//				if (workArea != null) {
//					LOG.debug("Obtained work area: " + workArea.getTkWorkAreaId());
//					workAreaMaintenanceDocument.setWorkArea(workArea);
//				} else {
//					throw new RuntimeException("Work area was null.");
//				}
//			} catch (NumberFormatException nfe) {   
//				LOG.error("nfe", nfe);
//			}
//		} else{
//			workAreaForm.setNewWorkArea(true);
//		}
//
//		return actionForw;
//	}

	@Override
	public ActionForward route(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward afw;
		afw = super.route(mapping, form, request, response);
		// TODO : Implement this
		throw new RuntimeException("Implement this method.");
		//return afw;
	}

	public ActionForward addPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		WorkAreaActionForm workAreaForm = (WorkAreaActionForm) form;
		WorkAreaMaintenanceDocument document = (WorkAreaMaintenanceDocument) workAreaForm.getDocument();
		// TODO : Implement this
		throw new RuntimeException("Implement this method.");
		//return mapping.findForward(RiceConstants.MAPPING_BASIC);
	}

	public ActionForward removePerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		WorkAreaActionForm workAreaForm = (WorkAreaActionForm) form;
		WorkAreaMaintenanceDocument document = (WorkAreaMaintenanceDocument) workAreaForm.getDocument();
		// TODO : Implement this
		throw new RuntimeException("Implement this method.");
		//return mapping.findForward(RiceConstants.MAPPING_BASIC);
	}

	public ActionForward removeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		WorkAreaActionForm workAreaForm = (WorkAreaActionForm) form;
		WorkAreaMaintenanceDocument document = (WorkAreaMaintenanceDocument) workAreaForm.getDocument();
		int deleteMe = this.getSelectedLine(request);
		WorkArea workArea = document.getWorkArea();
		Task task = workArea.getTasks().remove(deleteMe);
		LOG.info("removed " + task.getTkTaskId());
		workAreaForm.setNewTask(new Task());

		return mapping.findForward(RiceConstants.MAPPING_BASIC);
	}

	/**
	 * Adds a task to our in-memory collection. Persistence is handled
	 * post-route. This method should add the 'user_principal_id' to the object
	 * as it is added. This could be the backdoored user.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addTask(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		WorkAreaActionForm workAreaForm = (WorkAreaActionForm) form;
		WorkAreaMaintenanceDocument document = (WorkAreaMaintenanceDocument) workAreaForm.getDocument();

		Task task = workAreaForm.getNewTask();
		if (rule.validateTaskAddition(task, document.getWorkArea().getTasks())) {
			LOG.info("Adding task: " + task.getDescription());
			TKUser user = TKContext.getUser();
			task.setUserPrincipalId(user.getPrincipalId());
			document.getWorkArea().getTasks().add(task);
			workAreaForm.setNewTask(new Task());
		}

		return mapping.findForward(RiceConstants.MAPPING_BASIC);
	}
}
