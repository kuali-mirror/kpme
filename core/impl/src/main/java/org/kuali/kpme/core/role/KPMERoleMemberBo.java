/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.core.role;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.service.HrServiceLocator;
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
		if (StringUtils.isNotBlank(roleName)) {
			setRoleId(HrServiceLocator.getHRRoleService().getRoleIdByName(roleName));
		}
	}

}