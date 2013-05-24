package org.kuali.kpme.pm.positionresponsibility.service;

import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;

public class PositionResponsibilityMaintainableImpl extends HrBusinessObjectMaintainableImpl {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8359836961276276615L;

	@Override
	public HrBusinessObject getObjectById(String id) {
		return PmServiceLocator.getPositionResponsibilityService().getPositionResponsibilityById(id);
	}


}
