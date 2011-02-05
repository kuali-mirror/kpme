package org.kuali.hr.location.service;

import org.kuali.hr.location.Location;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class LocationMaintainableServiceImpl extends KualiMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void saveBusinessObject() {
		Location location = (Location)this.getBusinessObject();	
		location.setHrLocationId(null);
		location.setTimestamp(null);
		KNSServiceLocator.getBusinessObjectService().save(location);
	}
}
