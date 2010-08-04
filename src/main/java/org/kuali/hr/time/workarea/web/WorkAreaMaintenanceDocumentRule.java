package org.kuali.hr.time.workarea.web;

import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.time.role.assign.TkRoleAssign;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.hr.time.workarea.WorkAreaMaintenanceDocument;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;

public class WorkAreaMaintenanceDocumentRule {

    private static Logger LOG = Logger.getLogger(WorkAreaMaintenanceDocumentRule.class);

    protected boolean validateDepartmentId(String deptId) {
	boolean v = true;
	return v;
    }

    protected boolean validateOvertimePreference() {
	boolean v = true;
	return v;
    }

    protected boolean validateAdminDescription() {
	boolean v = true;
	return v;
    }

    protected boolean validateDescription(String desc) {
	boolean v = true;

	if (v && (desc == null || desc.trim().length() == 0)) {
	    v = false;
	    addError("document.workArea.description", "error.required", "description");
	}

	return v;
    }

    protected boolean validateRoleAssignments() {
	boolean v = true;
	return v;
    }

    protected boolean validateTask() {
	boolean v = true;
	return v;
    }

    public boolean validate(WorkAreaMaintenanceDocument wamd) {
	boolean valid = false;

	LOG.debug("entering custom validation for WorkAreaMaintenenace");
	WorkArea wa = (wamd != null) ? wamd.getWorkArea() : null;
	if (wa != null) {
	    valid = true;
	    valid &= this.validateDepartmentId(wa.getDeptId());
	    valid &= this.validateOvertimePreference();
	    valid &= this.validateAdminDescription();
	    valid &= this.validateDescription(wa.getDescription());
	    valid &= this.validateRoleAssignments();
	    valid &= this.validateTask();
	}

	return valid;
    }

    public boolean validateRoleAddition(TkRoleAssign tra, List<TkRoleAssign> list) {
	boolean v = true;

	// Verify that we even have an object to look at.
	if (tra == null) {
	    v = false;
	    addError("newRoleAssignment.principalId", "error.required", "principal id");
	}
	
	// 0 byte principal ID check
	if (v && (tra.getPrincipalId() == null || tra.getPrincipalId().trim().length() == 0) ) {
	    addError("newRoleAssignment.principalId", "error.required", "principal id");
	    v = false;
	}

	// Validate that principal ID maps to a person.
	if (v && (KIMServiceLocator.getIdentityService().getPrincipal(tra.getPrincipalId()) == null) )  {
	    addError("newRoleAssignment.principalId", "error.existence", "user");
	    v = false;
	}

	if (v && list.contains(tra)) {
	    addError("newRoleAssignment.principalId", "error.duplicate.entry", "role");
	    v = false;
	}

	return v;
    }

    private void addError(String formId, String errorCode, String... params) {
	GlobalVariables.getMessageMap().putError(formId, errorCode, params);
    }
}
