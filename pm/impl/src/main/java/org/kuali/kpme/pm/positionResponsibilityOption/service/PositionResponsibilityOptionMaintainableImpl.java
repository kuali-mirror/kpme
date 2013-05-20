package org.kuali.kpme.pm.positionResponsibilityOption.service;

import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;

public class PositionResponsibilityOptionMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8301277353231917991L;

	@Override
	public HrBusinessObject getObjectById(String id) {
		return PmServiceLocator.getPositionResponsibilityOptionService().getPositionResponsibilityOptionById(id);
	}

}

