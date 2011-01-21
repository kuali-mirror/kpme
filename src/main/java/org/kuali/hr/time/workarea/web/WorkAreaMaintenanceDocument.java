package org.kuali.hr.time.workarea.web;

import org.kuali.rice.kew.dto.DocumentRouteStatusChangeDTO;
import org.kuali.rice.kns.document.MaintenanceDocumentBase;

public class WorkAreaMaintenanceDocument extends MaintenanceDocumentBase {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public WorkAreaMaintenanceDocument() {
        super();
    }

    public WorkAreaMaintenanceDocument(String documentTypeName) {
        super(documentTypeName);
    }

	
    @Override
    public void doRouteStatusChange(DocumentRouteStatusChangeDTO statusChangeEvent) {
        super.doRouteStatusChange(statusChangeEvent);
        populateXmlDocumentContentsFromMaintainables();
    }
}

