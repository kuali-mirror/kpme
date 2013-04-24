package org.kuali.hr.pm.pstnrptgrpsubcat.service;

import org.kuali.hr.core.HrBusinessObject;
import org.kuali.hr.pm.service.base.PmServiceLocator;
import org.kuali.hr.tklm.time.util.HrBusinessObjectMaintainableImpl;

public class PstnRptGrpSubCatMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	private static final long serialVersionUID = 1L;

	@Override
	public HrBusinessObject getObjectById(String id) {
		return PmServiceLocator.getPstnRptGrpSubCatService().getPstnRptGrpSubCatById(id);
	}

}
