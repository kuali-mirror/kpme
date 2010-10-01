package org.kuali.hr.time.workflow.postprocessor;

import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.postprocessor.DefaultPostProcessor;
import org.kuali.rice.kew.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.postprocessor.ProcessDocReport;

public class TkPostProcessor extends DefaultPostProcessor {

	@Override
	public ProcessDocReport doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent) throws Exception {		
		ProcessDocReport pdr = super.doRouteStatusChange(statusChangeEvent);

		Long documentId = statusChangeEvent.getRouteHeaderId();
		TimesheetDocumentHeader document = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
		if (document != null) {
			// Only update the status if it's different.
			if (!document.getDocumentStatus().equals(statusChangeEvent.getNewRouteStatus())) {
				document.setDocumentStatus(statusChangeEvent.getNewRouteStatus());
				TkServiceLocator.getTimesheetDocumentHeaderService().saveOrUpdate(document);
			}
		}
		
		return pdr;
	}

}
