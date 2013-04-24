/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.tklm.time.workflow.service;

import org.joda.time.DateTime;
import org.kuali.hr.tklm.time.workflow.TimesheetDocumentHeader;

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
	public TimesheetDocumentHeader getDocumentHeader(String principalId, DateTime payBeginDate, DateTime payEndDate);
	/**
	 * Fetch previous document header
	 * @param principalId
	 * @param payBeginDate
	 * @return
	 */
    public TimesheetDocumentHeader getPreviousDocumentHeader(String principalId, DateTime payBeginDate);
	/**
	 * Fetch next document header
	 * @param principalId
	 * @param payBeginDate
	 * @return
	 */
    public TimesheetDocumentHeader getNextDocumentHeader(String principalId, DateTime payEndDate);
    /**
     * Fetch previous or next Document Header -- uses the current Document from context.
     * @param prevOrNext
     * @param principalId
     * @return
     */
    TimesheetDocumentHeader getPrevOrNextDocumentHeader(String prevOrNext, String principalId);
	/**
	 * Fetch document headers for a given pay period begin date and end date
	 * @param payBeginDate
	 * @param payEndDate
	 * @return
	 */
	public List<TimesheetDocumentHeader> getDocumentHeaders(DateTime payBeginDate, DateTime payEndDate);

    public void deleteTimesheetHeader(String documentId);
    
    /**
     * Fetch list of Document Headers by given principal id
     * @param principalId
     * @return List<TimesheetDocumentHeader>
     */
    public List<TimesheetDocumentHeader> getDocumentHeadersForPrincipalId(String principalId);
    
    /**
     * Fetch list of Document Headers by given principal id and year
     * @param principalId
     * @param year
     * @return List<TimesheetDocumentHeader>
     */
    public List<TimesheetDocumentHeader> getDocumentHeadersForYear(String principalId, String year);
    /*
     * Fetch the Timesheet document header that contains asOfDate
     * @param principalId
     * @param asOfDate
     * @return TimesheetDocumentHeader
     */
    public TimesheetDocumentHeader getDocumentHeaderForDate(String principalId, DateTime asOfDate);
    
}
