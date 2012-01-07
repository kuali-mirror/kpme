package org.kuali.hr.time.shiftdiff.rule.service;

import java.util.Map;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.util.GlobalVariables;

public class ShiftDifferentialRuleMaintenableImpl extends HrBusinessObjectMaintainableImpl {
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
	public HrBusinessObject getObjectById(String id) {
		return TkServiceLocator.getShiftDifferentialRuleService().getShiftDifferentialRule(id);
	}

}
