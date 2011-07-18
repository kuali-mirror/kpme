package org.kuali.hr.time.accrual.service;

import java.sql.Timestamp;

import org.kuali.hr.time.accrual.AccrualCategory;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;
/**
 * Used to override the Maintenance page saving routine
 * 
 *
 */
public class AccuralCategoryMaintainableImpl extends KualiMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Save off a new object instead of mutating the existing object
	 */
	@Override
	public void saveBusinessObject() {
		AccrualCategory accrualCategory = (AccrualCategory) this.getBusinessObject();

		//Inactivate the old object as of the effective date of new object
		if(accrualCategory.getLaAccrualCategoryId()!=null && accrualCategory.isActive()){
			AccrualCategory oldAccrualCategory = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory.getLaAccrualCategoryId());
			if(accrualCategory.getEffectiveDate().equals(oldAccrualCategory.getEffectiveDate())){
				accrualCategory.setTimestamp(null);
			} else{
				if(oldAccrualCategory!=null){
					oldAccrualCategory.setActive(false);
					//NOTE this is done to prevent the timestamp of the inactive one to be greater than the 
					oldAccrualCategory.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(System.currentTimeMillis())));
					oldAccrualCategory.setEffectiveDate(accrualCategory.getEffectiveDate());
					KNSServiceLocator.getBusinessObjectService().save(oldAccrualCategory);
				}
			}
			accrualCategory.setTimestamp(new Timestamp(System.currentTimeMillis()));
			accrualCategory.setLaAccrualCategoryId(null);

			KNSServiceLocator.getBusinessObjectService().save(accrualCategory);
		}
	}
}
