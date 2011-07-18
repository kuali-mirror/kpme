package org.kuali.hr.time.shiftdiff.rule.service;

import java.sql.Timestamp;
import java.util.Map;

import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule;
import org.kuali.hr.time.util.TKUtils;
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
		if(shiftDifferentialRule.getTkShiftDiffRuleId()!=null && shiftDifferentialRule.isActive()){
			ShiftDifferentialRule oldShiftDiffRule = TkServiceLocator.getShiftDifferentialRuleService().getShiftDifferentialRule(shiftDifferentialRule.getTkShiftDiffRuleId());
			if(shiftDifferentialRule.getEffectiveDate().equals(oldShiftDiffRule.getEffectiveDate())){
				shiftDifferentialRule.setTimestamp(null);
			} else{
				if(oldShiftDiffRule!=null){
					oldShiftDiffRule.setActive(false);
					//NOTE this is done to prevent the timestamp of the inactive one to be greater than the 
					oldShiftDiffRule.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(System.currentTimeMillis())));
					oldShiftDiffRule.setEffectiveDate(shiftDifferentialRule.getEffectiveDate());
					KNSServiceLocator.getBusinessObjectService().save(oldShiftDiffRule);
				}
				shiftDifferentialRule.setTimestamp(new Timestamp(System.currentTimeMillis()));
				shiftDifferentialRule.setTkShiftDiffRuleId(null);
			}
		}
		KNSServiceLocator.getBusinessObjectService()
				.save(shiftDifferentialRule);
	}

}
