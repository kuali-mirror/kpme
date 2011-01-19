package org.kuali.hr.time.roles.validation;

import java.sql.Date;

import org.codehaus.plexus.util.StringUtils;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class TkRoleValidation extends MaintenanceDocumentRuleBase{

	@Override
	protected boolean processCustomSaveDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = true;

		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof TkRole) {
			TkRole role = (TkRole)pbo;
			Date asOfDate = role.getEffectiveDate();
			
			String rname = role.getRoleName();
			if (StringUtils.equalsIgnoreCase(rname, TkConstants.ROLE_TK_APPROVER)) {
				// Only Work Area required
				boolean vwa = ValidationUtils.validateWorkArea(role.getWorkArea(), asOfDate);
				if (!vwa) {
					this.putFieldError("workArea", "workarea.notfound");
				}
				if (role.getDepartment() != null)
					this.putFieldError("department", "field.unused");				
				valid &= vwa; 
			} else if (StringUtils.equalsIgnoreCase(rname, TkConstants.ROLE_TK_ORG_ADMIN)) {
				// Only Department required
				boolean vwa = ValidationUtils.validateDepartment(role.getDepartment(), asOfDate);
				if (!vwa) {
					this.putFieldError("department", "dept.notfound");
				}
				if (role.getWorkArea() != null)
					this.putFieldError("workArea", "field.unused");				
				valid &= vwa;
			} else if (StringUtils.equalsIgnoreCase(rname, TkConstants.ROLE_TK_SYS_ADMIN)) {
				// no department or work area required, error if provided?
				if (role.getDepartment() != null)
					this.putFieldError("department", "field.unused");
				if (role.getWorkArea() != null)
					this.putFieldError("workArea", "field.unused");
			} else if (StringUtils.equalsIgnoreCase(rname, TkConstants.ROLE_TK_GLOBAL_VO)) {
				// nothing required
				if (role.getDepartment() != null)
					this.putFieldError("department", "field.unused");
				if (role.getWorkArea() != null)
					this.putFieldError("workArea", "field.unused");				
			} else if (StringUtils.equalsIgnoreCase(rname, TkConstants.ROLE_TK_DEPT_VO)) {
				// either department OR work area required
				boolean vwa = ValidationUtils.validateWorkArea(role.getWorkArea(), asOfDate);
				boolean vdp = ValidationUtils.validateDepartment(role.getDepartment(), asOfDate);
				if ((vwa || vdp)) {
					// may want to check for presence of fault
					if (!vwa && role.getWorkArea() != null) {
						this.putFieldError("workArea", "workarea.notfound");
					}
					if (!vdp && role.getDepartment() != null) {
						this.putFieldError("department", "dept.notfound");
					}
				} else {
					this.putFieldError("workArea",   "workarea.dept.and.or");
					this.putFieldError("department", "workarea.dept.and.or");
				}
				valid = vwa || vdp;
			} else if (StringUtils.equalsIgnoreCase(rname, TkConstants.ROLE_TK_REVIEWER)) {
				// work area required
				boolean vwa = ValidationUtils.validateWorkArea(role.getWorkArea(), asOfDate);
				if (!vwa) {
					this.putFieldError("workArea", "workarea.notfound");
				}
				if (role.getDepartment() != null)
					this.putFieldError("department", "field.unused");
				valid &= vwa; 				
			} else {
				// ? - Unexpected condition, do nothing.
			}
		}
		
		return valid;
	}

}
