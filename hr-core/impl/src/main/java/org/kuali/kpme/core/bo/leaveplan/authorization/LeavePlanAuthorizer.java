package org.kuali.kpme.core.bo.leaveplan.authorization;

import java.util.Map;

import org.kuali.kpme.core.authorization.KPMEMaintenanceDocumentAuthorizerBase;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;

@SuppressWarnings("deprecation")
public class LeavePlanAuthorizer extends KPMEMaintenanceDocumentAuthorizerBase {

	private static final long serialVersionUID = 4448167130416201537L;

	protected void addRoleQualification(Object dataObject, Map<String, String> attributes) {
		super.addRoleQualification(dataObject, attributes);

		attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), "%");
	}
	
}