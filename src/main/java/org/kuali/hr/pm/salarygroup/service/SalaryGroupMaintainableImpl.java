package org.kuali.hr.pm.salarygroup.service;

import org.kuali.hr.pm.service.base.PmServiceLocator;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;

public class SalaryGroupMaintainableImpl  extends HrBusinessObjectMaintainableImpl {
	private static final long serialVersionUID = 1L;

	@Override
	public HrBusinessObject getObjectById(String id) {
		return PmServiceLocator.getSalaryGroupService().getSalaryGroupById(id);
	}

}
