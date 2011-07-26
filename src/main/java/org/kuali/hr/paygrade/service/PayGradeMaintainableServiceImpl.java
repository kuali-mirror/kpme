package org.kuali.hr.paygrade.service;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;

public class PayGradeMaintainableServiceImpl extends HrBusinessObjectMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	public HrBusinessObject getObjectById(Long id) {
		return TkServiceLocator.getPayGradeService().getPayGrade(id);
	}
}
