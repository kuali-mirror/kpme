package org.kuali.hr.time.workflow.service;

import org.apache.log4j.Logger;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.kew.api.exception.WorkflowException;

public class DocumentServiceImpl implements DocumentService {

	private static final Logger LOG = Logger.getLogger(DocumentServiceImpl.class);
	
	@Override
	public WorkflowDocument createWorkflowDocument(String principalId, String documentType, String title) {
		WorkflowDocument workflowDocument = null;
		workflowDocument = WorkflowDocumentFactory.loadDocument(principalId, documentType);
		workflowDocument.setTitle(title);
        return workflowDocument;
	}

}
