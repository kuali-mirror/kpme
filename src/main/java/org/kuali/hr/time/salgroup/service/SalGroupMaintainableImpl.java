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
		SalGroup oldSalGroup = (SalGroup)KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(
				SalGroup.class, salGroup.getTkSalGroupId());
		if(oldSalGroup!=null){
			oldSalGroup.setActive(false);
			KNSServiceLocator.getBusinessObjectService().save(oldSalGroup);
		}
		salGroup.setTkSalGroupId(null);
		salGroup.setTimestamp(null);
		KNSServiceLocator.getBusinessObjectService().save(salGroup);
	}

}
