package org.kuali.hr.time.earncode.service;

import java.sql.Timestamp;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentAccount;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
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
		
		//Inactivate the old earn code as of the effective date of new earn code
		if(earnCode.getTkEarnCodeId()!=null && earnCode.isActive()){
			EarnCode oldEarnCode = TkServiceLocator.getEarnCodeService().getEarnCodeById(earnCode.getTkEarnCodeId());
			if(earnCode.getEffectiveDate().equals(oldEarnCode.getEffectiveDate())){
				earnCode.setTimestamp(null);
			} else{
				if(oldEarnCode!=null){
					oldEarnCode.setActive(false);
					//NOTE this is done to prevent the timestamp of the inactive one to be greater than the 
					oldEarnCode.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(System.currentTimeMillis())));
					oldEarnCode.setEffectiveDate(earnCode.getEffectiveDate());
					KNSServiceLocator.getBusinessObjectService().save(oldEarnCode);
				}
				earnCode.setTimestamp(new Timestamp(System.currentTimeMillis()));
				earnCode.setTkEarnCodeId(null);
			}
		}
		
		KNSServiceLocator.getBusinessObjectService().save(earnCode);
	}
	
}
