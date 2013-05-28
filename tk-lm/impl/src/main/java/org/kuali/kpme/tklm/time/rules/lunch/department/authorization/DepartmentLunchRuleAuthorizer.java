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
package org.kuali.kpme.tklm.time.rules.lunch.department.authorization;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.authorization.KPMEMaintenanceDocumentAuthorizerBase;
import org.kuali.kpme.core.department.Department;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.time.rules.lunch.department.DeptLunchRule;

@SuppressWarnings("deprecation")
public class DepartmentLunchRuleAuthorizer extends KPMEMaintenanceDocumentAuthorizerBase {

	private static final long serialVersionUID = -3734133844175107645L;

	protected void addRoleQualification(Object dataObject, Map<String, String> attributes) {
		super.addRoleQualification(dataObject, attributes);

		String department = StringUtils.EMPTY;
		String location = StringUtils.EMPTY;
		
		if (dataObject instanceof DeptLunchRule) {
			DeptLunchRule departmentLunchRuleObj = (DeptLunchRule) dataObject;
			
			if (departmentLunchRuleObj != null) {
				department = departmentLunchRuleObj.getDept();
				
				Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, departmentLunchRuleObj.getEffectiveLocalDate());
			
				if (departmentObj != null) {
					location = departmentObj.getLocation();
				}
			}
		}
		
		attributes.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
		attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
	}
	
}
