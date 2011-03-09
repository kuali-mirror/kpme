package org.kuali.hr.time.overtime.daily.rule.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;


public class DailyOvertimeRuleMaintainableImpl extends
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
		DailyOvertimeRule dailyOvertimeRule = (DailyOvertimeRule) document
				.getDocumentBusinessObject();
		dailyOvertimeRule.setUserPrincipalId(GlobalVariables.getUserSession()
				.getPrincipalId());
		super.processAfterPost(document, parameters);
	}

	@Override
	public void processAfterEdit(MaintenanceDocument document,
			Map<String, String[]> parameters) {
		DailyOvertimeRule dailyOvertimeRule = (DailyOvertimeRule) document
				.getDocumentBusinessObject();
		dailyOvertimeRule.setUserPrincipalId(GlobalVariables.getUserSession()
				.getPrincipalId());
		super.processAfterEdit(document, parameters);
	}

	@Override
	public void saveBusinessObject() {
		DailyOvertimeRule dailyOvertimeRule = (DailyOvertimeRule) this
				.getBusinessObject();
//		DailyOvertimeRule oldDailyOvertimeRule = (DailyOvertimeRule) KNSServiceLocator
//				.getBusinessObjectService().findBySinglePrimaryKey(
//						DailyOvertimeRule.class,
//						dailyOvertimeRule.getTkDailyOvertimeRuleId());
//		if (oldDailyOvertimeRule != null) {
//			oldDailyOvertimeRule.setActive(false);
//			KNSServiceLocator.getBusinessObjectService().save(
//					oldDailyOvertimeRule);
//		}
		dailyOvertimeRule.setTkDailyOvertimeRuleId(null);
		dailyOvertimeRule.setTimeStamp(null);
		KNSServiceLocator.getBusinessObjectService().save(dailyOvertimeRule);
	}
	
	@Override
	public Map populateBusinessObject(Map<String, String> fieldValues,
			MaintenanceDocument maintenanceDocument, String methodToCall) {
		if(fieldValues.containsKey("workArea") && StringUtils.equals(fieldValues.get("workArea"),"%")){
			fieldValues.put("workArea", "-1");
		}
		return super.populateBusinessObject(fieldValues, maintenanceDocument,
				methodToCall);
	}
    

}