package org.kuali.hr.job.web;

import org.kuali.rice.kew.dto.DocumentRouteStatusChangeDTO;
import org.kuali.rice.kns.document.MaintenanceDocumentBase;

public class JobMaintenanceDocument extends MaintenanceDocumentBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public JobMaintenanceDocument() {
        super();
    }

    public JobMaintenanceDocument(String documentTypeName) {
        super(documentTypeName);
    }

	
    @Override
    public void doRouteStatusChange(DocumentRouteStatusChangeDTO statusChangeEvent) {
        super.doRouteStatusChange(statusChangeEvent);
        populateXmlDocumentContentsFromMaintainables();
    }

}
