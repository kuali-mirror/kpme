package org.kuali.kpme.core.bo.location.authorization;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.authorization.KPMEMaintenanceDocumentAuthorizerBase;
import org.kuali.kpme.core.bo.location.Location;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;

@SuppressWarnings("deprecation")
public class LocationAuthorizer extends KPMEMaintenanceDocumentAuthorizerBase {

	private static final long serialVersionUID = -2692906117361544599L;

	protected void addRoleQualification(Object dataObject, Map<String, String> attributes) {
		super.addRoleQualification(dataObject, attributes);

		String location = StringUtils.EMPTY;
		
		if (dataObject instanceof Location) {
			Location locationObj = (Location) dataObject;
			
			if (locationObj != null) {
				location = locationObj.getLocation();
			}
		}
		
		attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
	}

}