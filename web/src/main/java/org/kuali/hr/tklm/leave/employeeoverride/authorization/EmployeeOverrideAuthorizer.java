package org.kuali.hr.tklm.leave.employeeoverride.authorization;

import java.util.Map;

import org.kuali.hr.core.authorization.KPMEMaintenanceDocumentAuthorizerBase;
import org.kuali.hr.core.role.KPMERoleMemberAttribute;

@SuppressWarnings("deprecation")
public class EmployeeOverrideAuthorizer extends KPMEMaintenanceDocumentAuthorizerBase {

	private static final long serialVersionUID = -7286077333728587902L;

	protected void addRoleQualification(Object dataObject, Map<String, String> attributes) {
		super.addRoleQualification(dataObject, attributes);

		attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), "%");
	}
	
}