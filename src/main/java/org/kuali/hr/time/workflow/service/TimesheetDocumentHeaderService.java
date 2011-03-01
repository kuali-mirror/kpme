package org.kuali.hr.time.workflow.service;

import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

import java.util.Date;

public interface TimesheetDocumentHeaderService {

	public void saveOrUpdate(TimesheetDocumentHeader documentHeader);
	public TimesheetDocumentHeader getDocumentHeader(String documentId);
	
	public TimesheetDocumentHeader getDocumentHeader(String principalId, Date payBeginDate, Date payEndDate);
    public TimesheetDocumentHeader getPreviousDocumentHeader(String principalId, Date payBeginDate);
    TimesheetDocumentHeader getPrevOrNextDocumentHeader(String prevOrNext, String principalId, String documentId);
}
