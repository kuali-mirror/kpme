package org.kuali.hr.time.workarea.web;

import javax.servlet.http.HttpServletRequest;

import org.kuali.hr.time.task.Task;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;

public class WorkAreaActionForm extends KualiTransactionalDocumentFormBase {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private Task newTask;
	private boolean newWorkArea = false;

	@Override
	public String getDefaultDocumentTypeName() {
		return "WorkAreaMaintenanceDocument";
	}

	@Override
	public String getDocTypeName() {
		return "WorkAreaMaintenanceDocument";
	}

	@Override
	public void populate(HttpServletRequest request) {
		super.populate(request);
	}

	public Task getNewTask() {
		return newTask;
	}

	public void setNewTask(Task newTask) {
		this.newTask = newTask;
	}

	public boolean isNewWorkArea() {
		return newWorkArea;
	}

	public void setNewWorkArea(boolean newWorkArea) {
		this.newWorkArea = newWorkArea;
	}
}
