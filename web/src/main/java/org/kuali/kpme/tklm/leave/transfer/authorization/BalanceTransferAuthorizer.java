package org.kuali.kpme.tklm.leave.transfer.authorization;

import java.util.Map;

import org.kuali.kpme.core.authorization.KPMEMaintenanceDocumentAuthorizerBase;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;

@SuppressWarnings("deprecation")
public class BalanceTransferAuthorizer extends KPMEMaintenanceDocumentAuthorizerBase {

	private static final long serialVersionUID = 6208655211538912184L;

	protected void addRoleQualification(Object dataObject, Map<String, String> attributes) {
		super.addRoleQualification(dataObject, attributes);

		attributes.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), "%");
		attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), "%");
	}
	
}