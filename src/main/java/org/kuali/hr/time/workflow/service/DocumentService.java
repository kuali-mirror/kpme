package org.kuali.hr.time.workflow.service;

import org.kuali.rice.kew.service.WorkflowDocument;

public interface DocumentService {

	public WorkflowDocument createWorkflowDocument(String principalId, String documentType, String title);
}
