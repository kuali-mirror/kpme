package org.kuali.hr.time.workflow.service;

import java.util.Date;

import org.kuali.hr.time.workflow.TkDocumentHeader;

public interface DocumentHeaderService {

	public void saveOrUpdate(TkDocumentHeader documentHeader);
	public TkDocumentHeader getDocumentHeader(Long documentId);
	
	public TkDocumentHeader getDocumentHeader(String principalId, Date payEndDate);
}
