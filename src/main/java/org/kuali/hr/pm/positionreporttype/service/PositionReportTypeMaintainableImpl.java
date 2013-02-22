package org.kuali.hr.pm.positionreporttype.service;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;

public class PositionReportTypeMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	@Override
	public HrBusinessObject getObjectById(String id) {
		// TODO Auto-generated method stub
		return TkServiceLocator.getPositionReportTypeService().getPositionReportTypeById(id);
	}

}
