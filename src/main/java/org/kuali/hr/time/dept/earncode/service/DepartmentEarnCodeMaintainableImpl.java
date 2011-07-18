package org.kuali.hr.time.dept.earncode.service;

import java.sql.Timestamp;
import java.util.Map;

import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
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
		
		//Inactivate the old dept earn code as of the effective date of new dept earn code
		if(departmentEarnCode.getTkDeptEarnCodeId()!=null && departmentEarnCode.isActive()){
			DepartmentEarnCode oldDeptEarnCode = TkServiceLocator.getDepartmentEarnCodeService().getDepartmentEarnCode(departmentEarnCode.getTkDeptEarnCodeId());
			if(departmentEarnCode.getEffectiveDate().equals(oldDeptEarnCode.getEffectiveDate())){
				departmentEarnCode.setTimestamp(null);
			} else{
				if(oldDeptEarnCode!=null){
					oldDeptEarnCode.setActive(false);
					//NOTE this is done to prevent the timestamp of the inactive one to be greater than the 
					oldDeptEarnCode.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(System.currentTimeMillis())));
					oldDeptEarnCode.setEffectiveDate(departmentEarnCode.getEffectiveDate());
					KNSServiceLocator.getBusinessObjectService().save(oldDeptEarnCode);
				}
				departmentEarnCode.setTimestamp(new Timestamp(System.currentTimeMillis()));
				departmentEarnCode.setTkDeptEarnCodeId(null);
			}
		}
		
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
