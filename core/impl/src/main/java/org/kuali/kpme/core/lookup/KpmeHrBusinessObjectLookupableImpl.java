package org.kuali.kpme.core.lookup;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.service.LookupService;

public class KpmeHrBusinessObjectLookupableImpl extends KPMELookupableImpl {

	private static final long serialVersionUID = -6069864640904085642L;
	
	private static final String KPME_HR_BUSINESS_OBJECT_LOOKUP_SERVICE = "kpmeHrBusinessObjectLookupService";
	
	
	// this constructor sets the lookup service that is wired with the 
    // hrBusinessObject lookup DAO
	public KpmeHrBusinessObjectLookupableImpl() {
		this.setLookupService((LookupService) KRADServiceLocatorWeb.getService(KPME_HR_BUSINESS_OBJECT_LOOKUP_SERVICE));
	}
	
	
	 // KPME-2699 editing inactive records is only allowed for admins
    @Override
    public boolean allowsMaintenanceEditAction(Object dataObject) {
        boolean allowsEdit = super.allowsMaintenanceEditAction(dataObject);
        HrBusinessObjectContract bo = (HrBusinessObjectContract) dataObject;
        if ( (!bo.isActive()) && (!HrContext.canEditInactiveRecords()) ) {
        	allowsEdit = false;
        }
        return allowsEdit;
    }
    
}