package org.kuali.hr.pm.paystep.validation;

import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.rules.MaintenanceDocumentRule;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.rule.event.ApproveDocumentEvent;

public class PayStepValidation implements MaintenanceDocumentRule {

	@Override
	public boolean processApproveDocument(ApproveDocumentEvent arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean processRouteDocument(Document arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean processSaveDocument(Document arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setupBaseConvenienceObjects(MaintenanceDocument arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setupConvenienceObjects() {
		// TODO Auto-generated method stub
		
	}


}
