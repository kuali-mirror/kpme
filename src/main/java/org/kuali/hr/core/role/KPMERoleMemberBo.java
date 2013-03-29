package org.kuali.hr.core.role;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.role.RoleMemberBo;

@SuppressWarnings("unchecked")
public abstract class KPMERoleMemberBo extends RoleMemberBo {

	private static final long serialVersionUID = 3137509859347223332L;

	public Date getEffectiveDate() {
		Date effectiveDate = null;
		
		if (getActiveFromDate() != null) {
			effectiveDate = getActiveFromDate().toDate();
		}
		
		return effectiveDate;
	}
	
	public void setEffectiveDate(Date effectiveDate) {
		if (effectiveDate != null) {
			setActiveFromDateValue(new Timestamp(effectiveDate.getTime()));
		}
	}
	
	public Date getExpirationDate() {
		Date expirationDate = null;
		
		if (getActiveToDate() != null) {
			expirationDate = getActiveToDate().toDate();
		}
		
		return expirationDate;
	}
	
	public void setExpirationDate(Date expirationDate) {
		if (expirationDate != null) {
			setActiveToDateValue(new Timestamp(expirationDate.getTime()));
		}
	}
	
	public String getRoleName() {
		String roleName = StringUtils.EMPTY;
		
		if (getRoleId() != null) {
			Role role = KimApiServiceLocator.getRoleService().getRole(getRoleId());
			
			if (role != null) {
				roleName = role.getName();
			}
		}
		
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		setRoleId(TkServiceLocator.getHRRoleService().getRoleIdByName(roleName));
	}

}