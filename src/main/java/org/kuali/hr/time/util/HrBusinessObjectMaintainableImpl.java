package org.kuali.hr.time.util;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

import java.sql.Timestamp;

public abstract class HrBusinessObjectMaintainableImpl extends KualiMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void saveBusinessObject() {
		HrBusinessObject hrObj = (HrBusinessObject) this.getBusinessObject();
		if(hrObj.getId()!=null){
			HrBusinessObject oldHrObj = this.getObjectById(hrObj.getId());
			if(oldHrObj!= null){
				//if the effective dates are the same do not create a new row just inactivate the old one
				if(hrObj.getEffectiveDate().equals(oldHrObj.getEffectiveDate())){
					oldHrObj.setActive(false);
					oldHrObj.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(TKUtils.getCurrentDate().getTime()))); 
				} else{
					//if effective dates not the same add a new row that inactivates the old entry based on the new effective date
					oldHrObj.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(TKUtils.getCurrentDate().getTime())));
					oldHrObj.setEffectiveDate(hrObj.getEffectiveDate());
					oldHrObj.setActive(false);
					oldHrObj.setId(null);
				}
				KNSServiceLocator.getBusinessObjectService().save(oldHrObj);
			}
		}
		hrObj.setTimestamp(new Timestamp(System.currentTimeMillis()));
		hrObj.setId(null);
		
		customSaveLogic(hrObj);
		KNSServiceLocator.getBusinessObjectService().save(hrObj);
	}
	
	public abstract HrBusinessObject getObjectById(String id);
	public void customSaveLogic(HrBusinessObject hrObj){};
}
