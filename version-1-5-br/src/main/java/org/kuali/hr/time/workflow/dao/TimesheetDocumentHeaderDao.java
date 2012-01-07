package org.kuali.hr.time.workflow.dao;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

public interface TimesheetDocumentHeaderDao {

	public void saveOrUpdate(TimesheetDocumentHeader documentHeader);

	public TimesheetDocumentHeader getTimesheetDocumentHeader(String documentId);

	public TimesheetDocumentHeader getTimesheetDocumentHeader(String principalId, Date PayBeginDate, Date payEndDate);

	public TimesheetDocumentHeader getPreviousDocumentHeader(String principalId, Date payBegindate);

    public TimesheetDocumentHeader getNextDocumentHeader(String principalId, Date payEndDate);

    public List<TimesheetDocumentHeader> getDocumentHeaders(Date payBeginDate);
    
    public void deleteTimesheetHeader(String documentId);
}
