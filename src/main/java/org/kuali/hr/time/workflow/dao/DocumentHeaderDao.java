package org.kuali.hr.time.workflow.dao;

import java.util.Date;

import org.kuali.hr.time.workflow.TkDocumentHeader;

public interface DocumentHeaderDao {

	public void saveOrUpdate(TkDocumentHeader documentHeader);
	
	public TkDocumentHeader getDocumentHeader(Long documentId);
	
	public TkDocumentHeader getDocumentHeader(String principalId, Date payEndDate);
}
