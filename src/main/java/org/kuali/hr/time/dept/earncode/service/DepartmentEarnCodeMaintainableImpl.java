package org.kuali.hr.time.dept.earncode.service;

import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class DepartmentEarnCodeMaintainableImpl extends KualiMaintainableImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void saveBusinessObject() {
		DepartmentEarnCode departmentEarnCode = (DepartmentEarnCode) this.getBusinessObject();
		DepartmentEarnCode oldDepartmentEarnCode = (DepartmentEarnCode) KNSServiceLocator
				.getBusinessObjectService().findBySinglePrimaryKey(
						DepartmentEarnCode.class,
						departmentEarnCode.getTkDeptEarnCodeId());
		if (oldDepartmentEarnCode != null) {
			oldDepartmentEarnCode.setActive(false);
			KNSServiceLocator.getBusinessObjectService().save(oldDepartmentEarnCode);
		}
		departmentEarnCode.setTkDeptEarnCodeId(null);
		departmentEarnCode.setTimestamp(null);
		KNSServiceLocator.getBusinessObjectService().save(departmentEarnCode);
	}
}
