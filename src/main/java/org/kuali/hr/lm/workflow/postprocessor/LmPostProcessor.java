package org.kuali.hr.lm.workflow.postprocessor;

import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.framework.postprocessor.ProcessDocReport;
import org.kuali.rice.kew.postprocessor.DefaultPostProcessor;

public class LmPostProcessor extends DefaultPostProcessor {

	@Override
	public ProcessDocReport doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent) throws Exception {		
		ProcessDocReport pdr = null;
		Long documentId = new Long(statusChangeEvent.getDocumentId());
		LeaveCalendarDocumentHeader document = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(documentId.toString());
		if (document != null) {
			pdr = super.doRouteStatusChange(statusChangeEvent);
			// Only update the status if it's different.
			if (!document.getDocumentStatus().equals(statusChangeEvent.getNewRouteStatus())) {
				document.setDocumentStatus(statusChangeEvent.getNewRouteStatus());
				TkServiceLocator.getLeaveCalendarDocumentHeaderService().saveOrUpdate(document);
			}
		}
		
		return pdr;
	}

}
