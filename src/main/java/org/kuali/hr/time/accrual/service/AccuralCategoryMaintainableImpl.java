package org.kuali.hr.time.accrual.service;

import org.kuali.hr.time.accrual.AccrualCategory;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class AccuralCategoryMaintainableImpl extends KualiMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void saveBusinessObject() {
		AccrualCategory accrualCategory = (AccrualCategory) this.getBusinessObject();
		AccrualCategory oldAccrualCategory = (AccrualCategory) KNSServiceLocator
				.getBusinessObjectService().findBySinglePrimaryKey(
						AccrualCategory.class,
						accrualCategory.getLaAccrualCategoryId());
		if (oldAccrualCategory != null) {
			oldAccrualCategory.setActive(false);
			KNSServiceLocator.getBusinessObjectService().save(oldAccrualCategory);
		}
		accrualCategory.setTimestamp(null);
		accrualCategory.setLaAccrualCategoryId(null);
		
		KNSServiceLocator.getBusinessObjectService().save(accrualCategory);
	}

}
