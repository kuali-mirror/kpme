package org.kuali.hr.time.collection.rule.service;

import java.util.Map;

import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.util.GlobalVariables;


public class TimeCollectionRuleMaintainableImpl extends KualiMaintainableImpl {
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
		TimeCollectionRule timeCollectionRule = (TimeCollectionRule) document
				.getNewMaintainableObject().getBusinessObject();
		timeCollectionRule.setUserPrincipalId(GlobalVariables.getUserSession()
				.getPrincipalId());
		super.processAfterPost(document, parameters);
	}

	@Override
	public void processAfterEdit(MaintenanceDocument document,
			Map<String, String[]> parameters) {
		TimeCollectionRule timeCollectionRule = (TimeCollectionRule) document
				.getNewMaintainableObject().getBusinessObject();
		timeCollectionRule.setUserPrincipalId(GlobalVariables.getUserSession()
				.getPrincipalId());
		super.processAfterEdit(document, parameters);
	}
}
