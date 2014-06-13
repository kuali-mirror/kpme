/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.earncode.security.authorization;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.authorization.KPMEMaintenanceDocumentAuthorizerBase;
import org.kuali.kpme.core.earncode.security.EarnCodeSecurityBo;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;

@SuppressWarnings("deprecation")
public class EarnCodeSecurityAuthorizer extends KPMEMaintenanceDocumentAuthorizerBase {

	private static final long serialVersionUID = 7352157020861633853L;

	protected void addRoleQualification(Object dataObject, Map<String, String> attributes) {
		super.addRoleQualification(dataObject, attributes);

		String department = StringUtils.EMPTY;
		String location = StringUtils.EMPTY;
        String groupKeyCode = StringUtils.EMPTY;
		
		if (dataObject instanceof EarnCodeSecurityBo) {
			EarnCodeSecurityBo earnCodeSecurityObj = (EarnCodeSecurityBo) dataObject;
			
			if (earnCodeSecurityObj != null) {
				department = StringUtils.equals(earnCodeSecurityObj.getDept(), "%") ? StringUtils.EMPTY : earnCodeSecurityObj.getDept();
                groupKeyCode = StringUtils.equals(earnCodeSecurityObj.getGroupKeyCode(), "%") ? StringUtils.EMPTY : earnCodeSecurityObj.getGroupKeyCode();
				location = StringUtils.equals(earnCodeSecurityObj.getLocation(), "%") ? StringUtils.EMPTY : earnCodeSecurityObj.getLocation();
			}
		}

        attributes.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
        attributes.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), groupKeyCode);
		attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
	}
	
}