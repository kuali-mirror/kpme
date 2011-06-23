package org.kuali.hr.time.shiftdiff.rule.service;

import java.util.Map;

import org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;

public class ShiftDifferentialRuleMaintenableImpl extends KualiMaintainableImpl {
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
		ShiftDifferentialRule shiftDifferentialRule = (ShiftDifferentialRule) document
				.getNewMaintainableObject().getBusinessObject();
		shiftDifferentialRule.setUserPrincipalId(GlobalVariables
				.getUserSession().getPrincipalId());
		super.processAfterPost(document, parameters);
	}

	@Override
	public void processAfterEdit(MaintenanceDocument document,
			Map<String, String[]> parameters) {
		ShiftDifferentialRule shiftDifferentialRule = (ShiftDifferentialRule) document
				.getNewMaintainableObject().getBusinessObject();
		shiftDifferentialRule.setUserPrincipalId(GlobalVariables
				.getUserSession().getPrincipalId());
		super.processAfterEdit(document, parameters);
	}

	@Override
	public void saveBusinessObject() {
		ShiftDifferentialRule shiftDifferentialRule = (ShiftDifferentialRule) this
				.getBusinessObject();
		shiftDifferentialRule.setTkShiftDiffRuleId(null);
		shiftDifferentialRule.setTimestamp(null);
		KNSServiceLocator.getBusinessObjectService()
				.save(shiftDifferentialRule);
	}

}
