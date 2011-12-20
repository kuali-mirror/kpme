package org.kuali.hr.lm.leavedonation.service;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;

public class LeaveDonationMaintainableServiceImpl extends HrBusinessObjectMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public HrBusinessObject getObjectById(Long id) {
		return TkServiceLocator.getLeaveDonationService().getLeaveDonation(id);
	}
}
