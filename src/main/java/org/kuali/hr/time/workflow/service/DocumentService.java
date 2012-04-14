package org.kuali.hr.time.workflow.service;

import org.kuali.rice.kew.api.WorkflowDocument;

public interface DocumentService {
	/**
	 * Create workflow document  
	 * @param principalId
	 * @param documentType
	 * @param title
	 * @return
	 */
	public WorkflowDocument createWorkflowDocument(String principalId, String documentType, String title);
}
