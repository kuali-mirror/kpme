package org.kuali.hr.time.workarea.web;

import javax.servlet.http.HttpServletRequest;

import org.kuali.hr.time.role.assign.TkRoleAssign;
import org.kuali.hr.time.task.Task;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;

public class WorkAreaActionForm extends KualiTransactionalDocumentFormBase {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private TkRoleAssign newRoleAssignment;
    private Task newTask;

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

    public TkRoleAssign getNewRoleAssignment() {
	return newRoleAssignment;
    }

    public void setNewRoleAssignment(TkRoleAssign newRoleAssignment) {
	this.newRoleAssignment = newRoleAssignment;
    }

    public Task getNewTask() {
	return newTask;
    }

    public void setNewTask(Task newTask) {
	this.newTask = newTask;
    }
}
