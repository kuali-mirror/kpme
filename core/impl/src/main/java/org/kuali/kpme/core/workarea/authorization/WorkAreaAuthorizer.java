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
package org.kuali.kpme.core.workarea.authorization;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.authorization.KPMEMaintenanceDocumentAuthorizerBase;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.workarea.WorkAreaBo;

@SuppressWarnings("deprecation")
public class WorkAreaAuthorizer extends KPMEMaintenanceDocumentAuthorizerBase {

	private static final long serialVersionUID = -2555420929126094696L;

	protected void addRoleQualification(Object dataObject, Map<String, String> attributes) {
		super.addRoleQualification(dataObject, attributes);

		String department = StringUtils.EMPTY;
		String location = StringUtils.EMPTY;
		
		if (dataObject instanceof WorkAreaBo) {
			WorkAreaBo workAreaObj = (WorkAreaBo) dataObject;
			
			if (workAreaObj != null) {
				department = cleanAttributeValue(workAreaObj.getDept());
				// TODO:
				// Remove the line below and uncomment out the next line when group key code is added to work area
				String groupKeyCode = null;
				//String groupKeyCode = workAreaObj != null ? workAreaObj.getGroupKeyCode() : null;
				Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, groupKeyCode, workAreaObj.getEffectiveLocalDate());
			
				if (departmentObj != null) {
					location = cleanAttributeValue(departmentObj.getGroupKey().getLocationId());
				}
			}
		}
		
		attributes.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
		attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
	}
	
}