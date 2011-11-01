package org.kuali.hr.lm.timeoff.service;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;

public class SystemScheduledTimeOffMaintainableServiceImpl extends HrBusinessObjectMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public HrBusinessObject getObjectById(Long id) {
		return TkServiceLocator.getSysSchTimeOffService().getSystemScheduledTimeOff(id);
	}
}
