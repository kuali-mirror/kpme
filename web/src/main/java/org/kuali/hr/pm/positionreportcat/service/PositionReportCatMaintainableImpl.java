package org.kuali.hr.pm.positionreportcat.service;

import org.kuali.hr.core.bo.HrBusinessObject;
import org.kuali.hr.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.hr.pm.service.base.PmServiceLocator;

public class PositionReportCatMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	@Override
	public HrBusinessObject getObjectById(String id) {
		return PmServiceLocator.getPositionReportCatService().getPositionReportCatById(id);
	}

}
