package org.kuali.kpme.core.lookup;

import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.service.LookupService;

public class KpmeHrGroupKeyedBusinessObjectLookupableImpl extends KpmeHrBusinessObjectLookupableImpl {

	private static final long serialVersionUID = -1720282920640239124L;
	
	private static final String KPME_GRP_KEYED_HR_BUSINESS_OBJECT_LOOKUP_SERVICE = "kpmeHrGroupKeyedBusinessObjectLookupService";
	
	// override to create the correct service type
	@Override
	protected LookupService createLookupServiceInstance() {
		return (LookupService) KRADServiceLocatorWeb.getService(KPME_GRP_KEYED_HR_BUSINESS_OBJECT_LOOKUP_SERVICE);
	}
}
