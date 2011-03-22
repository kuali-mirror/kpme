package org.kuali.hr.time.accrual.service;

import org.kuali.hr.time.accrual.AccrualCategory;
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
		accrualCategory.setTimestamp(null);
		accrualCategory.setLaAccrualCategoryId(null);
		
		KNSServiceLocator.getBusinessObjectService().save(accrualCategory);
	}

}
