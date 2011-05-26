package org.kuali.hr.time.workflow.dao;

import org.apache.ojb.broker.query.Criteria;
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

    List<TimesheetDocumentHeader> getDocumentHeaders(Criteria crit, int start, int end);

    List<String> getDataByField(Criteria crit, String[] fields);
}
