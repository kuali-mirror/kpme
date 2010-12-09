package org.kuali.hr.time.overtime.weekly.rule;

import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class WeeklyOvertimeRuleValidation extends MaintenanceDocumentRuleBase {

	@Override
	protected boolean validateMaintenanceDocument(
			MaintenanceDocument maintenanceDocument) {
		// TODO Auto-generated method stub
		return super.validateMaintenanceDocument(maintenanceDocument);
	}

	@Override
	public boolean processAddCollectionLineBusinessRules(
			MaintenanceDocument document, String collectionName,
			PersistableBusinessObject bo) {
		// TODO Auto-generated method stub
		return super
				.processAddCollectionLineBusinessRules(document, collectionName, bo);
	}

}
