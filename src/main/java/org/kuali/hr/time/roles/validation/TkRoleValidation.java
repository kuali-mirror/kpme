package org.kuali.hr.time.roles.validation;

import org.codehaus.plexus.util.StringUtils;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.roles.TkRoleGroup;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

import java.sql.Date;

public class TkRoleValidation extends MaintenanceDocumentRuleBase{

    private static final String ADD_LINE_LOCATION = "add.roles.";

    boolean validateTkRole(TkRole role, String fieldPrefix) {
        boolean valid = true;

        if (fieldPrefix == null)
            fieldPrefix = "";

        Date asOfDate = role.getEffectiveDate();
        String rname = role.getRoleName();
        if (StringUtils.equalsIgnoreCase(rname, TkConstants.ROLE_TK_APPROVER)) {
            // Only Work Area required
            boolean vwa = ValidationUtils.validateWorkArea(role.getWorkArea(), asOfDate);
            if (!vwa) {
                this.putFieldError(fieldPrefix + "workArea", "workarea.notfound");
            }
            if (role.getDepartment() != null)
                this.putFieldError(fieldPrefix + "department", "field.unused");
            valid &= vwa;
        } else if (StringUtils.equalsIgnoreCase(rname, TkConstants.ROLE_TK_ORG_ADMIN)) {
            // Only Department required
            boolean vwa = ValidationUtils.validateDepartment(role.getDepartment(), asOfDate);
            if (!vwa) {
                this.putFieldError(fieldPrefix + "department", "dept.notfound");
            }
            if (role.getWorkArea() != null)
                this.putFieldError(fieldPrefix + "workArea", "field.unused");
            valid &= vwa;
        } else if (StringUtils.equalsIgnoreCase(rname, TkConstants.ROLE_TK_SYS_ADMIN)) {
            // no department or work area required, error if provided?
            if (role.getDepartment() != null) {
                this.putFieldError(fieldPrefix + "department", "field.unused");
                valid = false;
            }
            if (role.getWorkArea() != null) {
                this.putFieldError(fieldPrefix + "workArea", "field.unused");
                valid = false;
            }
        } else if (StringUtils.equalsIgnoreCase(rname, TkConstants.ROLE_TK_GLOBAL_VO)) {
            // nothing required
            if (role.getDepartment() != null) {
                this.putFieldError(fieldPrefix + "department", "field.unused");
                valid = false;
            }
            if (role.getWorkArea() != null) {
                this.putFieldError(fieldPrefix + "workArea", "field.unused");
                valid = false;
            }
        } else if (StringUtils.equalsIgnoreCase(rname, TkConstants.ROLE_TK_DEPT_VO)) {
            // either department OR work area required
            boolean vwa = ValidationUtils.validateWorkArea(role.getWorkArea(), asOfDate);
            boolean vdp = ValidationUtils.validateDepartment(role.getDepartment(), asOfDate);
            if ((vwa || vdp)) {
                // may want to check for presence of fault
                if (!vwa && role.getWorkArea() != null) {
                    this.putFieldError(fieldPrefix + "workArea", "workarea.notfound");
                }
                if (!vdp && role.getDepartment() != null) {
                    this.putFieldError(fieldPrefix + "department", "dept.notfound");
                }
            } else {
                this.putFieldError(fieldPrefix + "workArea",   "workarea.dept.and.or");
                this.putFieldError(fieldPrefix + "department", "workarea.dept.and.or");
            }
            valid = vwa || vdp;
        } else if (StringUtils.equalsIgnoreCase(rname, TkConstants.ROLE_TK_REVIEWER)) {
            // work area required
            boolean vwa = ValidationUtils.validateWorkArea(role.getWorkArea(), asOfDate);
            if (!vwa) {
                this.putFieldError(fieldPrefix + "workArea", "workarea.notfound");
            }
            if (role.getDepartment() != null)
                this.putFieldError(fieldPrefix + "department", "field.unused");
            valid &= vwa;
        } else {
            // ? - Unexpected condition, do nothing.
        }

        return valid;
    }

    /**
     * Currently, the prefix will not work when the attributes of the collection
     * items are set to readOnlyAfterAdd. This seems to be a built in function
     * of rice. Errors will still appear but no marker will be placed next to
     * the field.
     */
    boolean validateTkRoleGroup(TkRoleGroup roleGroup) {
        boolean valid = true;

        int pos = 0;
        for (TkRole role: roleGroup.getRoles()) {
            StringBuffer prefix = new StringBuffer("roles[");
            prefix.append(pos).append("].");
            validateTkRole(role, prefix.toString());
            pos++;
        }

        return valid;
    }

	@Override
	protected boolean processCustomSaveDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = true;

		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof TkRole) {
			TkRole role = (TkRole)pbo;
            valid = validateTkRole(role, null);
		} else if (pbo instanceof TkRoleGroup) {
            TkRoleGroup roleGroup = (TkRoleGroup)pbo;
            valid = validateTkRoleGroup(roleGroup);
        }

		return valid;
	}

    @Override
    /**
     * Validation for the 'add' role in the collection manipulator.
     */
    public boolean processCustomAddCollectionLineBusinessRules(MaintenanceDocument document, String collectionName, PersistableBusinessObject line) {
        boolean valid = true;

        if (line instanceof TkRole) {
            TkRole role = (TkRole)line;
            valid = validateTkRole(role, ADD_LINE_LOCATION);
        }

        return valid;
    }
}
