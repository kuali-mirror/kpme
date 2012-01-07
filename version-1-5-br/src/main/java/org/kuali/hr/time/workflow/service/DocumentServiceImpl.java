package org.kuali.hr.time.workflow.service;

import org.apache.log4j.Logger;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kew.service.WorkflowDocument;

public class DocumentServiceImpl implements DocumentService {

	private static final Logger LOG = Logger.getLogger(DocumentServiceImpl.class);
	
	@Override
	public WorkflowDocument createWorkflowDocument(String principalId, String documentType, String title) {
		WorkflowDocument workflowDocument = null;
		try {
			workflowDocument = new WorkflowDocument(principalId, documentType);
			workflowDocument.setTitle(title);
		} catch (WorkflowException we) {
			LOG.error(we);
		}
        return workflowDocument;
	}

}
