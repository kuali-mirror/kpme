package org.kuali.hr.time.workarea.web;

import java.sql.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.role.assign.TkRoleAssign;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.hr.time.workarea.WorkAreaMaintenanceDocument;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.rules.TransactionalDocumentRuleBase;
import org.kuali.rice.kns.util.GlobalVariables;

public class WorkAreaMaintenanceDocumentRule extends TransactionalDocumentRuleBase {

    private static Logger LOG = Logger.getLogger(WorkAreaMaintenanceDocumentRule.class);

    /**
     * TODO : If we already have a date, it is probably already valid, we need to look at how to add back the kuali 
     * form binding validation for the date type.
     * 
     * @param effectiveDate
     * @return
     */
    protected boolean validateEffectiveDate(Date effectiveDate) {
	boolean v = true;
	return v;
    }

    protected boolean validateDepartmentId(String deptId) {
	boolean v = true;

	return v;
    }

    protected boolean validateOvertimePreference(String ovtPref) {
	boolean v = true;

	// TODO: What is considered valid here?
	if (StringUtils.isBlank(ovtPref)) {
	    v = false;
	    addError("document.workArea.overtimePreference", "error.required", "overtime preference");
	}

	// KeyLabelPair does not implement equals, so we need to search over each key
	if (v) {
	    boolean found = false;
	    for (KeyLabelPair klp : WorkAreaOvertimePreferenceValuesFinder.labels) {
		found |= klp.getKey().equals(ovtPref);
	    }
	    if (!found) {
		v = false;
		addError("document.workArea.overtimePreference", "error.existence", "overtime preference");
	    }
	}

	return v;
    }

    protected boolean validateAdminDescription(String desc) {
	boolean v = true;
	
	if (v && StringUtils.isBlank(desc)) {
	    v = false;
	    addError("document.workArea.adminDescr", "error.required", "admin description");
	}

	
	return v;
    }

    protected boolean validateDescription(String desc) {
	boolean v = true;

	if (v && StringUtils.isBlank(desc)) {
	    v = false;
	    addError("document.workArea.description", "error.required", "description");
	}

	return v;
    }

    protected boolean validateRoleAssignments(List<TkRoleAssign> list) {
	boolean v = true;
	
	// we validate on each addition, though we may want to do more checks here.
	// could be expensive - we may want to use Set instead of list, however rice
	// supports easier index based deletions if we use list
	
	return v;
    }

    protected boolean validateTasks(List<Task> list) {
	boolean v = true;
	
	for (int i=0; i<list.size(); i++) {
	    Task task = list.get(i);
	    v &= validateTaskGeneric("document.workArea.tasks["+i+"]", task);
	}
	
	return v;
    }

    public boolean validate(WorkAreaMaintenanceDocument wamd) {
	boolean valid = false;

	WorkArea wa = (wamd != null) ? wamd.getWorkArea() : null;
	if (wa != null) {
	    valid = true;
	    valid &= this.validateDepartmentId(wa.getDeptId());
	    valid &= this.validateEffectiveDate(wa.getEffectiveDate());
	    valid &= this.validateOvertimePreference(wa.getOvertimePreference());
	    valid &= this.validateAdminDescription(wa.getAdminDescr());
	    valid &= this.validateDescription(wa.getDescription());
	    valid &= this.validateRoleAssignments(wa.getRoleAssignments());
	    valid &= this.validateTasks(wa.getTasks());
	}

	return valid;
    }
    
    public boolean validateTaskAddition(Task task, List<Task> list) {
	return validateTaskGeneric("newTask", task);
    }
    
    public boolean validateTaskGeneric(String errorPrefix, Task task) {
	boolean v = true;

	if (task == null) {
	    v = false;
	    addError(errorPrefix, "error.required", "task");
	}
	
	if (v && StringUtils.isBlank(task.getDescription())) {
	    v = false;
	    addError(errorPrefix+".description", "error.required", "description");
	}
	
	if (v && StringUtils.isBlank(task.getAdministrativeDescription())) {
	    v = false;
	    addError(errorPrefix+".adminDescription", "error.required", "admin description");
	}
	
	if (v && task.getEffectiveDate() == null) {
	    v = false;
	    addError(errorPrefix+".effectiveDate", "error.required", "effective date");
	}
	
	if (v && task.getEffectiveDate() != null) {
	    Date effectiveDate = task.getEffectiveDate();
	    // TODO: Not sure what to do with this to validate it...
	}
	
	return v;
    }

    public boolean validateRoleAddition(TkRoleAssign tra, List<TkRoleAssign> list) {
	boolean v = true;

	// Verify that we even have an object to look at.
	if (tra == null) {
	    v = false;
	    addError("newRoleAssignment.principalId", "error.required", "principal id");
	}

	// 0 byte principal ID check
	if (v && StringUtils.isBlank(tra.getPrincipalId())) {
	    addError("newRoleAssignment.principalId", "error.required", "principal id");
	    v = false;
	}

	// Validate that principal ID maps to a person.
	if (v && (KIMServiceLocator.getIdentityService().getPrincipal(tra.getPrincipalId()) == null)) {
	    addError("newRoleAssignment.principalId", "error.existence", "user");
	    v = false;
	}

	if (v && list.contains(tra)) {
	    addError("newRoleAssignment.principalId", "error.duplicate.entry", "role");
	    v = false;
	}

	return v;
    }
    
    @Override
    protected boolean processCustomSaveDocumentBusinessRules(Document document) {
	LOG.debug("Validation called from rice");
	WorkAreaMaintenanceDocument wamd;
	boolean v = true;
	
	if (!(document instanceof WorkAreaMaintenanceDocument))
	    v = false;
	
	wamd = (WorkAreaMaintenanceDocument)document;
	v = this.validate(wamd);
	
	return v;
    }

    private void addError(String formId, String errorCode, String... params) {
	GlobalVariables.getMessageMap().putError(formId, errorCode, params);
    }
}
