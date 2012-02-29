package org.kuali.hr.time.workflow.postprocessor;

import org.kuali.rice.kew.postprocessor.DefaultPostProcessor;
import org.kuali.rice.kew.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.postprocessor.ProcessDocReport;

public class TkMissedPunchPostProcessor extends DefaultPostProcessor {

	@Override
	public ProcessDocReport doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent) throws Exception {
		ProcessDocReport pdr = super.doRouteStatusChange(statusChangeEvent);

		/*Long documentId = statusChangeEvent.getRouteHeaderId();

        DocumentHeader header = KNSServiceLocator.getDocumentHeaderService().getDocumentHeaderById(documentId.toString());*/
		return pdr;
	}

}
