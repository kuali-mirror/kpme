package org.kuali.hr.time.workarea.web;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

import java.sql.Date;
import java.util.List;

public class WorkAreaMaintenanceDocumentRule extends MaintenanceDocumentRuleBase {

	private static Logger LOG = Logger.getLogger(WorkAreaMaintenanceDocumentRule.class);

    boolean validateDepartment(String dept, Date asOfDate) {
        boolean valid = ValidationUtils.validateDepartment(dept, asOfDate);
        if (!valid) {
            this.putFieldError("dept", "dept.notfound");
        }
        return valid;
    }

    boolean validateRoles(List<TkRole> roles) {
        boolean valid = false;

        if (roles != null && roles.size() > 0) {
            for (TkRole role : roles) {
                valid |= role.isActive() && StringUtils.equals(role.getRoleName(), TkConstants.ROLE_TK_APPROVER);
            }
        }

        if (!valid) {
            this.putGlobalError("role.required");
        }

        return valid;
    }

    // TODO: Implement this method.
    boolean validateTasks(List<Task> tasks) {
        boolean valid = true;
        return valid;
    }

    boolean validateDefaultOTEarnCode(String earnCode, Date asOfDate) {
        boolean valid = ValidationUtils.validateEarnCode(earnCode, true, asOfDate);

        if (!valid) {
            this.putFieldError("defaultOvertimeEarnCode", "earncode.ovt.notfound", earnCode);
        }

        return valid;
    }

	@Override
	protected boolean processCustomSaveDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = false;

        PersistableBusinessObject pbo = this.getNewBo();
        if (pbo instanceof WorkArea) {
            WorkArea wa = (WorkArea) pbo;
            valid = validateDepartment(wa.getDept(), wa.getEffectiveDate());
            valid &= validateRoles(wa.getRoles());
            valid &= validateTasks(wa.getTasks());
            valid &= validateDefaultOTEarnCode(wa.getDefaultOvertimeEarnCode(), wa.getEffectiveDate());
        }

        return valid;
	}

}
