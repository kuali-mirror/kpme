package org.kuali.hr.pm.positionreportsubcat.service;

import org.kuali.hr.core.bo.HrBusinessObject;
import org.kuali.hr.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.hr.pm.service.base.PmServiceLocator;

public class PositionReportSubCatMaintainableImpl extends HrBusinessObjectMaintainableImpl {
	@Override
	public HrBusinessObject getObjectById(String id) {
		return PmServiceLocator.getPositionReportSubCatService().getPositionReportSubCatById(id);
	}

}
