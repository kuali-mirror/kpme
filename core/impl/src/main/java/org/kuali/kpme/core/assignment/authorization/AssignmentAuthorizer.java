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
package org.kuali.kpme.core.assignment.authorization;

import org.kuali.kpme.core.assignment.AssignmentBo;
import org.kuali.kpme.core.authorization.KPMEMaintenanceDocumentViewAuthorizer;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import java.util.Map;

public class AssignmentAuthorizer extends KPMEMaintenanceDocumentViewAuthorizer {

	private static final long serialVersionUID = 1962036374728477204L;

	protected void addRoleQualification(Object dataObject, Map<String, String> attributes) {
		super.addRoleQualification(dataObject, attributes);
		
		// put in the department qualifier for the role membership attributes
		if (dataObject instanceof AssignmentBo && dataObject != null) {
			attributes.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), ((AssignmentBo) dataObject).getDept());
			attributes.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), ((AssignmentBo) dataObject).getWorkArea().toString());
		}		
		
	}

}