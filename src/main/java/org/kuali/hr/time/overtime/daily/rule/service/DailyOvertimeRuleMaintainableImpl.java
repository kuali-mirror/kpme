package org.kuali.hr.time.overtime.daily.rule.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.util.GlobalVariables;


public class DailyOvertimeRuleMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	@SuppressWarnings("rawtypes")
	@Override
	public Map populateBusinessObject(Map<String, String> fieldValues,
			MaintenanceDocument maintenanceDocument, String methodToCall) {
		if(fieldValues.containsKey("workArea") && StringUtils.equals(fieldValues.get("workArea"),"%")){
			fieldValues.put("workArea", "-1");
		}
		return super.populateBusinessObject(fieldValues, maintenanceDocument,
				methodToCall);
	}

	@Override
	public HrBusinessObject getObjectById(String id) {
		return TkServiceLocator.getDailyOvertimeRuleService().getDailyOvertimeRule(id);
	}
    

}