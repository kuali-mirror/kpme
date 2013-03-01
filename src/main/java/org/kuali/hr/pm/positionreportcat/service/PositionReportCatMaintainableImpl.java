package org.kuali.hr.pm.positionreportcat.service;

import org.kuali.hr.pm.service.base.PmServiceLocator;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;

public class PositionReportCatMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	@Override
	public HrBusinessObject getObjectById(String id) {
		return PmServiceLocator.getPositionReportCatService().getPositionReportCatById(id);
	}

}
