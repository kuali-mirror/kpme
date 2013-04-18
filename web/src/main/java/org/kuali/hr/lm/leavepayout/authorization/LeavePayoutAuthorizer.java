package org.kuali.hr.lm.leavepayout.authorization;

import java.util.Map;

import org.kuali.hr.core.authorization.KPMEMaintenanceDocumentAuthorizerBase;
import org.kuali.hr.core.role.KPMERoleMemberAttribute;

@SuppressWarnings("deprecation")
public class LeavePayoutAuthorizer extends KPMEMaintenanceDocumentAuthorizerBase {

	private static final long serialVersionUID = 8233452898311917126L;

	protected void addRoleQualification(Object dataObject, Map<String, String> attributes) {
		super.addRoleQualification(dataObject, attributes);

		attributes.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), "%");
		attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), "%");
	}
	
}