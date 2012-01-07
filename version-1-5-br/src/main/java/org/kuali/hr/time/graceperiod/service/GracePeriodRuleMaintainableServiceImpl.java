package org.kuali.hr.time.graceperiod.service;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;

public class GracePeriodRuleMaintainableServiceImpl extends
		HrBusinessObjectMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public HrBusinessObject getObjectById(String id) {
		return TkServiceLocator.getGracePeriodService().getGracePeriodRule(id);
	}

}
