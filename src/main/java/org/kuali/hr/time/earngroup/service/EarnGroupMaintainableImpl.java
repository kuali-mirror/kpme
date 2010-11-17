package org.kuali.hr.time.earngroup.service;

import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class EarnGroupMaintainableImpl extends KualiMaintainableImpl{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public void saveBusinessObject() {
		EarnGroup earnGroup = (EarnGroup)this.getBusinessObject();
		EarnGroup oldEarnGroup = (EarnGroup)KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(
				EarnGroup.class, earnGroup.getTkEarnGroupId());
		if(oldEarnGroup!=null){
			oldEarnGroup.setActive(false);
			KNSServiceLocator.getBusinessObjectService().save(oldEarnGroup);
		}
		earnGroup.setTkEarnGroupId(null);
		earnGroup.setTimestamp(null);
		KNSServiceLocator.getBusinessObjectService().save(earnGroup);
	}
	
}
