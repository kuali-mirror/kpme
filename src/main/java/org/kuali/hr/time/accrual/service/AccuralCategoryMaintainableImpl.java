package org.kuali.hr.time.accrual.service;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
/**
 * Used to override the Maintenance page saving routine
 * 
 *
 */
public class AccuralCategoryMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public HrBusinessObject getObjectById(String id) {
		return TkServiceLocator.getAccrualCategoryService().getAccrualCategory(id);
	}
}
