package org.kuali.kpme.core.bo.calendar.authorization;

import java.util.Map;

import org.kuali.kpme.core.authorization.KPMEMaintenanceDocumentAuthorizerBase;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;

@SuppressWarnings("deprecation")
public class CalendarAuthorizer extends KPMEMaintenanceDocumentAuthorizerBase {

	private static final long serialVersionUID = 8723300595366697672L;

	protected void addRoleQualification(Object dataObject, Map<String, String> attributes) {
		super.addRoleQualification(dataObject, attributes);

		attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), "%");
	}

}
