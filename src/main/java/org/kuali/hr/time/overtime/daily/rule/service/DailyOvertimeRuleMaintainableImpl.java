package org.kuali.hr.time.overtime.daily.rule.service;


import java.util.Map;

import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;

public class DailyOvertimeRuleMaintainableImpl extends org.kuali.rice.kns.maintenance.KualiMaintainableImpl {

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
		DailyOvertimeRule dailyOvertimeRule = (DailyOvertimeRule) document.getDocumentBusinessObject();
		dailyOvertimeRule.setUserPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
		super.processAfterPost(document, parameters);
	}
	
	@Override
	public void processAfterEdit(MaintenanceDocument document,
			Map<String, String[]> parameters) {
		DailyOvertimeRule dailyOvertimeRule = (DailyOvertimeRule) document.getDocumentBusinessObject();
		dailyOvertimeRule.setUserPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
		super.processAfterEdit(document, parameters);
	}
}