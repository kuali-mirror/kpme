package org.kuali.kpme.pm.positionreportcat.web;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.pm.service.base.PmServiceLocator;

public class PositionReportCatMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	@Override
	public HrBusinessObject getObjectById(String id) {
		return PmServiceLocator.getPositionReportCatService().getPositionReportCatById(id);
	}

}
