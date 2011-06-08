package org.kuali.hr.time.workflow.service;

import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

import java.util.Date;
import java.util.List;

public interface TimesheetDocumentHeaderService {
	/**
	 * Save or Update the document header
	 * @param documentHeader
	 */
	public void saveOrUpdate(TimesheetDocumentHeader documentHeader);
	/**
	 * Fetch document header for a given document id
	 * @param documentId
	 * @return
	 */
	public TimesheetDocumentHeader getDocumentHeader(String documentId);
	/**
	 * Fetch document header for a given principal id and pay period begin date and end date
	 * @param principalId
	 * @param payBeginDate
	 * @param payEndDate
	 * @return
	 */
	public TimesheetDocumentHeader getDocumentHeader(String principalId, Date payBeginDate, Date payEndDate);
	/**
	 * Fetch previous document header 
	 * @param principalId
	 * @param payBeginDate
	 * @return
	 */
    public TimesheetDocumentHeader getPreviousDocumentHeader(String principalId, Date payBeginDate);
    /**
     * Fetch previous or next Document Header
     * @param prevOrNext
     * @param principalId
     * @param documentId
     * @return
     */
    TimesheetDocumentHeader getPrevOrNextDocumentHeader(String prevOrNext, String principalId, String documentId);
    
    public List<TimesheetDocumentHeader> getDocumentHeaders(Date payBeginDate);

    public List<TimesheetDocumentHeader> getDocumentHeadersByField(String field, String value);

    public List<String> getValueByField(String field, String value);

    List<TimesheetDocumentHeader> getSortedDocumentHeaders(String orderBy, String orderDirection, String rows);
}
