package org.kuali.hr.time.salgroup.service;

import org.kuali.hr.time.salgroup.SalGroup;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class SalGroupMaintainableImpl extends KualiMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void saveBusinessObject() {
		SalGroup salGroup = (SalGroup)this.getBusinessObject();
		salGroup.setTkSalGroupId(null);
		salGroup.setTimestamp(null);
		KNSServiceLocator.getBusinessObjectService().save(salGroup);
	}

}
