package org.kuali.hr.time.overtime.weekly.rule.service;

import java.sql.Timestamp;
import java.util.Map;

import org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
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
		if(weeklyOvertimeRule.getTkWeeklyOvertimeRuleId()!=null && weeklyOvertimeRule.isActive()){
			WeeklyOvertimeRule oldWeeklyOvertimeRule = TkServiceLocator.getWeeklyOvertimeRuleService().getWeeklyOvertimeRule(weeklyOvertimeRule.getTkWeeklyOvertimeRuleId());
			if(weeklyOvertimeRule.getEffectiveDate().equals(oldWeeklyOvertimeRule.getEffectiveDate())){
				weeklyOvertimeRule.setTimestamp(null);
			} else{
				if(oldWeeklyOvertimeRule!=null){
					oldWeeklyOvertimeRule.setActive(false);
					//NOTE this is done to prevent the timestamp of the inactive one to be greater than the 
					oldWeeklyOvertimeRule.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(System.currentTimeMillis())));
					oldWeeklyOvertimeRule.setEffectiveDate(weeklyOvertimeRule.getEffectiveDate());
					KNSServiceLocator.getBusinessObjectService().save(oldWeeklyOvertimeRule);
				}
				weeklyOvertimeRule.setTimestamp(new Timestamp(System.currentTimeMillis()));
				weeklyOvertimeRule.setTkWeeklyOvertimeRuleId(null);
			}
		}		
		KNSServiceLocator.getBusinessObjectService().save(weeklyOvertimeRule);
	}
}