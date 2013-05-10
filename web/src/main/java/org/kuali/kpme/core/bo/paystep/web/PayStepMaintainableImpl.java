package org.kuali.kpme.core.bo.paystep.web;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.pm.service.base.PmServiceLocator;

public class PayStepMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	private static final long serialVersionUID = 1L;

	@Override
	public HrBusinessObject getObjectById(String id) {
		return PmServiceLocator.getPayStepService().getPayStepById(id);
	}

}
