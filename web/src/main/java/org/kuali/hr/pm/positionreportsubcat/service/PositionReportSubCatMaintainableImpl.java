package org.kuali.hr.pm.positionreportsubcat.service;

import org.kuali.hr.core.HrBusinessObject;
import org.kuali.hr.pm.service.base.PmServiceLocator;
import org.kuali.hr.tklm.time.util.HrBusinessObjectMaintainableImpl;

public class PositionReportSubCatMaintainableImpl extends HrBusinessObjectMaintainableImpl {
	@Override
	public HrBusinessObject getObjectById(String id) {
		return PmServiceLocator.getPositionReportSubCatService().getPositionReportSubCatById(id);
	}

}
