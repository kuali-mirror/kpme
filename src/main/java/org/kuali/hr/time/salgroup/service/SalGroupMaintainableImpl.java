package org.kuali.hr.time.salgroup.service;

import java.sql.Timestamp;

import org.kuali.hr.time.salgroup.SalGroup;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
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
		if(salGroup.getTkSalGroupId()!=null && salGroup.isActive()){
			SalGroup oldSalGroup = TkServiceLocator.getSalGroupService().getSalGroup(salGroup.getTkSalGroupId());
			if(salGroup.getEffectiveDate().equals(oldSalGroup.getEffectiveDate())){
				salGroup.setTimestamp(null);
			} else{
				if(oldSalGroup!=null){
					oldSalGroup.setActive(false);
					//NOTE this is done to prevent the timestamp of the inactive one to be greater than the 
					oldSalGroup.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(System.currentTimeMillis())));
					oldSalGroup.setEffectiveDate(salGroup.getEffectiveDate());
					KNSServiceLocator.getBusinessObjectService().save(oldSalGroup);
				}
				salGroup.setTimestamp(new Timestamp(System.currentTimeMillis()));
				salGroup.setTkSalGroupId(null);
			}
		}
		KNSServiceLocator.getBusinessObjectService().save(salGroup);
	}

}
