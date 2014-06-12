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
package org.kuali.kpme.tklm.leave.transfer.authorization;

import java.util.Map;

import org.kuali.kpme.core.authorization.KPMEMaintenanceDocumentAuthorizerBase;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.document.Document;

@SuppressWarnings("deprecation")
public class BalanceTransferAuthorizer extends KPMEMaintenanceDocumentAuthorizerBase {

	private static final long serialVersionUID = 6208655211538912184L;

	protected void addRoleQualification(Object dataObject, Map<String, String> attributes) {
		super.addRoleQualification(dataObject, attributes);

		attributes.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), "%");
        attributes.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), "%");
		attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), "%");
	}

	@Override
	public boolean canEdit(Document document, Person user) {
		if(document.getDocumentHeader().hasWorkflowDocument()) {
			return(document.getDocumentHeader().getWorkflowDocument().getStatus().equals(DocumentStatus.INITIATED));
		}
		return false;
	}
	
}
