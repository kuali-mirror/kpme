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
package org.kuali.kpme.tklm.time.rules.timecollection.authorization;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.authorization.KPMEMaintenanceDocumentAuthorizerBase;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule;

import java.util.Map;

@SuppressWarnings("deprecation")
public class TimeCollectionRuleAuthorizer extends KPMEMaintenanceDocumentAuthorizerBase {

	private static final long serialVersionUID = 4244084290191633140L;

	protected void addRoleQualification(Object dataObject, Map<String, String> attributes) {
		super.addRoleQualification(dataObject, attributes);

		String department = StringUtils.EMPTY;
		String location = StringUtils.EMPTY;
		String groupKeyCode = StringUtils.EMPTY;
		
		if (dataObject instanceof TimeCollectionRule) {
			TimeCollectionRule timeCollectionRuleObj = (TimeCollectionRule) dataObject;
			
			if (timeCollectionRuleObj != null) {
				department = timeCollectionRuleObj.getDept(); 
				groupKeyCode = timeCollectionRuleObj.getGroupKeyCode();
				
				Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, groupKeyCode, timeCollectionRuleObj.getEffectiveLocalDate());
			
				if (departmentObj != null) {
					location = departmentObj.getGroupKey().getLocationId();
				}
			}
		}
		
		attributes.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
        attributes.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), groupKeyCode);
		attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
	}
	
}
