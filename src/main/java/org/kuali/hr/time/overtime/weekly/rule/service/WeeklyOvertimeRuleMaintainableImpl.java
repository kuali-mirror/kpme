package org.kuali.hr.time.overtime.weekly.rule.service;

import java.util.Map;

import org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule;
import org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;

public class WeeklyOvertimeRuleMaintainableImpl extends
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
	public void saveBusinessObject() {
		WeeklyOvertimeRule weeklyOvertimeRule = (WeeklyOvertimeRule) this
				.getBusinessObject();
		WeeklyOvertimeRule oldWeeklyOvertimeRule = (WeeklyOvertimeRule) KNSServiceLocator
				.getBusinessObjectService().findBySinglePrimaryKey(
						WeeklyOvertimeRule.class,
						weeklyOvertimeRule.getTkWeeklyOvertimeRuleId());
		if (oldWeeklyOvertimeRule != null) {
			oldWeeklyOvertimeRule.setActive(false);
			KNSServiceLocator.getBusinessObjectService().save(oldWeeklyOvertimeRule);
		}
		weeklyOvertimeRule.setTkWeeklyOvertimeRuleId(null);
		weeklyOvertimeRule.setTimeStamp(null);
		KNSServiceLocator.getBusinessObjectService().save(weeklyOvertimeRule);
	}
}