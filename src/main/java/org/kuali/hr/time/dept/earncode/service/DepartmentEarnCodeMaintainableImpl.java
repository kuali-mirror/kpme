package org.kuali.hr.time.dept.earncode.service;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KNSConstants;

import java.util.Map;

public class DepartmentEarnCodeMaintainableImpl extends HrBusinessObjectMaintainableImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Override
    public void processAfterEdit( MaintenanceDocument document, Map<String,String[]> parameters ) {
		DepartmentEarnCode departmentEarnCode = (DepartmentEarnCode) this.getBusinessObject();
		int count = TkServiceLocator.getDepartmentEarnCodeService().getNewerDeptEarnCodeCount(departmentEarnCode.getEarnCode(), departmentEarnCode.getEffectiveDate());
		if(count > 0) {
			GlobalVariables.getMessageMap().putWarningWithoutFullErrorPath(
					KNSConstants.MAINTENANCE_NEW_MAINTAINABLE + "effectiveDate", 
					"deptEarncode.effectiveDate.newer.exists");
		}
		
		if(ValidationUtils.duplicateDeptEarnCodeExists(departmentEarnCode)) {
			GlobalVariables.getMessageMap().putWarningWithoutFullErrorPath(
					KNSConstants.MAINTENANCE_NEW_MAINTAINABLE + "dept", 
					"deptEarncode.duplicate.exists");
		}
    }

	@Override
	public HrBusinessObject getObjectById(String id) {
		return TkServiceLocator.getDepartmentEarnCodeService().getDepartmentEarnCode(id);
	}
}
