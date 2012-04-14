package org.kuali.hr.time.workflow.postprocessor;

import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.framework.postprocessor.ProcessDocReport;
import org.kuali.rice.kew.postprocessor.DefaultPostProcessor;

public class TkPostProcessor extends DefaultPostProcessor {

	@Override
	public ProcessDocReport doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent) throws Exception {		
		ProcessDocReport pdr = null;
		Long documentId = new Long(statusChangeEvent.getDocumentId());
		TimesheetDocumentHeader document = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId.toString());
		if (document != null) {
			pdr = super.doRouteStatusChange(statusChangeEvent);
			// Only update the status if it's different.
			if (!document.getDocumentStatus().equals(statusChangeEvent.getNewRouteStatus())) {
				document.setDocumentStatus(statusChangeEvent.getNewRouteStatus());
				TkServiceLocator.getTimesheetDocumentHeaderService().saveOrUpdate(document);
			}
		}
		
		return pdr;
	}

}
