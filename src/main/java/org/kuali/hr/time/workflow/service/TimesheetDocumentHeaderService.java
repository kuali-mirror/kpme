package org.kuali.hr.time.workflow.service;

import java.util.Date;

import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

public interface TimesheetDocumentHeaderService {

	public void saveOrUpdate(TimesheetDocumentHeader documentHeader);
	public TimesheetDocumentHeader getDocumentHeader(Long documentId);
	
	public TimesheetDocumentHeader getDocumentHeader(String principalId, Date payEndDate);
}
