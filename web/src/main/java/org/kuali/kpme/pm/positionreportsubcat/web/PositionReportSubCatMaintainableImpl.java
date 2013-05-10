package org.kuali.kpme.pm.positionreportsubcat.web;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.pm.service.base.PmServiceLocator;

public class PositionReportSubCatMaintainableImpl extends HrBusinessObjectMaintainableImpl {
	@Override
	public HrBusinessObject getObjectById(String id) {
		return PmServiceLocator.getPositionReportSubCatService().getPositionReportSubCatById(id);
	}

}
