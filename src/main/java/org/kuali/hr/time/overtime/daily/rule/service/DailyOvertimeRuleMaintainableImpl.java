package org.kuali.hr.time.overtime.daily.rule.service;

import java.sql.Timestamp;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;


public class DailyOvertimeRuleMaintainableImpl extends
		org.kuali.rice.kns.maintenance.KualiMaintainableImpl {

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

	@Override
	public void saveBusinessObject() {
		DailyOvertimeRule dailyOvertimeRule = (DailyOvertimeRule) this
				.getBusinessObject();

		if(dailyOvertimeRule.getTkDailyOvertimeRuleId()!=null && dailyOvertimeRule.isActive()){
			DailyOvertimeRule oldDailyOvtRule = TkServiceLocator.getDailyOvertimeRuleService().getDailyOvertimeRule(dailyOvertimeRule.getTkDailyOvertimeRuleId());
			if(dailyOvertimeRule.getEffectiveDate().equals(oldDailyOvtRule.getEffectiveDate())){
				dailyOvertimeRule.setTimestamp(null);
			} else{
				if(oldDailyOvtRule!=null){
					oldDailyOvtRule.setActive(false);
					//NOTE this is done to prevent the timestamp of the inactive one to be greater than the 
					oldDailyOvtRule.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(System.currentTimeMillis())));
					oldDailyOvtRule.setEffectiveDate(dailyOvertimeRule.getEffectiveDate());
					KNSServiceLocator.getBusinessObjectService().save(oldDailyOvtRule);
				}
				dailyOvertimeRule.setTimestamp(new Timestamp(System.currentTimeMillis()));
				dailyOvertimeRule.setTkDailyOvertimeRuleId(null);
			}
		}
		
		KNSServiceLocator.getBusinessObjectService().save(dailyOvertimeRule);		
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
    

}