package org.kuali.hr.time.dept.lunch.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.core.util.RiceConstants;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.ErrorMessage;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.RiceKeyConstants;

public class DeptLunchRuleMaintainableImpl extends
		org.kuali.rice.kns.maintenance.KualiMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void processAfterNew(MaintenanceDocument document,
			Map<String, String[]> parameters) {
		super.processAfterNew(document, parameters);
	}

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
		// DeptLunchRule oldDeptLunchRule = (DeptLunchRule) KNSServiceLocator
		// .getBusinessObjectService().findBySinglePrimaryKey(
		// DeptLunchRule.class,
		// deptLunchRule.getTkDeptLunchRuleId());
		// if (oldDeptLunchRule != null) {
		// oldDeptLunchRule.setActive(false);
		// KNSServiceLocator.getBusinessObjectService().save(oldDeptLunchRule);
		// }
		deptLunchRule.setTkDeptLunchRuleId(null);
		deptLunchRule.setTimestamp(null);
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
