package org.kuali.hr.job.validation;

import org.kuali.hr.job.Job;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class JobValidation extends MaintenanceDocumentRuleBase {
	@Override
	protected boolean processCustomSaveDocumentBusinessRules(
			MaintenanceDocument document) {
		Job job = (Job)this.getNewBo();
		//TODO add validation here
		
		
		return true;
	}
}
