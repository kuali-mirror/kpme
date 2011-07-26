package org.kuali.hr.time.overtime.weekly.rule.service;

import java.sql.Timestamp;
import java.util.Map;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;

public class WeeklyOvertimeRuleMaintainableImpl extends HrBusinessObjectMaintainableImpl {

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
		WeeklyOvertimeRule weeklyOvertimeRule = (WeeklyOvertimeRule) document
				.getDocumentBusinessObject();
		weeklyOvertimeRule.setUserPrincipalId(GlobalVariables.getUserSession()
				.getPrincipalId());
		super.processAfterPost(document, parameters);
	}

	@Override
	public void processAfterEdit(MaintenanceDocument document,
			Map<String, String[]> parameters) {
		WeeklyOvertimeRule weeklyOvertimeRule = (WeeklyOvertimeRule) document
				.getDocumentBusinessObject();
		weeklyOvertimeRule.setUserPrincipalId(GlobalVariables.getUserSession()
				.getPrincipalId());
		super.processAfterEdit(document, parameters);
	}

	@Override
	public HrBusinessObject getObjectById(Long id) {
		return TkServiceLocator.getWeeklyOvertimeRuleService().getWeeklyOvertimeRule(id);
	}

}