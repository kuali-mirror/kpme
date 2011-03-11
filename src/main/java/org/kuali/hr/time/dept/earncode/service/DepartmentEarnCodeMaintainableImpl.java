package org.kuali.hr.time.dept.earncode.service;

import java.util.Map;

import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KNSConstants;

public class DepartmentEarnCodeMaintainableImpl extends KualiMaintainableImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void saveBusinessObject() {
		DepartmentEarnCode departmentEarnCode = (DepartmentEarnCode) this.getBusinessObject();
//		DepartmentEarnCode oldDepartmentEarnCode = (DepartmentEarnCode) KNSServiceLocator
//				.getBusinessObjectService().findBySinglePrimaryKey(
//						DepartmentEarnCode.class,
//						departmentEarnCode.getTkDeptEarnCodeId());
//		if (oldDepartmentEarnCode != null) {
//			oldDepartmentEarnCode.setActive(false);
//			KNSServiceLocator.getBusinessObjectService().save(oldDepartmentEarnCode);
//		}
//		departmentEarnCode.setTkDeptEarnCodeId(null);
//        departmentEarnCode.setTimestamp(new Timestamp(TKUtils.getCurrentDate().getTime()));
		KNSServiceLocator.getBusinessObjectService().save(departmentEarnCode);
	}
	
	@Override
    public void processAfterEdit( MaintenanceDocument document, Map<String,String[]> parameters ) {
		DepartmentEarnCode departmentEarnCode = (DepartmentEarnCode) this.getBusinessObject();
		if(ValidationUtils.newerVersionExists(DepartmentEarnCode.class, "earnCode", departmentEarnCode.getEarnCode(), departmentEarnCode.getEffectiveDate())) {
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
}
