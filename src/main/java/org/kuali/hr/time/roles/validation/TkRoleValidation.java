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

    private boolean isWorkAreaAndDeptXor(TkRole role, String fieldPrefix) {
        boolean valid = true;
        boolean depte = !StringUtils.isEmpty(role.getDepartment());

        // Check for presence of strings first.
        if (!(depte ^ (role.getWorkArea() != null))) {
            this.putFieldError(fieldPrefix + "department", "dept.workarea.xor");
            this.putFieldError(fieldPrefix + "workArea", "dept.workarea.xor");
            valid = false;
        }

        if (valid) {
            boolean vdpt  = ValidationUtils.validateDepartment(role.getDepartment(), role.getEffectiveDate());
            boolean vwa   = ValidationUtils.validateWorkArea(role.getWorkArea(), role.getEffectiveDate());

            if (vdpt ^ vwa) {
                // Do nothing, this is the correct case.
            } else {
                // should only have ONE defined.
                this.putFieldError(fieldPrefix + "department", "dept.workarea.xor");
                this.putFieldError(fieldPrefix + "workArea", "dept.workarea.xor");
                valid = false;
            }
        }

        return valid;
    }

    private boolean isDeptAndChartXor(TkRole role, String fieldPrefix) {
        boolean valid = true;
        boolean depte = !StringUtils.isEmpty(role.getDepartment());
        boolean chrte = !StringUtils.isEmpty(role.getChart());

        if (!(depte ^ chrte)) {
            valid = false;
            this.putFieldError(fieldPrefix + "department", "dept.chart.xor");
            this.putFieldError(fieldPrefix + "workArea", "dept.chart.xor");
        }

        if (valid) {
            boolean vdpt = ValidationUtils.validateDepartment(role.getDepartment(), role.getEffectiveDate());
            boolean vc   = ValidationUtils.validateChart(role.getChart());

            if (vdpt ^ vc) {
                // Do nothing, this is the correct case.
            } else {
                // should only have ONE defined.
                this.putFieldError(fieldPrefix + "department", "dept.chart.xor");
                this.putFieldError(fieldPrefix + "chart", "dept.chart.xor");
                valid = false;
            }
        }

        return valid;
    }

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
        } else if (StringUtils.equalsIgnoreCase(rname, TkConstants.ROLE_TK_LOCATION_ADMIN)) {
            valid &= isDeptAndChartXor(role, fieldPrefix);
        } else if (StringUtils.equalsIgnoreCase(rname, TkConstants.ROLE_TK_DEPT_ADMIN)) {
            valid &= isDeptAndChartXor(role, fieldPrefix);
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
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
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
