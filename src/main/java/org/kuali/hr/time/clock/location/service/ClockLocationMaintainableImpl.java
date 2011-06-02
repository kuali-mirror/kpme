package org.kuali.hr.time.clock.location.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.clock.location.ClockLocationRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;

/**
 * 
 * Used to modify behavior of Clock Location Maintenance page
 * 
 */
public class ClockLocationMaintainableImpl extends KualiMaintainableImpl {

	/**
     * 
     */
	private static final long serialVersionUID = -93577754706987067L;

	/**
	 * Used to preserve immutability of ClockLocationRule
	 */

	@Override
	public void saveBusinessObject() {
		ClockLocationRule clockLocationRule = (ClockLocationRule) this
				.getBusinessObject();
		//Inactivate the old clock location rule as of the effective date of new clock location rule
		if(clockLocationRule.getTkClockLocationRuleId()!=null && clockLocationRule.isActive()){
			ClockLocationRule oldClockLocationRule = TkServiceLocator.getClockLocationRuleService().getClockLocationRule(clockLocationRule.getTkClockLocationRuleId());
			//Do not lay down row if effective date is not before new one
			if(oldClockLocationRule.getEffectiveDate().before(clockLocationRule.getEffectiveDate())){
				oldClockLocationRule.setActive(false);
				//NOTE this is done to prevent the timestamp of the inactive one to be greater than the 
				oldClockLocationRule.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(TKUtils.getCurrentDate().getTime())));
				oldClockLocationRule.setTkClockLocationRuleId(null);
				KNSServiceLocator.getBusinessObjectService().save(oldClockLocationRule);
			}
		}
		
		clockLocationRule.setTimestamp(new Timestamp(TKUtils.getCurrentDate().getTime()));
		clockLocationRule.setTkClockLocationRuleId(null);
		KNSServiceLocator.getBusinessObjectService().save(clockLocationRule);
	}

	/**
	 * Used to swap out wildcard values for numeric types
	 */
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

	@Override
	public void processAfterEdit(MaintenanceDocument document,
			Map<String, String[]> parameters) {
		ClockLocationRule clockLocationRule = (ClockLocationRule) this
				.getBusinessObject();
		List<ClockLocationRule> clockLocationRuleList = TkServiceLocator
				.getClockLocationRuleService()
				.getNewerVersionClockLocationRule(clockLocationRule.getDept(),
						clockLocationRule.getWorkArea(),
						clockLocationRule.getPrincipalId(),
						clockLocationRule.getJobNumber(),
						clockLocationRule.getEffectiveDate());
		if (clockLocationRuleList.size() > 0) {
			GlobalVariables.getMessageMap().putWarningForSectionId(
					"Clock Location Rule Maintenance",
					"clocklocationrule.newer.exists", null);
		}
		super.processAfterEdit(document, parameters);
	}
}
