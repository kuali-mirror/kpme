package org.kuali.hr.time.workflow.dao;

import java.util.Date;

import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

public interface TimesheetDocumentHeaderDao {

	public void saveOrUpdate(TimesheetDocumentHeader documentHeader);
	
	public TimesheetDocumentHeader getTimesheetDocumentHeader(String documentId);
	
	public TimesheetDocumentHeader getTimesheetDocumentHeader(String principalId, Date PayBeginDate, Date payEndDate);
	
	public TimesheetDocumentHeader getPreviousDocumentHeader(String principalId, Long currDocumentId);
}
