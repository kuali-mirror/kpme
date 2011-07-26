package org.kuali.hr.time.clock.location.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.clock.location.ClockLocationRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.util.GlobalVariables;

/**
 * 
 * Used to modify behavior of Clock Location Maintenance page
 * 
 */
public class ClockLocationMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

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

	@Override
	public HrBusinessObject getObjectById(Long id) {
		return TkServiceLocator.getClockLocationRuleService().getClockLocationRule(id);
	}
}
