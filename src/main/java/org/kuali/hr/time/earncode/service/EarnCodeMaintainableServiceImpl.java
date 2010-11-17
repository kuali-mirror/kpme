package org.kuali.hr.time.earncode.service;

import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class EarnCodeMaintainableServiceImpl extends KualiMaintainableImpl{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public void saveBusinessObject() {
		EarnCode earnCode = (EarnCode)this.getBusinessObject();
		EarnCode oldEarnCode = (EarnCode)KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(
				EarnCode.class, earnCode.getTkEarnCodeId());
		if(oldEarnCode!=null){
			oldEarnCode.setActive(false);
			KNSServiceLocator.getBusinessObjectService().save(oldEarnCode);
		}
		earnCode.setTkEarnCodeId(null);
		earnCode.setTimestamp(null);
		KNSServiceLocator.getBusinessObjectService().save(earnCode);
	}
	
}
