package org.kuali.hr.time.workflow.dao;

import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

import java.util.Date;
import java.util.List;

public interface TimesheetDocumentHeaderDao {

	public void saveOrUpdate(TimesheetDocumentHeader documentHeader);
	
	public TimesheetDocumentHeader getTimesheetDocumentHeader(String documentId);
	
	public TimesheetDocumentHeader getTimesheetDocumentHeader(String principalId, Date PayBeginDate, Date payEndDate);
	
	public TimesheetDocumentHeader getPreviousDocumentHeader(String principalId, Date payBegindate);

    public TimesheetDocumentHeader getNextDocumentHeader(String principalId, Date payEndDate);
    
    public List<TimesheetDocumentHeader> getDocumentHeaders(Date payBeginDate);

    public List<TimesheetDocumentHeader> getDocumentHeadersByField(String field, String value);

    public List<String> getValueByField(String field, String value);

    List<TimesheetDocumentHeader> getSortedDocumentHeaders(String orderBy, String orderDirection, int rows);
}
