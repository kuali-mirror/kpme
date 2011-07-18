  package org.kuali.hr.time.dept.lunch.service;

import java.sql.Timestamp;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;

public class DeptLunchRuleMaintainableImpl extends
		org.kuali.rice.kns.maintenance.KualiMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void processAfterPost(MaintenanceDocument document,
			Map<String, String[]> parameters) {
		DeptLunchRule deptLunchRule = (DeptLunchRule) document
				.getDocumentBusinessObject();
		deptLunchRule.setUserPrincipalId(GlobalVariables.getUserSession()
				.getPrincipalId());
		super.processAfterPost(document, parameters);
	}

	@Override
	public void processAfterEdit(MaintenanceDocument document,
			Map<String, String[]> parameters) {
		DeptLunchRule deptLunchRule = (DeptLunchRule) document
				.getDocumentBusinessObject();
		deptLunchRule.setUserPrincipalId(GlobalVariables.getUserSession()
				.getPrincipalId());
		super.processAfterEdit(document, parameters);
	}

	@Override
	public void saveBusinessObject() {
		DeptLunchRule deptLunchRule = (DeptLunchRule) this.getBusinessObject();
		//Inactivate the old dept lunch rule as of the effective date of new dept lunch rules
		if(deptLunchRule.getTkDeptLunchRuleId()!=null && deptLunchRule.isActive()){
			DeptLunchRule oldDeptLunchRule = TkServiceLocator.getDepartmentLunchRuleService().getDepartmentLunchRule(deptLunchRule.getTkDeptLunchRuleId());
			if(deptLunchRule.getEffectiveDate().equals(oldDeptLunchRule.getEffectiveDate())){
				deptLunchRule.setTimestamp(null);
			} else{
				if(oldDeptLunchRule!=null){
					oldDeptLunchRule.setActive(false);
					//NOTE this is done to prevent the timestamp of the inactive one to be greater than the 
					oldDeptLunchRule.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(System.currentTimeMillis())));
					oldDeptLunchRule.setEffectiveDate(deptLunchRule.getEffectiveDate());
					KNSServiceLocator.getBusinessObjectService().save(oldDeptLunchRule);
				}
				deptLunchRule.setTimestamp(new Timestamp(System.currentTimeMillis()));
				deptLunchRule.setTkDeptLunchRuleId(null);
			}
		}
		
		KNSServiceLocator.getBusinessObjectService().save(deptLunchRule);
		
		
	}

	@Override
	public Map populateBusinessObject(Map<String, String> fieldValues,
			MaintenanceDocument maintenanceDocument, String methodToCall) {
		if (fieldValues.containsKey("workArea")
				&& StringUtils.equals(fieldValues.get("workArea"), "%")) {
			fieldValues.put("workArea", "-1");
		}
		if (fieldValues.containsKey("jobNumber")
				&& StringUtils.equals(fieldValues.get("jobNumber"), "%")) {
			fieldValues.put("jobNumber", "-1");
		}
		return super.populateBusinessObject(fieldValues, maintenanceDocument,
				methodToCall);
	}
}
